package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.DbCon;

public class ReportCountDao {

	public String DispatchReportCountlog(String USER_ID, String Region, String Zone,String District,String Office,String Frm_dt,String To_dt,String Team) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO REPORT_PRINT_COUNT\r\n"
					+ "    SELECT REPORT_COUNT.NEXTVAL     ID,\r\n" + "           SUB_TOKEN_ID,\r\n"
					+ "           'DISPATCH'               REPORT_NAME,\r\n" + "           '" + USER_ID
					+ "'                   PRINT_BY,\r\n" + "           SYSDATE                  PRINT_DT\r\n"
					+ "      FROM VW_TOKEN_MASTER_ONLY\r\n" + "     WHERE     MASTER_SUB_TEAM_CODE = 'SLMTN'\r\n"
					+ "           AND REGION_CODE = NVL (?, REGION_CODE)\r\n"
					+ "           AND ZONE_CODE = NVL (?, ZONE_CODE)\r\n"
					+ "           AND DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n"
					+ "           AND OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n"
					+ "           AND MASTER_CREATE_DT BETWEEN NVL (common.TO_AD (?),\r\n"
					+ "                                             SYSDATE - 30)\r\n"
					+ "                                    AND NVL (common.TO_AD (?), SYSDATE)\r\n"
					+ "           AND TEAM_ID = NVL (?, TEAM_ID)\r\n"
					+ "           AND MASTER_SOLVE_FLAG NOT IN ('C', 'Y')");
			pst.setString(1, Region);
			pst.setString(2,Zone);
			pst.setString(3,District);
			pst.setString(4,Office);
			pst.setString(5,Frm_dt);
			pst.setString(6,To_dt);
			pst.setString(7,Team);
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed " + e.getMessage();
		} finally {
			con.close();

		}
		return null;

	}

}
