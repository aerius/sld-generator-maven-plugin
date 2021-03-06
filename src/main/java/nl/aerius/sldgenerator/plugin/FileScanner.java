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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanner {

  /**
   * Returns all files in a certain path, with a certain extension as a list
   * @param searchPath path to search for files
   * @param fileExtension extension used to filter files
   * @return files as list
   */
  public static List<File> findFilesWithExtension(final File searchPath, final String fileExtension) throws IOException {
    try (Stream<Path> walk = Files.walk(searchPath.toPath())) {
      return walk
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith(fileExtension))
          .map(Path::toFile)
          .collect(Collectors.toList());
    }
  }
}
