package net.keepsoft.server;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.keepsoft.dao.BaseDao;
import net.keepsoft.entity.Function;
import net.keepsoft.entity.FunctionCheck;
import net.keepsoft.entity.FunctionPoint;
import net.keepsoft.entity.Model;
import net.keepsoft.entity.Point;
import net.keepsoft.entity.PointWithCheckFlag;
import net.keepsoft.entity.Product;
import net.keepsoft.entity.Project;
import net.keepsoft.entity.SeleniumCheck;
import net.keepsoft.entity.SeleniumResult;
import net.keepsoft.entity.Version;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

@Service
public class BaseServer {
	@Resource
	private BaseDao dao;
	
	public List<Model> findModel(){
		List<Model> result = new ArrayList<Model>();
		result = dao.queryForBeanList("select * from model", Model.class, null);
		return result;
	}
	public List<Point> findPointByModelId(String id){
		List<Point> result = new ArrayList<Point>();
		result = dao.queryForBeanList("select a.id,a.title,a.remark,b.name as model_name from point a,model b where a.modelId = b.id and modelId = ?", Point.class, Integer.parseInt(id));
		return result;
	}
	
	public Point findPointById(String id){
		Point result = new Point();
		result = dao.queryForBean("select * from point where id = ?", Point.class, Integer.parseInt(id));
		return result;
	}
	
