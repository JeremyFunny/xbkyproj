package nc.vo.cr.approvelist;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * <p>
 *  CRǰ�˷���JSON��ʽ�ַ������
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrResultListVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 0Ϊ�ɹ�������ʧ��
	 */
	public Integer status;
	
	/**
	 * ������Ϣ
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