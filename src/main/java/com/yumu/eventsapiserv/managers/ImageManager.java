/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.managers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yumu.eventsapiserv.controllers.Api;
import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.pojos.common.UserImage;
import com.yumu.eventsapiserv.utils.PropertiesUtil;

/*
 * https://spring.io/guides/gs/uploading-files/

 */

@Component
public class ImageManager {

//	@Autowired
//	private GridFsOperations gridClient;

	@Autowired
	private GridFsOperations gridClient;
	
	@Autowired
	private PropertiesUtil propsUtil;

	@Deprecated
	public ObjectId storeImage(MultipartFile file,  String userId) throws ApiAccessException{

		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			throw new ApiAccessException(ErrorMessages.IMAGE_ERROR + e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		DBObject metaData = new BasicDBObject();
		metaData.put("owner", userId);

		ObjectId gfile = 
				this.gridClient.store(inputStream, "test.png", "image/png", metaData);

		return gfile;
	}

	//	public GridFSFile storeImage(BufferedImage file,  String userId, String diskLocation, String name) throws ApiAccessException{
	//
	//		InputStream inputStream = null;
	//		try {
	//			inputStream = file.getInputStream();
	//		} catch (IOException e) {
	//			throw new ApiAccessException(ErrorMessages.IMAGE_ERROR + e.getMessage(), 
	//					HttpStatus.INTERNAL_SERVER_ERROR);
	//		}
	//
	//		DBObject metaData = new BasicDBObject();
	//		metaData.put("owner", userId);
	//		metaData.put("src", diskLocation );
	//
	//		GridFSFile gfile = 
	//				this.gridClient.store(inputStream, name, "image/png", metaData);
	//
	//		return gfile;
	//	}

	public ObjectId storeImage(File file, String userId, String diskLocation, String name) throws ApiAccessException{

		InputStream inputStream = null;
		try {
			inputStream = FileUtils.openInputStream(file);
		} catch (IOException e) {
			throw new ApiAccessException(ErrorMessages.IMAGE_ERROR + e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		DBObject metaData = new BasicDBObject();
		metaData.put("owner", userId);
		metaData.put("src", diskLocation );

		ObjectId gfile = 
				this.gridClient.store(inputStream, name, "image/png", metaData);

		return gfile;
	}

	public ResponseEntity<?> getImageById(String imageId) throws IOException{


		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(imageId));

		com.mongodb.client.gridfs.model.GridFSFile image = this.gridClient.findOne(query);
		

		if(image==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();		
		}

		return ResponseEntity.ok()
				.contentLength(image.getLength())
				.body(this.gridClient.getResource(image));


	}

	private String prepareImageDir(String userId) throws ApiAccessException{
		/*
		 * First store image to disk so that we can process
		 */
		String homeDir = System.getenv("HOME");
		String baseDir = this.propsUtil.getString("yumu.images.basedir");

		/*
		 * create parent directory
		 */
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String imageDir = homeDir 
				+ "/" + baseDir 
				+ "/" + year 
				+ "/" + month
				+ "/" + day
				+ "/" + userId;

		File dir = new File(imageDir);
		System.out.println("Creating image dir " + imageDir);
		if(!dir.exists()) {
			try {
				FileUtils.forceMkdir(dir);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ApiAccessException("PREP_DIR_ERR: " + e.getMessage(), 
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return imageDir;
	}
	

	private File createThumbnail(  BufferedImage image, String type, String filename ) throws ApiAccessException 
	{ 
		int maxHeight = 250;
		int height = image.getHeight(); 
		int width = image.getWidth(); 
		double scale = maxHeight / (double) height; 
		//        BufferedImage thumbnail = Scalr.resize( image, Scalr.Method.ULTRA_QUALITY, 
		//            ( (Double) ( width * scale ) ).intValue(), ( (Double) ( height * scale ) ).intValue(), Scalr.OP_ANTIALIAS ); 

		BufferedImage thumbnail = Scalr.resize( image, Scalr.Method.ULTRA_QUALITY, 
				200, 200, Scalr.OP_ANTIALIAS ); 

		try 
		{ 
			File thumb = new File(filename);
			ImageIO.write( thumbnail, type, thumb ); 
			return thumb;
		} 
		catch ( IOException e ) 
		{ 
			//System.out.println( "failed to create thumbnail for picture" + e ); 
			throw new ApiAccessException("THUMBNAIL_ERR: " + e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	} 

	private File createStandard(  BufferedImage image, String type, String filename ) throws ApiAccessException 
	{ 

		BufferedImage thumbnail = Scalr.resize( image, Scalr.Method.ULTRA_QUALITY, 1024, Scalr.OP_ANTIALIAS ); 
		try 
		{ 
			File thumb = new File(filename);
			ImageIO.write( thumbnail, type, thumb ); 
			return thumb;
		} 
		catch ( IOException e ) 
		{ 
			throw new ApiAccessException("STANDARD_ERR: " + e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	} 



	/**
	 * We need 2 items from the uploaded image.
	 * 1. A thumbnail
	 * 2. An image of smaller size
	 * 
	 * @param multipartImage
	 * @param userId
	 * @throws ApiAccessException 
	 */
	public List<?> processUploadedImage(MultipartFile multipartImage, String userId) throws ApiAccessException {

		/*
		 * prep image dir
		 */
		String imageDir = this.prepareImageDir(userId);

		/*
		 * write original file to disk
		 */
		String fileName = RandomStringUtils.randomAlphanumeric(16);
		String absFileName = imageDir +"/" + fileName;
		File imageFile  = new File(absFileName);
		BufferedImage bufferedOrgImage = null;

		try {
			multipartImage.transferTo(imageFile);
			bufferedOrgImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApiAccessException("ORG_IMG_ERR: " + e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		/*
		 * Generate thumbnail from original
		 */
		File thumbFile = createThumbnail(bufferedOrgImage, "jpg", absFileName+"-tmb");
		ObjectId thumbGridFile = this.storeImage(thumbFile, userId, absFileName, fileName+"tmb");
		UserImage i1 = new UserImage();
		i1.setId(thumbGridFile.toString());
		i1.setMethod(UserImage.Method.GET);
		i1.setType(UserImage.Type.THUMBNAIL);
		i1.setUri( Api.BASE_VERSION + "/images/"+i1.getId());

		/*
		 * generate standard size image from original
		 */
		File standardFile = createStandard(bufferedOrgImage, "jpg", absFileName+"-std");
		ObjectId standardGridFile = this.storeImage(standardFile, userId, absFileName, fileName+"std");
		UserImage i2 = new UserImage();
		i2.setId(standardGridFile.toString());
		i2.setMethod(UserImage.Method.GET);
		i2.setType(UserImage.Type.STANDARD);
		i2.setUri(Api.BASE_VERSION + "/images/"+i2.getId());

		/*
		 * store the original as well
		 */
		ObjectId originalGridFile = this.storeImage(imageFile, userId, absFileName, fileName);
		UserImage i3 = new UserImage();
		i3.setId(originalGridFile.toString());
		i3.setMethod(UserImage.Method.GET);
		i3.setType(UserImage.Type.ORIGINAL);
		i3.setUri(Api.BASE_VERSION + "/images/"+i3.getId());

		List<UserImage> images = new LinkedList<>();
		images.add(i1);
		images.add(i2);
		images.add(i3);		
		return images;
	}

}
