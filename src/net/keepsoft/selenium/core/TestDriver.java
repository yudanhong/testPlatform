package net.keepsoft.selenium.core;

import org.openqa.selenium.WebDriver;

public class TestDriver {
	private String url;
	private String browser;
	private WebDriver driver;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	

}
