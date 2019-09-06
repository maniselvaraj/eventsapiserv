package com.yumu.eventsapiserv.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.activities.Post;
import com.yumu.eventsapiserv.pojos.activities.PostDetail;
import com.yumu.eventsapiserv.pojos.activities.PostUserAction;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.PostRepository;
import com.yumu.eventsapiserv.repositories.PostUserActionRepository;
import com.yumu.eventsapiserv.repositories.UserRepository;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;



@Component
public class PostsManager {

	@Autowired
	private PostUserActionRepository postActionRepo;

	@Autowired
	private PostRepository postsRepo;

	@Autowired
	private UserRepository userRepo;
	
	public Post getPostById(String id){
		return this.postsRepo.findById(id).get();
	}

	public ResponseEntity<?> getPostsInActivity(String activityId, String status, Pageable page) {

			
		Page<Post> posts = null;
		if( StringUtils.equalsIgnoreCase(status, "reported") || StringUtils.equalsIgnoreCase(status, "abusive")){
			posts = postsRepo.findByActivityIdAndAbusivePostsAndActive(activityId, 0, 
					Post.Status.ACTIVE.name(), page);
		} else {
			//posts = postsRepo.findByActivityIdAndStatusOrderByCreatedAtDesc(activityId, status, page);
			posts = postsRepo.findByActivityIdAndStatus(activityId, status, page);
		}
		
		if(posts==null || posts.getSize()==0){
			return new ResponseEntity<>(posts, HttpStatus.OK);
		}
		
		
		List<String> postsIds = new ArrayList<>();
		posts.forEach(post -> postsIds.add(post.getId()));

		/*
		 * Why use a stringbuffer instead of string?
		 * Locally defined String cannot be used inside a lambda.
		 * Lambda can just reference it instead of copying a string.
		 */
		StringBuffer sLoggedInUser = new StringBuffer();
		
		MultiValuedMap<String, PostUserAction> postActionMultiMap = new ArrayListValuedHashMap<>();
		
		Authentication auth = UserAuthenticationUtils.getAuthenticatedUserNoException();
		if(auth!=null){
			UserDetails principal = (UserDetails) auth.getPrincipal();
			sLoggedInUser.append(principal.getUsername());

			List<PostUserAction> userActions = this.postActionRepo.findByPostIdIn(postsIds);

			/*
			 * If the logged in user has done any action in these posts,
			 */
			userActions.forEach(action -> {
				if(action.getYumuUserId().equals(sLoggedInUser.toString())) {
					postActionMultiMap.put(action.getPostId(), action);
				}
			});
		}

		List<PostDetail> postsDetail = new ArrayList<>();

		posts.forEach(post -> {
			PostDetail postDetail = new PostDetail();
			BeanUtils.copyProperties(post, postDetail);
			/*
			 * set the owner
			 */
			YumuUser owner = this.userRepo.findById(post.getOwner()).get();
			
			//TODO: Is it OK to send facebook information back in API?
			//owner.getSocialInfo().clear();
			//owner.setSocialInfo(null);
			
			postDetail.setOwnerInfo(owner);
			postDetail.setOwner(null);
			/*
			 * set the relation between logged in user and post
			 */
			if(StringUtils.isNotBlank(sLoggedInUser.toString())) {
				Collection<PostUserAction> action = postActionMultiMap.get(post.getId());
				if(action!=null /* && action.getYumuUserId().equals(sLoggedInUser.toString()) */) {
					postDetail.setPostRelation(action);
				}
			}
			postsDetail.add(postDetail);
		});

		Page<PostDetail> postsDetailPage = new PageImpl<>(postsDetail, page, posts.getTotalElements());

		return new ResponseEntity<>(postsDetailPage, HttpStatus.OK);
	}

	
	public PostDetail decoratePostWithDetail(Post post){
		
		PostDetail postDetail = new PostDetail();
		BeanUtils.copyProperties(post, postDetail);
		/*
		 * set the owner
		 */
		YumuUser owner = this.userRepo.findById(post.getOwner()).get();
		//owner.getSocialInfo().clear();
		//owner.setSocialInfo(null);
		postDetail.setOwnerInfo(owner);
		postDetail.setOwner(null);
		/*
		 * set the relation between logged in user and post
		 */
		Authentication auth = UserAuthenticationUtils.getAuthenticatedUserNoException();
		if(auth!=null){
			UserDetails principal = (UserDetails) auth.getPrincipal();
			Collection<PostUserAction> actions = postActionRepo.findByPostIdAndYumuUserId(post.getId(), principal.getUsername());
			postDetail.setPostRelation(actions);
			
		}
		return postDetail;
	}
	

}
