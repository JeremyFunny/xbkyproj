package nc.itf.cr;

import java.util.ArrayList;

import nc.vo.cr.CrDetailVo;
import nc.vo.cr.person.*;
import nc.vo.pub.BusinessException;

/**
 * <p>
 *  CR�û���Ϣ��ѯ������ӿ�
 * </p>
 * @author zsm
 * @date 2020-07-17
 */
public interface IPersonService {
	
	/**
	 * ��ѯ���˻�����Ϣ
	 * @param psnid
	 * @return
	 */
	CrPsnInfoVo queryPersonInfo(String psnid) throws BusinessException;
	
	/**
	 * ��ѯ������ϸ��Ϣ
	 * @param psnid
	 * @return
	 */
	CrPsnDetailVo queryPersonDetail(String psnid) throws BusinessException;
	
	/**
	 * ��ѯ���˹����ڼ�
	 * @param psnid
	 * @param year
	 * @return
	 * @throws BusinessException
	 */
	ArrayList<CrDetailVo> queryWaList(String psnid, String year) throws BusinessException;
	
	/**
	 * ��ѯ���˹�����
	 * @param psnid
	 * @return
	 */
	ArrayList<CrDetailVo> queryWaDetail(String wadataid) throws BusinessException;

	/**
	 * ������֤��ʧ��
	 * @param idtail,psnid
	 * @return
	 */
	boolean checkPsnId(String idtail, String psnid) throws BusinessException;
	
}
