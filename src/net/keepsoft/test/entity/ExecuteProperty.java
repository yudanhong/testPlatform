package net.keepsoft.test.entity;

public class ExecuteProperty {
	private String ProductId;
	private String suitId;
	private String caseId;
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getSuitId() {
		return suitId;
	}
	public void setSuitId(String suitId) {
		this.suitId = suitId;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public ExecuteProperty(String productId, String suitId, String caseId) {
		super();
		ProductId = productId;
		this.suitId = suitId;
		this.caseId = caseId;
	}
	

}
