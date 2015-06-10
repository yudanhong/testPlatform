package net.keepsoft.selenium.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.keepsoft.server.SeleniumServer;
import net.keepsoft.test.entity.ExecuteProperty;
import net.keepsoft.test.entity.TestCase;
import net.keepsoft.test.entity.TestResult;
import net.keepsoft.test.entity.TestStep;
import net.keepsoft.test.entity.TestSuit;
import net.keepsoft.utils.CommonUtils;
import net.keepsoft.utils.Waite;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;

import com.thoughtworks.selenium.Selenium;

public class DBSeleniumRunner implements SeleniumRunnable {
	public String basePath = "";
	public String SNAPSHOTPATH = "";
	public File result = null;
	public PrintWriter print = null;
	public Selenium selenium = null;
	public TestDriver tdriver = new TestDriver();
	private StringBuffer photos;
	private String photoBasePath;
	private SeleniumServer ss;
	
	private Integer execId ;//测试执行的编号
	
	@Override
	public void startUp(SeleniumServer server, String path,ExecuteProperty pro) {
		ss = server;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//初始化文件目录和日志文件
			basePath = path;//SeleniumCore.class.getClassLoader().getResource("/").getPath();
			SNAPSHOTPATH = path +"snapshot\\"+ CommonUtils.getTime("yyyyMMdd")+"\\"+CommonUtils.getTime("HHmm");
			CommonUtils.mkDir(SNAPSHOTPATH);
			result = new File(SNAPSHOTPATH+"\\result.log");
			print = new PrintWriter(new FileOutputStream(result));
			//设置tdriver对象
			map = ss.findSelenium(pro.getProductId());
			tdriver.setBrowser((String)map.get("browser"));
			tdriver.setUrl((String)map.get("address"));
			tdriver.setDriver(WebDriverFactory.getInstance((String)map.get("browser"),basePath)) ;
			//打开浏览器访问
			selenium = new MyWebDriverBackedSelenium(tdriver.getDriver(), tdriver.getUrl());
			selenium.open(tdriver.getUrl());
			tdriver.getDriver().manage().window().maximize(); //将浏览器设置为最大化的状态
			//保存本次运行信息-时间、浏览器、运行ip等
			execId = ss.saveSeleniumCheck(tdriver.getBrowser(),pro.getProductId());
			//开始init
			executeInitCase(pro.getProductId());
			//执行actions
			if(pro.getCaseId()==null&&pro.getSuitId()==null){
				loadSeleniumCase(null,pro.getProductId());
			}else if(pro.getSuitId()==null){
				TestCase cas = new TestCase();
				cas = ss.findTestCaseById(pro.getCaseId());
				executeCase(cas);
			}else{
				//如果测试集ignore，继续执行测试
				List<TestCase> cases = new ArrayList<TestCase>();
				cases = ss.findTestCaseBySuitId(Integer.parseInt(pro.getSuitId()));
				for(TestCase cas : cases){
					if(cas.getIsIgnore()==0){
						executeCase(cas);
					}
				}
			}
			
		}  catch (Exception e) {
			e.printStackTrace();
			print.println(CommonUtils.getTime("yyyy-MM-dd HH:mm:ss")+": 程序出现异常--"+e.getMessage());
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
		List<TestSuit> suits = ss.findSeleniumSuitByProductId(Integer.parseInt(productId));
		List<TestCase> cases = new ArrayList<TestCase>();
		for(TestSuit suit:suits){
			if(suit.getIsIgnore()==0){
				cases = ss.findTestCaseBySuitId(suit.getId());
				for(TestCase cas :cases){
					if(cas.getIsIgnore()==0){
						executeCase(cas);
					}
				}
			}
		}
	}

