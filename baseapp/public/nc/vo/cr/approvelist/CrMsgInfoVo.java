package nc.vo.cr.approvelist;

public class CrMsgInfoVo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 消息标题
	 */
	public String subject;
	/**
	 * 消息明细
	 */
	public String pk_detail;
	/**
	 * 消息内容
	 */
	public String content;
	
	/**
	 * 单据类型编号
	 */
	public String billtype;
	
	/**
	 * 单据类型名称
	 */
	public String billtypename;
	
	/**
	 * 发送时间
	 */
	public String sendtime;
	
	/**
	 * 发送人主健
	 */
	public String sender;
	
	/**
	 * 发送人名称
	 */
	public String sendername;
	
	/**
	 * 发送人部门
	 */
	public String deptname;
	
	/**
	 * 发送人公司
	 */
	public String orgname;
	/**
	 * 金额
	 */
	public String nmoney;
	/**
	 * 单据日期
	 */
	public String billdate;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getBilltypename() {
		return billtypename;
	}

	public void setBilltypename(String billtypename) {
		this.billtypename = billtypename;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public String getPk_detail() {
		return pk_detail;
	}

	public void setPk_detail(String pk_detail) {
		this.pk_detail = pk_detail;
	}

	
	
}
