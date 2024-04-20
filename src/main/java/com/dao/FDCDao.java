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

public class FDCDao {
	public static List<Map<String, Object>> getFDCList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from vw_ftth_all_fdc");
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
	
	
	public static List<Map<String, Object>> getFDCListByOLT(String OLT_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from m_fdc where OLT_CODE=?");
            pst.setString(1, OLT_CODE);
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
	
	
	public static List<Map<String, Object>> getFDCListByALL(String REGION_CODE,String ZONE_CODE,String DISTRICT_CODE,String OFFICE_CODE,String OLT_CODE,String FDC_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

       
        try {
            PreparedStatement pst = con.prepareStatement("select * from VW_FTTH_ALL_FDC \r\n" + 
            		"	where REGION_CODE=NVL(?,REGION_CODE) and \r\n" + 
            		"	ZONE_CODE=NVL(?,ZONE_CODE)  and \r\n" + 
            		"	DISTRICT_CODE=NVL(?,DISTRICT_CODE) and \r\n" + 
            		"	OFFICE_CODE=NVL(?,OFFICE_CODE) and \r\n" + 
            		"	OLT_CODE=NVL(?,OLT_CODE) and\r\n" + 
            		"	FDC_CODE=NVL(?,FDC_CODE) order by OLT_CODE,FDC");
            pst.setString(1, REGION_CODE);
            pst.setString(2, ZONE_CODE);
            pst.setString(3, DISTRICT_CODE);
            pst.setString(4, OFFICE_CODE);
            pst.setString(5, OLT_CODE);
            pst.setString(6, FDC_CODE);
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
//	
	
	public static String getCurrentNepaliDate() throws SQLException {
	 Connection con = DbCon.getConnection();
	
		try {
			PreparedStatement pst = con.prepareStatement("select common.to_bs(sysdate) as nepalidate from dual");
			ResultSet rs = pst.executeQuery();
	
			if (rs.next()) {
				
				return rs.getString("nepalidate");
			
				
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	
	public static List<Map<String, Object>> getFDCListlowlvl(String User) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT *\r\n"
            		+ "  FROM vw_ftth_all_fdc VFAF\r\n"
            		+ " WHERE EXISTS\r\n"
            		+ "           (SELECT fdc_code\r\n"
            		+ "              FROM WEB_USER_FDC_MAP\r\n"
            		+ "             WHERE     user_id = ?\r\n"
            		+ "                   AND VFAF.FDC_CODE = WEB_USER_FDC_MAP.FDC_CODE)");
            pst.setString(1, User);
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
