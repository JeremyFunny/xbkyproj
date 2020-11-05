package nc.impl.cr.okr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.logging.Logger;
import nc.impl.cr.approve.CrModelUtils;

import javax.naming.NamingException;

import org.w3c.dom.Element;

import nc.itf.cr.IOkrService;
import nc.vo.cr.okr.CrResultVo;
import nc.vo.cr.okr.OkrPaperIndicator;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
//import nccloud.base.exception.ExceptionUtils;

/**
 * <p>
 *  Ŀ�꼨Ч������ʵ��
 * </p>
 * @date 2020-09-20
 * @author zsm
 */
public class OkrServiceImpl implements IOkrService {

	@Override
	public CrResultVo queryOkrScoreInfo(String psnid, String cperiod) throws BusinessException {
		// TODO Auto-generated method stub
		
		CrResultVo result = new CrResultVo();
		//��ͷģ����Ϣ
		OkrServiceDmo dmo = null;
		try {
			dmo=new OkrServiceDmo();
			
			/**
			 * ���ü�Ч����ͷ��Ϣ
			 */
			ArrayList<HashMap<String, Object>> templet=CrModelUtils.getOkrTempletBody("okr");
			result.setTemplet(templet);
			
			/**
			 * ���ü�Ч��ָ����Ϣ
			 */
			ArrayList<OkrPaperIndicator> indiinfo=dmo.getOkrTemplet(psnid,cperiod);
			ArrayList<HashMap<String, Object>> okrindis=new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < indiinfo.size(); i++) {
				OkrPaperIndicator vo=indiinfo.get(i);
				HashMap<String, Object> okrindi=new HashMap<String, Object>();
				//�йؼ���Ϊָ������
				okrindi.put("itemkey", vo.getPk_paper_indicator());
				okrindi.put("showname", vo.getIndicator());
				okrindi.put("index", i);
				okrindi.put("width", 45);
				okrindi.put("ctype", "0");
				UFDouble iw= vo.getIndiweight();
				java.text.NumberFormat percentFormat =java.text.NumberFormat.getPercentInstance(); 
//				percentFormat.setMaximumFractionDigits(2); //���С��λ�� 
//				percentFormat.setMaximumIntegerDigits(2);;//�������λ�� 
//				percentFormat.setMinimumFractionDigits(0); //��СС��λ�� 
//				percentFormat.setMinimumIntegerDigits(0);//��С����λ�� 
				
				okrindi.put("weight", percentFormat.format(iw.doubleValue()));
				okrindis.add(okrindi);
	        }
			result.setOkrindi(okrindis);
			/**
			 * ���ü�Ч���˶��������Ϣ
			 */
			ArrayList<HashMap<String, Object>> data=dmo.getOkrData(psnid,cperiod);
			
			/**
			 * ��ѯ��Ч��������Ϣ
			 */
			HashMap<String, HashMap<String, Object>> scoreindi=dmo.getScoreIndi(psnid,cperiod);
			
			for (int i=0;i<data.size();i++)
			{
				HashMap<String, Object> hm=data.get(i);
				String key=hm.get("pk_score_objround").toString();
				HashMap<String, Object> hmindi=scoreindi.get(key);
				if (hmindi!=null&&hmindi.size()!=0)
				{
					Iterator<String> iter = hmindi.keySet().iterator();
					while (iter.hasNext()) {
						String indikey = iter.next();
						if (indikey==null||indikey.equals(""))
							continue;
						Object indivalue = hmindi.get(indikey);
						if (indivalue==null||indivalue.equals(""))
							continue;
						hm.put(indikey, indivalue);
					}
				}
			
			}
			
			result.setData(data);
			result.setStatus(0);
			result.setMsg("��ѯ������Ϣ�ɹ�!");
		} catch (NamingException e) {
			Logger.error("ApproveServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		} catch (SQLException e) {
			Logger.error("ApproveServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("��ѯ������Ϣ������" + e.getMessage() + "��");
		}
		
		return result;
	}

	@Override
	public HashMap<String,Object> updateOkrScore(String psnid, String objroundid, String okrindi, UFDouble score,
			String nactualvalue, UFDate dactualcompdate) throws BusinessException {
		// TODO Auto-generated method stub
		//��ͷģ����Ϣ
		HashMap<String,Object> result = new HashMap<String,Object>();
		OkrServiceDmo dmo = null;
		try {
			dmo=new OkrServiceDmo();
			String pk_score=dmo.queryOkrScore(objroundid,okrindi);
			if (pk_score==null||pk_score.equals(""))
			{
				dmo.insertOkrScore(objroundid, okrindi,score,nactualvalue,dactualcompdate,psnid);
			}
			else
			{
				dmo.updateOkrScore(pk_score,score,nactualvalue,dactualcompdate,psnid);
			}
			
			result.put("status", 0);
			result.put("msg", "����ɹ�!");
		} catch (NamingException e) {
			Logger.error("ApproveServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("���漨Ч���˷�������:��" + e.getMessage() + "��");
		} catch (SQLException e) {
			Logger.error("ApproveServiceImpl��ѯ������Ϣ����!", e);
			throw new BusinessException("���漨Ч���˷�������:��" + e.getMessage() + "��");
		}
		
		return result;
				
	}
	
	
}
