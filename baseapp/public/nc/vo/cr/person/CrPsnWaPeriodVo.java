package nc.vo.cr.person;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CR前端人员已发放薪资期间
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrPsnWaPeriodVo extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 薪资类别主健
	 */
	public String pk_wa_class;
	
	/**
	 * 薪资发放记录主健
	 */
	public String pk_wa_data;

	/**
	 * 薪资方案名称
	 */
	public String waclassname;
	
	/**
	 * 薪资年度
	 */
	public String cyear;
	
	/**
	 * 薪资期间
	 */
	public String cperiod;
	
	/**
	 * 薪资发放时间
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
