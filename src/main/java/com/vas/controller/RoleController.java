package com.vas.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.RoleDao;
import com.model.Role;

@Controller
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	RoleDao dao = new RoleDao();
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/role/list", method = RequestMethod.GET)
	public String rolelist(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Role u1 = new Role();

		List<Role> list = null;
		try {
			list = dao.getlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("role list" + list.size());

		model.addAttribute("fx", "Role List");
		model.addAttribute("data_list", list);

		return "role/list";
	}

	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	public String login(@Validated Role role, Model model, Locale locale) {

		logger.info("Welcome home! The client locale is {}.", locale);

		RoleDao dao = new RoleDao();
		System.out.println("role code==" + role.getROLE_CODE());

		role.setUSER("NEpal");

		model.addAttribute("fx", "Role controller list ");
		model.addAttribute("userName", role.getROLE_CODE());

		String msg = null;
		try {

			msg = dao.saveRole(role);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("sucess", msg);

		return "redirect:/role/list";

	}

	@RequestMapping(value = "/role/delete", method = RequestMethod.POST)
	@ResponseBody
	public String roleDelete(String ROLE_CODE, Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);

		return "Sucess";

	}

	@RequestMapping(value = "/role/editrole", method = RequestMethod.POST, produces = "application/json")

	public Role edit(String ROLE_CODE, Model model, Locale locale) {

		logger.info("Welcome home! The client locale is {}.", locale);
		
	
		Role emp = null;
		try {
			emp = dao.getRole(ROLE_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(ROLE_CODE);
		model.addAttribute("emp", emp);

		return emp;
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogrole")

	public String dialogrole(Model model, Locale locale) {

		return "role/roledialog";
	}

}
