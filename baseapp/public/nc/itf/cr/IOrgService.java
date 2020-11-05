package nc.itf.cr;

import java.util.ArrayList;
import java.util.HashMap;

import nc.vo.cr.CrResultListVo;
import nc.vo.cr.CrResultVo;
import nc.vo.pub.BusinessException;

public interface IOrgService {

	/*
	 * 查询该人员有权限的组织信息
	 * data里信息 CrOrgInfoVo 只返回 pk_org,deptcode,psncount
	 */
	public CrResultListVo queryOrgList(String psnid)  throws BusinessException;
	/*
	 * 查询该组织的详细信息
	 * data里信息 CrOrgInfoVo 只返回 deptcount,psncount
	 */	
	public CrResultVo queryOrgInfo(String psnid,String pk_org)  throws BusinessException;

	/*
	 * 查询该组织的详细信息
	 * data里信息 CrDeptInfoVo
	 */
	public CrResultListVo queryDeptInfo(String psnid,String pk_org)  throws BusinessException;

}
