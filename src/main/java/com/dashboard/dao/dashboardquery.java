package com.dashboard.dao;

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

public class dashboardquery {
	public List<Map<String, Object>> getSRVwiseRevenue() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("SELECT count(TOKENS.SUB_TEAM_CODE) SCOUNT,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SUB_TEAM\r\n" + 
					"           WHERE SUB_TEAM_CODE = TOKENS.SUB_TEAM_CODE)    SERVICE_DESC\r\n" + 
					"        \r\n" + 
					"    FROM (SELECT TM.TOKEN_ID,\r\n" + 
					"                 SUB_TOKEN_ID,\r\n" + 
					"                 SERVICE_ID,\r\n" + 
					"                 SRV_NO,\r\n" + 
					"                 COMPLAIN_NO,\r\n" + 
					"                 CONTACT_NAME,\r\n" + 
					"                 TM.PROBLEM_ID,\r\n" + 
					"                 TM.REMARKS,\r\n" + 
					"                 FDC_CODE,\r\n" + 
					"                 TM.SUB_TEAM_CODE,\r\n" + 
					"                 TM.SOLVE_FLAG,\r\n" + 
					"                 SERVICE_TYPE_ID,\r\n" + 
					"                 TM.CREATE_DT\r\n" + 
					"            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM\r\n" + 
					"           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID\r\n" + 
					"                 AND EXISTS\r\n" + 
					"                         (SELECT fdc_code\r\n" + 
					"                            FROM WEB_USER_FDC_MAP\r\n" + 
					"                           WHERE user_id = 'ARAN')) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = 'ARAN'\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"        \r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE=NVL (null, SUB_TEAM_CODE)\r\n" + 
					"                         and TOKENS.SERVICE_TYPE_ID=NVL (null, SERVICE_TYPE_ID)\r\n" + 
					"                         and TOKENS.CREATE_DT between nvl(null,sysdate-30) and nvl(null,sysdate) \r\n" + 
					"                         group by TOKENS.SUB_TEAM_CODE" + 
					"");
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


	public List<Map<String, Object>> getSRVwisepayable() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("SELECT count(TOKENS.SERVICE_TYPE_ID) SCOUNT,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SERVICE_TYPE\r\n" + 
					"           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC\r\n" + 
					"        \r\n" + 
					"    FROM (SELECT TM.TOKEN_ID,\r\n" + 
					"                 SUB_TOKEN_ID,\r\n" + 
					"                 SERVICE_ID,\r\n" + 
					"                 SRV_NO,\r\n" + 
					"                 COMPLAIN_NO,\r\n" + 
					"                 CONTACT_NAME,\r\n" + 
					"                 TM.PROBLEM_ID,\r\n" + 
					"                 TM.REMARKS,\r\n" + 
					"                 FDC_CODE,\r\n" + 
					"                 TM.SUB_TEAM_CODE,\r\n" + 
					"                 TM.SOLVE_FLAG,\r\n" + 
					"                 SERVICE_TYPE_ID,\r\n" + 
					"                 TM.CREATE_DT\r\n" + 
					"            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM\r\n" + 
					"           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID\r\n" + 
					"                 AND EXISTS\r\n" + 
					"                         (SELECT fdc_code\r\n" + 
					"                            FROM WEB_USER_FDC_MAP\r\n" + 
					"                           WHERE user_id = 'ARAN')) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = 'ARAN'\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"        \r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE=NVL (null, SUB_TEAM_CODE)\r\n" + 
					"                         and TOKENS.SERVICE_TYPE_ID=NVL (null, SERVICE_TYPE_ID)\r\n" + 
					"                         and TOKENS.CREATE_DT between nvl(null,sysdate-30) and nvl(null,sysdate) \r\n" + 
					"                         group by TOKENS.SERVICE_TYPE_ID\r\n" + 
					"");
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

	public List<Map<String, Object>> getSRVMonthly() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("SELECT count(TOKENS.SERVICE_TYPE_ID) SCOUNT,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SERVICE_TYPE\r\n" + 
					"           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC\r\n" + 
					"        \r\n" + 
					"    FROM (SELECT TM.TOKEN_ID,\r\n" + 
					"                 SUB_TOKEN_ID,\r\n" + 
					"                 SERVICE_ID,\r\n" + 
					"                 SRV_NO,\r\n" + 
					"                 COMPLAIN_NO,\r\n" + 
					"                 CONTACT_NAME,\r\n" + 
					"                 TM.PROBLEM_ID,\r\n" + 
					"                 TM.REMARKS,\r\n" + 
					"                 FDC_CODE,\r\n" + 
					"                 TM.SUB_TEAM_CODE,\r\n" + 
					"                 TM.SOLVE_FLAG,\r\n" + 
					"                 SERVICE_TYPE_ID,\r\n" + 
					"                 TM.CREATE_DT\r\n" + 
					"            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM\r\n" + 
					"           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID\r\n" + 
					"                 AND EXISTS\r\n" + 
					"                         (SELECT fdc_code\r\n" + 
					"                            FROM WEB_USER_FDC_MAP\r\n" + 
					"                           WHERE user_id = 'ARAN')) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = 'ARAN'\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"        \r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE=NVL (null, SUB_TEAM_CODE)\r\n" + 
					"                         and TOKENS.SERVICE_TYPE_ID=NVL (null, SERVICE_TYPE_ID)\r\n" + 
					"                         and TOKENS.CREATE_DT between nvl(null,sysdate-30) and nvl(null,sysdate) \r\n" + 
					"                         group by TOKENS.SERVICE_TYPE_ID\r\n" + 
					"");
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

	
	public List<Map<String, Object>> test() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("SELECT count(1) SCOUNT,\r\n" + 
					"         SOLVE_FLAG\r\n" + 
					"        \r\n" + 
					"    FROM (SELECT TM.TOKEN_ID,\r\n" + 
					"                 SUB_TOKEN_ID,\r\n" + 
					"                 SERVICE_ID,\r\n" + 
					"                 SRV_NO,\r\n" + 
					"                 COMPLAIN_NO,\r\n" + 
					"                 CONTACT_NAME,\r\n" + 
					"                 TM.PROBLEM_ID,\r\n" + 
					"                 TM.REMARKS,\r\n" + 
					"                 FDC_CODE,\r\n" + 
					"                 TM.SUB_TEAM_CODE,\r\n" + 
					"                 TM.SOLVE_FLAG,\r\n" + 
					"                 SERVICE_TYPE_ID,\r\n" + 
					"                 TM.CREATE_DT\r\n" + 
					"            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM\r\n" + 
					"           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID\r\n" + 
					"                 AND EXISTS\r\n" + 
					"                         (SELECT fdc_code\r\n" + 
					"                            FROM WEB_USER_FDC_MAP\r\n" + 
					"                           WHERE user_id = 'ARAN')) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = 'ARAN'\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"        \r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE=NVL (null, SUB_TEAM_CODE)\r\n" + 
					"                         and TOKENS.SERVICE_TYPE_ID=NVL (null, SERVICE_TYPE_ID)\r\n" + 
					"                         and TOKENS.CREATE_DT between nvl(null,sysdate-30) and nvl(null,sysdate) \r\n" + 
					"                         group by TOKENS.SOLVE_FLAG");
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
