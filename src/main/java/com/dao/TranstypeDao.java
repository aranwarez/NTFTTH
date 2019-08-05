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
public class TranstypeDao {

    public List<Map<String, Object>> getTranstypeList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from M_TRANS_TYPE ORDER BY TRANS_CD");
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
    
    public String saveTranstype(String TRANS_CD, String DESCRIPTION, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "insert into M_TRANS_TYPE(TRANS_CD, DESCRIPTION, CREATE_BY, CREATE_DT ) values(?,?,?,sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, TRANS_CD);
            pst.setString(2, DESCRIPTION);
            pst.setString(3, USER);

            pst.executeUpdate();

            return "Succesfully Saved Package Type";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateTranstype(String TRANS_CD, String DESCRIPTION, String user) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "update M_TRANS_CD set description=?, UPDATE_BY=?, UPDATE_DT=sysdate WHERE TRANS_CD=? ";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);
            pst.setString(2, user);
            pst.setString(3, TRANS_CD);

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

    public String DeleteTranstype(String TRANS_CD) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_TRANS_TYPE where TRANS_CD=?");
            pst.setString(1, TRANS_CD);

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
