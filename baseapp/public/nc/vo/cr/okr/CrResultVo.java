package nc.vo.cr.okr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
	 * ��ͷģ����Ϣ
	 */
	public ArrayList<HashMap<String, Object>> templet;
	
	/**
	 * ��ͷ��Ч���ֵ
	 */
	public ArrayList<HashMap<String, Object>> okrindi;
	
	/**
	 * ����������Ϣ
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
