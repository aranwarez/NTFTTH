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

public class ProblemDao {
	
	public List<Map<String, Object>> getProblemList() throws SQLException {
        Connection con = DbCon.getConnection();
System.out.println("sg");
        try {
            PreparedStatement pst = con.prepareStatement("SELECT\r\n" + 
            		"PROBLEM_ID,DESCRIPTION,(SELECT M_SERVICE_TYPE.DESCRIPTION from M_SERVICE_TYPE \r\n" + 
            		"WHERE M_SERVICE_TYPE.SERVICE_TYPE_ID=M_PROBLEM.SERVICE_TYPE_ID) AS SERVICE_TYPE_DESC,\r\n" + 
            		"SERVICE_TYPE_ID,common.to_BS(ACTIVE_DT) AS ACTIVE_DT,\r\n" + 
            		"common.to_BS(DEACTIVE_DT) AS DEACTIVE_DT,ACTIVE_STATUS,\r\n" + 
            		"CREATE_BY,CREATE_DT,UPDATE_BY,UPDATE_DT\r\n" + 
            		" FROM M_PROBLEM ORDER BY PROBLEM_ID");
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
	
	public String saveProblem(String DESCRIPTION, String SERVICE_TYPE_ID, String ACTIVE_DT,
            String DEACTIVE_DT, String ACTIVE_STATUS, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "INSERT INTO M_PROBLEM\r\n" + 
            		"(PROBLEM_ID,DESCRIPTION,SERVICE_TYPE_ID,ACTIVE_DT,DEACTIVE_DT,ACTIVE_STATUS,CREATE_BY,CREATE_DT)\r\n" + 
            		"VALUES((PROBLEM_ID.nextval),?,?,(common.to_ad(?)),(common.to_ad(?)),?,?,SYSDATE)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, DESCRIPTION);
            pst.setString(2, SERVICE_TYPE_ID);
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
	
	public String updateProblem(String PROBLEM_ID,String DESCRIPTION, String SERVICE_TYPE_ID, String ACTIVE_DT,
            String DEACTIVE_DT, String ACTIVE_STATUS, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry ="update  M_PROBLEM\r\n" + 
            		" set DESCRIPTION=?,SERVICE_TYPE_ID=?,\r\n" + 
            		"ACTIVE_DT=common.to_ad(?),DEACTIVE_DT=common.to_ad(?),ACTIVE_STATUS=?,\r\n" + 
            		"UPDATE_BY=?,UPDATE_DT=sysdate \r\n" + 
            		"where  PROBLEM_ID=?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);
            pst.setString(2, SERVICE_TYPE_ID);
            pst.setString(3, ACTIVE_DT);
            pst.setString(4, DEACTIVE_DT);
            pst.setString(5, ACTIVE_STATUS);
            pst.setString(6, USER);
            pst.setString(7, PROBLEM_ID);
           

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
            PreparedStatement pst = con.prepareStatement("delete from M_PROBLEM  where PROBLEM_ID=?");
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
