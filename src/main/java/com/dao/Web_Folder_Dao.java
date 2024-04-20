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

public class Web_Folder_Dao {
	public List<Map<String, Object>> getAllFolder() throws SQLException {
        Connection con = DbCon.getConnection();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM web_folder WHERE ACTIVE_FLAG='Y'");

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

    public List<Map<String, Object>> getAllFiles(String Pfolder) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM WEB_FILES WHERE TOPIC=?");
            pst.setString(1, Pfolder);
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

    public List<Map<String, Object>> getAllRecentFiles(String Tag) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
//            String cqry="SELECT * FROM WEB_FILES WHERE upper(TAG)='ALL' or upper(TAG) like "+"'%"+Tag+"%'" +" order by CREATED_DATE";
            String cqry="SELECT * FROM WEB_FILES order by CREATED_DATE";

PreparedStatement pst = con.prepareStatement(cqry);
          // System.out.println(cqry);
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

    public String saveFolder(String Path, String username, String topic) {
        try {
            String parentcode = "#";
            if (!topic.isEmpty()) {
                parentcode = topic;
                //   System.out.println("abc"+parentcode);
            }

            Connection con = DbCon.getConnection();
            String qry = "INSERT INTO WEB_FOLDER (FOLDER_CODE, PARENT_CODE, ACTIVE_FLAG, CREATE_BY, CREATE_DT,DESCRIPTION)\n"
                    + "VALUES(FOLDER_CODE_SEQ.nextval, ?, 'Y', ?, SYSDATE,?)";
            PreparedStatement ps = con.prepareCall(qry);
            ps.setString(3, Path);
            ps.setString(1, parentcode);
            ps.setString(2, username);
            ps.executeUpdate();
            con.close();
            return "File uploaded Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erorr because of " + e.getMessage();

        }

        //  return "Failed to Create Directory";
    }

    public String editFolder(String Path, String username, String parent, String topic) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            //  String parentcode = "#";

            con.setAutoCommit(false);
            String childqry = "select * from WEB_FOLDER where PARENT_CODE=?";
            PreparedStatement cps = con.prepareCall(childqry);
            cps.setString(1, topic);
            ResultSet rs = cps.executeQuery();
            while (rs.next()) {
                String updtchild = "update WEB_FOLDER set PARENT_CODE=? where parent_code=?";

                PreparedStatement ucps = con.prepareCall(updtchild);
                ucps.setString(1, Path);
                ucps.setString(2, rs.getString("PARENT_CODE"));
                ucps.executeUpdate();
            }

            String qry = "update WEB_FOLDER set DESCRIPTION=?,UPDATE_DT=SYSDATE,UPDATE_BY=? where PARENT_CODE=? and FOLDER_CODE=?";
            PreparedStatement ps = con.prepareCall(qry);
            ps.setString(1, Path);
            ps.setString(3, parent);
            ps.setString(2, username);
            ps.setString(4, topic);

            ps.executeUpdate();
            con.close();
            return "Directory Changed Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }

        return "Failed to Create Directory";
    }

    public String delFolder(String parent, String topic) throws SQLException {
        Connection con = DbCon.getConnection();
        try {
            //  String parentcode = "#";

            con.setAutoCommit(false);
            String childqry = "select * from WEB_FILES where TOPIC=?";
            PreparedStatement cps = con.prepareCall(childqry);
//            cps.setString(1, parent);
            cps.setString(1, topic);
            ResultSet rs = cps.executeQuery();
            while (rs.next()) {
                throw new Exception("This folder is not empty");
            }

            String childqry2 = "select * from WEB_FOLDER where PARENT_CODE=?";
            PreparedStatement cps2 = con.prepareCall(childqry2);
//            cps.setString(1, parent);
            cps2.setString(1, topic);
            ResultSet rs2 = cps2.executeQuery();
            while (rs2.next()) {
                throw new Exception("This folder contains folder.!!!");
            }

            String qry = "delete from WEB_FOLDER where PARENT_CODE=? and FOLDER_CODE=?";
            PreparedStatement ps = con.prepareCall(qry);

            ps.setString(1, parent);

            ps.setString(2, topic);

            ps.executeUpdate();
            con.close();
            return "Directory Deleted Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        //    return "Failed to Delete Directory";
    }
    
    public String saveFile(String File_path,String File_des, String username, int seq, String topic, String tag) {
        try {
            Connection con = DbCon.getConnection();
            String qry = "insert into web_files(file_id,file_path,file_des,created_by,topic,created_date,tag) values(?,?,?,?,?,sysdate,?)";
            PreparedStatement ps = con.prepareCall(qry);
            ps.setInt(1, seq);
            ps.setString(2, File_path);
            ps.setString(3, File_des);
            ps.setString(4, username);
            ps.setString(5, topic);
            ps.setString(6, tag);
            ps.executeUpdate();
            con.close();
            return "File uploaded Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Failed to upload file";
    }

    
    public Integer getFileseq() {
        try {
            Connection con = DbCon.getConnection();
            String qry = "select FILE_ID_SEQ.nextval from dual";
            PreparedStatement ps = con.prepareCall(qry);
            ResultSet result=ps.executeQuery();
            Integer seq=-1;
            while(result.next()) {
            	seq=result.getInt(1);
            }
            ps.executeUpdate();
            con.close();
            return seq;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


	
	}
