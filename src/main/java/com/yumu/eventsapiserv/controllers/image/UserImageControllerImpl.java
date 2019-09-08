package com.yumu.eventsapiserv.controllers.image;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.yumu.eventsapiserv.controllers.*;
import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.managers.ImageManager;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;

@RestController
@RequestMapping(path = Api.BASE_VERSION + "/images")
public class UserImageControllerImpl {


	@Autowired
	private ImageManager imageManager;

	
	@RequestMapping( headers=("content-type=multipart/*"),  method=RequestMethod.POST)
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {

		try {
			
			String userId = UserAuthenticationUtils.getAuthenticatedUserId();
			
			List<?> images  = this.imageManager.processUploadedImage(file, userId);
			
			return new ResponseEntity<>(images, HttpStatus.OK);
		}catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}

	@ResponseBody
	@RequestMapping(method=RequestMethod.GET, path="/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getImage( @PathVariable("imageId") String imageId) throws IOException{

		return this.imageManager.getImageById(imageId);

	}


}
