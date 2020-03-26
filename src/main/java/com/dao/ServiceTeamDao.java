package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.MenuAccess;
import com.model.ServiceTeamModel;

import util.DbCon;

public class ServiceTeamDao {
	
	public static List<ServiceTeamModel> getModeList(String SERVICE_TYPE_ID) throws Exception {
		Connection con = DbCon.getConnection();

		List<ServiceTeamModel> modelist = new ArrayList<ServiceTeamModel>();
		ServiceTeamModel m = null;
		try {

			PreparedStatement pst = con.prepareStatement(
					"select * from M_SERVICE_TEAM_MAP where SERVICE_TYPE_ID=?");
			pst.setString(1, SERVICE_TYPE_ID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				
				m = new ServiceTeamModel();
				m.setMSTM_ID(rs.getString("MSTM_ID"));
				
				m.setSERVICE_TYPE_ID(rs.getString("SERVICE_TYPE_ID"));			
				m.setSUB_TEAM_CODE(rs.getString("SUB_TEAM_CODE"));
				

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
	public String SaveMenuAccess(String SERVICE_TYPE_ID, List<Map<String, Object>> editmodelist, String USER)
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
					"delete from M_SERVICE_TEAM_MAP where SERVICE_TYPE_ID=?");

			pst1.setString(1, SERVICE_TYPE_ID);
			pst1.executeUpdate();

			for (Map<String, Object> obj : editmodelist) {
				PreparedStatement pst4 = con.prepareStatement("insert into M_SERVICE_TEAM_MAP(MSTM_ID,SERVICE_TYPE_ID,SUB_TEAM_CODE,CREATE_BY,CREATE_DT)\r\n" + 
						"values((MSTM_ID.nextval),?,?,?,sysdate)");

				pst4.setString(1, (String) obj.get("SERVICE_TYPE_ID"));
				pst4.setString(2, (String) obj.get("SUB_TEAM_CODE"));
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
