package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DbCon;

public class VASCommonDao {
	public String getTSCAmt(String Itemcode) throws SQLException {
        Connection con = DbCon.getConnection();
        try {

            String qry = "select get_tax_rate(?,sysdate) from dual";

            PreparedStatement pst = con.prepareStatement(qry);
            pst.setString(1, Itemcode);
        

            ResultSet rs=pst.executeQuery();
            while(rs.next()) {
           return 	rs.getString(1);
            }

            return "No value found for this item";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update : " + e.getMessage();
        } finally {
            con.close();
        }
    }

}
