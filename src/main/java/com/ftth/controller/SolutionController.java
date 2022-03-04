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
import com.dao.SolutionDao;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class SolutionController {
	private static final Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);
	private static final String classname = "../solution/list";
	
	@RequestMapping(value = "/solution/list", method = RequestMethod.GET)
	public String solutionlist(Locale locale, Model model,HttpServletRequest request) throws SQLException {
	//	logger.info("Getting Problem List", locale);	
		

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		SolutionDao dao = new SolutionDao();
		
		
		List<Map<String, Object>> list = null;
		
		
		try {
			list = dao.getProblemList();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Solution List for Problems");
		model.addAttribute("data_list", list);
		

		return "solution/list";
	}

	@ResponseBody
	@RequestMapping(value = "/solution/JSlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> serviceTypelist() throws SQLException {

		SolutionDao dao = new SolutionDao();
		List<Map<String, Object>> list = null;
		try {
			list = dao.getProblemList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogsolution")
	public String dialogservice(Model model, Locale locale) {

		return "solution/solutiondialog";

	}

	@RequestMapping(value = "/solution/saveJS", method = RequestMethod.POST)
	@ResponseBody
	public String saveJSService(String DESCRIPTION, String SERVICE_TYPE_ID, String PROBLEM_ID,
			String ACTIVE_STATUS,HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		
		SolutionDao dao = new SolutionDao();

		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();

		model.addAttribute("fx", "Problem list ");
		String msg = null;
		try {
			msg = dao.saveSolution(DESCRIPTION, SERVICE_TYPE_ID, ACTIVE_STATUS, USER, PROBLEM_ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/solution/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateProblem(String PROBLEM_ID, String DESCRIPTION, String SERVICE_TYPE_ID, String SOLUTION_ID,
			String ACTIVE_STATUS, HttpServletRequest request,HttpSession session, Model model, Locale locale) throws SQLException {

		logger.info("Updata Service {}.", locale);

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

				if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
				}

		SolutionDao dao = new SolutionDao();

		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();
		model.addAttribute("fx", "Service controller list ");
		String msg = null;
		try {
			msg = dao.updateSolution(PROBLEM_ID, DESCRIPTION, SERVICE_TYPE_ID, SOLUTION_ID, ACTIVE_STATUS, USER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/solution/delete", method = RequestMethod.POST)
	@ResponseBody
	public String problemDelete(String PROBLEM_ID, Model model,HttpServletRequest request, Locale locale) throws SQLException {
		logger.info("delete service", locale);
		
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	
		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		
		
		SolutionDao dao = new SolutionDao();

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
