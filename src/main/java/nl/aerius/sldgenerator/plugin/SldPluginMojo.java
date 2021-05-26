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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static nl.aerius.sldgenerator.plugin.SldPlugin.SOURCE_EXTENSION;
import static nl.aerius.sldgenerator.plugin.SldPlugin.TARGET_EXTENSION;

@Mojo(name = "generate-sld", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class SldPluginMojo extends AbstractMojo {

  @Parameter
  private File sourcePath;

  @Parameter
  private File targetPath;

  @Parameter
  private String targetPostfix = "";

  public void execute() throws MojoExecutionException {
    FileScanner.findFilesWithExtension(sourcePath, SOURCE_EXTENSION).forEach(s -> {

      // Source file
      final Path sourceFilePath = Paths.get(s.getAbsolutePath());

      // Target file
      String targetFileName = s.getName();
      targetFileName = targetFileName.substring(0, targetFileName.length() - SOURCE_EXTENSION.length());
      targetFileName = targetFileName + targetPostfix + TARGET_EXTENSION;
      final Path targetFilePath = Paths.get(targetPath.getAbsolutePath()).resolve(targetFileName);

      try {

        // Generate SLD and write to target file
        final FileOutputStream outputStream = new FileOutputStream(targetFilePath.toFile());
        SldPlugin.generateSld(getLog(), sourceFilePath, outputStream);
        outputStream.close();
      } catch (IOException e) {
        getLog().error(e);
      }

    });
  }
}