package nc.vo.cr;

import java.io.Serializable;
import java.util.ArrayList;

import nc.vo.cr.approve.CrBillInfoVo;


/**
 * <p>
 *  CRǰ�˷���JSON��ʽ�ַ������
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrResultVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 0Ϊ�ɹ�������ʧ��
	 */
	public Integer status;
	
	/**
	 * ������Ϣ
	 */
	public String msg;
	
	/**
	 * ��ϸ��Ϣ
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
