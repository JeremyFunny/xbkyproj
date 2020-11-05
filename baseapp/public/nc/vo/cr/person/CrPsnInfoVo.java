package nc.vo.cr.person;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CRǰ���û�������Ϣ
 * </p>
 * @author zsm
 * @date 2020-07-17
 */
public class CrPsnInfoVo extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ����
	 */
	public String pk_psndoc;

	/**
	 * ���˱���
	 */
	public String code;
	
	/**
	 * ��������
	 */
	public String name;
	
	/**
	 * ��λ����
	 */
	public String orgname;	
	
	/**
	 * ��������
	 */
	public String deptname;
	
	/**
	 * ��λ����
	 */
	public String jobname;
	
	/**
	 * �Ա�
	 */
	public String sex;
	
	/**
	 * ����
	 */
	public String age;
	
	/**
	 * ��Ƭ
	 */
	public String photo;
	

	
	public String getPk_psndoc() {
		return pk_psndoc;
	}

	public void setPk_psndoc(String pk_psndoc) {
		this.pk_psndoc = pk_psndoc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}