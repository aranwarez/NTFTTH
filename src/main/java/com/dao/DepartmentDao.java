package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DbCon;

public class DepartmentDao {
	public List<Map<String, Object>> getDepartmentList() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("select  *  from M_DEPARTMENT order by DESCRIPTION");
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

	public String saveDepartment(String DEPARTMENT, String ContactNo, String OFFICE_NAME, String AREA_NAME,
			String SECTION, String LANDLINE_NO, String MOBILE_NO, String REMARKS1, String REMARKS2, String USER)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			String qry = "/* Formatted on 5/27/2022 7:23:04 PM (QP5 v5.381) */\r\n"
					+ "INSERT INTO M_DEPARTMENT (DEPARTMENT_ID,\r\n"
					+ "                          DESCRIPTION,\r\n"
					+ "                          CONTACT_NO,\r\n"
					+ "                          CREATE_BY,\r\n"
					+ "                          CREATE_DT,\r\n"
					+ "                          OFFICE_NAME,\r\n"
					+ "                          AREA_NAME,\r\n"
					+ "                          SECTION,\r\n"
					+ "                          LANDLINE_NO,\r\n"
					+ "                          MOBILE_NO,\r\n"
					+ "                          REMARKS1,\r\n"
					+ "                          REMARKS2)\r\n"
					+ "     VALUES (DEPARTMENT_ID_SEQ.NEXTVAL,\r\n"
					+ "             ?,\r\n"
					+ "             ?,\r\n"
					+ "             ?,\r\n"
					+ "             SYSDATE,\r\n"
					+ "             ?,\r\n"
					+ "             ?,\r\n"
					+ "             ?,\r\n"
					+ "             ?,\r\n"
					+ "             ?,\r\n"
					+ "             ?,\r\n"
					+ "             ?)";

			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, DEPARTMENT);
			pst.setString(2, ContactNo);
			pst.setString(3, USER);
			pst.setString(4, OFFICE_NAME);
			pst.setString(5, AREA_NAME);
			pst.setString(6, SECTION);
			pst.setString(7, LANDLINE_NO);
			pst.setString(8, MOBILE_NO);
			pst.setString(9, REMARKS1);
			pst.setString(10, REMARKS2);
			
			

			pst.executeUpdate();

			return "Succesfully Saved Department";

		} catch (Exception e) {
		//	con.rollback();
			e.printStackTrace();
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String updateDepartment(String Department, String ContactNo,String OFFICE_NAME, String AREA_NAME,
			String SECTION, String LANDLINE_NO, String MOBILE_NO, String REMARKS1, String REMARKS2, String USER, String ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			String qry = "update  M_DEPARTMENT  set DESCRIPTION=?,CONTACT_NO=?,UPDATE_BY=?,UPDATE_DT=sysdate,OFFICE_NAME=?, \r\n"
					+ "AREA_NAME=?, \r\n"
					+ "SECTION=?, \r\n"
					+ "LANDLINE_NO=?, \r\n"
					+ "MOBILE_NO=?, \r\n"
					+ "REMARKS1=?, \r\n"
					+ "REMARKS2=? \r\n"
					+ "where DEPARTMENT_ID=?";

			PreparedStatement pst = con.prepareStatement(qry);

			pst.setString(1, Department);
			pst.setString(2, ContactNo);

			pst.setString(3, USER);
			pst.setString(4, OFFICE_NAME);
			pst.setString(5, AREA_NAME);
			pst.setString(6, SECTION);
			pst.setString(7, LANDLINE_NO);
			pst.setString(8, MOBILE_NO);
			pst.setString(9, REMARKS1);
			pst.setString(10, REMARKS2);
			
			pst.setString(11, ID);

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

	public String DeleteDepartment(String ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("delete from M_DEPARTMENT  where DEPARTMENT_ID=?");
			pst.setString(1, ID);

			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed Reason :" + e.getLocalizedMessage();
		} finally {
			con.close();
		}
		return "Record deleted successfully.";
	}

}
