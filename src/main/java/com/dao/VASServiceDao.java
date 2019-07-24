/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import util.DbCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Service;

/**
 *
 * @author Aran
 */
public class VASServiceDao {

    //get all Service Provider List
    public List<Map<String, Object>> getVasServiceList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from M_VAS_SERVICE");
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

    public String saveVasService(String SERVICE_CODE, String DESCRIPTION, String SLDG_CODE, String DATA_IMPORT, String ACTIVE_FLAG, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "insert into M_VAS_SERVICE(SERVICE_CODE,DESCRIPTION, SLDG_CODE, DATA_IMPORT, ACTIVE_FLAG, CREATE_BY, CREATE_DT ) values(?,?,?,?,?,?,sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SERVICE_CODE);
            pst.setString(2, DESCRIPTION);
            pst.setString(3, SLDG_CODE);
            pst.setString(4, DATA_IMPORT);
            pst.setString(5, ACTIVE_FLAG);
//            pst.setString(6, user);
            pst.setString(6, USER);

            pst.executeUpdate();

            return "Succesfully Saved Services Name";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateVasService(String SERVICE_CODE, String DESCRIPTION, String SLDG_CODE, String DATA_IMPORT, String ACTIVE_FLAG, String user) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "update M_VAS_SERVICE set description=?, SLDG_CODE=?, DATA_IMPORT=?, ACTIVE_FLAG=?, UPDATE_BY=?, UPDATE_DT=sysdate WHERE service_code=? ";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);
            pst.setString(2, SLDG_CODE);
            pst.setString(3, DATA_IMPORT);
            pst.setString(4, ACTIVE_FLAG);
            pst.setString(5, user);
            pst.setString(6, SERVICE_CODE);

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

    public String DeleteService(String SERVICE_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_VAS_SERVICE where SERVICE_CODE=?");
            pst.setString(1, SERVICE_CODE);

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
