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

public class ComplainDao {

	private String tOKEN_ID;
	private String sub_token_id;

	public String solveProblem(List<Map<String, Object>> servicestypelist, String serviceID, String SRV_NO,
			String Complain_no, String contactName, String Remarks, String USER, String FDCName, String teamname,
			String Supervisorname, String SupervisorContno, String Teamleader, String TeamleaderNo,
			String CUSTOMER_NAME, String CONTACT_NO, String OLT_PORT, String FAP_LOCATION, String FAP_PORT,
			String CPE_RX_LVL,String ODF_PORT) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		try {

			String cqry = "select * from MAIN_TOKEN_MASTER where SRV_NO=? and SOLVE_FLAG <>'C'";

			PreparedStatement qpst = con.prepareStatement(cqry);
			qpst.setString(1, SRV_NO);
			ResultSet rs = qpst.executeQuery();
			if (rs.next() == true) {
				return ("Resolving Service Complain while Complain Already Exist Token_ID:" + rs.getString("TOKEN_ID"));
				// throw new SQLException("Complain Already Exist");
			}
			WebTeamDao teammdao = new WebTeamDao();
			String team_id = teammdao.saveWebteam(con, teamname, FDCName, Supervisorname, SupervisorContno, Teamleader,
					TeamleaderNo);
			System.err.println("web team" + team_id);
			cqry = "select MTM_Token_ID.NEXTVAL from dual";
			ResultSet tokenrs = con.prepareStatement(cqry).executeQuery();
			while (tokenrs.next()) {
				tOKEN_ID = tokenrs.getString(1);
			}

			System.err.println("exception");

			String qry = "INSERT INTO MAIN_TOKEN_MASTER (TOKEN_ID,\n"
					+ "                                    SERVICE_ID,\n"
					+ "                                    SRV_NO, SUB_TEAM_CODE, \n"
					+ "                                    SOLVE_FLAG,\n"
					+ "                                    COMPLAIN_NO,\n"
					+ "                                    CONTACT_NAME,\n" +

					"                                    REMARKS,\n"
					+ "                                    CREATE_BY, \n"
					+ "                                    TEAM_ID, \n"

					+ "                                    FDC_CODE) \n" +

					"     VALUES (?,\n" + "             ?,\n" + "             ?,\n" + "             ?,\n"
					+ "             ?,\n" + "             ?,\n" + "             ?,\n" + "             ?,\n"
					+ "            ?, ?,\n" +

					"             (select fdc_code from M_FDC where DESCRIPTION=?)\r\n" + ")";

			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, tOKEN_ID);
			pst.setString(2, serviceID);
			pst.setString(3, SRV_NO);
			pst.setString(4, "FLMTA");
			pst.setString(5, "C");
			pst.setString(6, Complain_no);
			pst.setString(7, contactName);
			pst.setString(8, Remarks);
			pst.setString(9, USER);
			pst.setString(10, team_id);

			pst.setString(11, FDCName);
			pst.executeUpdate();

			// adding customer infomation
			qry = "INSERT INTO CUSTOMER_INFO (\r\n" + "   TOKEN_ID, CUSTOMER_NAME, CONTACT_NO, \r\n"
					+ "   OLT_PORT, FAP_LOCATION, FAP_PORT, \r\n" + "   CPE_RX_LVL,ODF_PORT) \r\n" + "VALUES ( ?,\r\n"
					+ " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,? )";
			pst = con.prepareStatement(qry);
			pst.setString(1, tOKEN_ID);
			pst.setString(2, CUSTOMER_NAME);
			pst.setString(3, CONTACT_NO);
			pst.setString(4, OLT_PORT);
			pst.setString(5, FAP_LOCATION);
			pst.setString(6, FAP_PORT);
			pst.setString(7, CPE_RX_LVL);
			pst.setString(8, ODF_PORT);
			pst.executeUpdate();

			// for multiple services - token master
			cqry = "select TM_SUB_TOKEN_ID.NEXTVAL from dual";

			String subqry = "INSERT INTO TOKEN_MASTER (SUB_TOKEN_ID,\r\n"
					+ "                               TOKEN_ID,\r\n"
					+ "                               SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               SERVICE_NO,\r\n"

					+ "                               SERVICE_TYPE_ID,\r\n"
					+ "                               CREATE_BY,\r\n" + "                               CREATE_DT)\r\n"
					+ "     VALUES (?,\r\n" + "             ?,\r\n" + "             (SELECT sub_team_code\r\n"
					+ "                FROM m_problem\r\n" + "               WHERE problem_id = ?),\r\n"
					+ "             'C',\r\n" + "             ?,\r\n" + "         ?,    ?,\r\n" + "             ?,\r\n"
					+ "             ?,\r\n" + "             SYSDATE)";

			if (servicestypelist.size() > 0) {
				for (Map<String, Object> obj : servicestypelist) {
					tokenrs = con.prepareStatement(cqry).executeQuery();
					while (tokenrs.next()) {
						sub_token_id = tokenrs.getString(1);
					}
					pst = con.prepareStatement(subqry);
					pst.setString(1, sub_token_id);
					pst.setString(2, tOKEN_ID);
					pst.setString(3, (String) obj.get("PROBLEM_ID"));
					pst.setString(4, (String) obj.get("PROBLEM_ID"));
					pst.setString(5, (String) obj.get("REMARKS"));
					pst.setString(6, (String) obj.get("SERVICE_NO"));

					pst.setString(7, (String) obj.get("SERVICE_ID"));

					pst.setString(8, USER);
					pst.executeUpdate();

					// for multiple services - token detail
					String seqqry = "select TM_SUB_TOKEN_DETAIL_ID.NEXTVAL from dual";
					tokenrs = con.prepareStatement(seqqry).executeQuery();
					while (tokenrs.next()) {
						seqqry = tokenrs.getString(1);
					}
					String detailqry = "INSERT INTO TOKEN_DETAIL (\r\n"
							+ "   TD_ID, SUB_TOKEN_ID, FROM_SUB_TEAM_CODE, \r\n"
							+ "   TO_SUB_TEAM_CODE, SOLVE_FLAG, PROBLEM_ID, \r\n"
							+ "   REMARKS, CREATE_BY, CREATE_DT \r\n" + "   ) \r\n"
							+ "VALUES ( TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + " ?,\r\n"
							+ " (select sub_team_code from m_problem where problem_id=?),\r\n"
							+ " (select sub_team_code from m_problem where problem_id=?),\r\n" + " 'C',\r\n" + " ?,\r\n"
							+ " ?,\r\n" + " ?,\r\n" + " sysdate)";

					pst = con.prepareStatement(detailqry);
					pst.setString(1, sub_token_id);
					pst.setString(2, (String) obj.get("PROBLEM_ID"));
					pst.setString(3, (String) obj.get("PROBLEM_ID"));
					pst.setString(4, (String) obj.get("PROBLEM_ID"));

					pst.setString(5, (String) obj.get("REMARKS"));
					pst.setString(6, USER);
					pst.executeUpdate();

					// ---------------- token detail
				}
			}
			con.commit();
			return "Succesfully Resolved Complaint";

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	// solving directly ticket even when complain exist
	public String addthencloseProblem(List<Map<String, Object>> servicestypelist, String token_ID, String USER)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			con.setAutoCommit(false);
			ResultSet tokenrs;
			PreparedStatement pst;
			// for multiple services - token master

