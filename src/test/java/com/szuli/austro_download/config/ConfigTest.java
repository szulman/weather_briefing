package com.szuli.austro_download.config;


import org.junit.Assert;
import org.junit.Test;


public class ConfigTest {

	
	@Test
	public void testCoreProperties() {
		ConfigFile config = ConfigFile.getInstance();
		Assert.assertNotNull(config);
		Assert.assertNotNull(config.getUsername());
		Assert.assertNotNull(config.getPassword());
	}
}
