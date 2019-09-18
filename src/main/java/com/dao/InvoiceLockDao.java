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
public class InvoiceLockDao {

    public List<Map<String, Object>> getBillMasterList(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
            String SERVICE_CODE, String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            PreparedStatement pst = con.prepareStatement(
                    "SELECT B.TRANS_NO, B.TRANS_DT, B.IMP_YEAR,  B.IMP_PERIOD, B.IMP_MONTH, B.SERVICE_CODE, \n"
                    + "   B.POST_FLAG, B.POST_BY, B.POST_DT,    B.CREATE_BY, B.CREATE_DT, B.UPDATE_BY,   B.UPDATE_DT\n"
                    + "FROM VASNTW.BILL_MASTER B WHERE imp_year=? AND imp_period=? AND imp_month=? AND service_code=nvl(?,service_code) AND post_flag=NVL(?,post_flag) ORDER BY 3,4,5,6");

            pst.setString(1, IMP_YEAR);
            pst.setString(2, IMP_PERIOD);
            pst.setString(3, IMP_MONTH);
            pst.setString(4, SERVICE_CODE);
            pst.setString(5, POST_FLAG);

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

    public List<Map<String, Object>> getBillDetailList(String TRANS_NO) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            PreparedStatement pst = con.prepareStatement("SELECT B.TRANS_ID, B.TRANS_NO, B.S_NO, B.ITEM_CODE, B.SHARING_TYPE,  B.DR_CR_FLAG, \n"
                    + "   B.AMT, B.ROYALTY_AMT, B.TSC_AMT, B.VAT_AMT, B.CREATE_BY, B.CREATE_DT,  B.UPDATE_BY, B.UPDATE_DT\n"
                    + "FROM VASNTW.BILL_DETAIL B WHERE TRANS_NO=? ORDER BY 3,4,5");

            pst.setString(1, TRANS_NO);

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
