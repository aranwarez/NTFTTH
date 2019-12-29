package com.dao;

import java.sql.CallableStatement;
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

public class NettingDao {
	public List<Map<String, Object>> getImpNTList(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
			String SERVICE_CODE, String NT_SP, String POST_FLAG) throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			PreparedStatement pst = con.prepareStatement(
					"select * from imp_nt WHERE imp_year=? AND imp_period=? AND imp_month=? AND service_code=? AND NT_SP=? AND post_flag=NVL(?,post_flag) ORDER BY trans_no, seq_no");

			pst.setString(1, IMP_YEAR);
			pst.setString(2, IMP_PERIOD);
			pst.setString(3, IMP_MONTH);
			pst.setString(4, SERVICE_CODE);
			pst.setString(5, NT_SP);
			pst.setString(6, POST_FLAG);

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

	public List<Map<String, Object>> getImpSPList(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
			String SERVICE_CODE, String NT_SP, String POST_FLAG) throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			PreparedStatement pst = con.prepareStatement(
					"select * from imp_sp WHERE imp_year=? AND imp_period=? AND imp_month=? AND service_code=? AND NT_SP=? AND post_flag=NVL(?,post_flag) ORDER BY trans_no, seq_no");

			pst.setString(1, IMP_YEAR);
			pst.setString(2, IMP_PERIOD);
			pst.setString(3, IMP_MONTH);
			pst.setString(4, SERVICE_CODE);
			pst.setString(5, NT_SP);
			pst.setString(6, POST_FLAG);

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

	public String updateImpNt(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
			String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST, String USER,
			String TRANS_NO, String SEQ_NO) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String qry = "UPDATE IMP_NT\n" + "SET    IMP_YEAR     = ?, IMP_PERIOD   = ?,IMP_MONTH    = ?,\n"
					+ "       SERVICE_CODE = ?, NT_SP        = ?,\n"
					+ "       CP_DESC     = ?, S_NO      = ?, ESME_CODE    = ?, MO_1ST       = ?,\n"
					+ "       MT_1ST       = ?,  \n" + "       UPDATE_DT    = sysdate, UPDATE_BY    = ?       \n"
					+ "WHERE  TRANS_NO     = ? AND    SEQ_NO       = ?";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, IMP_YEAR);
			pst.setString(2, IMP_PERIOD);
			pst.setString(3, IMP_MONTH);
			pst.setString(4, SERVICE_CODE);
			pst.setString(5, NT_SP);
			// pst.setString(6, CATEGORY);
			pst.setString(6, CP_DESC);
			pst.setString(7, S_NO);
			pst.setString(8, ESME_CODE);
			pst.setString(9, MO_1ST);
			pst.setString(10, MT_1ST);
			pst.setString(11, USER);
			pst.setString(12, TRANS_NO);
			pst.setString(13, SEQ_NO);
			pst.executeUpdate();
			return "Succesfully Updated";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to update : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String updateImpSP(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
			String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST, String USER,
			String TRANS_NO, String SEQ_NO) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String qry = "UPDATE IMP_SP\n" + "SET    IMP_YEAR     = ?, IMP_PERIOD   = ?,IMP_MONTH    = ?,\n"
					+ "       SERVICE_CODE = ?, NT_SP        = ?,\n"
					+ "       CP_DESC     = ?, S_NO      = ?, ESME_CODE    = ?, MO_1ST       = ?,\n"
					+ "       MT_1ST       = ?,  \n" + "       UPDATE_DT    = sysdate, UPDATE_BY    = ?       \n"
					+ "WHERE  TRANS_NO     = ? AND    SEQ_NO       = ?";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, IMP_YEAR);
			pst.setString(2, IMP_PERIOD);
			pst.setString(3, IMP_MONTH);
			pst.setString(4, SERVICE_CODE);
			pst.setString(5, NT_SP);
			// pst.setString(6, CATEGORY);
			pst.setString(6, CP_DESC);
			pst.setString(7, S_NO);
			pst.setString(8, ESME_CODE);
			pst.setString(9, MO_1ST);
			pst.setString(10, MT_1ST);
			pst.setString(11, USER);
			pst.setString(12, TRANS_NO);
			pst.setString(13, SEQ_NO);
			pst.executeUpdate();
			return "Succesfully Updated";
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed to update : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String DeleteImpNt(String TRANS_NO, String SEQ_NO) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("delete from IMP_NT where trans_no=? AND seq_no=?");
			pst.setString(1, TRANS_NO);
			pst.setString(2, SEQ_NO);

			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed Reason :" + e.getLocalizedMessage();
		} finally {
			con.close();
		}
		return "Record deleted successfully.";
	}

	public String saveImpNT(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
			String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST, String USER)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			String qry = "INSERT INTO IMP_NT (TRANS_ID,TRANS_NO, SEQ_NO, IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, \n"
					+ " NT_SP, S_NO, CP_DESC, ESME_CODE, MO_1ST, MT_1ST,\n" + "   CREATE_BY, POST_FLAG,  CREATE_DT) \n"
					+ "VALUES (SEQ_IMP_NT_ID.nextval,TMP_IMP_TRANS_NO.nextval, 1, ?, ?, ?,?,\n"
					+ "    ?, ?, ?, ?, ?, ?,\n" + "    ?, 'N', SYSDATE )";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, IMP_YEAR);
			pst.setString(2, IMP_PERIOD);
			pst.setString(3, IMP_MONTH);
			pst.setString(4, SERVICE_CODE);
			pst.setString(5, NT_SP);
			pst.setString(6, S_NO);
			pst.setString(7, CP_DESC);
			pst.setString(8, ESME_CODE);
			pst.setString(9, MO_1ST);
			pst.setString(10, MT_1ST);
