package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.model.MenuAccess;

import util.DbCon;

public class CommonMenuDao {

	public static MenuAccess checkAccess(String ROLE_CODE, String MENU_URL) {
		Connection con = DbCon.getConnection();

		try {
			MenuAccess m = new MenuAccess();

			PreparedStatement pst = con.prepareStatement(
					"select * from edit_mode where ROLE_CODE=? and menu_code=(select menu_code FROM WEB_MENU_ENTRY WHERE menu_url like ?)");
			pst.setString(1, ROLE_CODE);
			pst.setString(2, "%" + MENU_URL);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				m.setLIST_FLAG(rs.getString("LIST_FLAG"));
				m.setADD_FLAG(rs.getString("ADD_FLAG"));
				m.setEDIT_FLAG(rs.getString("EDIT_FLAG"));

				m.setDELETE_FLAG(rs.getString("DELETE_FLAG"));
				m.setPOST_FLAG(rs.getString("POST_FLAG"));
				m.setCANCEL_FLAG(rs.getString("CANCEL_FLAG"));
				return m;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
