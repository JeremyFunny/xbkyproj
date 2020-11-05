package nc.vo.cr.approve;

import java.io.Serializable;

/**
 * <p>
 *  CR前端单据审批信息Vo类
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrCheckMsgVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 处理人
	 */
	public String checkman;
	/**
	 * 审批类型
	 */
	public String checkflow;
	/**
	 * 处理时间
	 */
	public String checktime;
	/**
	 * 审批意见
	 */
	public String checknote;
	/**
	 * 审批结果
	 */
	public String checkresult;
	
	public String getCheckman() {
		return checkman;
	}
	public void setCheckman(String checkman) {
		this.checkman = checkman;
	}
	public String getCheckflow() {
		return checkflow;
	}
	public void setCheckflow(String checkflow) {
		this.checkflow = checkflow;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getChecknote() {
		return checknote;
	}
	public void setChecknote(String checknote) {
		this.checknote = checknote;
	}
	public String getCheckresult() {
		return checkresult;
	}
	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}
	
}
