package com.yumu.eventsapiserv.pojos.common;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageImplWithMeta<T> extends PageImpl<T> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4716680607076013647L;

	public PageImplWithMeta(List<T> content, Pageable pageable, long total, Map<String, String> metadata) {

		super(content, pageable, total);
		this.setMeta(metadata);
	}
	


	public Map<String, String> getMeta() {
		return meta;
	}



	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}



	private Map<String, String> meta = null;
	
	
	
	
}
