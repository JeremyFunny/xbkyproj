package nc.vo.cr.model;

import java.io.Serializable;

/**
 * <p>
 *  CRǰ��H5�ƶ���XML���õĵ���ģ��
 * </p>
 * <p>
 * 	ģ���ļ���ncchome/modules/baseapp/config/crwebconfig/��������.xml
 * </p>
 * @author zp
 * @date 2020-07-18
 */
public class CrModelVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String billtype;
	public String typename;
	
	public CrModelItemVo headModel;
	public CrModelItemVo bodyModel;
	
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public CrModelItemVo getHeadModel() {
		return headModel;
	}
	public void setHeadModel(CrModelItemVo headModel) {
		this.headModel = headModel;
	}
	public CrModelItemVo getBodyModel() {
		return bodyModel;
	}
	public void setBodyModel(CrModelItemVo bodyModel) {
		this.bodyModel = bodyModel;
	}
	
	
}
