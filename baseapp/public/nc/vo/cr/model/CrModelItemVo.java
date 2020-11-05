package nc.vo.cr.model;

import java.io.Serializable;

public class CrModelItemVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String alias;
	public String primary;
	public String sqlstr;
	public CrSelectVo[] selectVos;
	public CrJoinVo[] joinVos;
	public CrWhereVo[] whereVos;
	public CrOrderVo[] orderVos;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	public String getSqlstr() {
		return sqlstr;
	}
	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
	public CrSelectVo[] getSelectVos() {
		return selectVos;
	}
	public void setSelectVos(CrSelectVo[] selectVos) {
		this.selectVos = selectVos;
	}
	public CrJoinVo[] getJoinVos() {
		return joinVos;
	}
	public void setJoinVos(CrJoinVo[] joinVos) {
		this.joinVos = joinVos;
	}
	public CrWhereVo[] getWhereVos() {
		return whereVos;
	}
	public void setWhereVos(CrWhereVo[] whereVos) {
		this.whereVos = whereVos;
	}
	public CrOrderVo[] getOrderVos() {
		return orderVos;
	}
	public void setOrderVos(CrOrderVo[] orderVos) {
		this.orderVos = orderVos;
	}

}
