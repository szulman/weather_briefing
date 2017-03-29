package com.szuli.austro_download;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.szuli.austro_download.briefing.Briefing;
import com.szuli.austro_download.briefing.LegalText;
import com.szuli.austro_download.briefing.BriefingPack;
import com.szuli.austro_download.config.ConfigFile;

public class CreateBriefingPackTest {

	@BeforeClass
	public static void setup() {
		ConfigFile.getInstance(new File("my_config.txt"));
	}

	
	@Test
	public void login() {
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
	public void createBriefingPack() {
		CreateBriefingPack c = new CreateBriefingPack();
		try {
			BriefingPack bp = c.createBriefingPack();
			Assert.assertNotNull(bp.getBriefing(LegalText.NAME));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	@Ignore
	public void downloadBriefingPack() {
		CreateBriefingPack c = new CreateBriefingPack();
		try {
			BriefingPack bp = c.downloadBriefingPack();
			Assert.assertTrue(bp.getBriefings().size() > 5);
			Briefing metarHungary = bp.getBriefing("METAR Hungary");
			Assert.assertNotNull(metarHungary);
			String content = FileUtils.readFileToString(metarHungary.getBriefingFile(), Charset.defaultCharset());
			Assert.assertFalse(content.contains("<"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
