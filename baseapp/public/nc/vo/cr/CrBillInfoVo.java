package nc.vo.cr;

import java.io.Serializable;

public class CrBillInfoVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String billid;
	public String billtype;
	public String billname;
	public String checkman;
	public String isread;
	public String isdelete;
	
	public String getBillid() {
		return billid;
	}
	public void setBillid(String billid) {
		this.billid = billid;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}
	public String getBillname() {
		return billname;
	}
	public void setBillname(String billname) {
		this.billname = billname;
	}
	public String getCheckman() {
		return checkman;
	}
	public void setCheckman(String checkman) {
		this.checkman = checkman;
	}
	public String getIsRead() {
		return isread;
	}
	public void setIsRead(String isread) {
		this.isread = isread;
	}
	public String getIsDelete() {
		return isdelete;
	}
	public void setIsDelete(String isdelete) {
		this.isdelete = isdelete;
	}
}
