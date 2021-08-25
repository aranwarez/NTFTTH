package com.ftth.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommonDateDao;
import com.dao.ReportCountDao;
import com.model.UserInformationModel;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import util.DbCon;

@SuppressWarnings("deprecation")
@Controller
public class ReportController {

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "Report", method = RequestMethod.POST)
	@ResponseBody
	public void getRpt(HttpServletResponse response, HttpServletRequest request) throws JRException, IOException {
		logger.info("Preparing for report download");
		try {
			InputStream jasperStream = this.getClass()
					.getResourceAsStream("/Report/" + request.getParameter("reportname") + ".jasper");
			// Map<String, Object> params = new HashMap<>();
			// Date FROM_DT = CommonDateDao.convertDateAD(request.getParameter("FROM_DT"));
			// Date TO_DATE = CommonDateDao.convertDateAD(request.getParameter("TO_DATE"));

			Map<String, Object> parameters = getMapParameters(request);
			// parameters.put("pm_to_date", TO_DATE);
			// UserInformationModel user = (UserInformationModel)
			// request.getSession().getAttribute("UserList");
			// parameters.put("pm_user", user.getUSER_ID());

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbCon.getConnection());
			String reporttype = request.getParameter("reporttype");

			if (reporttype.equalsIgnoreCase("pdf")) {
				OutputStream outStream = response.getOutputStream();
				response.setContentType("application/x-pdf");
				response.setHeader("Content-disposition", "inline; filename=Report.pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

			} else if (reporttype.equalsIgnoreCase("XLS")) {
				response.setContentType("application/xls");
				response.setHeader("Content-Disposition", "inline; filename=\"report.xls\"");
				JRExporter exporter = new JRXlsExporter();
				OutputStream ouputStream = response.getOutputStream();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
				exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				exporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
				exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
						Boolean.TRUE);

				exporter.exportReport();
				ouputStream.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
	}

	@RequestMapping(value = "ReportView", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getRpt(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("Generating RPt" + request.getParameter("reportname"));

			InputStream jasperStream = this.getClass()
					.getResourceAsStream("/Report/" + request.getParameter("reportname") + ".jasper");
			// InputStream jasperStream =
			// this.getClass().getResourceAsStream("/Report/SpBalanceRep.jasper");
			// Map<String, Object> params = new HashMap<>();

			// Date FROM_DT = CommonDateDao.convertDateAD(request.getParameter("FROM_DT"));
			Map<String, Object> parameters = getMapParameters(request);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbCon.getConnection());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.add("content-disposition", "inline;filename=" + "Report.pdf");

			// to download pdf automatically
			// headers.setContentDispositionFormData("attachment", fileName);
			// headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			ResponseEntity<byte[]> responseview = new ResponseEntity<>(
					JasperExportManager.exportReportToPdf(jasperPrint), headers, HttpStatus.OK);
			return responseview;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		} finally {
			if (request.getParameter("reportname").equals("FTTHDispatch")) {
				ReportCountDao dao = new ReportCountDao();
				UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");

				try {
					dao.DispatchReportCountlog(user.getUSER_ID(), request.getParameter("REGION_CODE"),
							request.getParameter("ZONE_CODE"), request.getParameter("DISTRICT_CODE"),
							request.getParameter("OFFICE_CODE"), request.getParameter("QFROM_DT"),
							request.getParameter("QTO_DT"), request.getParameter("WEBTEAMCODE"));
				} catch (SQLException e) {
					System.err.println("Error inserting count for Dispatch report");
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	private Map<String, Object> getMapParameters(HttpServletRequest request) throws SQLException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pm_comp_name", "NEPAL TELECOM");
		parameters.put("pm_address", "HEAD OFFICE BHADRAKALI");

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		parameters.put("pm_user", user.getUSER_ID());

		String filterparam = "";

		if (request.getParameter("TRANS_NO") != null) {
			filterparam = filterparam + "TRANS_NO : " + request.getParameter("TRANS_NO").toString();
			parameters.put("pm_trans_no", request.getParameter("TRANS_NO"));
		}
		if (request.getParameter("REGION_CODE") != null && !request.getParameter("REGION_CODE").isEmpty()) {
			filterparam = filterparam + "REGION_CODE : " + request.getParameter("REGION_CODE").toString();
			parameters.put("pm_region", request.getParameter("REGION_CODE"));
		} else if (request.getParameter("REGION_CODE") == null) {
			filterparam = filterparam + "REGION_CODE : All ";
			parameters.put("pm_region", request.getParameter("REGION_CODE"));
		}
		if (request.getParameter("ZONE_CODE") != null && !request.getParameter("ZONE_CODE").isEmpty()) {
			filterparam = filterparam + "ZONE_CODE : " + request.getParameter("ZONE_CODE").toString();
			parameters.put("pm_zone", request.getParameter("ZONE_CODE"));
		} else if (request.getParameter("ZONE_CODE") == null || request.getParameter("ZONE_CODE").isEmpty()) {
			filterparam = filterparam + "ZONE_CODE : All ";
			parameters.put("pm_zone", request.getParameter("ZONE_CODE"));
		}
		if (request.getParameter("DISTRICT_CODE") != null && !request.getParameter("DISTRICT_CODE").isEmpty()) {
			filterparam = filterparam + "DISTRICT_CODE : " + request.getParameter("DISTRICT_CODE").toString();
			parameters.put("pm_district", request.getParameter("DISTRICT_CODE"));
		} else if (request.getParameter("DISTRICT_CODE") == null || request.getParameter("DISTRICT_CODE").isEmpty()) {
			filterparam = filterparam + "DISTRICT_CODE : All ";
			parameters.put("pm_district", request.getParameter("DISTRICT_CODE"));
		}
		if (request.getParameter("OFFICE_CODE") != null && !request.getParameter("OFFICE_CODE").isEmpty()) {
			filterparam = filterparam + "OFFICE_CODE : " + request.getParameter("OFFICE_CODE").toString();
			parameters.put("pm_office", request.getParameter("OFFICE_CODE"));
		} else if (request.getParameter("OFFICE_CODE") == null || request.getParameter("OFFICE_CODE").isEmpty()) {
			filterparam = filterparam + "OFFICE_CODE : All ";
			parameters.put("pm_office", request.getParameter("OFFICE_CODE"));
		}
		if (request.getParameter("WEBTEAMCODE") != null && !request.getParameter("WEBTEAMCODE").isEmpty()) {
			filterparam = filterparam + "WEBTEAMCODE : " + request.getParameter("WEBTEAMCODE").toString();
			parameters.put("pm_team_id", request.getParameter("WEBTEAMCODE"));
		} else if (request.getParameter("WEBTEAMCODE") == null || request.getParameter("WEBTEAMCODE").isEmpty()) {
			filterparam = filterparam + "TEAMCODE : All ";
			parameters.put("pm_team_id", request.getParameter("WEBTEAMCODE"));
		}

		if (request.getParameter("FRM_YEAR") != null) {
			filterparam = filterparam + "FRM_YEAR : " + request.getParameter("FRM_YEAR").toString();
			parameters.put("pm_frm_year", request.getParameter("FRM_YEAR"));
		}
		if (request.getParameter("FRM_MONTH") != null) {
			filterparam = filterparam + "FRM_MONTH : " + request.getParameter("FRM_MONTH").toString();
			parameters.put("pm_frm_month", request.getParameter("FRM_MONTH"));
		}
		if (request.getParameter("TO_YEAR") != null) {
			filterparam = filterparam + "TO_YEAR : " + request.getParameter("TO_YEAR").toString();
			parameters.put("pm_to_year", request.getParameter("TO_YEAR"));
		}
		if (request.getParameter("TO_MONTH") != null) {
			filterparam = filterparam + "TO_MONTH : " + request.getParameter("TO_MONTH").toString();
			parameters.put("pm_to_month", request.getParameter("TO_MONTH"));
		}
		if (request.getParameter("pm_within_days") != null) {
			filterparam = filterparam + "Within days : " + request.getParameter("pm_within_days").toString();
			parameters.put("pm_within_days", request.getParameter("pm_within_days"));
		}

		if (request.getParameter("QFROM_DT") != null && !request.getParameter("QFROM_DT").isEmpty()) {
			Date FROM_DATE = CommonDateDao.convertDateAD(request.getParameter("QFROM_DT"));
			parameters.put("pm_frm_dt", FROM_DATE);
			filterparam = filterparam + " From Date : " + request.getParameter("QFROM_DT").toString();
		} else if (request.getParameter("QFROM_DT") == null || request.getParameter("QFROM_DT").isEmpty()) {
		//	Date FROM_DATE = new Date();
		//	parameters.put("pm_frm_dt", FROM_DATE);
		//	filterparam = filterparam + " From Today";
		}

		if (request.getParameter("QTO_DT") != null && !request.getParameter("QTO_DT").isEmpty()) {
			Date TO_DATE = CommonDateDao.convertDateAD(request.getParameter("QTO_DT"));
			parameters.put("pm_to_dt", TO_DATE);
			filterparam = filterparam + " To Date : " + request.getParameter("QTO_DT").toString();
		} else if (request.getParameter("QTO_DT") == null || request.getParameter("QTO_DT").isEmpty()) {
			Date TO_DATE = new Date();
			parameters.put("pm_to_dt", TO_DATE);
			filterparam = filterparam + " To Date : Till Today";
		}

		parameters.put("pm_filter", filterparam);

		return parameters;

	}

}
