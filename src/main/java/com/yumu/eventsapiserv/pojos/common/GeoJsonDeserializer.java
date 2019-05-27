package com.yumu.eventsapiserv.pojos.common;

import java.io.IOException;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * Reference:
 * http://stackoverflow.com/questions/31533390/no-suitable-constructor-found-for-type-geojsonpoint/37340077#37340077
 * https://github.com/spring-projects/spring-hateoas/issues/262
 * http://stackoverflow.com/questions/31533390/no-suitable-constructor-found-for-type-geojsonpoint/37340077#37340077
 * @author mani
 *
 */
public class GeoJsonDeserializer extends JsonDeserializer<GeoJsonPoint> {
	private final static String GEOJSON_TYPE_POINT = "Point";

    private final static String JSON_KEY_GEOJSON_TYPE = "type";
    private final static String JSON_KEY_GEOJSON_COORDS = "coordinates";


    public GeoJsonPoint deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        final JsonNode tree = jp.getCodec().readTree(jp);
        //final String type = tree.get(JSON_KEY_GEOJSON_TYPE).asText();
        //final JsonNode coordsNode = tree.get(JSON_KEY_GEOJSON_COORDS);

        double x = tree.get("x").asDouble();
        double y = tree.get("y").asDouble();
//        if(GEOJSON_TYPE_POINT.equalsIgnoreCase(type)) {
//            x = coordsNode.get(0).asDouble();
//            y = coordsNode.get(1).asDouble();
//        }
//
//        else {
//            System.out.println(String.format("No logic present to deserialize %s ", tree.asText()));
//        }

        final GeoJsonPoint point = new GeoJsonPoint(x, y);

        return point;
    }
}
