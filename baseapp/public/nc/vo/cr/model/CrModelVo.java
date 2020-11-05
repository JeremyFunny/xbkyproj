package nc.vo.cr.model;

import java.io.Serializable;

/**
 * <p>
 *  CR前端H5移动端XML配置的单据模板
 * </p>
 * <p>
 * 	模板文件在ncchome/modules/baseapp/config/crwebconfig/单据类型.xml
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
