/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.BankDao;
import com.dao.BankListDao;
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
public class BankController {
	private static final String classname="../bank/list";

	@RequestMapping(value = "/bank/list", method = RequestMethod.GET)
	public String BankList(Locale locale, Model model, HttpServletRequest request, HttpSession session)
			throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		//String url = request.getServletPath();

//menu code should know before validate
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		BankDao dao=new BankDao();
		model.addAttribute("BANK_LIST", dao.getBankList());
		model.addAttribute("MENU_ACCESS", menuaccess);
		model.addAttribute("fx", "Bank Setup");
		return "bank/list";
	}
	
	//get out bank list
	@ResponseBody
	@RequestMapping(value = "/bank/getBankList", method = RequestMethod.GET)
	public List<Map<String, Object>> getOutbankList(Locale locale, Model model, HttpSession session,String TRANSCD) throws SQLException {
		if(TRANSCD.equals("BANKG")) {
		BankListDao dao=new BankListDao();
		return dao.getBankList();
		}
		else {
			BankDao BAN = new BankDao();
		return BAN.getBankList();
		}
			

	}

	

	@ResponseBody
	@RequestMapping(value = "/bank/getCOAList", method = RequestMethod.GET)
	public List<Map<String, Object>> getCOAList(Locale locale, Model model, HttpSession session) throws SQLException {
		COADao dao = new COADao();
		return dao.getCOAlist();

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogbank")
	public String dialogcp(Model model, Locale locale) {
		return "bank/dialog";
	}

}
