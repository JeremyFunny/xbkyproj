package nc.itf.cr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.vo.cr.approve.CrBillDataVo;
import nc.vo.cr.approve.CrBillInfoVo;
import nc.vo.cr.approve.CrCheckMsgVo;
import nc.vo.cr.approvelist.CrMsgInfoVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.cr.okr.CrResultVo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 *  CR前端手机审批,基于spring@service注解实现
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public interface IOkrService {
	
	/**
	 * 查询考核消息
	 * @param psnid
	 * @return
	 */
	CrResultVo queryOkrScoreInfo(String psnid, String cperiod) throws BusinessException;

	HashMap<String,Object> updateOkrScore(String psnid, String objroundid, String okrindi, UFDouble score,
			String nactualvalue, UFDate dactualcompdate) throws BusinessException;

	
}
