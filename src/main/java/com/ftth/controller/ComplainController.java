package com.ftth.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.MServiceDao;
import com.dao.VASCommonDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.MenuAccess;
import com.model.UserInformationModel;
import com.soap.dao.CrmDao;

@Controller
public class ComplainController {
	private static final String classname = "../complain/list";

	@RequestMapping(value = "/complain/list", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpSession session) throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
		model.addAttribute("fx", "Register New Complaint ");

		// getting list of paramater required

		// model.addAttribute("bank_list", bank.getBankListforCC(user.getCC_CODE()));
		CommonDateDao DAT = new CommonDateDao();
		model.addAttribute("Date_list", DAT.getDateList());

		MServiceDao services=new MServiceDao();
		model.addAttribute("Services", services.getServiceList());
		
		
		return "/complain/list";

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getCustomerInfo", method = RequestMethod.GET)
	public String getItemTariff(String info, String infotype, Locale locale, Model model, HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		CrmDao dao = new CrmDao();
		try {
			String msg = dao.getCustomerDetail(info, infotype);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/complain/dialog")
	public String dialogrole(Model model, Locale locale) {
		return "complain/dialog";

	}

	

}
