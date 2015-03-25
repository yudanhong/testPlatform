package net.keepsoft.selenium.entity;

import java.util.List;

public class SeleniumCase {
	private String name;
	private boolean ignore = false;
	private String expect;
	private List<SeleniumStep> steps;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isIgnore() {
		return ignore;
	}
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
	public List<SeleniumStep> getSteps() {
		return steps;
	}
	public void setSteps(List<SeleniumStep> steps) {
		this.steps = steps;
	}
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	

}
