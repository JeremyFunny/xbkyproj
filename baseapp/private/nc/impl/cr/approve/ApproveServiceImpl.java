package nc.impl.cr.approve;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.bs.pf.pub.PfHistoryMsgUtil;
import javax.naming.NamingException;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.itf.cr.IApproveService;
import nc.itf.uap.pf.IPFMobileAppServiceFacade;
import nc.itf.uap.pf.IPFWorkflowQry;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.cr.approve.CrBillDataVo;
import nc.vo.cr.approve.CrBillInfoVo;
import nc.vo.cr.approve.CrCheckMsgVo;
import nc.vo.cr.approvelist.CrMsgInfoVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.cr.model.CrSelectVo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.workflownote.WorkflownoteVO;
import nc.vo.wfengine.core.activity.Activity;
import nc.vo.wfengine.core.workflow.BasicWorkflowProcess;
import nc.vo.wfengine.definition.WorkflowTypeEnum;
//import nccloud.base.exception.ExceptionUtils;

/**
 * <p>
 *  CR前端手机审批实现类
 * </p>
 * @date 2020-07-17
 * @author zp
 */
public class ApproveServiceImpl implements IApproveService {
//	
	@Override
	public CrBillInfoVo queryBillInfoByMsgId(String msgId) throws BusinessException {
		CrBillInfoVo vo = null;
		ApproveServiceDmo dmo = null;
		try {
//			InvocationInfoProxy.getInstance().setUserDataSource(DSNAME);
			dmo = new ApproveServiceDmo();
			vo = dmo.queryBillInfo(msgId);
		} catch (NamingException e) {
			Logger.error("ApproveServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}  catch (SQLException e) {
			Logger.error("ApproveServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}
		return vo;
	}

	@Override
	public CrModelVo getBillModelByType(String billType) throws BusinessException {
		return CrModelUtils.getBillApproveModel(billType);
	}

	@Override
	public CrBillDataVo queryBillData(CrModelVo model, String billId) throws BusinessException {
		CrBillDataVo vo = null;
		ApproveServiceDmo dmo = null;
		HashMap<String,String> headMap = new HashMap<String,String>();
		ArrayList<HashMap<String,String>> bodyList = new ArrayList<HashMap<String,String>>();
		try {
//			InvocationInfoProxy.getInstance().setUserDataSource(DSNAME);
			dmo = new ApproveServiceDmo();
			if(model.getHeadModel()!=null) {
				headMap = dmo.queryCrBillHead(model, billId);
				/**
				 * 供应商申请单附加VO特殊处理
				 */
				if(model.getBilltype().equals("10GY")) {
					SupplierVO supVo = dmo.querySupplierInfo(billId);
					if(supVo!=null) {
						/**
						 * 供应商类型
						 */
						headMap.put("supprop", supVo.getSupprop()==0?"内部单位":"外部单位");
						/**
						 * 供应商税号
						 */
						headMap.put("taxid", supVo.getTaxpayerid());
						
						/**
						 * 所属行业-SYS005_0xx
						 
						headMap.put("trade", dmo.queryBdDefName(supVo.getTrade()));
						*/
						/**
						 * 经济类型SYS004_0xx
						 
						headMap.put("ecotype", dmo.queryBdDefName(supVo.getEcotypesincevfive()));

						******************************/
					}
				}
			}
			if(model.getBodyModel()!=null) {
				/**
				 * 薪资申请单特殊处理
				 */
				if(model.getBilltype().equals("6302")) {
					if(headMap!=null && headMap.get("waclass")!=null&& headMap.get("cyear")!=null && headMap.get("cperiod")!=null) {
						ArrayList<CrSelectVo> list = dmo.queryItem6302(headMap.get("waclass"), headMap.get("cyear"), headMap.get("cperiod"));
						CrSelectVo[] vos = model.getBodyModel().getSelectVos();
						if(list!=null && list.size()>0) {
							int newLenth = vos.length + list.size();
							CrSelectVo[] vos_new = new CrSelectVo[newLenth];
							for(int i=0;i<vos.length;i++) {
								vos_new[i] = vos[i];
							}
							for(int i=0;i<list.size();i++) {
								vos_new[i+vos.length] = list.get(i);
							}
							model.getBodyModel().setSelectVos(vos_new);
						}
					}
				}
				bodyList = dmo.queryCrBillBody(model, headMap);
			}
			vo = new CrBillDataVo();
			vo.setHead(headMap);
			vo.setBodys(bodyList);
		} catch (NamingException e) {
			Logger.error("ApproveServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		} catch (SQLException e) {
			Logger.error("ApproveServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}
		return vo;
	}
	
	@Override
	public List<Map<String, Object>> approveBill(String taskid, int status, String checkNote, String checkMan) throws BusinessException {
		List<Map<String, Object>> ret=new ArrayList<Map<String, Object>>();
//		InvocationInfoProxy.getInstance().setUserDataSource(DSNAME);
//		InvocationInfoProxy.getInstance().setGroupId(PK_GROUP);
		String pk_group = InvocationInfoProxy.getInstance().getGroupId();
		//解锁异常
//		ArrayList<String> userlist= new ArrayList<String>();
//		userlist.add(checkMan);
		IPFMobileAppServiceFacade service = NCLocator.getInstance().lookup(IPFMobileAppServiceFacade.class);
		if(status==1) {
			ret=service.doAgree(pk_group, checkMan, taskid, checkNote, null);
		} else if(status==0) {
			ret=service.doDisAgree(pk_group, checkMan, taskid, checkNote, null);
		}
		return ret;
	}

	@Override
	public ArrayList<CrCheckMsgVo> getHistoryMsg(String billlId, String billType) {
		IPFWorkflowQry bo = NCLocator.getInstance().lookup(IPFWorkflowQry.class);
		WorkflownoteVO[] wfHistorys = null;
		ArrayList<CrCheckMsgVo> msgList = null;
		try {
			int type = WorkflowTypeEnum.Approveflow.getIntValue();
			/**
			 * 获取已处理的审批流信息
			 */
			wfHistorys = bo.queryWorkitems(billlId, billType, type, 1);
			msgList = convertHistoryMsgVOs(wfHistorys);
		} catch (BusinessException ex) {
			Logger.error("ApproveServiceImpl查询单据信息出错!", ex);
//			throw new BusinessException("查询单据信息出错，【" + ex.getMessage() + "】");
		}
		return msgList;
	}
	
	/**
	 * 审批流VO转换
	 * @param noteVOs
	 * @return
	 */
	private ArrayList<CrCheckMsgVo> convertHistoryMsgVOs(WorkflownoteVO[] noteVOs) {
		ArrayList<CrCheckMsgVo> msgList = new ArrayList<CrCheckMsgVo>();
	    if ((noteVOs != null) && (noteVOs.length > 0)) {
	    	BasicWorkflowProcess process = PfHistoryMsgUtil.getBWFProcess(noteVOs);
	    	for (int i = 0; i < noteVOs.length; i++) {
	    		if(i==0) {
	    			CrCheckMsgVo msgVo = new CrCheckMsgVo();
	    			msgVo.setCheckman(noteVOs[i].getSendername());
	    			msgVo.setCheckflow("制单人<制单>");
		    		msgVo.setChecktime(noteVOs[i].getSenddate()!=null?noteVOs[i].getSenddate().toString():"");
		    		msgVo.setChecknote(null);
		    		msgVo.setCheckresult("提交");
		    		msgList.add(msgVo);
	    		}
	    		CrCheckMsgVo msgVo = new CrCheckMsgVo();
	    		msgVo.setCheckman(noteVOs[i].getCheckname());
	    		if(noteVOs[i].getIscheck().equals("Y")) {//已审批
	    			msgVo.setChecktime(noteVOs[i].getDealdate()!=null?noteVOs[i].getDealdate().toString():"");
	    			msgVo.setChecknote(noteVOs[i].getChecknote());
	    			if(noteVOs[i].getApproveresult()!=null && noteVOs[i].getApproveresult().equals("Y")) {
	    				msgVo.setCheckresult("批准");
	    			} else {
	    				msgVo.setCheckresult("驳回");
	    			}
	    		} else {
	    			msgVo.setCheckresult("待审批");
	    		}
	            if (process != null) {
	            	Activity act = PfHistoryMsgUtil.getActivity(noteVOs[i], process);
	            	if(act!=null) {
	            		msgVo.setCheckflow(act.getName());
			    		msgList.add(msgVo);
	            	}
	            }
	    	}
	    }
	    return msgList;
	}

	@Override
	public ArrayList<CrMsgInfoVo> queryMsgInfoByPsnId(String psnid, String isread) throws BusinessException {
		// TODO Auto-generated method stub
		ArrayList<CrMsgInfoVo> vo = null;
		ApproveServiceDmo dmo = null;
		try {
//			InvocationInfoProxy.getInstance().setUserDataSource(DSNAME);
			dmo = new ApproveServiceDmo();
			vo = dmo.queryMsgInfoByPsnId(psnid,isread);
		} catch (NamingException e) {
			Logger.error("ApproveServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}  catch (SQLException e) {
			Logger.error("ApproveServiceImpl查询单据信息出错!", e);
			throw new BusinessException("查询单据信息出错，【" + e.getMessage() + "】");
		}
		return vo;
	}
}
