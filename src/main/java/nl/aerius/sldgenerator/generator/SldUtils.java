/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.aerius.sldgenerator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import nl.aerius.sldgenerator.input.SldRule;
import nl.aerius.sldgenerator.input.ZoomLevel;

/**
 * Various SLD helper methods.
 */
public final class SldUtils {

  private static final String NAME_TAG = "Name";
  private static final String TITLE_TAG = "Title";

  private static final int STRING_BUILDER_INITIAL_CAPACITY = 4000;

  /**
   * Create a full SLD based on the {@link SldFile} given.
   * @param sldFile The WMS layer object to generate the SLD for.
   *
   */
  public static String generateSLD(final SldFile sldFile) {
    final StringBuilder sld = new StringBuilder(STRING_BUILDER_INITIAL_CAPACITY);

    sld.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        .append("<sld:StyledLayerDescriptor xmlns:sld=\"http://www.opengis.net/sld\" xmlns:java=\"java\" ")
        .append("xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:wfs=\"http://www.opengis.net/wfs\" ")
        .append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ")
        .append("xmlns:gml=\"http://www.opengis.net/gml\" ")
        .append("xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns=\"http://www.opengis.net/sld\" version=\"1.0.0\" ")
        .append("xsi:schemaLocation=\"http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd\">")
        .append("<sld:NamedLayer>");
    appendSLDTag(sld, NAME_TAG, sldFile.getSldName());
    sld.append("<sld:UserStyle>");
    appendSLDTag(sld, NAME_TAG, sldFile.getSldName());
    appendSLDTag(sld, TITLE_TAG, sldFile.getSldName());
    appendSLDTag(sld, "IsDefault", String.valueOf(1));
    sld.append("<sld:FeatureTypeStyle>");

    if (sldFile.getZoomLevels().isEmpty()) {
      for (final SldRule sldRule : sldFile.getRules()) {
        appendRulePartToSLD(sld, sldRule, null);
      }
    } else {
      for (final ZoomLevel zoomLevel : sldFile.getZoomLevels()) {
        for (final SldRule sldRule : sldFile.getRules()) {
          appendRulePartToSLD(sld, sldRule, zoomLevel);
        }
      }
    }

    sld.append("</sld:FeatureTypeStyle></sld:UserStyle></sld:NamedLayer></sld:StyledLayerDescriptor>");
    return sld.toString();
  }

  private static void appendSLDTag(final StringBuilder sld, final String tag, final String value) {
    sld.append("<sld:");
    sld.append(tag);
    sld.append('>');
    sld.append(escapeXML(value));
    sld.append("</sld:");
    sld.append(tag);
    sld.append('>');
  }

  /**
   * Append the rule part to the SLD. Watch out, zoomLevel is allowed to be null.
   */
  private static void appendRulePartToSLD(final StringBuilder sld, final SldRule sldRule, final ZoomLevel zoomLevel) {
    sld.append("<sld:Rule>");

    appendSLDTag(sld, NAME_TAG, getRuleTitle(sldRule, zoomLevel));
    appendSLDTag(sld, TITLE_TAG, getRuleTitle(sldRule, zoomLevel));

    appendFilterPartForRule(sld, sldRule, zoomLevel);

    if (zoomLevel != null) {
      appendSLDTag(sld, "MinScaleDenominator", Integer.toString(zoomLevel.getMinScale()));
      appendSLDTag(sld, "MaxScaleDenominator", Integer.toString(zoomLevel.getMaxScale()));
    }

    appendSymbolizerPartForRule(sld, sldRule);

    sld.append("</sld:Rule>");
  }

  private static String getRuleTitle(final SldRule sldRule, final ZoomLevel zoomLevel) {
    final String ruleTitle;
    if (sldRule.getCustomConditionSld() == null || sldRule.getCustomConditionSld().isEmpty()) {
      final List<String> titleParts = new ArrayList<>();
      if (zoomLevel != null && zoomLevel.getZoomLevel() > 0) {
        titleParts.add("zoom_level = " + zoomLevel.getZoomLevel());
      }
      if (!StringUtils.isEmpty(sldRule.getCondition())) {
        titleParts.add(sldRule.getCondition());
      }
      ruleTitle = StringUtils.join(titleParts, " && ");
    } else {
      ruleTitle = "customRule";
    }
    return ruleTitle;
  }

  /**
   * Append the filter part of the rule part to the SLD. Watch out, zoomLevel is allowed to be null.
   */
  private static void appendFilterPartForRule(final StringBuilder sld, final SldRule sldRule, final ZoomLevel zoomLevel) {
    final String customConditionSld = sldRule.getCustomConditionSldAsString();
    if (!StringUtils.isEmpty(customConditionSld) || !StringUtils.isEmpty(sldRule.getCondition())
        || zoomLevel != null && zoomLevel.getZoomLevel() > 0) {
      sld.append("<ogc:Filter>");
      if (StringUtils.isEmpty(customConditionSld)) {
        final List<ParsedCondition> subConditions = getSubConditions(sldRule, zoomLevel);
        if (subConditions.size() > 1) {
          sld.append(
              "<ogc:And>");
        }

        appendFilterSubConditions(sld, subConditions);

        if (subConditions.size() > 1) {
          sld.append("</ogc:And>");
        }
      } else {
        sld.append(customConditionSld);
      }
      sld.append("</ogc:Filter>");
    }
  }

