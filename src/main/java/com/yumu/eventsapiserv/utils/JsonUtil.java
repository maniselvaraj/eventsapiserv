package com.yumu.eventsapiserv.utils;

import java.io.IOException;

import org.springframework.data.geo.GeoModule;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	public static String toString(Object jsonObject){
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		
		mapper.registerModule(new GeoJsonModule());
		mapper.registerModule(new GeoModule());
		
		String json = "";
		
		try {
			json = mapper.writeValueAsString(jsonObject);
		} catch(IOException e){
			System.out.println("MDEBUG json to string failed");
		}
		return json;
	}
	
	public static Object  toObject(String json, String className) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException {
		
		Class<?> classs = Class.forName(className);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GeoJsonModule());
		//mapper.registerModule(new GeoModule());
		
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		
		return mapper.readValue(json, classs);
	}
	
}
