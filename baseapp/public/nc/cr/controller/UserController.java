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
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.server.ISecurityTokenCallback;
import nc.itf.cr.IPersonService;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.pub.cr.util.WxCorpUtil;
import nc.vo.cr.*;
import nc.vo.cr.person.*;
import nc.vo.pub.BusinessException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@RestController
@RequestMapping(value = "/userinfo", produces="application/json;charset=utf-8")
public class UserController {
	

	@RequestMapping(value = "/wxuserInfo")
	public String getUserInfo(@RequestParam(value="code")String code) {
//		return queryPersonInfo("0001X210000000008FSU");
		return queryPersonInfo("0001X210000000006XMW");
//		
//		CrResultVo result = new CrResultVo();
//		String corpid="wwc111261250a8d200";
//		String corpsecret="ZtNM82QgCfCVV29tVc-H85fbNT888BHyPztx_m3T91k";
//		String accesstoken=null;
//		String userid=null;
//		String mobile=null;
//		Object psnidobj=null;
//		IUAPQueryBS  qrybs=(IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
//		try {
//			accesstoken = WxCorpUtil.getAccessToken(corpid, corpsecret);
//			userid=WxCorpUtil.getUserid(accesstoken, code);
//			mobile=WxCorpUtil.getUserMobile(accesstoken, userid);
//			String sql=" select pk_psndoc from bd_psndoc where glbdef8='"+mobile+"' and dr=0 and enablestate=2 ";
//			psnidobj=qrybs.executeQuery(sql, new ColumnProcessor());
//			if(psnidobj==null){
//				result.setStatus(1);
//				result.setMsg("��ѯ���˻�����Ϣ����:û���ֻ���Ϊ'"+mobile+"'����Ա��Ϣ!");
//				return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
//			}
//		} catch (Exception e) {
//			result.setStatus(1);
//			result.setMsg("��ѯ���˻�����Ϣ����:" + e.getMessage());
//			return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
//		}
//		return queryPersonInfo(psnidobj.toString());
	}
	/**
	 * ��ѯ���˻�����Ϣ
	 */
	@RequestMapping(value = "/queryInfo")
	public String queryPersonInfo(@RequestParam(value="psnid") String psnid) {
		CrResultVo result = new CrResultVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			IPersonService service = NCLocator.getInstance().lookup(IPersonService.class);
			CrPsnInfoVo vo  = service.queryPersonInfo(psnid);
			result.setStatus(0);
			result.setMsg("��ѯ���˻�����Ϣ�ɹ�!");
			result.setData(vo);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("��ѯ���˻�����Ϣ����:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * ��ѯ������ϸ��Ϣ
	 */
	@RequestMapping(value = "/queryDetail")
	public String queryPersonDetail(@RequestParam(value="psnid") String psnid, @RequestParam(value="isread") String isread) {
		CrResultVo result = new CrResultVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			IPersonService service = NCLocator.getInstance().lookup(IPersonService.class);
			CrPsnDetailVo vo  = service.queryPersonDetail(psnid);
			result.setStatus(0);
			result.setMsg("��ѯ��Ա��ϸ��Ϣ�ɹ�!");
			result.setData(vo);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("��ѯ��Ա��ϸ��Ϣ����:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * ��ѯ�����ڼ�
	 */
	@RequestMapping(value = "/queryWaList")
	public String queryWaList(@RequestParam(value="psnid") String psnid, @RequestParam(value="cyear") String cyear) {
		CrResultListVo result = new CrResultListVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			IPersonService service = NCLocator.getInstance().lookup(IPersonService.class);
			ArrayList<CrDetailVo> list  = service.queryWaList(psnid, cyear);
			result.setStatus(0);
			result.setMsg("��ѯ�����ѷ���н���ڼ�ɹ�!");
			result.setDatas(list);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("��ѯ�����ѷ���н���ڼ����:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * ��ѯ������
	 */
	@RequestMapping(value = "/queryWaDetail")
	public String queryWaDetail(@RequestParam(value="wadataid") String wadataid) {
		CrResultListVo result = new CrResultListVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			IPersonService service = NCLocator.getInstance().lookup(IPersonService.class);
			ArrayList<CrDetailVo> waDataVo  = service.queryWaDetail(wadataid);
			result.setStatus(0);
			result.setMsg("��ѯ���˹�������ϸ�ɹ�!");
			result.setDatas(waDataVo);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("��ѯ���˹�������ϸ����:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * ������֤
	 */
	@RequestMapping(value = "/checkid" , method = RequestMethod.POST)
	public String checkBill(@RequestBody Map<String,String> map) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		try {
			String idtail = map.get("idtail");
			String psnid = map.get("psnid");
			

			IPersonService service = NCLocator.getInstance().lookup(IPersonService.class);
			boolean ispass  = service.checkPsnId(idtail,psnid);
			if (ispass)
			{
				result.put("status", 0);
				result.put("msg", "���У��ͨ��");
			} else {
				result.put("status", 1);
				result.put("msg", "���У��ʧ�ܣ����֤�ź���λ���벻��ȷ");
			}
			
		} catch (BusinessException e) {
			result.put("status", 1);
			result.put("msg", "���У��ʧ��:" + e.getMessage());
		} catch (Exception e) {
			result.put("status", 1);
			result.put("msg", "���У��ʧ��:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	
}
