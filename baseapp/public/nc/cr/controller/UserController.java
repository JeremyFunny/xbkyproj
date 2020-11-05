/*******************************
 * <p>
 *  CR移动端H5审批前端控制器
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
//				result.setMsg("查询个人基本信息出错:没有手机号为'"+mobile+"'的人员信息!");
//				return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
//			}
//		} catch (Exception e) {
//			result.setStatus(1);
//			result.setMsg("查询个人基本信息出错:" + e.getMessage());
//			return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
//		}
//		return queryPersonInfo(psnidobj.toString());
	}
	/**
	 * 查询个人基本信息
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
			result.setMsg("查询个人基本信息成功!");
			result.setData(vo);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("查询个人基本信息出错:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 查询个人详细信息
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
			result.setMsg("查询人员明细信息成功!");
			result.setData(vo);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("查询人员明细信息出错:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 查询工资期间
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
			result.setMsg("查询个人已发放薪资期间成功!");
			result.setDatas(list);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("查询个人已发放薪资期间出错:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 查询工资条
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
			result.setMsg("查询个人工资条明细成功!");
			result.setDatas(waDataVo);
		}  catch (BusinessException e) {
			result.setStatus(1);
			result.setMsg("查询个人工资条明细出错:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 检查身份证
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
				result.put("msg", "身份校验通过");
			} else {
				result.put("status", 1);
				result.put("msg", "身份校验失败，身份证号后六位输入不正确");
			}
			
		} catch (BusinessException e) {
			result.put("status", 1);
			result.put("msg", "身份校验失败:" + e.getMessage());
		} catch (Exception e) {
			result.put("status", 1);
			result.put("msg", "身份校验失败:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	
}
