package nc.vo.cr;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * <p>
 *  CR前端返回JSON格式字符串结果
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrResultListVo implements Serializable {
	
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
	 * 返回列表信息
	 */
	public ArrayList<CrDetailVo> datas;
	
	
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

	
	public ArrayList<CrDetailVo> setDatas() {
		return datas;
	}

	public void setDatas(ArrayList<CrDetailVo> datas) {
		this.datas = datas;
	}
}
