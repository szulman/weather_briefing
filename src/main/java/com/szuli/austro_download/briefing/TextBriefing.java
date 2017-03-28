package com.szuli.austro_download.briefing;

import java.io.File;

import com.szuli.austro_download.CleanupHTML;

public class TextBriefing extends Briefing {

	
	public TextBriefing() {
	}
	
	
	public TextBriefing(String name, File txtFile) throws Exception {
		setName(name);
		cleanHTML(txtFile);
	}

	
	public void cleanHTML(File txtFile) throws Exception {
		File cleanFile = CleanupHTML.cleanHTML(txtFile);
		setBriefingFile(cleanFile);
	}
}
