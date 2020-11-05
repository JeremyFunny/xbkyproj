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
 *  CRǰ���ֻ�����������Ϣ��ѯʵ����
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
			Logger.error("PersonServiceImpl��ѯ���˻�����Ϣ����!", e);
			throw new BusinessException("��ѯ���˻�����Ϣ������" + e.getMessage() + "��");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl��ѯ���˻�����Ϣ����!", e);
			throw new BusinessException("��ѯ�����˻�����Ϣ������" + e.getMessage() + "��");
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
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
			Logger.error("PersonServiceImpl��ѯ����н���ڼ����!", e);
			throw new BusinessException("��ѯ����н���ڼ������" + e.getMessage() + "��");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl��ѯ����н���ڼ����!", e);
			throw new BusinessException("��ѯ������н���ڼ������" + e.getMessage() + "��");
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
			Logger.error("PersonServiceImpl��ѯ���˹���������!", e);
			throw new BusinessException("��ѯ���˹�����������" + e.getMessage() + "��");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl��ѯ���˹���������!", e);
			throw new BusinessException("��ѯ�����˹�����������" + e.getMessage() + "��");
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
			Logger.error("PersonServiceImpl��ѯ����н���ڼ����!", e);
			throw new BusinessException("��ѯ����н���ڼ������" + e.getMessage() + "��");
		}  catch (SQLException e) {
			Logger.error("PersonServiceImpl��ѯ����н���ڼ����!", e);
			throw new BusinessException("��ѯ������н���ڼ������" + e.getMessage() + "��");
		}
		return ispass;
	}

}
