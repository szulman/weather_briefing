package com.szuli.austro_download.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigFile {


	public static final String CONFIG_FILE_NAME = "config.txt";

	private String username;
	private String password;

	private static ConfigFile instance;
	
	
	private ConfigFile() throws ConfigFileException {
		this(new ConfigFileLocator().find(CONFIG_FILE_NAME));
	}

	
	private ConfigFile(File cfgFile) throws ConfigFileException {
		loadPropertiesFile(cfgFile);
	}

	
	public static ConfigFile getInstance(File props) {
		if (instance == null) {
			try {
				instance = new ConfigFile(props);
			} catch (ConfigFileException e) {
				e.printStackTrace();
				System.err.println("Error loading config file " + props);
			}
		}
		return instance;
	}

	
	public static ConfigFile getInstance() {
    	if (instance == null) {
    		try {
        		File props;
    			props = new ConfigFileLocator().find(CONFIG_FILE_NAME);
    		    return getInstance(props);
    		 } catch (ConfigFileException e) {
    			e.printStackTrace();
    		}
        }
    	return instance;
    }
	
	
	public static ConfigFile load(File configFile) {
		try {
			if (instance == null) {
				if (configFile != null) {
					instance = new ConfigFile(configFile);
				} else {
					instance = new ConfigFile();
				}
			}
		} catch (ConfigFileException e) {
			System.err.println("Could not load configuration file. Generating a default config file for you..Please adapt it manually!!\n" + e.getMessage());
		}
		return instance;
	}

	
	private void loadPropertiesFile(File cfgFile) throws ConfigFileException {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(cfgFile));

			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
		} catch (IOException e) {
			throw new ConfigFileException("Error loading configuration file: " + e.getMessage(), e);
		}
	}

	
	public String getPassword() {
		return password;
	}
	
	
	public String getUsername() {
		return username;
	}
}
