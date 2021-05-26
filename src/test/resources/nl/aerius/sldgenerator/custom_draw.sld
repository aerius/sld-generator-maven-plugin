<?xml version="1.0" encoding="UTF-8"?><sld:StyledLayerDescriptor xmlns:sld="http://www.opengis.net/sld" xmlns:java="java" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:wfs="http://www.opengis.net/wfs" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:gml="http://www.opengis.net/gml" xmlns:ogc="http://www.opengis.net/ogc" xmlns="http://www.opengis.net/sld" version="1.0.0" xsi:schemaLocation="http://www.opengis.net/sld http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd"><sld:NamedLayer><sld:Name>custom_draw</sld:Name><sld:UserStyle><sld:Name>custom_draw</sld:Name><sld:Title>custom_draw</sld:Title><sld:IsDefault>1</sld:IsDefault><sld:FeatureTypeStyle><sld:Rule><sld:Name>bird_directive = false &amp;&amp; habitat_directive = false</sld:Name><sld:Title>bird_directive = false &amp;&amp; habitat_directive = false</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsEqualTo><ogc:PropertyName>bird_directive</ogc:PropertyName><ogc:Literal>false</ogc:Literal></ogc:PropertyIsEqualTo><ogc:PropertyIsEqualTo><ogc:PropertyName>habitat_directive</ogc:PropertyName><ogc:Literal>false</ogc:Literal></ogc:PropertyIsEqualTo></ogc:And></ogc:Filter><sld:PolygonSymbolizer>
  <sld:Fill>
    <sld:CssParameter name="fill">#99CDBD</sld:CssParameter>
    <sld:CssParameter name="fill-opacity">1</sld:CssParameter>
  </sld:Fill>
  <sld:Stroke />
</sld:PolygonSymbolizer>
<sld:TextSymbolizer>
  <sld:Label>
    <ogc:PropertyName>name</ogc:PropertyName>
  </sld:Label>
  <sld:Font>
    <sld:CssParameter name="font-family">RijksoverheidSansText-Bold</sld:CssParameter>
    <sld:CssParameter name="font-size">13</sld:CssParameter>
    <sld:CssParameter name="font-style">normal</sld:CssParameter>
    <sld:CssParameter name="font-weight">bold</sld:CssParameter>
  </sld:Font>
  <sld:LabelPlacement>
    <sld:PointPlacement>
      <sld:AnchorPoint>
        <sld:AnchorPointX>0.5</sld:AnchorPointX>
        <sld:AnchorPointY>0.5</sld:AnchorPointY>
      </sld:AnchorPoint>
    </sld:PointPlacement>
  </sld:LabelPlacement>
  <sld:VendorOption name="autoWrap">60</sld:VendorOption>
  <sld:VendorOption name="maxDisplacement">150</sld:VendorOption>
</sld:TextSymbolizer></sld:Rule><sld:Rule><sld:Name>bird_directive = false &amp;&amp; habitat_directive = true</sld:Name><sld:Title>bird_directive = false &amp;&amp; habitat_directive = true</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsEqualTo><ogc:PropertyName>bird_directive</ogc:PropertyName><ogc:Literal>false</ogc:Literal></ogc:PropertyIsEqualTo><ogc:PropertyIsEqualTo><ogc:PropertyName>habitat_directive</ogc:PropertyName><ogc:Literal>true</ogc:Literal></ogc:PropertyIsEqualTo></ogc:And></ogc:Filter><sld:PolygonSymbolizer>
  <sld:Fill>
    <sld:CssParameter name="fill">#E6E600</sld:CssParameter>
    <sld:CssParameter name="fill-opacity">1</sld:CssParameter>
  </sld:Fill>
  <sld:Stroke />
</sld:PolygonSymbolizer>
<sld:TextSymbolizer>
  <sld:Label>
    <ogc:PropertyName>name</ogc:PropertyName>
  </sld:Label>
  <sld:Font>
    <sld:CssParameter name="font-family">RijksoverheidSansText-Bold</sld:CssParameter>
    <sld:CssParameter name="font-size">13</sld:CssParameter>
    <sld:CssParameter name="font-style">normal</sld:CssParameter>
    <sld:CssParameter name="font-weight">bold</sld:CssParameter>
  </sld:Font>
  <sld:LabelPlacement>
    <sld:PointPlacement>
      <sld:AnchorPoint>
        <sld:AnchorPointX>0.5</sld:AnchorPointX>
        <sld:AnchorPointY>0.5</sld:AnchorPointY>
      </sld:AnchorPoint>
    </sld:PointPlacement>
  </sld:LabelPlacement>
  <sld:VendorOption name="autoWrap">60</sld:VendorOption>
  <sld:VendorOption name="maxDisplacement">150</sld:VendorOption>
