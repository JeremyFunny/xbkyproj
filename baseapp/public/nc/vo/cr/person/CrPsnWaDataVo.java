package nc.vo.cr.person;

import nc.vo.cr.CrDetailVo;

/**
 * <p>
 *  CRǰ�˸���н����ϸVo��
 * </p>
 * @author zp
 * @date 2020-07-17
 */
public class CrPsnWaDataVo  extends CrDetailVo {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * н����Ŀ����
	 */
	public String name;
	
	/**
	 * н����Ŀֵ
	 */
	public String value;
	
	/**
	 * ��ʾ˳��
	 */
	public int order;

	/**
	 * �ֶ�����
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
