package nc.vo.cr.model;

import java.io.Serializable;

public class CrWhereVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String table;
	public String key;
	public boolean bconst;
	public int type;
	public String itemkey;
	public String value;
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isBconst() {
		return bconst;
	}
	public void setBconst(boolean bconst) {
		this.bconst = bconst;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getItemkey() {
		return itemkey;
	}
	public void setItemkey(String itemkey) {
		this.itemkey = itemkey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
