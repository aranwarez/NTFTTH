package com.vas.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.AccCenterDao;
import com.dao.CollCenterDao;
import com.model.AccCenter;
import com.model.CollCenter;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class CcController {
	private static final Logger logger = LoggerFactory.getLogger(CcController.class);
	
	@RequestMapping(value = "/cc/list", method = RequestMethod.GET)
	public String cclist(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("pgtitle", "Collection Center Entry");
		model.addAttribute("fx", "Collection Center List");

		return "cc/list";
	}
	
	

	@RequestMapping(method = RequestMethod.GET, value = "dialogcc")
	public String dialogrole(Model model, Locale locale) {
		return "cc/ccdialog";
	}
	
	@ResponseBody
	@RequestMapping(value = "/acccenter/getlist", method = RequestMethod.GET)
	public List<AccCenter> accCenterGetlist(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
	
		
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {

		}
		List<AccCenter> list = null;
		AccCenterDao accdao=new AccCenterDao();
		try {
			list = accdao.getAccCenterList(request.getParameter("REGION_CODE"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	@ResponseBody
	@RequestMapping(value = "/cc/getlist", method = RequestMethod.GET)
	public List<CollCenter> ccGetlist(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
	
		
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
	
		List<CollCenter> list = null;
		
		CollCenterDao ccdao=new CollCenterDao();
		
		try {
			list = ccdao.getCC(request.getParameter("ACC_CEN_CODE"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	
}
