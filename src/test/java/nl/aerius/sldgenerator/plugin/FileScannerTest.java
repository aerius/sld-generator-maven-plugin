package nl.aerius.sldgenerator.plugin;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileScannerTest {

  @Test
  void findFilesWithExtension() throws URISyntaxException, IOException {
    final List<File> filesInFolder = FileScanner.findFilesWithExtension(
        new File(getClass().getResource(".").toURI()), ".sld.json");
    assertEquals(4, filesInFolder.size(), "Should be 4 files in the folder.");
  }
}