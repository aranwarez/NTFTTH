/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.COADao;
import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.SpDao;
import com.dao.VASServiceDao;
import com.model.Menu;
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

	@RequestMapping(value = "/COA/list", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpServletRequest request, HttpSession session)
			throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		String url = request.getServletPath();
		List<Menu> list = null;

//menu code should know before validate
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), url);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
//		model.addAttribute("EDIT_FLAG", menuaccess.getEDIT_FLAG());
//		model.addAttribute("DELETE_FLAG", menuaccess.getDELETE_FLAG());
//		model.addAttribute("POST_FLAG", menuaccess.getPOST_FLAG());
//		model.addAttribute("CANCEL_FLAG", menuaccess.getCANCEL_FLAG());
		model.addAttribute("fx", "Chart Of Accounts");
		model.addAttribute("data_list", list);

		VASServiceDao VASSER = new VASServiceDao();
		model.addAttribute("VASSer_list", VASSER.getVasServiceList());
		CommonDateDao DAT = new CommonDateDao();
		model.addAttribute("Date_list", DAT.getDateList());
		CommonDateDao mon = new CommonDateDao();
		model.addAttribute("Mon_list", mon.getNepMonthList());
		SpDao sp = new SpDao();
		model.addAttribute("Sp_list", sp.getSpList());

		return "coa/list";

	}

	@ResponseBody
	@RequestMapping(value = "/COA/getCOAList", method = RequestMethod.GET)
	public List<Map<String, Object>> getCOAList(Locale locale, Model model, HttpSession session) throws SQLException {
		COADao dao = new COADao();
		return dao.getCOAlist();

	}

}
