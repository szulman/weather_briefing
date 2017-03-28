package com.szuli.austro_download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFExport {

	public PDFExport() {
	}

	public void text2PDF(File in, File out) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(out));
		document.open();
		BufferedReader br = new BufferedReader(new FileReader(in));
		String line;
		Paragraph p;
		Font normal = new Font(FontFamily.TIMES_ROMAN, 12);
		Font bold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		boolean title = true;
		while ((line = br.readLine()) != null) {
			p = new Paragraph(line, title ? bold : normal);
			p.setAlignment(Element.ALIGN_JUSTIFIED);
			title = line.isEmpty();
			document.add(p);
		}
		document.close();
	}

	public void html2PDF(File in, File out) throws Exception {
		// String k = "<html><body> This is my Project </body></html>";
		String inHTML = FileUtils.readFileToString(in, Charset.defaultCharset());
		OutputStream file = new FileOutputStream(out);
		Document document = new Document();
		PdfWriter.getInstance(document, file);
		document.open();
		HTMLWorker htmlWorker = new HTMLWorker(document);
		htmlWorker.parse(new StringReader(inHTML));
		document.close();
		file.close();
	}
}