//            pst.setString(11, CATEGORY);
			pst.setString(11, USER);
			pst.executeUpdate();
			return "Succesfully Saved NT netting";
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String saveImpSP(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
			String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST, String USER)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			String qry = "INSERT INTO IMP_SP (TRANS_ID,TRANS_NO, SEQ_NO, IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, \n"
					+ " NT_SP, S_NO, CP_DESC, ESME_CODE, MO_1ST, MT_1ST,\n" + "   CREATE_BY, POST_FLAG,  CREATE_DT) \n"
					+ "VALUES (SEQ_IMP_SP_ID.nextval,TMP_IMP_TRANS_NO.nextval, 1, ?, ?, ?,?,\n"
					+ "    ?, ?, ?, ?, ?, ?,\n" + "    ?, 'N', SYSDATE )";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, IMP_YEAR);
			pst.setString(2, IMP_PERIOD);
			pst.setString(3, IMP_MONTH);
			pst.setString(4, SERVICE_CODE);
			pst.setString(5, NT_SP);
			pst.setString(6, S_NO);
			pst.setString(7, CP_DESC);
			pst.setString(8, ESME_CODE);
			pst.setString(9, MO_1ST);
			pst.setString(10, MT_1ST);
//            pst.setString(11, CATEGORY);
			pst.setString(11, USER);
			pst.executeUpdate();
			return "Succesfully Saved SP netting";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public String postExcelImportData(String inyear, String ivperiod, String ivmonth, String ivservice, String ivntsp,
			String ivuser) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String getDBUSERByUserIdSql = "{call VAS_NETTING(?,?,?,?,?)}";
			CallableStatement pst = con.prepareCall(getDBUSERByUserIdSql);
			pst.setString(1, inyear);
			pst.setString(2, ivperiod);
			pst.setString(3, ivmonth);
			pst.setString(4, ivservice);
//			pst.setString(5, ivntsp);
			pst.setString(5, ivuser);
			// pst.registerOutParameter(10, java.sql.Types.VARCHAR);
			pst.executeUpdate();
			// String result = pst.getString(10);
			return "Sucessfully Posted Excel imported Data";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to save " + e.getMessage();
		} finally {
			con.close();
		}
	}
	
	
	public List<Map<String, Object>> getNonSharing(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
			String SERVICE_CODE, String SHARING_TYPE, String POST_FLAG) throws SQLException {
		Connection con = DbCon.getConnection();
		try {

			PreparedStatement pst = con.prepareStatement(
					"select sn.*,common.to_bs(sn.START_DT) START_DT from sms_netting sn WHERE imp_year=? AND imp_period=? AND imp_month=NVL(?,imp_month) AND service_code=? AND SHARING_TYPE=? AND post_flag=NVL(?,post_flag) ORDER BY trans_id");

			pst.setString(1, IMP_YEAR);
			pst.setString(2, IMP_PERIOD);
			pst.setString(3, IMP_MONTH);
			pst.setString(4, SERVICE_CODE);
			pst.setString(5, SHARING_TYPE);
			pst.setString(6, POST_FLAG);

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
        
        public String postNettingtoBill(String inyear, String ivperiod, String ivmonth, String ivservice, 
			String ivuser) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String getDBUSERByUserIdSql = "{call VAS_BILL_POSTING(?,?,?,?,?)}";
			CallableStatement pst = con.prepareCall(getDBUSERByUserIdSql);
			pst.setString(1, inyear);
			pst.setString(2, ivperiod);
			pst.setString(3, ivmonth);
			pst.setString(4, ivservice);
			pst.setString(5, ivuser);
			// pst.registerOutParameter(10, java.sql.Types.VARCHAR);
			pst.executeUpdate();
			// String result = pst.getString(10);
                        
                        //System.out.println(inyear+ivperiod+ivmonth+ivservice+ivuser);
			return "Sucessfully Posted to Bill";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to save " + e.getMessage();
		} finally {
			con.close();
		}
	}

}
