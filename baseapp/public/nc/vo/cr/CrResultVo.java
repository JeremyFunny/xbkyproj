package nc.vo.cr;

import java.io.Serializable;
import java.util.ArrayList;

import nc.vo.cr.approve.CrBillInfoVo;


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
	 * 详细信息
	 */
	public CrDetailVo data;
	
	
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

	
	public CrDetailVo getData() {
		return data;
	}

	public void setData(CrDetailVo data) {
		this.data = data;
	}
}
