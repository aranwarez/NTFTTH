/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.COADao;
import com.dao.SpDao;
import com.dao.SpServiceDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SpController {
	private static final Logger logger = LoggerFactory.getLogger(SpController.class);

	@RequestMapping(value = "/sp/list", method = RequestMethod.GET)
	public String splist(Locale locale, Model model) throws SQLException {
		logger.info("Getting Service Provider List", locale);
		SpDao dao = new SpDao();
		List<Map<String, Object>> list = null;
		try {
			list = dao.getSpList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Service Provider List");
		model.addAttribute("data_list", list);

		COADao COA = new COADao();
		model.addAttribute("COA_list", COA.getCOAlist());

		return "sp/list";
	}

	@RequestMapping(value = "/sp/saveJS", method = RequestMethod.POST)
	@ResponseBody
	public String saveJSSp(String SP_CODE, String SP_NAME, String SHORT_CODE, String ADDRESS, String CONTACT_PERSON,
			String TEL_NO, String MOBILE_NO, String EMAIL, String SLDG_CODE, String PAN_NO, String CONTRACT_DT,
			String CONTRACT_TER_DT, String SERVICE_START_DT, String BANK_INFORMATION, Model model, Locale locale) {

		logger.info("Save service provider {}.", locale);
		SpDao dao = new SpDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
		String USER = "NEpal";
		model.addAttribute("fx", "Sp controller list ");
		model.addAttribute("userName", "NEPal");
		String msg = null;
		try {
			msg = dao.saveSp(SP_CODE, SP_NAME, SHORT_CODE, ADDRESS, CONTACT_PERSON, TEL_NO, MOBILE_NO, EMAIL, SLDG_CODE,
					PAN_NO, CONTRACT_DT, CONTRACT_TER_DT, SERVICE_START_DT, BANK_INFORMATION, USER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogsp")
	public String dialogsp(Model model, Locale locale) {
		return "sp/spdialog";
	}

	@RequestMapping(value = "/sp/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateSp(String SP_CODE, String SP_NAME, String SHORT_CODE, String ADDRESS, String CONTACT_PERSON,
			String TEL_NO, String MOBILE_NO, String EMAIL, String SLDG_CODE, String PAN_NO, String CONTRACT_DT,
			String CONTRACT_TER_DT, String SERVICE_START_DT, String BANK_INFORMATION, Model model, Locale locale) {

		logger.info("Updata Service Provider {}.", locale);
		SpDao dao = new SpDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
		String USER = "NEpal";
		model.addAttribute("fx", "Sp controller list ");
		model.addAttribute("userName", "NEPal");
		String msg = null;
		try {
			msg = dao.updateSp(SP_CODE, SP_NAME, SHORT_CODE, ADDRESS, CONTACT_PERSON, TEL_NO, MOBILE_NO, EMAIL,
					SLDG_CODE, PAN_NO, CONTRACT_DT, CONTRACT_TER_DT, SERVICE_START_DT, BANK_INFORMATION, USER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/sp/delete", method = RequestMethod.POST)
	@ResponseBody
	public String spDelete(String SP_CODE, Model model, Locale locale) {
		logger.info("delete service provider", locale);
		SpDao dao = new SpDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
		String msg = null;
		try {
			msg = dao.DeleteSp(SP_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	// getting list of all SP wise Service List
	@ResponseBody
	@RequestMapping(value = "/sp/getSPServiceList", method = RequestMethod.GET)
	public List<Map<String, Object>> getSPtargetist(String SP_CODE, Locale locale, Model model, HttpSession session)
			throws SQLException {
		SpServiceDao dao = new SpServiceDao();
		return dao.getSpServiceList(SP_CODE);
	}

}
