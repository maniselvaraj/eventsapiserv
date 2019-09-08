/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.controllers.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.controllers.*;
import com.yumu.eventsapiserv.pojos.activities.*;
import com.yumu.eventsapiserv.utils.PropertiesUtil;

@RestController
@RequestMapping(path = Api.BASE_VERSION +  "/images/")
public class StockImageControllerImpl {



	@Autowired
	private PropertiesUtil propsUtil;

	@RequestMapping(method=RequestMethod.GET, path="activities/stock")
	public ResponseEntity<?> getActivitiesStockImageList(){

		String baseDomain = propsUtil.getString("yumu.api.endpoint.domain");

		String basePath = propsUtil.getString("activity.stock.images.base.path");

		List<Object> stockImages = new ArrayList<>();

		Activity.Type[] types = Activity.Type.values();

		for(Activity.Type type: types){

			Map<String, Object> images  = new HashMap<>();

			images.put("type", type.name());

			List<String> files = propsUtil.getStringList("activity.stock.images."+type.name());

			List<String> results = new ArrayList<>();
			files.forEach(f -> {
				String url = baseDomain + basePath + type.name() + "/" + f;
				results.add(url);
			});

			images.put("files", results);

			stockImages.add(images);
		}

		return new ResponseEntity<>(stockImages, HttpStatus.OK);

	}


	@RequestMapping(method=RequestMethod.GET, path="avatars/stock")
	public ResponseEntity<?> getAvatarStockImageList(){

		String baseDomain = propsUtil.getString("yumu.api.endpoint.domain");

		String basePath = propsUtil.getString("user.avatars.images.base.path");

		List<String> files = propsUtil.getStringList("user.avatars.images.DEFAULT");
		List<String> results = new ArrayList<>();
		files.forEach(f -> {
			String url = baseDomain + basePath + f;
			results.add(url);
		});

		return new ResponseEntity<>(results, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, path="userbackground/stock")
	public ResponseEntity<?> getUserBackgroundImageList(){

		String baseDomain = propsUtil.getString("yumu.api.endpoint.domain");

		String basePath = propsUtil.getString("user.background.images.base.path");

		List<String> files = propsUtil.getStringList("user.background.images.DEFAULT");
		List<String> results = new ArrayList<>();
		files.forEach(f -> {
			String url = baseDomain + basePath + f;
			results.add(url);
		});

		return new ResponseEntity<>(results, HttpStatus.OK);
	}

}
