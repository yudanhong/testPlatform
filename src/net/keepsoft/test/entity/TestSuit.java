package net.keepsoft.test.entity;

public class TestSuit {
	private Integer id;
	private String name;
	private Integer isIgnore;//1-忽略， 0-执行
	private Integer productId;
	private Integer result;//用例集测试结果，由子用例的结果确定
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
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getIsIgnore() {
		return isIgnore;
	}
	public void setIsIgnore(Integer isIgnore) {
		this.isIgnore = isIgnore;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	
	
}
