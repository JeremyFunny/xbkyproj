package nc.vo.cr.model;

import java.io.Serializable;

public class CrOrderVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String table;
	public String key;
	public int type;
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
