package net.keepsoft.selenium.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
	private static WebDriver driver;

	/**
	 * mode;//执行方式: 0-xml, 1-db
	 * @param mode
	 * @return
	 */
	public static WebDriver getInstance(String mode,String basePath) throws Exception {
		/* selenuim 2.43.0
		 * 1、设置ie浏览器 关闭安全模式
		 * 2、关闭安全模式：	ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		 * 3、ie_64:   		System.setProperty("webdriver.ie.driver", "E:\\IEDriverServer_x64_2.43.0\\IEDriverServer.exe");
		 *   chrome_32:    	System.setProperty("webdriver.chrome.driver", "E:\\chromedriver_win32\\chromedriver.exe");
		 */
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		if(mode.equals("ie")) {
			System.setProperty("webdriver.ie.driver", basePath+"drivers\\IEDriverServer_x64_2.43.0\\IEDriverServer.exe");
        	driver = new InternetExplorerDriver(ieCapabilities);
		}
		if(mode.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", basePath+"drivers\\chromedriver_win32\\chromedriver.exe");
        	driver = new ChromeDriver(ieCapabilities);
		}
		if(mode.equals("firefox")) {
			driver = new FirefoxDriver(ieCapabilities);
		}
		return driver;
	}
}
