package com.yumu.eventsapiserv.managers;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.system.AppLog;
import com.yumu.eventsapiserv.pojos.system.AppLog.Type;
import com.yumu.eventsapiserv.repos.AppLogRepository;


@Component
public class AppLogManager {

	@Autowired
	private AppLogRepository logRepo;

	public void info(String msg){

		try {

			AppLog log = new AppLog();
			log.setType(Type.INFO);
			log.setMsg(msg);
			log.setCreatedAt(new DateTime(DateTimeZone.UTC));

			String actorId = UserAuthenticationUtils.getAuthenticatedUserIdNoException();
			if(StringUtils.isNotBlank(actorId)){
				log.setYumuUserId(actorId);
			}
			this.logRepo.save(log);
		}
		catch(Exception e){
			//TODO: remove this later
			e.printStackTrace();
		}
	}

}