</sld:TextSymbolizer></sld:Rule><sld:Rule><sld:Name>bird_directive = true &amp;&amp; habitat_directive = false</sld:Name><sld:Title>bird_directive = true &amp;&amp; habitat_directive = false</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsEqualTo><ogc:PropertyName>bird_directive</ogc:PropertyName><ogc:Literal>true</ogc:Literal></ogc:PropertyIsEqualTo><ogc:PropertyIsEqualTo><ogc:PropertyName>habitat_directive</ogc:PropertyName><ogc:Literal>false</ogc:Literal></ogc:PropertyIsEqualTo></ogc:And></ogc:Filter><sld:PolygonSymbolizer>
  <sld:Fill>
    <sld:CssParameter name="fill">#BEE8FF</sld:CssParameter>
    <sld:CssParameter name="fill-opacity">1</sld:CssParameter>
  </sld:Fill>
  <sld:Stroke />
</sld:PolygonSymbolizer>
<sld:TextSymbolizer>
  <sld:Label>
    <ogc:PropertyName>name</ogc:PropertyName>
  </sld:Label>
  <sld:Font>
    <sld:CssParameter name="font-family">RijksoverheidSansText-Bold</sld:CssParameter>
    <sld:CssParameter name="font-size">13</sld:CssParameter>
    <sld:CssParameter name="font-style">normal</sld:CssParameter>
    <sld:CssParameter name="font-weight">bold</sld:CssParameter>
  </sld:Font>
  <sld:LabelPlacement>
    <sld:PointPlacement>
      <sld:AnchorPoint>
        <sld:AnchorPointX>0.5</sld:AnchorPointX>
        <sld:AnchorPointY>0.5</sld:AnchorPointY>
      </sld:AnchorPoint>
    </sld:PointPlacement>
  </sld:LabelPlacement>
  <sld:VendorOption name="autoWrap">60</sld:VendorOption>
  <sld:VendorOption name="maxDisplacement">150</sld:VendorOption>
</sld:TextSymbolizer></sld:Rule><sld:Rule><sld:Name>bird_directive = true &amp;&amp; habitat_directive = true</sld:Name><sld:Title>bird_directive = true &amp;&amp; habitat_directive = true</sld:Title><ogc:Filter><ogc:And><ogc:PropertyIsEqualTo><ogc:PropertyName>bird_directive</ogc:PropertyName><ogc:Literal>true</ogc:Literal></ogc:PropertyIsEqualTo><ogc:PropertyIsEqualTo><ogc:PropertyName>habitat_directive</ogc:PropertyName><ogc:Literal>true</ogc:Literal></ogc:PropertyIsEqualTo></ogc:And></ogc:Filter><sld:PolygonSymbolizer>
  <sld:Fill>
    <sld:CssParameter name="fill">#D1FF73</sld:CssParameter>
    <sld:CssParameter name="fill-opacity">1</sld:CssParameter>
  </sld:Fill>
  <sld:Stroke />
</sld:PolygonSymbolizer>
<sld:TextSymbolizer>
  <sld:Label>
    <ogc:PropertyName>name</ogc:PropertyName>
  </sld:Label>
  <sld:Font>
    <sld:CssParameter name="font-family">RijksoverheidSansText-Bold</sld:CssParameter>
    <sld:CssParameter name="font-size">13</sld:CssParameter>
    <sld:CssParameter name="font-style">normal</sld:CssParameter>
    <sld:CssParameter name="font-weight">bold</sld:CssParameter>
  </sld:Font>
  <sld:LabelPlacement>
    <sld:PointPlacement>
      <sld:AnchorPoint>
        <sld:AnchorPointX>0.5</sld:AnchorPointX>
        <sld:AnchorPointY>0.5</sld:AnchorPointY>
      </sld:AnchorPoint>
    </sld:PointPlacement>
  </sld:LabelPlacement>
  <sld:VendorOption name="autoWrap">60</sld:VendorOption>
  <sld:VendorOption name="maxDisplacement">150</sld:VendorOption>
</sld:TextSymbolizer></sld:Rule></sld:FeatureTypeStyle></sld:UserStyle></sld:NamedLayer></sld:StyledLayerDescriptor>