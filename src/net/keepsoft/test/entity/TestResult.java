package net.keepsoft.test.entity;

public class TestResult {
	private Integer id;
	private Integer execId;
	private Integer caseId;
	private Integer result;
	private String photos;
	
	public TestResult(Integer execId, Integer caseId, Integer result,
			String photos) {
		super();
		this.execId = execId;
		this.caseId = caseId;
		this.result = result;
		this.photos = photos;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getExecId() {
		return execId;
	}
	public void setExecId(Integer execId) {
		this.execId = execId;
	}
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	

}
