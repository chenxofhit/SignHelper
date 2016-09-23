package com.csu.vlab.atlas.common.constants;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * @author Chenx
 * @version 1.0.0
 * 
 */
public class AtlasPropertyConfigurer extends PropertyPlaceholderConfigurer {

	private static Properties constants = new Properties();

	public static String getProperty(String key) {
		return constants.getProperty(key);
	}

	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
		constants = props;
	}

}
