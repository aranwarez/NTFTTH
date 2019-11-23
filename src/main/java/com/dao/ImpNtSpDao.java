/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DbCon;

/**
 *
 * @author nabin
 */
public class ImpNtSpDao {

    public List<Map<String, Object>> getImpNtSpList(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
            String SERVICE_CODE, String NT_SP, String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            PreparedStatement pst = con.prepareStatement(
                    "SELECT T.TRANS_NO, T.SEQ_NO, T.IMP_YEAR,  T.IMP_PERIOD, T.IMP_MONTH, T.SERVICE_CODE, \n"
                    + "   T.NT_SP, T.S_NO, T.SP_DESC,  T.CP_DESC, T.SERVICES, T.ESME_CODE, T.RATE, \n"
                    + "   T.START_DT, T.MO_1ST,   T.MT_1ST, T.FILE_NAME, T.CATEGORY, T.CP_CODE,  T.REMARKS, \n"
                    + "   T.POST_FLAG, T.POST_BY, T.POST_DT, T.CREATE_DT, T.CREATE_BY,   T.UPDATE_DT, T.UPDATE_BY \n"
                    + "FROM VASNTW.TMP_IMP_SMS_NT_SP T\n"
                    + "WHERE imp_year=? AND imp_period=? AND imp_month=?\n" + "AND service_code=? AND NT_SP=?\n"
                    + "AND post_flag=NVL(?,post_flag) ORDER BY trans_no, seq_no");

            pst.setString(1, IMP_YEAR);
            pst.setString(2, IMP_PERIOD);
            pst.setString(3, IMP_MONTH);
            pst.setString(4, SERVICE_CODE);
            pst.setString(5, NT_SP);
            pst.setString(6, POST_FLAG);

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

    public String saveExcelData(List<Map<String, Object>> JSONarrayList, String Filename, String IMP_YEAR,
            String Period, String IMP_MONTH, String Services, String NT_SP, String SERVICE_CODE, String IMP_PERIOD, String USER)
            throws SQLException {
        Connection con = DbCon.getConnection();
        con.setAutoCommit(false);
        try {
            Statement rstrans = con.createStatement();
            int transno = 0;
            ResultSet rst = rstrans.executeQuery("select TMP_IMP_TRANS_NO.nextval from dual");
            while (rst.next()) {
                transno = rst.getInt(1);
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO VASNTW.TMP_IMP_SMS_NT_SP\r\n"
                    + "(TRANS_NO, SEQ_NO, IMP_YEAR, IMP_MONTH, SERVICE_CODE, NT_SP,"
                    + " CP_DESC,MO_1ST, MT_1ST, FILE_NAME, CATEGORY,"
                    + " CREATE_BY, POST_FLAG, IMP_PERIOD)\r\n" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,'N',?)");
            int index = 1;
            for (Map<String, Object> obj : JSONarrayList) {
                pst.setInt(1, transno);
                pst.setInt(2, index);
                pst.setString(3, IMP_YEAR);
                pst.setString(4, IMP_MONTH);
                pst.setString(5, SERVICE_CODE);
                pst.setString(6, NT_SP);
                pst.setString(7, (String) obj.get("1"));
                pst.setString(8, (String) obj.get("2"));
                pst.setString(9, (String) obj.get("3"));
                pst.setString(10, Filename);
                pst.setString(11, (String) obj.get("0"));
                // CP_CODE, CREATE_BY, POST_FLAG, IMP_PERIOD
                pst.setString(12, USER);
                pst.setString(13, IMP_PERIOD);
                pst.executeUpdate();
                index = index + 1;
            }
            con.commit();
            return "Sucessfully imported Excel Data";
        } catch (Exception e) {
        	con.rollback();
            e.printStackTrace();
            return e.getMessage();
        } finally {
            con.close();
        }
    }

    public String postExcelImportData(String inyear, String ivperiod, String ivmonth, String ivservice, String ivntsp,
            String ivuser) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String getDBUSERByUserIdSql = "{call TRANSFER_IMPORT_DATA(?,?,?,?,?,?)}";
            CallableStatement pst = con.prepareCall(getDBUSERByUserIdSql);
            pst.setString(1, inyear);
            pst.setString(2, ivperiod);
            pst.setString(3, ivmonth);
            pst.setString(4, ivservice);
            pst.setString(5, ivntsp);
            pst.setString(6, ivuser);
            // pst.registerOutParameter(10, java.sql.Types.VARCHAR);
            pst.executeUpdate();
            // String result = pst.getString(10);
            return "Sucessfully Posted Excel imported Data";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to save " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String saveImpNtSp(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
            String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST, String USER) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "INSERT INTO TMP_IMP_SMS_NT_SP (TRANS_NO, SEQ_NO, IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, \n"
                    + " NT_SP, S_NO, CP_DESC, ESME_CODE, MO_1ST, MT_1ST, CATEGORY, \n"
                    + "   CREATE_BY, POST_FLAG,  CREATE_DT) \n"
                    + "VALUES (TMP_IMP_TRANS_NO.nextval, 1, ?, ?, ?,?,\n"
                    + "    ?, ?, ?, ?, ?, ?, ?,\n"
                    + "    ?, 'N', SYSDATE )";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, IMP_YEAR);
            pst.setString(2, IMP_PERIOD);
            pst.setString(3, IMP_MONTH);
            pst.setString(4, SERVICE_CODE);
            pst.setString(5, NT_SP);
            pst.setString(6, S_NO);
            pst.setString(7, CP_DESC);
            pst.setString(8, ESME_CODE);
            pst.setString(9, MO_1ST);
            pst.setString(10, MT_1ST);
            pst.setString(11, CATEGORY);
            pst.setString(12, USER);
            pst.executeUpdate();
            return "Succesfully Saved imp mo mt";
        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

    public String updateImpNtSp(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
            String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST, String USER, String TRANS_NO, String SEQ_NO) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            String qry = "UPDATE TMP_IMP_SMS_NT_SP\n"
                    + "SET    IMP_YEAR     = ?, IMP_PERIOD   = ?,IMP_MONTH    = ?,\n"
                    + "       SERVICE_CODE = ?, NT_SP        = ?,CATEGORY         = ?,\n"
                    + "       CP_DESC     = ?, S_NO      = ?, ESME_CODE    = ?, MO_1ST       = ?,\n"
                    + "       MT_1ST       = ?,  \n"
                    + "       UPDATE_DT    = sysdate, UPDATE_BY    = ?       \n"
                    + "WHERE  TRANS_NO     = ? AND    SEQ_NO       = ?";
            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, IMP_YEAR);
            pst.setString(2, IMP_PERIOD);
            pst.setString(3, IMP_MONTH);
            pst.setString(4, SERVICE_CODE);
            pst.setString(5, NT_SP);
            pst.setString(6, CATEGORY);
            pst.setString(7, CP_DESC);
            pst.setString(8, S_NO);            
            pst.setString(9, ESME_CODE);
            pst.setString(10, MO_1ST);
            pst.setString(11, MT_1ST);
            pst.setString(12, USER);
            pst.setString(13, TRANS_NO);
            pst.setString(14, SEQ_NO);
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

    public String DeleteImpNtSp(String TRANS_NO, String SEQ_NO) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from TMP_IMP_SMS_NT_SP where trans_no=? AND seq_no=?");
            pst.setString(1, TRANS_NO);
            pst.setString(2, SEQ_NO);
            
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed Reason :" + e.getLocalizedMessage();
        } finally {
            con.close();
        }
        return "Record deleted successfully.";
    }
    
    public String DeleteAllImpNtSp(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP, String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete from TMP_IMP_SMS_NT_SP where imp_year=? AND imp_period=? AND imp_month=? AND service_code=? AND nt_sp=? AND post_flag=?");
            pst.setString(1, IMP_YEAR);
            pst.setString(2, IMP_PERIOD);
            pst.setString(3, IMP_MONTH);
            pst.setString(4, SERVICE_CODE);
            pst.setString(5, NT_SP);
            pst.setString(6, POST_FLAG);
            
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
