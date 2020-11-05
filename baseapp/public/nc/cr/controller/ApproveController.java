/*******************************
 * <p>
 *  CR�ƶ���H5����ǰ�˿�����
 * </p>
 * @date 2020-07-17
 * @author zp
 * @version v6.5
 ******************************/
package nc.cr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.server.ISecurityTokenCallback;
import nc.bs.logging.Log;
import nc.itf.cr.IApproveService;
import nc.itf.uap.pf.IPFMobileAppService;
import nc.pubitf.para.SysInitQuery;
import nc.vo.cr.approve.CrBillDataVo;
import nc.vo.cr.approve.CrBillInfoVo;
import nc.vo.cr.approve.CrCheckMsgVo;
import nc.vo.cr.approve.CrResultVo;
import nc.vo.cr.approve.CrTempletVo;
import nc.vo.cr.approvelist.CrMsgInfoVo;
import nc.vo.cr.approvelist.CrResultListVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.cr.model.CrSelectVo;
import nc.vo.pub.BusinessException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@RestController
@RequestMapping(value = "/message", produces="application/json;charset=utf-8")
public class ApproveController {
	private static Log log =Log.getInstance("ekpoa");
	
	public ApproveController(){
		System.out.println("ApproveController===============");
	}
	@RequestMapping(value = "/test")
	public String test(@RequestParam(value="msgId") String msgId) {
		Map<String, Object> result = null;
		try {
			IPFMobileAppService appqry = NCLocator.getInstance().lookup(IPFMobileAppService.class);
			result = appqry.getApprovedDetail(CrConstant.PK_GROUP, null, msgId, "ishandled", "handled", 1, 1000);
			Map<String, Object> action = appqry.getTaskAction(CrConstant.PK_GROUP, msgId, "ishandled","handled");
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	@RequestMapping(value = "/query")
	public String queryApproveBill(@RequestParam(value="msgId") String msgId) {
		CrResultVo result = new CrResultVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			
			IApproveService service = NCLocator.getInstance().lookup(IApproveService.class);
			InvocationInfoProxy.getInstance().setUserDataSource(CrConstant.DSNAME);
			InvocationInfoProxy.getInstance().setGroupId(CrConstant.PK_GROUP);
			/**
			 * ��ѯ��������������Ϣ
			 */
			CrBillInfoVo billInfo = service.queryBillInfoByMsgId(msgId);
			if(billInfo!=null && billInfo.getBilltype()!=null) {
				if(billInfo.getIsDelete().equals("Y"))
				{
//					//�Ѿ�ɾ���ĵ������⴦��
//					HashMap<String, String> appms =new HashMap<String, String> ();
//					appms.put("billid",billInfo.getBillid());//��������
//					appms.put("pk_detail",msgId);
//					appms.put("pk_psndoc",billInfo.getPk_psndoc());		
//					appms.put("billtypename",billInfo.getBillname());
//					appms.put("subject",msgId);
//					appms.put("usercode",billInfo.usercode);
//					EKPUtilTools.deleteMassageToEKP(appms);
//					result.setStatus(1);
//					result.setMsg("�õ����Ѵ���!");
				}
				else
				{
					CrModelVo model = service.getBillModelByType(billInfo.getBilltype());
					if(model!=null) {
						
						/**
						 * ��ѯ��������
						 */
						CrBillDataVo billData = service.queryBillData(model, billInfo.getBillid());
						
						/**
						 * ģ��ת��
						 */
						CrTempletVo templetVo = getTempletVo(model);
						IPFMobileAppService appqry = NCLocator.getInstance().lookup(IPFMobileAppService.class);
						Map<String, Object> msgMap = appqry.getApprovedDetail(CrConstant.PK_GROUP, null, msgId, "ishandled", "handled", 1, 1000);
						result.setStatus(0);
						result.setMsg("��ѯ������Ϣ�ɹ�!");
						result.setInfo(billInfo);
						result.setTemplet(templetVo);
						result.setData(billData);
						result.setCheckmsg(msgMap!=null?getCheckMsgList(msgMap):null);
					} else {
						result.setStatus(1);
						result.setMsg("�������͡�" + billInfo.getBilltype() + "��δ��ѯ����Ч�ĵ���H5ģ��!");
					}
				}
			} else {
				//ɾ������
//				HashMap<String, String> appms =new HashMap<String, String> ();
//				appms.put("pk_detail",msgId);
//				appms.put("subject",msgId);
//				appms.put("billid",msgId);
//				appms.put("pk_psndoc","yy02");
//				EKPUtilTools.sendTodoMassageToEKP("isdelete",appms);
//				result.setStatus(1);
//				result.setMsg("�õ�����ɾ��!");
			}
		} catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("��ѯ���ݳ���:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMsg("��ѯ���ݳ���:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * ��ѯ��ǰ��Ա�µ����д���
	 */
	@RequestMapping(value = "/querylist")
	public String queryApproveBillList(@RequestParam(value="psnid") String psnid,@RequestParam(value="isread") String isread) {
		CrResultListVo result = new CrResultListVo();
		try {
			InvocationInfoProxy.getInstance().setUserDataSource(CrConstant.DSNAME);
			InvocationInfoProxy.getInstance().setGroupId(CrConstant.PK_GROUP);

//			InvocationInfoProxy.getInstance().setUserCode("10001461");
//			InvocationInfoProxy.getInstance().setUserId("1001X21000000000O5SI");
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			
			IApproveService service = NCLocator.getInstance().lookup(IApproveService.class);
			ArrayList<CrMsgInfoVo> msginfo  = service.queryMsgInfoByPsnId(psnid,isread);
			result.setStatus(0);
			result.setMsg("��ѯ������Ϣ�ɹ�!");
			result.setMsginfo(msginfo);
			
			
		}  catch (Exception e) {
			result.setStatus(1);
			result.setMsg("��ѯ���ݳ���:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	
	@RequestMapping(value = "/approve" , method = RequestMethod.POST)
	public String checkBill(@RequestBody Map<String,String> map) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		try {
			InvocationInfoProxy.getInstance().setUserDataSource(CrConstant.DSNAME);
			InvocationInfoProxy.getInstance().setGroupId(CrConstant.PK_GROUP);
			String taskid = map.get("taskid");
			int apprstatus = Integer.valueOf(map.get("apprstatus"));
			String checkNote = map.get("checkNote");
			String checkMan = map.get("checkMan");
			log.debug("������������!taskid="+taskid);
			IApproveService service = NCLocator.getInstance().lookup(IApproveService.class);
			List<Map<String, Object>> ret=service.approveBill(taskid, apprstatus, checkNote, checkMan);
			if(ret!=null&&ret.size()>0){
				if(ret.size()==1&&ret.get(0)!=null){
					Object flag= ret.get(0).get("flag");
					Object des= ret.get(0).get("des");
					if(flag!=null&&flag.equals("0")){
						result.put("status", 0);
						result.put("msg", "�����ɹ�!");
					} else {
						result.put("status", 1);
						result.put("msg", des);
					}
				} else {
					result.put("status", 1);
					result.put("msg", "��������ʧ��,����ֵ����ȷ"+ret.toString());
				}
			} else {
				result.put("status", 1);
				result.put("msg", "��������ʧ��,û�з��ش���ԭ��");
			}
		} catch (BusinessException e) {
			log.debug("�뿪��������!");
			result.put("status", 1);
			result.put("msg", "�������ݳ���:" + e.getMessage());
		} catch (Exception e) {
			log.debug("�뿪��������!");
			result.put("status", 1);
			result.put("msg", "�������ݳ���:" + e.getMessage());
		}
		log.debug("�뿪��������!");
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * ��������Ϣת��
	 * @param vo
	 * @return
	 */
	private ArrayList<CrCheckMsgVo> getCheckMsgList(Map<String,Object> map){
		ArrayList<CrCheckMsgVo> msgList = new ArrayList<CrCheckMsgVo>();
		if(map!=null ) {
			/************�Ƶ����ύ**************/
			CrCheckMsgVo msgVo = new CrCheckMsgVo();
			msgVo.setCheckman(map.get("makername")!=null?map.get("makername").toString():null);
			msgVo.setCheckflow("�Ƶ���<�Ƶ�>");
			msgVo.setChecktime(map.get("submitdate")!=null?map.get("submitdate").toString():null);
			msgVo.setChecknote(null);
			msgVo.setCheckresult("�ύ");
			msgList.add(msgVo);
			
			/***************����*****************/
			ArrayList<Map<String,Object>> list = (ArrayList<Map<String, Object>>) map.get("approvehistorylinelist");
			if(list!=null && list.size()>0) {
				for(int i=0;i<list.size();i++) {
					Map<String,Object> one = list.get(i);
					if(one.get("handledate")!=null && one.get("handledate").toString().length()>0) {
						CrCheckMsgVo vo = new CrCheckMsgVo();
						vo.setCheckman(one.get("handlername")!=null?one.get("handlername").toString():null);
						vo.setCheckflow("");
						vo.setChecktime(one.get("handledate")!=null?one.get("handledate").toString():null);
						vo.setChecknote(one.get("note")!=null?one.get("note").toString():null);
						vo.setCheckresult(one.get("action")!=null?one.get("action").toString():null);
						msgList.add(vo);
					}
				}
			}
		}
		return msgList;
	}
	
	/**
	 * ת��ǰ��ģ��
	 * @param model
	 * @return
	 */
	private CrTempletVo getTempletVo(CrModelVo model) {
		CrTempletVo templetVo = null;
		if(model!=null) {
			templetVo = new CrTempletVo();
			if(model.getHeadModel()!=null) {
				CrSelectVo[] headVos = model.getHeadModel().getSelectVos();
				if(headVos!=null && headVos.length>0) {
					ArrayList<HashMap<String,Object>> headMaps = new ArrayList<HashMap<String,Object>>();
					for(int i=0;i<headVos.length;i++) {
						CrSelectVo headVo = headVos[i];
						if(headVo.isBshow()) {
							headMaps.add(getTempletHead(headVo));
						}
					}
					templetVo.setHead(headMaps);
				}
			}
			if(model.getBodyModel()!=null) {
				CrSelectVo[] bodyVos = model.getBodyModel().getSelectVos();
				if(bodyVos!=null && bodyVos.length>0) {
					ArrayList<HashMap<String,Object>> bodyMaps = new ArrayList<HashMap<String,Object>>();
					for(int i=0;i<bodyVos.length;i++) {
						CrSelectVo bodyVo = bodyVos[i];
						if(bodyVo.isBshow()) {
							bodyMaps.add(getTempletBody(bodyVo));
						}
					}
					templetVo.setBody(bodyMaps);
				}
			}
		}
		return templetVo;
	}
	
	/**
	 * ģ���ͷת��
	 * @param vo
	 * @return
	 */
	private HashMap<String,Object> getTempletBody(CrSelectVo vo){
		HashMap<String,Object> itemMap = new HashMap<String,Object>();
		itemMap.put("itemkey", vo.getItemkey());
		itemMap.put("showname", vo.getShowname());
		itemMap.put("index", vo.getIndex());
		itemMap.put("width", vo.getWidth());
		itemMap.put("ctype", vo.getCtype());
		return itemMap;
	}
	
	/**
	 * ģ�����ת��
	 * @param vo
	 * @return
	 */
	private HashMap<String,Object> getTempletHead(CrSelectVo vo){
		HashMap<String,Object> itemMap = new HashMap<String,Object>();
		itemMap.put("itemkey", vo.getItemkey());
		itemMap.put("showname", vo.getShowname());
		itemMap.put("index", vo.getIndex());
		itemMap.put("ctype", vo.getCtype());
		return itemMap;
	}
}
