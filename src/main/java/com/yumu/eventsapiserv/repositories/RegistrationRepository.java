package com.yumu.eventsapiserv.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.user.RegistrationInfo;


public interface RegistrationRepository extends MongoRepository<RegistrationInfo, String>{

	RegistrationInfo findByYumuUserIdAndToken(String userId, String token);
	
	List<RegistrationInfo> findByYumuUserIdAndStatus(String userId, String status, Sort s);
	
	List<RegistrationInfo> findByYumuUserIdInAndStatus(Collection<String> ids, String status);


}
