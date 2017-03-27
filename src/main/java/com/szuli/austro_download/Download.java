package com.szuli.austro_download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.szuli.austro_download.config.ConfigFile;

/**
 * REST client library: http://unirest.io/java.html
 * 
 * @author szuli
 *
 */
public class Download {

	
	public Download() {
	}

	
	public void createBriefingPack() {
		try {
			System.out.println("=============================");
			System.out.println("  Creating Weather Briefing  ");
			System.out.println("=============================\n\n");
			String authToken = login();
			//downloadGAFOR(authToken);
			//downloadTextForecastDanube();
			download_METAR_OESTERREICH_NORDOST();
			download_METAR_OESTERREICH_SUD();
			download_METAR_OESTERREICH_WEST();
			download_METAR_Ungarn();
			download_METAR_Slovenia();
			download_TAF_OESTERREICH_NORDOST();
			download_TAF_OESTERREICH_SUD();
			download_TAF_OESTERREICH_WEST();
			download_TAF_Ungarn();
			download_TAF_Slovenia();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void download_METAR(String baseURL, String outFile) throws Exception {
		String url = baseURL + System.currentTimeMillis() / 1000;
		HttpResponse<String> response = Unirest.get(url).asString();
		String forecast = response.getBody();
		FileUtils.write(new File(outFile), forecast, Charset.defaultCharset());
	}
	
	public void download_METAR_OESTERREICH_NORDOST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-OESTERREICH-NORDOST&tstamp=";
		download_METAR(url, "METAR_OE_NorthEast.txt");
	}
	

	public void download_TAF_OESTERREICH_NORDOST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-OESTERREICH-NORDOST&tstamp=";
		download_METAR(url, "TAF_OE_NorthEast.txt");
	}
	
	
	public void download_METAR_Ungarn() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-UNGARN&tstamp=";
		download_METAR(url, "METAR_Hungary.txt");
	}
	
	
	public void download_TAF_Ungarn() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-UNGARN&tstamp=";
		download_TAF(url, "TAF_Hungary.txt");
	}
	
	
	public void download_METAR_OESTERREICH_SUD() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-OESTERREICH-SUED&tstamp=";
		download_METAR(url, "METAR_OE_South.txt");
	}
	

	public void download_TAF_OESTERREICH_SUD() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-OESTERREICH-SUED&tstamp=";
		download_METAR(url, "TAF_OE_South.txt");
	}
	

	public void download_TAF_OESTERREICH_WEST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-OESTERREICH-WEST&tstamp=";
		download_METAR(url, "TAF_OE_West.txt");	
	}
	
	
	public void download_METAR_OESTERREICH_WEST() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-OESTERREICH-WEST&tstamp=";
		download_METAR(url, "METAR_OE_West.txt");	
	}
	
	
	public void download_TAF(String baseURL, String outFile) throws Exception {
		download_METAR(baseURL, outFile);
	}


	public void download_METAR_Slovenia() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=SA-SLOWENIEN&tstamp=";
		download_METAR(url, "METAR_Slovenia.txt");
	}
	
	
	public void download_TAF_Slovenia() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=TAF-SLOWENIEN&tstamp=";
		download_METAR(url, "TAF_Slovenia.txt");
	}
	
	/**
	 * Downloads the textual weather forecast for the danube area
	 */
	public void downloadTextForecastDanube() throws Exception {
		String url = "https://www.austrocontrol.at/flugwetter/bin/medasdb.php?flreq=FXOS41&tstamp=" + System.currentTimeMillis() / 1000;
		HttpResponse<String> response = Unirest.get(url).asString();
		String forecast = response.getBody();
		FileUtils.write(new File("Flugwetter_uebersicht.txt"), forecast, Charset.defaultCharset());
	}
	
	
	/**
	 * Download GAFOR as PDF from Austro Control
	 */
	public void downloadGAFOR(String authToken) throws Exception {

		String url = "https://www.austrocontrol.at/flugwetter/products/chartloop/gafor.pdf" + "?mtime="
				+ System.currentTimeMillis() / 1000 + "&auth_tkt=" + authToken;
		System.out.println("URL --> " + url);
		HttpResponse<InputStream> gafor = Unirest.get(url).asBinary();
		String filePath = "gafor.pdf";
		FileOutputStream fos = new FileOutputStream(new File(filePath));
		int inByte;
		InputStream gaforIS = gafor.getBody();
		while ((inByte = gaforIS.read()) != -1)
			fos.write(inByte);
		gaforIS.close();
		fos.close();
	}

	public String login() throws Exception {
		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		CookieStore cookieStore = new org.apache.http.impl.client.BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
				.setSSLSocketFactory(sslsf).build();

		Unirest.setHttpClient(httpclient);
		Unirest.clearDefaultHeaders();
		printCookies(cookieStore);

		// GET START SITE
		HttpResponse<String> response = Unirest.get("https://www.austrocontrol.at/flugwetter/start.php").asString();
		printResponse("Get Startsite", response);
		printCookies(cookieStore);

		// LOGIN
		Map<String, Object> loginData = new HashMap<String, Object>();
		loginData.put("back", "http://www.austrocontrol.at/flugwetter/start.php");
		loginData.put("lang", "en");
		loginData.put("username", ConfigFile.getInstance().getUsername());
		loginData.put("password", ConfigFile.getInstance().getPassword());
		String loginBody = "";
		for (String key : loginData.keySet()) {
			loginBody += key + "=" + loginData.get(key) + "&";
		}
		System.out.println("Data --> " + URLEncoder.encode(loginBody, Charset.defaultCharset().name()));

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.put("Cache-Control", "no-cache");
		headers.put("Cookie", "");
		response = Unirest.post("https://www.austrocontrol.at/flugwetter/acglogin.cgi").headers(headers).body(loginBody)
				.asString();
		printResponse("Login", response);
		printCookies(cookieStore);

		String authToken = URLDecoder.decode(getCookieByName("auth_tkt", cookieStore), Charset.defaultCharset().name());
		System.out.println("Authentication token --> " + authToken);
		return authToken;
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
		System.out.println("Body --> " + response.getBody());
		System.out.println("Headers --> " + response.getHeaders().entrySet());
		System.out.println("=====================================================");
	}

	public static final void main(String[] args) {
		Download d = new Download();
		d.createBriefingPack();
	}
}
