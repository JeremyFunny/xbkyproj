package nc.vo.cr.model;

import java.io.Serializable;

/**
 * <p>
 *  CR前端H5模板单据项目
 * </p>
 * @author zp
 *
 */
public class CrSelectVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String table;
	public String key;
	public String itemkey;
	public boolean bshow;
	public String showname;
	public int index;
	public String ctype;
	public int width;
	
	
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
	public String getItemkey() {
		return itemkey;
	}
	public void setItemkey(String itemkey) {
		this.itemkey = itemkey;
	}
	public boolean isBshow() {
		return bshow;
	}
	public void setBshow(boolean bshow) {
		this.bshow = bshow;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
