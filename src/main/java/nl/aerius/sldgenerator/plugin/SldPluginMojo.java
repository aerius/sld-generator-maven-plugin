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

import static nl.aerius.sldgenerator.plugin.SldPlugin.SOURCE_EXTENSION;
import static nl.aerius.sldgenerator.plugin.SldPlugin.TARGET_EXTENSION;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.fasterxml.jackson.core.JacksonException;

@Mojo(name = "generate-sld", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class SldPluginMojo extends AbstractMojo {

  @Parameter private File sourcePath;

  @Parameter private File targetPath;

  @Parameter private final String targetPostfix = "";

  @Override
  public void execute() throws MojoExecutionException {

    try {
      for (final File file : FileScanner.findFilesWithExtension(sourcePath, SOURCE_EXTENSION)) {

        // Source file
        final Path sourceFilePath = Paths.get(file.getAbsolutePath());

        // Target file
        String targetFileName = file.getName();
        targetFileName = targetFileName.substring(0, targetFileName.length() - SOURCE_EXTENSION.length());
        targetFileName = targetFileName + targetPostfix + TARGET_EXTENSION;
        final Path targetFilePath = Paths.get(targetPath.getAbsolutePath()).resolve(targetFileName);

        try {

          // Generate SLD and write to target file
          final FileOutputStream outputStream = new FileOutputStream(targetFilePath.toFile());
          SldPlugin.generateSld(getLog(), sourceFilePath, outputStream);
          outputStream.close();

        } catch (final JacksonException e) {
          throw new MojoExecutionException("JSON Parse Error at (" + sourceFilePath + ") while generating SLDs", e);
        }
      }

    } catch (final IOException e) {
      throw new MojoExecutionException("IO Error while generating SLDs", e);
    }
  }
}
