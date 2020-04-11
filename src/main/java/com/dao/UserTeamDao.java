package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.ServiceTeamModel;
import com.model.UserTeamModel;

import util.DbCon;

public class UserTeamDao {
	
	public static List<UserTeamModel> getModeList(String USER_ID) throws Exception {
		Connection con = DbCon.getConnection();

		List<UserTeamModel> modelist = new ArrayList<UserTeamModel>();
		UserTeamModel m = null;
		try {

			PreparedStatement pst = con.prepareStatement(
					"select WUTM_ID,USER_ID, SUB_TEAM_CODE,common.to_bs(ACTIVE_DT) as ACTIVE_DT, common.to_bs(DEACTIVE_DT) as DEACTIVE_DT\r\n" + 
					"from WEB_USER_TEAM_MAP\r\n" + 
					"where   USER_ID =?");
			pst.setString(1, USER_ID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				
				m = new UserTeamModel();		
				
				m.setWUTM_ID(rs.getString("WUTM_ID"));
				m.setUSER_ID(rs.getString("USER_ID"));
				
				m.setSUB_TEAM_CODE(rs.getString("SUB_TEAM_CODE"));			
				m.setACTIVE_DT(rs.getString("ACTIVE_DT"));
				m.setDEACTIVE_DT(rs.getString("DEACTIVE_DT"));

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
	public String SaveUserTeam(String USER_ID, List<Map<String, Object>> editmodelist, String USER)
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
					"delete from WEB_USER_TEAM_MAP where USER_ID=?");

			pst1.setString(1, USER_ID);
			pst1.executeUpdate();

			for (Map<String, Object> obj : editmodelist) {
				PreparedStatement pst4 = con.prepareStatement("insert into WEB_USER_TEAM_MAP(WUTM_ID,USER_ID,SUB_TEAM_CODE,ACTIVE_DT,DEACTIVE_DT,CREATE_BY,CREATE_DT)\r\n" + 
						"values((WUTM_ID.nextval),?,?,common.to_ad(?),common.to_ad(?),?,sysdate)");

				pst4.setString(1, (String) obj.get("USER_ID"));
				pst4.setString(2, (String) obj.get("SUB_TEAM_CODE"));
				pst4.setString(3, (String) obj.get("ACTIVE_DT"));
				pst4.setString(4, (String) obj.get("DEACTIVE_DT"));
				
				pst4.setString(5, USER);
				
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
