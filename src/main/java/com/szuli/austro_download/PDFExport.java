package com.szuli.austro_download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.szuli.austro_download.briefing.Briefing;
import com.szuli.austro_download.briefing.BriefingPack;
import com.szuli.austro_download.briefing.PDFBriefing;
import com.szuli.austro_download.briefing.TextBriefing;


public class PDFExport {

	public PDFExport() {
	}

	
	/**
	 * Converts the briefing pack into a single PDF
	 * @param briefingPack
	 * @param pdf
	 */
	public static void briefingPack2PDF(BriefingPack briefingPack, File pdf) throws Exception {
		//TEXT (METAR, TAF,..) --> PDF
		List<TextBriefing> textBriefings = getTextBriefings(briefingPack);
		File textPDF = File.createTempFile("austro_download", "_textBriefings.pdf");
		textPDF.deleteOnExit();
		text2PDF(textBriefings, textPDF);
		List<PDFBriefing> pdfBriefings = getPDFBriefings(briefingPack);
		pdfMerge(pdfBriefings, textPDF, pdf);
	}
	
	
	public static void pdfMerge(List<PDFBriefing> pdfBriefings, File in, File out) throws Exception {
		PDFMergerUtility merge = new PDFMergerUtility();
		merge.addSource(in);
		for (PDFBriefing briefing : pdfBriefings) {
			merge.addSource(briefing.getBriefingFile());
		}
		merge.setDestinationFileName(out.getAbsolutePath());
		merge.mergeDocuments();
	}

	
	public static void text2PDF(List<TextBriefing> textBriefings, File out) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(out));
		document.open();
		for (TextBriefing textBriefing : textBriefings) {
			BufferedReader br = new BufferedReader(new FileReader(textBriefing.getBriefingFile()));
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
			br.close();
		}
		document.close();
	}


	/**
	 * Returns all text-based briefings (e.g. TAF, METAR,..)
	 * @param briefingPack
	 * @param pdf
	 */
	public static List<PDFBriefing> getPDFBriefings(BriefingPack briefingPack) {
		List<PDFBriefing> pdfBriefings = new ArrayList<PDFBriefing>();
		for (Briefing briefing: briefingPack.getBriefings().values()) {
			if (briefing instanceof PDFBriefing) {
				pdfBriefings.add((PDFBriefing) briefing);
			}
		}
		return pdfBriefings;
	}
	
	
	/**
	 * Returns all text-based briefings (e.g. TAF, METAR,..)
	 * @param briefingPack
	 * @param pdf
	 */
	public static List<TextBriefing> getTextBriefings(BriefingPack briefingPack) {
		List<TextBriefing> textBriefings = new ArrayList<TextBriefing>();
		for (Briefing briefing: briefingPack.getBriefings().values()) {
			if (briefing instanceof TextBriefing) {
				textBriefings.add((TextBriefing) briefing);
			}
		}
		return textBriefings;
	}

}
