


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

public class SubTeamDao {
	
	
	public List<Map<String, Object>> getSubTeamByTeam(String TEAM_CODE) throws SQLException {		
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select * from m_sub_team where TEAM_CODE=? order by sub_team_code");
            pst.setString(1, TEAM_CODE);
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
	
	
	
	public List<Map<String, Object>> getSubTeamList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from m_sub_team order by sub_team_code");
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
	
	
	public String saveSubTeam(String SUB_TEAM_CODE, String DESCRIPTION,String TEAM_CODE, String USER,String ACTIVE_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "insert into m_sub_team (SUB_TEAM_CODE,DESCRIPTION,TEAM_CODE,CREATE_BY,CREATE_DT,ACTIVE_FLAG)\r\n" + 
            		"values(?,?,?,?,sysdate,?)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SUB_TEAM_CODE);            
            pst.setString(2, DESCRIPTION);
            pst.setString(3, TEAM_CODE); 
            pst.setString(4, USER);
            pst.setString(5, ACTIVE_FLAG);
           

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
	public String updateSubTeam(String SUB_TEAM_CODE,String DESCRIPTION,String TEAM_CODE, String USER,String ACTIVE_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "update m_sub_team set DESCRIPTION=?,TEAM_CODE=?,UPDATE_BY=?,UPDATE_DT=sysdate,ACTIVE_FLAG=?\r\n" + 
            		"where SUB_TEAM_CODE=?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);        
            pst.setString(2, TEAM_CODE);            
            pst.setString(3, USER);      
            pst.setString(4, ACTIVE_FLAG);
            pst.setString(5, SUB_TEAM_CODE);

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
	
	public String DeleteSubTeam(String SUB_TEAM_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from m_sub_team where SUB_TEAM_CODE=?");
            pst.setString(1, SUB_TEAM_CODE);

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
