package com.yumu.eventsapiserv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Reference: https://github.com/basanta-spring-boot/spring-mongo-gridFsTemplate
 *
 */

@Configuration
public class GridFsConfig extends AbstractMongoConfiguration {


	@Bean
	public GridFsTemplate gridFsTemplate() throws Exception {
		return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
	}

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return "yumumongodb02";
	}

	@Override
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		return new MongoClient("127.0.0.1");

	}

	public Mongo mongo() throws Exception {
		return new MongoClient("127.0.0.1");
	}

}
