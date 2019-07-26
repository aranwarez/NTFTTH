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
public class CpDao {

    public List<Map<String, Object>> getCpList(String SP_CODE, String SERVICE_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT M.CP_CODE, M.SP_CODE, A.SP_NAME, M.SERVICE_CODE, \n"
                    + "M.CP_NAME, M.ESME_CODE, M.SRV_FOR, M.PACKAGE_TYPE, M.STREAM_TYPE, M.START_DT,  M.END_DT, M.SHARING_TYPE, M.SHARE_NT_PER, M.AFS_FLAG, M.MIN_QTY\n"
                    + "FROM M_CP M, M_SP A\n"
                    + "WHERE A.SP_CODE=M.SP_CODE\n"
                    + "AND A.SP_CODE=NVL(?,A.SP_CODE)\n"
                    + "AND M.SERVICE_CODE=NVL(?,M.SERVICE_CODE) ORDER BY CP_CODE");
            pst.setString(1, SP_CODE);
            pst.setString(2, SERVICE_CODE);
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

    public String saveCp(String SP_CODE, String SERVICE_CODE, String CP_NAME, String ESME_CODE,
            String SRV_FOR, String PACKAGE_TYPE, String STREAM_TYPE, String START_DT, String END_DT, String SHARING_TYPE,
            String SHARE_NT_PER, String AFS_FLAG, String MIN_QTY, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        String transid = null;
        try {
            String qry = "INSERT INTO M_CP (CP_CODE, SP_CODE, SERVICE_CODE, CP_NAME, ESME_CODE, SRV_FOR, \n"
                    + "   PACKAGE_TYPE, STREAM_TYPE, START_DT, END_DT, SHARING_TYPE, SHARE_NT_PER, \n"
                    + "   AFS_FLAG, MIN_QTY, CREATE_BY, CREATE_DT) \n"
                    + "VALUES (lpad(SEQ_CP_CODE.NEXTVAL,8,'0'), ?, ?, ?, ?, ?,\n"
                    + "    ?, ?, common.to_ad(?), common.to_ad(?), ?, ?,\n"
                    + "    ?, ?, ?, SYSDATE )";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SP_CODE);
            pst.setString(2, SERVICE_CODE);
            pst.setString(3, CP_NAME);
            pst.setString(4, ESME_CODE);
            pst.setString(5, SRV_FOR);
            pst.setString(6, PACKAGE_TYPE);
            pst.setString(7, STREAM_TYPE);
            pst.setString(8, START_DT);
            pst.setString(9, END_DT);
            pst.setString(10, SHARING_TYPE);
            pst.setString(11, SHARE_NT_PER);
            pst.setString(12, AFS_FLAG);
            pst.setString(13, MIN_QTY);
            pst.setString(14, USER);
            pst.executeUpdate();
            return "Succesfully Saved Content Provider";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateCp(String CP_CODE, String SP_CODE, String SERVICE_CODE, String CP_NAME, String ESME_CODE,
            String SRV_FOR, String PACKAGE_TYPE, String STREAM_TYPE, String START_DT, String END_DT, String SHARING_TYPE,
            String SHARE_NT_PER, String AFS_FLAG, String MIN_QTY, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE M_CP\n"
                    + "SET    SP_CODE      = ?, SERVICE_CODE = ?,  CP_NAME      = ?,\n"
                    + "       ESME_CODE    = ?, SRV_FOR      = ?,  PACKAGE_TYPE = ?,\n"
                    + "       STREAM_TYPE  = ?, START_DT = common.to_ad(?),  END_DT       = common.to_ad(?),\n"
                    + "       SHARING_TYPE = ?,  SHARE_NT_PER = ?,   AFS_FLAG     = ?,\n"
                    + "       MIN_QTY      = nvl(?,0),    UPDATE_BY    = ?,   UPDATE_DT    = sysdate\n"
                    + "WHERE  CP_CODE      = ?";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, SP_CODE);
            pst.setString(2, SERVICE_CODE);
            pst.setString(3, CP_NAME);
            pst.setString(4, ESME_CODE);
            pst.setString(5, SRV_FOR);
            pst.setString(6, PACKAGE_TYPE);
            pst.setString(7, STREAM_TYPE);
            pst.setString(8, START_DT);
            pst.setString(9, END_DT);
            pst.setString(10, SHARING_TYPE);
            pst.setString(11, SHARE_NT_PER);
            pst.setString(12, AFS_FLAG);
            pst.setString(13, MIN_QTY);
            pst.setString(14, USER);
            pst.setString(15, CP_CODE);
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
    public String DeleteCp(String CP_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from M_CP where CP_CODE=?");
            pst.setString(1, CP_CODE);
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
