# SLD Generator Maven Plugin

## About

Geoserver uses `.sld` files as stylesheets for the layers. These stylesheets specify which features are visible and with which style. These stylesheets are large `.xml` files, and their syntax is cumbersome.

This plugin generates these `.sld` files from a simple `.sld.json` definition. These definitions contain the rules that the `.sld` file should contain.

## Usage

### Install Maven Plugin

Add the following plugin and (adjusted) configuration to the `pom.xml` file.
```xml
<plugin>
  <groupId>nl.aerius</groupId>
  <artifactId>sld-generator-maven-plugin</artifactId>
  <version>{sld-generator.version}</version>
  <configuration>
    <sourcePath>src/main/sld-rivm</sourcePath>
    <targetPath>src/main/webapp/data/workspaces/calculator/styles</targetPath>
    <targetPostfix>_generated</targetPostfix>
  </configuration>
  <executions>
    <execution>
      <goals>
        <goal>generate-sld</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```
__Configuration__
* `sourcePath` path to the folder where `.sld.json`-files are located. The folder is searched recursively for these files.
* `targetPath` path to the folder where `.sld` files should be written to. Typical the `styles`-folder within the Geoserver workspace.
* `targetPostfix` _Optional_ postfix which will be added to the `.sld` file names.


### Example `.sld.json` file

```json
{
  "zoomLevelsFile": "zoom_levels_default.json",
  "rules": [
    {
      "condition": "deposition > 1900 && deposition <= 2200",
      "fillColor": "CC4C02",
      "strokeColor": "FFFFFF"
    },
    {
      "condition": "deposition > 2200",
      "fillColor": "8C2D04",
      "strokeColor": "FFFFFF"
    }
  ]
}
```

* `zoomLevelsFile` refers to the zoom level definition file. The zoom level definition is separate because it can be shared between styles. If no zoom level file is defined, no filtering takes place on zoom level.
* `rules` array containing the rules
  * `condition` contains the boolean condition to render this style to a feature
  * `fillColor` color to fill the feature (hexadecimal)
  * `strokeColor` color to outline the feature (hexadecimal)
  * `imageUrl` url to an image to draw at the center of the feature. The url is relative to the final `.sld` file, can be a local file in geoserver, or an external image (specify full url)
  * `customDrawSld` custom XML used in the draw part for this rule in the `.sld` file (for advanced options which can not be specified using above properties)
  * `customConditionSld` custom XML used in the condition part for this rule in the `.sld` file (for advanced options which can not be specified using above properties)

### Example zoom level definition

```json
[
  {
    "zoomLevel": 1,
    "minScale": 0,
    "maxScale": 30000
  },
  {
    "zoomLevel": 2,
    "minScale": 30000,
    "maxScale": 60000
  },
  {
    "zoomLevel": 3,
    "minScale": 60000,
    "maxScale": 150000
  },
  {
    "zoomLevel": 4,
    "minScale": 150000,
    "maxScale": 300000
  },
  {
    "zoomLevel": 5,
    "minScale": 300000,
    "maxScale": 800000
  }
]
```

Above zoom level definition makes sure that features with zoom level 1 are only rendered below scale 30000, features with zoom level 2 are rendered between scale 30000 and 60000 and so on. These are currently the default zoom levels and their scales in AERIUS.

## License

Copyright [the State of the Netherlands](https://www.government.nl)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see http://www.gnu.org/licenses/.