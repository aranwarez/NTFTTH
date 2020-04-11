package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.DbCon;

public class ComplainDao {

	private String tOKEN_ID;
	private String sub_token_id;

	public String saveProblem(List<Map<String, Object>>servicestypelist,String serviceID, String SRV_NO,String Complain_no,String contactName,String Remarks,String USER,String FDCName) throws SQLException {
        Connection con = DbCon.getConnection();
        con.setAutoCommit(false);
        try {
        	
        	String cqry="select * from MAIN_TOKEN_MASTER where SRV_NO=? and SOLVE_FLAG ='N'";
        	
        	PreparedStatement qpst = con.prepareStatement(cqry);
        	qpst.setString(1,SRV_NO);
             ResultSet rs = qpst.executeQuery();
             if(rs.next()==true) {
            	 return ("Complain Already Exist");
          //  	 throw new SQLException("Complain Already Exist");
          	 }
            cqry="select MTM_Token_ID.NEXTVAL from dual"; 
            ResultSet tokenrs = con.prepareStatement(cqry).executeQuery();
             while(tokenrs.next()){
             tOKEN_ID=tokenrs.getString(1);
             }
             
             
        	String qry = "INSERT INTO MAIN_TOKEN_MASTER (TOKEN_ID,\n" + 
            		"                                    SERVICE_ID,\n" + 
            		"                                    SRV_NO, SUB_TEAM_CODE, \n" + 
            		"                                    SOLVE_FLAG,\n" + 
            		"                                    COMPLAIN_NO,\n" + 
            		"                                    CONTACT_NAME,\n" + 
                	
            		"                                    REMARKS,\n" + 
            		"                                    CREATE_BY, \n" + 
               		"                                    FDC_CODE) \n" + 
               	 
            		"     VALUES (?,\n" + 
            		"             ?,\n" + 
            		"             ?,\n" + 
            		"             ?,\n" + 
            		"             ?,\n" + 
            		"             ?,\n" + 
            		"             ?,\n" + 
            		"             ?,\n" + 
               		"             ?,\n" + 
              	 
            		"             (select fdc_code from M_FDC where DESCRIPTION=?)\r\n" + 
            		")";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, tOKEN_ID);
            pst.setString(2, serviceID);
            pst.setString(3, SRV_NO);
            pst.setString(4, "FLMTA");
            pst.setString(5, "N");
            pst.setString(6, Complain_no);
            pst.setString(7, contactName);
            pst.setString(8, Remarks);
            pst.setString(9, USER);
            pst.setString(10, FDCName);
            pst.executeUpdate();
          
            //for multiple services - token master
        	cqry="select TM_SUB_TOKEN_ID.NEXTVAL from dual"; 
            
            String subqry="INSERT INTO FTTH.TOKEN_MASTER (SUB_TOKEN_ID,\r\n" + 
		"                               TOKEN_ID,\r\n" + 
		"                               SUB_TEAM_CODE,\r\n" + 
		"                               SOLVE_FLAG,\r\n" + 
		"                               PROBLEM_ID,\r\n" + 
		"                               REMARKS,\r\n" + 
		"                               CREATE_BY,\r\n" + 
		"                               CREATE_DT)\r\n" + 
		"     VALUES (?,\r\n" + 
		"             ?,\r\n" + 
		"             (SELECT sub_team_code\r\n" + 
		"                FROM m_problem\r\n" + 
		"               WHERE problem_id = ?),\r\n" + 
		"             'N',\r\n" + 
		"             ?,\r\n" + 
		"             ?,\r\n" + 
		"             ?,\r\n" + 
		"             SYSDATE)" ;           
           

if(servicestypelist.size()>0) {
            	for (Map<String, Object> obj : servicestypelist) {
            	   tokenrs = con.prepareStatement(cqry).executeQuery();
                     while(tokenrs.next()){
                     sub_token_id=tokenrs.getString(1);
                     }
                      pst = con.prepareStatement(subqry);
                     pst.setString(1, sub_token_id);
                     pst.setString(2, tOKEN_ID);
                     pst.setString(3, (String) obj.get("PROBLEM_ID"));
                     pst.setString(4, (String) obj.get("PROBLEM_ID"));
                     pst.setString(5, (String) obj.get("REMARKS"));
                     pst.setString(6, USER);
                     pst.executeUpdate();
            
            	
                     //for multiple services - token detail
                    String seqqry="select TM_SUB_TOKEN_DETAIL_ID.NEXTVAL from dual"; 
                    tokenrs = con.prepareStatement(seqqry).executeQuery();
                    while(tokenrs.next()){
                    	seqqry=tokenrs.getString(1);
                    }
                    String detailqry="INSERT INTO FTTH.TOKEN_DETAIL (\r\n" + 
                    		"   TD_ID, SUB_TOKEN_ID, FROM_SUB_TEAM_CODE, \r\n" + 
                    		"   TO_SUB_TEAM_CODE, SOLVE_FLAG, PROBLEM_ID, \r\n" + 
                    		"   REMARKS, CREATE_BY, CREATE_DT \r\n" + 
                    		"   ) \r\n" + 
                    		"VALUES ( TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + 
                    		" ?,\r\n" + 
                    		" (select sub_team_code from m_problem where problem_id=?),\r\n" + 
                    		" (select sub_team_code from m_problem where problem_id=?),\r\n" + 
                    		" 'N',\r\n" + 
                    		" ?,\r\n" + 
                    		" ?,\r\n" + 
                    		" ?,\r\n" + 
                    		" sysdate)";
                    
                    pst = con.prepareStatement(detailqry);
                    pst.setString(1, sub_token_id);
                   pst.setString(2, (String) obj.get("PROBLEM_ID"));
                    pst.setString(3, (String) obj.get("PROBLEM_ID"));
                    pst.setString(4, (String) obj.get("PROBLEM_ID"));
                    
                    pst.setString(5, (String) obj.get("REMARKS"));
                    pst.setString(6, USER);
                    pst.executeUpdate();
                    
                    
                     
                     
            	
            	// ---------------- token detail
            	}
            }
            con.commit();
            return "Succesfully Saved Complaint";

        } catch (Exception e) {
            con.rollback();
            e.printStackTrace();
            return "Failed to Save : " + e.getMessage();
        } finally {
            con.close();
        }
    }

	
	
}
