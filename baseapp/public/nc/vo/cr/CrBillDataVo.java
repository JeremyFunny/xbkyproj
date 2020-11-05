package nc.vo.cr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CR前端返回的单据内容
 * @author zp
 *
 */
public class CrBillDataVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public HashMap<String,String> head;
	public ArrayList<HashMap<String,String>> bodys;
	
	public HashMap<String, String> getHead() {
		return head;
	}
	public void setHead(HashMap<String, String> head) {
		this.head = head;
	}
	public ArrayList<HashMap<String, String>> getBodys() {
		return bodys;
	}
	public void setBodys(ArrayList<HashMap<String, String>> bodys) {
		this.bodys = bodys;
	}
}
