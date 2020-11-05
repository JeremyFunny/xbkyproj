package nc.impl.cr.approve;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nc.bs.framework.common.RuntimeEnv;
import nc.bs.logging.Logger;
import nc.vo.cr.model.CrJoinVo;
import nc.vo.cr.model.CrModelItemVo;
import nc.vo.cr.model.CrModelVo;
import nc.vo.cr.model.CrOrderVo;
import nc.vo.cr.model.CrSelectVo;
import nc.vo.cr.model.CrWhereVo;
import nc.vo.pub.BusinessException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <P>
 *   CR移动端单据审批H5展示模板工具类
 * </p>
 * @author zp
 * @date 2020-07-18
 */
public class CrModelUtils {
	
	/**
	 * 通过单据类型编码获取CR前端审批H5模板
	 * @param billType
	 * @return
	 */
	public static CrModelVo getBillApproveModel(String billType) throws BusinessException {
		CrModelVo model = null;
		if(billType==null || billType.length()==0) {
			return null;
		}
		String fileName = billType + ".xml";
		String filePath = getBillModelPath(fileName);
		if(filePath==null || filePath.length()==0) {
			return null;
		}
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(filePath));
			if(doc!=null) {
				model = new CrModelVo();
				model.setBilltype(billType);
				Element crmodel = (Element)doc.getElementsByTagName("CrModel").item(0);
				model.setTypename(crmodel.getAttribute("typename"));
				Element head = (Element)crmodel.getElementsByTagName("Head").item(0);
				if(head!=null) {
					CrModelItemVo headModel = new CrModelItemVo();
					headModel.setName(head.getAttribute("name"));
					headModel.setAlias(head.getAttribute("alias"));
					headModel.setPrimary(head.getAttribute("primary"));
					/**
					 * select
					 */
					Element select = (Element)head.getElementsByTagName("Select").item(0);
					if(select!=null) {
						NodeList selects=select.getElementsByTagName("Row");
						if(selects!=null && selects.getLength()>0) {
							CrSelectVo[] selectVos = new CrSelectVo[selects.getLength()];
							for(int i=0;i<selects.getLength();i++) {
								CrSelectVo selectVo = new CrSelectVo();
								Element row = (Element)selects.item(i);
								selectVo.setTable(row.getAttribute("table"));
								selectVo.setKey(row.getAttribute("key"));
								selectVo.setItemkey(row.getAttribute("itemkey"));
								selectVo.setBshow(getBooleanValue(row.getAttribute("bshow")));
								selectVo.setIndex(getIntValue(row.getAttribute("index")));
								selectVo.setShowname(row.getAttribute("showname"));
								selectVo.setCtype(row.getAttribute("ctype"));
								selectVos[i] = selectVo;
							}
							headModel.setSelectVos(selectVos);
						}
					}
					/**
					 * SqlStr
					 */
					Element sqlstr = (Element)head.getElementsByTagName("SqlStr").item(0);
					if(sqlstr!=null) {
						headModel.setSqlstr(sqlstr.getFirstChild().getTextContent());
					}
					/**
					 * join
					 */
					Element join = (Element)head.getElementsByTagName("Join").item(0);
					if(join!=null) {
						NodeList joins=join.getElementsByTagName("Row");
						if(joins!=null && joins.getLength()>0) {
							CrJoinVo[] joinVos = new CrJoinVo[joins.getLength()];
							for(int i=0;i<joins.getLength();i++) {
								CrJoinVo joinVo = new CrJoinVo();
								Element row = (Element)joins.item(i);
								joinVo.setAlias(row.getAttribute("alias"));
								joinVo.setName(row.getAttribute("name"));
								joinVo.setJoinon(row.getAttribute("joinon"));
								joinVos[i] = joinVo;
							}
							headModel.setJoinVos(joinVos);
						}
					}

					/**
					 * where
					 */
					Element where = (Element)head.getElementsByTagName("Where").item(0);
					if(where!=null) {
						NodeList wheres=where.getElementsByTagName("Row");
						if(wheres!=null && wheres.getLength()>0) {
							CrWhereVo[] whereVos = new CrWhereVo[wheres.getLength()];
							for(int i=0;i<wheres.getLength();i++) {
								CrWhereVo whereVo = new CrWhereVo();
								Element row = (Element)wheres.item(i);
								whereVo.setTable(row.getAttribute("table"));
								whereVo.setKey(row.getAttribute("key"));
								whereVo.setItemkey(row.getAttribute("itemkey"));
								whereVo.setBconst(getBooleanValue(row.getAttribute("bconst")));
								whereVo.setType(getIntValue(row.getAttribute("type")));
								whereVo.setValue(row.getAttribute("value"));
								whereVos[i] = whereVo;
							}
							headModel.setWhereVos(whereVos);
						}
					}
					
					/**
					 * order
					 */
					Element order = (Element)head.getElementsByTagName("Order").item(0);
					if(order!=null) {
						NodeList orders=order.getElementsByTagName("Row");
						if(orders!=null && orders.getLength()>0) {
							CrOrderVo[] orderVos = new CrOrderVo[orders.getLength()];
							for(int i=0;i<orders.getLength();i++) {
								CrOrderVo orderVo = new CrOrderVo();
								Element row = (Element)orders.item(i);
								orderVo.setTable(row.getAttribute("table"));
								orderVo.setKey(row.getAttribute("key"));
								orderVo.setType(getIntValue(row.getAttribute("type")));
								orderVos[i] = orderVo;
							}
							headModel.setOrderVos(orderVos);
						}
					}
					model.setHeadModel(headModel);
				}
				
				Element body = (Element)crmodel.getElementsByTagName("Body").item(0);
				if(body!=null) {
					CrModelItemVo bodyModel = new CrModelItemVo();
					bodyModel.setName(body.getAttribute("name"));
					bodyModel.setAlias(body.getAttribute("alias"));
					bodyModel.setPrimary(body.getAttribute("primary"));
					/**
					 * select
					 */
					Element select = (Element)body.getElementsByTagName("Select").item(0);
					if(select!=null) {
						NodeList selects=select.getElementsByTagName("Row");
						if(selects!=null && selects.getLength()>0) {
							CrSelectVo[] selectVos = new CrSelectVo[selects.getLength()];
							for(int i=0;i<selects.getLength();i++) {
								CrSelectVo selectVo = new CrSelectVo();
								Element row = (Element)selects.item(i);
								selectVo.setTable(row.getAttribute("table"));
								selectVo.setKey(row.getAttribute("key"));
								selectVo.setItemkey(row.getAttribute("itemkey"));
								selectVo.setBshow(getBooleanValue(row.getAttribute("bshow")));
								selectVo.setIndex(getIntValue(row.getAttribute("index")));
								selectVo.setShowname(row.getAttribute("showname"));
								selectVo.setWidth(getIntValue(row.getAttribute("width")));
								selectVo.setCtype(row.getAttribute("ctype"));
								selectVos[i] = selectVo;
							}
							bodyModel.setSelectVos(selectVos);
						}
						/**
						 * join
						 */
						Element join = (Element)body.getElementsByTagName("Join").item(0);
						if(join!=null) {
							NodeList joins=join.getElementsByTagName("Row");
							if(joins!=null && joins.getLength()>0) {
								CrJoinVo[] joinVos = new CrJoinVo[joins.getLength()];
								for(int i=0;i<joins.getLength();i++) {
									CrJoinVo joinVo = new CrJoinVo();
									Element row = (Element)joins.item(i);
									joinVo.setAlias(row.getAttribute("alias"));
									joinVo.setName(row.getAttribute("name"));
									joinVo.setJoinon(row.getAttribute("joinon"));
									joinVos[i] = joinVo;
								}
								bodyModel.setJoinVos(joinVos);
							}
						}
	
						/**
						 * where
						 */
						Element where = (Element)body.getElementsByTagName("Where").item(0);
						if(where!=null) {
							NodeList wheres=where.getElementsByTagName("Row");
							if(wheres!=null && wheres.getLength()>0) {
								CrWhereVo[] whereVos = new CrWhereVo[wheres.getLength()];
								for(int i=0;i<wheres.getLength();i++) {
									CrWhereVo whereVo = new CrWhereVo();
									Element row = (Element)wheres.item(i);
									whereVo.setTable(row.getAttribute("table"));
									whereVo.setKey(row.getAttribute("key"));
									whereVo.setItemkey(row.getAttribute("itemkey"));
									whereVo.setBconst(getBooleanValue(row.getAttribute("bconst")));
									whereVo.setType(getIntValue(row.getAttribute("type")));
									whereVo.setValue(row.getAttribute("value"));
									whereVos[i] = whereVo;
								}
								bodyModel.setWhereVos(whereVos);
							}
						}
						
						/**
						 * order
						 */
						Element order = (Element)body.getElementsByTagName("Order").item(0);
						if(order!=null) {
							NodeList orders=order.getElementsByTagName("Row");
							if(orders!=null && orders.getLength()>0) {
								CrOrderVo[] orderVos = new CrOrderVo[orders.getLength()];
								for(int i=0;i<orders.getLength();i++) {
									CrOrderVo orderVo = new CrOrderVo();
									Element row = (Element)orders.item(i);
									orderVo.setTable(row.getAttribute("table"));
									orderVo.setKey(row.getAttribute("key"));
									orderVo.setType(getIntValue(row.getAttribute("type")));
									orderVos[i] = orderVo;
								}
								bodyModel.setOrderVos(orderVos);
							}
						}
						model.setBodyModel(bodyModel);
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			Logger.error("CR前端查询单据模板出错,ParserConfigurationException,", e);
			throw new BusinessException("CR前端查询单据审批H5模板出错!", e);
		}  catch (SAXException e) {
			e.printStackTrace();
			Logger.error("CR前端查询单据模板出错,SAXException,", e);
			throw new BusinessException("CR前端查询单据审批H5模板出错!", e);
		} catch (IOException e) {
			e.printStackTrace();
			Logger.error("CR前端查询单据模板出错,IOException,", e);
			throw new BusinessException("CR前端查询单据审批H5模板出错!", e);
		}
		return model;
	}
	
	/**
	 * 获取目标绩效表体显示模版
	 * @param string 
	 * @param vo
	 * @return
	 */
	public static ArrayList<HashMap<String,Object>> getOkrTempletBody(String billType) throws BusinessException {
		ArrayList<HashMap<String,Object>> okrtemplet=new ArrayList<HashMap<String,Object>>();
		if(billType==null || billType.length()==0) {
			return null;
		}
		String fileName = billType + ".xml";
		String filePath = getBillModelPath(fileName);
		if(filePath==null || filePath.length()==0) {
			return null;
		}
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(filePath));
			if(doc!=null) {
				Element crmodel = (Element)doc.getElementsByTagName("CrModel").item(0);
				/**
				 * select
				 */
				Element select = (Element)crmodel.getElementsByTagName("Select").item(0);
				if(select!=null) {
					NodeList selects=select.getElementsByTagName("Row");
					if(selects!=null && selects.getLength()>0) {
						for(int i=0;i<selects.getLength();i++) {
							Element row = (Element)selects.item(i);
							HashMap<String,Object> itemMap = new HashMap<String,Object>();
							itemMap.put("itemkey", row.getAttribute("itemkey"));
							itemMap.put("showname", row.getAttribute("showname"));
							itemMap.put("index", getIntValue(row.getAttribute("index")));
							itemMap.put("width", getIntValue(row.getAttribute("width")));
							itemMap.put("ctype", row.getAttribute("ctype"));
							okrtemplet.add(itemMap);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			Logger.error("CR前端查询单据模板出错,ParserConfigurationException,", e);
			throw new BusinessException("CR前端查询单据审批H5模板出错!", e);
		}  catch (SAXException e) {
			e.printStackTrace();
			Logger.error("CR前端查询单据模板出错,SAXException,", e);
			throw new BusinessException("CR前端查询单据审批H5模板出错!", e);
		} catch (IOException e) {
			e.printStackTrace();
			Logger.error("CR前端查询单据模板出错,IOException,", e);
			throw new BusinessException("CR前端查询单据审批H5模板出错!", e);
		}
	
		return okrtemplet;
	}
	
	private static boolean getBooleanValue(String value) {
		return value!=null && value.equals("Y")?true:false;
	}
	
	private static int getIntValue(String value) {
		return Integer.valueOf(value);
	}
	
	/**
	 * 获取模板文件路劲
	 * @param filename
	 * @return
	 */
	public static  String getBillModelPath(String fileName) {
		String path="";
		if(fileName==null) {
			return null;
		}
//		path = CrModelUtils.class.getClassLoader().getResource("").getPath();
		path = getNC_HOME()
				+ File.separator
				+ "modules"
				+ File.separator
				+ "baseapp"
				+ File.separator
				+ "config"
				+ File.separator
				+ "crwebconfig"
				+ File.separator
				+ fileName;
		
		return path;
	}
	

	/**
	 * 获得NC_HOME路径。
	 * 
	 */
	public static String getNC_HOME() {
		return RuntimeEnv.getInstance().getNCHome();
	}
	
}
