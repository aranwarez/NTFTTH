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

import com.model.UserInformationModel;
import com.smpp.SendSMS;

import util.DbCon;

public class UserDao {

	// check user name and password
	public UserInformationModel getUserByUsername(String username, String password) throws SQLException {

		Connection con = DbCon.getConnection();
		UserInformationModel level = new UserInformationModel();
		try {
			String qry = "select * from WEB_USER where upper(user_id)=upper(?) and password=app_user_security.get_hash(upper(?),?)";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, username.toUpperCase());
			pst.setString(2, username);
			pst.setString(3, password);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {

				level.setUSER_ID(rs.getString("USER_ID"));
				level.setFULL_NAME(rs.getString("FULL_NAME"));
				level.setPASSWORD(rs.getString("PASSWORD"));
				level.setEMPLOYEE_CODE(rs.getString("EMPLOYEE_CODE"));
				level.setLOCK_FLAG(rs.getString("LOCK_FLAG"));
				level.setSUPER_FLAG(rs.getString("SUPER_FLAG"));
				level.setCREATED_BY(rs.getString("CREATED_BY"));
				level.setCREATED_DATE(rs.getString("CREATED_DATE"));
				level.setDISABLE_FLAG(rs.getString("DISABLE_FLAG"));
				level.setUSER_LEVEL(rs.getString("USER_LEVEL"));
				level.setROLE_CODE(rs.getString("ROLE_CODE"));

				level.setOFFICE_CODE(rs.getString("OFFICE_CODE"));

				return level;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	public List<UserInformationModel> getList() throws SQLException {
		// String enc_code=(getEncrCode(password));
		Connection con = DbCon.getConnection();
		UserInformationModel level = null;
		List<UserInformationModel> list = new ArrayList<UserInformationModel>();
		try {

			String qry = "select (select description from m_office where office_code=a.office_code) office,a.user_id,a.full_name,\r\n"
					+ "a.password,a.employee_code,a.lock_flag,\r\n" + "a.super_flag,a.disable_flag,\r\n"
					+ "a.location_code,a.user_level,\r\n" + "a.role_code,a.office_code,\r\n"
					+ "a.mobile_no,a.created_by,\r\n" + "a.created_Date,a.UPDATE_BY,\r\n" + "a.update_Dt\r\n"
					+ "from web_user a order by a.user_id";
			PreparedStatement pst = con.prepareStatement(qry);

			ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				level = new UserInformationModel();
				level.setSN(i);

				level.setUSER_ID(rs.getString("USER_ID"));
				level.setFULL_NAME(rs.getString("FULL_NAME"));
				level.setPASSWORD(rs.getString("PASSWORD"));
				level.setEMPLOYEE_CODE(rs.getString("EMPLOYEE_CODE"));
				level.setLOCK_FLAG(rs.getString("LOCK_FLAG"));
				level.setSUPER_FLAG(rs.getString("SUPER_FLAG"));
				level.setOFFICE_CODE(rs.getString("OFFICE_CODE"));
				level.setOFFICE(rs.getString("OFFICE"));
				level.setCREATED_BY(rs.getString("CREATED_BY"));
				level.setCREATED_DATE(rs.getString("CREATED_DATE"));
				level.setDISABLE_FLAG(rs.getString("DISABLE_FLAG"));
				level.setUSER_LEVEL(rs.getString("USER_LEVEL"));
				level.setROLE_CODE(rs.getString("ROLE_CODE"));
				level.setMOBILE_NO(rs.getString("MOBILE_NO"));
				list.add(level);

				i = i + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return list;
	}

	public String saveUser(UserInformationModel m) throws SQLException {
		Connection con = DbCon.getConnection();
	//	con.setAutoCommit(false);
		try {
			PreparedStatement pst = con.prepareStatement("insert into web_user(USER_ID,FULL_NAME,PASSWORD,\r\n"
					+ "EMPLOYEE_CODE,LOCK_FLAG,SUPER_FLAG,DISABLE_FLAG,\r\n"
					+ "LOCATION_CODE,USER_LEVEL,ROLE_CODE,OFFICE_CODE,\r\n" + "MOBILE_NO,CREATED_BY,CREATED_DATE)\r\n"
					+ "values(?,?,app_user_security.get_hash(upper(?),?),?,?,?,?,?,?,?,?,?,?,sysdate)");
			pst.setString(1, m.getUSER_ID().toUpperCase());
			pst.setString(2, m.getFULL_NAME());
			pst.setString(3, m.getUSER_ID().toUpperCase());
			pst.setString(4, m.getPASSWORD());
			pst.setString(5, m.getEMPLOYEE_CODE());
			pst.setString(6, "N");
			pst.setString(7, m.getSUPER_FLAG());

			pst.setString(8, m.getDISABLE_FLAG());
			pst.setString(9, m.getLOCATION_CODE());

			pst.setString(10, m.getUSER_LEVEL());

			pst.setString(11, m.getROLE_CODE());

			pst.setString(12, m.getOFFICE_CODE());
			pst.setString(13, m.getMOBILE_NO());
			pst.setString(14, m.getUSER());

			pst.executeUpdate();
			String message = "FTTHCMS URL: http://172.16.39.16:8080/FTTH " + "Userid: " + m.getUSER_ID().toUpperCase()
					+ " Password: " + m.getPASSWORD();
			SendSMS.sendsms(m.getMOBILE_NO(), message, "USER ADDED", m.getUSER(), "0");
	//		con.commit();
		} catch (Exception e) {
	//		con.rollback();
			e.printStackTrace();
			if (e.getMessage().contains("unique constraint")) {

				return "This user id already Exists ";
			}
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
		return "Successfully saved!";
	}

	public String passWordChange(String pass, String USER_ID) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			if (pass != null) {

				PreparedStatement pst1 = con.prepareStatement("update web_user set pass_update='Y' where user_id=?");
				pst1.setString(1, USER_ID);
				pst1.executeUpdate();

				PreparedStatement pst = con.prepareStatement(
						"update WEB_USER set PASSWORD=app_user_security.get_hash(upper(?),?) where USER_ID=?");
				pst.setString(1, USER_ID);
				pst.setString(2, pass);

				pst.setString(3, USER_ID);
				pst.executeUpdate();

			} else {
				return "Password Field is Empty!!! ";
			}
		} catch (Exception e) {
			e.printStackTrace();

			return "Failed " + e;
		} finally {
			con.close();
		}
		return "Successfully updated !";
	}

	public UserInformationModel getUser(String id) throws SQLException {
		Connection con = DbCon.getConnection();
		UserInformationModel level = null;

		try {
			PreparedStatement pst = con.prepareStatement(
					"select a.*,(select employee_name from m_employee where employee_code=a.employee_code)employee_name,"
							+ "" + ""
							+ "(select DESCRIPTION from M_ROLE where ROLE_CODE=a.ROLE_CODE)ROLE_DESCRIPTION from WEB_USER a where USER_ID=?");
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			int i = 1;
			if (rs.next()) {
				level = new UserInformationModel();
				level.setSN(i);
				level.setUSER_ID(rs.getString("USER_ID"));
				level.setFULL_NAME(rs.getString("FULL_NAME"));
				level.setPASSWORD(rs.getString("PASSWORD"));
				level.setEMPLOYEE_CODE(rs.getString("EMPLOYEE_CODE"));
				level.setLOCK_FLAG(rs.getString("LOCK_FLAG"));

				level.setSUPER_FLAG(rs.getString("SUPER_FLAG"));
				level.setDISABLE_FLAG(rs.getString("DISABLE_FLAG"));
				level.setLOCATION_CODE(rs.getString("LOCATION_CODE"));

				level.setUSER_LEVEL(rs.getString("USER_LEVEL"));
				level.setROLE_CODE(rs.getString("ROLE_CODE"));

				level.setOFFICE_CODE(rs.getString("OFFICE_CODE"));
				level.setMOBILE_NO(rs.getString("MOBILE_NO"));

				level.setCREATED_BY(rs.getString("CREATED_BY"));
				level.setCREATED_DATE(rs.getString("CREATED_DATE"));

				level.setROLE_DESCRIPTION(rs.getString("ROLE_DESCRIPTION"));

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			con.close();
		}
		return level;
	}

	public String updateUser(UserInformationModel m) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("update web_user set FULL_NAME=?,\r\n"
					+ "EMPLOYEE_CODE=?,LOCK_FLAG=?,SUPER_FLAG=?,DISABLE_FLAG=?,\r\n"
					+ "LOCATION_CODE=?,USER_LEVEL=?,ROLE_CODE=?,OFFICE_CODE=?,\r\n"
					+ "MOBILE_NO=?,UPDATE_BY=?,UPDATE_DT=sysdate\r\n" + "where USER_ID=?");

			pst.setString(1, m.getFULL_NAME());
			pst.setString(2, m.getEMPLOYEE_CODE());
			pst.setString(3, m.getLOCK_FLAG());
			pst.setString(4, m.getSUPER_FLAG());
			pst.setString(5, m.getDISABLE_FLAG());
			pst.setString(6, m.getLOCATION_CODE());
			pst.setString(7, m.getUSER_LEVEL());
			pst.setString(8, m.getROLE_CODE());

			pst.setString(9, m.getOFFICE_CODE());
			pst.setString(10, m.getMOBILE_NO());

			pst.setString(11, m.getUSER());
			pst.setString(12, m.getUSER_ID());

			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

			return "Failed " + e;

		} finally {
			con.close();
		}
		return "Successfully updated!";
	}

	public String deleteUser(String code) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("update WEB_USER set LOCK_FLAG='Y' where USER_ID=?");

			pst.setString(1, code);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

			return "Failed " + e;

		} finally {
			con.close();
		}
		return "Successfully deleted!";
	}

	public static List<Map<String, Object>> getUserListByALL(String WORKING_REGION_CODE, String WORKING_ZONE_CODE,
			String WORKING_DIS_CODE, String WORKING_OFFICE_CODE) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement(
					"select * from vw_user_information where WORKING_REGION_CODE=nvl(null, WORKING_REGION_CODE)  and \r\n"
							+ "WORKING_ZONE_CODE=nvl(null, WORKING_ZONE_CODE) and WORKING_DIS_CODE=nvl(null, WORKING_DIS_CODE) and WORKING_OFFICE_CODE=nvl(null, WORKING_OFFICE_CODE)");

