package nc.vo.cr.model;

import java.io.Serializable;

public class CrJoinVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String name;
	public String alias;
	public String joinon;
	
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
	public String getJoinon() {
		return joinon;
	}
	public void setJoinon(String joinon) {
		this.joinon = joinon;
	}
}
