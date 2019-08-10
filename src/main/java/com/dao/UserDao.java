package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.UserInformationModel;

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
				level.setREGION_CODE(rs.getString("REGION_CODE"));
				level.setCC_CODE(rs.getString("CC_CODE"));
				level.setUSER_LEVEL(rs.getString("USER_LEVEL"));
				level.setROLE_CODE(rs.getString("ROLE_CODE"));
				level.setACC_CEN_CODE(rs.getString("ACC_CEN_CODE"));
				level.setMODULE_ACCESS(rs.getString("MODULE_ACCESS"));

				return level;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	public List<UserInformationModel> getList(String module_access) throws SQLException {
		// String enc_code=(getEncrCode(password));
		Connection con = DbCon.getConnection();
		UserInformationModel level = null;
		List<UserInformationModel> list = new ArrayList<UserInformationModel>();
		try {
			String qry = null;
			// user list as per module access
			if (module_access.substring(0, 1).equals("B")) {
				qry = "select USER_ID,FULL_NAME,PASSWORD,EMPLOYEE_CODE,LOCK_FLAG,SUPER_FLAG,CREATED_BY,CREATED_DATE,DISABLE_FLAG,REGION_CODE,USER_LEVEL,ROLE_CODE,CC_CODE,ACC_CEN_CODE, nvl(MODULE_ACCESS,'.') MODULE_ACCESS from WEB_USER";
			} else if (module_access.substring(0, 1).equals("C")) {
				qry = "select USER_ID,FULL_NAME,PASSWORD,EMPLOYEE_CODE,LOCK_FLAG,SUPER_FLAG,CREATED_BY,CREATED_DATE,DISABLE_FLAG,REGION_CODE,USER_LEVEL,ROLE_CODE,CC_CODE,ACC_CEN_CODE, nvl(MODULE_ACCESS,'.') MODULE_ACCESS from WEB_USER WHERE module_access='C'";
			} else {
				qry = "select USER_ID,FULL_NAME,PASSWORD,EMPLOYEE_CODE,LOCK_FLAG,SUPER_FLAG,CREATED_BY,CREATED_DATE,DISABLE_FLAG,REGION_CODE,USER_LEVEL,ROLE_CODE,CC_CODE,ACC_CEN_CODE, nvl(MODULE_ACCESS,'.') MODULE_ACCESS from WEB_USER WHERE module_access='P'";
			}
			// String qry = "select
			// USER_ID,FULL_NAME,PASSWORD,EMPLOYEE_CODE,LOCK_FLAG,SUPER_FLAG,CREATED_BY,CREATED_DATE,DISABLE_FLAG,REGION_CODE,USER_LEVEL,ROLE_CODE,CC_CODE,ACC_CEN_CODE,
			// nvl(MODULE_ACCESS,'.') MODULE_ACCESS from WEB_USER";
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

				level.setCREATED_BY(rs.getString("CREATED_BY"));
				level.setCREATED_DATE(rs.getString("CREATED_DATE"));
				level.setDISABLE_FLAG(rs.getString("DISABLE_FLAG"));

				level.setREGION_CODE(rs.getString("REGION_CODE"));
				level.setCC_CODE(rs.getString("CC_CODE"));
				level.setACC_CEN_CODE(rs.getString("ACC_CEN_CODE"));
				level.setUSER_LEVEL(rs.getString("USER_LEVEL"));
				level.setROLE_CODE(rs.getString("ROLE_CODE"));
				level.setMODULE_ACCESS(rs.getString("MODULE_ACCESS"));

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
		try {
			PreparedStatement pst = con.prepareStatement("insert into WEB_USER" + "(USER_ID,FULL_NAME,PASSWORD,"
					+ "EMPLOYEE_CODE,LOCK_FLAG,SUPER_FLAG," + "CREATED_BY,CREATED_DATE,DISABLE_FLAG,"
					+ "REGION_CODE,USER_LEVEL,ROLE_CODE,CC_CODE,ACC_CEN_CODE,MODULE_ACCESS)"
					+ "values(?,?,app_user_security.get_hash(upper(?),?),?,?,?,?,sysdate,?,?,?,?,?,?,?)");
			pst.setString(1, m.getUSER_ID().toUpperCase());
			pst.setString(2, m.getFULL_NAME());
			pst.setString(3, m.getUSER_ID().toUpperCase());
			pst.setString(4, m.getPASSWORD());

			pst.setString(5, m.getEMPLOYEE_CODE());
			pst.setString(6, "N");
			pst.setString(7, m.getSUPER_FLAG());
			pst.setString(8, m.getUSER());
			pst.setString(9, m.getDISABLE_FLAG());
			pst.setString(10, m.getREGION_CODE());
			pst.setString(11, m.getUSER_LEVEL());
			pst.setString(12, m.getROLE_CODE());
			pst.setString(13, m.getCC_CODE());
			pst.setString(14, m.getACC_CEN_CODE());
			pst.setString(15, m.getMODULE_ACCESS());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
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

				PreparedStatement pst = con.prepareStatement(
						"update WEB_USER set PASSWORD=app_user_security.get_hash(upper(?),?) where USER_ID=?");
				pst.setString(1, USER_ID.toUpperCase());
				pst.setString(2, pass);

				pst.setString(3, USER_ID);
				pst.executeUpdate();
			} else {
				return " ";
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
				level.setCREATED_BY(rs.getString("CREATED_BY"));
				level.setCREATED_DATE(rs.getString("CREATED_DATE"));
				level.setDISABLE_FLAG(rs.getString("DISABLE_FLAG"));
				level.setREGION_CODE(rs.getString("REGION_CODE"));
				level.setUSER_LEVEL(rs.getString("USER_LEVEL"));
				level.setMODULE_ACCESS(rs.getString("MODULE_ACCESS"));
				level.setROLE_CODE(rs.getString("ROLE_CODE"));

				level.setEMPLOYEE_NAME(rs.getString("EMPLOYEE_NAME"));

				level.setCC_CODE(rs.getString("CC_CODE"));
				level.setACC_CEN_CODE(rs.getString("ACC_CEN_CODE"));

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
			PreparedStatement pst = con.prepareStatement("update WEB_USER " + "set FULL_NAME=?,EMPLOYEE_CODE=?,"
					+ "LOCK_FLAG=?,SUPER_FLAG=?,DISABLE_FLAG=?," + "REGION_CODE=?,USER_LEVEL=?,"
					+ "ROLE_CODE=?,CC_CODE=?,ACC_CEN_CODE=?, module_access=? where USER_ID=?");

			pst.setString(1, m.getFULL_NAME());
			pst.setString(2, m.getEMPLOYEE_CODE());
			pst.setString(3, m.getLOCK_FLAG());
			pst.setString(4, m.getSUPER_FLAG());
			pst.setString(5, m.getDISABLE_FLAG());
			pst.setString(6, m.getREGION_CODE());

			pst.setString(7, m.getUSER_LEVEL());
			pst.setString(8, m.getROLE_CODE());
			pst.setString(9, m.getCC_CODE());
			pst.setString(10, m.getACC_CEN_CODE());
			pst.setString(11, m.getMODULE_ACCESS());
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

}
