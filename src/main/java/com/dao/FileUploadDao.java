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

public class FileUploadDao {
	
	public List<Map<String, Object>> getUploadList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
        	 
        	 
        			
            PreparedStatement pst = con.prepareStatement("select  a.*, NVL(a.ROLE_CODE, 'ALL') ROLE  from FILE_NOTIFICATION a order by a.UPLOAD_ID");
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
	
	public String saveUpload(String DESCRIPTION,String FILE_LOCATION,String ROLE_CODE, String DISPLAY_FLAG,String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

        	
        	
        	String qry1 = "delete from FILE_NOTIFICATION where role_code=?";        	
        	PreparedStatement pst1 = con.prepareStatement(qry1);
        	pst1.setString(1, ROLE_CODE);
        	pst1.executeUpdate();
            
            
            String qry = "insert into FILE_NOTIFICATION(UPLOAD_ID,DESCRIPTION,FILE_LOCATION,role_code,DISPLAY_FLAG,CREATE_BY,CREATE_DT)\r\n" + 
            		"values((upload_id.nextval),?,?,?,?,?,sysdate) ";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, DESCRIPTION);
            pst.setString(2, FILE_LOCATION);
            pst.setString(3, ROLE_CODE);
            pst.setString(4, DISPLAY_FLAG);           
            pst.setString(5, USER);
           

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
	
	public String enable_file(String UPLOAD_ID, String DISPLAY_FLAG,String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
        	PreparedStatement pst1 = con.prepareStatement("update FILE_NOTIFICATION set DISPLAY_FLAG='N'");
         	 pst1.executeUpdate();
       	 
            String qry = "update FILE_NOTIFICATION set DISPLAY_FLAG=?,UPDATE_BY=?,UPDATE_DT=sysdate where upload_id=?";

            PreparedStatement pst = con.prepareStatement(qry);            
            pst.setString(1, DISPLAY_FLAG);                
            pst.setString(2, USER);           
            pst.setString(3, UPLOAD_ID);
            
            pst.executeUpdate();

            return "Succesfully Saved Item";

        } catch (Exception e) {
           
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }
	
	public String delete_file(String UPLOAD_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
        	
       	 
            String qry = "delete from FILE_NOTIFICATION where upload_id=?";

            PreparedStatement pst = con.prepareStatement(qry);            
            pst.setString(1, UPLOAD_ID);                
           
            
            pst.executeUpdate();

            return "Sucessfully deleted!";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to delete : " + e.getMessage();
        } finally {
            con.close();
        }
    }
	
	public String get_file(String ROLE_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
        	
       	 
            String qry = "select file_location from file_notification where role_code=?";

            PreparedStatement pst = con.prepareStatement(qry);            
            pst.setString(1, ROLE_CODE);                
           ResultSet rs=pst.executeQuery();
           if(rs.next()) {
        	   return rs.getString("file_location");
           }
            
          

        } catch (Exception e) {
        
            e.printStackTrace();
            return "Failed to delete : " + e.getMessage();
        } finally {
            con.close();
        }
        return null;
    }
	
	public String maxOneActiveFile(String ROLE_CODE) throws SQLException {
    	Connection con = DbCon.getConnection();
		  try {
		
			  PreparedStatement pst=con.prepareStatement("select file_location from FILE_NOTIFICATION \r\n" + 
			  		"where upload_id=(select max(upload_id)upload_id from FILE_NOTIFICATION where display_flag='Y' and ROLE_CODE= NVL(?, ROLE_CODE)) \r\n" + 
			  		"and ROLE_CODE= NVL(?, ROLE_CODE)");
			  pst.setString(1, ROLE_CODE); 
			  pst.setString(2, ROLE_CODE); 
			  ResultSet rs = pst.executeQuery();
			   if(rs.next()) {
					  System.out.println("file location ="+rs.getString("FILE_LOCATION"));
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
