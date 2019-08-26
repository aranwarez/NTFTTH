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
public class CpDetailDao {

	public List<Map<String, Object>> getCpDetailList(String SP_CODE, String SERVICE_CODE, String CP_CODE)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(
					"SELECT a.cp_code, a.sp_code, a.service_code, a.esme_code, a.cp_name, (SELECT sp_name FROM m_sp WHERE sp_code=a.sp_code) sp_name,\n"
							+ "(SELECT rate FROM m_cp_detail \n" + "WHERE cp_code=a.cp_code \n"
							+ "AND effective_dt=(SELECT max(effective_dt) FROM m_cp_detail WHERE cp_code=a.cp_code)) RATE,\n"
							+ "(SELECT max(effective_dt) FROM m_cp_detail WHERE cp_code=a.cp_code) EFFECTIVE_DT,\n"
							+ "(SELECT common.to_bs(max(effective_dt)) FROM m_cp_detail WHERE cp_code=a.cp_code) NEP_EFFECTIVE_DT, \n"
							+ "((SELECT description FROM m_package_type WHERE package_type=a.package_type)|| '-'||a.package_type) PACKAGE_TYPE FROM m_cp a\n"
							+ "WHERE a.service_code in('APP','WAP','IVR')\n" + "AND a.sp_code=nvl(?, a.sp_code)\n"
							+ "AND a.service_code=nvl(?,a.service_code) AND a.cp_code=nvl(?,a.cp_code)\n"
							+ "ORDER BY a.sp_code, a.service_code, a.esme_code");
			pst.setString(1, SP_CODE);
			pst.setString(2, SERVICE_CODE);
			pst.setString(3, CP_CODE);
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

	public List<Map<String, Object>> getCpDetailListAll(String SP_CODE, String SERVICE_CODE, String CP_CODE)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con
					.prepareStatement("SELECT a.cp_code, a.sp_code, a.service_code, a.esme_code, a.cp_name,\n"
							+ "(SELECT sp_name FROM m_sp WHERE sp_code=a.sp_code) sp_name, b.RATE, b.effective_dt, common.to_bs(b.effective_dt) nep_effective_dt\n"
							+ "((SELECT description FROM m_package_type WHERE package_type=a.package_type)|| '-'||a.package_type) PACKAGE_TYPE FROM m_cp a, m_cp_detail b \n"
							+ "WHERE a.cp_code=b.cp_code AND a.service_code in('APP','WAP','IVR')\n"
							+ "AND a.sp_code=nvl(?, a.sp_code)\n"
							+ "AND a.service_code=nvl(?,a.service_code) AND a.cp_code=nvl(?,a.cp_code)\n"
							+ "ORDER BY a.sp_code, a.service_code, a.esme_code, effective_dt");
			pst.setString(1, SP_CODE);
			pst.setString(2, SERVICE_CODE);
			pst.setString(3, CP_CODE);
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

	public String saveCpDetail(String CP_CODE, String RATE, String EFFECTIVE_DT, String USER) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String qry = "INSERT INTO VASNTW.M_CP_DETAIL (\n" + "   TRANS_ID, CP_CODE, RATE, \n"
					+ "   EFFECTIVE_DT, CREATE_BY, CREATE_DT) \n" + "VALUES ( SEQ_CP_DETAIL_ID.NEXTVAL , ? , ?,\n"
					+ "    common.to_ad(?), ?, SYSDATE)";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, CP_CODE);
			pst.setString(2, RATE);
			pst.setString(3, EFFECTIVE_DT);
			pst.setString(4, USER);
			pst.executeUpdate();
			return "Succesfully Saved Detail Rate";
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String updateCpDetail(String TRANS_ID, String CP_CODE, String RATE, String EFFECTIVE_DT, String USER)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String qry = "UPDATE M_CP_DETAIL\n"
					+ "SET    CP_CODE      = ?,       RATE         = ?,       EFFECTIVE_DT = common.to_ad(?),\n"
					+ "       UPDATE_BY    = ?,       UPDATE_DT    = SYSDATE\n" + "WHERE  TRANS_ID     = ?";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, CP_CODE);
			pst.setString(2, RATE);
			pst.setString(3, EFFECTIVE_DT);
			pst.setString(4, USER);
			pst.setString(5, TRANS_ID);
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

	public String DeleteCp(String TRANS_ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("delete from M_CP_DETAIL where TRANS_ID=?");
			pst.setString(1, TRANS_ID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed Reason :" + e.getLocalizedMessage();
		} finally {
			con.close();
		}
		return "Successfully deleted Record .";
	}

	public List<Map<String, Object>> getCpRateList(String CP_CODE) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(
					"select a.*,common.to_bs(a.effective_dt) as NEP_DT from m_cp_detail a where cp_code=?");
			pst.setString(1, CP_CODE);
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
