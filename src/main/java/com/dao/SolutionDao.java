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

public class SolutionDao {
	
	public List<Map<String, Object>> getProblemList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT ms.*, mp.DESCRIPTION PROBLEM_DESC, mst.DESCRIPTION SERVICE_DESC\r\n"
            		+ "  FROM m_solution ms, M_PROBLEM mp, M_SERVICE_TYPE mst\r\n"
            		+ " WHERE     ms.PROBLEM_ID = mp.PROBLEM_ID\r\n"
            		+ "       AND mst.SERVICE_TYPE_ID = ms.SERVICE_TYPE_ID");
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
	
	public String saveSolution(String DESCRIPTION, String SERVICE_TYPE_ID, String ACTIVE_STATUS, String USER,String PROBLEM_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "INSERT INTO M_SOLUTION (SOLUTION_ID,\r\n"
            		+ "                                 PROBLEM_ID,\r\n"
            		+ "                                 DESCRIPTION,\r\n"
            		+ "                                 SERVICE_TYPE_ID,\r\n"
            		+ "                                 ACTIVE_STATUS,\r\n"
            		+ "                                 CREATE_BY)\r\n"
            		+ "     VALUES (SOLUTION_SEQ_ID.NEXTVAL,\r\n"
            		+ "             ?,\r\n"
            		+ "             ?,\r\n"
            		+ "             ?,\r\n"
            		+ "             ?,\r\n"
            		+ "             ?)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(2, DESCRIPTION);
            pst.setString(3, SERVICE_TYPE_ID);
            pst.setString(4, ACTIVE_STATUS);
            pst.setString(5, USER);           
            pst.setString(1, PROBLEM_ID);
            
            pst.executeUpdate();

            return "Succesfully Saved Item";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }
	
	public String updateSolution(String PROBLEM_ID,String DESCRIPTION, String SERVICE_TYPE_ID, String SOLUTION_ID,
            String ACTIVE_STATUS, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry ="UPDATE M_SOLUTION\r\n"
            		+ "   SET PROBLEM_ID = ?,\r\n"
            		+ "       DESCRIPTION = ?,\r\n"
            		+ "       SERVICE_TYPE_ID = ?,\r\n"
            		+ "       ACTIVE_STATUS = ?,UPDATE_BY=?,UPDATE_DT=sysdate"
            		+ " WHERE solution_id = ?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(2, DESCRIPTION);
            pst.setString(3, SERVICE_TYPE_ID);
            pst.setString(4, ACTIVE_STATUS);
            pst.setString(5, USER);            
            pst.setString(6, SOLUTION_ID);
            pst.setString(1, PROBLEM_ID);
           

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
	
	
	public String DeleteProblem(String PROBLEM_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_SOLUTION  where SOLUTION_ID=?");
            pst.setString(1, PROBLEM_ID);

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
