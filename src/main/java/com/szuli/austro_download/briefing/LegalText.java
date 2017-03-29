package com.szuli.austro_download.briefing;

import java.io.File;

public class LegalText extends TextBriefing {

	public static final String NAME = "legal";
	public static final String WARRANTY_FILE = "warranty.txt";
	
	
	public LegalText() throws Exception {
		super(NAME, new File(WARRANTY_FILE));
	}

}
