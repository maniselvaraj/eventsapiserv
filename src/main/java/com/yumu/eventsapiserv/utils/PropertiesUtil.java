package com.yumu.eventsapiserv.utils;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Component;

@Component
public class PropertiesUtil {

	private Configuration config;

	@PostConstruct
	public void init(){
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
				new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
				.configure(params.properties()
						.setFileName("application.properties"));
		try
		{
			this.config = builder.getConfiguration();
		}
		catch(ConfigurationException cex)
		{
			// loading of the configuration file failed
		}
	}
	
	public String getString(String key){
		return this.config.getString(key);
	}
	
	public List<String> getStringList(String key){
		return this.config.getList(String.class, key);
	}
}
