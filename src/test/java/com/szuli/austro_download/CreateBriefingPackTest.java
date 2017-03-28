package com.szuli.austro_download;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.szuli.austro_download.briefing.BriefingPack;
import com.szuli.austro_download.config.ConfigFile;

public class CreateBriefingPackTest {

	@BeforeClass
	public static void setup() {
		ConfigFile.getInstance(new File("my_config.txt"));
	}

	
	@Test
	public void testLogin() {
		CreateBriefingPack c = new CreateBriefingPack();
		try {
			String authToken = c.login();
			Assert.assertTrue(authToken.length() > 5);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testCreateBriefingPack() {
		CreateBriefingPack c = new CreateBriefingPack();
		try {
			BriefingPack bp = c.createBriefingPack();
			Assert.assertTrue(bp.getBriefings().size() > 5);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
