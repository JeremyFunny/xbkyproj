package nc.itf.cr;

import java.util.ArrayList;
import java.util.HashMap;

import nc.vo.cr.CrResultListVo;
import nc.vo.cr.CrResultVo;
import nc.vo.pub.BusinessException;

public interface IOrgService {

	/*
	 * ��ѯ����Ա��Ȩ�޵���֯��Ϣ
	 * data����Ϣ CrOrgInfoVo ֻ���� pk_org,deptcode,psncount
	 */
	public CrResultListVo queryOrgList(String psnid)  throws BusinessException;
	/*
	 * ��ѯ����֯����ϸ��Ϣ
	 * data����Ϣ CrOrgInfoVo ֻ���� deptcount,psncount
	 */	
	public CrResultVo queryOrgInfo(String psnid,String pk_org)  throws BusinessException;

	/*
	 * ��ѯ����֯����ϸ��Ϣ
	 * data����Ϣ CrDeptInfoVo
	 */
	public CrResultListVo queryDeptInfo(String psnid,String pk_org)  throws BusinessException;

}
