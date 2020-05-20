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

public class ProfileDao {

	public static List<Map<String, Object>> getUserInfo(String USER_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select * from vw_user_all where USER_ID=? order by USER_ID");
            pst.setString(1, USER_ID);
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
	public String updateProfile(String fullname,String mobileno,String userid,String updateby) throws SQLException {
		   Connection con = DbCon.getConnection();
		   try {
			   PreparedStatement pst=con.prepareStatement("update WEB_USER set full_name=?,mobile_no=?,UPDATE_BY=?,UPDATE_DT=sysdate where user_id=?");
			   pst.setString(1, fullname);
			   pst.setString(2, mobileno);
			   pst.setString(3, updateby);
			   pst.setString(4, userid);
			   pst.executeUpdate();
			   
		   }catch(Exception e) {
			   e.printStackTrace();
			   return "Failed " + e;
		   }finally {
			   con.close();
			   
		   }
			return "Successfully saved!";
	}
	
	public static String loginLog(String USER_ID) throws SQLException{
		Connection con = DbCon.getConnection();
		  try {
			  PreparedStatement pst=con.prepareStatement("insert into LOGIN_LOG(USER_ID,CREATE_DT)values(?,sysdate)");
			   pst.setString(1, USER_ID);
			   pst.executeUpdate();
			   
		  }catch(Exception e) {
			   e.printStackTrace();
			   return "Failed " + e.getMessage();
		   }finally {
			   con.close();
			   
		   }
		  return "DONE";
			
	}
	public static String loginCount(String USER_ID) throws SQLException{
		Connection con = DbCon.getConnection();
		  try {
			  PreparedStatement pst=con.prepareStatement("select count(user_id)count from login_log where user_id=?");
			   pst.setString(1, USER_ID);			 
			   ResultSet rs = pst.executeQuery();
			   if(rs.next()) {
				   return rs.getString(1);
			   }
			   
		  }catch(Exception e) {
			   e.printStackTrace();
			   return "Failed " + e.getMessage();
		   }finally {
			   con.close();
			   
		   }
		  return null;
		
			
	}
	
	public static String firstTimePasswordCheck(String USER_ID) throws SQLException{
		Connection con = DbCon.getConnection();
		  try {
			  PreparedStatement pst=con.prepareStatement("select PASS_UPDATE from web_user where user_id=?");
			   pst.setString(1, USER_ID);			 
			   ResultSet rs = pst.executeQuery();
			   if(rs.next()) {
				   return rs.getString(1);
			   }
			   
		  }catch(Exception e) {
			   e.printStackTrace();
			   return "Failed " + e.getMessage();
		   }finally {
			   con.close();
			   
		   }
		  return null;
		
			
	}
	
}
