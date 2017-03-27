package com.szuli.austro_download;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.szuli.austro_download.config.ConfigFile;

public class TestDownload {


	@BeforeClass
	public static void setup() {
		ConfigFile.getInstance(new File("my_config.txt"));
	}

	
	@Test
	public void testDownload() {
		Download d = new Download();
		d.createBriefingPack();
	}
}
