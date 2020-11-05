package nc.vo.cr.okr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
	 * 表头模版信息
	 */
	public ArrayList<HashMap<String, Object>> templet;
	
	/**
	 * 表头绩效打分值
	 */
	public ArrayList<HashMap<String, Object>> okrindi;
	
	/**
	 * 表体数据信息
	 */
	public ArrayList<HashMap<String, Object>> data;
	
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

	public ArrayList<HashMap<String, Object>> getTemplet() {
		return templet;
	}
	public void setTemplet(ArrayList<HashMap<String, Object>> templet) {
		this.templet = templet;
	}
	
	public ArrayList<HashMap<String, Object>> getOkrindi() {
		return okrindi;
	}
	public void setOkrindi(ArrayList<HashMap<String, Object>> okrindi) {
		this.okrindi = okrindi;
	}
	
	public ArrayList<HashMap<String, Object>> getData() {
		return data;
	}
	public void setData(ArrayList<HashMap<String, Object>> data) {
		this.data = data;
	}
}
