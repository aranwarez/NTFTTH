package com.ftth.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.RegionDao;
import com.dao.TeamDao;
import com.dao.UserDao;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class ReportMenuController {

	private static final String classname = "../report/dispatch";
	private static final Logger logger = LoggerFactory.getLogger(ReportMenuController.class);

	@RequestMapping(value = "/report/dispatch", method = RequestMethod.GET)
	public String reportdispatch(Locale locale, Model model, HttpServletRequest request) throws Exception {

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		RegionDao dao = new RegionDao();
		List<Region> regionlist = null;

		CommonDateDao DAT = new CommonDateDao();
		TeamDao teamdao = new TeamDao();

		List<Map<String, Object>> webteamlist = null;
		List<Map<String, Object>> levelcontrollist = null;

		try {
			regionlist = dao.getlistByUserFDC(user.getUSER_ID(), user.getUSER_LEVEL());
			// regionlist = dao.getlist();
			webteamlist = teamdao.getWebTeamList();

			model.addAttribute("Date_list", DAT.getDateList());
			model.addAttribute("webteamlist", webteamlist);

			levelcontrollist = UserDao.getUserDetailByOfficeCode(user.getOFFICE_CODE());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Outside Dispatch Report");
		model.addAttribute("regionlist", regionlist);

		model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());
		model.addAttribute("levelcontrollist", levelcontrollist);

		return "report/dispatch";

	}

	@RequestMapping(value = "/report/complainsummary", method = RequestMethod.GET)
	public String reportcomplainsummary(Locale locale, Model model, HttpServletRequest request) throws Exception {
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		logger.info("/complain-summary/report by user" + user.getUSER_ID(), locale);

		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), "../report/complainsummary");
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		RegionDao dao = new RegionDao();
		List<Region> regionlist = null;

		CommonDateDao DAT = new CommonDateDao();
		TeamDao teamdao = new TeamDao();

		List<Map<String, Object>> webteamlist = null;
		List<Map<String, Object>> levelcontrollist = null;

		try {
			regionlist = dao.getlistByUserFDC(user.getUSER_ID(), user.getUSER_LEVEL());
			
			webteamlist = teamdao.getWebTeamList();

			model.addAttribute("Date_list", DAT.getDateList());
			model.addAttribute("webteamlist", webteamlist);

			levelcontrollist = UserDao.getUserDetailByOfficeCode(user.getOFFICE_CODE());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Complain summary");
		model.addAttribute("regionlist", regionlist);

		model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());
		model.addAttribute("levelcontrollist", levelcontrollist);

		return "report/complainsummary";

	}

}
