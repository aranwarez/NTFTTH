package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.UserInformationModel;

import util.DbCon;

public class UserDao {
	//check user name and password
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
}
