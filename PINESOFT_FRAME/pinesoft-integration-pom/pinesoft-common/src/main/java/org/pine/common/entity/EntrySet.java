package org.pine.common.entity;

/**
 * 键值类型公共对象
 * 
 * @author yangsoon
 *
 */
public class EntrySet {
	public EntrySet() {

	}

	public EntrySet(String key, String value) {
		this.key = key;
		this.value = value;
	}

	private String key;

	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
