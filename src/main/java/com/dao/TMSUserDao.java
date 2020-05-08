package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.model.TMSUser;

import util.DbCon;

public class TMSUserDao {
	private static final List<TMSUser> tmsusers = new ArrayList<TMSUser>();

	public void getnewTMSUsers() throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("select * from TMS_USERS where STATUS='N'");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Boolean found = false;

				for (int i = 0; i < tmsusers.size(); i++) {
					if (tmsusers.get(i).getUSERNAME().equals(rs.getString(1))) {
						found = true;
					}
				}
				if (!found) {
					TMSUser obj = new TMSUser();
					obj.setUSERNAME(rs.getString(1));
					obj.setPASSWORD(rs.getString(2));
					obj.setSTATUS(rs.getString(3));
					tmsusers.add(obj);
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		
	}
	
public TMSUser getUserCreden() throws SQLException{
	
	for (int i = 0; i < tmsusers.size(); i++) {
		if(tmsusers.get(i).getSTATUS().equals("N")) {
			TMSUser obj=new TMSUser();
			obj.setUSERNAME(tmsusers.get(i).getUSERNAME());
			obj.setPASSWORD(tmsusers.get(i).getPASSWORD());
			tmsusers.get(i).setSTATUS("Y");
		return obj;
		}
		
	}
	//if no result found try once again to get new items from db
	
		getnewTMSUsers();
		
	
	for (int i = 0; i < tmsusers.size(); i++) {
		if(tmsusers.get(i).getSTATUS().equals("N")) {
			TMSUser obj=new TMSUser();
			obj.setUSERNAME(tmsusers.get(i).getUSERNAME());
			obj.setPASSWORD(tmsusers.get(i).getPASSWORD());
			tmsusers.get(i).setSTATUS("Y");
		return obj;
		}
		
	}
	return null;
	
	
}


public void CloseTMSstatus(String username){
	
	for (int i = 0; i < tmsusers.size(); i++) {
		if(tmsusers.get(i).getUSERNAME().equals(username)) {
			tmsusers.get(i).setSTATUS("N");
		}
		
	}
	//if no result found try once again to get new items from db
}

public void ErroroutTMSstatus(String username){
	
	for (int i = 0; i < tmsusers.size(); i++) {
		if(tmsusers.get(i).getUSERNAME().equals(username)) {
			tmsusers.get(i).setSTATUS("E");
			try {
			ErrorOutTMS(username);
			tmsusers.remove(i);
			}
			catch(Exception e) {
				System.err.println("Error while updating status to Error to TMS for username:"+username);;
				e.printStackTrace();
				
			}
			
			
		}
		
	}

}



public String ErrorOutTMS(String username) throws SQLException {
	Connection con = DbCon.getConnection();
	PreparedStatement pst = null;
	try {

		pst = con.prepareStatement(
				"UPDATE TMS_USERS SET Status='E',update_dt=sysdate,update_by='System' where username=?");
		pst.setString(1, username);
		pst.executeUpdate();

	} catch (Exception e) {
		e.printStackTrace();
		return "Failed "+e;
	} finally {
		con.close();
	}
	return "Successfully Errored Record";
}





}
