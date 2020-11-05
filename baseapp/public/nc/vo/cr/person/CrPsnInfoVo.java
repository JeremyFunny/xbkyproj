package nc.vo.cr.person;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CR前端用户基本信息
 * </p>
 * @author zsm
 * @date 2020-07-17
 */
public class CrPsnInfoVo extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	public String pk_psndoc;

	/**
	 * 个人编码
	 */
	public String code;
	
	/**
	 * 个人姓名
	 */
	public String name;
	
	/**
	 * 单位名称
	 */
	public String orgname;	
	
	/**
	 * 部门名称
	 */
	public String deptname;
	
	/**
	 * 岗位名称
	 */
	public String jobname;
	
	/**
	 * 性别
	 */
	public String sex;
	
	/**
	 * 年龄
	 */
	public String age;
	
	/**
	 * 照片
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
