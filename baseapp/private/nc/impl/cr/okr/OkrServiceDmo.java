package nc.impl.cr.okr;

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
import nc.vo.cr.approve.CrBillInfoVo;
import nc.vo.cr.approvelist.CrMsgInfoVo;
import nc.vo.cr.model.CrJoinVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.cr.model.CrOrderVo;
import nc.vo.cr.model.CrSelectVo;
import nc.vo.cr.model.CrWhereVo;
import nc.vo.cr.okr.OkrPaperIndicator;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 * <p>
 *  目标绩效管理数据库操作实现
 * </p>
 * @author zp
 * @Date 2020-07-17
 */
public class OkrServiceDmo extends DataManageObject {
	private static final String NCVERSION="nc65";
	/**
	 * 
	 * @throws NamingException
	 */
	public OkrServiceDmo() throws NamingException {
		super();
	}
	
	/**
	 * 构造
	 * @param dbName
	 * @throws NamingException
	 */
	public OkrServiceDmo(String dbName) throws NamingException {
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

	public ArrayList<OkrPaperIndicator> getOkrTemplet(String psnid, String cperiod) throws SQLException {
		ArrayList<OkrPaperIndicator> list = new ArrayList<OkrPaperIndicator>();
		OkrPaperIndicator indiinfo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";
		
			sql=sql + " select indicator,pk_paper_indicator,indiweight,indiorder \n";
			sql=sql + " from okr_paper_indicator  \n";
			sql=sql + " where pk_paper in ( select b.pk_paper \n";
			sql=sql + " from okr_score_objround a \n";
			sql=sql + " left join okr_score_obj b on a.pk_score_obj=b.pk_score_obj \n";
			sql=sql + " where a.pk_psndoc_eva=? ";

			if (cperiod!=null&&cperiod.length()>0)
				sql=sql + " and b.cperiod= ? ";
			sql=sql + ") \n order by indiorder ";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, psnid);
			if (cperiod!=null&&cperiod.length()>0)
				stmt.setString(2, cperiod);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				indiinfo = new OkrPaperIndicator();
				indiinfo.setIndicator(rs.getString("indicator"));
				indiinfo.setPk_paper_indicator(rs.getString("pk_paper_indicator"));
				indiinfo.setIndiweight(new UFDouble(rs.getString("indiweight")));
				indiinfo.setIndiorder(rs.getString("indiorder"));
				list.add(indiinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return list;
	}

	public ArrayList<HashMap<String, Object>> getOkrData(String psnid, String cperiod)  throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> okadata = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = " select a.pk_score_objround,a.pk_score_obj,b.pk_paper,c.code as psncode,c.name as psnname, \n";
			sql=sql + " e.code as orgcode,e.name as orgname ,f.code as deptcode,f.name as deptname ,\n";
			sql=sql + " g.jobcode,g.jobname,h.postcode,h.postname \n";
			sql=sql + " from okr_score_objround a \n";
			sql=sql + " left join okr_score_obj b on a.pk_score_obj=b.pk_score_obj \n";
			sql=sql + " left join bd_psndoc c on b.pk_psndoc_obj=c.pk_psndoc \n";
			sql=sql + " left join hi_psnjob d on b.pk_psndoc_obj=d.pk_psndoc and d.lastflag='Y' and d.ismainjob='Y' and d.endflag='N' \n";
			sql=sql + " left join org_orgs e on d.pk_org=e.pk_org \n";
			sql=sql + " left join org_dept f on d.pk_dept=f.pk_dept \n";
			sql=sql + " left join om_job g on d.pk_job=g.pk_job \n";
			sql=sql + " left join om_post h on d.pk_post=h.pk_post \n";
			sql=sql + " where a.pk_psndoc_eva=? ";
			if (cperiod!=null&&cperiod.length()>0)
				sql=sql + "and b.cperiod= ? ";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, psnid);
			if (cperiod!=null&&cperiod.length()>0)
				stmt.setString(2, cperiod);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				okadata = new HashMap<String, Object>();
				String key=rs.getString("pk_score_objround");
				okadata.put("pk_score_objround", rs.getString("pk_score_objround"));
				okadata.put("pk_score_obj", rs.getString("pk_score_obj"));
				okadata.put("psncode", rs.getString("psncode"));
				okadata.put("psnname", rs.getString("psnname"));
				okadata.put("orgcode", rs.getString("orgcode"));
				okadata.put("orgname", rs.getString("orgname"));
				okadata.put("deptcode", rs.getString("deptcode"));
				okadata.put("deptname", rs.getString("deptname"));
				okadata.put("jobcode", rs.getString("jobcode"));
				okadata.put("jobname", rs.getString("jobname"));
				okadata.put("postcode", rs.getString("postcode"));
				okadata.put("postname", rs.getString("postname"));
				list.add(okadata);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return list;
	}

	public HashMap<String, HashMap<String, Object>> getScoreIndi(String psnid, String cperiod)  throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String,HashMap<String, Object>> scroeindimap = new HashMap<String,HashMap<String, Object>>();
		String sql = " select a.pk_score_objround,b.pk_score_obj,a.pk_paper,a.pk_paper_indicator,a.nvalue,a.nactualvalue,a.dactualcompdate,a.cmemo \n";
		sql=sql + " from okr_score a \n";
		sql=sql + " left join okr_score_objround b on a.pk_score_objround=b.pk_score_objround \n";
		sql=sql + " where b.pk_psndoc_eva=? \n";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, psnid);
			if (cperiod!=null&&cperiod.length()>0)
				stmt.setString(2, cperiod);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String key=rs.getString("pk_score_objround");
				if(scroeindimap.containsKey(key))
				{
					HashMap<String, Object> map=scroeindimap.get(key);
					if (rs.getString("pk_paper_indicator")!=null&&!rs.getString("pk_paper_indicator").equals(""))
						map.put(rs.getString("pk_paper_indicator"), rs.getString("nvalue"));
				}
				else
				{
					HashMap<String, Object> map=new HashMap<String,Object>();
					if (rs.getString("pk_paper_indicator")!=null&&!rs.getString("pk_paper_indicator").equals(""))
						map.put(rs.getString("pk_paper_indicator"), rs.getString("nvalue"));
					scroeindimap.put(key, map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return scroeindimap;
	}

	public String queryOkrScore(String objroundid, String okrindi) throws SQLException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String pk_score=null;
		HashMap<String, Object> okadata = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = " select pk_score from okr_score  \n";
		sql=sql + "  where pk_score_objround = ? and pk_paper_indicator = ? and dr=0 ";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, objroundid);
			stmt.setString(2, okrindi);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				pk_score=rs.getString("pk_score");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return pk_score;
	}

	public String updateOkrScore(String pk_score, UFDouble score, String nactualvalue, UFDate dactualcompdate, String psnid) throws SQLException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sql = "   update okr_score\r\n"
				+ "  set nvalue=?,nactualvalue=?,dactualcompdate=?,mender=?,modifytime=to_char(sysdate,'YYYY-MM-DD HH24:mm:ss') \n"
				+ "  where pk_score=? \n";

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setDouble(1, score.getDouble());
			stmt.setString(2, nactualvalue);
			if (dactualcompdate==null)
				stmt.setString(3,null);
			else
				stmt.setString(3, dactualcompdate.toString());
			stmt.setString(4, psnid);
			stmt.setString(5, pk_score);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return null;
	}

	public String insertOkrScore(String objroundid, String okrindi, UFDouble score, String nactualvalue,
			UFDate dactualcompdate, String psnid)  throws SQLException {
		
		
		String groupNumber = InvocationInfoProxy.getInstance().getGroupNumber();
		if ((groupNumber == null) || (groupNumber.isEmpty())) {
		groupNumber = "0001";
		}
		String ds = InvocationInfoProxy.getInstance().getUserDataSource();
		String pk_score=OidGenerator.getInstance().nextOid(ds, groupNumber);
		if(pk_score==null||pk_score.equals(""))
			return null;
		// TODO Auto-generated method stub
		String sql = " insert into okr_score(pk_score,pk_scheme_period,pk_scheme,cperiod,pk_score_obj,pk_score_objround,pk_paper,indiscoretype,pk_paper_indicator,nvalue,nactualvalue,dactualcompdate,creator,creationtime,ts,dr) \n";
		sql=sql + " select '"+ pk_score +"' as pk_score,b.pk_scheme_period,b.pk_scheme,b.cperiod,a.pk_score_obj, \n";
		sql=sql + " a.pk_score_objround,b.pk_paper,0 as indiscoretype,'"+okrindi+"' as pk_paper_indicator,"+score+" as nvalue,"+nactualvalue+"  as nactualvalue,"+dactualcompdate+"  as dactualcompdate,\n";
		sql=sql + " '" +psnid+"' as creator,to_char(sysdate,'YYYY-MM-DD HH24:mm:ss') as creationtime,to_char(sysdate,'YYYY-MM-DD HH24:mm:ss') as ts,0 as dr \n";
		sql=sql + " from okr_score_objround a \n";
		sql=sql + " left join okr_score_obj b on a.pk_score_obj=b.pk_score_obj \n";
		sql=sql + " where a.pk_score_objround=? \n";

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, objroundid);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return null;
	}
}
