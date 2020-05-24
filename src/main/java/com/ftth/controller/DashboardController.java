package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.dao.CommonMenuDao;
import com.dashboard.dao.dashboardquery;
import com.model.MenuAccess;
import com.model.Role;
import com.model.UserInformationModel;

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
		//System.out.println(id);
		// return "NTC/dashboard/index";
		return "dashboard/" + id;
	}

	@ResponseBody
	@RequestMapping(value = "/charts/srvreveneue", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> srvwiserevenue(Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		dashboardquery dao=new dashboardquery();
		return dao.getSRVwiseRevenue(user.getUSER_ID(),user.getUSER_LEVEL());
	}
	
	@ResponseBody
	@RequestMapping(value = "/charts/srvpayable", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> srvwisepayable(Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		dashboardquery dao=new dashboardquery();
		return dao.getSRVwisepayable(user.getUSER_ID(),user.getUSER_LEVEL());
	}
	
	@ResponseBody
	@RequestMapping(value = "/charts/srvmonthly", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> srvwisemonthly(Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		dashboardquery dao=new dashboardquery();
		return dao.getSolvedvsUnsovled(user.getUSER_ID(),user.getUSER_LEVEL());
	}
	
	@RequestMapping(value = "/subTeamSolveUnsolve", method = RequestMethod.GET)
	public String subTeamSolveUnsolve(Locale locale, Model model, HttpSession session, HttpServletRequest request) throws SQLException {
		logger.info("Welcome subTeamSolveUnsolve ! The client locale is {}.", locale);
		
		model.addAttribute("menu_name", "Sub Team Solve Unsolve ");
		
		return "chart/bar/subTeamSolveUnsolve";
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "bar-charts/subTeamSolveUnsolve",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> subTeamSolveUnsolve(HttpServletRequest request,HttpSession session) throws SQLException {
		
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			
		dashboardquery dao=new dashboardquery();
			
		String SUB_TEAM_CODE = request.getParameter("SUB_TEAM_CODE");
		String SERVICE_TYPE_ID = request.getParameter("SERVICE_TYPE_ID");
		String FROM_DT = request.getParameter("FROM_DT");
		String TO_DT = request.getParameter("TO_DT");
		
	
		return dao.subTeamSolveUnsolve(user.getUSER_ID(), SUB_TEAM_CODE, SERVICE_TYPE_ID, FROM_DT, TO_DT);


	}
	

}