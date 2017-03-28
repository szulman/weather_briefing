package com.szuli.austro_download;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Removes the HTML tags from the METAR/TAF/.. reports retrieved from Austrocontrol.
 * @author szuli
 *
 */
public class CleanupHTML {

	
	public static File cleanHTML(File in) throws Exception {
		if (!isHTML(in)) {
			return in;
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		dBuilder.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				if (systemId.toLowerCase().contains(".dtd")) {
					return new InputSource(new StringReader(""));
				} else {
					return null;
				}
			}
		});
		Document doc = dBuilder.parse(in);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList metarBodyList = doc.getElementsByTagName("p");
		Node metarBody = metarBodyList.item(0);
		String rawContent = metarBody.getTextContent();
		System.out.println("METARS --> " + rawContent);
		File out = File.createTempFile("austro_weather", "_clean_html.txt");
		out.deleteOnExit();
		FileUtils.write(out, rawContent, Charset.defaultCharset());
		return out;
	}
	
	/**
	 * Makes a quick huristical check if the file is still HTML. The check is made based on the existence of the <html> tag in the file
	 * @return
	 */
	public static boolean isHTML(File file) throws IOException {
		String content = FileUtils.readFileToString(file, Charset.defaultCharset());
		return content.contains("<html>");
	}
}
