package nc.vo.cr.approve;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 *  CR前端H5显示模板类
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrTempletVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public ArrayList<HashMap<String,Object>> head;
	public  ArrayList<HashMap<String,Object>> body;
	
	public ArrayList<HashMap<String, Object>> getHead() {
		return head;
	}
	public void setHead(ArrayList<HashMap<String, Object>> head) {
		this.head = head;
	}
	public ArrayList<HashMap<String, Object>> getBody() {
		return body;
	}
	public void setBody(ArrayList<HashMap<String, Object>> body) {
		this.body = body;
	}
}
