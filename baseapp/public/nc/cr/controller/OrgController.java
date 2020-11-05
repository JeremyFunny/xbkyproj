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
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.server.ISecurityTokenCallback;
import nc.bs.logging.Log;
import nc.itf.cr.IApproveService;
import nc.itf.cr.IOkrService;
import nc.itf.cr.IOrgService;
import nc.itf.cr.IPersonService;
import nc.itf.uap.IUAPQueryBS;
import nc.itf.uap.pf.IPFMobileAppService;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.pub.cr.util.WxCorpUtil;
import nc.vo.cr.CrResultListVo;
import nc.vo.cr.CrResultVo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@RestController
@RequestMapping(value = "/orginfo", produces="application/json;charset=utf-8")
public class OrgController {
	private static Log log =Log.getInstance("okr");
	
	@RequestMapping(value = "/qryOrgListInfo")
	public String queryOrgList(@RequestParam(value="psnid") String psnid) {
		CrResultListVo result = new CrResultListVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			
			InvocationInfoProxy.getInstance().setUserDataSource(CrConstant.DSNAME);
			InvocationInfoProxy.getInstance().setGroupId(CrConstant.PK_GROUP);
			
		

			IOrgService service = NCLocator.getInstance().lookup(IOrgService.class);
			result = service.queryOrgList(psnid);
			
		
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMsg("查询单据出错:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 查询更新考核信息
	 */
	@RequestMapping(value = "/qryOrgInfo")
	public String queryOrgInfo(@RequestParam(value="psnid") String psnid,@RequestParam(value="pk_org") String pk_org) {
		CrResultVo result = new CrResultVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			
			InvocationInfoProxy.getInstance().setUserDataSource(CrConstant.DSNAME);
			InvocationInfoProxy.getInstance().setGroupId(CrConstant.PK_GROUP);
			
		

			IOrgService service = NCLocator.getInstance().lookup(IOrgService.class);
			result = service.queryOrgInfo(psnid, pk_org);
			
		
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMsg("查询单据出错:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	@RequestMapping(value = "/qryDeptInfo")
	public String queryDeptInfo(@RequestParam(value="psnid") String psnid, @RequestParam(value="pk_org") String pk_org) {
		CrResultListVo result = new CrResultListVo();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			
			InvocationInfoProxy.getInstance().setUserDataSource(CrConstant.DSNAME);
			InvocationInfoProxy.getInstance().setGroupId(CrConstant.PK_GROUP);
			
		

			IOrgService service = NCLocator.getInstance().lookup(IOrgService.class);
			result = service.queryDeptInfo(psnid, pk_org);
			
		
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMsg("查询单据出错:" + e.getMessage());
		}
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	
	
}
