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

public class MServiceDao {

    public List<Map<String, Object>> getServiceList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select SERVICE_ID,DESCRIPTION,\r\n" + 
            		"SHORT_CODE,common.to_BS(ACTIVE_DT) as ACTIVE_DT,\r\n" + 
            		"common.to_BS(DEACTIVE_DT) AS DEACTIVE_DT ,ACTIVE_STATUS,\r\n" + 
            		"CREATE_BY,CREATE_DT,\r\n" + 
            		"UPDATE_BY,UPDATE_DT\r\n" + 
            		"FROM M_SERVICE\r\n" + 
            		"ORDER BY SERVICE_ID");
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
    
    public String saveService(String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,
            String DEACTIVE_DT, String ACTIVE_STATUS, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "insert into m_service(SERVICE_ID,DESCRIPTION,\r\n" + 
            		"SHORT_CODE,ACTIVE_DT,\r\n" + 
            		"DEACTIVE_DT,ACTIVE_STATUS,CREATE_BY,CREATE_DT)\r\n" + 
            		"values((service_ID.nextval),?,?,common.to_ad(?),common.to_ad(?),?,?,sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, DESCRIPTION);
            pst.setString(2, SHORT_CODE);
            pst.setString(3, ACTIVE_DT);
            pst.setString(4, DEACTIVE_DT);
            pst.setString(5, ACTIVE_STATUS);
            pst.setString(6, USER);
           

            pst.executeUpdate();

            return "Succesfully Saved Item";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }
    

    public String updateService(String SERVICE_ID,String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,
            String DEACTIVE_DT, String ACTIVE_STATUS, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "update m_service set DESCRIPTION=?,\r\n" + 
            		"SHORT_CODE=?,ACTIVE_DT=common.to_ad(?),\r\n" + 
            		"DEACTIVE_DT=common.to_ad(?),ACTIVE_STATUS=?,UPDATE_BY=?,UPDATE_DT=sysdate\r\n" + 
            		"where SERVICE_ID=?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);
            pst.setString(2, SHORT_CODE);
            pst.setString(3, ACTIVE_DT);
            pst.setString(4, DEACTIVE_DT);
            pst.setString(5, ACTIVE_STATUS);
            pst.setString(6, USER);
            pst.setString(7, SERVICE_ID);
           

            pst.executeUpdate();

            return "Succesfully Updated";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to update : " + e.getMessage();
        } finally {
            con.close();
        }
    }
    
    public String DeleteService(String SERVICE_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from m_service  where SERVICE_ID=?");
            pst.setString(1, SERVICE_ID);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed Reason :" + e.getLocalizedMessage();
        } finally {
            con.close();
        }
        return "Record deleted successfully.";
    }
    
}
