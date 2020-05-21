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
	public List<Map<String, Object>> getSRVwiseRevenue(String username,String userlvl) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="  SELECT COUNT (TOKENS.SUB_TEAM_CODE)                     SCOUNT,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SUB_TEAM\r\n" + 
					"           WHERE SUB_TEAM_CODE = TOKENS.SUB_TEAM_CODE)    SERVICE_DESC\r\n" + 
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
					"           WHERE MTM.TOKEN_ID = TM.TOKEN_ID) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         AND TOKENS.SUB_TEAM_CODE = NVL (NULL, SUB_TEAM_CODE)\r\n" + 
					"         AND TOKENS.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
					"         AND TOKENS.CREATE_DT BETWEEN NVL (NULL, SYSDATE - 30)\r\n" + 
					"                                  AND NVL (NULL, SYSDATE)\r\n" + 
					"GROUP BY TOKENS.SUB_TEAM_CODE";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, username);
			
			//for lvl 6 custom officewise report
			if(userlvl.equals("6")) {
				qry="  SELECT COUNT (TOKENS.SUB_TEAM_CODE)                     SCOUNT,\r\n" + 
						"         (SELECT DESCRIPTION\r\n" + 
						"            FROM M_SUB_TEAM\r\n" + 
						"           WHERE SUB_TEAM_CODE = TOKENS.SUB_TEAM_CODE)    SERVICE_DESC\r\n" + 
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
						"                           WHERE     user_id = ?\r\n" + 
						"                                 AND MTM.FDC_CODE = WEB_USER_FDC_MAP.FDC_CODE))\r\n" + 
						"         TOKENS\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"         AND TOKENS.SUB_TEAM_CODE = NVL (NULL, SUB_TEAM_CODE)\r\n" + 
						"         AND TOKENS.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
						"         AND TOKENS.CREATE_DT BETWEEN NVL (NULL, SYSDATE - 30)\r\n" + 
						"                                  AND NVL (NULL, SYSDATE)\r\n" + 
						"GROUP BY TOKENS.SUB_TEAM_CODE";
				pst = con.prepareStatement(qry);
				pst.setString(1, username);
				pst.setString(2, username);
				
			}
			
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


	public List<Map<String, Object>> getSRVwisepayable(String user,String userlvl) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="  SELECT COUNT (TOKENS.SERVICE_TYPE_ID)                       SCOUNT,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SERVICE_TYPE\r\n" + 
					"           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC\r\n" + 
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
					"           WHERE MTM.TOKEN_ID = TM.TOKEN_ID) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         AND TOKENS.SUB_TEAM_CODE = NVL (NULL, SUB_TEAM_CODE)\r\n" + 
					"         AND TOKENS.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
					"         AND TOKENS.CREATE_DT BETWEEN NVL (NULL, SYSDATE - 30)\r\n" + 
					"                                  AND NVL (NULL, SYSDATE)\r\n" + 
					"GROUP BY TOKENS.SERVICE_TYPE_ID";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, user);
			//for lvl 6 custom officewise report
			if(userlvl.equals("6")) {
				qry="  SELECT COUNT (TOKENS.SERVICE_TYPE_ID)                       SCOUNT,\r\n" + 
						"         (SELECT DESCRIPTION\r\n" + 
						"            FROM M_SERVICE_TYPE\r\n" + 
						"           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC\r\n" + 
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
						"                           WHERE     user_id = ?\r\n" + 
						"                                 AND MTM.FDC_CODE = WEB_USER_FDC_MAP.FDC_CODE))\r\n" + 
						"         TOKENS\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"         AND TOKENS.SUB_TEAM_CODE = NVL (NULL, SUB_TEAM_CODE)\r\n" + 
						"         AND TOKENS.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
						"         AND TOKENS.CREATE_DT BETWEEN NVL (NULL, SYSDATE - 30)\r\n" + 
						"                                  AND NVL (NULL, SYSDATE)\r\n" + 
						"GROUP BY TOKENS.SERVICE_TYPE_ID";
				pst = con.prepareStatement(qry);
				pst.setString(1, user);
				pst.setString(2, user);
				
			}

			
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

	public List<Map<String, Object>> getSRVMonthly(String user) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="SELECT count(TOKENS.SERVICE_TYPE_ID) SCOUNT,\r\n" + 
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
					"";
			
			PreparedStatement pst = con.prepareStatement(qry);
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

	
	public List<Map<String, Object>> getSolvedvsUnsovled(String user,String userlvl) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="SELECT count(1) SCOUNT,\r\n" + 
					"         decode(SOLVE_FLAG,'N','NEW','Y','SOLVED','F','FORWARDED','C','CLOSED') SOLVE_FLAG\r\n" + 
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
					"                 ) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"        \r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE=NVL (null, SUB_TEAM_CODE)\r\n" + 
					"                         and TOKENS.SERVICE_TYPE_ID=NVL (null, SERVICE_TYPE_ID)\r\n" + 
					"                         and TOKENS.CREATE_DT between nvl(null,sysdate-30) and nvl(null,sysdate) \r\n" + 
					"                         group by TOKENS.SOLVE_FLAG";
			
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, user);
			if(userlvl.equals("6")) {
				qry="SELECT count(1) SCOUNT,\r\n" + 
						"         decode(SOLVE_FLAG,'N','NEW','Y','SOLVED','F','FORWARDED','C','CLOSED') SOLVE_FLAG\r\n" + 
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
						"           AND EXISTS\r\n" + 
						"                         (SELECT fdc_code\r\n" + 
						"                            FROM WEB_USER_FDC_MAP\r\n" + 
						"                           WHERE     user_id = ?\r\n" + 
						"                                 AND MTM.FDC_CODE = WEB_USER_FDC_MAP.FDC_CODE)\r\n" + 
						"                 ) TOKENS\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"        \r\n" + 
						"                         AND TOKENS.SUB_TEAM_CODE=NVL (null, SUB_TEAM_CODE)\r\n" + 
						"                         and TOKENS.SERVICE_TYPE_ID=NVL (null, SERVICE_TYPE_ID)\r\n" + 
						"                         and TOKENS.CREATE_DT between nvl(null,sysdate-30) and nvl(null,sysdate) \r\n" + 
						"                         group by TOKENS.SOLVE_FLAG";
				pst = con.prepareStatement(qry);
				pst.setString(1, user);
				pst.setString(2, user);
				
			}

			
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
