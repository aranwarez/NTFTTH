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
public class PaymentDao {

    public List<Map<String, Object>> getPaymentList(String CC_CODE, String S_NO, String FROM_DT, String TO_DT, String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT \n"
                    + "R.CC_CODE, R.PAYMENT_NO, R.PAYMENT_DT, COMMON.to_bs(R.PAYMENT_DT) nep_dt, R.TRANS_FROM, R.S_NO, R.PAYMENT_MODE, \n"
                    + "   R.BANK_CD, R.CHEQUE_NO,   R.BANK_ADDRESS, R.PAID_AMT, R.BAL_AMT, \n"
                    + "   R.TSC, R.VAT, R.ROYALTY, (R.PAID_AMT-NVL(R.ROYALTY,0)+NVL(R.TSC,0)+NVL(R.VAT,0)) TOTAL_AMT,   R.REMARKS, R.CANCEL_FLAG, R.CANCEL_BY, \n"
                    + "   R.CANCEL_DT, R.POST_FLAG, R.POST_BY,   R.POST_DT, R.CREATE_BY, R.CREATE_DT, common.to_bs(R.CREATE_DT) NEP_CREATE_DT, \n"
                    + "   R.UPDATE_BY, R.UPDATE_DT, R.SERVICE_CODE\n"
                    + "FROM VASNTW.PAYMENT_MASTER R\n"
                    + "WHERE cc_code=nvl(?,'CC040501')\n"
                    + "AND PAYMENT_dt BETWEEN nvl(common.to_ad(?),sysdate-30) AND nvl(common.to_ad(?),sysdate)\n"
                    + "AND s_no =nvl(?,s_no)\n"
                    + "AND cancel_flag=nvl(?,cancel_flag)\n"
                    + "ORDER BY PAYMENT_NO");
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

    public String savePayment(String CC_CODE, String PAYMENT_NO, String PAYMENT_DT, String S_NO, String BANK_CD,
            String CHEQUE_NO, String PAID_AMT, String USER, String REMARKS, String SERVICE_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        String transid = null;
        try {
            String qry1 = "SELECT COMMON.GET_FISCAL_YEAR(?) || lpad(SEQ_PAYMENT_NO.nextval,6,'0') FROM dual";
            PreparedStatement prs1 = con.prepareStatement(qry1);
            prs1.setString(1, PAYMENT_DT);
            ResultSet rs1 = prs1.executeQuery();
            
            PreparedStatement bpst = con.prepareStatement("SELECT abs(sum(nvl(amt,0))) payable_amt, abs(sum(nvl(royalty,0))) royalty,sum(nvl(amt,0)-nvl(royalty,0)) payable_before_tax, abs(sum(nvl(tsc_amt,0))) tsc, abs(sum(nvl(vat_amt,0))) vat, nvl(sum(total_amt),0) bal_amt_with_tax FROM vw_ledger\n"
                    + "WHERE s_no=? AND item_code=?\n"
                    + "AND sharing_type='Y' AND post_flag='Y'");
            bpst.setString(1, S_NO);
            bpst.setString(2, SERVICE_CODE);
            ResultSet brs = bpst.executeQuery();
            String tsc=null;
            String vat=null;
            String royalty=null; 
            String payable_amt=null;
           while(brs.next()){
            tsc=brs.getString("TSC");
            vat=brs.getString("VAT");
            royalty=brs.getString("ROYALTY");
            payable_amt=brs.getString("PAYABLE_AMT");
            }
            if (rs1.next()) {
                transid = rs1.getString(1);
            }
            String qry = "INSERT INTO VASNTW.PAYMENT_MASTER (\n"
                    + "   CC_CODE, PAYMENT_NO, PAYMENT_DT,TRANS_FROM, S_NO, PAYMENT_MODE, \n"
                    + "   BANK_CD, CHEQUE_NO,  BANK_ADDRESS, PAID_AMT, BAL_AMT, \n"
                    + "   TSC, VAT, ROYALTY,  REMARKS, CANCEL_FLAG, POST_FLAG, POST_BY, \n"
                    + "   POST_DT, CREATE_BY, CREATE_DT, SERVICE_CODE) \n"
                    + "VALUES ( ?, ?, common.to_ad(?),'PAY', ?, 'Q',\n"
                    + "    ?, ?, null, ?, 0,\n"
                    + "    ?,?, ?, ?,'N', 'Y', ?,\n"
                    + "    sysdate, ?, sysdate,?)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, CC_CODE);
            pst.setString(2, transid);
            pst.setString(3, PAYMENT_DT);
            pst.setString(4, S_NO);
            pst.setString(5, BANK_CD);
            pst.setString(6, CHEQUE_NO);
            pst.setString(7, payable_amt);
            pst.setString(8, tsc);
            pst.setString(9, vat);
            pst.setString(10, royalty);
            pst.setString(11, REMARKS);
            pst.setString(12, USER);
            pst.setString(13, USER);
            pst.setString(14, SERVICE_CODE);
            pst.executeUpdate();
            return "Succesfully Saved Payment Transaction";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String DeletePayment(String PAYMENT_NO, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE PAYMENT_MASTER SET cancel_flag='Y', cancel_dt=sysdate, cancel_by=? WHERE PAYMENT_no=?";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, USER);
            pst.setString(2, PAYMENT_NO);
            pst.executeUpdate();
            return "Succesfully Cancel Payment Transaction";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to cancel : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public Map<String, Object> getSpdue(String SP_CODE, String ITEM_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT sum(nvl(amt,0)) payable_amt, sum(nvl(royalty,0)) royalty,nvl(sum(nvl(amt,0)-nvl(royalty,0)),0) payable_before_tax, sum(nvl(tsc_amt,0)), sum(nvl(vat_amt,0)), nvl(sum(total_amt),0) bal_amt_with_tax FROM vw_ledger\n"
                    + "WHERE s_no=? AND item_code=?\n"
                    + "AND sharing_type='Y' AND post_flag='Y'");
            pst.setString(1, SP_CODE);
            pst.setString(2, ITEM_CODE);
            ResultSet rs = pst.executeQuery();

            Map<String, Object> row = null;

            ResultSetMetaData metaData = rs.getMetaData();
            Integer columnCount = metaData.getColumnCount();
            while (rs.next()) {
                row = new HashMap<String, Object>();

                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }

            }
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return null;
    }

    
    
}
