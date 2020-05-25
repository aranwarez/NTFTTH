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

import util.DbCon;

public class ProfileDao {

	public static Map<String, String> getSessionRegion(String user, String USER_LEVEL) {
		try {
			List<Map<String, Object>> userinfo = getUserInfo(user);
			String regioncode = userinfo.get(0).get("REGION_CODE").toString();
			String zonecode = userinfo.get(0).get("ZONE_CODE").toString();
			String districtcode = userinfo.get(0).get("DISTRICT_CODE").toString();
			String officecode = userinfo.get(0).get("OFFICE_CODE").toString();
			if (USER_LEVEL.equals("1")) {
				regioncode = null;
				zonecode = null;
				districtcode = null;
				officecode = null;
			} else if (USER_LEVEL.equals("2")) {
				zonecode = null;
				districtcode = null;
				officecode = null;
			} else if (USER_LEVEL.equals("3")) {
				districtcode = null;
				officecode = null;
			} else if (USER_LEVEL.equals("4")) {
				officecode = null;
			}

			Map<String, String> regionmap = null;
			regionmap = new HashMap<String, String>();
			regionmap.put("Region", regioncode);
			regionmap.put("Zone", zonecode);
			regionmap.put("District", districtcode);
			regionmap.put("Office", officecode);
			return regionmap;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static List<Map<String, Object>> getUserInfo(String USER_ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("select * from vw_user_all where USER_ID=? order by USER_ID");
			pst.setString(1, USER_ID);
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

	public String updateProfile(String fullname, String mobileno, String userid, String updateby) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(
					"update WEB_USER set full_name=?,mobile_no=?,UPDATE_BY=?,UPDATE_DT=sysdate where user_id=?");
			pst.setString(1, fullname);
			pst.setString(2, mobileno);
			pst.setString(3, updateby);
			pst.setString(4, userid);
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed " + e;
		} finally {
			con.close();

		}
		return "Successfully saved!";
	}

	public static String loginLog(String USER_ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("insert into LOGIN_LOG(USER_ID,CREATE_DT)values(?,sysdate)");
			pst.setString(1, USER_ID);
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed " + e.getMessage();
		} finally {
			con.close();

		}
		return "DONE";

	}

	public static String loginCount(String USER_ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("select count(user_id)count from login_log where user_id=?");
			pst.setString(1, USER_ID);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed " + e.getMessage();
		} finally {
			con.close();

		}
		return null;

	}

	public static String firstTimePasswordCheck(String USER_ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("select PASS_UPDATE from web_user where user_id=?");
			pst.setString(1, USER_ID);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed " + e.getMessage();
		} finally {
			con.close();

		}
		return null;

	}

	public String uniqueUserCheck(String USER_ID) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
//				This user id already Exists
			PreparedStatement pst1 = con
					.prepareStatement("select user_id,mobile_no from web_user where user_id=? or user_id=?");

			pst1.setString(1, USER_ID.toUpperCase());
			pst1.setString(2, USER_ID);
			ResultSet rs = pst1.executeQuery();
			if (rs.next()) {

				return "This user id already Exists";
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			con.close();

		}
		return "";

	}

}
