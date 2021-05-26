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

/**
 * Represents the definition of a zoom level
 */
public class ZoomLevel {

  private int zoomLevel;
  private int minScale;
  private int maxScale;

  // Object must be constructable from Jackson
  public ZoomLevel() {
  }

  public ZoomLevel(int zoomLevel, int minScale, int maxScale) {
    this.zoomLevel = zoomLevel;
    this.minScale = minScale;
    this.maxScale = maxScale;
  }

  /**
   * The zoom level number
   */
  public int getZoomLevel() {
    return zoomLevel;
  }

  public void setZoomLevel(int zoomLevel) {
    this.zoomLevel = zoomLevel;
  }

  /**
   * The minimum scale of when the layer should be visible
   */
  public int getMinScale() {
    return minScale;
  }

  public void setMinScale(int minScale) {
    this.minScale = minScale;
  }

  /**
   * The maximum scale of when the layer should be visible
   */
  public int getMaxScale() {
    return maxScale;
  }

  public void setMaxScale(int maxScale) {
    this.maxScale = maxScale;
  }
}
