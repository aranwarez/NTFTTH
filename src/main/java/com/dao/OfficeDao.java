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

public class OfficeDao {
	public static List<Map<String, Object>> getOfficeList() throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select * from M_OFFICE order by OFFICE_CODE");
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
	
	
	public static List<Map<String, Object>> getOfficeListByDistrict(String DISTRICT_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select * from M_OFFICE where DISTRICT_CODE=? order by OFFICE_CODE");
//            DISTRICT_CODE
            pst.setString(1, DISTRICT_CODE);
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
	
	public static List<Map<String, Object>> getOfficeListByDistrictFDC(String DISTRICT_CODE,String userid) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select distinct(OFFICE_CODE  ),OFFICE  DESCRIPTION, DISTRICT_CODE  from VW_FTTH_ALL_FDC VFAF \r\n" + 
            		"where DISTRICT_CODE=? and \r\n" + 
            		"exists (select FDC_CODE  from WEB_USER_FDC_MAP where VFAF.FDC_CODE=WEB_USER_FDC_MAP.FDC_CODE and user_id=?)");
//            DISTRICT_CODE
            pst.setString(1, DISTRICT_CODE);
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
	
	public static List<Map<String, Object>> getOfficeListByALL(String REGION_CODE,String ZONE_CODE,String DISTRICT_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

       
        try {
            PreparedStatement pst = con.prepareStatement("select DISTINCT OFFICE_CODE,OFFICE from VW_FTTH_ALL_FDC \r\n" + 
            		"	where REGION_CODE=NVL(?,REGION_CODE) and \r\n" + 
            		"	ZONE_CODE=NVL(?,ZONE_CODE)  and \r\n" + 
            		"	DISTRICT_CODE=NVL(?,DISTRICT_CODE) order by OFFICE_CODE");
            pst.setString(1, REGION_CODE);
            pst.setString(2, ZONE_CODE);
            pst.setString(3, DISTRICT_CODE);
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
