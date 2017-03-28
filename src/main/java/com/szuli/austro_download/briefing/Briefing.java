package com.szuli.austro_download.briefing;

import java.io.File;

public abstract class Briefing {

	private String name;
	private File briefingFile;
	
	
	public Briefing() {
	}
	

	public Briefing(String name, File briefingFile) {
		this.name = name;
		this.briefingFile = briefingFile;
	}
	
	
	public File getBriefingFile() {
		return briefingFile;
	}
	
	
	public void setBriefingFile(File briefingFile) {
		this.briefingFile = briefingFile;
	}

	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
}