			String chkqry = "select sub_token_id from token_master where token_id=? and service_no=?";
			String cqry = "select TM_SUB_TOKEN_ID.NEXTVAL from dual";

			String subqry = "INSERT INTO TOKEN_MASTER (SUB_TOKEN_ID,\r\n"
					+ "                               TOKEN_ID,\r\n"
					+ "                               SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               SERVICE_NO,\r\n"

					+ "                               SERVICE_TYPE_ID,\r\n"
					+ "                               CREATE_BY,\r\n" + "                               CREATE_DT)\r\n"
					+ "     VALUES (?,\r\n" + "             ?,\r\n" + "             (?),\r\n" + "             'C',\r\n"
					+ "             ?,\r\n" + "         ?,    ?,\r\n" + "             ?,\r\n" + "             ?,\r\n"
					+ "             SYSDATE)";
			String subcloseqry = "UPDATE TOKEN_MASTER\r\n" + "   SET SUB_TEAM_CODE = ?,\r\n"
					+ "       SOLVE_FLAG = 'C',\r\n" + "       SOLVE_DT = SYSDATE,\r\n" + "       SOLVE_BY = ?,\r\n"
					+ "       REMARKS = ?,\r\n" + "       UPDATE_BY = ?,\r\n" + "       UPDATE_DT = SYSDATE\r\n"
					+ " WHERE SUB_TOKEN_ID = ?";

