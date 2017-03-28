package com.szuli.austro_download.briefing;

import java.io.File;

public abstract class Briefing {

	private File briefingFile;
	
	
	public Briefing() {
	}
	

	public Briefing(File briefingFile) {
		this.briefingFile = briefingFile;
	}
	
	
	public File getBriefingFile() {
		return briefingFile;
	}
	
	
	public void setBriefingFile(File briefingFile) {
		this.briefingFile = briefingFile;
	}
}
