package com.szuli.austro_download;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CreateBriefingPack {

	
	public CreateBriefingPack() {
	}
	
	
	public String cleanHTML(File in) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(in);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList metarBodyList = doc.getElementsByTagName("p");
		Node metarBody = metarBodyList.item(0);
		String rawContent = metarBody.getTextContent();
		System.out.println("METARS --> " + rawContent);
		return rawContent;
	}

	
	public static void main(String[] args) {
		CreateBriefingPack b = new CreateBriefingPack();
		try {
			b.cleanHTML(new File("METAR_Hungary.html"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
