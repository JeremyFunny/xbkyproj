package nc.vo.cr.approvelist;

public class CrMsgInfoVo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * ��Ϣ����
	 */
	public String subject;
	/**
	 * ��Ϣ��ϸ
	 */
	public String pk_detail;
	/**
	 * ��Ϣ����
	 */
	public String content;
	
	/**
	 * �������ͱ��
	 */
	public String billtype;
	
	/**
	 * ������������
	 */
	public String billtypename;
	
	/**
	 * ����ʱ��
	 */
	public String sendtime;
	
	/**
	 * ����������
	 */
	public String sender;
	
	/**
	 * ����������
	 */
	public String sendername;
	
	/**
	 * �����˲���
	 */
	public String deptname;
	
	/**
	 * �����˹�˾
	 */
	public String orgname;
	/**
	 * ���
	 */
	public String nmoney;
	/**
	 * ��������
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