			if (servicestypelist.size() > 0) {
				for (Map<String, Object> obj : servicestypelist) {

					pst = con.prepareStatement(chkqry);
					pst.setString(1, token_ID);
					pst.setString(2, (String) obj.get("SERVICE_NO"));
					tokenrs = pst.executeQuery();
					// repon existing close service and close it again
					if (tokenrs.next()) {
						sub_token_id = tokenrs.getString(1);
						System.out.println("subtoken" + sub_token_id);
						pst = con.prepareStatement(subcloseqry);
						pst.setString(1, "FLMTA");
						pst.setString(2, USER);
						pst.setString(3, (String) obj.get("REMARKS"));
						pst.setString(4, USER);
						pst.setString(5, sub_token_id);
						pst.executeUpdate();

						// for multiple services - token detail
						String seqqry = "select TM_SUB_TOKEN_DETAIL_ID.NEXTVAL from dual";
						tokenrs = con.prepareStatement(seqqry).executeQuery();
						while (tokenrs.next()) {
							seqqry = tokenrs.getString(1);
						}
						String detailqry = "INSERT INTO TOKEN_DETAIL (\r\n"
								+ "   TD_ID, SUB_TOKEN_ID, FROM_SUB_TEAM_CODE, \r\n"
								+ "   TO_SUB_TEAM_CODE, SOLVE_FLAG, PROBLEM_ID, \r\n"
								+ "   REMARKS, CREATE_BY, CREATE_DT \r\n" + "   ) \r\n"
								+ "VALUES ( TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + " ?,\r\n" + " (?),\r\n" + " (?),\r\n"
								+ " 'C',\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " sysdate)";

						pst = con.prepareStatement(detailqry);
						pst.setString(1, sub_token_id);
						pst.setString(2, "FLMTA");
						pst.setString(3, "FLMTA");
						pst.setString(4, (String) obj.get("PROBLEM_ID"));

						pst.setString(5, (String) obj.get("REMARKS"));
						pst.setString(6, USER);
						pst.executeUpdate();

						// ---------------- token detail

					}

					// just resolving tickets

					else {

						tokenrs = con.prepareStatement(cqry).executeQuery();
						while (tokenrs.next()) {
							sub_token_id = tokenrs.getString(1);
						}
						pst = con.prepareStatement(subqry);
						pst.setString(1, sub_token_id);
						pst.setString(2, token_ID);
						pst.setString(3, "FLMTA");
						pst.setString(4, (String) obj.get("PROBLEM_ID"));
						pst.setString(5, (String) obj.get("REMARKS"));
						pst.setString(6, (String) obj.get("SERVICE_NO"));

						pst.setString(7, (String) obj.get("SERVICE_ID"));

						pst.setString(8, USER);
						pst.executeUpdate();

						// for multiple services - token detail
						String seqqry = "select TM_SUB_TOKEN_DETAIL_ID.NEXTVAL from dual";
						tokenrs = con.prepareStatement(seqqry).executeQuery();
						while (tokenrs.next()) {
							seqqry = tokenrs.getString(1);
						}
						String detailqry = "INSERT INTO TOKEN_DETAIL (\r\n"
								+ "   TD_ID, SUB_TOKEN_ID, FROM_SUB_TEAM_CODE, \r\n"
								+ "   TO_SUB_TEAM_CODE, SOLVE_FLAG, PROBLEM_ID, \r\n"
								+ "   REMARKS, CREATE_BY, CREATE_DT \r\n" + "   ) \r\n"
								+ "VALUES ( TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + " ?,\r\n" + " (?),\r\n" + " (?),\r\n"
								+ " 'C',\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " sysdate)";

						pst = con.prepareStatement(detailqry);
						pst.setString(1, sub_token_id);
						pst.setString(2, "FLMTA");
						pst.setString(3, "FLMTA");
						pst.setString(4, (String) obj.get("PROBLEM_ID"));

						pst.setString(5, (String) obj.get("REMARKS"));
						pst.setString(6, USER);
						pst.executeUpdate();

						// ---------------- token detail
					}
				}
			}

			con.commit();
			return "Succesfully Added and Then Closed Complaint for Services to existing Complaint";

		} catch (Exception e) {
			e.printStackTrace();
			;
			return "Failed to add Services in exisiting problem. Error:" + e.getLocalizedMessage();
		} finally {
			con.close();
		}

	}

	public String addProblem(List<Map<String, Object>> servicestypelist, String token_ID, String USER)
			throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			con.setAutoCommit(false);
			ResultSet tokenrs;
			PreparedStatement pst;
			// for multiple services - token master
			String chkqry = "select sub_token_id from token_master where token_id=? and service_no=?";

			String cqry = "select TM_SUB_TOKEN_ID.NEXTVAL from dual";

			String subqry = "INSERT INTO TOKEN_MASTER (SUB_TOKEN_ID,\r\n"
					+ "                               TOKEN_ID,\r\n"
					+ "                               SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               SERVICE_NO,\r\n"

					+ "                               SERVICE_TYPE_ID,\r\n"
					+ "                               CREATE_BY,\r\n" + "                               CREATE_DT)\r\n"
					+ "     VALUES (?,\r\n" + "             ?,\r\n" + "             (SELECT sub_team_code\r\n"
					+ "                FROM m_problem\r\n" + "               WHERE problem_id = ?),\r\n"
					+ "             'N',\r\n" + "             ?,\r\n" + "         ?,    ?,\r\n" + "             ?,\r\n"
					+ "             ?,\r\n" + "             SYSDATE)";
			String subcloseqry = "UPDATE TOKEN_MASTER\r\n"
					+ "   SET SUB_TEAM_CODE = (select sub_team_code from m_problem where problem_id=?),\r\n"
					+ "       SOLVE_FLAG = 'F',\r\n" + "       SOLVE_DT = SYSDATE,\r\n" + "       SOLVE_BY = ?,\r\n"
					+ "       REMARKS = ?,\r\n" + "       UPDATE_BY = ?,\r\n" + "       UPDATE_DT = SYSDATE\r\n"
					+ " WHERE SUB_TOKEN_ID = ?";

			if (servicestypelist.size() > 0) {
				for (Map<String, Object> obj : servicestypelist) {
					pst = con.prepareStatement(chkqry);
					pst.setString(1, token_ID);
					pst.setString(2, (String) obj.get("SERVICE_NO"));
					tokenrs = pst.executeQuery();
					// reopen existing close service and add detail only
					if (tokenrs.next()) {
						sub_token_id = tokenrs.getString(1);
						pst = con.prepareStatement(subcloseqry);
						pst.setString(1, (String) obj.get("PROBLEM_ID"));
						pst.setString(2, USER);
						pst.setString(3, "REOPEN:" + (String) obj.get("REMARKS"));
						pst.setString(4, USER);
						pst.setString(5, sub_token_id);
						pst.executeUpdate();

						// for multiple services - token detail
						String seqqry = "select TM_SUB_TOKEN_DETAIL_ID.NEXTVAL from dual";
						tokenrs = con.prepareStatement(seqqry).executeQuery();
						while (tokenrs.next()) {
							seqqry = tokenrs.getString(1);
						}
						String detailqry = "INSERT INTO TOKEN_DETAIL (\r\n"
								+ "   TD_ID, SUB_TOKEN_ID, FROM_SUB_TEAM_CODE, \r\n"
								+ "   TO_SUB_TEAM_CODE, SOLVE_FLAG, PROBLEM_ID, \r\n"
								+ "   REMARKS, CREATE_BY, CREATE_DT \r\n" + "   ) \r\n"
								+ "VALUES ( TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + " ?,\r\n"
								+ " (select sub_team_code from m_problem where problem_id=?),\r\n"
								+ " (select sub_team_code from m_problem where problem_id=?),\r\n" + " 'F',\r\n"
								+ " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " sysdate)";

						pst = con.prepareStatement(detailqry);
						pst.setString(1, sub_token_id);
						pst.setString(2, (String) obj.get("PROBLEM_ID"));
						pst.setString(3, (String) obj.get("PROBLEM_ID"));
						pst.setString(4, (String) obj.get("PROBLEM_ID"));

						pst.setString(5, "REOPEN:" + (String) obj.get("REMARKS"));
						pst.setString(6, USER);
						pst.executeUpdate();

						// ---------------- token detail

					}

					// just resolving tickets
					else {
						tokenrs = con.prepareStatement(cqry).executeQuery();
						while (tokenrs.next()) {
							sub_token_id = tokenrs.getString(1);
						}
						pst = con.prepareStatement(subqry);
						pst.setString(1, sub_token_id);
						pst.setString(2, token_ID);
						pst.setString(3, (String) obj.get("PROBLEM_ID"));
						pst.setString(4, (String) obj.get("PROBLEM_ID"));
						pst.setString(5, (String) obj.get("REMARKS"));
						pst.setString(6, (String) obj.get("SERVICE_NO"));

						pst.setString(7, (String) obj.get("SERVICE_ID"));

						pst.setString(8, USER);
						pst.executeUpdate();

						// for multiple services - token detail
						String seqqry = "select TM_SUB_TOKEN_DETAIL_ID.NEXTVAL from dual";
						tokenrs = con.prepareStatement(seqqry).executeQuery();
						while (tokenrs.next()) {
							seqqry = tokenrs.getString(1);
						}
						String detailqry = "INSERT INTO TOKEN_DETAIL (\r\n"
								+ "   TD_ID, SUB_TOKEN_ID, FROM_SUB_TEAM_CODE, \r\n"
								+ "   TO_SUB_TEAM_CODE, SOLVE_FLAG, PROBLEM_ID, \r\n"
								+ "   REMARKS, CREATE_BY, CREATE_DT \r\n" + "   ) \r\n"
								+ "VALUES ( TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + " ?,\r\n"
								+ " (select sub_team_code from m_problem where problem_id=?),\r\n"
								+ " (select sub_team_code from m_problem where problem_id=?),\r\n" + " 'N',\r\n"
								+ " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " sysdate)";

						pst = con.prepareStatement(detailqry);
						pst.setString(1, sub_token_id);
						pst.setString(2, (String) obj.get("PROBLEM_ID"));
						pst.setString(3, (String) obj.get("PROBLEM_ID"));
						pst.setString(4, (String) obj.get("PROBLEM_ID"));

						pst.setString(5, (String) obj.get("REMARKS"));
						pst.setString(6, USER);
						pst.executeUpdate();

						// ---------------- token detail

					}
				}
			}

			con.commit();
			return "Succesfully Added Services to existing Complaint";

		} catch (Exception e) {
			e.printStackTrace();
			;
			return "Failed to add Services in exisiting problem. Error:" + e.getLocalizedMessage();
		} finally {
			con.close();
		}

	}

	public String saveProblem(List<Map<String, Object>> servicestypelist, String serviceID, String SRV_NO,
			String Complain_no, String contactName, String Remarks, String USER, String FDCName, String teamname,
			String Supervisorname, String SupervisorContno, String Teamleader, String TeamleaderNo,
			String CUSTOMER_NAME, String CONTACT_NO, String OLT_PORT, String FAP_LOCATION, String FAP_PORT,
			String CPE_RX_LVL,String ODF_PORT) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		try {

			String cqry = "select * from MAIN_TOKEN_MASTER where SRV_NO=? and SOLVE_FLAG <>'C'";

			PreparedStatement qpst = con.prepareStatement(cqry);
			qpst.setString(1, SRV_NO);
			ResultSet rs = qpst.executeQuery();
			if (rs.next() == true) {
				return ("Complain Already Exist Token_ID:" + rs.getString("TOKEN_ID"));
				// throw new SQLException("Complain Already Exist");
			}
			WebTeamDao teammdao = new WebTeamDao();
			String team_id = teammdao.saveWebteam(con, teamname, FDCName, Supervisorname, SupervisorContno, Teamleader,
					TeamleaderNo);
			System.err.println(team_id);
			cqry = "select MTM_Token_ID.NEXTVAL from dual";
			ResultSet tokenrs = con.prepareStatement(cqry).executeQuery();
			while (tokenrs.next()) {
				tOKEN_ID = tokenrs.getString(1);
			}

			String qry = "INSERT INTO MAIN_TOKEN_MASTER (TOKEN_ID,\n"
					+ "                                    SERVICE_ID,\n"
					+ "                                    SRV_NO, SUB_TEAM_CODE, \n"
					+ "                                    SOLVE_FLAG,\n"
					+ "                                    COMPLAIN_NO,\n"
					+ "                                    CONTACT_NAME,\n" +

					"                                    REMARKS,\n"
					+ "                                    CREATE_BY, \n"
					+ "                                    TEAM_ID, \n"

					+ "                                    FDC_CODE) \n" +

					"     VALUES (?,\n" + "             ?,\n" + "             ?,\n" + "             ?,\n"
					+ "             ?,\n" + "             ?,\n" + "             ?,\n" + "             ?,\n"
					+ "            ?, ?,\n" +

					"             (select fdc_code from M_FDC where DESCRIPTION=?)\r\n" + ")";

			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, tOKEN_ID);
			pst.setString(2, serviceID);
			pst.setString(3, SRV_NO);
			pst.setString(4, "FLMTA");
			pst.setString(5, "N");
			pst.setString(6, Complain_no);
			pst.setString(7, contactName);
			pst.setString(8, Remarks);
			pst.setString(9, USER);
			pst.setString(10, team_id);

			pst.setString(11, FDCName);
			pst.executeUpdate();

			// adding customer infomation
			qry = "INSERT INTO CUSTOMER_INFO (\r\n" + "   TOKEN_ID, CUSTOMER_NAME, CONTACT_NO, \r\n"
					+ "   OLT_PORT, FAP_LOCATION, FAP_PORT, \r\n" + "   CPE_RX_LVL,ODF_PORT) \r\n" + "VALUES ( ?,\r\n"
					+ " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,\r\n" + " ?,? )";
			pst = con.prepareStatement(qry);
			pst.setString(1, tOKEN_ID);
			pst.setString(2, CUSTOMER_NAME);
			pst.setString(3, CONTACT_NO);
			pst.setString(4, OLT_PORT);
			pst.setString(5, FAP_LOCATION);
			pst.setString(6, FAP_PORT);
			pst.setString(7, CPE_RX_LVL);
			pst.setString(8, ODF_PORT);
			pst.executeUpdate();

			// for multiple services - token master
			cqry = "select TM_SUB_TOKEN_ID.NEXTVAL from dual";

			String subqry = "INSERT INTO TOKEN_MASTER (SUB_TOKEN_ID,\r\n"
					+ "                               TOKEN_ID,\r\n"
					+ "                               SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               SERVICE_NO,\r\n"

					+ "                               SERVICE_TYPE_ID,\r\n"
					+ "                               CREATE_BY,\r\n" + "                               CREATE_DT)\r\n"
					+ "     VALUES (?,\r\n" + "             ?,\r\n" + "             (SELECT sub_team_code\r\n"
					+ "                FROM m_problem\r\n" + "               WHERE problem_id = ?),\r\n"
					+ "             'N',\r\n" + "             ?,\r\n" + "         ?,    ?,\r\n" + "             ?,\r\n"
					+ "             ?,\r\n" + "             SYSDATE)";

			if (servicestypelist.size() > 0) {
				for (Map<String, Object> obj : servicestypelist) {
					tokenrs = con.prepareStatement(cqry).executeQuery();
					while (tokenrs.next()) {
						sub_token_id = tokenrs.getString(1);
					}
					pst = con.prepareStatement(subqry);
					pst.setString(1, sub_token_id);
					pst.setString(2, tOKEN_ID);
					pst.setString(3, (String) obj.get("PROBLEM_ID"));
					pst.setString(4, (String) obj.get("PROBLEM_ID"));
					pst.setString(5, (String) obj.get("REMARKS"));
					pst.setString(6, (String) obj.get("SERVICE_NO"));

					pst.setString(7, (String) obj.get("SERVICE_ID"));

					pst.setString(8, USER);
					pst.executeUpdate();

					// for multiple services - token detail
					String seqqry = "select TM_SUB_TOKEN_DETAIL_ID.NEXTVAL from dual";
					tokenrs = con.prepareStatement(seqqry).executeQuery();
					while (tokenrs.next()) {
						seqqry = tokenrs.getString(1);
					}
					String detailqry = "INSERT INTO TOKEN_DETAIL (\r\n"
							+ "   TD_ID, SUB_TOKEN_ID, FROM_SUB_TEAM_CODE, \r\n"
							+ "   TO_SUB_TEAM_CODE, SOLVE_FLAG, PROBLEM_ID, \r\n"
							+ "   REMARKS, CREATE_BY, CREATE_DT \r\n" + "   ) \r\n"
							+ "VALUES ( TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + " ?,\r\n"
							+ " (select sub_team_code from m_problem where problem_id=?),\r\n"
							+ " (select sub_team_code from m_problem where problem_id=?),\r\n" + " 'N',\r\n" + " ?,\r\n"
							+ " ?,\r\n" + " ?,\r\n" + " sysdate)";

					pst = con.prepareStatement(detailqry);
					pst.setString(1, sub_token_id);
					pst.setString(2, (String) obj.get("PROBLEM_ID"));
					pst.setString(3, (String) obj.get("PROBLEM_ID"));
					pst.setString(4, (String) obj.get("PROBLEM_ID"));

					pst.setString(5, (String) obj.get("REMARKS"));
					pst.setString(6, USER);
					pst.executeUpdate();

					// ---------------- token detail
				}
			}
			con.commit();
			return "New Trouble Ticket Generated, Token ID : " + tOKEN_ID;

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			if (e.getMessage().contains("\"MAIN_TOKEN_MASTER\".\"FDC_CODE\"")) {
				return "Failed to Save : "
						+ "Contact administrator to import new list of FDC. Current FDC isn't mapped in database";
			}
			return "Failed to Save : " + e.getMessage();
		} finally {
			con.close();
		}
	}

	public List<Map<String, Object>> getComplainListlowlvl(String User, String Region, String Zone, String District,
			String Office, String Olt, String Subteam, String Service_type, String Frm_dt, String To_dt, String Status,
			String Teamid) throws SQLException {
		Connection con = DbCon.getConnection();
		try {
			String qry = "SELECT TOKENS.*,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SERVICE_TYPE\r\n" + 
					"           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_PROBLEM\r\n" + 
					"           WHERE PROBLEM_ID = TOKENS.PROBLEM_ID)              PROBLEM_DESC,\r\n" + 
					"           (SELECT DESCRIPTION||' '||FDC_LOCATION\r\n" + 
					"            FROM M_FDC \r\n" + 
					"           WHERE FDC_CODE  = TOKENS.FDC_CODE )              FDC_DESC\r\n" + 
					"    FROM (SELECT TM.TOKEN_ID,\r\n" + 
					"                 TM.SERVICE_NO,\r\n" + 
					"                 SUB_TOKEN_ID,\r\n" + 
					"                 SERVICE_ID,\r\n" + 
					"                 SRV_NO,\r\n" + 
					"                 COMPLAIN_NO,\r\n" + 
					"                 CONTACT_NAME,\r\n" + 
					"                 CUSTOMER_NAME,\r\n" + 
					"                 TM.PROBLEM_ID,\r\n" + 
					"                 TM.REMARKS,\r\n" + 
					"                 FDC_CODE,\r\n" + 
					"                 TM.SUB_TEAM_CODE,\r\n" + 
					"                 TM.SOLVE_FLAG,\r\n" + 
					"                 SERVICE_TYPE_ID,\r\n" + 
					"                 TM.CREATE_DT,\r\n" + 
					"                 MTM.TEAM_ID\r\n" + 
					"            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM,CUSTOMER_INFO CI\r\n" + 
					"           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID and CI.TOKEN_ID = MTM.TOKEN_ID\r\n" + 
					"                 AND EXISTS\r\n" + 
					"                         (SELECT fdc_code\r\n" + 
					"                            FROM WEB_USER_FDC_MAP\r\n" + 
					"                           WHERE user_id = ? AND MTM.FDC_CODE = WEB_USER_FDC_MAP.FDC_CODE)) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         AND EXISTS\r\n" + 
					"                 (SELECT FDC_CODE\r\n" + 
					"                    FROM VW_FTTH_ALL_FDC\r\n" + 
					"                   WHERE     TOKENS.fdc_code = VW_FTTH_ALL_FDC.fdc_code\r\n" + 
					"                         AND REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
					"                         AND ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
					"                         AND DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
					"                         AND OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
					"                         AND OLT_CODE = NVL (?, OLT_CODE))\r\n" + 
					"         AND TOKENS.SUB_TEAM_CODE = NVL (?, SUB_TEAM_CODE)\r\n" + 
					"         AND TOKENS.SERVICE_TYPE_ID = NVL (?, SERVICE_TYPE_ID)\r\n" + 
					"         AND TOKENS.SOLVE_FLAG = NVL (?, SOLVE_FLAG)\r\n" + 
					"         AND TOKENS.CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
					"                                  AND NVL (?, SYSDATE)\r\n" + 
					"          AND TOKENS.TEAM_ID=NVL(?,TEAM_ID)\r\n" + 
					"ORDER BY token_ID, create_dt DESC";

			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, User);
			pst.setString(2, User);
			pst.setString(3, Region);
			pst.setString(4, Zone);
			pst.setString(5, District);
			pst.setString(6, Office);
			pst.setString(7, Olt);
			pst.setString(8, Subteam);
			pst.setString(9, Service_type);
			pst.setString(10, Status);

			pst.setString(11, Frm_dt);
			pst.setString(12, To_dt);
			pst.setString(13, Teamid);

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

	public List<Map<String, Object>> getComplainList(String User, String Region, String Zone, String District,
			String Office, String Olt, String Subteam, String Service_type, String Frm_dt, String To_dt, String Status,
			String Teamid) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry = "SELECT TOKENS.*,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_SERVICE_TYPE\r\n" + 
					"           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC,\r\n" + 
					"         (SELECT DESCRIPTION\r\n" + 
					"            FROM M_PROBLEM\r\n" + 
					"           WHERE PROBLEM_ID = TOKENS.PROBLEM_ID)              PROBLEM_DESC,\r\n" + 
					"           (SELECT DESCRIPTION||' '||FDC_LOCATION\r\n" + 
					"            FROM M_FDC \r\n" + 
					"           WHERE FDC_CODE  = TOKENS.FDC_CODE )              FDC_DESC\r\n" + 
					"    FROM (SELECT TM.TOKEN_ID,\r\n" + 
					"                 TM.SERVICE_NO,   \r\n" + 
					"                 SUB_TOKEN_ID,\r\n" + 
					"                 SERVICE_ID,\r\n" + 
					"                 SRV_NO,\r\n" + 
					"                 COMPLAIN_NO,\r\n" + 
					"                 CONTACT_NAME,\r\n" + 
					"                 CUSTOMER_NAME,\r\n" + 
					"                 TM.PROBLEM_ID,\r\n" + 
					"                 TM.REMARKS,\r\n" + 
					"                 FDC_CODE,\r\n" + 
					"                 TM.SUB_TEAM_CODE,\r\n" + 
					"                 TM.SOLVE_FLAG,\r\n" + 
					"                 SERVICE_TYPE_ID,\r\n" + 
					"                 TM.CREATE_DT,\r\n" + 
					"                 MTM.TEAM_ID\r\n" + 
					"            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM,CUSTOMER_INFO CI\r\n" + 
					"           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID and CI.TOKEN_ID = MTM.TOKEN_ID\r\n" + 
					"                 ) TOKENS\r\n" + 
					"   WHERE     EXISTS\r\n" + 
					"                 (SELECT *\r\n" + 
					"                    FROM WEB_USER_TEAM_MAP\r\n" + 
					"                   WHERE     USER_ID = ?\r\n" + 
					"                         AND TOKENS.SUB_TEAM_CODE =\r\n" + 
					"                             WEB_USER_TEAM_MAP.SUB_TEAM_CODE)\r\n" + 
					"         AND EXISTS\r\n" + 
					"                 (SELECT FDC_CODE\r\n" + 
					"                    FROM VW_FTTH_ALL_FDC\r\n" + 
					"                   WHERE     TOKENS.fdc_code = VW_FTTH_ALL_FDC.fdc_code\r\n" + 
					"                         AND REGION_CODE = NVL (?, REGION_CODE)\r\n" + 
					"                         AND ZONE_CODE = NVL (?, ZONE_CODE)\r\n" + 
					"                         AND DISTRICT_CODE = NVL (?, DISTRICT_CODE)\r\n" + 
					"                         AND OFFICE_CODE = NVL (?, OFFICE_CODE)\r\n" + 
					"                         AND OLT_CODE = NVL (?, OLT_CODE))\r\n" + 
					"         AND TOKENS.SUB_TEAM_CODE = NVL (?, SUB_TEAM_CODE)\r\n" + 
					"         AND TOKENS.SERVICE_TYPE_ID = NVL (?, SERVICE_TYPE_ID)\r\n" + 
					"         AND TOKENS.SOLVE_FLAG = NVL (?, SOLVE_FLAG)\r\n" + 
					"         AND TOKENS.CREATE_DT BETWEEN NVL (?, SYSDATE - 30)\r\n" + 
					"                                  AND NVL (?, SYSDATE)\r\n" + 
					"          AND TOKENS.TEAM_ID=NVL(?,TEAM_ID)\r\n" + 
					"ORDER BY token_ID, create_dt DESC";

			//System.out.println(qry);
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, User);
			pst.setString(2, Region);
			pst.setString(3, Zone);
			pst.setString(4, District);
			pst.setString(5, Office);
			pst.setString(6, Olt);
			pst.setString(7, Subteam);
			pst.setString(8, Service_type);
			pst.setString(9, Status);

			pst.setString(10, Frm_dt);
			pst.setString(11, To_dt);
			pst.setString(12, Teamid);

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

	// forward dao
	public String ForwardTeam(String forwardtoken, String toteam, String User, String Remarks) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		PreparedStatement pst = null;
		try {
			// check validate if forwardtoken team is assigned to session user
			pst = con.prepareStatement(
					"select sub_team_code from token_master where sub_token_id=? and exists (select SUB_TEAM_CODE from WEB_USER_TEAM_MAP where USER_ID=? and token_master.SUB_TEAM_CODE = WEB_USER_TEAM_MAP.SUB_TEAM_CODE)");
			pst.setString(1, forwardtoken);
			pst.setString(2, User);
			ResultSet rsvalidateteam = pst.executeQuery();
			if (!rsvalidateteam.next()) {
				return ("Forbidden: Current Ticket Team isn't assigned to User!!!");

			}
			// validation till here

			pst = con.prepareStatement("INSERT INTO TOKEN_DETAIL (TD_ID,\r\n"
					+ "                               SUB_TOKEN_ID,\r\n"
					+ "                               FROM_SUB_TEAM_CODE,\r\n"
					+ "                               TO_SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               CREATE_BY)\r\n"
					+ "     VALUES (TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + "             ?,\r\n"
					+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n"
					+ "             ?,\r\n" + "             'F',\r\n" + "             (SELECT PROBLEM_ID\r\n"
					+ "                FROM TOKEN_MASTER\r\n" + "               WHERE SUB_TOKEN_ID = ?),\r\n"
					+ "             ?,\r\n" + "             ?)");
			pst.setString(1, forwardtoken);
			pst.setString(2, forwardtoken);
			pst.setString(3, toteam);
			pst.setString(4, forwardtoken);
			pst.setString(5, Remarks);
			pst.setString(6, User);
			pst.executeUpdate();
//after forwarding team updating subcode in token master
			pst = con.prepareStatement(
					"UPDATE TOKEN_MASTER SET SOLVE_FLAG='F', SUB_TEAM_CODE= ?, UPDATE_BY=?,UPDATE_DT=sysdate WHERE  SUB_TOKEN_ID= ?");
			pst.setString(1, toteam);
			pst.setString(3, forwardtoken);
			pst.setString(2, User);
			pst.executeUpdate();

			con.commit();

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed " + e;
		} finally {
			con.close();
		}
		return "Successfully Forwarded Trouble Ticket to another team";
	}

	// forward dao
	public String Resolved(String forwardtoken, String User, String Remarks,String Solution) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		PreparedStatement pst = null;
		try {
			// check validate if forwardtoken team is assigned to session user
			pst = con.prepareStatement(
					"select sub_team_code from token_master where sub_token_id=? and exists (select SUB_TEAM_CODE from WEB_USER_TEAM_MAP where USER_ID=? and token_master.SUB_TEAM_CODE = WEB_USER_TEAM_MAP.SUB_TEAM_CODE)");
			pst.setString(1, forwardtoken);
			pst.setString(2, User);
			ResultSet rsvalidateteam = pst.executeQuery();
			if (!rsvalidateteam.next()) {
				return ("Forbidden: Current Ticket Team isn't assigned to User!!!");

			}
			// validation till here

			pst = con.prepareStatement("INSERT INTO TOKEN_DETAIL (TD_ID,\r\n"
					+ "                               SUB_TOKEN_ID,\r\n"
					+ "                               FROM_SUB_TEAM_CODE,\r\n"
					+ "                               TO_SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               CREATE_BY,SOLUTION_ID)\r\n"
					+ "     VALUES (TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + "             ?,\r\n"
					+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n"
					+ "             ?,\r\n" + "             'Y',\r\n" + "             (SELECT PROBLEM_ID\r\n"
					+ "                FROM TOKEN_MASTER\r\n" + "               WHERE SUB_TOKEN_ID = ?),\r\n"
					+ "             ?,\r\n" + "             ?,?)");
			pst.setString(1, forwardtoken);
			pst.setString(2, forwardtoken);
			pst.setString(3, "FLMTA");
			pst.setString(4, forwardtoken);
			pst.setString(5, Remarks);
			pst.setString(6, User);
			pst.setString(7, Solution);
			pst.executeUpdate();
			// after forwarding team updating subcode in token master
			pst = con.prepareStatement(
					"UPDATE TOKEN_MASTER SET SOLVE_FLAG='Y', SUB_TEAM_CODE= ?, UPDATE_BY=?,UPDATE_DT=sysdate WHERE  SUB_TOKEN_ID= ?");
			pst.setString(1, "FLMTA");
			pst.setString(3, forwardtoken);
			pst.setString(2, User);
			pst.executeUpdate();

			con.commit();

		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed " + e;
		} finally {
			con.close();
		}
		return "Successfully Resovled Ticket";
	}

	// close tickets dao
	public String Closeticket(String closetoken, String User, String Remarks) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		PreparedStatement pst = null;
		try {
			// check validate if ticket team is assigned to session user
			pst = con.prepareStatement(
					"select sub_team_code from token_master where sub_token_id=? and exists (select SUB_TEAM_CODE from WEB_USER_TEAM_MAP where USER_ID=? and token_master.SUB_TEAM_CODE = WEB_USER_TEAM_MAP.SUB_TEAM_CODE)");
			pst.setString(1, closetoken);
			pst.setString(2, User);
			ResultSet rsvalidateteam = pst.executeQuery();
			if (!rsvalidateteam.next()) {
				return ("Forbidden: Current Ticket Team isn't assigned to User!!!");

			}
			// validation till here

			pst = con.prepareStatement("INSERT INTO TOKEN_DETAIL (TD_ID,\r\n"
					+ "                               SUB_TOKEN_ID,\r\n"
					+ "                               FROM_SUB_TEAM_CODE,\r\n"
					+ "                               TO_SUB_TEAM_CODE,\r\n"
					+ "                               SOLVE_FLAG,\r\n"
					+ "                               PROBLEM_ID,\r\n" + "                               REMARKS,\r\n"
					+ "                               CREATE_BY)\r\n"
					+ "     VALUES (TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + "             ?,\r\n"
					+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n"
					+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n" +

					"             'C',\r\n" + "             (SELECT PROBLEM_ID\r\n"
					+ "                FROM TOKEN_MASTER\r\n" + "               WHERE SUB_TOKEN_ID = ?),\r\n"
					+ "             ?,\r\n" + "             ?)");
			pst.setString(1, closetoken);
			pst.setString(2, closetoken);
			pst.setString(3, closetoken);
			pst.setString(4, closetoken);
			pst.setString(5, Remarks);
			pst.setString(6, User);
			pst.executeUpdate();

			// after closing ticket updating flag in token master
			pst = con.prepareStatement(
					"UPDATE TOKEN_MASTER SET SOLVE_FLAG= 'C',SOLVE_DT=sysdate,SOLVE_BY=?, UPDATE_BY=?,UPDATE_DT=sysdate WHERE  SUB_TOKEN_ID= ?");
			pst.setString(3, closetoken);
			pst.setString(2, User);
			pst.setString(1, User);

			pst.executeUpdate();

			// checking if all tickets are resolved

			pst = con.prepareStatement(
					"select * from token_master where solve_flag<>'C' and token_id=(select token_id from token_master where sub_token_id=?)");
			pst.setString(1, closetoken);
			ResultSet qsolveflag = pst.executeQuery();
			if (!qsolveflag.next()) {
				pst = con.prepareStatement("UPDATE MAIN_TOKEN_MASTER\r\n" + "   SET SOLVE_FLAG = 'C',\r\n"
						+ "       SOLVE_DT = SYSDATE,\r\n" + "       SOLVE_BY = ?,\r\n" +

						"       UPDATE_BY = ?,\r\n" + "       UPDATE_DT = SYSDATE\r\n"
						+ " WHERE token_id = (SELECT token_id\r\n" + "                     FROM token_master\r\n"
						+ "                    WHERE sub_token_id = ?)");
				pst.setString(1, User);

				pst.setString(2, User);
				pst.setString(3, closetoken);
				pst.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed " + e;
		} finally {
			con.close();
		}
		return "Successfully Trouble Ticket has been Resolved";
	}

	// close array of tickets dao
	public String Closearrayofticket(List<String> closetokenarray, String User, String Remarks) throws SQLException {
		Connection con = DbCon.getConnection();
		con.setAutoCommit(false);
		PreparedStatement pst = null;
		try {
			for (String var : closetokenarray) {
				// check validate if ticket team is assigned to session user
				pst = con.prepareStatement(
						"select sub_team_code from token_master where sub_token_id=? and exists (select SUB_TEAM_CODE from WEB_USER_TEAM_MAP where USER_ID=? and token_master.SUB_TEAM_CODE = WEB_USER_TEAM_MAP.SUB_TEAM_CODE)");
				pst.setString(1, var);
				pst.setString(2, User);
				ResultSet rsvalidateteam = pst.executeQuery();
				if (!rsvalidateteam.next()) {
					con.rollback();
					return ("Forbidden: Current Ticket Team isn't assigned to User!!!");

				}
				// validation till here

				String closetoken = var;
				pst = con.prepareStatement(
						"INSERT INTO TOKEN_DETAIL (TD_ID,\r\n" + "                               SUB_TOKEN_ID,\r\n"
								+ "                               FROM_SUB_TEAM_CODE,\r\n"
								+ "                               TO_SUB_TEAM_CODE,\r\n"
								+ "                               SOLVE_FLAG,\r\n"
								+ "                               PROBLEM_ID,\r\n"
								+ "                               REMARKS,\r\n"
								+ "                               CREATE_BY)\r\n"
								+ "     VALUES (TM_SUB_TOKEN_DETAIL_ID.NEXTVAL,\r\n" + "             ?,\r\n"
								+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n"
								+ "             (select sub_team_code from TOKEN_MASTER where SUB_TOKEN_ID=?),\r\n" +

								"             'C',\r\n" + "             (SELECT PROBLEM_ID\r\n"
								+ "                FROM TOKEN_MASTER\r\n"
								+ "               WHERE SUB_TOKEN_ID = ?),\r\n" + "             ?,\r\n"
								+ "             ?)");
				pst.setString(1, closetoken);
				pst.setString(2, closetoken);
				pst.setString(3, closetoken);
				pst.setString(4, closetoken);
				pst.setString(5, Remarks);
				pst.setString(6, User);
				pst.executeUpdate();

				// after closing ticket updating flag in token master
				pst = con.prepareStatement(
						"UPDATE TOKEN_MASTER SET SOLVE_FLAG= 'C',SOLVE_DT=sysdate,SOLVE_BY=?, UPDATE_BY=?,UPDATE_DT=sysdate WHERE  SUB_TOKEN_ID= ?");
				pst.setString(3, closetoken);
				pst.setString(2, User);
				pst.setString(1, User);

				pst.executeUpdate();

				// checking if all tickets are resolved

				pst = con.prepareStatement(
						"select * from token_master where solve_flag<>'C' and token_id=(select token_id from token_master where sub_token_id=?)");
				pst.setString(1, closetoken);
				ResultSet qsolveflag = pst.executeQuery();
				if (!qsolveflag.next()) {
					pst = con.prepareStatement("UPDATE MAIN_TOKEN_MASTER\r\n" + "   SET SOLVE_FLAG = 'C',\r\n"
							+ "       SOLVE_DT = SYSDATE,\r\n" + "       SOLVE_BY = ?,\r\n" +

							"       UPDATE_BY = ?,\r\n" + "       UPDATE_DT = SYSDATE\r\n"
							+ " WHERE token_id = (SELECT token_id\r\n" + "                     FROM token_master\r\n"
							+ "                    WHERE sub_token_id = ?)");
					pst.setString(1, User);

					pst.setString(2, User);
					pst.setString(3, closetoken);
					pst.executeUpdate();
				}
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return "Failed " + e;
		} finally {
			con.close();
		}
		return "Successfully All Trouble Ticket has been Resolved";
	}

	public List<Map<String, Object>> getTokenDetail(String SUB_TOKEN_ID) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con
					.prepareStatement("select td.*,nvl(ms.DESCRIPTION ,'-') SOLUTION from TOKEN_DETAIL td,m_solution ms where\r\n"
							+ " td.SOLUTION_ID=ms.SOLUTION_ID(+) and SUB_TOKEN_ID=? order by td_id");
			pst.setString(1, SUB_TOKEN_ID);

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

	public List<Map<String, Object>> getComplainbyCPESN(String CPESN) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			PreparedStatement pst = con.prepareStatement(
					"select * from VW_TOKEN_MASTER_ONLY where token_id=(select token_id from MAIN_TOKEN_MASTER where SRV_NO=? and solve_flag<>'C')");
			pst.setString(1, CPESN);

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

	public List<Map<String, Object>> getComplainListDetailbyToken(String TokenID) throws SQLException {
		Connection con = DbCon.getConnection();

		try {
			String qry = "/* Formatted on 4/12/2020 1:09:32 AM (QP5 v5.354) */\r\n" + "  SELECT TOKENS.*,\r\n"
					+ "         (SELECT DESCRIPTION\r\n" + "            FROM M_SERVICE_TYPE\r\n"
					+ "           WHERE SERVICE_TYPE_ID = TOKENS.SERVICE_TYPE_ID)    SERVICE_DESC,\r\n"
					+ "         (SELECT DESCRIPTION\r\n" + "            FROM M_PROBLEM\r\n"
					+ "           WHERE PROBLEM_ID = TOKENS.PROBLEM_ID)              PROBLEM_DESC,\r\n"
					+ "           (SELECT DESCRIPTION||' '||FDC_LOCATION\r\n" + "            FROM M_FDC \r\n"
					+ "           WHERE FDC_CODE  = TOKENS.FDC_CODE )              FDC_DESC\r\n" +

					"    FROM (SELECT TM.TOKEN_ID,\r\n" + "                 SUB_TOKEN_ID,\r\n"
					+ "                 SERVICE_ID,\r\n" + "                 SRV_NO,\r\n"
					+ "                 COMPLAIN_NO,\r\n" + "                 CONTACT_NAME,\r\n"
					+ "                 TM.PROBLEM_ID,\r\n" + "                 TM.REMARKS,\r\n"
					+ "                 FDC_CODE,\r\n" + "                 TM.SUB_TEAM_CODE,\r\n"
					+ "                 TM.SOLVE_FLAG,\r\n" + "                 SERVICE_TYPE_ID,\r\n"
					+ "                 TM.CREATE_DT,\r\n" + "                 MTM.REMARKS MAIN_REMARKS\r\n"
					+ "            FROM MAIN_TOKEN_MASTER MTM, TOKEN_MASTER TM\r\n"
					+ "           WHERE     MTM.TOKEN_ID = TM.TOKEN_ID\r\n" + "           and mtm.token_id=?\r\n"
					+ "                ) TOKENS\r\n" + "ORDER BY token_ID, create_dt DESC";
		//	System.out.println(qry);
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, TokenID);

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
