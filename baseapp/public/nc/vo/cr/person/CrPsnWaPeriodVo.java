package nc.vo.cr.person;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CRǰ����Ա�ѷ���н���ڼ�
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrPsnWaPeriodVo extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;

	/**
	 * н���������
	 */
	public String pk_wa_class;
	
	/**
	 * н�ʷ��ż�¼����
	 */
	public String pk_wa_data;

	/**
	 * н�ʷ�������
	 */
	public String waclassname;
	
	/**
	 * н�����
	 */
	public String cyear;
	
	/**
	 * н���ڼ�
	 */
	public String cperiod;
	
	/**
	 * н�ʷ���ʱ��
	 */
	public String cpaydate;
	
	public String getPk_wa_class() {
		return pk_wa_class;
	}

	public void setPk_wa_class(String pk_wa_class) {
		this.pk_wa_class = pk_wa_class;
	}

	public String getPk_wa_data() {
		return pk_wa_data;
	}

	public void setPk_wa_data(String pk_wa_data) {
		this.pk_wa_data = pk_wa_data;
	}

	public String getWaclassname() {
		return waclassname;
	}

	public void setWaclassname(String waclassname) {
		this.waclassname = waclassname;
	}

	public String getCyear() {
		return cyear;
	}

	public void setCyear(String cyear) {
		this.cyear = cyear;
	}

	public String getCperiod() {
		return cperiod;
	}

	public void setCperiod(String cperiod) {
		this.cperiod = cperiod;
	}

	public String getCpaydate() {
		return cpaydate;
	}

	public void setCpaydate(String cpaydate) {
		this.cpaydate = cpaydate;
	}
	
}
