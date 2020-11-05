package nc.vo.cr.approvelist;

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
	
	ArrayList<CrMsgInfoVo> msginfo;
	
	
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

	
	public ArrayList<CrMsgInfoVo> getMsginfo() {
		return msginfo;
	}

	public void setMsginfo(ArrayList<CrMsgInfoVo> msginfo) {
		this.msginfo = msginfo;
	}
}
