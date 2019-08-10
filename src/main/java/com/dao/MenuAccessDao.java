package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.MenuAccess;

import util.DbCon;

public class MenuAccessDao {

	
	
	public static List<MenuAccess> getModeList(String ROLE_CODE) throws Exception {
		Connection con = DbCon.getConnection();

		List<MenuAccess> modelist = new ArrayList<MenuAccess>();
		MenuAccess m = null;
		try {

			PreparedStatement pst = con.prepareStatement(
					"select ROLE_CODE,MENU_CODE,EDIT_FLAG,DELETE_FLAG,POST_FLAG,ADD_FLAG,CANCEL_FLAG,LIST_FLAG from EDIT_MODE where ROLE_CODE=?");
			pst.setString(1, ROLE_CODE);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				m = new MenuAccess();
				m.setROLE_CODE(rs.getString("ROLE_CODE"));
				m.setMENU_CODE(rs.getString("MENU_CODE"));
			
				m.setEDIT_FLAG(rs.getString("EDIT_FLAG"));
				m.setDELETE_FLAG(rs.getString("DELETE_FLAG"));
				m.setPOST_FLAG(rs.getString("POST_FLAG"));
				m.setADD_FLAG(rs.getString("ADD_FLAG"));
				m.setCANCEL_FLAG(rs.getString("CANCEL_FLAG"));
				m.setLIST_FLAG(rs.getString("LIST_FLAG"));

				modelist.add(m);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return modelist;
	}

//  menu-access role define
	public String SaveMenuAccess(String ROLE_CODE, List<Map<String, Object>> editmodelist, String USER)
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
					"delete from EDIT_MODE where ROLE_CODE=?");

			pst1.setString(1, ROLE_CODE);
			pst1.executeUpdate();

			for (Map<String, Object> obj : editmodelist) {
				PreparedStatement pst4 = con.prepareStatement(
						"insert into EDIT_MODE(ROLE_CODE,MENU_CODE,EDIT_FLAG,DELETE_FLAG,POST_FLAG,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,ADD_FLAG,CANCEL_FLAG,LIST_FLAG)values(?,?,?,?,?,?,sysdate,?,sysdate,?,?,?)");

				pst4.setString(1, (String) obj.get("ROLE_CODE"));
				pst4.setString(2, (String) obj.get("MENU_CODE"));
				pst4.setString(3, (String) obj.get("EDIT_FLAG"));
				pst4.setString(4, (String) obj.get("DELETE_FLAG"));
				pst4.setString(5, (String) obj.get("POST_FLAG"));
				pst4.setString(6, USER);
				pst4.setString(7, USER);
				pst4.setString(8, (String) obj.get("ADD_FLAG"));
				pst4.setString(9, (String) obj.get("CANCEL_FLAG"));
				pst4.setString(10, (String) obj.get("LIST_FLAG"));
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
