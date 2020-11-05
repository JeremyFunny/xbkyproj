package nc.vo.cr.person;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CR前端个人薪资明细Vo类
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrPsnWaDataVo  extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 薪资项目名称
	 */
	public String name;
	
	/**
	 * 薪资项目值
	 */
	public String value;
	
	/**
	 * 显示顺序
	 */
	public int order;

	/**
	 * 字段类型
	 */
	public int itype;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public int getItype() {
		return itype;
	}

	public void setItype(int itype) {
		this.itype = itype;
	}

}
