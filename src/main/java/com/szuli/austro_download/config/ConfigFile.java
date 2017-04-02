package com.szuli.austro_download.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFile {

	public static final String CONFIG_FILE_NAME = "config.txt";

	// #=========================
	// #AUSTROCONTROL CREDENTIALS
	// #=========================
	private String austrocontrol_username;
	private String austrocontrol_password;

	// #=========================
	// #AIRSPACE USE PLAN
	// #=========================
	private boolean download_aup;

	// #=========================
	// #METARs
	// #=========================
	private boolean download_metar_austria_northeast;
	private boolean download_metar_austria_south;
	private boolean download_metar_austria_west;
	private boolean download_metar_hungary;
	private boolean download_metar_slovenia;

	// #=========================
	// #TAFs
	// #=========================
	private boolean download_taf_austria_northeast;
	private boolean download_taf_austria_south;
	private boolean download_taf_austria_west;
	private boolean download_taf_hungary;
	private boolean download_taf_slovenia;

	// #=========================
	// #METAOGRAMS
	// #=========================
	private boolean download_meteogramm_loww;
	private boolean download_meteogramm_traiskirchen;

	// #=========================
	// #VARIOUS DIAGRAMS
	// #=========================
	private boolean download_gramet_loav;
	private boolean download_zamg_forecast;
	private boolean download_rain;
	private boolean download_gafor_austria;
	private boolean download_qnh_austria;
	private boolean download_wind_austria;
	private boolean download_swc_alps;

	private static ConfigFile instance;

	private ConfigFile() throws ConfigFileException {
		this(new ConfigFileLocator().find(CONFIG_FILE_NAME));
	}

	private ConfigFile(File cfgFile) throws ConfigFileException {
		loadPropertiesFile(cfgFile);
	}

	public static ConfigFile getInstance(File props) {
		if (instance == null) {
			try {
				instance = new ConfigFile(props);
			} catch (ConfigFileException e) {
				e.printStackTrace();
				System.err.println("Error loading config file " + props);
			}
		}
		return instance;
	}

	public static ConfigFile getInstance() {
		if (instance == null) {
			try {
				File props;
				props = new ConfigFileLocator().find(CONFIG_FILE_NAME);
				return getInstance(props);
			} catch (ConfigFileException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public static ConfigFile load(File configFile) {
		try {
			if (instance == null) {
				if (configFile != null) {
					instance = new ConfigFile(configFile);
				} else {
					instance = new ConfigFile();
				}
			}
		} catch (ConfigFileException e) {
			System.err.println(
					"Could not load configuration file. Generating a default config file for you..Please adapt it manually!!\n"
							+ e.getMessage());
		}
		return instance;
	}

	private void loadPropertiesFile(File cfgFile) throws ConfigFileException {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(cfgFile));

			// #=========================
			// #AUSTROCONTROL CREDENTIALS
			// #=========================
			austrocontrol_username = prop.getProperty("austrocontrol_username");
			austrocontrol_password = prop.getProperty("austrocontrol_password");

			// #=========================
			// #AIRSPACE USE PLAN
			// #=========================
			download_aup = prop.getProperty("download_aup").toLowerCase().equals("yes");

			// #=========================
			// #METARs
			// #=========================
			download_metar_austria_northeast = prop.getProperty("download_metar_austria_northeast").toLowerCase().equals("yes");
			download_metar_austria_south = prop.getProperty("download_metar_austria_south").toLowerCase().equals("yes");
			download_metar_austria_west = prop.getProperty("download_metar_austria_west").toLowerCase().equals("yes");
			download_metar_hungary = prop.getProperty("download_metar_hungary").toLowerCase().equals("yes");
			download_metar_slovenia = prop.getProperty("download_metar_slovenia").toLowerCase().equals("yes");

			// #=========================
			// #TAFs
			// #=========================
			download_taf_austria_northeast = prop.getProperty("download_taf_austria_northeast").toLowerCase().equals("yes");
			download_taf_austria_south = prop.getProperty("download_taf_austria_south").toLowerCase().equals("yes");
			download_taf_austria_west = prop.getProperty("download_taf_austria_west").toLowerCase().equals("yes");
			download_taf_hungary = prop.getProperty("download_taf_hungary").toLowerCase().equals("yes");
			download_taf_slovenia = prop.getProperty("download_taf_slovenia").toLowerCase().equals("yes");

			// #=========================
			// #METAOGRAMS
			// #=========================
			download_meteogramm_loww = prop.getProperty("download_meteogramm_loww").toLowerCase().equals("yes");
			download_meteogramm_traiskirchen = prop.getProperty("download_meteogramm_traiskirchen").toLowerCase().equals("yes");

			// #=========================
			// #VARIOUS DIAGRAMS
			// #=========================
			download_gramet_loav = prop.getProperty("download_gramet_loav").toLowerCase().equals("yes");
			download_zamg_forecast = prop.getProperty("download_zamg_forecast").toLowerCase().equals("yes");
			download_rain = prop.getProperty("download_rain").toLowerCase().equals("yes");
			download_gafor_austria = prop.getProperty("download_gafor_austria").toLowerCase().equals("yes");
			download_qnh_austria = prop.getProperty("download_qnh_austria").toLowerCase().equals("yes");
			download_wind_austria = prop.getProperty("download_wind_austria").toLowerCase().equals("yes");
			download_swc_alps = prop.getProperty("download_swc_alps").toLowerCase().equals("yes");
		} catch (IOException e) {
			throw new ConfigFileException("Error loading configuration file: " + e.getMessage(), e);
		}
	}

	public static String getConfigFileName() {
		return CONFIG_FILE_NAME;
	}

	public String getAustrocontrol_username() {
		return austrocontrol_username;
	}

	public String getAustrocontrol_password() {
		return austrocontrol_password;
	}

	public boolean isDownload_aup() {
		return download_aup;
	}

	public boolean isDownload_metar_austria_northeast() {
		return download_metar_austria_northeast;
	}

	public boolean isDownload_metar_austria_south() {
		return download_metar_austria_south;
	}

	public boolean isDownload_metar_austria_west() {
		return download_metar_austria_west;
	}

	public boolean isDownload_metar_hungary() {
		return download_metar_hungary;
	}

	public boolean isDownload_metar_slovenia() {
		return download_metar_slovenia;
	}

	public boolean isDownload_taf_austria_northeast() {
		return download_taf_austria_northeast;
	}

	public boolean isDownload_taf_austria_south() {
		return download_taf_austria_south;
	}

	public boolean isDownload_taf_austria_west() {
		return download_taf_austria_west;
	}

	public boolean isDownload_taf_hungary() {
		return download_taf_hungary;
	}

	public boolean isDownload_taf_slovenia() {
		return download_taf_slovenia;
	}

	public boolean isDownload_meteogramm_loww() {
		return download_meteogramm_loww;
	}

	public boolean isDownload_meteogramm_traiskirchen() {
		return download_meteogramm_traiskirchen;
	}

	public boolean isDownload_gramet_loav() {
		return download_gramet_loav;
	}

	public boolean isDownload_zamg_forecast() {
		return download_zamg_forecast;
	}

	public boolean isDownload_rain() {
		return download_rain;
	}

	public boolean isDownload_gafor_austria() {
		return download_gafor_austria;
	}

	public boolean isDownload_qnh_austria() {
		return download_qnh_austria;
	}

	public boolean isDownload_wind_austria() {
		return download_wind_austria;
	}

	public boolean isDownload_swc_alps() {
		return download_swc_alps;
	}
}
