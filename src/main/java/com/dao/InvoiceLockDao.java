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
            String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            PreparedStatement pst = con.prepareStatement(
                    "SELECT B.TRANS_NO, B.TRANS_DT, common.to_bs(B.TRANS_DT) NEP_DT, B.IMP_YEAR,  B.IMP_PERIOD, B.IMP_MONTH, B.S_NO, \n"
                    + "   B.POST_FLAG, B.POST_BY, B.POST_DT,    B.CREATE_BY, B.CREATE_DT, B.UPDATE_BY,   B.UPDATE_DT,\n"
                    + " (SELECT nvl(SUM(dr_cr_flag*AMT),0) FROM BILL_DETAIL WHERE trans_no=b.trans_no AND sharing_type='N') Non_Sharing, (SELECT nvl(abs(SUM(dr_cr_flag*AMT)),0) FROM BILL_DETAIL WHERE trans_no=b.trans_no AND sharing_type='Y') Sharing FROM VASNTW.BILL_MASTER B WHERE imp_year=? AND imp_period=? AND imp_month=? AND post_flag=NVL(?,post_flag) ORDER BY 3,4,5,6");

            pst.setString(1, IMP_YEAR);
            pst.setString(2, IMP_PERIOD);
            pst.setString(3, IMP_MONTH);
            pst.setString(4, POST_FLAG);

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

            PreparedStatement pst = con.prepareStatement("SELECT B.TRANS_ID, B.TRANS_NO, B.SERVICE_CODE, B.ITEM_CODE, B.SHARING_TYPE,  DECODE(B.DR_CR_FLAG,'1','DR','CR') DR_CR_FLAG, \n"
                    + "   B.AMT, B.ROYALTY_AMT, B.TSC_AMT, B.VAT_AMT, decode(dr_cr_flag,'1',(b.amt+b.tsc_amt+b.vat_amt),(b.amt-b.royalty_amt-b.tsc_amt-b.vat_amt)) Total,B.CREATE_BY, B.CREATE_DT,  B.UPDATE_BY, B.UPDATE_DT\n"
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
    
        public String post_new(List<String> list, String user) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            con.setAutoCommit(false);
            for (String transno : list) {
                PreparedStatement pst = con.prepareStatement("update bill_master set POST_FLAG=?,POST_BY=?,POST_DT=sysdate where TRANS_NO=?");
                pst.setString(1, "Y");
                pst.setString(2, user);
                pst.setString(3, transno);
                int c = pst.executeUpdate();
                pst.executeUpdate();
                //      System.out.println(c);
            }
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
            return "Failed ";
        } finally {
            con.close();
        }
        return "Successfully Post Your Data";
    }
    public String Unpost(String transno, String user) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            //   for (String transno : list) {
            PreparedStatement pst = con.prepareStatement("update bill_master set POST_FLAG=?,POST_BY=?,POST_DT=sysdate where TRANS_NO=?");

            pst.setString(1, "N");
            pst.setString(2, user);
            pst.setString(3, transno);

            int c = pst.executeUpdate();
            pst.executeUpdate();

            //      System.out.println(c);
            //  }
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
            return "Failed ";
        } finally {
            con.close();
        }
        return "Successfully UNPosted Your Data";
    }
    public String DeleteInvoicelock(String TRANS_NO) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement pst = con.prepareStatement("delete from BILL_DETAIL where TRANS_NO IN(SELECT TRANS_NO FROM BILL_MASTER WHERE TRANS_NO=? AND NVL(POST_FLAG,'N')='N')");
            pst.setString(1, TRANS_NO);
            pst.executeUpdate();
            PreparedStatement pstm = con.prepareStatement("delete from BILL_MASTER where TRANS_NO=?");
            pstm.setString(1, TRANS_NO);

            pstm.executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
            return "Failed Reason :" + e.getLocalizedMessage();
        } finally {
            con.close();
        }
        return "Record deleted successfully.";
    }
}
