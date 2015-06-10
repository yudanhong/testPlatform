package net.keepsoft.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.keepsoft.commons.JSONUtils;
import net.keepsoft.entity.TreeNode;
import net.keepsoft.entity.ZTCase;
import net.keepsoft.entity.ZTProduct;
import net.keepsoft.selenium.core.SeleniumRunnable;
import net.keepsoft.selenium.core.SeleniumRunnerFactory;
import net.keepsoft.server.SeleniumServer;
import net.keepsoft.test.entity.ExecuteProperty;
import net.keepsoft.test.entity.TestCase;
import net.keepsoft.test.entity.TestStep;
import net.keepsoft.test.entity.TestSuit;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/selenium")
public class SeleniumController{
	@Resource
	private SeleniumServer ss;
	/**
	 * 查询所有的产品
	 * @return
	 */
	@RequestMapping(value="/findZTProduct")
	@ResponseBody
	public List<ZTProduct> findZTProduct(){
		return ss.findZTProduct();
	}
	/**
	 * 添加用例集
	 * @return
	 */
	@RequestMapping(value="/addTestSuit")
	@ResponseBody
	public Integer addTestSuit(TestSuit suit){
		return ss.addTestSuit(suit);
	}
	/**
	 * 根据id编辑用例集
	 * @return
	 */
	@RequestMapping(value="/editTestSuit")
	@ResponseBody
	public Integer editTestSuit(TestSuit suit){
		return ss.editTestSuit(suit);
	}
	/**
	 * 根据productId查询action用例集
	 * @return
	 */
	@RequestMapping(value="/findSeleniumSuitByProductId")
	@ResponseBody
	public List<TestSuit> findSeleniumSuitByProductId(Integer productId){
		return ss.findSeleniumSuitByProductId(productId);
	}
	/**
	 * 根据id删除用例集
	 * @return
	 */
	@RequestMapping(value="/deleteSuit")
	@ResponseBody
	public Integer deleteSuit(Integer id){
		return ss.deleteSuit(id);
	}
	/**
	 * 根据id删除用例
	 * @return
	 */
	@RequestMapping(value="/deleteCase")
	@ResponseBody
	public Integer deleteCase(Integer id){
		return ss.deleteCase(id);
	}
	/**
	 * 根据id获取用例集
	 * @return
	 */
	@RequestMapping(value="/getTestSuitById")
	@ResponseBody
	public TestSuit getTestSuitById(Integer id){
		return ss.getTestSuitById(id);
	}
	/**
	 * 为用例集添加case
	 * @return
	 */
	@RequestMapping(value="/addTestSCase")
	@ResponseBody
	public Integer addTestSCase(TestCase cas){
		int flag = 1;
		Integer caseId = ss.addTestSCase(cas);
		if(caseId!=0&&!cas.getCopyFrom().equals("")){
			flag = ss.copyStepsByCaseId(Integer.parseInt(cas.getCopyFrom()),caseId);
		}
		return flag;
	}
	/**
	 * 编辑用例信息
	 * @param cas
	 * @return
	 */
	@RequestMapping(value="/editTestSCase")
	@ResponseBody
	public Integer editTestSCase(TestCase cas){
		return ss.editTestSCase(cas);
	}
	/**
	 * 根据suitId查询所有case
	 * @return
	 */
	@RequestMapping(value="/findTestCaseBySuitId")
	@ResponseBody
	public List<TestCase> findTestCaseBySuitId(Integer suitId){
		return ss.findTestCaseBySuitId(suitId);
	}
	/**
	 * 通用接口
	 * 根据key和表名删除记录
	 * @return
	 */
	@RequestMapping(value="/deleteRecordByTable")
	@ResponseBody
	public Integer deleteRecordByTable(String table ,Integer key){
		return ss.deleteRecordByTable(table,key);
	}
	/**
	 * 根据caseId查询所有step
	 * @return
	 */
	@RequestMapping(value="/findTestStepByCaseId")
	@ResponseBody
	public List<TestStep> findTestStepByCaseId(Integer caseId){
		return ss.findTestStepByCaseId(caseId);
	}
	/**
	 * 保存用例步骤
	 * @param caseId 用例编号
	 * @return
	 */
	@RequestMapping(value="/saveStepsByCaseId")
	@ResponseBody
	public Integer saveStepsByCaseId(String steps,String caseId){
		List list = new ArrayList();
		list = JSONUtils.fromJSON(steps,new ArrayList().getClass());
		return ss.saveStepsByCaseId(list, caseId);
	}
	
