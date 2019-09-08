package com.yumu.eventsapiserv.controllers.image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import com.yumu.eventsapiserv.utils.HttpResponseUtil;


@ControllerAdvice
public class ImageUploadExceptionHandler {

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<?> handleError1(MultipartException e) {
		return HttpResponseUtil.getJsonMessage("MultipartException: File size exceeded the maximum 5Mb limit", 
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> handleError2(MaxUploadSizeExceededException e) {
		return HttpResponseUtil.getJsonMessage("MaxUploadSizeExceededException: File size exceeded the maximum 5Mb limit", 
				HttpStatus.BAD_REQUEST);
	}

}
