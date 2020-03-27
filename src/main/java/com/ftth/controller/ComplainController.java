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

	@ResponseBody
	@RequestMapping(value = "/cashsale/savebill", method = RequestMethod.POST)
	public String saveBill(String SP_CODE, String nepdate, String BANK_CODE, String REMARKS, String AMT, String DATA,
			Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");

			// throw new ForbiddenException();
			// model.addAttribute("fx", "Unauthorized Action for this role!!");
			// return "/home";
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			List<Map<String, Object>> myarray = mapper.readValue(DATA, new TypeReference<List<Map<String, Object>>>() {
			});
//			CashSaleDao dao=new CashSaleDao();
//		return 	dao.SaveBill(myarray, SP_CODE, nepdate, BANK_CODE, REMARKS, AMT, user.getUSER_ID(), user.getCC_CODE());
//			
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
