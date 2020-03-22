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

public class MServiceTypeDao {
	
	 public List<Map<String, Object>> getServiceTypeList() throws SQLException {
	        Connection con = DbCon.getConnection();

	        try {
	            PreparedStatement pst = con.prepareStatement("select  \r\n" + 
	            		"(select DESCRIPTION from M_SERVICE where service_id=M_SERVICE_TYPE.service_id) as SERVICE_DESC,SERVICE_TYPE_ID,SERVICE_ID,\r\n" + 
	            		"DESCRIPTION,SHORT_CODE,\r\n" + 
	            		"common.to_BS(ACTIVE_DT) as ACTIVE_DT,\r\n" + 
	            		"common.to_BS(DEACTIVE_DT) as DEACTIVE_DT,\r\n" + 
	            		"ACTIVE_STATUS,CREATE_BY,CREATE_DT,\r\n" + 
	            		"UPDATE_BY,UPDATE_DT\r\n" + 
	            		" from M_SERVICE_TYPE\r\n" + 
	            		"order by SERVICE_TYPE_ID");
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
	 
	 public String saveServiceType(String SERVICE_ID,String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,
	            String DEACTIVE_DT, String ACTIVE_STATUS, String USER) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {

	            String qry = "insert into M_SERVICE_TYPE(SERVICE_ID,DESCRIPTION,\r\n" + 
	            		"SHORT_CODE,ACTIVE_DT,\r\n" + 
	            		"DEACTIVE_DT,ACTIVE_STATUS,CREATE_BY,CREATE_DT,SERVICE_TYPE_ID)\r\n" + 
	            		"values(?,?,?,common.to_ad(?),common.to_ad(?),?,?,sysdate,(SERVICE_TYPE_ID.nextval))\r\n" + 
	            		"";

	            PreparedStatement pst = con.prepareStatement(qry);
	            pst.setString(1, SERVICE_ID);
	            pst.setString(2, DESCRIPTION);
	            pst.setString(3, SHORT_CODE);
	            pst.setString(4, ACTIVE_DT);
	            pst.setString(5, DEACTIVE_DT);
	            pst.setString(6, ACTIVE_STATUS);
	            pst.setString(7, USER);
	           

	            pst.executeUpdate();

	            return "Succesfully Saved Service Type";

	        } catch (Exception e) {
	            con.rollback();
	            e.printStackTrace();
	            return "Failed to Save : " + e.getMessage();
	        } finally {
	            con.close();
	        }
	    }
	 
	 public String updateServiceType(String SERVICE_TYPE_ID,String SERVICE_ID,String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,
	            String DEACTIVE_DT, String ACTIVE_STATUS, String USER) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {

	            String qry = "update M_SERVICE_TYPE set SERVICE_ID=?,DESCRIPTION=?,\r\n" + 
	            		"SHORT_CODE=?,ACTIVE_DT=common.to_ad(?),\r\n" + 
	            		"DEACTIVE_DT=common.to_ad(?),ACTIVE_STATUS=?,UPDATE_BY=?,UPDATE_DT=sysdate\r\n" + 
	            		"where SERVICE_TYPE_ID=?";

	            PreparedStatement pst = con.prepareStatement(qry);
	            
	            pst.setString(1, SERVICE_ID);
	            pst.setString(2, DESCRIPTION);
	            pst.setString(3, SHORT_CODE);
	            pst.setString(4, ACTIVE_DT);
	            pst.setString(5, DEACTIVE_DT);
	            pst.setString(6, ACTIVE_STATUS);
	            pst.setString(7, USER);
	            pst.setString(8, SERVICE_TYPE_ID);
	           

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
	 
	 public String DeleteServiceType(String SERVICE_TYPE_ID) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {
	            PreparedStatement pst = con.prepareStatement("delete from M_SERVICE_TYPE  where SERVICE_TYPE_ID=?");
	            pst.setString(1, SERVICE_TYPE_ID);

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
