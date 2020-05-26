package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Region;

import util.DbCon;

public class RegionDao {
	public List<Region> getlist() throws SQLException {
		Connection con = DbCon.getConnection();
		List<Region> list = new ArrayList<Region>();
		Region m = null;
		try {
			PreparedStatement pst = con.prepareStatement("select * from M_REGION order by REGION_CODE");
			ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				m = new Region();
				m.setSN(i);
				m.setREGION_CODE(rs.getString("REGION_CODE"));
				m.setDESCRIPTION(rs.getString("DESCRIPTION"));
				m.setACTIVE_STATUS(rs.getString("ACTIVE_STATUS"));
			
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
	
	public List<Region> getlistByUserFDC(String USER,String level) throws SQLException {
		 
		Connection con = DbCon.getConnection();
		List<Region> list = new ArrayList<Region>();
		Region m = null;
		PreparedStatement pst=null;
		try {
			if(level.contains("6")) {		
				System.out.println("level = 6");
				pst = con.prepareStatement("select distinct(REGION_CODE),REGION DESCRIPTION,'Y' ACTIVE_STATUS from VW_FTTH_ALL_FDC VFAF where exists (select FDC_CODE  from WEB_USER_FDC_MAP where VFAF.FDC_CODE=WEB_USER_FDC_MAP.FDC_CODE and user_id=?)");
				pst.setString(1, USER);
			}else {
				System.out.println("size = other");
				  pst = con.prepareStatement("select * from M_REGION order by REGION_CODE");
			}
			ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				m = new Region();
				m.setSN(i);
				m.setREGION_CODE(rs.getString("REGION_CODE"));
				m.setDESCRIPTION(rs.getString("DESCRIPTION"));				
				m.setACTIVE_STATUS(rs.getString("ACTIVE_STATUS"));
				
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

	public String saveRegion(Region abc) throws SQLException {
		Connection con = DbCon.getConnection();
		PreparedStatement pst = null;
		try {

			pst = con.prepareStatement(
					"INSERT INTO m_region (region_code, description, created_by, created_date) VALUES (?, ?, ?, SYSDATE)");
			pst.setString(1, abc.getREGION_CODE().toUpperCase());
			pst.setString(2, abc.getDESCRIPTION());
			pst.setString(3, abc.getUSER());
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed "+e;
		} finally {

			con.close();

		}
		return "Successfully Saved Data";
	}

	public String UpdateRegion(Region r) throws SQLException {
		Connection con = DbCon.getConnection();
		PreparedStatement pst = null;
		try {

			pst = con.prepareStatement(
					"UPDATE m_region SET description=?, updated_by=?, updated_date=SYSDATE WHERE region_code=?");
			pst.setString(1, r.getDESCRIPTION());
			pst.setString(2, r.getUSER());
			pst.setString(3, r.getREGION_CODE());
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed "+e;
		} finally {
			con.close();
		}
		return "Successfully Updated Record";
	}

	public String deleteRegion(String code) throws SQLException {
		Connection con = DbCon.getConnection();
		PreparedStatement pst = null;
		try {

			pst = con.prepareStatement("delete FROM m_region WHERE region_code=?");
			pst.setString(1, code);

			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed "+e;
		} finally {
			con.close();

		}
		return "Successfully Delete This Data";
	}

}
