package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.DbCon;

public class CashSaleDao {
	
	
	public String SaveBill(List<Map<String, Object>> myarray, String SP_CODE,String nepdate,String BANK_CODE,String REMARKS, String AMT,String USER,String CC_CODE)
			throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String transno=null;
			con.setAutoCommit(false);
			  String qry1 = "SELECT COMMON.GET_FISCAL_YEAR(?) || lpad(SEQ_CASHSALE_TRANS_NO.nextval,6,'0') FROM dual";
	            PreparedStatement prs1 = con.prepareStatement(qry1);
	            prs1.setString(1, nepdate);
	            ResultSet rs1 = prs1.executeQuery();
	            if (rs1.next()){
	                transno = rs1.getString(1);
	            }
					PreparedStatement pst1 = con.prepareStatement(
					"INSERT INTO SALES_MASTER\r\n" + 
					"(CC_CODE, TRANS_NO, SALES_DATE, CUSTOMER_CODE, BANK_PAY_AMT, BANK_CD,"
					+ " REMARKS, POST_FLAG,CREATE_BY, POST_BY, POST_DT)\r\n" + 
					"VALUES(?,?,common.to_ad(?),?,?,?,?,'Y',?,?,sysdate)");

			pst1.setString(1, CC_CODE);
			pst1.setString(2, transno);
			pst1.setString(3, nepdate);
			pst1.setString(4, SP_CODE);
			pst1.setString(5,  AMT);
			pst1.setString(6, BANK_CODE);
			pst1.setString(7, REMARKS);
			pst1.setString(8, USER);
			pst1.setString(9, USER);
			
			pst1.executeUpdate();

			for (Map<String, Object> obj : myarray) {
				PreparedStatement pst4 = con.prepareStatement(
						"INSERT INTO VASNTW.SALES_DETAIL\r\n" + 
						"(TRANS_ID, CC_CODE, TRANS_NO, ITEM_CODE, QTY, RATE, REVENUE_AMT, TSC_AMT, VAT_AMT, CREATE_BY)\r\n" + 
						"VALUES(SEQ_CASHSALE_TRANS_ID.nextval,?,?,?,?,?,?,?,?,?)");

				pst4.setString(1, CC_CODE);
				pst4.setString(2, transno);
				pst4.setString(3, (String) obj.get("ITEM_CODE"));
				pst4.setString(4, (String) obj.get("QUANTITY"));
				pst4.setString(5, (String) obj.get("RATE"));
				pst4.setString(6,  (String) obj.get("REV"));
				pst4.setString(7,  (String) obj.get("TSC"));
				pst4.setString(8, (String) obj.get("VAT"));
				pst4.setString(9, USER);
				
				pst4.executeUpdate();
			}

			con.commit();

			return "Sucessfully added data";

		} catch (SQLException ex) {
			con.rollback();
			ex.printStackTrace();
			return "Failed " + ex;
		} finally {

			con.close();

		}

	}


}
