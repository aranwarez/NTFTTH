package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.MenuAccess;
import com.model.ServiceTeamModel;
import com.model.UserFdcModel;

import util.DbCon;

public class UserFdcDao {
	
	public static List<UserFdcModel> getModeList(String USER_ID) throws Exception {
		Connection con = DbCon.getConnection();

		List<UserFdcModel> modelist = new ArrayList<UserFdcModel>();
		UserFdcModel m = null;
		try {

			PreparedStatement pst = con.prepareStatement(
					"select WUTM_ID,USER_ID,FDC_CODE,common.to_BS(ACTIVE_DT) as ACTIVE_DT,common.to_BS(DEACTIVE_DT) as DEACTIVE_DT\r\n" + 
					" from WEB_USER_FDC_MAP\r\n" + 
					"where USER_ID=?");
			pst.setString(1, USER_ID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				
				m = new UserFdcModel();
				m.setWUTM_ID(rs.getString("WUTM_ID"));				
				m.setUSER_ID(rs.getString("USER_ID"));			
				m.setFDC_CODE(rs.getString("FDC_CODE"));				
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
	
//  
	public String SaveUserFDC(String USER_ID, List<Map<String, Object>> editmodelist, String USER)
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
					"delete from WEB_USER_FDC_MAP where USER_ID=?");

			pst1.setString(1, USER_ID);
			pst1.executeUpdate();
			
			//delete user reference from WEB_USER_OFFICE_MAP
			 pst1 = con.prepareStatement(
					"delete from WEB_USER_OFFICE_MAP where USER_ID=?");

			pst1.setString(1, USER_ID);
			pst1.executeUpdate();

			
			PreparedStatement pst4 = con.prepareStatement("insert into WEB_USER_FDC_MAP\r\n" + 
					"(WUTM_ID,USER_ID,FDC_CODE,ACTIVE_DT,DEACTIVE_DT,CREATE_BY,CREATE_DT)\r\n" + 
					"values((WUTM_ID.nextval),?,?,common.to_ad(?),common.to_ad(?),?,sysdate)");
			
			for (Map<String, Object> obj : editmodelist) {
				

				pst4.setString(1, (String) obj.get("USER_ID"));
				pst4.setString(2, (String) obj.get("FDC_CODE"));
				
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
	
	
	 public List<Map<String, Object>> getServiceTeamList() throws SQLException {
	        Connection con = DbCon.getConnection();

	        try {
	            PreparedStatement pst = con.prepareStatement("select * from M_SERVICE_TEAM_MAP order by MSTM_ID");
	            ResultSet rs = pst.executeQuery();

	            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	            Map<String, Object> row = null;

	            ResultSetMetaData metaData = rs.getMetaData();
	            Integer columnCount = metaData.getColumnCount();

	            while (rs.next()) {
	                row = new HashMap<String, Object>();
	                for (int i = 1; i <= columnCount; i++) {
	                    row.put(metaData.getColumnName(i), rs.getObject(i));
	                }
	                resultList.add(row);
	            }
	            return resultList;
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            con.close();
	        }
	        return null;
	    }
	
}