	/**
	 * 获取初始化脚本步骤
	 * @return
	 */
	@RequestMapping(value="/findNoActionTestSteps")
	@ResponseBody
	public List<TestStep> findNoActionTestSteps(String stage,String productId){
		return ss.findNoActionTestSteps(stage,productId);
	}
	/**
	 * 
	 * @param steps
	 * @param caseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveNoActionSteps")
	@ResponseBody
	public Integer saveNoActionSteps(String steps,String stage,String productId){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list = JSONUtils.fromJSON(steps,new ArrayList<Map<String,String>>().getClass());
		return ss.saveNoActionSteps(list,stage,productId);
	}
	/**
	 * 开始执行selenium测试
	 * @param address
	 * @param driver
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/startSelenium")
	@ResponseBody
	public Integer startSelenium(HttpServletRequest request,String address,String driver,String productId){
		System.out.println(request.getSession().getServletContext().getRealPath("")+"\\");
		int flag = ss.saveSelenium(address,driver,productId);
		if(flag==0){
		return 0;
		}else{
			//开始后台执行selenium自动化
			String path = request.getSession().getServletContext().getRealPath("")+"\\";
			//从数据库获取case进行执行： mode=1
			SeleniumRunnable selenium = SeleniumRunnerFactory.getInstance(1);
			selenium.startUp(ss,path,new ExecuteProperty(productId,null,null));
		}
		return flag;
	}
	@RequestMapping(value="/saveSelenium")
	@ResponseBody
	public Integer saveSelenium(HttpServletRequest request,String address,String driver,String productId){
		System.out.println(request.getSession().getServletContext().getRealPath("")+"\\");
		int flag = ss.saveSelenium(address,driver,productId);
		return flag;
	}
	/**
	 * 执行单个用例
	 * @param caseId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/executeCase")
	@ResponseBody
	public Integer executeCase(HttpServletRequest request,String caseId,String productId){
		//开始后台执行selenium自动化
		String path = request.getSession().getServletContext().getRealPath("")+"\\";
		//从数据库获取case进行执行： mode=1
		SeleniumRunnable selenium = SeleniumRunnerFactory.getInstance(1);
		selenium.startUp(ss,path,new ExecuteProperty(productId,null,caseId));
		return 1;
	}
	@RequestMapping(value="/executeSuit")
	@ResponseBody
	public Integer executeSuit(HttpServletRequest request,String suitId,String productId){
		//开始后台执行selenium自动化
		String path = request.getSession().getServletContext().getRealPath("")+"\\";
		//从数据库获取case进行执行： mode=1
		SeleniumRunnable selenium = SeleniumRunnerFactory.getInstance(1);
		selenium.startUp(ss,path,new ExecuteProperty(productId,suitId,null));
		return 1;
	}
	/**
	 * 查找selenium
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/findSelenium")
	@ResponseBody
	public Map<String,Object> findSelenium(String productId){
		Map<String,Object> map = new HashMap<String,Object>();
		map = ss.findSelenium(productId);
		return map;
	}
	/**
	 * 根据id查询case
	 * @param caseId
	 * @return
	 */
	@RequestMapping(value="/findTestCaseById")
	@ResponseBody
	public TestCase findTestCaseById(String caseId){
		return ss.findTestCaseById(caseId);
	}
	/**
	 * 根据id更正测试结果
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/correctResult")
	@ResponseBody
	public Integer correctResult(String id,Integer result){
		return ss.correctResult(id,result);
	}
	/**
	 * 根据moduleId获取禅道用例
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/findZTCasebyModuleId")
	@ResponseBody
	public List<ZTCase> findZTCasebyModuleId(String moduleId){
		return ss.findZTCasebyModuleId(moduleId);
	}
	/**
	 * 根据productId查询禅道用例模块
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/findZTModuleByProductId")
	@ResponseBody
	public List<TreeNode> findZTModuleByProductId(String productId){
		List<TreeNode> list = ss.findZTModuleByProductId(productId);
		list.get(0).setOpen(true);
		return list;
	}
	/**
	 * 根据productId查询selenium用例
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/findTestSuitTree")
	@ResponseBody
	public List<TreeNode> findTestSuitTree(String productId){
		List<TreeNode> list = ss.findTestSuitTree(productId);
		if(list!=null&&list.size()!=0){
			list.get(0).setOpen(true);
		}
		return list;
	}
	/**
	 * 根据suitId查询testCase列表
	 * @param suitId
	 * @return
	 */
	@RequestMapping(value="/findCKCaseBySuitId")
	@ResponseBody
	public List<TestCase> findCKCaseBySuitId(String suitId){
		List<TestCase> list = ss.findCKCaseBySuitId(suitId);
		return list;
	}
	/**
	 * 添加关联
	 * @param ztcaseId
	 * @param ckcaseId
	 * @return
	 */
	@RequestMapping(value="/addRelate")
	@ResponseBody
	public Integer addRelate(String ztcaseId,String ckcaseId){
		Integer flag = ss.addRelate(ztcaseId,ckcaseId);
		return flag;
	}
	/**
	 * 更新关联
	 * @param relateId
	 * @param ckcaseId
	 * @return
	 */
	@RequestMapping(value="/updateRelate")
	@ResponseBody
	public Integer updateRelate(String relateId,String ckcaseId){
		Integer flag = ss.updateRelate(relateId,ckcaseId);
		return flag;
	}
	@RequestMapping(value="/clearRelate")
	@ResponseBody
	public Integer clearRelate(String relateId){
		return ss.clearRelate(relateId);
	}
	/**
	 * 
	 * @param relateId
	 * @return
	 */
	@RequestMapping(value="/exportScript")
	@ResponseBody
	public Integer exportScript(String productId,HttpServletRequest request){
		Map<String, Object> seleniumPropertyies = ss.findSelenium(productId);
		
		Document document = DocumentHelper.createDocument();
		//根节点
		Element root =  document.addElement("selenium");
		//设置config
		Element config = root.addElement("config");
		Element baseUrl = config.addElement("baseUrl");
		baseUrl.addText((String) seleniumPropertyies.get("address"));
		Element driver = config.addElement("driver");
		driver.addText((String) seleniumPropertyies.get("browser"));
		//写入init
		
		return 0;//ss.exportScript(productId);
	}
}
