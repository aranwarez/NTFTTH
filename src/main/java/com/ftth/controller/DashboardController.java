package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.sl.usermodel.ObjectMetaData.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dashboard.dao.dashboardquery;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DashboardController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/dashboard/{id}", method = RequestMethod.GET)
	public String dashboard(Locale locale, Model model, @PathVariable String id) {
		model.addAttribute("fx", "");
		System.out.println(id);
		// return "NTC/dashboard/index";
		return "dashboard/" + id;
	}

	@ResponseBody
	@RequestMapping(value = "/charts/srvreveneue", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> srvwiserevenue(Locale locale, Model model, HttpSession session) throws SQLException {
		dashboardquery dao=new dashboardquery();
		return dao.getSRVwiseRevenue();
	}
	
	@ResponseBody
	@RequestMapping(value = "/charts/srvpayable", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> srvwisepayable(Locale locale, Model model, HttpSession session) throws SQLException {
		dashboardquery dao=new dashboardquery();
		return dao.getSRVwisepayable();
	}
	
	@ResponseBody
	@RequestMapping(value = "/charts/srvmonthly", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> srvwisemonthly(Locale locale, Model model, HttpSession session) throws SQLException {
		dashboardquery dao=new dashboardquery();
		return dao.test();
	}
	

}