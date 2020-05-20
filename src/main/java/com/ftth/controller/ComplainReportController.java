package com.ftth.controller;

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

import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.MServiceTypeDao;
import com.dao.RegionDao;
import com.dao.TeamDao;
import com.dao.UserDao;
import com.dao.UserTeamDao;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class ComplainReportController {
	private static final Logger logger = LoggerFactory.getLogger(ComplainReportController.class);

	private static final String classname = "../complain-summary/report";

	@RequestMapping(value = "/complain-summary/report", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		RegionDao dao = new RegionDao();
		List<Region> regionlist = null;
		
		MServiceTypeDao ServiceTypedao= new MServiceTypeDao();
		List<Map<String, Object>> servicetypelist=null;
		CommonDateDao DAT = new CommonDateDao();
		TeamDao teamdao=new TeamDao();
		List<Map<String, Object>> teamlist = null;
		
		List<Map<String, Object>> webteamlist = null;
		List<Map<String, Object>> levelcontrollist=null;
		
		try {
			regionlist = dao.getlist();
			servicetypelist=ServiceTypedao.getServiceTypeList();
			teamlist=teamdao.getTeamList();
			webteamlist=teamdao.getWebTeamList();
			
			model.addAttribute("Date_list", DAT.getDateList());
			model.addAttribute("userteamlist", UserTeamDao.getModeList(user.getUSER_ID()));
			model.addAttribute("teamlist", teamlist);
			model.addAttribute("webteamlist", webteamlist);
			
			
			levelcontrollist=UserDao.getUserDetailByOfficeCode(user.getOFFICE_CODE());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Compalin Handling Summary Report");
		model.addAttribute("regionlist", regionlist);
		
		model.addAttribute("servicetypelist", servicetypelist);
		model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());        
        model.addAttribute("levelcontrollist", levelcontrollist);
		
        System.out.println(user.getUSER_LEVEL());
        
		return "complainsummary-report/list";

	}
}
