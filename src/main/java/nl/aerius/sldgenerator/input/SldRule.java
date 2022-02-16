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

  private String symbolizerType;
  private String condition;
  private String strokeColor;
  private String fillColor;
  private String pointType;
  private int pointSize;
  private String imageUrl;
  private List<String> customDrawSld;
  private List<String> customConditionSld;

  private static final String DELIMITER = "\n";

  /**
   * The symbolizer type to use.
   * If supplied, and if a known value is used, this will override any other implicit type.
   * Known values:
   * - POLYGON_SYMBOLIZER
   * - POINT_SYMBOLIZER
   * - GRAPHIC
   * - CUSTOM
   *
   * If not supplied:
   * - customDrawSld will be used if supplied
   * - graphic will be used if supplied and customDrawSld is not supplied
   * - PolygonSymbolizer will be used otherwise
   */
  public String getSymbolizerType() {
    return symbolizerType;
  }

  public void setSymbolizerType(final String symbolizerType) {
    this.symbolizerType = symbolizerType;
  }

  /**
   * The rule from which to generate the rule part of.
   * Will be overridden by {@link #customConditionSld} if specified.
   */
  public String getCondition() {
    return condition;
  }

  public void setCondition(final String condition) {
    this.condition = condition;
  }

  /**
   * The color to use for the borders.
   * Will be overridden by {@link #customDrawSld}/{@link #imageUrl} if specified.
   */
  public String getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(final String strokeColor) {
    this.strokeColor = strokeColor;
  }

  /**
   * The fill color to use when generating the draw part.
   * Will be overridden by {@link #customDrawSld}/{@link #imageUrl} if specified.
   */
  public String getFillColor() {
    return fillColor;
  }

  public void setFillColor(final String fillColor) {
    this.fillColor = fillColor;
  }

  /**
   * The type of point to use. Will only be used for a PointSymbolizer.
   * For possible values, look at SLD documentation for Point > Mark > WellKnownName
   */
  public String getPointType() {
    return pointType;
  }

  public void setPointType(final String pointType) {
    this.pointType = pointType;
  }

  /**
   * The size of point (in pixels) to use. Will only be used for a PointSymbolizer.
   */
  public int getPointSize() {
    return pointSize;
  }

  public void setPointSize(final int pointSize) {
    this.pointSize = pointSize;
  }

  /**
   * The URL to the image.
   * Will be overridden by {@link #customDrawSld} if specified. Should be relative to the webapp.
   */
  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * The custom XML used in the draw part for this rule in the SLD.
   * It should contain the symbolizer + content.
   */
  public List<String> getCustomDrawSld() {
    return customDrawSld;
  }

  public void setCustomDrawSld(final List<String> customDrawSld) {
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

  public void setCustomConditionSld(final List<String> customConditionSld) {
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
