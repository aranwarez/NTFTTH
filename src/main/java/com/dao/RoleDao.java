package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Role;

import util.DbCon;

public class RoleDao {

	public List<Role> getlist() throws SQLException {
		System.out.println("ARan ");
		Connection con = DbCon.getConnection();
		List<Role> list = new ArrayList<Role>();
		Role m = null;
		try {
			PreparedStatement pst = con.prepareStatement("select * from M_ROLE order by ROLE_CODE");
			ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				m = new Role();
				m.setSN(i);
				m.setROLE_CODE(rs.getString("ROLE_CODE"));
				m.setDESCRIPTION(rs.getString("DESCRIPTION"));
				list.add(m);
				i += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return list;
	}

	public String saveRole(Role m) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(
					"insert into m_role(ROLE_CODE,DESCRIPTION,CREATE_BY,CREATE_DT)values(?,?,?,sysdate)");
			pst.setString(1, m.getROLE_CODE());
			pst.setString(2, m.getDESCRIPTION());
			pst.setString(3, m.getUSER());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		} finally {
			con.close();
		}
		return "Sucessfully saved!";
	}

	public String updateRole(Role m) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(
					"update m_role set DESCRIPTION=?,UPDATE_BY=?,UPDATE_DT=sysdate where ROLE_CODE=?");
			pst.setString(1, m.getDESCRIPTION());
			pst.setString(2, m.getUSER());
			pst.setString(3, m.getROLE_CODE());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return "Sucessfully upated!";
	}

	public String deleteRole(String code) throws SQLException {
		System.out.println(code);
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("delete from m_role where ROLE_CODE=?");

			pst.setString(1, code);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		} finally {
			con.close();
		}
		return "Sucessfully deleted!";
	}

	public Role getRole(String code) throws SQLException {

		Connection con = DbCon.getConnection();

		Role m = null;
		try {
			PreparedStatement pst = con.prepareStatement("select * from M_ROLE where ROLE_CODE=?");
			pst.setString(1, code);
			ResultSet rs = pst.executeQuery();
			int i = 1;
			if (rs.next()) {
				m = new Role();

				m.setSN(i);
				m.setROLE_CODE(rs.getString("ROLE_CODE"));
				m.setDESCRIPTION(rs.getString("DESCRIPTION"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return m;
	}

}
