package com.yumu.eventsapiserv.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.system.AppLog;


/*
 * Repo for storing AppLog in capped collection
 */
public interface AppLogRepository  extends MongoRepository<AppLog, String> {

}