	//新增模板
	public int saveModel(String name,String remark){
		int i = 1;
		try {
			dao.update("insert into model(name,remark) values(?,?)", name,remark);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	//新增检查点
		public int savePoint(String name,String remark,String modelId){
			int i = 1;
			try {
				dao.update("insert into point(title,remark,modelId) values(?,?,?)", name,remark,Integer.parseInt(modelId));
			} catch (Exception e) {
				i = 0;
			}
			return i;
		}
	//更新模板
	public int updateModel(String name,String remark,String id){
		int i = 1;
		try {
			dao.update("update model set name=?,remark=? where id=?", name,remark,Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public int updatePoint(String title,String remark,String modelId,String id){
		int i = 1;
		try {
			dao.update("update point set title=?,remark=? ,modelId=? where id=?", title,remark,modelId,Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	
	//根据id查找数据
	public Model findModelById(String id){
		return dao.queryForBean("select * from model where id = ?", Model.class, Integer.parseInt(id));
	}
	
	//删除模板
		public int delModel(String id){
			int i = 1;
			try {
				dao.update("delete from model where id = ?",Integer.parseInt(id));
			} catch (Exception e) {
				i = 0;
			}
			return i;
		}
	//删除模板
	public int delByTableInfo(String table,String id){
		int i = 1;
		try {
			dao.update("delete from "+table+" where id = ?",Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	/**********project*******************/
	public int updateProject(String name, String remark, String id) {
		int i = 1;
		try {
			dao.update("update project set name=?,remark=? where id=?", name,remark,Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public int saveProject(String name, String remark) {
		int i = 1;
		try {
			dao.update("insert into project(name,remark) values(?,?)", name,remark);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public List<Project> findProject() {
		List<Project> result = new ArrayList<Project>();
		result = dao.queryForBeanList("SELECT * from zt_project where `status`<>'done' and deleted='0'", Project.class, null);
		return result;
	}
	public List<Product> findProduct() {
		List<Product> result = new ArrayList<Product>();
		result = dao.queryForBeanList("SELECT * from zt_product where `status` <> 'closed' and deleted='0'", Product.class, null);
		return result;
	}
	public Project findProjectById(String id) {
		Project result = new Project();
		result = dao.queryForBean("select * from project where id = ?", Project.class, Integer.parseInt(id));
		return result;
	}
	
	/************************version*******************/
	public List<Version> findVersionByProjectId(String projectId) {
		List<Version> result = new ArrayList<Version>();
		result = dao.queryForBeanList("select * from version where projectId = ?", Version.class, Integer.parseInt(projectId));
		return result;
	}
	public int saveVersion(String name, String remark, String projectId) {
		int key = 0;
		try {
			key = (Integer) dao.updateAndReturnPrimaryKey("insert into version(name,remark,projectId) values(?,?,?)", name,remark,Integer.parseInt(projectId));
		} catch (Exception e) {
			key = 0;
		}
		return key;
	}
	public int updateVersion(String name, String remark, String projectId,
			String id) {
		int i = 1;
		try {
			dao.update("update version set name=?,remark=?,projectId=? where id=?", name,remark,Integer.parseInt(projectId),Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public Version findVersionById(String id) {
		Version result = new Version();
		result = dao.queryForBean("select * from version where id = ?", Version.class, Integer.parseInt(id));
		return result;
	}
	/************************functionPoint*******************/
	public List<PointWithCheckFlag> findFunctionByVersionId(String versionId) {
		//flag：0-未通过 1-通过 2-其他
		List<PointWithCheckFlag> result = new ArrayList<PointWithCheckFlag>();
		result = dao.queryForBeanList("select a.id,a.name as title,a.remark," +
				"case  WHEN sum(flag)<count(b.id) then 0 when sum(flag)=count(b.id) then 1 else 2 end as flag " +
				"from function a left join functionpoint b on a.id = b.functionId " +
				"where versionId = ? group by a.id", PointWithCheckFlag.class, Integer.parseInt(versionId));
		return result;
	}
	public Function findFunctionById(String id) {
		Function result = new Function();
		result = dao.queryForBean("select * from function where id = ?", Function.class, Integer.parseInt(id));
		return result;
	}
	public List<FunctionPoint> findFunctionPointByFunctionId(String functionId) {
		List<FunctionPoint> result = new ArrayList<FunctionPoint>();
		result = dao.queryForBeanList("select * from functionpoint where functionId = ?", FunctionPoint.class, Integer.parseInt(functionId));
		return result;
	}
	public Integer saveFunction(String name, String remark, String versionId) {
		Integer i = null;
		try {
			i = (Integer) dao.updateAndReturnPrimaryKey("insert into function(name,remark,versionId) values(?,?,?)", name,remark,Integer.parseInt(versionId));
		} catch (Exception e) {
//			i = 0;
		}
		return i;
	}
	public int updateFunction(String name, String remark, String id) {
		int i = 1;
		try {
			dao.update("update function set name=? where id=?", name,Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public List<Point> findPoint() {
		List<Point> result = new ArrayList<Point>();
		result = dao.queryForBeanList("select a.id,title,a.remark,name as model_name  from point a left join model b on a.modelId = b.id  order by b.id ", Point.class, null);
		return result;
	}
	public int saveFunctionPoint(final String functionId, final String[] pointIds) {
		int result = 0;
		try {
			// TODO Auto-generated method stub
			dao.batchUpdate("insert into functionpoint(functionId,pointId) values(?,?)", new BatchPreparedStatementSetter(){

				public int getBatchSize() {
					// TODO Auto-generated method stub
					return pointIds.length;
				}

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setInt(1, Integer.parseInt(functionId));
					ps.setInt(2, Integer.parseInt(pointIds[i]));
				}
				
			});
			result =1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public List<PointWithCheckFlag> findPointCheckFlag(String functionId) {
		List<PointWithCheckFlag> result = new ArrayList<PointWithCheckFlag>();
		result = dao.queryForBeanList("select a.id,a.title,a.remark,if(b.id<>'',1,0) as flag,(select name from function where id = ?) as function_name from point a left join  functionpoint b on a.id = b.pointId and b.functionId = ?", PointWithCheckFlag.class, Integer.parseInt(functionId),Integer.parseInt(functionId));
		return result;
	}
	public int delFunctionPointByFunctionId(String functionId) {
		int i = 1;
		try {
			dao.update("delete from functionpoint where functionId = ?",Integer.parseInt(functionId));
		} catch (Exception e) {
			i = 0;
		}
		return i;
		
	}
	public List<PointWithCheckFlag> findcheckPointByFunctionId(String functionId) {
		List<PointWithCheckFlag> result = new ArrayList<PointWithCheckFlag>();
		result = dao.queryForBeanList("select a.id,b.title,b.remark,a.flag from functionpoint a,point b where a.pointId = b.id and a.functionId = ?", PointWithCheckFlag.class, Integer.parseInt(functionId));
		return result;
	}
	public int setChecked(String functionPointId,String flag) {
		int i = 1;
		try {
			dao.update("update functionpoint set flag=? where id=?",Integer.parseInt(flag),Integer.parseInt(functionPointId));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public int copyFuntionByVersionId(Integer versionId,String copyVertionId) {
		int i = 1;
		try {
			dao.update("insert into function(name,remark,versionId) select name,remark,? from function where versionId=?",versionId,Integer.parseInt(copyVertionId));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public int delCheckFunctionPointByPointId(String functionId, String pointId) {
		int i = 1;
		try {
			dao.update("delete from functionpoint where functionId=? and pointId=?",Integer.parseInt(functionId),Integer.parseInt(pointId));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public void delFunctionPoint(final String functionId, final String[] del) {
		dao.batchUpdate("delete from functionpoint where functionId = ? and pointId = ? ", new BatchPreparedStatementSetter(){

			public int getBatchSize() {
				// TODO Auto-generated method stub
				return del.length;
			}

			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, Integer.parseInt(functionId));
				ps.setInt(2, Integer.parseInt(del[i]));
			}
			
		});
		
	}
	public List<FunctionCheck> findFunctionCheckList(String versionId) {
		List<FunctionCheck> result = new ArrayList<FunctionCheck>();
		result = dao.queryForBeanList("select * from functioncheck where versionId = ?", FunctionCheck.class, Integer.parseInt(versionId));
		return result;
	}
	public int saveFunctionCheck(String versionId, String name, String remark) {
		int i = 1;
		try {
			dao.update("insert into functioncheck(versionId,name,remark) values(?,?,?)", Integer.parseInt(versionId),name,remark);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public int updateFunctionCheck(String id, String name, String remark) {
		int i = 1;
		try {
			dao.update("update functioncheck set name=?,remark=? where id=?", name,remark,Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public int setFunctionCheckFlag(String id, String flag) {
		int i = 1;
		try {
			dao.update("update functioncheck set flag=? where id=?", Integer.parseInt(flag),Integer.parseInt(id));
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	public FunctionCheck findFunctionCheckById(String id) {
		FunctionCheck result = new FunctionCheck();
		result = dao.queryForBean("select * from functioncheck where id = ?", FunctionCheck.class, Integer.parseInt(id));
		return result;
	}
	public List<SeleniumCheck> findSeleniumCheck() {
		List<SeleniumCheck> result = new ArrayList<SeleniumCheck>();
		result = dao.queryForBeanList("select * from seleniumcheck", SeleniumCheck.class, null);
		return result;
	}
	public List<SeleniumResult> findSeleniumResultByCheckId(String checkId) {
		List<SeleniumResult> result = new ArrayList<SeleniumResult>();
		result = dao.queryForBeanList("select * from seleniumresult where checkId = ?", SeleniumResult.class, Integer.parseInt(checkId));
		return result;
	}
	
	public  BaseDao getBaseDao(){
		return dao;
	}
}
