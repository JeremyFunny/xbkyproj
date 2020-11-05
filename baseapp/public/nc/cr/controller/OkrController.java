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
public class OkrController {
	private static Log log =Log.getInstance("okr");
	
	@RequestMapping(value = "/queryOrgInfo")
	public String queryOrgInfo(@RequestParam(value="psnid") String psnid) {
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
	@RequestMapping(value = "/updateOkrScore", method = RequestMethod.POST)
	public String updateOkrScore(@RequestBody Map<String,String> map) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		try {
			byte[] token=NCLocator.getInstance().lookup(ISecurityTokenCallback.class).token("NCSystem".getBytes(),"pfxx".getBytes());
			nc.bs.framework.comn.NetStreamContext.setToken(token);
			InvocationInfoProxy.getInstance().setUserDataSource(CrConstant.DSNAME);
			InvocationInfoProxy.getInstance().setGroupId(CrConstant.PK_GROUP);
			String psnid = map.get("psnid");
			String objroundid = map.get("objroundid");
			String okrindi = map.get("okrindi");
			UFDouble score = new UFDouble(map.get("score"));
			String nactualvalue = map.get("actvalue");
			
			UFDate dactualcompdate=null;
			if (map.get("compdate")!=null)
				dactualcompdate = new UFDate(map.get("compdate"));
			
			log.debug("进入保存方法!objroundid="+objroundid);
			IOkrService service = NCLocator.getInstance().lookup(IOkrService.class);
			result=service.updateOkrScore(psnid,objroundid,okrindi,score,nactualvalue,dactualcompdate);
			
			if(result==null||result.size()!=2){
//				Object flag= result.get("flag");
//				Object des= result.get("des");
//				if(flag!=null&&flag.equals("0")){
//					result.put("status", 0);
//					result.put("msg", "保存成功!");
//				} else {
//					result.put("status", 1);
//					result.put("msg", des);
//				}
//			} else {
				result.put("status", 1);
				result.put("msg", "保存失败,没有返回错误原因");
			}
		} catch (Exception e) {
			log.debug("离开保存方法!");
			result.put("status", 1);
			result.put("msg", e.getMessage());
		}
		log.debug("离开保存方法!");
		return JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty);
	}
	
	
	
	
}
