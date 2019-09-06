/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class LogUtil {

	public static String stackToString(Exception e){
		final Writer sWriter = new StringWriter();
		final PrintWriter printer = new PrintWriter(sWriter);
		e.printStackTrace(printer);
		return sWriter.toString();
	}
}
