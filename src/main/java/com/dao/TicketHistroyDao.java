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

public class TicketHistroyDao {

	
//	public List<Map<String, Object>> getComplainBySrvNo(String User, String MAIN_SRV_NO) throws SQLException {
//		Connection con = DbCon.getConnection();
//
//		try {
//			PreparedStatement pst = con
//					.prepareStatement("select a.* from\r\n" + 
//							" TOKEN_DETAIL a,\r\n" + 
//							"token_master b, main_token_master c\r\n" + 
//							" where a.sub_token_id=b.sub_token_id and\r\n" + 
//							"b.token_id=c.token_id and c.SRV_NO=? order by a.td_id");
//			pst.setString(1, MAIN_SRV_NO);
//
//			ResultSet rs = pst.executeQuery();
//
//			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
//			Map<String, Object> row = null;
//
//			ResultSetMetaData metaData = rs.getMetaData();
//			Integer columnCount = metaData.getColumnCount();
//
//			while (rs.next()) {
//				row = new HashMap<String, Object>();
//				for (int i = 1; i <= columnCount; i++) {
//					row.put(metaData.getColumnName(i), rs.getObject(i));
//				}
//				resultList.add(row);
//			}
//			return resultList;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			con.close();
//		}
//		return null;
//	}
	
	
	public List<Map<String, Object>> getComplainListDetailbyToken(String MAIN_SRV_NO) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry = "/* Formatted on 4/12/2020 1:09:32 AM (QP5 v5.354) */\r\n" + "  SELECT TOKENS.*,\r\n"
					+ "         (SELECT DESCRIPTION\r\n" + "            FROM M_SERVICE_TYPE\r\n"
					+ "           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC,\r\n"
					+ "         (SELECT DESCRIPTION\r\n" + "            FROM M_PROBLEM\r\n"
					+ "           WHERE PROBLEM_ID = TOKENS.PROBLEM_ID)              PROBLEM_DESC,\r\n"
					+ "           (SELECT DESCRIPTION||' '||FDC_LOCATION\r\n" + "            FROM M_FDC \r\n"
					+ "           WHERE FDC_CODE  = TOKENS.FDC_CODE )              FDC_DESC\r\n" +

					"    FROM (SELECT TM.TOKEN_ID,\r\n" + "                 SUB_TOKEN_ID,\r\n"
					+ "                 SERVICE_ID,\r\n" + "                 SRV_NO,\r\n"
					+ "                 COMPLAIN_NO,\r\n" + "                 CONTACT_NAME,\r\n"
					+ "                 TM.PROBLEM_ID,\r\n" + "                 TM.REMARKS,\r\n"
					+ "                 FDC_CODE,\r\n" + "                 TM.SUB_TEAM_CODE,\r\n"
					+ "                 TM.SOLVE_FLAG,\r\n" + "                 SERVICE_TYPE_ID,\r\n"
					+ "                 TM.CREATE_DT,\r\n" + "                 MTM.REMARKS MAIN_REMARKS\r\n"
					+ "            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM\r\n"
					+ "           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID\r\n" + "           and mtm.SRV_NO=?\r\n"
					+ "                ) TOKENS\r\n" + "ORDER BY token_ID, create_dt DESC";

			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, MAIN_SRV_NO);

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
