package org.whois.model;

import java.io.Serializable;

public class WhoisQueryRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4732734031523711231L;
	private Integer wqrId;
	private String wqrDomain;
	private String wqrPrefix;
	private String wqrSuffix;
	private Long wqrUseTime;
	private Integer wqrStatus;
	private String wqrIpAddress;

	public Integer getWqrId() {
		return wqrId;
	}

	public void setWqrId(Integer wqrId) {
		this.wqrId = wqrId;
	}

	public String getWqrDomain() {
		return wqrDomain;
	}

	public void setWqrDomain(String wqrDomain) {
		this.wqrDomain = wqrDomain;
	}

	public String getWqrPrefix() {
		return wqrPrefix;
	}

	public void setWqrPrefix(String wqrPrefix) {
		this.wqrPrefix = wqrPrefix;
	}

	public String getWqrSuffix() {
		return wqrSuffix;
	}

	public void setWqrSuffix(String wqrSuffix) {
		this.wqrSuffix = wqrSuffix;
	}

	public Long getWqrUseTime() {
		return wqrUseTime;
	}

	public void setWqrUseTime(Long wqrUseTime) {
		this.wqrUseTime = wqrUseTime;
	}

	public Integer getWqrStatus() {
		return wqrStatus;
	}

	public void setWqrStatus(Integer wqrStatus) {
		this.wqrStatus = wqrStatus;
	}

	public String getWqrIpAddress() {
		return wqrIpAddress;
	}

	public void setWqrIpAddress(String wqrIpAddress) {
		this.wqrIpAddress = wqrIpAddress;
	}

}
