/**
 * Project Name:WebMagic
 * File Name:MagicSelectable.java
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
public class MagicSelectable {
	private List<MagicSelectItem> getUrls;
	private List<MagicSelectItem> getHtmls;
	private List<MagicSelectItem> targetRequests;

	public List<MagicSelectItem> getGetUrls() {
		return getUrls;
	}

	public void setGetUrls(List<MagicSelectItem> getUrls) {
		this.getUrls = getUrls;
	}

	public List<MagicSelectItem> getGetHtmls() {
		return getHtmls;
	}

	public void setGetHtmls(List<MagicSelectItem> getHtmls) {
		this.getHtmls = getHtmls;
	}

	public List<MagicSelectItem> getTargetRequests() {
		return targetRequests;
	}

	public void setTargetRequests(List<MagicSelectItem> targetRequests) {
		this.targetRequests = targetRequests;
	}
}
