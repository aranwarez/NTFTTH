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

    public List<Map<String, Object>> getSpBgList(String SP_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT M.TRANS_ID, M.TRANS_CD, M.SP_CODE, A.SP_NAME, M.TRANS_DT, COMMON.TO_BS(M.TRANS_DT) NEP_TRANS_DT, \n"
                    + "M.BANK_CD, B.BANK_ADDRESS, B.ACCT_NO,  M.BANK_GUARENTEE_DATE, COMMON.TO_BS(M.BANK_GUARENTEE_DATE) NEP_BANK_GUARENTEE_DATE, \n"
                    + "   M.BANK_VALIDITY_DATE, COMMON.TO_BS(M.BANK_VALIDITY_DATE) NEP_BANK_VALIDITY_DATE, M.AMT,   \n"
                    + "   M.POST_DT, M.POST_BY, M.POST_FLAG, M.CREATE_BY, \n"
                    + "   M.CREATE_DT, M.UPDATE_BY, M.UPDATE_DT\n"
                    + "FROM M_SP_BG M, M_SP A, M_BANK B\n"
                    + "WHERE A.SP_CODE=M.SP_CODE\n"
                    + "AND B.BANK_CD=M.BANK_CD\n"
                    + "AND A.SP_CODE=?\n"
                    + "ORDER BY 1 desc");
            pst.setString(1, SP_CODE);
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
    
    public String saveSpBg(String SP_CODE, String EFFECTIVE_DT, String REVENUE_TARGET, String MINIMUM_GUARENTEE, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "INSERT INTO VASNTW.M_SP_TARGET (\n"
                    + "   TRANS_ID, SP_CODE, EFFECTIVE_DT, \n"
                    + "   REVENUE_TARGET, MINIMUM_GUARENTEE, CREATE_BY,   CREATE_DT) \n"
                    + "VALUES (SEQ_SPTARGET_TRANSID.NEXTVAL , ?, COMMON.TO_AD(?),\n"
                    + "    NVL(?,0), NVL(?,0), ?, SYSDATE )";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SP_CODE);
            pst.setString(2, EFFECTIVE_DT);
            pst.setString(3, REVENUE_TARGET);
            pst.setString(4, MINIMUM_GUARENTEE);
            pst.setString(5, USER);

            pst.executeUpdate();

            return "Succesfully Saved Service Provider Target";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

}
