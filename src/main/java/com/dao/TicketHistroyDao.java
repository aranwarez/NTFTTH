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

public class TicketHistroyDao {

	
	public List<Map<String, Object>> getComplainBySrvNo(String User, String MAIN_SRV_NO) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String qry = "select * from vw_token_all where MAIN_SRV_NO=?";

			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, MAIN_SRV_NO.trim());
			
			
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
