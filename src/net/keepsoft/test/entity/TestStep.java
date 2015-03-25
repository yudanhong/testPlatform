package net.keepsoft.test.entity;

public class TestStep {
	private Integer id;
	private String name;
	private String type;
	private String dom;
	private String value;
	private long sleep;//毫秒
	private Integer ischeck;//检查case输出，默认0，不检查
	private Integer snapshot;//截图，默认0，不截图
	private Integer ord;//step顺序
	private Integer caseId;
	private Integer isIgnore;//是否忽略次步骤：默认0-不忽略  1-忽略
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDom() {
		return dom;
	}
	public void setDom(String dom) {
		this.dom = dom;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getSleep() {
		return sleep;
	}
	public void setSleep(long sleep) {
		this.sleep = sleep;
	}
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public Integer getOrd() {
		return ord;
	}
	public void setOrd(Integer ord) {
		this.ord = ord;
	}
	public Integer getIscheck() {
		return ischeck;
	}
	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}
	public Integer getSnapshot() {
		return snapshot;
	}
	public void setSnapshot(Integer snapshot) {
		this.snapshot = snapshot;
	}
	public Integer getIsIgnore() {
		return isIgnore;
	}
	public void setIsIgnore(Integer isIgnore) {
		this.isIgnore = isIgnore;
	}
	

}
