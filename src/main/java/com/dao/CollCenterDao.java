package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.CollCenter;

import util.DbCon;

public class CollCenterDao {

    public List<CollCenter> getCC(String ACC_CEN_CODE) throws SQLException {
        Connection con = DbCon.getConnection();
        List<CollCenter> list = new ArrayList<CollCenter>();
        CollCenter m = null;
        try {
            PreparedStatement pst = con.prepareStatement("select * from M_COLL_CENTER where ACC_CEN_CODE=? order by CC_CODE");
            pst.setString(1, ACC_CEN_CODE);
            ResultSet rs = pst.executeQuery();
            int i = 1;
            while (rs.next()) {
                m = new CollCenter();
                m.setSN(i);
                m.setCC_CODE(rs.getString("CC_CODE"));
                m.setDESCRIPTION(rs.getString("DESCRIPTION"));
                m.setERP_CC_CD(rs.getString("ERP_CC_CD"));
                m.setACC_CEN_CODE(rs.getString("ACC_CEN_CODE"));
                m.setCC_TYPE(rs.getString("CC_TYPE"));
                m.setOFFICE_CODE(rs.getString("OFFICE_CODE"));

                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }
    //module wise collection center get list

    public List<CollCenter> getCCModule(String ACC_CEN_CODE, String module_access) throws SQLException {
        Connection con = DbCon.getConnection();
        List<CollCenter> list = new ArrayList<CollCenter>();
        CollCenter m = null;
        try {
            PreparedStatement pst = null;
            if (module_access.substring(0, 1).equals("C")) {
                pst = con.prepareStatement("select * from M_COLL_CENTER where ACC_CEN_CODE=? order by CC_CODE");
                pst.setString(1, ACC_CEN_CODE);
            } else if (module_access.substring(0, 1).equals("P")) {
                //pst = con.prepareStatement("select PBIM_CC_CODE CC_CODE, DESCRIPTION, ERP_CC_CD, pbim_ACC_CEN_CODE ACC_CEN_CODE,CC_TYPE, OFFICE_CODE from M_PBIM_COLL_CENTER where PBIM_ACC_CEN_CODE=? order by PBIM_CC_CODE");
                pst = con.prepareStatement("select CC_CODE, DESCRIPTION, ERP_CC_CD, ACC_CEN_CODE,CC_TYPE, OFFICE_CODE from M_COLL_CENTER where ACC_CEN_CODE=? UNION ALL select PBIM_CC_CODE CC_CODE, DESCRIPTION, ERP_CC_CD, pbim_ACC_CEN_CODE ACC_CEN_CODE,CC_TYPE, OFFICE_CODE from M_PBIM_COLL_CENTER where PBIM_ACC_CEN_CODE=? order by 1");
                pst.setString(1, ACC_CEN_CODE);
                pst.setString(2, ACC_CEN_CODE);
            } else if (module_access.substring(0, 1).equals("B")) {
                pst = con.prepareStatement("select CC_CODE, DESCRIPTION, ERP_CC_CD, ACC_CEN_CODE,CC_TYPE, OFFICE_CODE from M_COLL_CENTER where ACC_CEN_CODE=? UNION ALL select PBIM_CC_CODE CC_CODE, DESCRIPTION, ERP_CC_CD, pbim_ACC_CEN_CODE ACC_CEN_CODE,CC_TYPE, OFFICE_CODE from M_PBIM_COLL_CENTER where PBIM_ACC_CEN_CODE=? order by 1");
                pst.setString(1, ACC_CEN_CODE);
                pst.setString(2, ACC_CEN_CODE);
            }

            ResultSet rs = pst.executeQuery();
            int i = 1;
            while (rs.next()) {
                m = new CollCenter();
                m.setSN(i);
                m.setCC_CODE(rs.getString("CC_CODE"));
                m.setDESCRIPTION(rs.getString("DESCRIPTION"));
                m.setERP_CC_CD(rs.getString("ERP_CC_CD"));
                m.setACC_CEN_CODE(rs.getString("ACC_CEN_CODE"));
                m.setCC_TYPE(rs.getString("CC_TYPE"));
                m.setOFFICE_CODE(rs.getString("OFFICE_CODE"));

                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        return list;
    }

    public String saveCC(CollCenter abc) throws SQLException {
        Connection con = DbCon.getConnection();
        PreparedStatement pst = null;
        try {

            pst = con.prepareStatement("INSERT INTO M_COLL_CENTER (CC_CODE,DESCRIPTION,ERP_CC_CD,ACC_CEN_CODE,CC_TYPE,created_by, created_date, OFFICE_CODE) VALUES (?, ?, ?,?,?,?, SYSDATE,?)");
            pst.setString(1, abc.getCC_CODE().toUpperCase());
            pst.setString(2, abc.getDESCRIPTION());
            pst.setString(3, abc.getERP_CC_CD());
            pst.setString(4, abc.getACC_CEN_CODE());
            pst.setString(5, abc.getCC_TYPE());
            pst.setString(6, abc.getUSER());
            pst.setString(7, abc.getOFFICE_CODE());

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed "+e;
        } finally {

            con.close();

        }
        return "Successfully Saved Data";
    }

    public String UpdateCC(CollCenter r) throws SQLException {
        Connection con = DbCon.getConnection();
        PreparedStatement pst = null;
        try {

            pst = con.prepareStatement("UPDATE M_COLL_CENTER SET DESCRIPTION=?,ERP_CC_CD=?,ACC_CEN_CODE=?,CC_TYPE=?, updated_by=?, updated_date=SYSDATE, office_code=? WHERE CC_CODE=?");
            pst.setString(1, r.getDESCRIPTION());
            pst.setString(2, r.getERP_CC_CD());
            pst.setString(3, r.getACC_CEN_CODE());

            pst.setString(4, r.getCC_TYPE());
            pst.setString(5, r.getUSER());
            pst.setString(6, r.getOFFICE_CODE());
            pst.setString(7, r.getCC_CODE());
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed "+e;
        } finally {
            con.close();
        }
        return "Successfully Updated Record";
    }

    public String deleteCC(String code) throws SQLException {
        Connection con = DbCon.getConnection();
        PreparedStatement pst = null;
        try {

            pst = con.prepareStatement("delete FROM M_COLL_CENTER WHERE CC_CODE=?");
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
