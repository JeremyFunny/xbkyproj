package nc.vo.cr.org;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CRǰ���û�������Ϣ
 * </p>
 * @author zsm
 * @date 2020-07-17
 */
public class CrOrgInfoVo extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;
	

	/**
	 * ����
	 */
	public String pk_org;

	/**
	 * ��λ����
	 */
	public String orgcode;
	
	/**
	 * ��λ����
	 */
	public String orgname;

	/**
	 * ��֯�²�������
	 */
	public String deptcount;
	
	/**
	 * ��֯����Ա����
	 */
	public String psncount;


	public String getPk_org() {
		return pk_org;
	}

	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	

	public String getDeptcount() {
		return deptcount;
	}

	public void setDeptcount(String deptcount) {
		this.deptcount = deptcount;
	}

	public String getPsncount() {
		return psncount;
	}

	public void setPsncount(String psncount) {
		this.psncount = psncount;
	}
}
