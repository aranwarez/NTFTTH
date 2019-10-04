package com.vas.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.BankDao;
import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.SpDao;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class ReportMenuController {
private static final String classname="../report/list";
	
	@RequestMapping(value = "/report/ledger", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpServletRequest request)
			throws Exception {
		String url = request.getServletPath();
        
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), ".."+url);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
		model.addAttribute("fx", "Report for Ledger");

		// getting list of paramater required
		SpDao sp = new SpDao();
		model.addAttribute("Sp_list", sp.getSpList());
		BankDao bank = new BankDao();

		CommonDateDao DAT = new CommonDateDao();
		model.addAttribute("Date_list", DAT.getDateList());

		return "report/ledger";

	}
}
