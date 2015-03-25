package net.keepsoft.test.entity;


public class TestCase {
	private Integer id;
	private String name;
	private Integer isIgnore;
	private String expect;
	private Integer suitId;
	private Integer ord;
	private Integer result;//case的最近一次执行结果： 0-失败 1-成功
	private String photos;//截图文件
	private String copyFrom;//添加时所用，表示从其他case中复制步骤
	private String exetime;//执行时间
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsIgnore() {
		return isIgnore;
	}
	public void setIsIgnore(Integer isIgnore) {
		this.isIgnore = isIgnore;
	}
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	public Integer getSuitId() {
		return suitId;
	}
	public void setSuitId(Integer suitId) {
		this.suitId = suitId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrd() {
		return ord;
	}
	public void setOrd(Integer ord) {
		this.ord = ord;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public String getCopyFrom() {
		return copyFrom;
	}
	public void setCopyFrom(String copyFrom) {
		this.copyFrom = copyFrom;
	}
	public String getExetime() {
		return exetime;
	}
	public void setExetime(String exetime) {
		this.exetime = exetime;
	}
	

}
