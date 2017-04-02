package com.szuli.austro_download.config;


import org.junit.Assert;
import org.junit.Test;


public class ConfigTest {


	@Test
	public void testCoreProperties() {
		ConfigFile config = ConfigFile.getInstance();
		Assert.assertNotNull(config);
		Assert.assertNotNull(config.getAustrocontrol_username());
		Assert.assertNotNull(config.getAustrocontrol_password());
		Assert.assertTrue(config.isDownload_aup());
		Assert.assertTrue(config.isDownload_gafor_austria());
		Assert.assertTrue(config.isDownload_gramet_loav());
		Assert.assertTrue(config.isDownload_metar_austria_northeast());
		Assert.assertTrue(config.isDownload_metar_austria_south());
		Assert.assertTrue(config.isDownload_metar_austria_west());
		Assert.assertTrue(config.isDownload_metar_hungary());
		Assert.assertTrue(config.isDownload_metar_slovenia());
		Assert.assertTrue(config.isDownload_meteogramm_loww());
		Assert.assertTrue(config.isDownload_meteogramm_traiskirchen());
		Assert.assertTrue(config.isDownload_qnh_austria());
		Assert.assertTrue(config.isDownload_rain());
		Assert.assertTrue(config.isDownload_swc_alps());
		Assert.assertTrue(config.isDownload_taf_austria_northeast());
		Assert.assertTrue(config.isDownload_taf_austria_south());
		Assert.assertTrue(config.isDownload_taf_austria_west());
		Assert.assertTrue(config.isDownload_wind_austria());
		Assert.assertTrue(config.isDownload_zamg_forecast());
	}
}
