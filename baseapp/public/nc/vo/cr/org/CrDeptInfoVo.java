package nc.vo.cr.org;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CRǰ���û�������Ϣ
 * </p>
 * @author zsm
 * @date 2020-07-17
 */
public class CrDeptInfoVo extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;
	/**
	 * ����
	 */
	public String pk_dept;

	/**
	 * ��λ����
	 */
	public String deptcode;
	
	/**
	 * ��λ����
	 */
	public String deptname;

	/**
	 * ��֯����Ա����
	 */
	public String psncount;
	

	
	
	

	public String getPk_dept() {
		return pk_dept;
	}

	public void setPk_dept(String pk_dept) {
		this.pk_dept = pk_dept;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPsncount() {
		return psncount;
	}

	public void setPsncount(String psncount) {
		this.psncount = psncount;
	}

}
