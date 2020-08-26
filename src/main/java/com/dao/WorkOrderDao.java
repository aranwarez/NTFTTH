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

import util.DbCon;

public class WorkOrderDao {
	public List<Map<String, Object>> getWorkOrderList() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement(
					"select woe.DESCRIPTION,wo.* from workorder wo,WORK_ORDER_ELEMENT woe where wo.ELEMENT_TYPE=woe.ID order by wo.ID");
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
			String modifier = "?";

			if (!fdc.isEmpty()) {
				modifier = "(select olt from VW_FTTH_ALL_FDC where fdc=?)";
			}
			String qry = "INSERT INTO FTTH.WORKORDER (ID,\r\n" + "                            ELEMENT_TYPE,\r\n"
					+ "                            ELEMENT_VALUE,\r\n" + "                            REMARKS,\r\n"
					+ "                            STARTTIME,\r\n" + "                            CREATE_BY,\r\n"
					+ "                            ACTIVE_FLAG,OLT,FDC)\r\n" + "     VALUES (WO_ID.NEXTVAL,\r\n"
					+ "             ?,\r\n" + "             ?,\r\n" + "             ?,\r\n" + "             ?,\r\n"
					+ "             ?,\r\n" + "             ?," + modifier + ",?)";

			PreparedStatement pst = con.prepareStatement(qry);

			// SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			Date sqldateDate = (java.util.Date) formatter.parse(starttime.replace("T", " "));
			java.sql.Date sqlStartDate = new java.sql.Date(sqldateDate.getTime());
			pst.setString(1, type);
			pst.setString(2, value);
			pst.setString(3, remarks);
			// pst.setDate(4, starttime.valueOf(datetimeLocal.replace("T"," ")));
			pst.setDate(4, sqlStartDate);
			pst.setString(5, USER);
			pst.setString(6, active_flag);
			if (fdc.isEmpty()) {
				pst.setString(7, olt);
			} else
				pst.setString(7, fdc);
			pst.setString(8, fdc);

			pst.executeUpdate();

			return "Succesfully Saved Item";

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
				}
				else if ((rs.getInt("ELEMENT_TYPE") == 2)) {
					qry = "select * from VW_TOKEN_MASTER_ONLY where main_solve_flag<>'C' and FDC=?  and main_create_dt between ? and ?";
					pst = con.prepareStatement(qry);
					pst.setString(1, rs.getString("ELEMENT_VALUE"));
					pst.setDate(2, rs.getDate("STARTTIME"));
					pst.setDate(3, sqlendtime);
				}
				else {
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
				}

				// sending sms

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

}
