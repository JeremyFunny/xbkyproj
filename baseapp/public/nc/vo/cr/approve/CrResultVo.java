package nc.vo.cr.approve;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * <p>
 *  CR前端返回JSON格式字符串结果
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrResultVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 0为成功，其他失败
	 */
	public Integer status;
	
	/**
	 * 错误信息
	 */
	public String msg;
	
	/**
	 * 单据信息
	 */
	public CrBillInfoVo info;
	
	/**
	 * 模板
	 */
	public CrTempletVo templet;
	
	/**
	 * 数据
	 */
	public CrBillDataVo data;
	
	/**
	 * 审批流
	 */
	ArrayList<CrCheckMsgVo> checkmsg;
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public CrBillInfoVo getInfo() {
		return info;
	}

	public void setInfo(CrBillInfoVo info) {
		this.info = info;
	}

	public CrTempletVo getTemplet() {
		return templet;
	}

	public void setTemplet(CrTempletVo templet) {
		this.templet = templet;
	}

	public CrBillDataVo getData() {
		return data;
	}

	public void setData(CrBillDataVo data) {
		this.data = data;
	}

	public ArrayList<CrCheckMsgVo> getCheckmsg() {
		return checkmsg;
	}

	public void setCheckmsg(ArrayList<CrCheckMsgVo> checkmsg) {
		this.checkmsg = checkmsg;
	}
}
