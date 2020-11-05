package nc.itf.uap.pf;

import java.util.List;
import java.util.Map;

import nc.vo.pub.BusinessException;

/**
 * ����IPFMobileAppService��������flag��desc�Ŀ��� ������ӿڰ�һ��
 * 
 * IPFMobileAppService������CMT���񣬲��������flag��desc���Ǵ����ҵ��ӿ� 
 * ���ӿ�δ�������񣬻����flag��desc
 * ��ʹ�ô˽ӿڣ���Ҫֱ��ʹ��IPFMobileAppService
 * 
 * @author yanke1
 * 
 */
@ComponentMetaInfo( CMT = false, remote = true )
public interface IPFMobileAppServiceFacade {

	public List<Map<String, Object>> getTaskButtonList(
			String status
		) throws BusinessException;

	/**ȡ�����������б�
	 * @param statuskey ҳǩ
	 * @param statuscode ҳǩ�ϵı���
	 * */
	public List<Map<String, Object>> getTaskList(
			String groupid, 
			String userid,
			String date, 
			String statuskey, 
			String statuscode, 
			String condition, 
			Integer startline, 
			Integer count
		) throws BusinessException;
	
	public List<Map<String, Object>> getTaskBill(
			String groupid,
			String userid,
			String taskid,
			String statuskey,
			String statuscode
		) throws BusinessException;
	
	
	public List<Map<String, Object>> getTaskAction(
			String groupid,
			String taskid,
			String statuskey,
			String statuscode
		) throws BusinessException;
	
	public List<Map<String, Object>> doAgree(
			String groupid,
			String userid,
			String taskid,
			String note,
			List<String> cuserids
		) throws BusinessException;
	
	public List<Map<String, Object>>  doDisAgree(
			String groupid,
			String userid,
			String taskid,
			String note,
			List<String> cuserids
		) throws BusinessException;
	
	public List<Map<String, Object>> doAddApprover(
			String groupid,
			String userid,
			String taskid,
			String note,
			List<String> userids
		) throws BusinessException;
	
	public List<Map<String, Object>> doReject(
			String groupid,
			String userid,
			String taskid,
			String note,
			String nodeid
		) throws BusinessException;
	
	
	public List<Map<String, Object>> doReassign(
			String groupid,
			String userid,
			String taskid,
			String note,
			String targetUserId
		) throws BusinessException;
	
	
	
	public List<Map<String, Object>> getUserList(
			String groupid, 
			String userid, 
			String taskid, 
			int startline, 
			int count, 
			String condition
		) throws BusinessException;
	
	public List<Map<String, Object>> getRejectNodeList(
			String groupid,
			String userid,
			String taskid,
			int startline,
			int count,
			String condition
		) throws BusinessException;
	
	public List<Map<String, Object>> getAssignPsnList(
			String groupid,
			String userid,
			String taskid,
			String isagree,
			int startline,
			int count,
			String condition
		) throws BusinessException;
	
	public List<Map<String, Object>> getApprovedDetail(
			String groupid,
			String userid,
			String taskid,
			String statuskey,
			String statuscode,
			int startline,
			int count
		) throws BusinessException;
	
	public List<Map<String, Object>> getPsnDetail(
			String groupid,
			String userid,
			String psnid
		) throws BusinessException;
	
	public List<Map<String, Object>> getMessageAttachmentList(
			String groupid,
			String userid,
			String taskid,
			String statuskey,
			String statuscode
		) throws BusinessException;
	
	public List<Map<String, Object>> getMessageAttachment(
			String groupid,
			String userid,
			String fileid,
			String downflag,
			String startposition
		) throws BusinessException;
	
	public List<Map<String, Object>> getConditionDescription(
			String groupid,
			String userid
		) throws BusinessException;
	
	public List<Map<String, Object>> getTaskStatusList(
			String groupid,
			String userid
		)throws BusinessException;
	
	public List<Map<String, Object>> getDefaultValueOfAction(
			String groupid,
			String userid,
			String taskid,
			String statuskey,
			String statuscode,
			String actioncode
		)throws BusinessException;
	
	public List<Map<String, Object>> doAction(
			String groupid,
			String userid,
			List<Map<String, Object>> list
		)throws BusinessException;
	
	public List<Map<String, Object>> uploadFile(
			String groupid,
			String userid,
			String taskid,
			String actioncode,
			List<Map<String, Object>> filelist
		)throws BusinessException;
	
	/**
	 * �����û����칤��������
	 * @param groupid
	 * @param usridsList
	 * @param type
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String,Object>> getPushNumber(
			String groupid,
			List<String> usridsList,
			String type
		)throws BusinessException;
	
	/**
	 * ����htmlȫò
	 */
	public List<Map<String, Object>> getTaskBillContent(
			String groupid,
			String userid,
			String taskid,
			String statuskey,
			String statuscode
		) throws BusinessException;
	
	/**
	 * ����ͼ
	 */
	public List<Map<String, Object>> getTaskFlowChart(
			String groupid,
			String userid,
			String taskid,
			String statuskey, 
			String statuscode,
			String picid,
			String width,
			String height
		) throws BusinessException;
	
}
