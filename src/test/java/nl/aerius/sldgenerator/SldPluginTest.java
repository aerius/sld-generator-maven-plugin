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
package nl.aerius.sldgenerator;


import nl.aerius.sldgenerator.plugin.SldPlugin;
import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.logging.LoggerManager;
import org.codehaus.plexus.logging.console.ConsoleLoggerManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SldPluginTest {

  private static final LoggerManager logger = new ConsoleLoggerManager();

  @ParameterizedTest
  @ValueSource(strings = {"simple.sld", "simple_with_zoomlevels.sld", "image.sld", "custom_draw.sld"})
  void testAgainstReferenceFile(final String fileName) throws URISyntaxException, IOException {

    final Log mavenLog = new DefaultLog(logger.getLoggerForComponent(getClass().getName()));

    final Path sourceFilePath = Paths.get(getClass().getResource(fileName + ".json").toURI());
    final InputStream referenceFile = getClass().getResourceAsStream(fileName);
    String referenceOutput = new String(referenceFile.readAllBytes());

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    SldPlugin.generateSld(mavenLog, sourceFilePath, outputStream);
    final String generatedOutput = outputStream.toString(StandardCharsets.UTF_8);

    assertEquals(referenceOutput, generatedOutput);
  }

}