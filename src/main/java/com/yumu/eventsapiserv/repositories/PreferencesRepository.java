package com.yumu.eventsapiserv.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.user.preferences.Preferences;


public interface PreferencesRepository extends MongoRepository<Preferences, String>{

	Preferences findByYumuUserId(String userId);
}
