package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.EmployeeModel;

import util.DbCon;

public class EmployeeDao {
	
	
	
	public static List<Map<String, Object>> getEmpList() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
        	
            PreparedStatement pst = con.prepareStatement("select * from M_EMPLOYEE ORDER BY EMPLOYEE_CODE");
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
	
	public String getEmp_level(String emmp_code) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select * from M_EMPLOYEE where EMPLOYEE_CODE=?");
            pst.setString(1, emmp_code);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("EMP_LEVEL");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return "";
    }
	
	
	public String saveEmployeeObj(EmployeeModel abc) throws Exception {
        Connection con = DbCon.getConnection();
        System.out.println(abc.getDOB());

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
       // java.util.Date myDate = format.parse(abc.getDOB());
        //java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        try {
            PreparedStatement pst = con.prepareStatement("insert into m_employee(EMPLOYEE_CODE,EMPLOYEE_NAME,ADDRESS,SEX,MARITAL_STATUS,\n"
                    + "QUALIFICATION,CREATE_BY,CREATE_DT,DISABLE_FLAG,TEL_NO,MOBILE_NO,EMAIL,EMP_NO,TTC_NO,POST_CD,DEPT_CD,\n"
                    + "LOCATION_CD,EMP_LEVEL,EMP_TYPE,EMP_TITLE)\n"
                    + "values(?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, abc.getEMPLOYEE_CODE());
            pst.setString(2, abc.getEMPLOYEE_NAME());
            pst.setString(3, abc.getADDRESS());
            pst.setString(4, abc.getSEX());
            pst.setString(5, abc.getMARITAL_STATUS());
            //pst.setDate(6, sqlDate);
            //pst.setString(7, abc.getDOJ());
            pst.setString(6, abc.getQUALIFICATION());
            pst.setString(7, abc.getUSER());
            pst.setString(8, abc.getDISABLE_FLAG());
            pst.setString(9, abc.getTEL_NO());
            pst.setString(10, abc.getMOBILE_NO());
            pst.setString(11, abc.getEMAIL());
            pst.setString(12, abc.getEMP_NO());
            pst.setString(13, abc.getTTC_NO());
            pst.setString(14, abc.getPOST_CD());
            pst.setString(15, abc.getDEPT_CD());
            pst.setString(16, abc.getLOCATION_CD());
            pst.setString(17, abc.getEMP_LEVEL());
            pst.setString(18, abc.getEMP_TYPE());
            pst.setString(19, abc.getEMP_TITLE());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            con.close();
        }
        return "Successfully saved!";
    }
	
	 
	 public List<Map<String, Object>> getEmpFilterlist(String REGION, String zone, String district,String office) throws SQLException {
		 Connection con = DbCon.getConnection();

	        try {
	            PreparedStatement pst = con.prepareStatement("select  a.region_code, a.description region, b.zone_code,\r\n" + 
	            		"          b.description ZONE, c.district_code, c.description district,\r\n" + 
	            		"          TO_CHAR(d.office_code) office_code, d.description office,e.*\r\n" + 
	            		"   from m_region a, m_zone b, m_district c, m_office d,M_EMPLOYEE e\r\n" + 
	            		"WHERE a.region_code = b.region_code\r\n" + 
	            		"      AND b.zone_code = c.zone_code\r\n" + 
	            		"      AND c.district_code = d.district_code\r\n" + 
	            		"      AND TO_CHAR(d.office_code) = TO_CHAR(e.LOCATION_CD)\r\n" + 
	            		"      AND a.region_code =nvl(?,a.region_code) \r\n" + 
	            		"      AND b.zone_code =nvl(?,b.zone_code)\r\n" + 
	            		"      AND c.district_code =nvl(?,c.district_code)\r\n" + 
	            		"      AND TO_CHAR(e.LOCATION_CD) =nvl(?,TO_CHAR(e.LOCATION_CD))\r\n" + 
	            		"      ORDER BY e.employee_code");
	            pst.setString(1, REGION);
	            pst.setString(2, zone);
	            pst.setString(3, district);
	            pst.setString(4, office);
	            
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
	 
	 public String ChangeOffice(String USER, String LOCATION_CD, String employee_code) throws SQLException {
	        Connection con = DbCon.getConnection();
	        try {

	            PreparedStatement pst = con.prepareStatement("update M_EMPLOYEE set LOCATION_CD=? where EMPLOYEE_CODE=?");
	            pst.setString(1, LOCATION_CD);
	            pst.setString(2, employee_code);
	            pst.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Failed "+e.getMessage();

	        } finally {
	            con.close();

	        }
	        return "successfully changed";
	    }
	 
	    public String updateEmployee(String USER, String EMPLOYEE_NAME,String ADDRESS, String TEL_NO, String MOBILE_NO, String EMAIL, String DEPT_CD, String LOCATION_CD, String employee_code) throws SQLException {
	  
	        Connection con = DbCon.getConnection();
	        try {
	            PreparedStatement pst = con.prepareStatement("update m_employee set EMPLOYEE_NAME=?,ADDRESS=?,TEL_NO=?,MOBILE_NO=?,EMAIL=?,DEPT_CD=?,LOCATION_CD=?,UPDATE_DT=sysdate where EMPLOYEE_CODE=?");
	            pst.setString(1, EMPLOYEE_NAME);
	            pst.setString(2, ADDRESS);
	            pst.setString(3, TEL_NO);
	            pst.setString(4, MOBILE_NO);
	            pst.setString(5, EMAIL);
	            pst.setString(6, DEPT_CD);
	            pst.setString(7, LOCATION_CD);
	            pst.setString(8, employee_code);
	            
	            pst.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Failed:- "+e.getMessage();
	        } finally {
	            con.close();

	        }
	        return "Data has been saved successfully ";
	    }
	 
	 public String deleteEmployee(String code) throws SQLException {
	        Connection con = DbCon.getConnection();
	   //     System.out.println("nabin");
	        
	        
	        try {
	            PreparedStatement pst = con.prepareStatement("DELETE FROM M_employee WHERE employee_code=?");

	            pst.setString(1, code);
	            pst.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Failed "+e.getMessage();
	        } finally {
	            con.close();
	        }
	        return "Successfully deleted!";
	    }
	 
	 public static List<Map<String, Object>> getEmplistparamLocationCD(String LOCATION_CD) throws SQLException {
	        Connection con = DbCon.getConnection();

	        try {
	        	
	            PreparedStatement pst = con.prepareStatement("select * from M_EMPLOYEE where LOCATION_CD=? order by EMPLOYEE_NAME");
	            pst.setString(1, LOCATION_CD);
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
	 public static List<Map<String, Object>> getEmpdetailcode(String empcode) throws SQLException {
	        Connection con = DbCon.getConnection();

	        try {
	        	
	            PreparedStatement pst = con.prepareStatement("select * from M_EMPLOYEE where EMPLOYEE_CODE=? order by EMPLOYEE_NAME");
	            pst.setString(1, empcode);
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
