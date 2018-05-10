package org.pine.webmagic.entity;

public class MagicEntrySet {
	private String pguid;
	private String key;
	private Object value;
	private String type;

	public String getPguid() {
		return pguid;
	}
	public void setPguid(String pguid) {
		this.pguid = pguid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
