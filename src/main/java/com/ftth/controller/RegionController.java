package com.ftth.controller;

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

import com.dao.RegionDao;
import com.model.Menu;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class RegionController {
	private static final Logger logger = LoggerFactory.getLogger(RegionController.class);
	RegionDao dao = new RegionDao();
	Region m = new Region();

	@RequestMapping(value = "/region/list", method = RequestMethod.GET)
	public String rolelist(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {
			return "redirect:/login";
		}
		List<Region> list = null;

		try {
			list = dao.getlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("pgtitle", "Region Entry");
		model.addAttribute("fx", "Region List");
		model.addAttribute("data_list", list);

		return "region/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogregion")
	public String dialogrole(Model model, Locale locale) {
		return "region/regiondialog";

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "region/save")
	public String saveRegion(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) {
		logger.info(" user id:", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return "Session has been expired";
		}
		String REGION_CODE = request.getParameter("REGION_CODE");
		String DESCRIPTION = request.getParameter("DESCRIPTION");
		m.setREGION_CODE(REGION_CODE);
		m.setDESCRIPTION(DESCRIPTION);
		m.setUSER(user.getUSER_ID());

		String msg = null;
		try {
			msg = dao.saveRegion(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "region/update")
	public String updateRegion(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		logger.info(" user id:", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return "Session has been expired";
		}
		String REGION_CODE = request.getParameter("REGION_CODE");
		String DESCRIPTION = request.getParameter("DESCRIPTION");
		m.setREGION_CODE(REGION_CODE);
		m.setDESCRIPTION(DESCRIPTION);
		m.setUSER(user.getUSER_ID());

		String msg = null;
		try {
			msg = dao.UpdateRegion(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "region/delete")
	public String deleteRegion(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		logger.info(" user id:", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return "Session has been expired";
		}
		String REGION_CODE = request.getParameter("REGION_CODE");

		String msg = null;
		try {
			msg = dao.deleteRegion(REGION_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(value = "/region/getlist", method = RequestMethod.GET)
	public List<Region> regionGetlist(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {

		}
		List<Region> list = null;

		try {
			list = dao.getlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
