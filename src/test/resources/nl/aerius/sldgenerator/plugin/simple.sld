<?xml version="1.0" encoding="UTF-8"?><sld:StyledLayerDescriptor xmlns:sld="http://www.opengis.net/sld" xmlns:java="java" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:wfs="http://www.opengis.net/wfs" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc" xmlns="http://www.opengis.net/sld" version="1.0.0" xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"><sld:NamedLayer><sld:Name>simple</sld:Name><sld:UserStyle><sld:Name>simple</sld:Name><sld:Title>simple</sld:Title><sld:IsDefault>1</sld:IsDefault><sld:FeatureTypeStyle><sld:Rule><sld:Name>bird_directive = false</sld:Name><sld:Title>bird_directive = false</sld:Title><ogc:Filter><ogc:PropertyIsEqualTo><ogc:PropertyName>bird_directive</ogc:PropertyName><ogc:Literal>false</ogc:Literal></ogc:PropertyIsEqualTo></ogc:Filter><sld:PolygonSymbolizer><sld:Fill><sld:CssParameter name="fill">#99CDBD</sld:CssParameter><sld:CssParameter name="fill-opacity">1</sld:CssParameter></sld:Fill><sld:Stroke><sld:CssParameter name="stroke">#000000</sld:CssParameter><sld:CssParameter name="stroke-opacity">1</sld:CssParameter><sld:CssParameter name="stroke-width">0.5</sld:CssParameter></sld:Stroke></sld:PolygonSymbolizer></sld:Rule><sld:Rule><sld:Name>bird_directive = true</sld:Name><sld:Title>bird_directive = true</sld:Title><ogc:Filter><ogc:PropertyIsEqualTo><ogc:PropertyName>bird_directive</ogc:PropertyName><ogc:Literal>true</ogc:Literal></ogc:PropertyIsEqualTo></ogc:Filter><sld:PolygonSymbolizer><sld:Fill><sld:CssParameter name="fill">#E6E600</sld:CssParameter><sld:CssParameter name="fill-opacity">1</sld:CssParameter></sld:Fill><sld:Stroke><sld:CssParameter name="stroke">#000000</sld:CssParameter><sld:CssParameter name="stroke-opacity">1</sld:CssParameter><sld:CssParameter name="stroke-width">0.5</sld:CssParameter></sld:Stroke></sld:PolygonSymbolizer></sld:Rule></sld:FeatureTypeStyle></sld:UserStyle></sld:NamedLayer></sld:StyledLayerDescriptor>