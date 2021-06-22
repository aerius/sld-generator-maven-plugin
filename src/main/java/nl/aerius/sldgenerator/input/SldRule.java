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
package nl.aerius.sldgenerator.input;

import java.util.List;

/**
 * Represents an individual rule in the .sld.json file
 */
public class SldRule {

  private String condition;
  private String strokeColor;
  private String fillColor;
  private String imageUrl;
  private List<String> customDrawSld;
  private List<String> customConditionSld;

  private static final String DELIMITER = "\n";

  // Object must be constructable from Jackson
  public SldRule() {
  }

  public SldRule(final String condition, final String strokeColor, final String fillColor, final String imageUrl, final List<String> customDrawSld,
      final List<String> customConditionSld) {
    this.condition = condition;
    this.strokeColor = strokeColor;
    this.fillColor = fillColor;
    this.imageUrl = imageUrl;
    this.customDrawSld = customDrawSld;
    this.customConditionSld = customConditionSld;
  }

  /**
   * The rule from which to generate the rule part of.
   * Will be overridden by {@link #customConditionSld} if specified.
   */
  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  /**
   * The color to use for the borders.
   * Will be overridden by {@link #customDrawSld}/{@link #imageUrl} if specified.
   */
  public String getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(String strokeColor) {
    this.strokeColor = strokeColor;
  }

  /**
   * The fill color to use when generating the draw part.
   * Will be overridden by {@link #customDrawSld}/{@link #imageUrl} if specified.
   */
  public String getFillColor() {
    return fillColor;
  }

  public void setFillColor(String fillColor) {
    this.fillColor = fillColor;
  }

  /**
   * The URL to the image.
   * Will be overridden by {@link #customDrawSld} if specified. Should be relative to the webapp.
   */
  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * The custom XML used in the draw part for this rule in the SLD.
   * It should contain the symbolizer + content.
   */
  public List<String> getCustomDrawSld() {
    return customDrawSld;
  }

  public void setCustomDrawSld(List<String> customDrawSld) {
    this.customDrawSld = customDrawSld;
  }

  public String getCustomDrawSldAsString() {
    if (customDrawSld == null) {
      return null;
    } else {
      return String.join(DELIMITER, customDrawSld);
    }
  }

  /**
   * The custom XML used in the rule condition for this rule in the SLD.
   * It should contain the content of the ogc:Filter tag.
   */
  public List<String> getCustomConditionSld() {
    return customConditionSld;
  }

  public void setCustomConditionSld(List<String> customConditionSld) {
    this.customConditionSld = customConditionSld;
  }

  public String getCustomConditionSldAsString() {
    if (customConditionSld == null) {
      return null;
    } else {
      return String.join(DELIMITER, customConditionSld);
    }
  }
}
