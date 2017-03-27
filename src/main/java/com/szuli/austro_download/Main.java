package com.szuli.austro_download;

import java.io.File;

public class Main {

	
	public Main() {
	}
	
	
	public void start() {
		try {
			PDFOutput pdf = new PDFOutput();
			pdf.text2PDF(new File("METAR_Hungary.txt"), new File("hello.pdf"));
			//	pdf.html2PDF(new File("METAR_Hungary.txt"), new File("hello.pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Main m = new Main();
		m.start();
	}
}
