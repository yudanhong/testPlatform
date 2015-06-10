package net.keepsoft.server;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.keepsoft.dao.BaseDao;
import net.keepsoft.entity.RelateCase;
import net.keepsoft.entity.TreeNode;
import net.keepsoft.entity.ZTCase;
import net.keepsoft.entity.ZTProduct;
import net.keepsoft.test.entity.TestCase;
import net.keepsoft.test.entity.TestResult;
import net.keepsoft.test.entity.TestStep;
import net.keepsoft.test.entity.TestSuit;
import net.keepsoft.utils.CommonUtils;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;



@Service
public class SeleniumServer{
	@Resource
	private BaseDao dao ;
	public List<ZTProduct> findZTProduct(){
		List<ZTProduct> result = new ArrayList<ZTProduct>();
		result = dao.queryForBeanList("select * from zt_product where status<>'closed' and deleted = '0'", ZTProduct.class, null);
		return result;
	}
	public Integer addTestSuit(TestSuit suit) {
		int flag = 1;
		try {
			dao.update("insert into ck_suit(name,isIgnore,productId) values (?,?,?)", suit.getName(),suit.getIsIgnore(),suit.getProductId());
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public List<TestSuit> findSeleniumSuitByProductId(Integer productId) {
		List<TestSuit> result = new ArrayList<TestSuit>();
		String sql = "select max(id) id,name , max(isIgnore) isIgnore,max(productId) productId,case  when count(*)-sum(result=1)=0 then 1 else 0 end result from ("
				+" select s.*,c.result from ck_suit s left join (select * from ck_case where deleted=0 ) c on  s.id = c.suitId where s.productId = ?  and s.deleted =0 "
				+" ) t group by name";
		result = dao.queryForBeanList(sql, TestSuit.class, productId);
		return result;
	}
	public Integer deleteSuit(Integer id) {
		int flag = 1;
		try {
			dao.update("update ck_suit set deleted=1 where id = ? ", id);
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public Integer deleteCase(Integer id) {
		int flag = 1;
		try {
			dao.update("update ck_case set deleted=1 where id = ? ", id);
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public TestSuit getTestSuitById(Integer id) {
		TestSuit suit = dao.queryForBean("select * from ck_suit where id = ?", TestSuit.class, id);
		return suit;
	}
	public Integer editTestSuit(TestSuit suit) {
		int flag = 1;
		try {
			dao.update("update ck_suit set name = ?,isIgnore = ? where id = ? ", suit.getName(),suit.getIsIgnore(),suit.getId());
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public Integer addTestSCase(TestCase cas) {
		Integer flag = 0;
		try {
			flag = (Integer) dao.updateAndReturnPrimaryKey("insert into ck_case(name,isIgnore,suitId,expect,ord) values (?,?,?,?,?)", 
					cas.getName(),
					cas.getIsIgnore(),
					cas.getSuitId(),
					cas.getExpect(),
					cas.getOrd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public List<TestCase> findTestCaseBySuitId(Integer suitId) {
		List<TestCase> result = new ArrayList<TestCase>();
		result = dao.queryForBeanList("select * from ck_case where suitId = ? and deleted = 0 order by ord", TestCase.class, suitId);
		return result;
	}
	public Integer deleteRecordByTable(String table, Integer key) {
		int flag = 1;
		try {
			dao.update("delete from "+table+" where id = ? ", key);
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public List<TestStep> findTestStepByCaseId(Integer caseId) {
		List<TestStep> result = new ArrayList<TestStep>();
		try {
			result = dao.queryForBeanList("select * from ck_step where caseId = ? order by ord", TestStep.class, caseId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Integer saveStepsByCaseId( final  List<Map<String,String>> list,final String caseId) {
		int flag = 1;
		try {
			dao.update("delete from ck_step where caseId = ? ", caseId);
			dao.batchUpdate("insert into ck_step(name,type,dom,value,sleep,caseId,ord,ischeck,snapshot,isIgnore) values(?,?,?,?,?,?,?,?,?,?)", 
					new BatchPreparedStatementSetter(){
					
						@Override
						public int getBatchSize() {
							return list.size();
						}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							ps.setString(1, list.get(i).get("name"));
							ps.setString(2, list.get(i).get("type"));
							ps.setString(3, list.get(i).get("dom"));
							ps.setString(4, list.get(i).get("value"));
							ps.setLong(5, StringUtils.isEmpty(list.get(i).get("sleep"))?0:Long.parseLong(list.get(i).get("sleep")));
							ps.setInt(6, Integer.parseInt(caseId));
							ps.setInt(7, i+1);
							ps.setInt(8, Integer.parseInt(list.get(i).get("ischeck")));
							ps.setInt(9, Integer.parseInt(list.get(i).get("snapshot")));
							ps.setInt(10, Integer.parseInt(list.get(i).get("isIgnore")));
						}
				
			});
			
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public List<TestStep> findNoActionTestSteps(String stage,String productId) {
		List<TestStep> result = new ArrayList<TestStep>();
		result = dao.queryForBeanList("select * from ck_step where stage = ? and productId=? order by ord", TestStep.class, stage,productId);
		return result;
	}
	public Integer saveNoActionSteps(final List<Map<String,String>> list,final String stage,final String productId) {
		int flag = 1;
		try {
			dao.update("delete from ck_step where stage = ? and productId = ? ",stage,productId);
			dao.batchUpdate("insert into ck_step(name,type,dom,value,sleep,ord,stage,snapshot,productId) values(?,?,?,?,?,?,?,?,?)", 
					new BatchPreparedStatementSetter(){
					
						@Override
						public int getBatchSize() {
							return list.size();
						}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							ps.setString(1, list.get(i).get("name"));
							ps.setString(2, list.get(i).get("type"));
							ps.setString(3, list.get(i).get("dom"));
							ps.setString(4, list.get(i).get("value"));
							ps.setLong(5, StringUtils.isEmpty(list.get(i).get("sleep"))?0:Long.parseLong(list.get(i).get("sleep")));
							ps.setInt(6, i+1);
							ps.setString(7, stage);
							ps.setInt(8, Integer.parseInt(list.get(i).get("snapshot")));
							ps.setInt(9, Integer.parseInt(productId));
						}
				
			});
			
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public Integer saveSelenium(final String address, final String driver, final String productId) {
		int flag = 1;
		try {
			dao.getJdbcTemplate().execute("call saveExecute('"+productId+"','"+address+"','"+driver+"')");
		} catch (DataAccessException e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public Integer saveSeleniumCheck(String browser,String productId) {
		try {
			return (Integer) dao.updateAndReturnPrimaryKey("insert into ck_execute(exectime,ip,browser,productId) values(?,?,?,?)",
					CommonUtils.getTime("yyyy-MM-dd HH:mm:ss"),CommonUtils.getLocalIpAddress(),browser,productId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	public Map<String, Object> findSelenium(String productId) {
		Map<String, Object> result = new HashMap<String,Object>();
		result = dao.queryForMap("select * from ck_selenium where productId = ?", productId);
		return result;
	}
	public void saveSeleniumModuleResult(TestResult result) {
		dao.update("update ck_case set photos=?,result=?,exetime=now() where id=?", result.getPhotos(),result.getResult(),result.getCaseId()); 
	}
	public TestCase findTestCaseById(String caseId) {
		TestCase result = new TestCase();
		result = dao.queryForBean("select * from ck_case where id=?", TestCase.class, caseId);
		return result;
	}
	public Integer editTestSCase(TestCase cas) {
		int flag = 1;
		try {
			dao.update("update ck_case set name = ?,isIgnore = ?,suitId=?,expect=?,ord=? where id = ? ", 
					cas.getName(),
					cas.getIsIgnore(),
					cas.getSuitId(),
					cas.getExpect(),
					cas.getOrd(),
					cas.getId());
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	//通过caseId复制step
	public int copyStepsByCaseId(Integer fromCase,Integer caseId) {
		int flag = 1;
		try {
			dao.update("insert into ck_step(name,type,dom,value,sleep,ischeck,snapshot,ord,caseId,stage,isIgnore) " +
					"select name,type,dom,value,sleep,ischeck,snapshot,ord,?,stage,isIgnore from ck_step where caseId=?",caseId,fromCase);
		} catch (DataAccessException e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public Integer correctResult(String id, Integer result) {
		int flag = 1;
		try {
			dao.update("update ck_case set result = ? where id = ? ",result,id);
		} catch (DataAccessException e) {
			flag = 0;
			e.printStackTrace();
		}
		return flag;
	}
	public List<ZTCase> findZTCasebyModuleId(String moduleId) {
		List<ZTCase> result = new ArrayList<ZTCase>();
		result = dao.queryForBeanList("select a.*,b.name as relatecaseName from (" +
				"select a.id,a.title,a.module,a.lastRunDate,a.lastRunResult,b.id as relatecaseId,ckcaseId from zt_case a left join ck_relatecase b on a.id = b.ztcaseId " +
				"where a.deleted='0' and a.module=?" +
				")a left join ck_case b on a.ckcaseId = b.id", ZTCase.class, moduleId);
		return result;
	}
	public List<TreeNode> findZTModuleByProductId(String productId) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		result = dao.queryForBeanList("select id,parent as pId,name from zt_module  where root = ? ", TreeNode.class, productId);
		return result;
	}
	public List<TreeNode> findTestSuitTree(String productId) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		result = dao.queryForBeanList("select * from ck_suit where productId=? and deleted = 0", TreeNode.class, productId);
		return result;
	}
	public List<TestCase> findCKCaseBySuitId(String suitId) {
		List<TestCase> result = new ArrayList<TestCase>();
		result = dao.queryForBeanList("select * from ck_case where suitId=?", TestCase.class, suitId);
		return result;
	}
	public Integer addRelate(String ztcaseId, String ckcaseId) {
		Integer result = 0;
		try {
			result  = (Integer) dao.updateAndReturnPrimaryKey("insert into ck_relatecase(ztcaseId,ckcaseId) values(?,?) ",ztcaseId,ckcaseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Integer updateRelate(String ztcaseId, String ckcaseId) {
		int result = 0;
		try {
			result  = dao.update("update ck_relatecase set ckcaseId = ? where id = ? ",ckcaseId,ztcaseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Integer clearRelate(String relateId) {
		int result = 0;
		try {
			result  = dao.update("delete from ck_relatecase where id = ? ",relateId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void updateZTCaseResult(Integer ckcaseId, boolean res) {
		RelateCase result = null;
		try {
			result  = dao.queryForBean("select * from ck_relatecase where ckcaseId = ? ",RelateCase.class,ckcaseId);
			if(result!=null){
				dao.update("update zt_case set lastRunDate = now(),lastRunResult = ? where id = ?", (res==true?"pass":"fail"),result.getZtcaseId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Integer exportScript(String productId) {
		// TODO Auto-generated method stub
		return null;
	}
}
