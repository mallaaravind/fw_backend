package com.aravind.projects.fwbackend;

public class Key {
	
	private String value;
	private Integer ttl;
	private Long createTimeInMillis;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getTtl() {
		return ttl;
	}
	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}
	public Long getCreateTimeInMillis() {
		return createTimeInMillis;
	}
	public void setCreateTimeInMillis(Long createTimeInMillis) {
		this.createTimeInMillis = createTimeInMillis;
	}
	
}
