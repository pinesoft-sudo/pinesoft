/**
 * Project Name:WebMagic
 * File Name:MSelectItem.java
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
public class MagicSelectItem {
	private String guid;
	private String pguid;
	private String type;
	private String name;
	// private LinkedHashMap<String, Object> selects;
	private List<MagicEntrySet> selects;
	private String resultType;
	private int isSkip;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public List<MagicEntrySet> getSelects() {
		return selects;
	}

	public void setSelects(List<MagicEntrySet> selects) {
		this.selects = selects;
	}

	public int isSkip() {
		return isSkip;
	}

	public void setSkip(int isSkip) {
		this.isSkip = isSkip;
	}

	public String getPguid() {
		return pguid;
	}

	public void setPguid(String pguid) {
		this.pguid = pguid;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
