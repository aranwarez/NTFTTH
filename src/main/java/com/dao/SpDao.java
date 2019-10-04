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
public class SpDao {

    public List<Map<String, Object>> getSpList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from M_SP ORDER BY SP_CODE");
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

    public String saveSp(String SP_CODE, String SP_NAME, String SHORT_CODE, String ADDRESS, String CONTACT_PERSON,
            String TEL_NO, String MOBILE_NO, String EMAIL, String SLDG_CODE, String PAN_NO, String CONTRACT_DT,
            String CONTRACT_TER_DT, String SERVICE_START_DT, String BANK_INFORMATION, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "insert into M_SP(SP_CODE, SP_NAME, SHORT_CODE, ADDRESS, CONTACT_PERSON, \n"
                    + "            TEL_NO, MOBILE_NO, EMAIL, SLDG_CODE, PAN_NO, CONTRACT_DT, \n"
                    + "            CONTRACT_TER_DT, SERVICE_START_DT, BANK_INFORMATION, CREATE_BY, CREATE_DT ) \n"
                    + "values(?,?,?,?,?,?,?,?,?,?,common.to_ad(?),common.to_ad(?),?,?,?,sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SP_CODE);
            pst.setString(2, SP_NAME);
            pst.setString(3, SHORT_CODE);
            pst.setString(4, ADDRESS);
            pst.setString(5, CONTACT_PERSON);
            pst.setString(6, TEL_NO);
            pst.setString(7, MOBILE_NO);
            pst.setString(8, EMAIL);
            pst.setString(9, SLDG_CODE);
            pst.setString(10, PAN_NO);
            pst.setString(11, CONTRACT_DT);
            pst.setString(12, CONTRACT_TER_DT);
            pst.setString(13, SERVICE_START_DT);
            pst.setString(14, BANK_INFORMATION);
            pst.setString(15, USER);

            pst.executeUpdate();

            return "Succesfully Saved Service Provider";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateSp(String SP_CODE, String SP_NAME, String SHORT_CODE, String ADDRESS, String CONTACT_PERSON,
            String TEL_NO, String MOBILE_NO, String EMAIL, String SLDG_CODE, String PAN_NO, String CONTRACT_DT,
            String CONTRACT_TER_DT, String SERVICE_START_DT, String BANK_INFORMATION, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "update M_SP set SP_NAME=?, SHORT_CODE=?, ADDRESS=?, CONTACT_PERSON=?, TEL_NO=?, MOBILE_NO=?, EMAIL=?, SLDG_CODE=?, PAN_NO=?, CONTRACT_DT=common.to_ad(?), CONTRACT_TER_DT=common.to_ad(?), SERVICE_START_DT=?, BANK_INFORMATION=?, UPDATE_BY=?, UPDATE_DT=sysdate WHERE SP_CODE=? ";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, SP_NAME);
            pst.setString(2, SHORT_CODE);
            pst.setString(3, ADDRESS);
            pst.setString(4, CONTACT_PERSON);
            pst.setString(5, TEL_NO);
            pst.setString(6, MOBILE_NO);
            pst.setString(7, EMAIL);
            pst.setString(8, SLDG_CODE);
            pst.setString(9, PAN_NO);
            pst.setString(10, CONTRACT_DT);
            pst.setString(11, CONTRACT_TER_DT);
            pst.setString(12, SERVICE_START_DT);
            pst.setString(13, BANK_INFORMATION);
            pst.setString(14, USER);
            pst.setString(15, SP_CODE);

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

    public String DeleteSp(String SP_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_SP where SP_CODE=?");
            pst.setString(1, SP_CODE);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed Reason :" + e.getLocalizedMessage();
        } finally {
            con.close();
        }
        return "Record deleted successfully.";
    }
    
    public List<Map<String, Object>> getSPInfo(String SPCODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from M_SP where SP_CODE=?");
            pst.setString(1, SPCODE);
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
