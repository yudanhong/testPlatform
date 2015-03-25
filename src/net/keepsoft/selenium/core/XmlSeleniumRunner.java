package net.keepsoft.selenium.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import net.keepsoft.server.SeleniumServer;
import net.keepsoft.test.entity.ExecuteProperty;
import net.keepsoft.test.entity.TestCase;
import net.keepsoft.utils.CommonUtils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.selenium.Selenium;

@SuppressWarnings("deprecation")
public class XmlSeleniumRunner implements SeleniumRunnable {
	public static String basePath = "";
	public static String SNAPSHOTPATH = "";
	public static File result = null;
	public static PrintWriter print = null;
	public static Selenium selenium = null;
	public static TestDriver tdriver = null;
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void startUp(SeleniumServer ss, String path,ExecuteProperty pro) {

		Document doc = null;
		InputStream is;
		try {
			//初始化文件目录和日志文件
			basePath = path;//SeleniumCore.class.getClassLoader().getResource("/").getPath();
			SNAPSHOTPATH = path +"snapshot\\"+ CommonUtils.getTime("yyyyMMdd")+"\\"+CommonUtils.getTime("HHmm");
			CommonUtils.mkDir(SNAPSHOTPATH);
			result = new File(SNAPSHOTPATH+"\\result.log");
			print = new PrintWriter(new FileOutputStream(result));
			//开始加载selenium文件
			is = new FileInputStream(basePath+"selenium.xml");
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(is);
			Element root = doc.getRootElement();
			for (Iterator i = root.elementIterator("config"); i.hasNext();) {
				Element el = (Element) i.next();
				tdriver.setUrl(el.element("baseUrl").attributeValue("value")) ;
				String browser = el.element("driver").attributeValue("browser");
				tdriver.setBrowser(browser);
				tdriver.setDriver(WebDriverFactory.getInstance(browser,basePath)) ;
				
			}
			//打开浏览器访问测试地址
			selenium = new MyWebDriverBackedSelenium(tdriver.getDriver(), tdriver.getUrl());
			selenium.open(tdriver.getUrl());
			if(!selenium.getTitle().contains("中小河流")){
				throw new Exception("无法打开网页");
			}
			//保存selenium运行记录-持久化
			ss.saveSeleniumCheck(tdriver.getBrowser(),pro.getProductId());
			
			//解析test文件
			for(Iterator imp = root.elementIterator("import"); imp.hasNext();){
				Element el = (Element) imp.next();
				loadSeleniumCase(basePath+el.attributeValue("path"),pro.getProductId());
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			print.println(CommonUtils.getTime("yyyyMMdd HHmmss")+": 程序出现异常--"+e.getMessage());
			print.flush();
		}finally{
			try {
				tearDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void loadSeleniumCase(String path,String productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeCase(TestCase tcase) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tearDown() {
		// TODO Auto-generated method stub
		
	}


}
