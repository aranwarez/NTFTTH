package com.vas.controller;

import java.io.IOException;
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

import com.dao.BankDao;
import com.dao.CashSaleDao;
import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.ItemDao;
import com.dao.SpDao;
import com.dao.VASCommonDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class CashSaleBillController {

	@RequestMapping(value = "/cashsale/bill", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpServletRequest request, HttpSession session)
			throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		String url = request.getServletPath();
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), url);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
		model.addAttribute("fx", "Sales Bill For SMS & Line Connection");

		// getting list of paramater required
		SpDao sp = new SpDao();
		model.addAttribute("Sp_list", sp.getSpList());
		BankDao bank = new BankDao();

		model.addAttribute("bank_list", bank.getBankListforCC(user.getCC_CODE()));
		CommonDateDao DAT = new CommonDateDao();
		model.addAttribute("Date_list", DAT.getDateList());

		return "cashsale/list";

	}

	@ResponseBody
	@RequestMapping(value = "/cashsale/getitemlist", method = RequestMethod.GET)
	public List<Map<String, Object>> getCOAList(Locale locale, Model model, HttpSession session) throws SQLException {
		ItemDao dao = new ItemDao();
		return dao.getItemList();

	}

	@ResponseBody
	@RequestMapping(value = "/cashsale/getitemtariff", method = RequestMethod.GET)
	public String getItemTariff(String Itemcode, Locale locale, Model model, HttpSession session) throws SQLException {
		VASCommonDao dao = new VASCommonDao();
		return dao.getTSCAmt(Itemcode);

	}

	@ResponseBody
	@RequestMapping(value = "/cashsale/savebill", method = RequestMethod.POST)
	public String saveBill(String SP_CODE,String nepdate,String BANK_CODE,String REMARKS, String AMT, String DATA, Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			List<Map<String, Object>> myarray = mapper.readValue(DATA,
			        new TypeReference<List<Map<String, Object>>>() {
			});
			CashSaleDao dao=new CashSaleDao();
		return 	dao.SaveBill(myarray, SP_CODE, nepdate, BANK_CODE, REMARKS, AMT, user.getUSER_ID(), user.getCC_CODE());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		
		
	}

}
