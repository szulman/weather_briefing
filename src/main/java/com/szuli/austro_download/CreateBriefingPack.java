package com.szuli.austro_download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.szuli.austro_download.briefing.BriefingPack;
import com.szuli.austro_download.briefing.ForecastBriefing;
import com.szuli.austro_download.briefing.GAFORBriefing;
import com.szuli.austro_download.briefing.HeaderText;
import com.szuli.austro_download.briefing.LegalText;
import com.szuli.austro_download.briefing.METARBriefing;
import com.szuli.austro_download.briefing.TAFBriefing;
import com.szuli.austro_download.config.ConfigFile;

/**
 * REST client library: http://unirest.io/java.html
 * 
 * @author szuli
 *
 */
public class CreateBriefingPack {

	private String authToken = "";
	public static final String BRIEFING_DIR = "briefings/";
	
	public CreateBriefingPack() {
	}

	public BriefingPack createBriefingPack() throws Exception {
		//download reports
		BriefingPack bp = downloadBriefingPack();
		
		//get timestamp
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timeStamp = format.format(new Date(System.currentTimeMillis()));
		//generate PDF output
		File briefingDir = new File(BRIEFING_DIR);
		if (!briefingDir.exists()) {
			briefingDir.mkdir();
		}
		File pdf = new File(briefingDir + "/briefing_pack_" + timeStamp + ".pdf");
		PDFExport.briefingPack2PDF(bp, pdf);
		System.out.println("Generated briefing pack --> " + pdf.getAbsolutePath());
		return bp;
	}

	
	public BriefingPack downloadBriefingPack() throws Exception {
		BriefingPack bp = new BriefingPack();
		System.out.println("=============================");
		System.out.println("  Weather Briefing (v0.1.0) ");
		System.out.println("=============================\n\n");
		String authToken = login();
		if (authToken != null && !authToken.trim().equals("")) {
			bp.addBriefing(new HeaderText());
			bp.addBriefing(new LegalText());
			bp.addBriefing(downloadGAFOR(authToken));
			bp.addBriefing(downloadTextForecastDanube());
			bp.addBriefing(download_METAR_OESTERREICH_NORDOST());
			bp.addBriefing(download_METAR_OESTERREICH_SUD());
			bp.addBriefing(download_METAR_OESTERREICH_WEST());
			bp.addBriefing(download_METAR_Ungarn());
			bp.addBriefing(download_METAR_Slovenia());
			bp.addBriefing(download_TAF_OESTERREICH_NORDOST());
			bp.addBriefing(download_TAF_OESTERREICH_SUD());
			bp.addBriefing(download_TAF_OESTERREICH_WEST());
			bp.addBriefing(download_TAF_Ungarn());
			bp.addBriefing(download_TAF_Slovenia());
		} else {
			throw new Exception("Couldn't login to https://www.austrocontrol.at/flugwetter/. Check you username/password in the config.txt file.");
		}
		return bp;
	}

	public TAFBriefing download_TAF(String briefingName, String baseURL) throws Exception {
		return new TAFBriefing(briefingName, download_Report(briefingName, baseURL).getBriefingFile());
	}

	public METARBriefing download_Report(String briefingName, String baseURL) throws Exception {
		System.out.print("Downloading " + briefingName + "...");
		String url = baseURL + System.currentTimeMillis() / 1000;
		HttpResponse<String> response = Unirest.get(url).asString();
		String forecast = response.getBody();
		File metarFile = File.createTempFile("austro_weather", "_.txt");
		metarFile.deleteOnExit();
		FileUtils.write(metarFile, forecast, Charset.defaultCharset());
		System.out.println("OK");
		return new METARBriefing(briefingName, metarFile);
	}

