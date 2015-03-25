package net.keepsoft.selenium.core;

import net.keepsoft.server.SeleniumServer;
import net.keepsoft.test.entity.ExecuteProperty;
import net.keepsoft.test.entity.TestCase;

public interface SeleniumRunnable {
	public void startUp(SeleniumServer ss,String path,ExecuteProperty pro);
	public void loadSeleniumCase(String path,String productId);
	public void executeCase(TestCase tcase) throws Exception;
	public void tearDown();
}
