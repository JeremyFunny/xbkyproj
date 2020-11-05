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
 *  CR前端手机审批,基于spring@service注解实现
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public interface IApproveService {
	
	/**
	 * 根据审批消息messageID，获取单据类型以及ID信息
	 * @param msgId
	 * @return
	 * @throws BusinessException
	 */
	CrBillInfoVo queryBillInfoByMsgId(String msgId) throws BusinessException;
	
	/**
	 * 根据单据类型获取移动端H5显示模板
	 * @param billType
	 * @return
	 * @throws BusinessException
	 */
	CrModelVo getBillModelByType(String billType) throws BusinessException;
	
	/**
	 * 根据单据模板以及单据ID查询单据数据
	 * @param billType
	 * @param billId
	 * @return
	 * @throws BusinessException
	 */
	CrBillDataVo queryBillData(CrModelVo model, String billId) throws BusinessException;
	
	/**
	 *   单据审批
	 * @param taskid
	 * @param status
	 * @param checkNote
	 * @param checkMan
	 * @return
	 * @throws BusinessException
	 */
	List<Map<String, Object>> approveBill(String taskid, int status, String checkNote, String checkMan) throws BusinessException;
	
	/**
	 * 查询单据审批明细记录
	 * @param billlId
	 * @param billType
	 * @return
	 */
	ArrayList<CrCheckMsgVo> getHistoryMsg(String billlId,String billType);
	
	/**
	 * 查询当前用户审批消息
	 * @param psnid
	 * @return
	 */
	ArrayList<CrMsgInfoVo> queryMsgInfoByPsnId(String psnid,String isread) throws BusinessException;

	
}
