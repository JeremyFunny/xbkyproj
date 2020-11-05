package nc.impl.cr.org;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.naming.NamingException;

import com.ufida.web.html.Map;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.DataManageObject;
import nc.bs.uap.oid.OidGenerator;
import nc.jdbc.framework.util.InOutUtil;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.cr.CrDetailVo;
import nc.vo.cr.approve.CrBillInfoVo;
import nc.vo.cr.approvelist.CrMsgInfoVo;
import nc.vo.cr.model.CrJoinVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.cr.model.CrOrderVo;
import nc.vo.cr.model.CrSelectVo;
import nc.vo.cr.model.CrWhereVo;
import nc.vo.cr.okr.OkrPaperIndicator;
import nc.vo.cr.org.CrDeptInfoVo;
import nc.vo.cr.org.CrOrgInfoVo;
import nc.vo.cr.person.CrPsnInfoVo;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 *  目标绩效管理数据库操作实现
 * </p>
 * @author zp
 * @Date 2020-07-17
 */
public class OrgServiceDmo extends DataManageObject {
	private static final String NCVERSION="nc65";
	/**
	 * 
	 * @throws NamingException
	 */
	public OrgServiceDmo() throws NamingException {
		super();
	}
	
	/**
	 * 构造
	 * @param dbName
	 * @throws NamingException
	 */
	public OrgServiceDmo(String dbName) throws NamingException {
		super(dbName);
	}

	
	/**
	 * 释放数据库链接
	 * @param con
	 * @param smt
	 * @param rs
	 */
	private static void relaseConn(Connection con, Statement smt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (smt != null) {
				smt.close();
			}
		} catch (Exception e) {
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
		}
	}

	public ArrayList<CrDetailVo> getOrgListInfo(String psnid) throws SQLException {
		ArrayList<CrDetailVo> list = new ArrayList<CrDetailVo>();
		CrOrgInfoVo orginfovo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";
		
			sql=sql + " select a.pk_corp as pk_org,a.code as orgcode,a.name as orgname from org_orgs a \n";
			sql=sql + " where a.enablestate=2 and a.isbusinessunit='Y' AND a.dr=0   \n";
			sql=sql + " order by a.code \n";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
	
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				orginfovo = new CrOrgInfoVo();
				orginfovo.setPk_org(rs.getString("pk_org"));
				orginfovo.setOrgcode(rs.getString("orgcode"));
				orginfovo.setOrgname(rs.getString("orgname"));
				list.add(orginfovo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return list;
	}
	
	
	public ArrayList<CrDetailVo> getDeptListInfo(String psnid,String pk_org) throws SQLException {
		ArrayList<CrDetailVo> list = new ArrayList<CrDetailVo>();
		CrDeptInfoVo deptinfovo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";
		
			sql=sql + " select distinct a.pk_dept ,a.code as deptcode,a.name as deptname,count(b.pk_psnjob) as psncount from org_dept a \n";
			sql=sql + " left join bd_psnjob  b on a.pk_dept=b.pk_dept and b.ismainjob='Y' and b.dr=0  \n";
			sql=sql + " where a.enablestate=2 AND a.dr=0  and a.pk_org='"+pk_org+"' \n";
			sql=sql + " group by a.pk_dept,a.code,a.name \n";
			sql=sql + " order by a.code \n";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
	
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				deptinfovo = new CrDeptInfoVo();
				deptinfovo.setPk_dept(rs.getString("pk_dept"));
				deptinfovo.setDeptcode(rs.getString("deptcode"));
				deptinfovo.setDeptname(rs.getString("deptname"));
				deptinfovo.setPsncount(rs.getString("psncount"));
				list.add(deptinfovo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return list;
	}

	public CrOrgInfoVo getOrgInfo(String psnid,String pk_org) throws SQLException {
		CrOrgInfoVo orginfovo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		sql= sql+" select count(c.pk_psnjob) as psncount,count(distinct a.pk_dept) as deptcount,b.code as orgcode,b.name as orgname from org_dept   a \n";
		sql= sql+" left join org_orgs b on a.pk_org=b.pk_org  \n";
		sql= sql+" left join bd_psnjob c  on a.pk_dept=c.pk_dept and c.ismainjob='Y' and c.dr=0   \n";
		sql= sql+" where  a.pk_org='"+pk_org+"' and a.enablestate=2  \n";
		sql= sql+" group by b.code,b.name   \n";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				orginfovo = new CrOrgInfoVo();
				orginfovo.setPk_org(pk_org);
				orginfovo.setOrgcode(rs.getString("orgcode"));
				orginfovo.setDeptcount(rs.getString("deptcount"));
				orginfovo.setOrgname(rs.getString("orgname"));
				orginfovo.setPsncount(rs.getString("psncount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, rs);
		}
		return orginfovo;
	}
	
}
