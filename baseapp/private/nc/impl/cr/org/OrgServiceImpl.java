package nc.impl.cr.org;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.impl.cr.approve.CrModelUtils;

import javax.naming.NamingException;

import org.w3c.dom.Element;

import nc.itf.cr.IOrgService;
import nc.vo.cr.CrDetailVo;
import nc.vo.cr.CrResultListVo;
import nc.vo.cr.CrResultVo;
import nc.vo.cr.org.CrOrgInfoVo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
//import nccloud.base.exception.ExceptionUtils;

/**
 * <p>
 *  Ŀ�꼨Ч������ʵ��
 * </p>
 * @date 2020-09-20
 * @author zsm
 */
public class OrgServiceImpl implements IOrgService {


	@Override
	public CrResultListVo queryOrgList(String psnid) throws BusinessException {
		CrResultListVo result = new CrResultListVo();
		//��ͷģ����Ϣ
		OrgServiceDmo dmo = null;
		try {
			dmo=new OrgServiceDmo();
			
			ArrayList<CrDetailVo> datas=dmo.getOrgListInfo(psnid);								
			result.setDatas(datas);
			result.setStatus(0);
			result.setMsg("��ѯ������Ϣ�ɹ�!");
		} catch (NamingException e) {
			Logger.error("OrgServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		} catch (SQLException e) {
			Logger.error("OrgServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		}
		
		return result;
	}

	@Override
	public CrResultVo queryOrgInfo(String psnid, String pk_org)
			throws BusinessException {
		CrResultVo result = new CrResultVo();
		//��ͷģ����Ϣ
		OrgServiceDmo dmo = null;
		try {
			dmo=new OrgServiceDmo();
			
			CrDetailVo data=dmo.getOrgInfo(psnid,pk_org);		
			result.setData(data);
			result.setStatus(0);
			result.setMsg("��ѯ������Ϣ�ɹ�!");
		} catch (NamingException e) {
			Logger.error("OrgServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		} catch (SQLException e) {
			Logger.error("OrgServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		}
		
		return result;
	}

	@Override
	public CrResultListVo queryDeptInfo(String psnid, String pk_org)
			throws BusinessException {
		CrResultListVo result = new CrResultListVo();
		//��ͷģ����Ϣ
		OrgServiceDmo dmo = null;
		try {
			dmo=new OrgServiceDmo();
			
			ArrayList<CrDetailVo> datas=dmo.getDeptListInfo(psnid, pk_org);					
			result.setDatas(datas);
			result.setStatus(0);
			result.setMsg("��ѯ������Ϣ�ɹ�!");
		} catch (NamingException e) {
			Logger.error("OrgServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		} catch (SQLException e) {
			Logger.error("OrgServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		}
		
		return result;
	}


	
	
}
