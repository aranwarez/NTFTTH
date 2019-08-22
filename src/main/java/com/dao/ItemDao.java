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
public class ItemDao {

    public List<Map<String, Object>> getItemList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select * from M_ITEM ORDER BY ITEM_CODE");
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

    public String saveItem(String ITEM_CODE, String DESCRIPTION, String CATEGORY_CODE, String IS_RECURRING,
            String TAXABLE_AMT, String VATABLE_AMT, String OWN_AMT, String CASH_SALE_FLAG, String ACTIVE_FLAG, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "INSERT INTO M_ITEM (\n"
                    + "   ITEM_CODE, DESCRIPTION, CATEGORY_CODE, IS_RECURRING, TAXABLE_AMT, VATABLE_AMT, \n"
                    + "   OWN_AMT, CASH_SALE_FLAG, ACTIVE_FLAG, CREATE_BY, CREATE_DT) \n"
                    + "   VALUES ( ? , ?,  ?, ?, ?, ?,\n"
                    + "    ?, ?, ?, ?, SYSDATE)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, ITEM_CODE);
            pst.setString(2, DESCRIPTION);
            pst.setString(3, CATEGORY_CODE);
            pst.setString(4, IS_RECURRING);
            pst.setString(5, TAXABLE_AMT);
            pst.setString(6, VATABLE_AMT);
            pst.setString(7, OWN_AMT);
            pst.setString(8, CASH_SALE_FLAG);
            pst.setString(9, ACTIVE_FLAG);
            pst.setString(10, USER);

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

    public String updateItem(String ITEM_CODE, String DESCRIPTION, String CATEGORY_CODE, String IS_RECURRING,
            String TAXABLE_AMT, String VATABLE_AMT, String OWN_AMT, String CASH_SALE_FLAG, String ACTIVE_FLAG, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "UPDATE VASNTW.M_ITEM SET    DESCRIPTION    = ?,  CATEGORY_CODE  = ?,   IS_RECURRING   = ?,\n"
                    + "       TAXABLE_AMT    = ?,  VATABLE_AMT    = ?,    OWN_AMT        = ?,\n"
                    + "       CASH_SALE_FLAG = ?, ACTIVE_FLAG    = ?,  UPDATE_BY  = ?,  UPDATE_DT = SYSDATE\n"
                    + "WHERE  ITEM_CODE      = ?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, DESCRIPTION);
            pst.setString(2, CATEGORY_CODE);
            pst.setString(3, IS_RECURRING);
            pst.setString(4, TAXABLE_AMT);
            pst.setString(5, VATABLE_AMT);
            pst.setString(6, OWN_AMT);
            pst.setString(7, CASH_SALE_FLAG);
            pst.setString(8, ACTIVE_FLAG);
            pst.setString(9, USER);
            pst.setString(10, ITEM_CODE);

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

    public String DeleteItem(String ITEM_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_ITEM where ITEM_CODE=?");
            pst.setString(1, ITEM_CODE);

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