//            pst.setString(1, WORKING_REGION_CODE);
//            pst.setString(2, WORKING_ZONE_CODE);
//            pst.setString(3, WORKING_DIS_CODE);
//            pst.setString(4, WORKING_OFFICE_CODE);

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

	public static List<Map<String, Object>> getUserDetailByOfficeCode(String OFFICE_CODE) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement("select * from VW_FTTH_ALL_FDC where OFFICE_CODE=?");

			pst.setString(1, OFFICE_CODE);
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

	public String checkOldPAss(String user, String password) throws SQLException {
		Connection con = DbCon.getConnection();
		System.out.println("user id=" + user);
		System.out.println("password id=" + password);
		try {
			PreparedStatement pst = con.prepareStatement(
					"select * from WEB_USER where user_id=? AND PASSWORD=app_user_security.get_hash(upper(?),?)");
			pst.setString(1, user.toUpperCase());
			pst.setString(2, user.toUpperCase());
			pst.setString(3, password);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return "Confirmed!!";
			} else {
				return "Old Password Didnt Match";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return "Database issue!!! Contact Admin/Support for Help.";
	}

	public String generateToken(String user, String randnum) throws SQLException {
		Connection con = DbCon.getConnection();
		System.out.println("Generating Token for User :" + user);

		try {
			PreparedStatement pst = con.prepareStatement("select mobile_no from WEB_USER where user_id=?");
			pst.setString(1, user.toUpperCase());

			ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				return "Valid Mobile Not Found";
			}

			// validating mobile Number

			String msg = ("One Time Generated Key ID :" + randnum + " ,Username : " + user
					+ " , Valid for only 15 min.");
			SendSMS.sendsms(rs.getString(1).toString(), msg, "OTP TO RESET PASSWORD", user.toUpperCase(), randnum);

			return "OTP has been generated and sent to mobile Number. This token is valid for 15 mintues";

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return "Database issue!!! Contact Admin/Support for Help.";
	}

	// check user name and token key for password reset
	public UserInformationModel getUserByTokenkey(String username, String token) throws SQLException {

		Connection con = DbCon.getConnection();
		UserInformationModel level = new UserInformationModel();
		try {
			String qry = "SELECT *\r\n" + "  FROM WEB_USER\r\n" + " WHERE user_id =\r\n"
					+ "       (SELECT create_by\r\n" + "          FROM sms_log\r\n"
					+ "         WHERE     remarks = 'OTP TO RESET PASSWORD'\r\n"
					+ "               AND create_dt >= (SYSDATE - (15 / (24 * 60)))\r\n"
					+ "               AND ref_token = ?\r\n" + "               AND create_by = ?)";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, token);
			pst.setString(2, username.toUpperCase());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				// changing pass flag so that user changes password asap
				PreparedStatement pst1 = con.prepareStatement("update web_user set pass_update='N' where user_id=?");
				pst1.setString(1, username.toUpperCase());
				pst1.executeUpdate();
				//

				level.setUSER_ID(rs.getString("USER_ID"));
				level.setFULL_NAME(rs.getString("FULL_NAME"));
				level.setPASSWORD(rs.getString("PASSWORD"));
				level.setEMPLOYEE_CODE(rs.getString("EMPLOYEE_CODE"));
				level.setLOCK_FLAG(rs.getString("LOCK_FLAG"));
				level.setSUPER_FLAG(rs.getString("SUPER_FLAG"));
				level.setCREATED_BY(rs.getString("CREATED_BY"));
				level.setCREATED_DATE(rs.getString("CREATED_DATE"));
				level.setDISABLE_FLAG(rs.getString("DISABLE_FLAG"));
				level.setUSER_LEVEL(rs.getString("USER_LEVEL"));
				level.setROLE_CODE(rs.getString("ROLE_CODE"));
				level.setOFFICE_CODE(rs.getString("OFFICE_CODE"));
				return level;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
}
