package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.dao.CommonMenuDao;
import com.dao.ProblemDao;
import com.dao.TeamDao;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class ProblemController {
	private static final Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);
	private static final String classname = "../problem/list";
	
	@RequestMapping(value = "/problem/list", method = RequestMethod.GET)
	public String problemlist(Locale locale, Model model,HttpServletRequest request) throws SQLException {
		logger.info("Getting Problem List", locale);	
		

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		ProblemDao dao = new ProblemDao();
		TeamDao teamdao=new TeamDao();
		
		List<Map<String, Object>> list = null;
		
		List<Map<String, Object>> teamlist = null;
		
		try {
			list = dao.getProblemList();
			teamlist=teamdao.getTeamList();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Problem List");
		model.addAttribute("data_list", list);
		model.addAttribute("teamlist", teamlist);


		return "problem/list";
	}

	@ResponseBody
	@RequestMapping(value = "/problem/JSlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> serviceTypelist() throws SQLException {

		ProblemDao dao = new ProblemDao();
		List<Map<String, Object>> list = null;
		try {
			list = dao.getProblemList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogproblem")
	public String dialogservice(Model model, Locale locale) {

		return "problem/problemdialog";

	}

	@RequestMapping(value = "/problem/saveJS", method = RequestMethod.POST)
	@ResponseBody
	public String saveJSService(String DESCRIPTION, String SERVICE_TYPE_ID, String ACTIVE_DT, String DEACTIVE_DT,
			String ACTIVE_STATUS,String SUB_TEAM_CODE,HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		
		logger.info("Save Service {}.", locale);
		ProblemDao dao = new ProblemDao();

		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();

		model.addAttribute("fx", "Problem list ");
		model.addAttribute("userName", "NEPal");
		String msg = null;
		try {
			msg = dao.saveProblem(DESCRIPTION, SERVICE_TYPE_ID, ACTIVE_DT, DEACTIVE_DT, ACTIVE_STATUS, USER,SUB_TEAM_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/problem/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateService(String PROBLEM_ID, String DESCRIPTION, String SERVICE_TYPE_ID, String ACTIVE_DT,
			String DEACTIVE_DT, String ACTIVE_STATUS,String SUB_TEAM_CODE, HttpServletRequest request,HttpSession session, Model model, Locale locale) throws SQLException {

		logger.info("Updata Service {}.", locale);

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

				if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
				}

		ProblemDao dao = new ProblemDao();

		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();
		model.addAttribute("fx", "Service controller list ");
		model.addAttribute("userName", "NEPal");
		String msg = null;
		try {
			msg = dao.updateProblem(PROBLEM_ID, DESCRIPTION, SERVICE_TYPE_ID, ACTIVE_DT, DEACTIVE_DT, ACTIVE_STATUS,
					USER,SUB_TEAM_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/problem/delete", method = RequestMethod.POST)
	@ResponseBody
	public String problemDelete(String PROBLEM_ID, Model model,HttpServletRequest request, Locale locale) throws SQLException {
		logger.info("delete service", locale);
		
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	
		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		
		
		ProblemDao dao = new ProblemDao();

		String msg = null;
		try {
			msg = dao.DeleteProblem(PROBLEM_ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

}
