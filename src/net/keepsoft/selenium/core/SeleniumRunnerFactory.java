package net.keepsoft.selenium.core;

public class SeleniumRunnerFactory {
	private static SeleniumRunnable runner;

	/**
	 * mode;//执行方式: 0-xml, 1-db
	 * @param mode
	 * @return
	 */
	public static SeleniumRunnable getInstance(int mode) {
		if(runner==null){
			runner = mode==0?new XmlSeleniumRunner():new DBSeleniumRunner();
		}
		return runner;
	}
	
}
