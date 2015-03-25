package net.keepsoft.entity;

public class ZTCase {
	private Integer id;
	private String title;
	private String module;
	private Integer relatecaseId;
	private String relatecaseName;
	private String lastRunResult;
	private String lastRunDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getRelatecaseId() {
		return relatecaseId;
	}
	public void setRelatecaseId(Integer relatecaseId) {
		this.relatecaseId = relatecaseId;
	}
	public String getRelatecaseName() {
		return relatecaseName;
	}
	public void setRelatecaseName(String relatecaseName) {
		this.relatecaseName = relatecaseName;
	}
	public String getLastRunResult() {
		return lastRunResult;
	}
	public void setLastRunResult(String lastRunResult) {
		this.lastRunResult = lastRunResult;
	}
	public String getLastRunDate() {
		return lastRunDate;
	}
	public void setLastRunDate(String lastRunDate) {
		this.lastRunDate = lastRunDate;
	}
	

}
