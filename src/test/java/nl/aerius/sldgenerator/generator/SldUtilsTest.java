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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import nl.aerius.sldgenerator.input.SldRule;
import nl.aerius.sldgenerator.input.ZoomLevel;

/**
 *
 */
public class SldUtilsTest {

  private static final String USED_EXTENSION = ".xml";
  private static final String SIMPLE_SLD = "simple_sld";
  private static final String RULES_SLD = "rules_in_sld";
  private static final String RULES_ZOOMLVLS_SLD = "rules_and_zoomlevels_in_sld";
  private static final String INCORRECT_RULE_SLD = "incorrect_rule_sld";

  @Test
  public void testSimplestSLD() throws IOException {
    final SldFile layer = new SldFile("aeirus:someLayer");
    final String sldContent = SldUtils.generateSLD(layer);
    testContent(sldContent, SIMPLE_SLD);
  }

  @Test
  public void testSLDWithZoomLevels() throws IOException {
    //zoom levels have no effect without rules...
    final SldFile layer = new SldFile("aeirus:someLayer");
    addZoomLevels(layer);
    final String sldContent = SldUtils.generateSLD(layer);
    testContent(sldContent, SIMPLE_SLD);
  }

  @Test
  public void testSLDWithRules() throws IOException {
    final SldFile layer = new SldFile("aeirus:someLayer");
    addRules(layer);
    final String sldContent = SldUtils.generateSLD(layer);
    testContent(sldContent, RULES_SLD);
  }

  @Test
  public void testSLDWithIncorrectRules() throws IOException {
    final SldFile layer = new SldFile("aeirus:someLayer");
    //the condition (2nd param) is being parsed. This one can't be parsed correctly
    //tbh there should be something done about the colors too... but ah well...
    final SldRule rule = new SldRule();
    rule.setCondition("someweirdcond");
    rule.setStrokeColor("nocolors");
    rule.setFillColor("reallynocolors");
    layer.getRules().add(rule);
    final String sldContent = SldUtils.generateSLD(layer);
    testContent(sldContent, INCORRECT_RULE_SLD);
  }

  @Test
  public void testSLDWithRulesAndZoomLevels() throws IOException {
    final SldFile layer = new SldFile("aeirus:someLayer");
    addRules(layer);
    addZoomLevels(layer);
    final String sldContent = SldUtils.generateSLD(layer);
    testContent(sldContent, RULES_ZOOMLVLS_SLD);
  }

  private void testContent(final String generatedContent, final String correspondingFile) throws IOException {
    assertNotNull(generatedContent, "Content shouldn't be null");
    assertEquals(getFileContent(correspondingFile), generatedContent, "Content");
  }

  private void addZoomLevels(final SldFile layer) {
    layer.getZoomLevels().add(new ZoomLevel(1, 100, 200));
    layer.getZoomLevels().add(new ZoomLevel(2, 200, 1000));
    layer.getZoomLevels().add(new ZoomLevel(3, 1000, 10000));
  }

  private void addRules(final SldFile layer) {
    final SldRule imageRule = new SldRule();
    imageRule.setImageUrl("someImg.png");
    layer.getRules().add(imageRule);
    final SldRule customRule = new SldRule();
    customRule.setCustomDrawSld(
        Collections.singletonList(
            "<sld:LineSymbolizer><sld:Stroke><sld:CssParameter name=\"stroke\">#001122</sld:CssParameter></sld:Stroke></sld:LineSymbolizer>"));
    customRule.setCustomConditionSld(
        Collections.singletonList("<ogc:PropertyIsNull><ogc:PropertyName>someProperty</ogc:PropertyName></ogc:PropertyIsNull>"));
    layer.getRules().add(customRule);
    final SldRule polygonRule1 = new SldRule();
    polygonRule1.setCondition("something <= 4");
    polygonRule1.setStrokeColor("654321");
    polygonRule1.setFillColor("123456");
    layer.getRules().add(polygonRule1);
    final SldRule polygonRule2 = new SldRule();
    polygonRule2.setCondition("somethingElse = 8");
    polygonRule2.setStrokeColor("FFFFFF");
    polygonRule2.setFillColor("000000");
    layer.getRules().add(polygonRule2);
  }

  private String getFileContent(final String fileName) throws IOException {
    final File file = new File(getClass().getResource(fileName + USED_EXTENSION).getFile());
    final StringBuilder result = new StringBuilder();
    try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;

      while ((line = br.readLine()) != null) {
        result.append(line);
      }
    }
    return result.toString();
  }

}
