package com.yumu.eventsapiserv.tasks;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class ActivityCreationNotification extends GCMNotificationTask implements Runnable {

	private final static Logger logger = LogManager.getLogger(ActivityCreationNotification.class);

	
	private final String activityId;
	public ActivityCreationNotification(String key, String regId, String title, String body, String actId) {
		super(key, regId, title, body);
		this.activityId = actId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.logger.debug("activity created sent: " + body);

		Sender sender = new Sender(this.apiKey);
//		Notification value = new Notification.Builder("TiTlE").body("BoDy").build();
//		Message message = new Message.Builder().notification(value)
//				.addData("message", body)
//				.addData("title", title)
//				.build();
		Message message = new Message.Builder()
				.addData("message", body)
				.addData("title", title)
				.addData("activity_id", this.activityId)
				.build();

		try {
			Result result = sender.send(message, this.registrationId, 1);
			//System.out.println("gcm result success:" + result.getSuccess() + " failure:"+ result.getFailure() + " fail msg: " + result.getErrorCodeName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("activity creation notification sent");

	}
}
