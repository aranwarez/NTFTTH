package com.aaa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.AAAModel;

import util.DbCon;

public class AAAStatusDao {
	public AAAModel getAAAstatus(String Servniceno) throws SQLException {
        Connection con = DbCon.getAAAConnection();
        String username = null ;
        try {
            PreparedStatement pst = con.prepareStatement("select useraccount,  qoslevel, reserstr3 from rad_userinf where useraccount=?");
            pst.setString(1,Servniceno);
            
            ResultSet rs = pst.executeQuery();
            
          
            AAAModel model=new AAAModel();
             
            while (rs.next()) {
          if(rs.getString(3).equals("AAAA")) {
        	  username=(rs.getString(1));  
        	  
          }
          else username=(rs.getString(3));
          model.setPLAN(rs.getString(2));
           }
            model.setOldUSERID(username);
            
            pst = con.prepareStatement("select useraccount, address  from rad_userport  where useraccount=?");
            pst.setString(1,username);
            rs = pst.executeQuery();
           while(rs.next()) {
        	  model.setFCALLERID(rs.getString(2));
           } 
          
           pst = con.prepareStatement("select callingno, loginip, starttime, caller_id from rad_session where useraccount=?");
           pst.setString(1,username);
           rs = pst.executeQuery();
           while(rs.next()) {
         	  model.setUSERID(rs.getString(1));
         	  model.setLOGINIP(rs.getString(2));
         	  model.setSTARTTIME(rs.getString(3));
         	  model.setCALLERID(rs.getString(4));
            } 
           
         
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }
	
	public List<Map<String, Object>> getAuthenticationlog(String datanum,String mmdd) throws SQLException {
        Connection con = DbCon.getAAA2Connection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT accesstime, user_name, calling_station_id, result FROM rad_access_cdr"+mmdd+"  WHERE user_name=?");            
            pst.setString(1, datanum);
            
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

	public  List<Map<String, Object>> getAccountinglog(String datanum,String mmdd) throws SQLException {
        Connection con = DbCon.getAAA2Connection();

        try {
            PreparedStatement pst = con.prepareStatement("select callingnumber, starttime, stoptime,total_volume,terminate_cause from rad_account_adsl_cdr"+mmdd+" where callingnumber= ?");            
            pst.setString(1, datanum);
            
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
