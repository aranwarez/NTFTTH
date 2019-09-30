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
public class ReceiptDao {

    public List<Map<String, Object>> getReceiptList(String CC_CODE, String S_NO, String FROM_DT, String TO_DT, String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT \n"
                    + "R.CC_CODE, R.RECEIPT_NO, R.RECEIPT_DT, COMMON.to_bs(R.RECEIPT_DT) nep_dt, R.TRANS_FROM, R.S_NO, R.PAYMENT_MODE, \n"
                    + "   R.BANK_CD, R.BANK_NAME, R.CHEQUE_NO,   R.BANK_ADDRESS, R.PAID_AMT, R.BAL_AMT, \n"
                    + "   R.TSC, R.VAT, R.ROYALTY,    R.REMARKS, R.CANCEL_FLAG, R.CANCEL_BY, \n"
                    + "   R.CANCEL_DT, R.POST_FLAG, R.POST_BY,   R.POST_DT, R.CREATE_BY, R.CREATE_DT, \n"
                    + "   R.UPDATE_BY, R.UPDATE_DT\n"
                    + "FROM VASNTW.RECEIPT_MASTER R\n"
                    + "WHERE cc_code=nvl(?,'CC040501')\n"
                    + "AND receipt_dt BETWEEN nvl(common.to_ad(?),sysdate-30) AND nvl(common.to_ad(?),sysdate)\n"
                    + "AND s_no =nvl(?,s_no)\n"
                    + "AND cancel_flag=nvl(?,cancel_flag)\n"
                    + "ORDER BY RECEIPT_NO");
            pst.setString(1, CC_CODE);
            pst.setString(2, FROM_DT);
            pst.setString(3, TO_DT);
            pst.setString(4, S_NO);
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

    public String saveReceipt(String CC_CODE, String RECEIPT_NO, String RECEIPT_DT, String S_NO, String BANK_CD, String BANK_NAME,
            String CHEQUE_NO, String PAID_AMT, String USER, String REMARKS) throws SQLException {
        Connection con = DbCon.getConnection();
        String transid = null;
        try {
            String qry1 = "SELECT COMMON.GET_FISCAL_YEAR(?) || lpad(SEQ_RECEIPT_NO.nextval,6,'0') FROM dual";
            PreparedStatement prs1 = con.prepareStatement(qry1);
            prs1.setString(1, RECEIPT_DT);
            ResultSet rs1 = prs1.executeQuery();
            if (rs1.next()) {
                transid = rs1.getString(1);
            }
            String qry = "INSERT INTO VASNTW.RECEIPT_MASTER (\n"
                    + "   CC_CODE, RECEIPT_NO, RECEIPT_DT,TRANS_FROM, S_NO, PAYMENT_MODE, \n"
                    + "   BANK_CD, BANK_NAME, CHEQUE_NO,  BANK_ADDRESS, PAID_AMT, BAL_AMT, \n"
                    + "   TSC, VAT, ROYALTY,  REMARKS, CANCEL_FLAG, POST_FLAG, POST_BY, \n"
                    + "   POST_DT, CREATE_BY, CREATE_DT) \n"
                    + "VALUES ( ?, ?, common.to_ad(?),'REC', ?, 'Q',\n"
                    + "    ?, ?, ?, null, ?, 0,\n"
                    + "    0,0, 0, ?,'N', 'Y', ?,\n"
                    + "    sysdate, ?, sysdate)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, CC_CODE);
            pst.setString(2, transid);
            pst.setString(3, RECEIPT_DT);
            pst.setString(4, S_NO);
            pst.setString(5, BANK_CD);
            pst.setString(6, BANK_NAME);
            pst.setString(7, CHEQUE_NO);
            pst.setString(8, PAID_AMT);
            pst.setString(9, REMARKS);
            pst.setString(10, USER);
            pst.setString(11, REMARKS);
            pst.executeUpdate();
            return "Succesfully Saved Receipt Transaction";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String DeleteReceipt(String RECEIPT_NO, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE RECEIPT_MASTER SET cancel_flag='Y', cancel_dt=sysdate, cancel_by=? WHERE receipt_no=?";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, USER);
            pst.setString(2, RECEIPT_NO);
            pst.executeUpdate();
            return "Succesfully Cancel Receipt Transaction";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to cancel : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String getSpdue(String SP_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT sum(total_amt) bal_amt FROM vw_ledger\n"
                    + "WHERE s_no=?\n"
                    + "AND post_flag='Y'");
            pst.setString(1, SP_CODE);
            ResultSet rs = pst.executeQuery();

            
            while (rs.next()) {
            return rs.getString(1);
            }
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }

}
