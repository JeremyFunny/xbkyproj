package nc.impl.cr.person;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;

import sun.misc.BASE64Encoder;
import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.pub.DataManageObject;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.bd.psn.PsndocVO;
import nc.vo.cr.CrDetailVo;
import nc.vo.cr.person.CrPsnInfoVo;
import nc.vo.cr.person.CrPsnWaDataVo;
import nc.vo.cr.person.CrPsnWaPeriodVo;

/**
 * <p>
 *  CR前端移动审批数据库操作实现
 * </p>
 * @author zp
 * @Date 2020-07-17
 */
public class PersonServiceDmo extends DataManageObject {
	 private String dsName;
	  private JdbcSession session;
	  
	  private JdbcSession getSession()
		        throws DbException
		    {
		        if(session == null)
		            session = new JdbcSession(dsName);
		        return session;
		    }

		    public void close()
		    {
		        if(session != null)
		            session.closeAll();
		    }
	/**
	 * 
	 * @throws NamingException
	 */
	public PersonServiceDmo() throws NamingException {
		super();
	}
	
	/**
	 * 构造
	 * @param dbName
	 * @throws NamingException
	 */
	public PersonServiceDmo(String dbName) throws NamingException {
		super(dbName);
	}

	/**
	 * 根据人员主健查询个人基本信息
	 * @param psnid
	 * @return
	 * @throws Exception 
	 */
	public CrPsnInfoVo queryPersonInfo(String psnid) throws Exception {
		CrPsnInfoVo vo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " select a.code,a.name,c.name as orgname,d.name as deptname,e.jobname, \n";
		sql=sql + " to_char(sysdate,'YYYY')-substr(birthdate,1,4) as age,sex,a.photo  from bd_psndoc a \n";
		sql=sql + " left join bd_psnjob b on a.pk_psndoc=b.pk_psndoc \n";
		sql=sql + " left join org_orgs c on b.pk_org=c.pk_org \n";
		sql=sql + " left join org_dept d on b.pk_dept=d.pk_dept \n";
		sql=sql + " left join om_job e on b.pk_job=e.pk_job \n";
		sql=sql + " where  a.pk_psndoc=?";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, psnid);
			rs = stmt.executeQuery();
			if(rs.next()) {
				
		
				byte[] buffer=getFilebyte(psnid);
				BASE64Encoder encoder=new BASE64Encoder();
				String photo=encoder.encode(buffer).trim();
				vo = new CrPsnInfoVo();
				
				vo.setPk_psndoc(psnid);
				vo.setCode(rs.getString("code"));
				vo.setName(rs.getString("name"));
				vo.setOrgname(rs.getString("orgname"));
				vo.setDeptname(rs.getString("deptname"));
				vo.setJobname(rs.getString("jobname"));
				vo.setAge(rs.getString("age"));
				vo.setSex(rs.getString("sex"));
				vo.setPhoto(photo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, rs);
		}
		return vo;
	}
	
	public ArrayList<CrDetailVo> queryWaList(String psnid, String cyear) throws SQLException {
		ArrayList<CrDetailVo> list = new ArrayList<CrDetailVo>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " select a.cyear,a.cperiod,a.pk_wa_class,a.pk_wa_data,a.pk_psndoc,b.name as waclassname,substr(d.cpaydate,1,10) as cpaydate from wa_data a\r\n";
		sql = sql +	" left join wa_waclass b on a.pk_wa_class=b.pk_wa_class\r\n";
		sql = sql +	" left join wa_period c on a.cyear=c.cyear and a.cperiod=c.cperiod\r\n";
		sql = sql +	" left join wa_periodstate d on A.pk_wa_class=d.pk_wa_class and d.pk_wa_period=c.pk_wa_period\r\n";
		sql = sql +	" where  a.pk_psndoc='"+psnid+"' ";
		if (cyear==null||cyear.equals(""))
			sql = sql +	" and a.cyear>='2020'";
		else
			sql = sql +	" and a.cyear='"+cyear+"'";
		sql = sql +"and a.dr=0 and d.payoffflag='Y'\r\n";
		sql = sql +	" order by a.cpaydate desc";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				CrPsnWaPeriodVo vo = new CrPsnWaPeriodVo();
				vo.setCyear(rs.getString("cyear"));
				vo.setCperiod(rs.getString("cperiod"));
				vo.setPk_wa_class(rs.getString("pk_wa_class"));
				vo.setPk_wa_data(rs.getString("pk_wa_data"));
				vo.setWaclassname(rs.getString("waclassname"));
				vo.setCpaydate(rs.getString("cpaydate"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, rs);
		}
		return list;
	}
	
	/**
	 *  查询薪资发放申请单子表明细显示项目
	 * @param billId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String,String>> queryWaItem(String wadataid) throws SQLException {
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = " select a.slip_item as itemkey,a.item_displayname as showname,a.data_type as itype from wa_payslip_item a\r\n";
		sql = sql +	" left join wa_payslip b on a.pk_payslip=b.pk_payslip\r\n";
		sql = sql +	" left join wa_data c on b.pk_wa_class=c.pk_wa_class and b.accyear=c.cyear and b.accmonth=c.cperiod\r\n";
		sql = sql +	" where a.dr=0 and b.dr=0 and item_table='wa_data' and c.pk_wa_data=?\r\n";
		sql = sql +	" order by a.showorder";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, wadataid);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("itemkey", rs.getString("itemkey"));
				map.put("showname", rs.getString("showname"));
				if (rs.getString("itype").equals("2"))
					map.put("itype", "0");//数字类型
				else 
					map.put("itype", "1");//其他都按照字符处理
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return list;
	}
	
//	/**
//	 * 查询工资条明细数据
//	 * @param SQL
//	 * @param pkwadata
//	 * @return
//	 * @throws SQLException
//	 */
//	public HashMap<String,String> queryWaDetail(String SQL, String pkwadata) throws SQLException {
//		HashMap<String,String> map = new HashMap<String,String>();
//		Connection con = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try {
//			con = getConnection();
//			stmt = con.prepareStatement(SQL);
//			stmt.setString(1, pkwadata);
//			rs = stmt.executeQuery();
//	        if(rs.next()) {
//		        int col = rs.getMetaData().getColumnCount();
//		        map = new HashMap<String,String>();
//	            for (int i=1;i<=col;i++) {
//	            	map.put(rs.getMetaData().getColumnName(i).toLowerCase(), rs.getString(i));
//	            }
//	        }
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new SQLException(e);
//		} finally {
//			relaseConn(con, stmt, rs);
//		}
//		return map;
//	}
	
	
	public ArrayList<HashMap<String,String>> queryWaApproveItem(String wadataid) throws SQLException {
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Connection con = null;
		PreparedStatement stmt = null;

		String sql = " select a.itemkey as itemkey,a.name as showname,b.iitemtype as itype from wa_classitem a \n";
		sql = sql +	" left join wa_item b on a.pk_wa_item=b.pk_wa_item \n";
		sql = sql +	" left join wa_data c on a.pk_wa_class=c.pk_wa_class and a.cyear=c.cyear and a.cperiod=c.cperiod \n";
		sql = sql +	" where a.dr=0 and inapproveditem='Y' and showflag='Y' and c.pk_wa_data=? \n";
		sql = sql +	" order by a.idisplayseq ";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, wadataid);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("itemkey", rs.getString("itemkey"));
				map.put("showname", rs.getString("showname"));
				map.put("itype", rs.getString("itype"));//数字类型
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return list;
	}

	/**
	 * 查询工资条明细数据
	 * @param SQL
	 * @param pkwadata
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CrDetailVo> queryWaDetail(String pkwadata, ArrayList<HashMap<String,String>> itemList) throws SQLException {
		ArrayList<CrDetailVo> list=new ArrayList<CrDetailVo>();
		CrPsnWaDataVo vo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL= buildWaDetailSQL(itemList);
		try {
			con = getConnection();
			stmt = con.prepareStatement(SQL);
			stmt.setString(1, pkwadata);
			rs = stmt.executeQuery();
	        if(rs.next()) {
				vo = new CrPsnWaDataVo();
				vo.setName("人员编码");
				vo.setValue(rs.getString("psncode"));
				vo.setOrder(1);
				vo.setItype(1);
				list.add(vo);
				vo = new CrPsnWaDataVo();
				vo.setName("人员姓名");
				vo.setValue(rs.getString("psnname"));
				vo.setOrder(2);
				vo.setItype(1);
				list.add(vo);
				vo = new CrPsnWaDataVo();
				vo.setName("发放单位");
				vo.setValue(rs.getString("orgname"));
				vo.setOrder(3);
				vo.setItype(1);
				list.add(vo);
				vo = new CrPsnWaDataVo();
				vo.setName("发放期间");
				vo.setValue(rs.getString("cperiod"));
				vo.setOrder(4);
				vo.setItype(1);
				list.add(vo);
				vo = new CrPsnWaDataVo();
				vo.setName("薪资类别");
				vo.setValue(rs.getString("classname"));
				vo.setOrder(5);
				vo.setItype(1);
				list.add(vo);
				if(itemList!=null && itemList.size()>0) {
					for(int i=0;i<itemList.size();i++) {
						vo = new CrPsnWaDataVo();
						vo.setName(itemList.get(i).get("showname"));
						vo.setValue(rs.getString(itemList.get(i).get("itemkey")));
						vo.setOrder(i+6);
						vo.setItype(0);
						list.add(vo);
					}
				}
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, rs);
		}
		return list;
	}
	
	public String buildWaDetailSQL(ArrayList<HashMap<String,String>> itemList) {
		String SQL = "select b.code as psncode,b.name as psnname,c.shortname as orgname,a.cyear||'年'||a.cperiod||'月' as cperiod,d.name as classname";
		if(itemList!=null && itemList.size()>0) {
			for(int i=0;i<itemList.size();i++) {
				SQL = SQL + "," + itemList.get(i).get("itemkey");
			}
		}
		SQL = SQL + " from wa_data a\r\n";
		SQL = SQL + " left join bd_psndoc b on a.pk_psndoc=b.pk_psndoc \n";
		SQL = SQL + " left join org_orgs c on a.pk_org=c.pk_org \n";
		SQL = SQL + " left join wa_waclass d on a.pk_wa_class=d.pk_wa_class \n";
		SQL = SQL + " where a.dr=0 and a.pk_wa_data=?";
		return SQL;
	}
	
	/**
	 * 释放数据库链接
	 * @param con
	 * @param smt
	 * @param rs
	 */
	private static void relaseConn(Connection con, Statement smt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}
		try {
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

	public String queryPsnId( String psnid) throws SQLException {
		// TODO Auto-generated method stub
		String id=null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = " select id from bd_psndoc where a.pk_psndoc=? ";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, psnid);
			rs = stmt.executeQuery();
			if(rs.next()) {
				id=rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, rs);
		}
		return id;
	}

	public byte[] getFilebyte(String psnid) throws Exception{
		BaseDAO dao=new BaseDAO();
		String sql=" select * from bd_psndoc where pk_psndoc='"+psnid+"' ";
		nc.vo.hi.psndoc.PsndocVO vo=(nc.vo.hi.psndoc.PsndocVO) dao.executeQuery(sql, new BeanProcessor(nc.vo.hi.psndoc.PsndocVO.class));
		 Object obj =vo.getPhoto();
		 byte[] buffer=objectToBytesArray(obj);
		 return buffer;
	
	}
	public byte[] objectToBytesArray(Object o){//Object转换为byte数组
		byte[] bytes = null;
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
	    	ObjectOutputStream out = new ObjectOutputStream(bos);
	        out.writeObject(o);
	        out.flush();
	        bytes = bos.toByteArray();
	        out.close();
	        bos.close();
	    } catch (IOException e) {
	         e.printStackTrace();
	    }
		return bytes;
	}
}
