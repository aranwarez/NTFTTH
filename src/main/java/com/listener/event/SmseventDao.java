package com.listener.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.dao.ProfileDao;
import com.smpp.SendSMS;

import util.DbCon;

public class SmseventDao {
	public void sendSMS() throws SQLException {
		Connection con = DbCon.getConnection();
		// con.setAutoCommit(false);
		try {
			String qry = "select user_id,role_code,mobile_no,user_level from web_user where REGEXP_LIKE(mobile_no, '^[[:digit:]]+$') and DISABLE_FLAG='N'";
			PreparedStatement pst = con.prepareStatement(qry);
			ResultSet rsuser = pst.executeQuery();
			while (rsuser.next()) {
				// getting count for each
				if (!rsuser.getString(4).equals("6")) {
					Map<String, String> regionmap=	ProfileDao.getSessionRegion(rsuser.getString(1), rsuser.getString(4));
					
					qry = "/* Formatted on 5/26/2020 1:59:48 AM (QP5 v5.354) */\r\n" + 
							"SELECT *\r\n" + 
							"  FROM (  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)    SCOUNT,\r\n" + 
							"                 DECODE (MASTER_SOLVE_FLAG,\r\n" + 
							"                         'N', 'NEW',\r\n" + 
							"                         'Y', 'SOLVED',\r\n" + 
							"                         'F', 'FORWARDED',\r\n" + 
							"                         'C', 'CLOSED')           SOLVE_FLAG\r\n" + 
							"            FROM (SELECT * FROM VW_TOKEN_MASTER_ONLY) VTM\r\n" + 
							"           WHERE EXISTS\r\n" + 
							"                     (SELECT *\r\n" + 
							"                        FROM WEB_USER_TEAM_MAP\r\n" + 
							"                       WHERE     USER_ID = ?\r\n" + 
							"                             AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
							"                                 WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
							"        --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
							"        --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
							"        --     AND VTM.MASTER_CREATE_DT BETWEEN NVL (NULL, SYSDATE - 30)\r\n" + 
							"        --                                  AND NVL (NULL, SYSDATE)\r\n" + 
							"               AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
							"                 AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
							"                 AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
							"                 AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
							"        GROUP BY VTM.MASTER_SOLVE_FLAG)\r\n" + 
							"       PIVOT (MAX (SCOUNT)\r\n" + 
							"             FOR SOLVE_FLAG\r\n" + 
							"             IN ('SOLVED' AS SOLVED,\r\n" + 
							"                'CLOSED' AS CLOSED,\r\n" + 
							"                'NEW' AS NEW,\r\n" + 
							"                'FORWARDED' AS FORWARD))";
					pst = con.prepareStatement(qry);
					pst.setString(1, rsuser.getString(1));
					pst.setString(2, regionmap.get("Region"));
					pst.setString(3, regionmap.get("Zone"));
					pst.setString(4, regionmap.get("District"));
					pst.setString(5, regionmap.get("Office"));
				} else {
					qry = "/* Formatted on 5/26/2020 2:11:28 AM (QP5 v5.354) */\r\n" + 
							"SELECT *\r\n" + 
							"  FROM (  SELECT COUNT (VTM.MASTER_SOLVE_FLAG)    SCOUNT,\r\n" + 
							"                 DECODE (MASTER_SOLVE_FLAG,\r\n" + 
							"                         'N', 'NEW',\r\n" + 
							"                         'Y', 'SOLVED',\r\n" + 
							"                         'F', 'FORWARDED',\r\n" + 
							"                         'C', 'CLOSED')           SOLVE_FLAG\r\n" + 
							"            FROM (SELECT *\r\n" + 
							"                    FROM VW_TOKEN_MASTER_ONLY\r\n" + 
							"                   WHERE EXISTS\r\n" + 
							"                             (SELECT fdc_code\r\n" + 
							"                                FROM WEB_USER_FDC_MAP\r\n" + 
							"                               WHERE     user_id = ?\r\n" + 
							"                                     AND VW_TOKEN_MASTER_ONLY.FDC_CODE =\r\n" + 
							"                                         WEB_USER_FDC_MAP.FDC_CODE)) VTM\r\n" + 
							"           WHERE EXISTS\r\n" + 
							"                     (SELECT *\r\n" + 
							"                        FROM WEB_USER_TEAM_MAP\r\n" + 
							"                       WHERE     USER_ID = ?\r\n" + 
							"                             AND VTM.MASTER_SUB_TEAM_CODE =\r\n" + 
							"                                 WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
							"        --      AND VTM.MASTER_SUB_TEAM_CODE = NVL (NULL, MASTER_SUB_TEAM_CODE)\r\n" + 
							"        --      AND VTM.SERVICE_TYPE_ID = NVL (NULL, SERVICE_TYPE_ID)\r\n" + 
							"        --     AND VTM.MASTER_CREATE_DT BETWEEN NVL (NULL, SYSDATE - 30)\r\n" + 
							"        --                                  AND NVL (NULL, SYSDATE)\r\n" + 
							"        --               AND VTM.REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
							"        --                 AND VTM.ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
							"        --                 AND VTM.DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
							"        --                 AND VTM.OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
							"        GROUP BY VTM.MASTER_SOLVE_FLAG)\r\n" + 
							"       PIVOT (MAX (SCOUNT)\r\n" + 
							"             FOR SOLVE_FLAG\r\n" + 
							"             IN ('SOLVED' AS SOLVED,\r\n" + 
							"                'CLOSED' AS CLOSED,\r\n" + 
							"                'NEW' AS NEW,\r\n" + 
							"                'FORWARDED' AS FORWARD))";
					pst = con.prepareStatement(qry);
					pst.setString(1, rsuser.getString(1));
					pst.setString(2, rsuser.getString(1));

				}

				ResultSet rscount = pst.executeQuery();
				if (rscount.next()) {
					String message = "FTTH USER:"+rsuser.getString(1).toLowerCase()+" Role: " + rsuser.getString(2) + " NEW:" + rscount.getString("NEW") + " Close:"
							+ rscount.getString("CLOSED") + " FORWARD:" + rscount.getString("FORWARD") + " SOLVED:"
							+ rscount.getString("SOLVED");

					try {
				
						SendSMS smsobj=new SendSMS();
						
								smsobj.sendsms(rsuser.getString(3), message, "AUTOSMSEVENT", "SYSTEM", "0");
					} catch (Exception e) {
						System.err.println("Failed to SMS EVENT to "+rsuser.getString(1));
						e.printStackTrace();
					}

				}

			}

			// con.commit();
		} catch (Exception e) {
			// con.rollback();
			e.printStackTrace();
		} finally {
			con.close();
		}

	}

}
