package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smpp.SendSMS;
import com.soap.dao.WorkOrderAPI;

import util.DbCon;

public class WorkOrderDao {
	
	public List<Map<String, Object>> getlowlvlWorkOrderList(String REGION_CODE, String ZONE_CODE, String DISTRICT_CODE,
			String OFFICE_CODE, String QFROM_DT, String QTO_DT, String qtype, String ACTIVE_FLAG,String User) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="\r\n" + 
					"SELECT wo.*,\r\n" + 
					"       vfad.*,\r\n" + 
					"       woe.*,\r\n" + 
					"       wo.id     WOID\r\n" + 
					"  FROM WORKORDER WO, VW_FTTH_ALL_OLT VFAD, WORK_ORDER_ELEMENT WOE\r\n" + 
					" WHERE     EXISTS\r\n" + 
					"               (SELECT DISTINCT (VFAF.OLT)\r\n" + 
					"                  FROM WEB_USER_FDC_MAP WUFM, VW_FTTH_ALL_FDC VFAF\r\n" + 
					"                 WHERE     WUFM.FDC_CODE = VFAF.FDC_CODE\r\n" + 
					"                       AND WUFM.user_id = ?\r\n" + 
					"                       AND VFAF.OLT = WO.OLT)\r\n" + 
					"       AND WO.OLT = VFAD.OLT\r\n" + 
					"       AND WO.ELEMENT_TYPE = woe.id\r\n" + 
					"       AND element_type = NVL (?, element_type)\r\n" + 
					"       AND region_code = NVL (?, region_code)\r\n" + 
					"       AND zone_code = NVL (?, zone_code)\r\n" + 
					"       AND district_code = NVL (?, district_code)\r\n" + 
					"       AND office_code = NVL (?, office_code)\r\n" + 
					"       AND active_flag = NVL (?, active_flag)\r\n" + 
					"       AND create_dt BETWEEN NVL (common.to_ad (?), SYSDATE - 30)\r\n" + 
					"                         AND NVL (common.to_ad (?), SYSDATE)";
			PreparedStatement pst = con.prepareStatement(qry);
			//System.out.println(qry);
			pst.setString(1, User);
			pst.setString(2, qtype);
			pst.setString(3, REGION_CODE);
			pst.setString(4, ZONE_CODE);
			pst.setString(5, DISTRICT_CODE);
			pst.setString(6, OFFICE_CODE);
			pst.setString(7, ACTIVE_FLAG);
			pst.setString(8, QFROM_DT);
			pst.setString(9, QTO_DT);

			ResultSet rs = pst.executeQuery();

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> row = null;

			ResultSetMetaData metaData = rs.getMetaData();
			Integer columnCount = metaData.getColumnCount();

