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

import com.model.Bank;

import util.DbCon;

/**
 *
 * @author nabin
 */
public class BankDao {

	public List<Map<String, Object>> getBankList() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("select * from M_BANK ORDER BY BANK_CD");
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

	public List<Map<String, Object>> getBankListforCC(String CC_Code) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("select * from M_BANK  where cc_code=? ORDER BY BANK_CD");
			pst.setString(1, CC_Code);
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
	
	  public String saveBank(Bank m,String user) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {

	            PreparedStatement pst = con.prepareStatement("insert into M_BANK(CC_CODE,BANK_CD,BANK_NAME,BANK_ADDRESS,ACCT_NO,ACCT_TYPE,ACT_FLAG,DEACTIVE_DT,CREATE_BY,CREATE_DT)values(?,?,?,?,?,?,?,COMMON.TO_AD(?),?,sysdate)");
	            //pst.setString(1, m.getCC_CODE());
	            pst.setString(1, "VAS");
	            System.out.println(m.getBANK_CD());
	            pst.setString(2, m.getBANK_CD());
	            pst.setString(3, m.getBANK_NAME());
	            pst.setString(4, m.getBANK_ADDRESS());
	            pst.setString(5, m.getACCT_NO());
	            pst.setString(6, m.getACCT_TYPE());
	            pst.setString(7, m.getACT_FLAG());
	            pst.setString(8, m.getDEACTIVE_DT());
	            pst.setString(9, user);
	            pst.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Failed";
	        } finally {
	            con.close();
	        }
	        return "Successfully bank has been added";
	    }

	    public String updateBank(Bank m) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {
	            PreparedStatement pst = con.prepareStatement("update m_bank set BANK_NAME=?,BANK_ADDRESS=?,ACCT_NO=?,ACCT_TYPE=?,ACT_FLAG=?,DEACTIVE_DT=COMMON.TO_AD(?),UPDATE_BY=?,UPDATE_DT=sysdate where cc_code=? and bank_cd=?");
	            pst.setString(1, m.getBANK_NAME());
	            pst.setString(2, m.getBANK_ADDRESS());
	            pst.setString(3, m.getACCT_NO());
	            pst.setString(4, m.getACCT_TYPE());
	            pst.setString(5, m.getACT_FLAG());
	            pst.setString(6, m.getDEACTIVE_DT());
	            pst.setString(7, m.getUSER());
	            pst.setString(8, m.getCC_CODE());
	            pst.setString(9, m.getBANK_CD());

	            pst.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Failed";
	        } finally {
	            con.close();

	        }
	        return "Data has been saved successfully ";
	    }

	    public String deleteBank(String cc_code, String bankcd) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {

	            PreparedStatement pst = con.prepareStatement("delete from M_BANK where CC_CODE=? and BANK_CD=?");
	            pst.setString(1, cc_code);
	            pst.setString(2, bankcd);
	            pst.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Failed";
	        } finally {
	            con.close();
	        }
	        return "Data has been saved successfully ";
	    }

	  
	
	

}
