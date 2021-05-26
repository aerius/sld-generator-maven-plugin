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
package nl.aerius.sldgenerator.plugin;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.aerius.sldgenerator.input.SldJsonFile;
import nl.aerius.sldgenerator.input.ZoomLevel;
import nl.aerius.sldgenerator.input.ZoomLevelsJsonFile;
import nl.aerius.sldgenerator.generator.SldFile;
import nl.aerius.sldgenerator.generator.SldUtils;
import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SldPlugin {

  public static String SOURCE_EXTENSION = ".sld.json";
  public static String TARGET_EXTENSION = ".sld";

  public static void generateSld(final Log log, final Path sourceFilePath, final OutputStream targetFile) throws IOException {

    try {
      final ObjectMapper objectMapper = new ObjectMapper();
      SldJsonFile sldJsonFile = objectMapper.readValue(sourceFilePath.toFile(), SldJsonFile.class);

      // If a zoom level file is specified, read and parse this file
      List<ZoomLevel> zoomLevels = new ArrayList<>();
      if (sldJsonFile.getZoomLevelsFile() != null) {
        final Path zoomLevelsFilePath = sourceFilePath.getParent().resolve(sldJsonFile.getZoomLevelsFile());
        zoomLevels = objectMapper.readValue(zoomLevelsFilePath.toFile(), ZoomLevelsJsonFile.class);
      }

      String sldFileName = sourceFilePath.toFile().getName();
      SldFile sldFile = new SldFile(sldFileName.substring(0, sldFileName.length() - SOURCE_EXTENSION.length()), sldJsonFile.getRules(), zoomLevels);

      String generatedSld = SldUtils.generateSLD(sldFile);
      targetFile.write(generatedSld.getBytes(StandardCharsets.UTF_8));

    } catch (JacksonException e) {
      log.error("JSON Parse Error in SLD Generation");
      log.error(e);
    } catch (IOException e) {
      log.error("IO Error in SLD Generation");
      log.error(e);
    }
  }

}
