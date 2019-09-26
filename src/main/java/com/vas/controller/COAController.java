/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.COADao;
import com.dao.CommonMenuDao;
import com.model.MenuAccess;
import com.model.UserInformationModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nabin
 */
@Controller
public class COAController {

	@RequestMapping(value = "/coa/list", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpServletRequest request, HttpSession session)
			throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		String url = request.getServletPath();

//menu code should know before validate
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), url);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
		model.addAttribute("fx", "Chart Of Accounts");
		return "coa/list";

	}

	@ResponseBody
	@RequestMapping(value = "/coa/getCOAList", method = RequestMethod.GET)
	public List<Map<String, Object>> getCOAList(Locale locale, Model model, HttpSession session) throws SQLException {
		COADao dao = new COADao();
		return dao.getCOAlist();

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogCOA")
	public String dialogcp(Model model, Locale locale) {
		return "coa/modal";
	}

}
