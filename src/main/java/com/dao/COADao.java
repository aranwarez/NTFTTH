/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author nabin
 */
public class COADao {
	public List<Map<String, Object>> getCOAlist() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("SELECT a.* FROM M_CHART_OF_ACCOUNTS a");

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

	public String Save(String COMP_CODE, String SLDG_CODE, String ENG_DESC, String NEP_DESC, String PARENT_SLDG_CODE,
			String STATEMENT_TYPE, String ACTIVE_FLAG, String DR_CR_FLAG, String AC_TYPE, String REMARKS, String User)
			throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);

		String tPARENT_SLDG_CODE = null;
		String tAC_Type = null;
		try {
			PreparedStatement pst1 = con.prepareStatement(
					"select a.PARENT_SLDG_CODE,a.STATEMENT_TYPE from M_CHART_OF_ACCOUNTS a where SLDG_CODE=?");
			pst1.setString(1, SLDG_CODE);
			ResultSet rs = pst1.executeQuery();
			while (rs.next()) {
				tPARENT_SLDG_CODE = rs.getString("PARENT_SLDG_CODE");
				tAC_Type = rs.getString("AC_TYPE");

			}
			// next level ko lagi-- parent same hunchha
			if (tAC_Type.equals("T")) {
				PreparedStatement pst2 = con
						.prepareStatement("update M_CHART_OF_ACCOUNTS set AC_TYPE=? where SLDG_CODE=?");
				pst2.setString(1, "F");
				pst2.setString(2, tPARENT_SLDG_CODE);
				pst2.executeUpdate();
			}

			PreparedStatement pst = con.prepareStatement(
					"insert into M_CHART_OF_ACCOUNTS" + "(COMP_CODE,SLDG_CODE,ENG_DESC,PARENT_SLDG_CODE,STATEMENT_TYPE,"
							+ "ACTIVE_FLAG,DR_CR_FLAG,AC_TYPE," + "REMARKS,CREATE_BY)values(?,?,?,?,?,?,?,?,?,?)");
			pst.setString(1, "G0001");
			pst.setString(2, SLDG_CODE);// G0001
			pst.setString(3, ENG_DESC);

			pst.setString(4, PARENT_SLDG_CODE);
			pst.setString(5, STATEMENT_TYPE);
			pst.setString(6, ACTIVE_FLAG);
			pst.setString(7, DR_CR_FLAG);
			pst.setString(8, "T");
			pst.setString(9, REMARKS);
			pst.setString(10, User);
			pst.executeUpdate();
			con.commit();
			return "Sucessfully Saved";

		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			return e.getLocalizedMessage().toString();
		} finally {
			con.close();
		}

	}

	public String Update(String COMP_CODE, String SLDG_CODE, String ENG_DESC, String STATEMENT_TYPE, String ACTIVE_FLAG,
			String DR_CR_FLAG, String REMARKS, String User) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement(
					"update M_CHART_OF_ACCOUNTS SET ENG_DESC=?,STATEMENT_TYPE=?,DR_CR_FLAG=?,REMARKS=?,UPDATE_BY=?,UPDATE_DT=sysdate where SLDG_CODE=?");

			pst.setString(1, ENG_DESC);
			pst.setString(2, STATEMENT_TYPE);
			pst.setString(3, DR_CR_FLAG);
			pst.setString(4, REMARKS);
			pst.setString(5, User);
			pst.setString(6, SLDG_CODE);
			pst.executeUpdate();

			return "Sucessfully Saved";

		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage().toString();
		} finally {
			con.close();
		}

	}

	public String delete(String SLDG_CODE, String User) throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			PreparedStatement pst1 = con.prepareStatement(
					"select a.SLDG_CODE,a.STATEMENT_TYPE,a.ENG_DESC from M_CHART_OF_ACCOUNTS a where PARENT_SLDG_CODE=?");
			pst1.setString(1, SLDG_CODE);
			ResultSet rs = pst1.executeQuery();
			if (rs.next()) {
				return "You can not delete the parent node please first delete child node  ";
			} else {

				PreparedStatement pst = con.prepareStatement("delete from M_CHART_OF_ACCOUNTS where SLDG_CODE=?");
				pst.setString(1, SLDG_CODE);
				pst.executeUpdate();

				pst = con.prepareStatement(
						"select * from m_chart_of_accounts where sldg_code=(select PARENT_SLDG_CODE from m_chart_of_accounts where sldg_code=?);");
				pst.setString(1, SLDG_CODE);
				rs = pst.executeQuery();
				if (!rs.next()) {
					pst = con.prepareStatement(
							"update M_CHART_OF_ACCOUNTS SET AC_TYPE=?,UPDATE_BY=?,UPDATE_DT=sysdate where SLDG_CODE=?");

					pst.setString(1, "T");
					pst.setString(2, User);
					pst.setString(3, SLDG_CODE);
					pst.executeUpdate();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		} finally {
			con.close();
		}
		return "Delete Successfully";
	}

}

