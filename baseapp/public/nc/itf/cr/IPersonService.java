package nc.itf.cr;

import java.util.ArrayList;

import nc.vo.cr.CrDetailVo;
import nc.vo.cr.person.*;
import nc.vo.pub.BusinessException;

/**
 * <p>
 *  CR用户信息查询服务类接口
 * </p>
 * @author zsm
 * @date 2020-07-17
 */
public interface IPersonService {
	
	/**
	 * 查询个人基本信息
	 * @param psnid
	 * @return
	 */
	CrPsnInfoVo queryPersonInfo(String psnid) throws BusinessException;
	
	/**
	 * 查询个人明细信息
	 * @param psnid
	 * @return
	 */
	CrPsnDetailVo queryPersonDetail(String psnid) throws BusinessException;
	
	/**
	 * 查询个人工资期间
	 * @param psnid
	 * @param year
	 * @return
	 * @throws BusinessException
	 */
	ArrayList<CrDetailVo> queryWaList(String psnid, String year) throws BusinessException;
	
	/**
	 * 查询个人工资条
	 * @param psnid
	 * @return
	 */
	ArrayList<CrDetailVo> queryWaDetail(String wadataid) throws BusinessException;

	/**
	 * 检查身份证号失败
	 * @param idtail,psnid
	 * @return
	 */
	boolean checkPsnId(String idtail, String psnid) throws BusinessException;
	
}
