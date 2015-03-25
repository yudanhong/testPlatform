package net.keepsoft.selenium.core;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class MyWebDriverBackedSelenium extends  WebDriverBackedSelenium {

	public MyWebDriverBackedSelenium(WebDriver driver, String baseUrl) {
		super(driver, baseUrl);
	}
	@Override
	public void click(String locator){
		super.click(locator);
		this.waitForPageToLoad("30000");
	}
	
}
