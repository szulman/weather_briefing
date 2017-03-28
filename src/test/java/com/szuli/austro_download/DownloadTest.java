package com.szuli.austro_download;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.szuli.austro_download.config.ConfigFile;



public class DownloadTest {


	@BeforeClass
	public static void setup() {
		ConfigFile.getInstance(new File("my_config.txt"));
	}

	
	@Test
	public void testLogin() {
		Download d = new Download();
		try {
			String authToken = d.login();
			Assert.assertTrue(authToken.length() > 5);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
