package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.UserOfficeModel;

import util.DbCon;

public class UserOfficeDao {
	public static List<UserOfficeModel> getModeList(String USER_ID) throws Exception {
		Connection con = DbCon.getConnection();

		List<UserOfficeModel> modelist = new ArrayList<UserOfficeModel>();
		UserOfficeModel m = null;
		try {

			PreparedStatement pst = con.prepareStatement(
					"select WUTM_ID,USER_ID,OFFICE_CODE\r\n" + 
					" from WEB_USER_OFFICE_MAP\r\n" + 
					"where USER_ID=?");
			pst.setString(1, USER_ID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				
				m = new UserOfficeModel();
				m.setWUTM_ID(rs.getString("WUTM_ID"));				
				m.setUSER_ID(rs.getString("USER_ID"));			
				m.setOFFICE_CODE(rs.getString("OFFICE_CODE"));				
				
				
				modelist.add(m);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return modelist;
	}
	
	public String SaveUserOffice(String USER_ID, List<Map<String, Object>> editmodelist, String USER)
			throws SQLException {
		Connection con = DbCon.getConnection();
		
		try {
			con.setAutoCommit(false);

			// if value found then code goes here

			/*
			 * ------------------------------------ editMode save and update
			 * ------------------------------------
			 */

			PreparedStatement pst1 = con.prepareStatement(
					"delete from WEB_USER_OFFICE_MAP where USER_ID=?");

			pst1.setString(1, USER_ID);
			pst1.executeUpdate();
			PreparedStatement pst4 = con.prepareStatement("insert into WEB_USER_OFFICE_MAP\r\n" + 
					"(WUTM_ID,USER_ID,OFFICE_CODE,CREATE_BY,CREATE_DT)\r\n" + 
					"values((WUTM_ID.nextval),?,?,?,sysdate)");
			
			for (Map<String, Object> obj : editmodelist) {
				

				pst4.setString(1, (String) obj.get("USER_ID"));
				pst4.setString(2, (String) obj.get("OFFICE_CODE"));
				
				
				pst4.setString(3, USER);
				
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
