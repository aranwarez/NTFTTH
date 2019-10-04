package com.vas.controller;

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
import com.model.UserInformationModel;

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
			Date TO_DATE = CommonDateDao.convertDateAD(request.getParameter("TO_DATE"));

			Map<String, Object> parameters = getMapParameters(request);
			parameters.put("pm_to_date", TO_DATE);
			UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
			parameters.put("pm_user", user.getUSER_ID());

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
	public ResponseEntity<byte[]> getRpt(HttpServletRequest request, HttpServletResponse response)
			throws JRException, IOException {
		logger.info("Preparing for report");
		try {
			InputStream jasperStream = this.getClass().getResourceAsStream("/Report/SpBalanceRep.jasper");
			// Map<String, Object> params = new HashMap<>();

			// Date FROM_DT = CommonDateDao.convertDateAD(request.getParameter("FROM_DT"));
			Date TO_DATE = CommonDateDao.convertDateAD(request.getParameter("TO_DATE"));

			Map<String, Object> parameters = getMapParameters(request);
			parameters.put("pm_to_date", TO_DATE);
			UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
			parameters.put("pm_user", user.getUSER_ID());

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
		}
		return null;
	}

	private Map<String, Object> getMapParameters(HttpServletRequest request) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pm_comp_name", "NEPAL TELECOM");
		parameters.put("pm_address", "HEAD OFFICE BHADRAKALI");
		String filterparam = "";
		if (request.getParameter("SP_CODE") != null) {
			filterparam = filterparam + "SP_CODE : " + request.getParameter("SP_CODE").toString();
			parameters.put("pm_sp_code", request.getParameter("SP_CODE"));
		}
		if (request.getParameter("TODATE") != null) {
			filterparam = filterparam + "To Date : " + request.getParameter("TODATE").toString();
		}

		parameters.put("pm_filter", filterparam);

		return parameters;

	}

}
