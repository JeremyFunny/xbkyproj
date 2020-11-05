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
 *  目标绩效管理后端实现
 * </p>
 * @date 2020-09-20
 * @author zsm
 */
public class OrgServiceImpl implements IOrgService {


	@Override
	public CrResultListVo queryOrgList(String psnid) throws BusinessException {
		CrResultListVo result = new CrResultListVo();
		//表头模版信息
		OrgServiceDmo dmo = null;
		try {
			dmo=new OrgServiceDmo();
			
			ArrayList<CrDetailVo> datas=dmo.getOrgListInfo(psnid);								
			result.setDatas(datas);
			result.setStatus(0);
			result.setMsg("查询单据信息成功!");
		} catch (NamingException e) {
			Logger.error("OrgServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		} catch (SQLException e) {
			Logger.error("OrgServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}
		
		return result;
	}

	@Override
	public CrResultVo queryOrgInfo(String psnid, String pk_org)
			throws BusinessException {
		CrResultVo result = new CrResultVo();
		//表头模版信息
		OrgServiceDmo dmo = null;
		try {
			dmo=new OrgServiceDmo();
			
			CrDetailVo data=dmo.getOrgInfo(psnid,pk_org);		
			result.setData(data);
			result.setStatus(0);
			result.setMsg("查询单据信息成功!");
		} catch (NamingException e) {
			Logger.error("OrgServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		} catch (SQLException e) {
			Logger.error("OrgServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}
		
		return result;
	}

	@Override
	public CrResultListVo queryDeptInfo(String psnid, String pk_org)
			throws BusinessException {
		CrResultListVo result = new CrResultListVo();
		//表头模版信息
		OrgServiceDmo dmo = null;
		try {
			dmo=new OrgServiceDmo();
			
			ArrayList<CrDetailVo> datas=dmo.getDeptListInfo(psnid, pk_org);					
			result.setDatas(datas);
			result.setStatus(0);
			result.setMsg("查询单据信息成功!");
		} catch (NamingException e) {
			Logger.error("OrgServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		} catch (SQLException e) {
			Logger.error("OrgServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}
		
		return result;
	}


	
	
}
