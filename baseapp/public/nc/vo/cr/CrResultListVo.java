package nc.vo.cr;

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
	
	/**
	 * �����б���Ϣ
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
