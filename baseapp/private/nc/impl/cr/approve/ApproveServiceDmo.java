package nc.impl.cr.approve;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;
import nc.bs.pub.DataManageObject;
import nc.jdbc.framework.util.InOutUtil;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.cr.approve.CrBillInfoVo;
import nc.vo.cr.approvelist.CrMsgInfoVo;
import nc.vo.cr.model.CrJoinVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.cr.model.CrOrderVo;
import nc.vo.cr.model.CrSelectVo;
import nc.vo.cr.model.CrWhereVo;

/**
 * <p>
 *  CR前端移动审批数据库操作实现
 * </p>
 * @author zp
 * @Date 2020-07-17
 */
public class ApproveServiceDmo extends DataManageObject {
	private static final String NCVERSION="nc65";
	/**
	 * 
	 * @throws NamingException
	 */
	public ApproveServiceDmo() throws NamingException {
		super();
	}
	
	/**
	 * 构造
	 * @param dbName
	 * @throws NamingException
	 */
	public ApproveServiceDmo(String dbName) throws NamingException {
		super(dbName);
	}

	/**
	 * 根据审批流消息查询审批单据的类型、单据主键以及单据名称
	 * @param msgId
	 * @return
	 * @throws SQLException
	 */
	public CrBillInfoVo queryBillInfo(String msgId) throws SQLException {
		CrBillInfoVo bill = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";
		if (NCVERSION.equals("ncc"))
		{
			sql=sql + " select a.billid,a.billtype,b.billtypename,a.receiver,c.isread,c.isdelete,d.pk_psndoc,d.user_code from sm_msg_approve a \n";
			sql=sql + " left join bd_billtype b on a.billtype=b.pk_billtypecode \n";
			sql=sql + " left join sm_msg_user c on a.pk_message=c.pk_message and c.destination = 'inbox' \n";
			sql=sql + " left join sm_user d on a.receiver=d.cuserid \n";
			sql=sql + " where nvl(a.dr,0)=0 and a.pk_detail=?";
		}
		else 
		{
			sql=sql + " select  a.billid,a.pk_billtype as billtype,b.billtypename,a.checkman as receiver, \n";
			sql=sql + " (case when a.approvestatus=1 then 'Y' else 'N' end) as isread,(case when a.dr=0 then 'N' else 'Y' end) as isdelete,\n";
			sql=sql + " d.pk_psndoc,d.user_code \n";
			sql=sql + " from pub_workflownote a \n";
			sql=sql + " left join bd_billtype b on a.pk_billtype=b.pk_billtypecode \n";
			sql=sql + " and exists (select c.pk_billtypecode,max(c.ts||c.PK_BILLTYPEID) from bd_billtype c  where c.pk_billtypecode=b.pk_billtypecode group by c.pk_billtypecode having max(c.ts||c.PK_BILLTYPEID)=b.ts||b.PK_BILLTYPEID)\n";
			sql=sql + " left join sm_user d on a.checkman=d.cuserid \n";
			sql=sql + " where nvl(a.dr,0)=0 and a.pk_checkflow= ? \n";
		}
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, msgId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				bill = new CrBillInfoVo();
				bill.setBillid(rs.getString(1));
				bill.setBilltype(rs.getString(2));
				bill.setBillname(rs.getString(3));
				bill.setCheckman(rs.getString(4));
				bill.setIsRead(rs.getString(5));
				bill.setIsDelete(rs.getString(6));
				bill.setPk_psndoc(rs.getString(7));
				bill.setUsercode(rs.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return bill;
	}
	
	/**
	 * 根据单据主键及模板查询单据主表
	 * @param model
	 * @param billId
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String,String> queryCrBillHead(CrModelVo model, String billId) throws SQLException {
		HashMap<String,String> map = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		String headSQL=buildHeadSQL(model);
		try {
			con = getConnection();
			stmt = con.prepareStatement(headSQL);
			stmt.setString(1, billId);
			CrWhereVo[] wheres = model.getHeadModel().getWhereVos();
			if  (model.getHeadModel().getSqlstr()==null||model.getHeadModel().getSqlstr().length()==0){
				if(wheres!=null && wheres.length>0) {
					for(int i=0;i<wheres.length;i++) {
						if(wheres[i].getType()==0) {
							stmt.setInt(i+2, Integer.valueOf(wheres[i].getValue()));
						} else if(wheres[i].getType()==1) {
							stmt.setString(i+2, wheres[i].getValue());
						} else if(wheres[i].getType()==2) {
							stmt.setDouble(i+2, Double.valueOf(wheres[i].getValue()));
						}
					}
				}
				rs = stmt.executeQuery();
				if(rs.next()) {
					CrSelectVo[] headVos = model.getHeadModel().getSelectVos();
					map = new HashMap<String,String>();
					for(int i=0;i<headVos.length;i++) {
						if(headVos[i].getKey()!=null && headVos[i].getKey().length()>0) {
							map.put(headVos[i].getItemkey(), rs.getString(headVos[i].getItemkey()));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, rs);
		}
		return map;
	}
	
	/**
	 * 根据单据模板以及主表数据查询子表List
	 * @param model
	 * @param headMap
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<HashMap<String,String>> queryCrBillBody(CrModelVo model, HashMap<String,String> headMap) throws SQLException {
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Connection con = null;
		PreparedStatement stmt = null;
		String bodySQL=buildBodySQL(model);
		try {
			con = getConnection();
			stmt = con.prepareStatement(bodySQL);
			CrWhereVo[] wheres = model.getBodyModel().getWhereVos();
			if(wheres!=null && wheres.length>0) {
				for(int i=0;i<wheres.length;i++) {
					if(wheres[i].isBconst()) {
						if(wheres[i].getType()==0) {
							stmt.setInt(i+1, Integer.valueOf(wheres[i].getValue()));
						} else if(wheres[i].getType()==1) {
							stmt.setString(i+1, wheres[i].getValue());
						} else if(wheres[i].getType()==2) {
							stmt.setDouble(i+1, Double.valueOf(wheres[i].getValue()));
						}
					} else {
						if(wheres[i].getType()==0) {
							stmt.setInt(i+1, Integer.valueOf(headMap.get(wheres[i].getItemkey())));
						} else if(wheres[i].getType()==1) {
							stmt.setString(i+1, headMap.get(wheres[i].getItemkey()));
						} else if(wheres[i].getType()==2) {
							stmt.setDouble(i+1, Double.valueOf(headMap.get(wheres[i].getItemkey())));
						}
					}
				}
			}
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				HashMap<String,String> map = new HashMap<String,String>();
				CrSelectVo[] bodyVos = model.getBodyModel().getSelectVos();
				if(bodyVos!=null && bodyVos.length>0) {
					for(int i=0;i<bodyVos.length;i++) {
						map.put(bodyVos[i].getItemkey(), rs.getString(bodyVos[i].getItemkey()));
					}
				}
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
	 * 根据模板构建查询单据主表语句
	 */
	private String buildHeadSQL(CrModelVo model) {
		String SQL = "select ";
		boolean bfirst= true;
		CrSelectVo[] selects = model.getHeadModel().getSelectVos();
		if(selects!=null && selects.length>0) {
			for(int i=0;i<selects.length;i++) {
				if(selects[i].getKey()!=null && selects[i].getKey().length()>0) {
					if(!bfirst) {
						SQL = SQL + ",";
					}
					SQL = SQL + selects[i].getTable()+"."+selects[i].getKey() + " as " + selects[i].getItemkey() + " ";
					bfirst = false;
				}
			}
		} else {
			SQL = SQL + "'' ";
		}
		//赵思敏添加特殊处理
		if  (model.getHeadModel().getSqlstr()!=null&&model.getHeadModel().getSqlstr().length()>0) {
			SQL = SQL + model.getHeadModel().getSqlstr();
		} else {
			SQL = SQL + " from ";
			SQL = SQL + model.getHeadModel().getName() + " " + model.getHeadModel().getAlias();
			
			CrJoinVo[] joins = model.getHeadModel().getJoinVos();
			if(joins!=null && joins.length>0) {
				for(int i=0;i<joins.length;i++) {
					SQL = SQL + " left join " + joins[i].getName() + " " + joins[i].getAlias() + " on " + joins[i].getJoinon();
				}
			}
	
			CrWhereVo[] wheres = model.getHeadModel().getWhereVos();
			if(wheres!=null && wheres.length>0) {
				SQL = SQL + " where " + model.getHeadModel().getAlias() + "." + model.getHeadModel().getPrimary()+ "=?";
				for(int i=0;i<wheres.length;i++) {
					SQL = SQL + " and " + wheres[i].getTable() + "." + wheres[i].getKey() + "=?";
				}
			}
			CrOrderVo[] orders = model.getHeadModel().getOrderVos();
			if(orders!=null && orders.length>0) {
				SQL = SQL + " Order by ";
				for(int i=0;i<orders.length;i++) {
					if(i!=0) {
						SQL = SQL + ",";
					}
					SQL = SQL + orders[i].getTable() + "." + orders[i].getKey() + (orders[i].getType()==1?" desc ":" ");
				}
			}
		}
		return SQL;
	}
	
	/**
	 * 根据模板构建查询单据主表语句
	 */
	private String buildBodySQL(CrModelVo model) {
		String SQL = "select ";
		CrSelectVo[] selects = model.getBodyModel().getSelectVos();
		if(selects!=null && selects.length>0) {
			for(int i=0;i<selects.length;i++) {
				if(i!=0) {
					SQL = SQL + ",";
				}
				SQL = SQL + selects[i].getTable()+"."+selects[i].getKey() + " as " + selects[i].getItemkey() + " ";
			}
		} else {
			SQL = SQL + "0 ";
		}
		SQL = SQL + " from ";
		SQL = SQL + model.getBodyModel().getName() + " " + model.getBodyModel().getAlias();
		
		CrJoinVo[] joins = model.getBodyModel().getJoinVos();
		if(joins!=null && joins.length>0) {
			for(int i=0;i<joins.length;i++) {
				SQL = SQL + " left join " + joins[i].getName() + " " + joins[i].getAlias() + " on " + joins[i].getJoinon();
			}
		}

		CrWhereVo[] wheres = model.getBodyModel().getWhereVos();
		if(wheres!=null && wheres.length>0) {
			SQL = SQL + " where ";
			for(int i=0;i<wheres.length;i++) {
				if(i!=0) {
					SQL = SQL + " and ";
				}
				SQL = SQL + wheres[i].getTable() + "." + wheres[i].getKey() + "=?";
			}
		}
		CrOrderVo[] orders = model.getBodyModel().getOrderVos();
		if(orders!=null && orders.length>0) {
			SQL = SQL + " Order by ";
			for(int i=0;i<orders.length;i++) {
				if(i!=0) {
					SQL = SQL + ",";
				}
				SQL = SQL + orders[i].getTable() + "." + orders[i].getKey() + (orders[i].getType()==1?" desc ":" ");
			}
		}
		return SQL;
	}
	
	/**
	 *  查询薪资发放申请单子表明细显示项目
	 * @param billId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CrSelectVo> queryItem6302(String classItem, String cyear, String cperiod) throws SQLException {
		ArrayList<CrSelectVo> list = new ArrayList<CrSelectVo>();
		Connection con = null;
		PreparedStatement stmt = null;
		int index=4;
		String sql = " select itemkey,name from wa_classitem where pk_wa_class=? and cyear=? and cperiod=? and dr=0\r\n" + 
				" and inapproveditem='Y' and showflag='Y'\r\n" + 
				" order by idisplayseq";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, classItem);
			stmt.setString(2, cyear);
			stmt.setString(3, cperiod);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				CrSelectVo vo = new CrSelectVo();
				vo.setIndex(index);
				vo.setBshow(true);
				vo.setCtype("0");
				vo.setKey(rs.getString(1));
				vo.setItemkey(rs.getString(1));
				vo.setShowname(rs.getString(2));
				vo.setTable("a");
				vo.setWidth(80);
				list.add(vo);
				index++;
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
	 * 根据客商申请单单据主键查询附加客商基本档案VO
	 * @param billId
	 * @return
	 * @throws SQLException
	 */
	public SupplierVO querySupplierInfo(String billId) throws SQLException {
		SupplierVO vo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = " select bsupbaseinfo from bd_supplier_pf where dr=0 and pk_supplier_pf=?";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, billId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				//vo = (SupplierVO) byte2Object(rs.getBytes(1));
				vo = (SupplierVO) InOutUtil.deserialize(rs.getBytes(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return vo;
	}
	
	/**
	 * 根据自定义档案主键查询自定义档案名称
	 * @param pkDefdoc
	 * @return
	 * @throws SQLException
	 */
	public String queryBdDefName(String pkDefdoc) throws SQLException {
		String defName = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = " select name from bd_defdoc where dr=0 and pk_defdoc=?";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, pkDefdoc);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				defName = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return defName;
	}
	
	/**
	 * 字节流转OBJECT方法，后nc有InOutUtil.deserialize(byte[] b) 方法，故弃用
	 * @param bytes
	 * @return
	 */
	@Deprecated
	public Object byte2Object(byte[] bytes) {
		Object value = null;
        if(bytes != null) {
        	try {
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis);
                value = (Serializable)ois.readObject();
                bis.close();
                ois.close();
            } catch (IOException ex){
                ex.printStackTrace();
            } catch (ClassNotFoundException e){
    			e.printStackTrace();
    		}
        }
        return value;
	}
	
	/**
	 * 根据薪资发放申请单主键查询单据主表
	 * <p>2020-07-18 通用方法queryCrBillHead代替，已弃用</p>
	 * @param billId
	 * @return
	 * @throws SQLException
	 */
	@Deprecated
	public HashMap<String,String> queryWaBillHead(String billId) throws SQLException {
		HashMap<String,String> map = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = " select b.name as orgname,a.billcode,a.billname,a.applydate,c.user_name as operator,\r\n" + 
				" a.billstate,a.cyear,a.cperiod,b.name as classname,a.batch,a.pk_wa_class as waclass from wa_payroll a\r\n" + 
				" left join org_orgs b on a.pk_org=b.pk_org\r\n" + 
				" left join sm_user c on a.operator=c.cuserid\r\n" + 
				" left join wa_waclass d on a.pk_wa_class=d.pk_wa_class\r\n" + 
				" where a.dr=0 and a.pk_payroll=?";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, billId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				map = new HashMap<String,String>();
				map.put("orgname", rs.getString(1));
				map.put("billcode", rs.getString(2));
				map.put("billname", rs.getString(3));
				map.put("applydate", rs.getString(4));
				map.put("operator", rs.getString(5));
				map.put("billstate", rs.getString(6));
				map.put("cyear", rs.getString(7));
				map.put("cperiod", rs.getString(8));
				map.put("classname", rs.getString(9));
				map.put("batch", rs.getString(10));
				map.put("waclass", rs.getString(11));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return map;
	}
	
	/**
	 * 根据薪资发放申请单薪资类别、年、月查询单据子表
	 * <p>2020-07-18 通用方法queryCrBillBody代替，已弃用</p>
	 * @param waClass
	 * @param cyear
	 * @param cperiod
	 * @return
	 * @throws SQLException
	 */
	@Deprecated
	public ArrayList<HashMap<String,String>> queryWaBillBody(String waClass, int cyear, int cperiod) throws SQLException {
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = " select b.code,b.name,a.f_1,a.f_3,a.f_5,a.f_7 from wa_data a\r\n" + 
				"left join bd_psndoc b on a.pk_psndoc=b.pk_psndoc\r\n" + 
				"where a.dr=0 and a.pk_wa_class=? and a.cyear=? and a.cperiod=?";
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, waClass);
			stmt.setInt(2, cyear);
			stmt.setInt(3, cperiod);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("psncode", rs.getString(1));
				map.put("psnname", rs.getString(2));
				map.put("yfhj", rs.getString(3));
				map.put("sfhj", rs.getString(4));
				map.put("bcks", rs.getString(5));
				map.put("yks", rs.getString(6));
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

	public ArrayList<CrMsgInfoVo> queryMsgInfoByPsnId(String psnid, String isread) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<CrMsgInfoVo> msglist=new ArrayList<CrMsgInfoVo>();
		CrMsgInfoVo msginfo = null;
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";
		if (NCVERSION.equals("ncc"))
		{
			sql=sql + " select a.pk_detail,a.subject,a.content,a.billtype,b.billtypename,substr(sendtime,1,10) as sendtime, \n";
			sql=sql + " a.sender,d.user_name as sendername,f.shortname as orgname,g.name as deptname,h.pk_psndoc,a.receiver   \n";
			sql=sql + " from sm_msg_approve a \n";
			sql=sql + " left join bd_billtype b on a.billtype=b.pk_billtypecode   \n";
			sql=sql + " left join sm_msg_user c on a.pk_message=c.pk_message and c.destination = 'inbox'  \n";
			sql=sql + " left join sm_user d on a.sender=d.cuserid  \n";
			sql=sql + " left join bd_psnjob e on d.pk_psndoc=e.pk_psndoc and e.ismainjob='Y' and e.enddutydate is null \n";
			sql=sql + " left join org_orgs f on e.pk_org=f.pk_org \n";
			sql=sql + " left join org_dept g on e.pk_dept=g.pk_dept \n";
			sql=sql + " left join sm_user h on c.pk_user=h.cuserid  \n";
			sql=sql + " where nvl(a.dr,0)=0 and isdelete='N' and h.pk_psndoc=? and isread=? ";
		}
		else
		{
			sql=sql + " select a.pk_checkflow as pk_detail,a.messagenote as subject,a.billid as content,a.pk_billtype as billtype,b.billtypename,substr(senddate,1,10) as sendtime, \n";
			sql=sql + " a.senderman as sender,d.user_name as sendername,(case when nvl(f.shortname,'~')='~' then f.name else f.shortname end) as orgname,g.name as deptname,h.pk_psndoc,a.checkman as receiver from pub_workflownote a \n";
			sql=sql + " left join bd_billtype b on a.pk_billtype=b.pk_billtypecode  \n";
			sql=sql + " and exists (select c.pk_billtypecode,max(c.ts||c.PK_BILLTYPEID) from bd_billtype c  where c.pk_billtypecode=b.pk_billtypecode group by c.pk_billtypecode having max(c.ts||c.PK_BILLTYPEID)=b.ts||b.PK_BILLTYPEID) \n";
			sql=sql + " left join sm_user d on a.senderman=d.cuserid   \n";
			sql=sql + " left join bd_psnjob e on d.pk_psndoc=e.pk_psndoc and e.ismainjob='Y' and e.enddutydate is null  \n";
			sql=sql + " left join org_orgs f on e.pk_org=f.pk_org  \n";
			sql=sql + " left join org_dept g on e.pk_dept=g.pk_dept \n";
			sql=sql + " left join sm_user h on a.checkman=h.cuserid \n";
			sql=sql + " where nvl(a.dr,0)=0 and a.ismsgbind = 'N' and a.workflow_type in ( 2, 3, 6 ) and a.actiontype <> 'BIZ' \n";
			sql=sql + " and h.pk_psndoc=? " ;
			if (isread.equals("N"))
				sql=sql + " and a.approvestatus in ( 0, 2 ) \n";
			else
				sql=sql + " and a.approvestatus in ( 1 ) \n";
			sql=sql+" order by a.ts desc ";
		}
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, psnid);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				msginfo = new CrMsgInfoVo();
				msginfo.setPk_detail(rs.getString("pk_detail"));
				msginfo.setSubject(rs.getString("subject"));
				msginfo.setContent(rs.getString("content"));
				msginfo.setBilltype(rs.getString("billtype"));
				msginfo.setBilltypename(rs.getString("billtypename"));
				msginfo.setSendtime(rs.getString("sendtime"));
//				msginfo.setSender(rs.getString("sender"));
				msginfo.setSendername(rs.getString("sendername"));
				msginfo.setOrgname(rs.getString("orgname"));
				msginfo.setDeptname(rs.getString("deptname"));
//				msginfo.setPk_psndoc(rs.getString("pk_psndoc"));
//				msginfo.setReceiver(rs.getString("receiver"));
				msglist.add(msginfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			relaseConn(con, stmt, null);
		}
		return msglist;
	}
}
