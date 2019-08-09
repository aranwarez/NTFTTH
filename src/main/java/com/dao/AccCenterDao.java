package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.AccCenter;

import util.DbCon;

public class AccCenterDao {
	
	
	public List<AccCenter> getAccCenterList(String code) throws SQLException {
		Connection con = DbCon.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<AccCenter> list = new ArrayList<AccCenter>();
		AccCenter abc = null;

		try {

			pst = con.prepareStatement("select * from m_acc_center where REGION_CODE=?");
			pst.setString(1, code);
			rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				abc = new AccCenter();
				abc.setSN(i);
				abc.setACC_CEN_CODE(rs.getString("ACC_CEN_CODE"));
				abc.setDESCRIPTION(rs.getString("DESCRIPTION"));
				abc.setREGION_CODE(rs.getString("REGION_CODE"));
				abc.setERP_ACC_CEN_CD(rs.getString("ERP_ACC_CEN_CD"));
				list.add(abc);
				i = i + 1;

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return list;
	}

	public String addAccenter(AccCenter abc) throws SQLException {
		Connection con = DbCon.getConnection();
		PreparedStatement pst = null;
		try {

			pst = con.prepareStatement(
					"INSERT INTO m_acc_center (acc_cen_code, description, region_code,ERP_ACC_CEN_CD, created_by, created_date) VALUES (?, ?, ?,?,?, SYSDATE)");
			pst.setString(1, abc.getACC_CEN_CODE().toUpperCase());
			pst.setString(2, abc.getDESCRIPTION());
			pst.setString(3, abc.getREGION_CODE());
			pst.setString(4, abc.getERP_ACC_CEN_CD());
			pst.setString(5, abc.getUSER());
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed "+e;
		} finally {
			con.close();
		}
		return "Saved Successfully";
	}
//update AccCenter

	public String updateAcc(AccCenter abc) throws SQLException {
		Connection con = DbCon.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"UPDATE M_ACC_CENTER SET DESCRIPTION=? ,ERP_ACC_CEN_CD=?,UPDATED_BY=?, UPDATED_DATE=SYSDATE,REGION_CODE=? WHERE ACC_CEN_CODE=?");
			pst.setString(1, abc.getDESCRIPTION());
			pst.setString(2, abc.getERP_ACC_CEN_CD());
			pst.setString(3, abc.getUSER());
			pst.setString(4, abc.getREGION_CODE());
			pst.setString(5, abc.getACC_CEN_CODE().toUpperCase());

			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed "+e;
		} finally {
			con.close();
		}
		return "Successfully Updated";
	}

	// delete AccCenter
	public String deleteAccenter(String code) throws SQLException {
		Connection con = DbCon.getConnection();
		PreparedStatement pst = null;
		try {

			pst = con.prepareStatement("delete from m_acc_center where acc_cen_code=?");
			pst.setString(1, code);
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed "+e;
		} finally {
			con.close();
		}
		return "Delete Successfully";
	}
}
