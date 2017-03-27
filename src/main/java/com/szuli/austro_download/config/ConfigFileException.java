package com.szuli.austro_download.config;


public class ConfigFileException extends Exception {
 
	
	public ConfigFileException(String message) {
        this(message, null);
    }
    

    public ConfigFileException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
