package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.dao.CommonMenuDao;
import com.dao.RoleDao;
import com.model.MenuAccess;
import com.model.Role;
import com.model.UserInformationModel;

@Controller
public class RoleController {
	private static final String classname = "../role/list";
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	RoleDao dao = new RoleDao();

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/role/list", method = RequestMethod.GET)
	public String rolelist(Locale locale, Model model, HttpSession session, HttpServletRequest request) throws SQLException {
		logger.info("Welcome home! The client locale is {}.", locale);
		Role u1 = new Role();
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		
		List<Role> list = null;
		try {
			list = dao.getlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("fx", "Role List");
		model.addAttribute("data_list", list);
		model.addAttribute("data_list", list);
		return "role/list";
	}

	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	public String saveRole(@Validated Role role, Model model, Locale locale, HttpSession session,
			HttpServletRequest request) throws SQLException {
		logger.info("Trying to save new role by user id:", locale);
		RoleDao dao = new RoleDao();
		
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		
		
		if(session.getAttribute("UserList")!=null) {
		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		role.setUSER(userinfo.getUSER_ID());
		}
		

		model.addAttribute("userName", role.getROLE_CODE());
		String msg = null;
		try {
			msg = dao.saveRole(role);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (msg.toUpperCase().substring(0, 3).equals("SUC")) {
			model.addAttribute("sucess", msg);
		} else {
			model.addAttribute("error", msg);
		}
		return "redirect:/role/list";
	}

	@RequestMapping(value = "/role/delete", method = RequestMethod.POST)
	@ResponseBody
	public String roleDelete(String ROLE_CODE, Model model, Locale locale,HttpServletRequest request) throws SQLException {
		logger.info("delete role", locale);
		RoleDao dao = new RoleDao();
		
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		System.out.println(menuaccess.getDELETE_FLAG());
		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}


		

		String msg = null;
		try {
			msg = dao.deleteRole(ROLE_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogrole")
	public String dialogrole(Model model, Locale locale) {
		return "role/roledialog";

	}

	@RequestMapping(value = "/role/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateRole(String ROLE_CODE, String DESCRIPTION, Model model, Locale locale, HttpSession session,HttpServletRequest request) throws SQLException {

		logger.info("Welcome home! The client locale is {}.", locale);

		RoleDao dao = new RoleDao();
		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");

		model.addAttribute("fx", "Role controller list ");
		
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		

		String msg = null;
		try {

			msg = dao.updateRole(ROLE_CODE, DESCRIPTION, userinfo.getUSER_ID());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

}
