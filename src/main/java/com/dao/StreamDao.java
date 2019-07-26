/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author nabin
 */
public class StreamDao {
    
    public List<Map<String, Object>> getStreamList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM M_STREAM_TYPE ORDER BY STREAM_TYPE");
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
    
    
    public String saveStream(String STREAM_TYPE, String DESCRIPTION, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "insert into M_STREAM_TYPE(STREAM_TYPE, DESCRIPTION, CREATED_BY, CREATED_DATE ) values(?,?,?,sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, STREAM_TYPE);
            pst.setString(2, DESCRIPTION);
            pst.setString(3, USER);

            pst.executeUpdate();

            return "Succesfully Saved Stream Type";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateStream(String STREAM_TYPE, String DESCRIPTION, String user) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "update M_STREAM_TYPE set description=?, UPDATED_BY=?, UPDATED_DATE=sysdate WHERE STREAM_TYPE=? ";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);
            pst.setString(2, user);
            pst.setString(3, STREAM_TYPE);

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

    public String DeleteStream(String STREAM_TYPE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_STREAM_TYPE where STREAM_TYPE=?");
            pst.setString(1, STREAM_TYPE);

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
