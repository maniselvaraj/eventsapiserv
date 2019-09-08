/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.filters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.UserRepository;


@Component
public class YumuUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<YumuUser> facebookUser = userRepo.findById(username);// findByFacebookUserId(username);
		if(facebookUser.isEmpty()){
			return null;
		}
		UserDetails userDetails = 
				new org.springframework.security.core.userdetails.User(username, username, AuthorityUtils.NO_AUTHORITIES);
		return userDetails;
	}

}
