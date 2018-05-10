/**
 * Project Name:WebMagic
 * File Name:MagicProcessor.java
 * Package Name:com.gisquest.webmagic.entity
 * Date:2017�?3�?1�?
 * Copyright (c) 2017, Yangs All Rights Reserved.
 * Current Author:  yangs.
*/
package org.pine.webmagic.entity;

import java.util.List;
/**
 * 
 * @author yangs
 *
 */
public class MagicProcessor {
	private String guid;
	private String pguid;
	private int retryTimes;
	private String charset;
	private int timeOut;
	private int cycleRetryTimes;
	private int sleepTime;
	private int retrySleepTime;
	private List<MagicEntrySet> addHeader;
	private List<MagicEntrySet> httpProxy;
	private MagicSelectable selects;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPguid() {
		return pguid;
	}

	public void setPguid(String pguid) {
		this.pguid = pguid;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getCycleRetryTimes() {
		return cycleRetryTimes;
	}

	public void setCycleRetryTimes(int cycleRetryTimes) {
		this.cycleRetryTimes = cycleRetryTimes;
	}

	public List<MagicEntrySet> getAddHeader() {
		return addHeader;
	}

	public void setAddHeader(List<MagicEntrySet> addHeader) {
		this.addHeader = addHeader;
	}

	public List<MagicEntrySet> getHttpProxy() {
		return httpProxy;
	}

	public void setHttpProxy(List<MagicEntrySet> httpProxy) {
		this.httpProxy = httpProxy;
	}

	public MagicSelectable getSelects() {
		return selects;
	}

	public void setSelects(MagicSelectable selects) {
		this.selects = selects;
	}
	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getRetrySleepTime() {
		return retrySleepTime;
	}

	public void setRetrySleepTime(int retrySleepTime) {
		this.retrySleepTime = retrySleepTime;
	}
}