  private static void appendFilterSubConditions(final StringBuilder sld, final List<ParsedCondition> subConditions) {
    // Per subcondition, add a block to filter it out.
    for (final ParsedCondition parsedCondition : subConditions) {
      // Add parent NOT tag if its a NOT EQUAL operator.
      if (parsedCondition.conditionOperator == ConditionOperatorEnum.NOT_EQUAL) {
        sld.append("<ogc:Not>");
      }
      sld.append('<').append(parsedCondition.getConditionOperator().getSldTagName()).append('>');
      sld.append("<ogc:PropertyName>").append(escapeXML(parsedCondition.getKey())).append("</ogc:PropertyName>");
      sld.append("<ogc:Literal>").append(escapeXML(parsedCondition.getValue())).append("</ogc:Literal>");
      sld.append("</").append(parsedCondition.getConditionOperator().getSldTagName()).append('>');
      if (parsedCondition.conditionOperator == ConditionOperatorEnum.NOT_EQUAL) {
        sld.append("</ogc:Not>");
      }
    }
  }

  private static List<ParsedCondition> getSubConditions(final SldRule sldRule, final ZoomLevel zoomLevel) {
    final String condition = sldRule.getCondition();
    final List<ParsedCondition> subConditions = new ArrayList<ParsedCondition>();
    if (zoomLevel != null && zoomLevel.getZoomLevel() > 0) {
      subConditions.add(new ParsedCondition("zoom_level", ConditionOperatorEnum.EQUAL, String.valueOf(zoomLevel.getZoomLevel())));
    }
    if (!StringUtils.isEmpty(condition)) {
      for (final String subCondition : condition.split("&&")) {
        final ParsedCondition parsedCondition = parseCondition(subCondition);
        if (parsedCondition == null) {
          // error - bad parse! Ignore it.
          System.out
              .println("Could not parse (sub)condition \"" + subCondition + "\" as part of conditions: " + Arrays.toString(subConditions.toArray()));
        } else {
          subConditions.add(parsedCondition);
        }
      }
    }
    return subConditions;
  }

  /**
   * Returns the parsed condition. Null if parsing failed.
   */
  private static ParsedCondition parseCondition(final String subCondition) {
    ParsedCondition parsedCondition = null;

    for (final ConditionOperatorEnum conditionOperatorEnum : ConditionOperatorEnum.values()) {
      final String conditionOperator = conditionOperatorEnum.getOperator();
      final int posOperator = subCondition.indexOf(conditionOperator);
      if (posOperator > -1) {
        final String key = subCondition.substring(0, posOperator).trim();
        final String value = subCondition.substring(posOperator + conditionOperator.length()).trim();

        parsedCondition = new ParsedCondition(key, conditionOperatorEnum, value);

        break;
      }
    }
    return parsedCondition;
  }

  /**
   * Append the symbolizer part of the rule part to the SLD.
   */
  private static void appendSymbolizerPartForRule(final StringBuilder sld, final SldRule sldRule) {
    final SymbolizerType symbolizerType = determineSymbolizer(sldRule);
    switch (symbolizerType) {
    case GRAPHIC:
      appendGraphicSymbolizerPart(sld, sldRule);
      break;
    case POLYGON_SYMBOLIZER:
      appendPolygonSymbolizerPart(sld, sldRule);
      break;
    case POINT_SYMBOLIZER:
      appendPointSymbolizerPart(sld, sldRule);
      break;
    case CUSTOM:
      sld.append(sldRule.getCustomDrawSldAsString());
      break;
    default:
      break;
    }
  }

  private static SymbolizerType determineSymbolizer(final SldRule sldRule) {
    SymbolizerType correctType;
    if (sldRule.getSymbolizerType() == null) {
      if (sldRule.getCustomDrawSld() == null && !StringUtils.isEmpty(sldRule.getImageUrl())) {
        correctType = SymbolizerType.GRAPHIC;
      } else if (sldRule.getCustomDrawSld() == null) {
        correctType = SymbolizerType.POLYGON_SYMBOLIZER;
      } else {
        correctType = SymbolizerType.CUSTOM;
      }
    } else {
      correctType = SymbolizerType.safeValueOf(sldRule.getSymbolizerType());
    }
    return correctType;
  }

