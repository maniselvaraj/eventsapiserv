package com.yumu.eventsapiserv.tasks;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class FriendRequestNotification extends GCMNotificationTask implements Runnable {

	public FriendRequestNotification(String apiK, String regId, String t, String b) {
		super(apiK, regId, t, b);
	}

	@Override
	public void run() {
		System.out.println("Friend request sent");
		
		Sender sender = new Sender(this.apiKey);
		//Notification value = new Notification.Builder(title).body(body).build();
		//Message message = new Message.Builder().notification(value).build();
		Message message = new Message.Builder()
				.addData("message", body)
				.addData("title", title)
				.build();
		try {
			Result result = sender.send(message, this.registrationId, 1);
			//System.out.println("gcm result success:" + result.getSuccess() + " failure:"+ result.getFailure() + " fail msg: " + result.getErrorCodeName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
