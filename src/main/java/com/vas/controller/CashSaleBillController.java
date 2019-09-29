package com.vas.controller;

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
	private static final String classname="../cashsale/list";

	@RequestMapping(value = "/cashsale/bill", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpSession session)
			throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
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
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			throw new ResponseStatusException(
			           HttpStatus.FORBIDDEN, "Unauthorized");
			
		//	throw new ForbiddenException();
	//		model.addAttribute("fx", "Unauthorized Action for this role!!");
		//	return "/home";
		}
		
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
	
//Cash Sale Master Form
	
	@RequestMapping(value = "/cashsale/list", method = RequestMethod.GET)
	public String getMasterList(Locale locale, Model model, HttpSession session)
			throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
		model.addAttribute("fx", "Bill Master");

		// getting list of paramater required
		SpDao sp = new SpDao();
		model.addAttribute("Sp_list", sp.getSpList());
//		BankDao bank = new BankDao();
//
//		model.addAttribute("bank_list", bank.getBankListforCC(user.getCC_CODE()));
		CommonDateDao DAT = new CommonDateDao();
		model.addAttribute("Date_list", DAT.getDateList());

		return "cashsale/Master";

	}
	@ResponseBody
	@RequestMapping(value = "/cashsale/getMasterlist", method = RequestMethod.GET)
	public List<Map<String, Object>> getBillMaster(String S_NO, String FROM_DT, String TO_DT,String POST_FLAG,Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(
			           HttpStatus.FORBIDDEN, "Unauthorized");
		}
		CashSaleDao dao = new CashSaleDao();
		
		List<Map<String, Object>> data= dao.getReceiptList(S_NO, FROM_DT, TO_DT, POST_FLAG);
		return data;
	
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/cashsale/cancelbill", method = RequestMethod.POST)
	public String Cancelbill(String Transno,Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		System.out.println(menuaccess.getCANCEL_FLAG());
		if (menuaccess == null || menuaccess.getCANCEL_FLAG().equals("N")) {
			throw new ResponseStatusException(
			           HttpStatus.FORBIDDEN, "Unauthorized");
		}
		CashSaleDao dao = new CashSaleDao();
		
		return dao.DeleteReceipt(Transno, user.getUSER_ID());
		
	
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cashsale/dialog")
    public String dialogreceipt(Model model, Locale locale) {
        return "cashsale/Masterdialog";
    }
	
		
	@ResponseBody
	@RequestMapping(value = "/cashsale/getDetaillist", method = RequestMethod.GET)
	public List<Map<String, Object>> getBillDetail(String transno,Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(
			           HttpStatus.FORBIDDEN, "Unauthorized");
		}
		CashSaleDao dao = new CashSaleDao();
		
		return dao.getDetailList(transno);
		
	
	}



}