  private static void appendGraphicSymbolizerPart(final StringBuilder sld, final SldRule sldRule) {
    sld.append("<sld:PointSymbolizer>")
        .append("<sld:Geometry><ogc:PropertyName>geometry</ogc:PropertyName></sld:Geometry>")
        .append("<sld:Graphic><sld:ExternalGraphic>")
        .append("<sld:OnlineResource xlink:type=\"simple\" xlink:href=\"").append(sldRule.getImageUrl()).append("\" />")
        .append("<sld:Format>image/png</sld:Format>")
        .append("</sld:ExternalGraphic></sld:Graphic>")
        .append("</sld:PointSymbolizer>");
  }

  private static void appendPolygonSymbolizerPart(final StringBuilder sld, final SldRule sldRule) {
    sld.append("<sld:PolygonSymbolizer>");
    appendFill(sld, sldRule);
    appendStroke(sld, sldRule);
    sld.append("</sld:PolygonSymbolizer>");
  }

  private static void appendPointSymbolizerPart(final StringBuilder sld, final SldRule sldRule) {
    sld.append("<sld:PointSymbolizer>");
    sld.append("<sld:Graphic>");
    sld.append("<sld:Mark>");
    if (!StringUtils.isEmpty(sldRule.getPointType())) {
      sld.append("<sld:WellKnownName>")
          .append(sldRule.getPointType())
          .append("</sld:WellKnownName>");
    }
    appendFill(sld, sldRule);
    appendStroke(sld, sldRule);
    sld.append("</sld:Mark>");
    sld.append("<sld:Size>")
        .append(sldRule.getPointSize())
        .append("</sld:Size>");
    sld.append("</sld:Graphic>");
    sld.append("</sld:PointSymbolizer>");
  }

  private static void appendFill(final StringBuilder sld, final SldRule sldRule) {
    if (!StringUtils.isEmpty(sldRule.getFillColor())) {
      sld.append("<sld:Fill>")
          .append("<sld:CssParameter name=\"fill\">#").append(sldRule.getFillColor()).append("</sld:CssParameter>")
          .append("<sld:CssParameter name=\"fill-opacity\">1</sld:CssParameter>")
          .append("</sld:Fill>");
    }
  }

  private static void appendStroke(final StringBuilder sld, final SldRule sldRule) {
    if (!StringUtils.isEmpty(sldRule.getStrokeColor())) {
      sld.append("<sld:Stroke>")
          .append("<sld:CssParameter name=\"stroke\">#").append(sldRule.getStrokeColor()).append("</sld:CssParameter>")
          .append("<sld:CssParameter name=\"stroke-opacity\">1</sld:CssParameter><sld:CssParameter name=\"stroke-width\">0.5</sld:CssParameter>")
          .append("</sld:Stroke>");
    }
  }

  /**
   * Convenience method to escape XML.
   */
  private static String escapeXML(final String value) {
    return StringEscapeUtils.escapeXml10(value);
  }

  private enum ConditionOperatorEnum {
    // @formatter:off
    LESS_THAN_OR_EQUAL("<=", "ogc:PropertyIsLessThanOrEqualTo"),
    GREATER_THAN_OR_EQUAL(">=", "ogc:PropertyIsGreaterThanOrEqualTo"),
    NOT_EQUAL("!=", "ogc:PropertyIsEqualTo"), // In the code above the tag will be surrounded with a NOT tag
    EQUAL("=", "ogc:PropertyIsEqualTo"),
    LESS_THAN("<", "ogc:PropertyIsLessThan"),
    GREATER_THAN(">", "ogc:PropertyIsGreaterThan");
    // @formatter:on

    private final String operator;
    private final String sldTagName;

    ConditionOperatorEnum(final String operator, final String sldTagName) {
      this.operator = operator;
      this.sldTagName = sldTagName;
    }

    public String getOperator() {
      return operator;
    }

    public String getSldTagName() {
      return sldTagName;
    }
  }

  private static class ParsedCondition {
    private final String key;
    private final ConditionOperatorEnum conditionOperator;
    private final String value;

    public ParsedCondition(final String key, final ConditionOperatorEnum conditionOperator, final String value) {
      this.key = key;
      this.conditionOperator = conditionOperator;
      this.value = value;
    }

    public String getKey() {
      return key;
    }

    public ConditionOperatorEnum getConditionOperator() {
      return conditionOperator;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return "ParsedCondition [key=" + key + ", conditionOperator=" + conditionOperator + ", value=" + value + "]";
    }

  }

  private enum SymbolizerType {

    POLYGON_SYMBOLIZER, POINT_SYMBOLIZER, GRAPHIC, CUSTOM;

    public static final SymbolizerType safeValueOf(final String value) {
      // Use polygon symbolizer as default, either when no value is supplied or an unknown value is used.
      try {
        return value == null ? SymbolizerType.POLYGON_SYMBOLIZER : valueOf(value.toUpperCase(Locale.ROOT));
      } catch (final IllegalArgumentException e) {
        return SymbolizerType.POLYGON_SYMBOLIZER;
      }
    }
  }
}
