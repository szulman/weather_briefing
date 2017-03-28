package com.szuli.austro_download;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;


public class CleanupHTMLTest {

	
	@Test
	public void testCleanupHTML() {
		try {
			File cleanFile = CleanupHTML.cleanHTML(new File("METAR.txt"));
			String content = FileUtils.readFileToString(cleanFile, Charset.defaultCharset());
			System.out.println("clean HTML --> " + content);
			Assert.assertFalse(content.contains("<"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
