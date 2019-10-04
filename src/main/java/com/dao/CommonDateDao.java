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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DbCon;

/**
 *
 * @author nabin
 */
public class CommonDateDao {
    
    public List<Map<String, Object>> getDateList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select trunc(sysdate) today_date, common.to_bs(sysdate) nep_today_date, common.to_bs(sysdate-30) nep_from_date, substr(common.to_bs(sysdate),1,4) cur_year, substr(common.to_bs(sysdate),6,2) cur_month, decode(substr(common.to_bs(sysdate),6,2),'04','01','05','01','06','01','07','02','08','02','09','02','10','03','11','03','12','03','04') cur_period from sys.dual");
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
    
    public List<Map<String, Object>> getNepMonthList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("select MONTH_CD, NEP_MONTH, FISCAL_Cd FROM NEPALI_MONTHS");
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
    
    
    public static Date convertDateAD(String date) throws SQLException {

        try (Connection con = DbCon.getConnection() //  System.out.println("date= "+date);
                ) {
            PreparedStatement pst = con.prepareStatement("select common.to_ad(?)from dual");
            pst.setString(1, date);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDate(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
