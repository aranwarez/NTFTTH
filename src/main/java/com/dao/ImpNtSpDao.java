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
public class ImpNtSpDao {

    public List<Map<String, Object>> getImpNtSpList(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE,
            String NT_SP, String POST_FLAG) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT T.TRANS_NO, T.SEQ_NO, T.IMP_YEAR,  T.IMP_PERIOD, T.IMP_MONTH, T.SERVICE_CODE, \n"
                    + "   T.NT_SP, T.S_NO, T.SP_DESC,  T.CP_DESC, T.SERVICES, T.ESME_CODE, T.RATE, \n"
                    + "   T.START_DT, T.MO_1ST,   T.MT_1ST, T.FILE_NAME, T.CATEGORY, T.CP_CODE,  T.REMARKS, \n"
                    + "   T.POST_FLAG, T.POST_BY, T.POST_DT, T.CREATE_DT, T.CREATE_BY,   T.UPDATE_DT, T.UPDATE_BY \n"
                    + "FROM VASNTW.TMP_IMP_SMS_NT_SP T\n"
                    + "WHERE imp_year=? AND imp_period=? AND imp_month=?\n"
                    + "AND service_code=? AND NT_SP=?\n"
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

}
