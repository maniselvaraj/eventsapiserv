package com.yumu.eventsapiserv.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.managers.FriendshipManager;
import com.yumu.eventsapiserv.pojos.user.RegistrationInfo;
import com.yumu.eventsapiserv.repositories.RegistrationRepository;
import com.yumu.eventsapiserv.utils.PropertiesUtil;




@Component
public class TaskGenerator {


	@Autowired
	private PropertiesUtil propsUtil;
	
	@Autowired
	private RegistrationRepository regRepo;

	@Autowired
	private FriendshipManager friendManager;
	
	@Autowired
	private YumuTaskExecutor executor;

	
	private final static Logger logger = LogManager.getLogger(TaskGenerator.class);

	
	public void generateFriendshipNotification(String receiverId, String body){
		
		String apiKey = this.propsUtil.getString("gcm_server_key");
		
		List<RegistrationInfo> rInfos = this.regRepo.findByYumuUserIdAndStatus(receiverId, 
				RegistrationInfo.Status.LOGGED_IN.name(),
				new Sort(Sort.Direction.DESC, "updatedAt"));
		
		/*
		 * hack to avoid duplicate gcm notifications. Get only the latest device token and send
		 * GCM. If user is logged in from multiple devices, he will get notification in one device only
		 */
		if(rInfos!=null && rInfos.size()>0){
			this.executor.addTask(new FriendRequestNotification(apiKey, 
					rInfos.get(0).getToken(), 
					"Yumu friendship request", 
					body) );
		}
		
//		rInfos.forEach(rInfo -> {
//			this.executor.addTask(new FriendRequestNotification(apiKey, 
//					rInfo.getToken(), 
//					"Yumu friendship request", 
//					body) );
//		});

	}

	public void generateMembershipInvitationNotification(String activityId, String receiverId, String body){
		
		String apiKey = this.propsUtil.getString("gcm_server_key");
		
		List<RegistrationInfo> rInfos = this.regRepo.findByYumuUserIdAndStatus(receiverId, 
				RegistrationInfo.Status.LOGGED_IN.name(),
				new Sort(Sort.Direction.DESC, "updatedAt"));

		/*
		 * hack to avoid duplicate gcm notifications. Get only the latest device token and send
		 * GCM. If user is logged in from multiple devices, he will get notification in one device only
		 */

		if(rInfos!=null && rInfos.size()>0){
			this.executor.addTask(new MembershipInvitationNotification(apiKey, 
					rInfos.get(0).getToken(), 
					"Yumu member invite", 
					body,
					activityId));
		}
		
//		rInfos.forEach(rInfo -> {
//			this.executor.addTask(new MembershipInvitationNotification(apiKey, 
//					rInfo.getToken(), 
//					"Yumu member invite", 
//					body,
//					activityId));
//		});

	}
	
	public void generateMembershipRequestNotification(String activityId, String receiverId, String body){
		
		String apiKey = this.propsUtil.getString("gcm_server_key");
		
		
		List<RegistrationInfo> rInfos = this.regRepo.findByYumuUserIdAndStatus(receiverId, 
				RegistrationInfo.Status.LOGGED_IN.name(), 
				new Sort(Sort.Direction.DESC, "updatedAt"));

		/*
		 * hack to avoid duplicate gcm notifications. Get only the latest device token and send
		 * GCM. If user is logged in from multiple devices, he will get notification in one device only
		 */

		if(rInfos!=null && rInfos.size()>0){
			this.executor.addTask(new MembershipRequestNotification(apiKey, 
					rInfos.get(0).getToken(), 
					"Yumu member request", 
					body,
					activityId));
		}
//		rInfos.forEach(rInfo -> {
//			this.executor.addTask(new MembershipRequestNotification(apiKey, 
//					rInfo.getToken(), 
//					"Yumu member request", 
//					body,
//					activityId));
//		});

	}

	public void generateActivityCreationNotification(String activityId, String activityCreatorId, String body) {
		
		String apiKey = this.propsUtil.getString("gcm_server_key");
		
		/*
		 * get list of friend's and send them a notification that your friend created a new activity
		 */
		
		List<RegistrationInfo> devices = this.friendManager.getFriendsDeviceRegId(activityCreatorId);
		
		/*
		 * filter duplicates. keep only the latest per user
		 */
		Map<String, RegistrationInfo> filtered = new HashMap<>();
		
		devices.forEach(dev -> {
			RegistrationInfo current = filtered.get(dev.getYumuUserId());
			if(current == null){
				filtered.put(dev.getYumuUserId(), dev);
			} else {
				
				if(current.getUpdatedAt()!=null && dev.getUpdatedAt()!=null) {
					if(current.getUpdatedAt().isBefore(dev.getUpdatedAt())){
						filtered.put(dev.getYumuUserId(), dev);
					}
				}
			}
		});
		
		filtered.forEach((k,v)-> {

			this.logger.debug("GCM ACT creation from usr: " + activityCreatorId + " for device " + v.getToken());
			
			this.executor.addTask(new ActivityCreationNotification(apiKey, 
					v.getToken(), 
					"Yumu activity created", 
					body,
					activityId));

		});
		
//		devices.forEach(device -> {
//			
//			this.logger.debug("GCM ACT creation from usr: " + activityCreatorId + " for device " + device.getToken());
//			
//			this.executor.addTask(new ActivityCreationNotification(apiKey, 
//					device.getToken(), 
//					"Yumu activity created", 
//					body,
//					activityId));
//
//		});
		
	}
	
}
