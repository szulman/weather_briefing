package com.szuli.austro_download;

public class Main {

	
	public Main() {
	}
	
	
	public void start() {
		CreateBriefingPack c = new CreateBriefingPack();
		try {
			c.createBriefingPack();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		Main m = new Main();
		m.start();
	}
}
