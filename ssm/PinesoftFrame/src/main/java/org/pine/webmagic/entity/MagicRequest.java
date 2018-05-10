/**
 * Project Name:WebMagic
 * File Name:MagicRequest.java
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
public class MagicRequest {
	private String guid;
	private String name;
	private String entryUrl;
	private int threadNum;
	private MagicProcessor processor;
	private List<MagicEntrySet> pipeline;
	private int isAsync;

	// 是否添加监控（jmx 可�?�过jconsole来查看，存在重构爬虫时实例化重复的错误问题，尽量不要使用�?
	private int isMonitor;

	public String getEntryUrl() {
		return entryUrl;
	}

	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public MagicProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(MagicProcessor processor) {
		this.processor = processor;
	}

	public List<MagicEntrySet> getPipeline() {
		return pipeline;
	}

	public void setPipeline(List<MagicEntrySet> pipeline) {
		this.pipeline = pipeline;
	}
	@Deprecated
	public int isMonitor() {
		return isMonitor;
	}
	@Deprecated
	public void setMonitor(int monitor) {
		this.isMonitor = monitor;
	}

	public int isAsync() {
		return isAsync;
	}

	public void setAsync(int isAsync) {
		this.isAsync = isAsync;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