			while (rs.next()) {
				row = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				resultList.add(row);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	
	public List<Map<String, Object>> getWorkOrderList(String REGION_CODE, String ZONE_CODE, String DISTRICT_CODE,
			String OFFICE_CODE, String QFROM_DT, String QTO_DT, String qtype, String ACTIVE_FLAG) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="SELECT wo.*,vfad.*,woe.*, wo.id WOID\r\n" + "  FROM WORKORDER WO, VW_FTTH_ALL_OLT VFAD, WORK_ORDER_ELEMENT WOE\r\n"
					+ " WHERE     WO.OLT = VFAD.OLT\r\n" + "       AND WO.ELEMENT_TYPE = woe.id\r\n"
					+ "       AND element_type = NVL (?, element_type)\r\n"
					+ "       AND region_code = NVL (?,region_code)\r\n"
					+ "       AND zone_code = NVL (?, zone_code)\r\n"
					+ "       AND district_code = NVL (?,district_code)\r\n"
					+ "       AND office_code = NVL (?, office_code)\r\n"
					+ "       AND active_flag = NVL (?, active_flag)\r\n"
					+ "       AND create_dt BETWEEN NVL (common.to_ad(?), SYSDATE - 30) AND NVL (common.to_ad(?), SYSDATE)";
			PreparedStatement pst = con.prepareStatement(qry);
			
			pst.setString(1, qtype);
			pst.setString(2, REGION_CODE);
			pst.setString(3, ZONE_CODE);
			pst.setString(4, DISTRICT_CODE);
			pst.setString(5, OFFICE_CODE);
			pst.setString(6, ACTIVE_FLAG);
			pst.setString(7, QFROM_DT);
			pst.setString(8, QTO_DT);

			ResultSet rs = pst.executeQuery();

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> row = null;

			ResultSetMetaData metaData = rs.getMetaData();
			Integer columnCount = metaData.getColumnCount();

			while (rs.next()) {
				row = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				resultList.add(row);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	public List<Map<String, Object>> getActiveWorkOrder() throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(
					"select woe.DESCRIPTION,wo.* from workorder wo,WORK_ORDER_ELEMENT woe where wo.ELEMENT_TYPE=woe.ID and wo.ACTIVE_FLAG='Y' order by wo.ID");
			ResultSet rs = pst.executeQuery();

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> row = null;

			ResultSetMetaData metaData = rs.getMetaData();
			Integer columnCount = metaData.getColumnCount();

			while (rs.next()) {
				row = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				resultList.add(row);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	public List<Map<String, Object>> getWorkOrderElementList() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("select  *  from Work_Order_Element order by ID");
			ResultSet rs = pst.executeQuery();

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> row = null;

			ResultSetMetaData metaData = rs.getMetaData();
			Integer columnCount = metaData.getColumnCount();

			while (rs.next()) {
				row = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				resultList.add(row);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	public String saveWorkOrder(String type, String value, String remarks, String starttime, String active_flag,
			String USER, String olt, String fdc) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		try {
			String getseq="select WO_ID.NEXTVAL from dual";
			ResultSet seqrs=con.createStatement().executeQuery(getseq);
			String seq=null;
			while(seqrs.next()) {
				seq=seqrs.getString(1);
			}
			
			
			String modifier = "?";

			if (!fdc.isEmpty()) {
				modifier = "(select olt from VW_FTTH_ALL_FDC where fdc=?)";
			}
			String qry = "INSERT INTO WORKORDER (ID,\r\n" + "                            ELEMENT_TYPE,\r\n"
					+ "                            ELEMENT_VALUE,\r\n" + "                            REMARKS,\r\n"
					+ "                            STARTTIME,\r\n" + "                            CREATE_BY,\r\n"
					+ "                            ACTIVE_FLAG,OLT,FDC)\r\n" + "     VALUES (?,\r\n"
					+ "             ?,\r\n" + "             ?,\r\n" + "             ?,\r\n" + "             ?,\r\n"
					+ "             ?,\r\n" + "             ?," + modifier + ",?)";

			PreparedStatement pst = con.prepareStatement(qry);

			// SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			Date sqldateDate = (java.util.Date) formatter.parse(starttime.replace("T", " "));
			java.sql.Date sqlStartDate = new java.sql.Date(sqldateDate.getTime());
			pst.setString(1, seq);
			
			pst.setString(2, type);
			pst.setString(3, value);
			pst.setString(4, remarks);
			// pst.setDate(4, starttime.valueOf(datetimeLocal.replace("T"," ")));
			pst.setDate(5, sqlStartDate);
			pst.setString(6, USER);
			pst.setString(7, active_flag);
			if (fdc.isEmpty()) {
				pst.setString(8, olt);
			} else
				pst.setString(8, fdc);
			pst.setString(9, fdc);

			pst.executeUpdate();

			return seq;

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String completeWorkOrder(String ID, String USER, String remarks, String Enddate) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		try {

			String qry = "select * from WORKORDER where id=?";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, ID);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				// update the workorder
				qry = "update WORKORDER set remarks=?,endtime=?,update_by=?,update_dt=sysdate,active_flag='C' where id=?";
				pst = con.prepareStatement(qry);
				pst.setString(1, remarks);
				pst.setString(3, USER);
				pst.setString(4, ID);
				// SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date sqldateDate = (java.util.Date) formatter.parse(Enddate.replace("T", " "));
				java.sql.Date sqlendtime = new java.sql.Date(sqldateDate.getTime());
				pst.setDate(2, sqlendtime);
				pst.executeUpdate();
				// close all the ticket solved by work_order
				if (rs.getInt("ELEMENT_TYPE") == 1) {
					qry = "select * from VW_TOKEN_MASTER_ONLY where main_solve_flag<>'C' and substr(FAP_PORT,0,?)=?  and main_create_dt between ? and ?";
					pst = con.prepareStatement(qry);
					pst.setInt(1, rs.getString("ELEMENT_VALUE").length());
					pst.setString(2, rs.getString("ELEMENT_VALUE"));
					pst.setDate(3, rs.getDate("STARTTIME"));
					pst.setDate(4, sqlendtime);
				} else if ((rs.getInt("ELEMENT_TYPE") == 2)) {
					qry = "select * from VW_TOKEN_MASTER_ONLY where main_solve_flag<>'C' and FDC=?  and main_create_dt between ? and ?";
					pst = con.prepareStatement(qry);
					pst.setString(1, rs.getString("ELEMENT_VALUE"));
					pst.setDate(2, rs.getDate("STARTTIME"));
					pst.setDate(3, sqlendtime);
				} else if ((rs.getInt("ELEMENT_TYPE") == 3)) {
					qry = "select * from VW_TOKEN_MASTER_ONLY where main_solve_flag<>'C' and substr(ODF_PORT,0,?)=?  and main_create_dt between ? and ?";
					pst = con.prepareStatement(qry);
					pst.setInt(1, rs.getString("ELEMENT_VALUE").length());
					pst.setString(2, rs.getString("ELEMENT_VALUE"));
					pst.setDate(3, rs.getDate("STARTTIME"));
					pst.setDate(4, sqlendtime);
				} else {
					qry = "select * from VW_TOKEN_MASTER_ONLY where main_solve_flag<>'C' and substr(OLT_PORT,0,?)=?  and main_create_dt between ? and ?";
					pst = con.prepareStatement(qry);
					pst.setInt(1, rs.getString("ELEMENT_VALUE").length());
					pst.setString(2, rs.getString("ELEMENT_VALUE"));
					pst.setDate(3, rs.getDate("STARTTIME"));
					pst.setDate(4, sqlendtime);

				}

				ResultSet tokenrs = pst.executeQuery();

				while (tokenrs.next()) {
					System.out.println(tokenrs.getString("MASTER_SERVICE_NO"));
					String closeticketreport = Closeticket(tokenrs.getString("SUB_TOKEN_ID"), USER,
							"Ref WorkOrder:" + ID);
					qry = "INSERT INTO WORKORDER_TOKEN_LOG (WO_ID, SUB_TOKEN_ID, RESULT)\r\n" + "     VALUES (?, ?, ?)";
					pst = con.prepareStatement(qry);
					pst.setString(1, ID);
					pst.setString(2, tokenrs.getString("SUB_TOKEN_ID"));
					pst.setString(3, closeticketreport);
					pst.executeUpdate();

				}

				// sending sms to all customer
				WorkOrderAPI APIdao = new WorkOrderAPI();
				SendSMS smsdao=new SendSMS();
				String smsmsg="Dear Customer, \n We are pleased to inform about the restoration of your FTTH servies. Please dail 198 incase of further problems. \n -Nepal Telecom";
				List<String> MDN = APIdao.getFTTHNumberInfo(rs.getString("ELEMENT_TYPE"),
						rs.getString("ELEMENT_VALUE"));
				for (String mdn : MDN) {
					try {

						// send sms to these number regarding work order
				//		System.out.println(APIdao.getContactNumber(mdn));
						smsdao.sendsms(APIdao.getContactNumber(mdn), smsmsg , "WORKORDER", USER, ID);
						

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return "Succesfully Completed Work Order";

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String updateTeam(String TEAM_CODE, String DESCRIPTION, String USER) throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			String qry = "update  M_TEAM  set DESCRIPTION=?,UPDATE_BY=?,UPDATE_DT=sysdate\r\n" + "where TEAM_CODE=?";

			PreparedStatement pst = con.prepareStatement(qry);

			pst.setString(1, DESCRIPTION);
			pst.setString(2, USER);
			pst.setString(3, TEAM_CODE);

			pst.executeUpdate();

			return "Succesfully Updated";

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed to update : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String DeleteTeam(String TEAM_CODE) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("delete from M_TEAM  where TEAM_CODE=?");
			pst.setString(1, TEAM_CODE);

			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed Reason :" + e.getLocalizedMessage();
		} finally {
			con.close();
		}
		return "Record deleted successfully.";
	}

	public List<Map<String, Object>> getWebTeamList() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement(
					"select wt.*,mo.DESCRIPTION from web_team wt,M_OFFICE mo where wt.OFFICE_CODE = mo.OFFICE_CODE");
			ResultSet rs = pst.executeQuery();

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> row = null;

			ResultSetMetaData metaData = rs.getMetaData();
			Integer columnCount = metaData.getColumnCount();

			while (rs.next()) {
				row = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				resultList.add(row);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	// close tickets dao
	public String Closeticket(String closetoken, String User, String Remarks) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		PreparedStatement pst = null;
		try {
			// check validate if ticket team is assigned to session user
//				pst = con.prepareStatement(
//						"select sub_team_code from token_master where sub_token_id=? and exists (select SUB_TEAM_CODE from WEB_USER_TEAM_MAP where USER_ID=? and token_master.SUB_TEAM_CODE = WEB_USER_TEAM_MAP.SUB_TEAM_CODE)");
//				pst.setString(1, closetoken);
//				pst.setString(2, User);
//				ResultSet rsvalidateteam = pst.executeQuery();
//				if (!rsvalidateteam.next()) {
//					return ("Forbidden: Current Ticket Team isn't assigned to User!!!");
//
//				}
			// validation till here

			pst = con.prepareStatement("INSERT INTO TOKEN_DETAIL (TD_ID,\r\n"
					+ "                               SUB_TOKEN_ID,\r\n"
					+ "                               FROM_SUB_TEAM_CODE,\r\n"
					+ "                               TO_SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               CREATE_BY)\r\n"
					+ "     VALUES (TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + "             ?,\r\n"
					+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n"
					+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n" +

					"             'C',\r\n" + "             (SELECT PROBLEM_ID\r\n"
					+ "                FROM TOKEN_MASTER\r\n" + "               WHERE SUB_TOKEN_ID = ?),\r\n"
					+ "             ?,\r\n" + "             ?)");
			pst.setString(1, closetoken);
			pst.setString(2, closetoken);
			pst.setString(3, closetoken);
			pst.setString(4, closetoken);
			pst.setString(5, Remarks);
			pst.setString(6, User);
			pst.executeUpdate();

			// after closing ticket updating flag in token master
			pst = con.prepareStatement(
					"UPDATE TOKEN_MASTER SET SOLVE_FLAG= 'C',SOLVE_DT=sysdate,SOLVE_BY=?, UPDATE_BY=?,UPDATE_DT=sysdate WHERE  SUB_TOKEN_ID= ?");
			pst.setString(3, closetoken);
			pst.setString(2, User);
			pst.setString(1, User);

			pst.executeUpdate();

			// checking if all tickets are resolved

			pst = con.prepareStatement(
					"select * from token_master where solve_flag<>'C' and token_id=(select token_id from token_master where sub_token_id=?)");
			pst.setString(1, closetoken);
			ResultSet qsolveflag = pst.executeQuery();
			if (!qsolveflag.next()) {
				pst = con.prepareStatement("UPDATE MAIN_TOKEN_MASTER\r\n" + "   SET SOLVE_FLAG = 'C',\r\n"
						+ "       SOLVE_DT = SYSDATE,\r\n" + "       SOLVE_BY = ?,\r\n" +

						"       UPDATE_BY = ?,\r\n" + "       UPDATE_DT = SYSDATE\r\n"
						+ " WHERE token_id = (SELECT token_id\r\n" + "                     FROM token_master\r\n"
						+ "                    WHERE sub_token_id = ?)");
				pst.setString(1, User);

				pst.setString(2, User);
				pst.setString(3, closetoken);
				pst.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed " + e;
		} finally {
			con.close();
		}
		return "Successfully Trouble Ticket has been Resolved";
	}

}
