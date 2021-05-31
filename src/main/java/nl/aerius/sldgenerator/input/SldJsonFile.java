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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the JSON content in an .sld.json file
 */
public class SldJsonFile {

  private String zoomLevelsFile;
  private List<SldRule> sldRules = new ArrayList<>();

  /**
   * Provides a relative path to a zoom level definition file
   */
  public String getZoomLevelsFile() {
    return zoomLevelsFile;
  }

  public void setZoomLevelsFile(String zoomLevelsFile) {
    this.zoomLevelsFile = zoomLevelsFile;
  }

  /**
   * Returns the rules in the .sld.json file
   */
  public List<SldRule> getRules() {
    return sldRules;
  }

  public void setRules(List<SldRule> sldRules) {
    this.sldRules = sldRules;
  }
}
