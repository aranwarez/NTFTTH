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

public class DistrictDao {
	public static List<Map<String, Object>> getDistrictListByZone(String ZONE_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from M_DISTRICT where ZONE_CODE=? order by DISTRICT_CODE");            
            pst.setString(1, ZONE_CODE);
            
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
	public static List<Map<String, Object>> getDistrictListByZoneFDC(String ZONE_CODE,String userid) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select distinct(DISTRICT_CODE  ),DISTRICT DESCRIPTION,ZONE_CODE    from VW_FTTH_ALL_FDC VFAF \r\n" + 
            		"where exists (select FDC_CODE  from WEB_USER_FDC_MAP \r\n" + 
            		"where ZONE_CODE=? and \r\n" + 
            		"VFAF.FDC_CODE=WEB_USER_FDC_MAP.FDC_CODE and user_id=?) order by DISTRICT_CODE");            
            pst.setString(1, ZONE_CODE);
            pst.setString(2, userid);
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
