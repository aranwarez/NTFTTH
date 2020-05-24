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
			   String REF_TOKEN) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO SMS_LOG (\r\n" + 
            		"   ID, MESSAGE_ID, TRANS_ID, \r\n" + 
            		"   SUBMIT_DT, DELIVERY_DT, MESSAGE, \r\n" + 
            		"   STATUS, REMARKS, CREATE_BY, \r\n" + 
            		"   CREATE_DT, REF_TOKEN) \r\n" + 
            		"VALUES ( SMS_SEQ_ID.nextval,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?,\r\n" + 
            		" ?, sysdate,\r\n" + 
            		" ?)");
            pst.setString(1, MESSAGE_ID);
            pst.setString(2, TRANS_ID);
            pst.setDate(3, date);
            pst.setDate(4, date2);
            pst.setString(5, MESSAGE);
            pst.setString(6, STATUS);
            pst.setString(7, REMARKS);
            pst.setString(8, CREATE_BY);
            pst.setString(9, REF_TOKEN);

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
