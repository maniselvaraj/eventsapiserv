package com.yumu.eventsapiserv.tasks;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class MembershipRequestNotification extends GCMNotificationTask implements Runnable {

	private final String activityId;
	public MembershipRequestNotification(String key, String regId, String title, String body, String actId) {
		super(key, regId, title, body);
		this.activityId = actId;

	}
	
	@Override
	public void run() {
		System.out.println("Membership request sent");

		Sender sender = new Sender(this.apiKey);
		
		//Notification value = new Notification.Builder(title).body(body).build();
		//Message message = new Message.Builder().notification(value).build();
		Message message = new Message.Builder()
				.addData("message", body)
				.addData("title", title)
				.addData("activity_id", this.activityId)
				.build();
		try {
			Result result = sender.send(message, this.registrationId, 1);
			//System.out.println("gcm send membership request result success:" + result.getSuccess() + " failure:"+ result.getFailure() + " fail msg: " + result.getErrorCodeName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
