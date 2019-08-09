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
import com.dao.RegionDao;
import com.model.AccCenter;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class AccCenterController {
	private static final Logger logger = LoggerFactory.getLogger(AccCenterController.class);

	AccCenterDao accdao = new AccCenterDao();
	RegionDao regiondao = new RegionDao();

	AccCenter m = new AccCenter();

	@RequestMapping(value = "/acccenter/list", method = RequestMethod.GET)
	public String rolelist(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("pgtitle", "Account Center Entry");
		model.addAttribute("fx", "Account Center List");

		return "acccenter/list";
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "response/acccenter")
	public List<AccCenter> responseAccCenter(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		logger.info(" user id:", locale);
		List<AccCenter> list = null;
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return list;
		}
		String REGION_CODE = request.getParameter("REGION_CODE");

		try {
			list = accdao.getAccCenterList(REGION_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogacccenter")
	public String dialogrole(Model model, Locale locale) {
		return "acccenter/acccenterdialog";

	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "acccenter/save")
	public String saveAccCenter(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		
		logger.info(" user id:", locale);
		
		List<AccCenter> list = null;
		
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return "Session has been expired";
		}		
        AccCenter m = new AccCenter();
        
    
        String USER = user.getUSER_ID();
        
        m.setACC_CEN_CODE(request.getParameter("ACC_CEN_CODE"));
        m.setDESCRIPTION(request.getParameter("DESCRIPTION"));
        m.setREGION_CODE(request.getParameter("REGION_CODE"));
        m.setERP_ACC_CEN_CD(request.getParameter("ERP_ACC_CEN_CD"));
        m.setUSER(USER);
        
        String msg = null;
        try {
			msg = accdao.addAccenter(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return msg;

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "acccenter/update")
	public String updateAccCenter(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		
		logger.info(" user id:", locale);
		
		List<AccCenter> list = null;
		
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return "Session has been expired";
		}		
        AccCenter m = new AccCenter();
        
    
        String USER = user.getUSER_ID();
        
        m.setACC_CEN_CODE(request.getParameter("ACC_CEN_CODE"));
        m.setDESCRIPTION(request.getParameter("DESCRIPTION"));
        m.setREGION_CODE(request.getParameter("REGION_CODE"));
        m.setERP_ACC_CEN_CD(request.getParameter("ERP_ACC_CEN_CD"));
        m.setUSER(USER);
        
        String msg = null;
        try {
			msg = accdao.updateAcc(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return msg;

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "acccenter/delete")
	public String deleteAccCenter(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		
		logger.info(" acc center delete id:", locale);
		
		
		
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return "Session has been expired";
		}		

		String msg = null;
        try {
			msg = accdao.deleteAccenter(request.getParameter("ACC_CEN_CODE"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return msg;

	}
	

}
