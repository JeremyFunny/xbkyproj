package nc.impl.cr.person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import nc.bs.logging.Logger;

import javax.naming.NamingException;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.itf.cr.IPersonService;
import nc.vo.cr.CrDetailVo;
import nc.vo.cr.person.CrPsnDetailVo;
import nc.vo.cr.person.CrPsnInfoVo;
import nc.vo.cr.person.CrPsnWaDataVo;
import nc.vo.cr.person.CrPsnWaPeriodVo;
import nc.vo.pub.BusinessException;

/**
 * <p>
 *  CR前端手机审批个人信息查询实现类
 * </p>
 * @date 2020-08-17
 * @author zp
 */
public class PersonServiceImpl implements IPersonService {
	
	@Override
	public CrPsnInfoVo queryPersonInfo(String psnid) throws BusinessException {
		CrPsnInfoVo vo = null;
		PersonServiceDmo dmo = null;
		try {
			dmo = new PersonServiceDmo();
			vo = dmo.queryPersonInfo(psnid);
		} catch (NamingException e) {
			Logger.error("PersonServiceImpl查询个人基本信息出错!", e);
			throw new BusinessException("查询个人基本信息出错，【" + e.getMessage() + "】");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl查询个人基本信息出错!", e);
			throw new BusinessException("查询单个人基本信息出错，【" + e.getMessage() + "】");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return vo;
	}
	
	@Override
	public CrPsnDetailVo queryPersonDetail(String psnid) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<CrDetailVo> queryWaList(String psnid, String cyear) throws BusinessException {
		ArrayList<CrDetailVo> list =null;
		PersonServiceDmo dmo = null;
		try {
			dmo = new PersonServiceDmo();
			list = dmo.queryWaList(psnid, cyear);
		} catch (NamingException e) {
			Logger.error("PersonServiceImpl查询个人薪资期间出错!", e);
			throw new BusinessException("查询个人薪资期间出错，【" + e.getMessage() + "】");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl查询个人薪资期间出错!", e);
			throw new BusinessException("查询单个人薪资期间出错，【" + e.getMessage() + "】");
		}
		return list;
	}
	
	@Override
	public ArrayList<CrDetailVo> queryWaDetail(String wadataid) throws BusinessException {
		ArrayList<CrDetailVo> list = new ArrayList<CrDetailVo>();
		PersonServiceDmo dmo = null;
		try {
			dmo = new PersonServiceDmo();
			ArrayList<HashMap<String, String>>  itemlist = dmo.queryWaItem(wadataid);
			if (itemlist==null||itemlist.isEmpty())
				itemlist=dmo.queryWaApproveItem(wadataid);
			list = dmo.queryWaDetail(wadataid, itemlist);
		} catch (NamingException e) {
			Logger.error("PersonServiceImpl查询个人工资条出错!", e);
			throw new BusinessException("查询个人工资条出错，【" + e.getMessage() + "】");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl查询个人工资条出错!", e);
			throw new BusinessException("查询单个人工资条出错，【" + e.getMessage() + "】");
		}
		return list;
	}

	@Override
	public boolean checkPsnId(String idtail, String psnid) throws BusinessException {
		// TODO Auto-generated method stub
		boolean ispass=false;
		try {
			PersonServiceDmo dmo = new PersonServiceDmo();
			String id = dmo.queryPsnId(psnid);
			if (id!=null&&!id.equals("")&&idtail.equals(id.substring(id.length()-6)))
				ispass=true;
		} catch (NamingException e) {
			Logger.error("PersonServiceImpl查询个人薪资期间出错!", e);
			throw new BusinessException("查询个人薪资期间出错，【" + e.getMessage() + "】");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl查询个人薪资期间出错!", e);
			throw new BusinessException("查询单个人薪资期间出错，【" + e.getMessage() + "】");
		}
		return ispass;
	}

}
