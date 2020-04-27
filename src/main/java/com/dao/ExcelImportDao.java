/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import java.sql.CallableStatement;
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
public class ExcelImportDao {

  

    public String saveExcelHalfData(List<Map<String, Object>> JSONarrayList, String Filename, String IMP_YEAR,
            String Period, String AREA_ID, String Services, String NT_SP, String SERVICE_CODE, String IMP_PERIOD, String USER)
            throws SQLException {
        Connection con = DbCon.getConnection();
        con.setAutoCommit(false);
        try {
        	con.createStatement().executeUpdate("truncate table EXCEL2");;  
        
            
            PreparedStatement pst = con.prepareStatement("INSERT INTO EXCEL2(DISTRICT_NAME,DISTRICT_ID,AREANAME,AREA_ID,OLT_NAME,OLT_ID,FDC_NAME,FDC_ID,FDC_LOCATION)\r\n" + 
            		"VALUES(?,?,?,?,?,?,?,?,?)");
            int index = 1;
            for (Map<String, Object> obj : JSONarrayList) {
                pst.setString(1, (String) obj.get("0"));
                pst.setString(2, (String) obj.get("1"));
                pst.setString(3, (String) obj.get("2"));
                pst.setString(4, (String) obj.get("3"));
                pst.setString(5, (String) obj.get("4"));
                pst.setString(6, (String) obj.get("5"));
                pst.setString(7, (String) obj.get("6"));
                pst.setString(8, (String) obj.get("7"));
                pst.setString(9, (String) obj.get("8"));
            
                pst.executeUpdate();
                index = index + 1;
            }
            
//            ExcelImportDao dao=new ExcelImportDao();
           String msg= postExcelImportData();
            
            con.commit();
            return "Sucessfully imported Excel Data "+msg;
        } catch (Exception e) {
        	con.rollback();
            e.printStackTrace();
            return e.getMessage();
        } finally {
            con.close();
        }
    }
    
    public List<Map<String, Object>> getExcel2List() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select DISTINCT DISTRICT_NAME,DISTRICT_ID,AREANAME,AREA_ID,OLT_NAME,OLT_ID,FDC_NAME,FDC_ID,FDC_LOCATION\r\n" + 
            		"from EXCEL2");
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
    
    public String postExcelImportData() throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String getDBUSERByUserIdSql = "{call FTTH_INSERT_UPDATE_EXCEL2(?)}";
            CallableStatement pst = con.prepareCall(getDBUSERByUserIdSql);
            pst.registerOutParameter(1, java.sql.Types.VARCHAR);
            pst.executeUpdate();
            String result = pst.getString(1);
            return "Sucessfully Posted Excel imported Data "+result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to save " + e.getMessage();
        } finally {
            con.close();
        }
    }
    

}
