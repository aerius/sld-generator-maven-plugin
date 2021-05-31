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

import nl.aerius.sldgenerator.input.SldRule;
import nl.aerius.sldgenerator.input.ZoomLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the input required to generate an .sld file
 */
public class SldFile {

  private final String sldName;
  private final List<SldRule> sldRules;
  private final List<ZoomLevel> zoomLevels;

  public SldFile(final String sldName) {
    this.sldName = sldName;
    this.sldRules = new ArrayList<>();
    this.zoomLevels = new ArrayList<>();
  }

  public SldFile(final String sldName, final List<SldRule> sldRules, final List<ZoomLevel> zoomLevels) {
    this.sldName = sldName;
    this.sldRules = sldRules;
    this.zoomLevels = zoomLevels;
  }

  /**
   * Name of the sld file
   */
  public String getSldName() {
    return sldName;
  }

  /**
   * Zoom levels used in this style file
   */
  public List<ZoomLevel> getZoomLevels() {
    return zoomLevels;
  }

  /**
   * Rules used in this style file
   */
  public List<SldRule> getRules() {
    return sldRules;
  }
}
