/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.COADao;
import com.dao.SpDao;
import com.dao.SpTargetDao;

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
public class SPTargetController {
	private static final Logger logger = LoggerFactory.getLogger(SPTargetController.class);

	@RequestMapping(value = "/sp/sptarget", method = RequestMethod.GET)
	public String splist(Locale locale, Model model) throws SQLException {
		logger.info("Getting Service Provider Target List", locale);
		SpDao dao = new SpDao();
		List<Map<String, Object>> list = null;
		try {
			list = dao.getSpList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Service Provider Target List");
		model.addAttribute("data_list", list);

		COADao COA = new COADao();
		model.addAttribute("COA_list", COA.getCOAlist());

		return "sp/sptarget";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/sptargetdialog")
	public String dialogservice(Model model, Locale locale) {
		return "sp/sptargetdialog";

	}

	// getting list of all SP target
	@ResponseBody
	@RequestMapping(value = "/sp/getSPTargetList", method = RequestMethod.GET)
	public List<Map<String, Object>> getSPtargetist(String SP_CODE, Locale locale, Model model,
			HttpSession session) throws SQLException {
		SpTargetDao dao = new SpTargetDao();
		return dao.getSpTargetList(SP_CODE);
	}

	@RequestMapping(value = "/sp/savesptargetJS", method = RequestMethod.POST)
	@ResponseBody
	public String saveJSSp(String SP_CODE, String EFFECTIVE_DT, String REVENUE_TARGET, String MINIMUM_GUARENTEE,
			Model model, Locale locale) {

		logger.info("Save service provider {}.", locale);
		SpTargetDao dao = new SpTargetDao();
		String USER = "NEpal";
		String msg = null;
		try {
			msg = dao.saveSpTarget(SP_CODE, EFFECTIVE_DT, REVENUE_TARGET, MINIMUM_GUARENTEE, USER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

}
