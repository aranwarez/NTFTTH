package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebTeamDao {

	public String saveWebteam(Connection con, String teamname, String fdc_name, String Supervisorname,
			String SupervisorContno, String Teamleader, String TeamleaderNo) {
		try {

			String qry = "select * from WEB_TEAM where TEAMNAME=? and OFFICE_CODE=(select office_code from VW_FTTH_ALL_FDC where fdc=?)";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setString(1, teamname);
			pst.setString(2, fdc_name);
			ResultSet rs = pst.executeQuery();
			if (rs.next() == true) {
				String rsTEAMSUPERVISORNAME = "";
				String rsTEAMLEADERCONTACTNUMBER = "";
				String rsTEAMSUPERVISORCONTACTNUMBER = "";
				String rsTEAMLEADERNAME = "";
				if (rs.getString("TEAMSUPERVISORNAME") != null) {
					rsTEAMSUPERVISORNAME = rs.getString("TEAMSUPERVISORNAME");
				}

				if (rs.getString("TEAMLEADERCONTACTNUMBER") != null) {
					rsTEAMLEADERCONTACTNUMBER = rs.getString("TEAMLEADERCONTACTNUMBER");
				}

				if (rs.getString("TEAMSUPERVISORCONTACTNUMBER") != null) {
					rsTEAMSUPERVISORCONTACTNUMBER = rs.getString("TEAMSUPERVISORCONTACTNUMBER");
				}

				if (rs.getString("TEAMLEADERNAME") != null) {
					rsTEAMLEADERNAME = rs.getString("TEAMLEADERNAME");
				}

				if (!(rsTEAMSUPERVISORNAME.equals(Supervisorname) && rsTEAMLEADERCONTACTNUMBER.equals(TeamleaderNo)
						&& rsTEAMSUPERVISORCONTACTNUMBER.equals(SupervisorContno)
						&& rsTEAMLEADERNAME.equals(Teamleader))) {
					// changes found in team details

					qry = "UPDATE FTTH.WEB_TEAM\r\n" + "SET    TEAMSUPERVISORCONTACTNUMBER = ?,\r\n"
							+ "       TEAMSUPERVISORNAME          = ?,\r\n"
							+ "       TEAMLEADERCONTACTNUMBER     = ?,\r\n"
							+ "       TEAMLEADERNAME              = ?,\r\n"
							+ "       UPDATE_BY                   = 'SYSTEM',\r\n"
							+ "       UPDATE_DT                   = sysdate\r\n"
							+ "WHERE  TEAM_ID                     = ?\r\n" + "";
					pst = con.prepareStatement(qry);
					pst.setString(1, SupervisorContno);
					pst.setString(2, Supervisorname);
					pst.setString(3, TeamleaderNo);
					pst.setString(4, Teamleader);
					pst.setString(5, rs.getString("TEAM_ID"));
					pst.executeUpdate();
				}

			} else {
				System.err.println("NOT found");

				qry = "INSERT INTO FTTH.WEB_TEAM (\r\n" + "   TEAM_ID, TEAMNAME, TEAMSUPERVISORCONTACTNUMBER, \r\n"
						+ "   TEAMSUPERVISORNAME, TEAMLEADERCONTACTNUMBER, TEAMLEADERNAME, \r\n"
						+ "   OFFICE_CODE, CREATE_BY, CREATE_DT\r\n" + "  ) \r\n" + "VALUES ( WT_TEAM_ID.nextval,\r\n"
						+ " ?,\r\n" + " ?,\r\n" + "?,\r\n" + " ?,\r\n" + " ?,\r\n"
						+ " (select OFFICE_CODE from VW_FTTH_ALL_FDC where FDC=?)  ,\r\n" + " 'SYSTEM',\r\n"
						+ " sysdate)";
				pst = con.prepareStatement(qry);
				pst.setString(1, teamname);

				pst.setString(2, SupervisorContno);
				pst.setString(3, Supervisorname);
				pst.setString(4, TeamleaderNo);
				pst.setString(5, Teamleader);
				pst.setString(6, fdc_name);
				pst.executeUpdate();
			}

			qry = "select * from WEB_TEAM where TEAMNAME=? and OFFICE_CODE=(select office_code from VW_FTTH_ALL_FDC where fdc=?)";
			pst = con.prepareStatement(qry);
			pst.setString(1, teamname);
			pst.setString(2, fdc_name);
			rs = pst.executeQuery();
			while (rs.next()) {
				return rs.getString("TEAM_ID");
			}
			throw new SQLException(
					"CANNOT MAP TEAM OR FDC PLEASE MAKE SURE FDC is updated and check if team is valid in team information");

		} catch (Exception e) {

		}
		return null;

	}

}
