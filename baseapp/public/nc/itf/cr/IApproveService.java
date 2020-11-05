package nc.itf.cr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.vo.cr.approve.CrBillDataVo;
import nc.vo.cr.approve.CrBillInfoVo;
import nc.vo.cr.approve.CrCheckMsgVo;
import nc.vo.cr.approvelist.CrMsgInfoVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.pub.BusinessException;

/**
 * <p>
 *  CRǰ���ֻ�����,����spring@serviceע��ʵ��
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public interface IApproveService {
	
	/**
	 * ����������ϢmessageID����ȡ���������Լ�ID��Ϣ
	 * @param msgId
	 * @return
	 * @throws BusinessException
	 */
	CrBillInfoVo queryBillInfoByMsgId(String msgId) throws BusinessException;
	
	/**
	 * ���ݵ������ͻ�ȡ�ƶ���H5��ʾģ��
	 * @param billType
	 * @return
	 * @throws BusinessException
	 */
	CrModelVo getBillModelByType(String billType) throws BusinessException;
	
	/**
	 * ���ݵ���ģ���Լ�����ID��ѯ��������
	 * @param billType
	 * @param billId
	 * @return
	 * @throws BusinessException
	 */
	CrBillDataVo queryBillData(CrModelVo model, String billId) throws BusinessException;
	
	/**
	 *   ��������
	 * @param taskid
	 * @param status
	 * @param checkNote
	 * @param checkMan
	 * @return
	 * @throws BusinessException
	 */
	List<Map<String, Object>> approveBill(String taskid, int status, String checkNote, String checkMan) throws BusinessException;
	
	/**
	 * ��ѯ����������ϸ��¼
	 * @param billlId
	 * @param billType
	 * @return
	 */
	ArrayList<CrCheckMsgVo> getHistoryMsg(String billlId,String billType);
	
	/**
	 * ��ѯ��ǰ�û�������Ϣ
	 * @param psnid
	 * @return
	 */
	ArrayList<CrMsgInfoVo> queryMsgInfoByPsnId(String psnid,String isread) throws BusinessException;

	
}
