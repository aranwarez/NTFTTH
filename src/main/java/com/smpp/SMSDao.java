package com.smpp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DbCon;

public class SMSDao {
	public String ADDSMSLOG(String MESSAGE_ID, String TRANS_ID, 
			   Date date,Date date2,String MESSAGE, 
			   String STATUS,String REMARKS,String CREATE_BY, 
			   String REF_TOKEN,String Number) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO SMS_LOG (\r\n" + 
            		"   ID, MESSAGE_ID, TRANS_ID, \r\n" + 
            		"   SUBMIT_DT, DELIVERY_DT, MESSAGE, \r\n" + 
            		"   STATUS, REMARKS, CREATE_BY, \r\n" + 
            		"   CREATE_DT, REF_TOKEN,MOBILE_NO) \r\n" + 
            		"VALUES ( SMS_SEQ_ID.nextval,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?, sysdate,\r\n" + 
            		" ?,?)");
            pst.setString(1, MESSAGE_ID);
            pst.setString(2, TRANS_ID);
            pst.setDate(3, date);
            pst.setDate(4, date2);
            pst.setString(5, MESSAGE);
            pst.setString(6, STATUS);
            pst.setString(7, REMARKS);
            pst.setString(8, CREATE_BY);
            pst.setString(9, REF_TOKEN);
            pst.setString(10, Number);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println( MESSAGE_ID+":"+  TRANS_ID+":"+ 
    			    date+":"+ date2+":"+ MESSAGE+":"+ 
    			    STATUS+":"+ REMARKS+":"+ CREATE_BY+":"+ 
    			    REF_TOKEN);
            return "Failed To append SMS log in DB :" + e.getLocalizedMessage();
        } finally {
            con.close();
        }
        return "SMS log successfully.";
    }
	
	public String UPDATESMSLOG(String MESSAGE_ID, String TRANS_ID, 
			   Date date,Date date2,String MESSAGE, 
			   String STATUS,String REMARKS,String CREATE_BY, 
			   String REF_TOKEN,String Number) throws SQLException {
     Connection con = DbCon.getConnection();
     try {
         PreparedStatement pst = con.prepareStatement("\r\n" + 
         		"MERGE INTO SMS_LOG\r\n" + 
         		"     USING DUAL\r\n" + 
         		"        ON (MESSAGE_ID = ? AND mobile_no = ?)\r\n" + 
         		"WHEN MATCHED\r\n" + 
         		"THEN\r\n" + 
         		"    UPDATE SET TRANS_ID = ?,\r\n" + 
         		"               SUBMIT_DT = ?,\r\n" + 
         		"               DELIVERY_DT = ?,\r\n" + 
         		"               STATUS = ?\r\n" + 
         		"WHEN NOT MATCHED\r\n" + 
         		"THEN\r\n" + 
         		"    INSERT     (ID,\r\n" + 
         		"                MESSAGE_ID,\r\n" + 
         		"                TRANS_ID,\r\n" + 
         		"                SUBMIT_DT,\r\n" + 
         		"                DELIVERY_DT,\r\n" + 
         		"                MESSAGE,\r\n" + 
         		"                STATUS,\r\n" + 
         		"                REMARKS,\r\n" + 
         		"                CREATE_BY,\r\n" + 
         		"                CREATE_DT,\r\n" + 
         		"                REF_TOKEN,\r\n" + 
         		"                MOBILE_NO)\r\n" + 
         		"        VALUES (SMS_SEQ_ID.nextval, ?,?,?,?,?,?,?,?,sysdate,?,?)");
         //for update
         pst.setString(1, MESSAGE_ID);
         pst.setString(2, Number);
         pst.setString(3, TRANS_ID);
         pst.setDate(4, date);
         pst.setDate(5, date2);
         pst.setString(6, STATUS);
         //for insert
         pst.setString(7, MESSAGE_ID);
         pst.setString(8, TRANS_ID);
         pst.setDate(9, date);
         pst.setDate(10, date2);
         pst.setString(11, MESSAGE);
         pst.setString(12, STATUS);
         pst.setString(13, REMARKS);
         pst.setString(14, CREATE_BY);
         pst.setString(15, REF_TOKEN);
         pst.setString(16, Number);
         pst.executeUpdate();

     } catch (Exception e) {
         e.printStackTrace();
         System.err.println( MESSAGE_ID+":"+  TRANS_ID+":"+ 
 			    date+":"+ date2+":"+ MESSAGE+":"+ 
 			    STATUS+":"+ REMARKS+":"+ CREATE_BY+":"+ 
 			    REF_TOKEN);
         return "Failed To append SMS log in DB :" + e.getLocalizedMessage();
     } finally {
         con.close();
     }
     return "SMS log successfully.";
 }
	

}