	@Override
	public void executeCase(TestCase tcase){
		JavascriptExecutor js= (JavascriptExecutor)tdriver.getDriver();
		//执行单个用例时发生异常，刷新主页
		try {
			if(!tdriver.getDriver().equals("htmlUnit")){
				photos = new StringBuffer("");
				//创建case路径
				photoBasePath = SNAPSHOTPATH+"\\"+CommonUtils.getTime("HHmmss")+"_"+tcase.getName();
				CommonUtils.mkDir(photoBasePath);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
			}
				List<TestStep> steps = ss.findTestStepByCaseId(tcase.getId());
				for(TestStep step:steps){
					if(step.getIsIgnore()==0){
						if(step.getType().equals("javascript")){
							js.executeScript("var e = document.createEvent('MouseEvents');e.initEvent('click', true, true);"+step.getDom());
							//若是输入document.getElementsByName("rvcd").options[1].selected = true;
							//WebElement.sendKeys ("The words you want to type");
						}else if(step.getType().equals("getElementByClassName")){
							js.executeScript(getElementByTagAndClassName(step.getDom().split("=")[0], step.getDom().split("=")[1], step.getValue().split(",")[0].split("=")[1], step.getValue().split(",")[1].split("=")[1]));
							
						}else{
							
							if(StringUtils.isEmpty(step.getValue())){
								selenium.getClass().getMethod(step.getType(),  String.class).invoke(selenium,step.getDom());
							}else{
								selenium.getClass().getMethod(step.getType(),  String.class,String.class).invoke(selenium,step.getDom(),step.getValue());
							}
						}
						
						if(step.getSleep()!=0){
							Thread.sleep(step.getSleep());
						}
						System.out.println("---------------------------"+step.getDom()+"--------------------------------------"+"\r"+selenium.getHtmlSource());
						if(!tdriver.getDriver().equals("htmlUnit")&&step.getSnapshot()==1){
							photos.append(";"+CommonUtils.snapshot(photoBasePath, tdriver.getDriver()));
						}
						if(step.getIscheck()==1){
							checkResult(selenium,tcase);
						}
					}
					
					
				}
		} catch (Exception e) {
			print.println(CommonUtils.getTime("yyyy-MM-dd HH:mm:ss")+": 用例--"+tcase.getName()+"发生异常:"+e.getMessage());
			print.flush();
			saveExeResult(selenium,tcase,0);
			updateZTCaseResult(tcase,false);
			e.printStackTrace();
		}finally{
			//单个用例执行完后刷新至主页
			selenium.open(tdriver.getUrl());
		}
				
			
	
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tearDown() {
		selenium.stop();
	}
	
	public void executeInitCase(String productId){
		try {
			if(!tdriver.getBrowser().equals("htmlUnit")){
				photos = new StringBuffer("");
				//创建case路径
				photoBasePath = SNAPSHOTPATH+"\\"+CommonUtils.getTime("HHmmss")+"_initcase";
				CommonUtils.mkDir(photoBasePath);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
			}
			List<TestStep> steps = ss.findNoActionTestSteps("init",productId);
			if(steps!=null&&steps.size()>0){
				for(TestStep step:steps){
					//不执行忽略的步骤
					if(step.getIsIgnore()==0){
						if(StringUtils.isEmpty(step.getValue())){
							selenium.getClass().getMethod(step.getType(),  String.class).invoke(selenium,step.getDom());
							System.out.println("---------------------------"+step.getDom()+"--------------------------------------"+"\r"+selenium.getHtmlSource());
						}else{
							selenium.getClass().getMethod(step.getType(),  String.class,String.class).invoke(selenium,step.getDom(),step.getValue());
							System.out.println("---------------------------"+step.getDom()+"--------------------------------------"+"\r"+selenium.getHtmlSource());
						}
						if(step.getSleep()!=0){
							Thread.sleep(step.getSleep());
						}
						if(!tdriver.getBrowser().equals("htmlUnit")&&step.getSnapshot()==1){
							photos.append(";"+CommonUtils.snapshot(photoBasePath,tdriver.getDriver()));
						}
					}
					
				}
				
			}
		} catch (Exception e) {
			print.println(CommonUtils.getTime("yyyy-MM-dd HH:mm:ss")+": 初始化脚本时发生异常:"+e.getMessage());
			print.flush();
			e.printStackTrace();
		}
	}
	public void checkResult(Selenium selenium,TestCase tcase ) throws InterruptedException{
		boolean checkResult = Waite.tillTextPresent(selenium, tcase.getExpect());
		print.println(CommonUtils.getTime("yyyy-MM-dd HH:mm:ss")+": "+tcase.getName()+"：预期结果'"+tcase.getExpect()+"'是否通过:——>"+checkResult);
		print.flush();
		saveExeResult(selenium,tcase,checkResult==true?1:0);
		updateZTCaseResult(tcase,checkResult);
	}
	public void saveExeResult(Selenium selenium,TestCase tcase,Integer res ){
		TestResult result = new TestResult(execId,tcase.getId(),
				res,
				tdriver.getDriver().equals("unitHtml")==true?"":photos.toString());
		ss.saveSeleniumModuleResult(result);
	}
	public void updateZTCaseResult(TestCase tcase,boolean res){
		ss.updateZTCaseResult(tcase.getId(),res);
	}
	
	//根据className获取元素
	public String getElementByTagAndClassName(String tagName,String className,String index,String act){
		StringBuffer script = new StringBuffer("var all = document.getElementsByTagName('"+tagName+"');");
		   script.append("var elements = new Array();var n = 0 ;");
		   script.append("for (var e = 0; e < all.length; e++) {");
			   script.append("if (all[e].className.indexOf('"+className+"')!=-1) {");
				   script.append("elements[n++] = all[e];");
				  // script.append("break;");
				   script.append("}");
			   script.append("}");
			 //  script.append("document.getElementsByName('starttime')[0].value=elements["+index+"].innerHTML; ");
			   
			   script.append("var e = document.createEvent('MouseEvents');");
			   script.append("e.initEvent('click', true, true);");
		   script.append("elements["+index+"]."+act+";");
		   return script.toString();
	}
	

}
