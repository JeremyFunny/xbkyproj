package nc.vo.cr.approve;

import java.io.Serializable;
import java.util.ArrayList;


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
	 * ������Ϣ
	 */
	public CrBillInfoVo info;
	
	/**
	 * ģ��
	 */
	public CrTempletVo templet;
	
	/**
	 * ����
	 */
	public CrBillDataVo data;
	
	/**
	 * ������
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
