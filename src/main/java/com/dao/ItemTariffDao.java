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
public class ItemTariffDao {

    public List<Map<String, Object>> getItemTariffCodeList(String SERVICE_CODE, String ITEM_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT DISTINCT ITEM_CODE FROM M_ITEM_TARIFF \n"
                    + "WHERE SERVICE_CODE=?\n"
                    + "AND ITEM_CODE=?\n"
                    + "AND ACTIVE_FLAG='Y'");
            pst.setString(1, SERVICE_CODE);
            pst.setString(2, ITEM_CODE);
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

    public List<Map<String, Object>> getItemTariffList(String SERVICE_CODE, String ITEM_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT M.ID_NO, M.ITEM_CODE, A.DESCRIPTION ITEM,\n"
                    + "  B.CATEGORY_CODE, B.DESCRIPTION CATEGORY, M.SERVICE_CODE, \n"
                    + "   M.AMOUNT, M.EFFECTIVE_DT, common.to_bs(M.EFFECTIVE_DT) nep_effective_dt, M.ACTIVE_FLAG, \n"
                    + "   M.CREATE_BY, M.CREATE_DT, M.UPDATE_BY, \n"
                    + "   M.UPDATE_DT\n"
                    + "FROM VASNTW.M_ITEM_TARIFF M, M_ITEM A, M_ITEM_CATEGORY B\n"
                    + "WHERE m.item_code=a.item_code\n"
                    + "AND b.CATEGORY_CODE=a.CATEGORY_CODE\n"
                    + "AND m.service_code=NVL(?,m.service_code)\n"
                    + "AND a.item_code=NVL(?,a.item_code)\n"
                    + "ORDER BY a.item_code, m.effective_dt, a.category_code");
            pst.setString(1, SERVICE_CODE);
            pst.setString(2, ITEM_CODE);
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

    public String saveItemTariff(String ITEM_CODE, String SERVICE_CODE, String AMOUNT, String EFFECTIVE_DT,
            String ACTIVE_FLAG, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "INSERT INTO VASNTW.M_ITEM_TARIFF (\n"
                    + "   ID_NO, ITEM_CODE, SERVICE_CODE, AMOUNT, EFFECTIVE_DT, ACTIVE_FLAG, \n"
                    + "   CREATE_BY, CREATE_DT) \n"
                    + "VALUES ( SEQ_ITEM_TARIFF_ID.NEXTVAL, ?, ?, ?, COMMON.TO_AD(?), ?,\n"
                    + "    ?, sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, ITEM_CODE);
            pst.setString(2, SERVICE_CODE);
            pst.setString(3, AMOUNT);
            pst.setString(4, EFFECTIVE_DT);
            pst.setString(5, ACTIVE_FLAG);
            pst.setString(6, USER);
            pst.executeUpdate();

            return "Succesfully Saved Item Tariff";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateItemTariff(String ID_NO, String ITEM_CODE, String SERVICE_CODE, String AMOUNT, String EFFECTIVE_DT,
            String ACTIVE_FLAG, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "UPDATE VASNTW.M_ITEM_TARIFF\n"
                    + "SET    ITEM_CODE    = ?,  SERVICE_CODE = ?,   AMOUNT       = ?,   EFFECTIVE_DT = COMMON.TO_AD(?),\n"
                    + "       ACTIVE_FLAG  = ?,  UPDATE_BY    = ?,  UPDATE_DT    = SYSDATE\n"
                    + "WHERE  ID_NO        = ?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, ITEM_CODE);
            pst.setString(2, SERVICE_CODE);
            pst.setString(3, AMOUNT);
            pst.setString(4, EFFECTIVE_DT);
            pst.setString(5, ACTIVE_FLAG);
            pst.setString(6, USER);
            pst.setString(7, ID_NO);

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
    public String DeleteItemTariff(String ID_NO) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_ITEM_TARIFF where ID_NO=?");
            pst.setString(1, ID_NO);

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
