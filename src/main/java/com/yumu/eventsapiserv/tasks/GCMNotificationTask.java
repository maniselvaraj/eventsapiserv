package com.yumu.eventsapiserv.tasks;

public abstract class GCMNotificationTask  {


	protected final String apiKey;
	protected final String registrationId;
	protected final String title;
	protected final String body;
	
	public GCMNotificationTask( String key, String regId, String title, String body) {
		// TODO Auto-generated constructor stub
		this.apiKey = key;
		this.registrationId = regId;
		this.title = title;
		this.body = body;
	}

}
