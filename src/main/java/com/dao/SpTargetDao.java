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
public class SpTargetDao {

    public List<Map<String, Object>> getSpTargetList(String SP_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT M.TRANS_ID, M.SP_CODE, A.SP_NAME, M.EFFECTIVE_DT, COMMON.TO_BS(M.EFFECTIVE_DT) NEP_EFFECTIVE_DT,   \n"
                    + "   M.REVENUE_TARGET, M.MINIMUM_GUARENTEE, M.CREATE_BY, M.CREATE_DT, M.UPDATE_BY, M.UPDATE_DT\n"
                    + "FROM M_SP_TARGET M, M_SP A\n"
                    + "WHERE A.SP_CODE=M.SP_CODE\n"
                    + "AND M.SP_CODE=?\n"
                    + "ORDER BY 4");
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

    public String saveSpTarget(String SP_CODE, String EFFECTIVE_DT, String REVENUE_TARGET, String MINIMUM_GUARENTEE, String USER) throws SQLException {
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

    public String updateSpTarget(String TRANS_ID, String SP_CODE, String EFFECTIVE_DT, String REVENUE_TARGET, String MINIMUM_GUARENTEE, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "UPDATE VASNTW.M_SP_TARGET\n"
                    + "SET    SP_CODE           = ?, EFFECTIVE_DT  = COMMON.TO_AD(?),  REVENUE_TARGET = nvl(?,0),\n"
                    + "       MINIMUM_GUARENTEE = nvl(?,0), UPDATE_BY  = ?,  UPDATE_DT    = SYSDATE\n"
                    + "WHERE  TRANS_ID          = ?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, SP_CODE);
            pst.setString(2, EFFECTIVE_DT);
            pst.setString(3, REVENUE_TARGET);
            pst.setString(4, MINIMUM_GUARENTEE);
            pst.setString(5, USER);
            pst.setString(6, TRANS_ID);

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
    public String DeleteSpTarget(String TRANS_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_SP_TARGET where TRANS_ID=?");
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
