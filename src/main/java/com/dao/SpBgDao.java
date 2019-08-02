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
public class SpBgDao {

    public List<Map<String, Object>> getSpBgList(String SP_CODE, String FROM_DT, String TO_DT, String TRANS_CD, String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT M.TRANS_ID, M.TRANS_CD, M.SP_CODE, A.SP_NAME, M.TRANS_DT, COMMON.TO_BS(M.TRANS_DT) NEP_TRANS_DT, \n"
                    + "M.BANK_CD, B.BANK_ADDRESS, B.ACCT_NO,  M.BANK_GUARENTEE_DATE, COMMON.TO_BS(M.BANK_GUARENTEE_DATE) NEP_BANK_GUARENTEE_DATE, \n"
                    + "   M.BANK_VALIDITY_DATE, COMMON.TO_BS(M.BANK_VALIDITY_DATE) NEP_BANK_VALIDITY_DATE, M.AMT, M.REMARKS,  \n"
                    + "   M.POST_DT, M.POST_BY, M.POST_FLAG, M.CREATE_BY, \n"
                    + "   M.CREATE_DT, M.UPDATE_BY, M.UPDATE_DT\n"
                    + "FROM M_SP_BG M, M_SP A, M_BANK B\n"
                    + "WHERE A.SP_CODE=M.SP_CODE\n"
                    + "AND B.BANK_CD=M.BANK_CD AND A.SP_CODE=NVL(?,A.SP_CODE)\n"
                    + "AND TRANS_DT BETWEEN nvl(COMMON.TO_AD(?),(sysdate-30)) AND nvl(COMMON.TO_AD(?),sysdate) AND M.TRANS_CD=NVL(?,M.TRANS_CD) and M.POST_FLAG=NVL(?,M.POST_FLAG)\n"
                    + "ORDER BY 1 desc");
            pst.setString(1, SP_CODE);
            pst.setString(2, FROM_DT);
            pst.setString(3, TO_DT);
            pst.setString(4, TRANS_CD);
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

    public String saveSpBg(String TRANS_CD, String SP_CODE, String TRANS_DT, String BANK_CD, String BANK_GUARENTEE_DATE,
            String AMT, String BANK_VALIDITY_DATE, String USER, String REMARKS) throws SQLException {
        Connection con = DbCon.getConnection();
        String transid = null;
        try {
            String qry1 = "SELECT COMMON.GET_FISCAL_YEAR(?) || lpad(SEQ_SPBG_TRANSID.nextval,6,'0') FROM dual";
            PreparedStatement prs1 = con.prepareStatement(qry1);
            prs1.setString(1, TRANS_DT);
            ResultSet rs1 = prs1.executeQuery();
            if (rs1.next()){
                transid = rs1.getString(1);
            }
            
            String qry = "INSERT INTO M_SP_BG (\n"
                    + "   TRANS_ID, TRANS_CD, SP_CODE, TRANS_DT, BANK_CD, BANK_GUARENTEE_DATE, \n"
                    + "   AMT, BANK_VALIDITY_DATE, CREATE_BY,   CREATE_DT, POST_FLAG, REMARKS) \n"
                    + "VALUES (?,? ,? , COMMON.TO_AD(?),? ,COMMON.TO_AD(?), \n"
                    + "NVL(?,0), COMMON.TO_AD(?), ?,   SYSDATE,'N', ?)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, transid);
            pst.setString(2, TRANS_CD);
            pst.setString(3, SP_CODE);
            pst.setString(4, TRANS_DT);
            pst.setString(5, BANK_CD);
            pst.setString(6, BANK_GUARENTEE_DATE);
            pst.setString(7, AMT);
            pst.setString(8, BANK_VALIDITY_DATE);
            pst.setString(9, USER);
            pst.setString(10, REMARKS);
            pst.executeUpdate();
            return "Succesfully Saved Service Provider Bank Transaction";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateSpBg(String TRANS_ID, String TRANS_CD, String SP_CODE, String TRANS_DT, String BANK_CD, String BANK_GUARENTEE_DATE,
            String AMT, String BANK_VALIDITY_DATE, String USER, String REMARKS) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE M_SP_BG\n"
                    + "SET    TRANS_CD = ?,  SP_CODE  = ?, TRANS_DT= common.to_ad(?),\n"
                    + "       BANK_CD  = ?,  BANK_GUARENTEE_DATE = common.to_ad(?),    AMT   = nvl(?,0),\n"
                    + "       BANK_VALIDITY_DATE  = common.to_ad(?), UPDATE_BY = ?, UPDATE_DT = sysdate, REMARKS=?\n"
                    + "WHERE  TRANS_ID            = ?";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, TRANS_CD);
            pst.setString(2, SP_CODE);
            pst.setString(3, TRANS_DT);
            pst.setString(4, BANK_CD);
            pst.setString(5, BANK_GUARENTEE_DATE);
            pst.setString(6, AMT);
            pst.setString(7, BANK_VALIDITY_DATE);
            pst.setString(8, USER);
            pst.setString(9, REMARKS);
            pst.setString(10, TRANS_ID);
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

    public String DeleteSpBg(String TRANS_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_SP_BG where TRANS_ID=?");
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

    public String postSpBg(String TRANS_ID, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE M_SP_BG\n"
                    + "SET    POST_BY = ?,  POST_DT  = SYSDATE, POST_FLAG= 'Y'\n"
                    + "WHERE  TRANS_ID            = ?";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, USER);
            pst.setString(2, TRANS_ID);
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

    public String unpostSpBg(String TRANS_ID, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE M_SP_BG\n"
                    + "SET    POST_BY = ?,  POST_DT  = SYSDATE, POST_FLAG= 'N'\n"
                    + "WHERE  TRANS_ID            = ?";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, USER);
            pst.setString(2, TRANS_ID);
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
}
