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
public class WapAppTariffDao {

    public List<Map<String, Object>> getWapAppTariffList(String SERVICE_CODE, String PACKAGE_TYPE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT TRANS_ID, SERVICE_CODE, PACKAGE_TYPE, RATE, EFFECTIVE_DT, common.to_bs(EFFECTIVE_DT) NEP_EFFECTIVE_DT, RANGE_FROM, RANGE_TO,\n"
                    + "CREATE_BY, CREATE_DT, UPDATE_BY, UPDATE_DT\n"
                    + "FROM m_wap_app_tariff\n"
                    + "WHERE SERVICE_CODE=NVL(?,SERVICE_CODE)\n"
                    + "AND PACKAGE_TYPE=NVL(?,PACKAGE_TYPE)\n"
                    + "ORDER BY 2,3,5");
            pst.setString(1, SERVICE_CODE);
            pst.setString(2, PACKAGE_TYPE);
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

    public String saveWapAppTariff(String SERVICE_CODE, String PACKAGE_TYPE, String RATE,
            String EFFECTIVE_DT, String RANGE_FROM, String RANGE_TO, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "INSERT INTO M_WAP_APP_TARIFF (\n"
                    + "   TRANS_ID, SERVICE_CODE, PACKAGE_TYPE,   RATE, EFFECTIVE_DT, RANGE_FROM,  RANGE_TO, \n"
                    + "   CREATE_BY, CREATE_DT) \n"
                    + "VALUES (SEQ_WAP_APP_TARIFF.NEXTVAL , ? , ? ,nvl(?,0), common.to_ad(?), nvl(?,0), nvl(?,0), \n"
                    + "?, SYSDATE)";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SERVICE_CODE);
            pst.setString(2, PACKAGE_TYPE);
            pst.setString(3, RATE);
            pst.setString(4, EFFECTIVE_DT);
            pst.setString(5, RANGE_FROM);
            pst.setString(6, RANGE_TO);
            pst.setString(7, USER);
            pst.executeUpdate();
            return "Succesfully Saved Wap App Tariff";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateWapAppTariff(String TRANS_ID, String SERVICE_CODE, String PACKAGE_TYPE, String RATE,
            String EFFECTIVE_DT, String RANGE_FROM, String RANGE_TO, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE M_WAP_APP_TARIFF\n"
                    + "SET    SERVICE_CODE = ?,\n"
                    + "       PACKAGE_TYPE = ?,\n"
                    + "       RATE         = nvl(?,0),\n"
                    + "       EFFECTIVE_DT = common.to_ad(?),\n"
                    + "       RANGE_FROM   = nvl(?,0),\n"
                    + "       RANGE_TO     = nvl(?,0),\n"
                    + "       UPDATE_BY    = ?,\n"
                    + "       UPDATE_DT    = sysdate\n"
                    + "WHERE  TRANS_ID     = ?";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SERVICE_CODE);
            pst.setString(2, PACKAGE_TYPE);
            pst.setString(3, RATE);
            pst.setString(4, EFFECTIVE_DT);
            pst.setString(5, RANGE_FROM);
            pst.setString(6, RANGE_TO);
            pst.setString(7, USER);
            pst.setString(8, TRANS_ID);
            
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
    public String DeleteWapAppTariff(String TRANS_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_WAP_APP_TARIFF where TRANS_ID=?");
            pst.setString(1, TRANS_ID);
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
