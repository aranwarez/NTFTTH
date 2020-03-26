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

public class TeamDao {
	public List<Map<String, Object>> getTeamList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select  *  from M_TEAM order by team_code");
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
	
	
	public String saveTeam(String TEAM_CODE, String DESCRIPTION, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "insert into M_TEAM (TEAM_CODE,DESCRIPTION,CREATE_BY,CREATE_DT)\r\n" + 
            		"values(?,?,?,sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, TEAM_CODE);
            pst.setString(2, DESCRIPTION);           
            pst.setString(3, USER);
           

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
	
	public String updateTeam(String TEAM_CODE,String DESCRIPTION, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "update  M_TEAM  set DESCRIPTION=?,UPDATE_BY=?,UPDATE_DT=sysdate\r\n" + 
            		"where TEAM_CODE=?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);           
            pst.setString(2, USER);
            pst.setString(3, TEAM_CODE);
           

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
	
	 public String DeleteTeam(String TEAM_CODE) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {
	            PreparedStatement pst = con.prepareStatement("delete from M_TEAM  where TEAM_CODE=?");
	            pst.setString(1, TEAM_CODE);

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
