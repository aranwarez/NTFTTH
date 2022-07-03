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

public class OLTDao {
	public static List<Map<String, Object>> getOLTListByOffice(String OFFICE_CODE) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("select * from M_OLT where OFFICE_CODE=? order by OLT_CODE");
//            DISTRICT_CODE
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

	public static List<Map<String, Object>> getOLTList() throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("select * from M_OLT order by OLT_CODE");
//            DISTRICT_CODE
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

	public String saveoltcontact(String OLTCODE, String ContactNo, String ContactName, String User)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO OLT_CONTACT (PK_ID,OLT_CODE,\r\n"
					+ "                                  CONTACT_NO,\r\n"
					+ "                                  CONTACT_NAME,\r\n"
					+ "                                  CREATE_BY)\r\n" + "     VALUES (OLT_CONTACT_SEQ.nextval,?,\r\n"
					+ "             ?,\r\n" + "             ?,\r\n" + "             ?)");

			pst.setString(1, OLTCODE);
			pst.setString(2, ContactNo);
			pst.setString(3, ContactName);
			pst.setString(4, User);

			pst.executeUpdate();

			return "Successfully added OLT Contact.";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		} finally {
			con.close();
		}
		// return null;
	}

	public List<Map<String, Object>> getOLTContact(String Region, String zone, String district, String OFFICE_CODE)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT *\r\n"
					+ "  FROM VW_FTTH_ALL_OLT VFAO, OLT_CONTACT oc\r\n" + " WHERE     VFAO.OLT_CODE = oc.OLT_CODE\r\n"
					+ "       AND region_code = NVL (?, region_code)\r\n"
					+ "       AND zone_code = NVL (?, zone_code)\r\n"
					+ "       AND district_code = NVL (?, district_code)\r\n"
					+ "       AND office_code = NVL (?, office_code)");
			System.out.println(Region);
			pst.setString(1, Region);
			pst.setString(2, zone);
			pst.setString(3, district);
			pst.setString(4, OFFICE_CODE);

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

	public String updateoltcontact(String OLTCODE, String ContactNo, String ContactName, String User)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement(
					"UPDATE OLT_CONTACT\r\n" + "   SET CONTACT_NO = ?,\r\n" + "       CONTACT_NAME = ?,\r\n"
							+ "       UPDATE_BY = ?,\r\n" + "       UPDATE_DT = SYSDATE\r\n" + " WHERE PK_ID = ?");
			pst.setString(4, OLTCODE);
			pst.setString(1, ContactNo);
			pst.setString(2, ContactName);
			pst.setString(3, User);

			pst.executeUpdate();

			return "Successfully updated OLT Contact.";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		} finally {
			con.close();
		}
		// return null;
	}

	public String deleteoltcontact(String OLTCODE) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("delete from OLT_CONTACT where pk_id=?");
			pst.setString(1, OLTCODE);

			pst.executeUpdate();

			return "Successfully deleted OLT Contact.";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		} finally {
			con.close();
		}
		// return null;
	}
	
	
	public static List<Map<String, Object>> getOLTListlowlvl(String user) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT distinct(OLT) DESCRIPTION\r\n"
					+ "  FROM vw_ftth_all_fdc VFAF\r\n"
					+ " WHERE EXISTS\r\n"
					+ "           (SELECT fdc_code\r\n"
					+ "              FROM WEB_USER_FDC_MAP\r\n"
					+ "             WHERE     user_id = ?\r\n"
					+ "                   AND VFAF.FDC_CODE = WEB_USER_FDC_MAP.FDC_CODE)\r\n"
					+ "");
//            DISTRICT_CODE
			pst.setString(1, user);
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