	public METARBriefing download_METAR_OESTERREICH_NORDOST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-OESTERREICH-NORDOST&tstamp=";
		return download_Report("METAR Austria North-East", url);
	}

	public TAFBriefing download_TAF_OESTERREICH_NORDOST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-OESTERREICH-NORDOST&tstamp=";
		return download_TAF("TAF Austria North-East", url);
	}

	public METARBriefing download_METAR_Ungarn() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-UNGARN&tstamp=";
		return download_Report("METAR Hungary", url);
	}

	public TAFBriefing download_TAF_Ungarn() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-UNGARN&tstamp=";
		return download_TAF("TAF Hungary", url);
	}

	public METARBriefing download_METAR_OESTERREICH_SUD() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-OESTERREICH-SUED&tstamp=";
		return download_Report("METAR Austria South", url);
	}

	public TAFBriefing download_TAF_OESTERREICH_SUD() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-OESTERREICH-SUED&tstamp=";
		return download_TAF("TAF Austria South", url);
	}

	public TAFBriefing download_TAF_OESTERREICH_WEST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-OESTERREICH-WEST&tstamp=";
		return download_TAF("TAF Austria West", url);
	}

	public METARBriefing download_METAR_OESTERREICH_WEST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-OESTERREICH-WEST&tstamp=";
		return download_Report("METAR Austria West", url);
	}

	public METARBriefing download_METAR_Slovenia() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-SLOWENIEN&tstamp=";
		return download_Report("METAR Slovenia", url);
	}

	public TAFBriefing download_TAF_Slovenia() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-SLOWENIEN&tstamp=";
		return download_TAF("TAF Slovenia", url);
	}

	/**
	 * Downloads the textual weather forecast for the danube area
	 */
	public ForecastBriefing downloadTextForecastDanube() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=FXOS41&tstamp="
				+ System.currentTimeMillis() / 1000;
		String briefingName = "Forecast Danube Area";
		return new ForecastBriefing(briefingName, download_Report(briefingName, url).getBriefingFile());
	}

	/**
	 * Download GAFOR as PDF from Austro Control
	 */
	public GAFORBriefing downloadGAFOR(String authToken) throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/products/chartloop/gafor.pdf" + "?mtime="
				+ System.currentTimeMillis() / 1000 + "&auth_tkt=" + authToken;
		HttpResponse<InputStream> gafor = Unirest.get(url).asBinary();
		File gaforFile = File.createTempFile("austro_weather", "_gafor.pdf");
		gaforFile.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(gaforFile);
		int inByte;
		InputStream gaforIS = gafor.getBody();
		while ((inByte = gaforIS.read()) != -1)
			fos.write(inByte);
		gaforIS.close();
		fos.close();
		return new GAFORBriefing("GAFOR Austria", gaforFile);
	}

	public String login() throws Exception {
		if (authToken != null && authToken.length() > 1) {
			return authToken;
		}

		CookieStore cookieStore = new BasicCookieStore();

		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.setCookieStore(cookieStore);
		Unirest.setHttpClient(httpClient);

		// GET START SITE
		HttpResponse<String> response = Unirest.get("https://www.austrocontrol.at/flugwetter/start.php").asString();

		// LOGIN
		Map<String, Object> loginData = new HashMap<String, Object>();
		loginData.put("lang", "en");
		loginData.put("username", ConfigFile.getInstance().getUsername());
		loginData.put("password", ConfigFile.getInstance().getPassword());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Referer",
				"https://www.austrocontrol.at/flugwetter/start.php?back=https://www.austrocontrol.at/flugwetter/index.php?id=470&lang=en");
		headers.put("Cookie", "auth_probe=1;");
		response = Unirest.post("https://www.austrocontrol.at/flugwetter/acglogin.cgi").headers(headers)
				.fields(loginData).asString();
		String authToken = URLDecoder.decode(getCookieByName("auth_tkt", cookieStore), Charset.defaultCharset().name());
		return authToken;
	}

	/**
	 * Creates a cookie auth_probe=1. Austrocontrol seems to only allow the
	 * login if this cookie is submitted as well
	 * 
	 * @param cookieStore
	 * @return
	 */
	public Cookie createAuthCookie(CookieStore cookieStore) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 100);
		Date date = calendar.getTime();
		BasicClientCookie cookie = new BasicClientCookie("auth_probe", "1");
		cookie.setDomain("www.austrocontrol.at");
		cookie.setExpiryDate(date);
		cookie.setPath("/");
		return cookie;
	}

	/**
	 * Serializes the cookies into a string
	 * 
	 * @param cookieStore
	 * @return
	 */
	public String cookies2String(CookieStore cookieStore) {
		String cookieString = "";
		List<Cookie> cookies = cookieStore.getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			Cookie cookie = cookies.get(i);
			String name = cookie.getName();
			String value = cookie.getValue();
			cookieString += name + "=" + value;
			if (i < cookies.size() - 1) {
				cookieString += "; ";
			}
		}
		return cookieString;
	}

	
	public void printCookies(CookieStore cookieStore) {
		System.out.println("=====================================================");
		System.out.println("      PRINTING COOKIES..\n\n");
		for (Cookie cookie : cookieStore.getCookies()) {
			System.out.println("Name --> " + cookie.getName() + ", value --> " + cookie.getValue() + ", path --> "
					+ cookie.getPath() + ", domain --> " + cookie.getDomain() + ", comment --> " + cookie.getComment());
		}
		System.out.println("=====================================================");
	}

	public String getCookieByName(String name, CookieStore cookieStore) {
		for (Cookie cookie : cookieStore.getCookies()) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return "";
	}

	public void printResponse(String topic, HttpResponse<String> response) {
		System.out.println("=====================================================");
		System.out.println("              " + topic.toUpperCase() + "\n\n");
		System.out.println("Status --> " + response.getStatus() + "/" + response.getStatusText());
		// System.out.println("Body --> " + response.getBody());
		System.out.println("Headers --> " + response.getHeaders().entrySet());
		System.out.println("=====================================================");
	}
}
