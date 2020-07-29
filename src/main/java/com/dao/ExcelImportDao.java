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
            con.commit();     
//            ExcelImportDao dao=new ExcelImportDao();
           String msg= postExcelImportData();
            
       
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
		con.setAutoCommit(false);
		try {
			String getDBUSERByUserIdSql = "{call FTTH_INSERT_UPDATE_EXCEL2(?)}";
			CallableStatement pst = con.prepareCall(getDBUSERByUserIdSql);
			pst.registerOutParameter(1, java.sql.Types.VARCHAR);
			pst.executeUpdate();
			String result = pst.getString(1);
			
			// Syncronizing new FDC to users for office mapped users
			if (result.equals("Y")) {
				System.out.println("Syncing FDC user according to office mapped users");
				PreparedStatement qryloop = con.prepareStatement("select distinct(user_id) from WEB_USER_OFFICE_MAP");
				ResultSet rs = qryloop.executeQuery();
				while (rs.next()) {
					PreparedStatement pst6 = con.prepareStatement("delete from WEB_USER_FDC_MAP where USER_ID=?");

					pst6.setString(1, rs.getString(1));
					pst6.executeUpdate();

					PreparedStatement pst7 = con.prepareStatement(
							"insert into WEB_USER_FDC_MAP (WUTM_ID, USER_ID, FDC_CODE, ACTIVE_DT, DEACTIVE_DT, CREATE_BY, CREATE_DT) (\r\n"
									+ "select WUTM_ID.nextval WUTM_ID,? USER_ID ,FDC_CODE,sysdate ACTIVE_DT,sysdate DEACTIVE_DT ,'SYSTEM' CREATE_BY ,sysdate CREATE_DT  from VW_FTTH_ALL_FDC vfaf  where EXISTS  (select office_code from WEB_USER_OFFICE_MAP where vfaf.office_code=WEB_USER_OFFICE_MAP.office_code and WEB_USER_OFFICE_MAP.user_id=?))");

					pst7.setString(1, rs.getString(1));
					pst7.setString(2, rs.getString(1));
					pst7.executeUpdate();
				}
			}
			//
			con.commit();
			return "Result:" + result;
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			
			return "Failed to save " + e.getMessage();
		} finally {
			con.close();
		}
	}    

}
