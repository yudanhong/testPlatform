package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class TestA {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://192.168.1.168:8080/zxhl/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void test() throws Exception {
		selenium.open("/zxhl/");
		selenium.type("id=unm", "administrator");
		selenium.type("id=password", "请输入密码");
		selenium.type("id=psw", "123456");
		selenium.click("css=input.btn");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=div.header-icon.sitenet-icon");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=#menu1 > p.nav-name.fl");
		selenium.click("css=span.operat-name");
		selenium.selectFrame("frame_content");
		selenium.type("name=stcd", "tesetcod");
		selenium.type("id=stnm", "测站名称");
		selenium.keyUp("id=stnm", "e");
	}

	@After
	public void tearDown() throws Exception {
		//selenium.stop();
	}
}
