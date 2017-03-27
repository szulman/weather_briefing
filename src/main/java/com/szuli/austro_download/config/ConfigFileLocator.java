package com.szuli.austro_download.config;

import java.io.File;

public class ConfigFileLocator {
   
	
	private File cfgFile = null;
	
	public ConfigFileLocator() {
	}
	
	
	public ConfigFileLocator(File cfgFile) {
		this.cfgFile = cfgFile;
	}
	
	
	public File find(String confFileName) throws ConfigFileException {
        if (cfgFile != null) {
        	return cfgFile;
        } else {
    		File cfgFile1 = new File("./" + confFileName);
    		if (cfgFile1.exists()) {
    			System.out.println("Configuration file found in " + cfgFile1.getAbsolutePath());
                return cfgFile1;
    		} else {
    			File cfgFile2 = new File(getUserDir(), confFileName);
                if (cfgFile2.exists()) {
                	System.out.println("Configuration file found in " + cfgFile2.getAbsolutePath());
                    return cfgFile2;
                } else {
                	String userHome = getUserHome();
                    File cfgFile3 = new File(userHome, "/.version-x/" + confFileName);
                    if (cfgFile3.exists()) {
                    	 System.out.println("Configuration file found in " + cfgFile3.getAbsolutePath());
                         return cfgFile3;
                    } else {
                        throw new ConfigFileException(
                                String.format("Configuration file not found neither in '%s' nor in '%s' nor in '%s'", 
                                        		cfgFile1.getAbsolutePath(), 
                                        		cfgFile2.getAbsolutePath(),
                                        		cfgFile3.getAbsolutePath()));
                    }
                }
    		}
       }
    }
    
    
    public static String getUserDir() {
        if (System.getProperty("user.dir") != null) {
            return System.getProperty("user.dir");
        }
        
        return new File(".").getAbsolutePath();
    }
    
    
    public static String getUserHome() throws ConfigFileException {
        String userHome = System.getProperty("user.home");
        
        if(userHome == null)
            throw new ConfigFileException("User home cannot be located: System property user.home not found");
        return userHome;
    }
}
