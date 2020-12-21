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
	public List<Map<String, Object>> getTicketwiseTeam(String username,String userlvl,String region,String zone,String district,String office,String fromdate,String todate) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="/* Formatted on 12/8/2020 8:17:24 PM (QP5 v5.354) */\r\n" + 
					"  SELECT COUNT (VTM.MASTER_SUB_TEAM_CODE)                     SCOUNT,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SUB_TEAM\r\n" + 
					"           WHERE SUB_TEAM_CODE = VTM.MASTER_SUB_TEAM_CODE)    SERVICE_DESC\r\n" + 
					"    FROM (SELECT * FROM VW_TOKEN_MASTER_ONLY) VTM\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
					"         --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
					"         AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
					"         AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
					"         AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
					"         AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
					"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
					"                                      AND NVL (?, SYSDATE)\r\n" + 
					"GROUP BY VTM.MASTER_SUB_TEAM_CODE";
		//	System.out.println(qry);
			
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, username);
			pst.setString(2, region);
			pst.setString(3, zone);
			pst.setString(4, district);
			pst.setString(5, office);
			pst.setString(6, fromdate);
			pst.setString(7, todate);
			
			
			//for lvl 6 custom officewise report
			if(userlvl.equals("6")) {
				qry="/* Formatted on 5/26/2020 12:43:12 AM (QP5 v5.354) */\r\n" + 
						"  SELECT COUNT (VTM.MASTER_SUB_TEAM_CODE)                     SCOUNT,\r\n" + 
						"         (SELECT DESCRIPTION\r\n" + 
						"            FROM M_SUB_TEAM\r\n" + 
						"           WHERE SUB_TEAM_CODE = VTM.MASTER_SUB_TEAM_CODE)    SERVICE_DESC\r\n" + 
						"    FROM (SELECT *\r\n" + 
						"            FROM VW_TOKEN_MASTER_ONLY\r\n" + 
						"           WHERE EXISTS\r\n" + 
						"                     (SELECT fdc_code\r\n" + 
						"                        FROM WEB_USER_FDC_MAP\r\n" + 
						"                       WHERE     user_id = ?\r\n" + 
						"                             AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
						"                                 WEB_USER_FDC_MAP.FDC_CODE)) VTM\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"         --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
						"         --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
						"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
						"                                      AND NVL (?, SYSDATE)\r\n" + 
						"         --AND VTM.REGION_CODE = NVL (null, REGION_CODE)\r\n" + 
						"         --AND VTM.ZONE_CODE = NVL (NULL, ZONE_CODE)\r\n" + 
						"         --AND VTM.DISTRICT_CODE = NVL (NULL, DISTRICT_CODE)\r\n" + 
						"         --AND VTM.OFFICE_CODE = NVL (NULL, OFFICE_CODE)\r\n" + 
						"GROUP BY VTM.MASTER_SUB_TEAM_CODE";
			
				pst = con.prepareStatement(qry);
				pst.setString(1, username);
				pst.setString(2, username);
				pst.setString(3, fromdate);
				pst.setString(4, todate);
				
				
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

	//query for Service Wise Trouble Ticket
	public List<Map<String, Object>> getSRVwiseTeam(String username,String userlvl,String region,String zone,String district,String office,String fromdate,String todate) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="/* Formatted on 12/15/2020 11:25:10 AM (QP5 v5.354) */\r\n" + 
					"  SELECT COUNT (VTM.SERVICE_TYPE_ID)                       SCOUNT,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SERVICE_TYPE\r\n" + 
					"           WHERE SERVICE_TYPE_ID = VTM.SERVICE_TYPE_ID)    SERVICE_DESC\r\n" + 
					"    FROM (SELECT * FROM VW_TOKEN_MASTER_ONLY) VTM\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
					"         --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
					"         AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
					"         AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
					"         AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
					"         AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
					"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
					"                                      AND NVL (?, SYSDATE)\r\n" + 
					"GROUP BY VTM.SERVICE_TYPE_ID";
		
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, username);
			pst.setString(2, region);
			pst.setString(3, zone);
			pst.setString(4, district);
			pst.setString(5, office);
			pst.setString(6, fromdate);
			pst.setString(7, todate);
			
			//for lvl 6 custom officewise report
			if(userlvl.equals("6")) {
				qry="/* Formatted on 5/26/2020 12:43:12 AM (QP5 v5.354) */\r\n" + 
						"  SELECT COUNT (VTM.SERVICE_TYPE_ID)                     SCOUNT,\r\n" + 
						"         (SELECT DESCRIPTION\r\n" + 
						"            FROM M_SERVICE_TYPE\r\n" + 
						"           WHERE SERVICE_TYPE_ID = VTM.SERVICE_TYPE_ID)    SERVICE_DESC\r\n" + 
						"    FROM (SELECT *\r\n" + 
						"            FROM VW_TOKEN_MASTER_ONLY\r\n" + 
						"           WHERE EXISTS\r\n" + 
						"                     (SELECT fdc_code\r\n" + 
						"                        FROM WEB_USER_FDC_MAP\r\n" + 
						"                       WHERE     user_id = ?\r\n" + 
						"                             AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
						"                                 WEB_USER_FDC_MAP.FDC_CODE)) VTM\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"         --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
						"         --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
						"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
						"                                      AND NVL (?, SYSDATE)\r\n" + 
						"         --AND VTM.REGION_CODE = NVL (null, REGION_CODE)\r\n" + 
						"         --AND VTM.ZONE_CODE = NVL (NULL, ZONE_CODE)\r\n" + 
						"         --AND VTM.DISTRICT_CODE = NVL (NULL, DISTRICT_CODE)\r\n" + 
						"         --AND VTM.OFFICE_CODE = NVL (NULL, OFFICE_CODE)\r\n" + 
						"GROUP BY VTM.SERVICE_TYPE_ID";
				
				pst = con.prepareStatement(qry);
				pst.setString(1, username);
				pst.setString(2, username);
				pst.setString(3, fromdate);
				pst.setString(4, todate);
				
				
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

	//query for status of tickets
	public List<Map<String, Object>> getSolvedvsUnsovled(String username,String userlvl,String region,String zone,String district,String office,String fromdate,String todate) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="/* Formatted on 12/15/2020 12:01:01 PM (QP5 v5.354) */\r\n" + 
					"  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)    SCOUNT,\r\n" + 
					"         DECODE (MASTER_SOLVE_FLAG,\r\n" + 
					"                 'N', 'NEW',\r\n" + 
					"                 'Y', 'SOLVED',\r\n" + 
					"                 'F', 'FORWARDED',\r\n" + 
					"                 'C', 'CLOSED')           SOLVE_FLAG\r\n" + 
					"    FROM (SELECT * FROM VW_TOKEN_MASTER_ONLY) VTM\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
					"         --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
					"         AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
					"         AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
					"         AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
					"         AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
					"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
					"                                      AND NVL (?, SYSDATE)\r\n" + 
					"GROUP BY VTM.MASTER_SOLVE_FLAG";
			//System.out.println(qry);
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, username);
			pst.setString(2, region);
			pst.setString(3, zone);
			pst.setString(4, district);
			pst.setString(5, office);
			pst.setString(6, fromdate);
			pst.setString(7, todate);
		
			if(userlvl.equals("6")) {
				qry="/* Formatted on 5/26/2020 1:04:15 AM (QP5 v5.354) */\r\n" + 
						"  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)    SCOUNT,\r\n" + 
						"         DECODE (MASTER_SOLVE_FLAG,\r\n" + 
						"                 'N', 'NEW',\r\n" + 
						"                 'Y', 'SOLVED',\r\n" + 
						"                 'F', 'FORWARDED',\r\n" + 
						"                 'C', 'CLOSED')           SOLVE_FLAG\r\n" + 
						"    FROM (SELECT *\r\n" + 
						"            FROM VW_TOKEN_MASTER_ONLY\r\n" + 
						"           WHERE EXISTS\r\n" + 
						"                     (SELECT fdc_code\r\n" + 
						"                        FROM WEB_USER_FDC_MAP\r\n" + 
						"                       WHERE     user_id = ?\r\n" + 
						"                             AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
						"                                 WEB_USER_FDC_MAP.FDC_CODE)) VTM\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"         --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
						"         --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
						"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
						"                                      AND NVL (?, SYSDATE)\r\n" + 
						"--  AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
						"--  AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
						"--  AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
						"--  AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
						"GROUP BY VTM.MASTER_SOLVE_FLAG";
				System.out.println(qry);
				pst = con.prepareStatement(qry);
				pst.setString(1, username);
				pst.setString(2, username);
				pst.setString(3, fromdate);
				pst.setString(4, todate);
				
				
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
	

	public List<Map<String, Object>> subTeamSolveUnsolve(String username,String userlvl,String region,String zone,String district,String office,String fromdate,String todate) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="/* Formatted on 12/15/2020 12:16:05 PM (QP5 v5.354) */\r\n" + 
					"  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)    SCOUNT,\r\n" + 
					"         MASTER_SUB_TEAM_CODE             sub_team_code,\r\n" + 
					"         DECODE (MASTER_SOLVE_FLAG,\r\n" + 
					"                 'N', 'NEW',\r\n" + 
					"                 'Y', 'SOLVED',\r\n" + 
					"                 'F', 'FORWARDED',\r\n" + 
					"                 'C', 'CLOSED')           SOLVE_FLAG\r\n" + 
					"    FROM (SELECT * FROM VW_TOKEN_MASTER_ONLY--           WHERE EXISTS\r\n" + 
					"                                            --                     (SELECT fdc_code\r\n" + 
					"                                            --                        FROM WEB_USER_FDC_MAP\r\n" + 
					"                                            --                       WHERE     user_id = ?\r\n" + 
					"                                            --                             AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
					"                                            --                                 WEB_USER_FDC_MAP.FDC_CODE)\r\n" + 
					"                                            ) VTM\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
					"         AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
					"         AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
					"         AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
					"         AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
					"         AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
					"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
					"                                      AND NVL (?, SYSDATE)\r\n" + 
					"GROUP BY MASTER_SUB_TEAM_CODE, VTM.MASTER_SOLVE_FLAG\r\n" + 
					"ORDER BY MASTER_SUB_TEAM_CODE, VTM.MASTER_SOLVE_FLAG";
			
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, username);
			pst.setString(2, region);
			pst.setString(3, zone);
			pst.setString(4, district);
			pst.setString(5, office);
			pst.setString(6, fromdate);
			pst.setString(7, todate);
		
			if(userlvl.equals("6")) {
				qry="/* Formatted on 5/26/2020 1:04:15 AM (QP5 v5.354) */\r\n" + 
						"  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)    SCOUNT,\r\n" + 
						"  MASTER_SUB_TEAM_CODE sub_team_code,\r\n" + 
						"         DECODE (MASTER_SOLVE_FLAG,\r\n" + 
						"                 'N', 'NEW',\r\n" + 
						"                 'Y', 'SOLVED',\r\n" + 
						"                 'F', 'FORWARDED',\r\n" + 
						"                 'C', 'CLOSED')           SOLVE_FLAG\r\n" + 
						"    FROM (SELECT *\r\n" + 
						"            FROM VW_TOKEN_MASTER_ONLY\r\n" + 
						"           WHERE EXISTS\r\n" + 
						"                     (SELECT fdc_code\r\n" + 
						"                        FROM WEB_USER_FDC_MAP\r\n" + 
						"                       WHERE     user_id = ?\r\n" + 
						"                             AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
						"                                 WEB_USER_FDC_MAP.FDC_CODE)\r\n" + 
						"                                 ) VTM\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"              AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
						"               AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
						"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
						"                                      AND NVL (?, SYSDATE)\r\n" + 
						"--  AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
						"--  AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
						"--  AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
						"--  AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
						"GROUP BY  MASTER_SUB_TEAM_CODE,VTM.MASTER_SOLVE_FLAG order by MASTER_SUB_TEAM_CODE,VTM.MASTER_SOLVE_FLAG";
				
			//	System.out.println(qry);
				pst = con.prepareStatement(qry);
				pst.setString(1, username);
				pst.setString(2, username);
				pst.setString(3, fromdate);
				pst.setString(4, todate);
				
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
	
	
	//query for Ticket Type Vs Sub Team
	public List<Map<String, Object>> subTeamServiceType(String username,String userlvl,String region,String zone,String district,String office,String fromdate,String todate) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry="/* Formatted on 12/15/2020 12:39:35 PM (QP5 v5.354) */\r\n" + 
					"  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)                     SCOUNT,\r\n" + 
					"         MASTER_SUB_TEAM_CODE                              sub_team_code,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SERVICE_TYPE\r\n" + 
					"           WHERE SERVICE_TYPE_ID = VTM.SERVICE_TYPE_ID)    SERVICE_TYPE\r\n" + 
					"    FROM (SELECT * FROM VW_TOKEN_MASTER_ONLY--           WHERE EXISTS\r\n" + 
					"                                            --                     (SELECT fdc_code\r\n" + 
					"                                            --                        FROM WEB_USER_FDC_MAP\r\n" + 
					"                                            --                       WHERE     user_id = ?\r\n" + 
					"                                            --                             AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
					"                                            --                                 WEB_USER_FDC_MAP.FDC_CODE)\r\n" + 
					"                                            ) VTM\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
					"         AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
					"         AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
					"         AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
					"         AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
					"         AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
					"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
					"                                      AND NVL (?, SYSDATE)\r\n" + 
					"GROUP BY MASTER_SUB_TEAM_CODE, VTM.SERVICE_TYPE_ID\r\n" + 
					"ORDER BY MASTER_SUB_TEAM_CODE";
			
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, username);
			pst.setString(2, region);
			pst.setString(3, zone);
			pst.setString(4, district);
			pst.setString(5, office);
			pst.setString(6, fromdate);
			pst.setString(7, todate);
			
		
			if(userlvl.equals("6")) {
				qry="/* Formatted on 5/26/2020 1:04:15 AM (QP5 v5.354) */\r\n" + 
						"  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)    SCOUNT,\r\n" + 
						"  MASTER_SUB_TEAM_CODE sub_team_code,\r\n" + 
						"         (select DESCRIPTION  from M_SERVICE_TYPE where SERVICE_TYPE_ID=VTM.SERVICE_TYPE_ID) SERVICE_TYPE\r\n" + 
						"    FROM (SELECT *\r\n" + 
						"            FROM VW_TOKEN_MASTER_ONLY\r\n" + 
						"           WHERE EXISTS\r\n" + 
						"                     (SELECT fdc_code\r\n" + 
						"                        FROM WEB_USER_FDC_MAP\r\n" + 
						"                       WHERE     user_id = ?\r\n" + 
						"                             AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
						"                                 WEB_USER_FDC_MAP.FDC_CODE)\r\n" + 
						"                                 ) \r\n" + 
						"                                 VTM\r\n" + 
						"   WHERE     EXISTS\r\n" + 
						"                 (SELECT *\r\n" + 
						"                    FROM WEB_USER_TEAM_MAP\r\n" + 
						"                   WHERE     USER_ID = ?\r\n" + 
						"                         AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
						"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
						"              AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
						"               AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
						"         AND VTM.MASTER_CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
						"                                      AND NVL (?, SYSDATE)\r\n" + 
						"--  AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
						"--  AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
						"--  AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
						"--  AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
						"GROUP BY  MASTER_SUB_TEAM_CODE,VTM.SERVICE_TYPE_ID order by MASTER_SUB_TEAM_CODE";
				//System.out.println(qry);
				pst = con.prepareStatement(qry);
				pst.setString(1, username);
				pst.setString(2, username);
				pst.setString(3, fromdate);
				pst.setString(4, todate);
				
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
