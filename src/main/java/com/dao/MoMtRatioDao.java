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
public class MoMtRatioDao {

    public List<Map<String, Object>> getMoMtRatioList(String CP_CODE) throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT M.TRANS_ID, M.CP_CODE, M.ESME_CODE,  M.IMP_YEAR, M.IMP_PERIOD, M.IMP_MONTH, \n"
                    + "   M.MO_MT_RATIO, M.CREATE_BY, M.CREATE_DT,  M.UPDATE_BY, M.UPDATE_DT\n"
                    + "FROM M_CP_MO_MT_RATIO M, M_CP A\n"
                    + "WHERE A.CP_CODE=M.CP_CODE\n"
                    + "AND A.CP_CODE=?\n"
                    + "ORDER BY 4,6");
            pst.setString(1, CP_CODE);
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

    public String saveMoMtRatio(String CP_CODE, String ESME_CODE, String IMP_YEAR, String IMP_PERIOD,
            String IMP_MONTH, String MO_MT_RATIO, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "INSERT INTO M_CP_MO_MT_RATIO (\n"
                    + "   TRANS_ID, CP_CODE, ESME_CODE, \n"
                    + "   IMP_YEAR, IMP_PERIOD, IMP_MONTH, \n"
                    + "   MO_MT_RATIO, CREATE_BY, CREATE_DT) \n"
                    + "VALUES (SEQ_CP_MO_MT_RATIO.NEXTVAL , ?, ?,\n"
                    + "    ?, ?, ?,\n"
                    + "    ?, ?,SYSDATE);)";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, CP_CODE);
            pst.setString(2, ESME_CODE);
            pst.setString(3, IMP_YEAR);
            pst.setString(4, IMP_PERIOD);
            pst.setString(5, IMP_MONTH);
            pst.setString(6, MO_MT_RATIO);
            pst.setString(7, USER);

            pst.executeUpdate();

            return "Succesfully Saved Content Provider MO MT Ratio";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateMoMtRatio(String TRANS_ID, String CP_CODE, String ESME_CODE, String IMP_YEAR, String IMP_PERIOD,
            String IMP_MONTH, String MO_MT_RATIO, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "UPDATE M_CP_MO_MT_RATIO\n"
                    + "SET    CP_CODE     = ?,       ESME_CODE   = ?,       IMP_YEAR    = ?,\n"
                    + "       IMP_PERIOD  = ?,       IMP_MONTH   = ?,       MO_MT_RATIO = ?,\n"
                    + "       UPDATE_BY   = ?,       UPDATE_DT   = SYSDATE\n"
                    + "WHERE  TRANS_ID    = ?";

            PreparedStatement pst = con.prepareStatement(qry);

            pst.setString(1, CP_CODE);
            pst.setString(2, ESME_CODE);
            pst.setString(3, IMP_YEAR);
            pst.setString(4, IMP_PERIOD);
            pst.setString(5, IMP_MONTH);
            pst.setString(6, MO_MT_RATIO);
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
    
    public String DeleteMoMtRatio(String TRANS_ID) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_CP_MO_MT_RATIO where TRANS_ID=?");
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
