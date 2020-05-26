package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.ComplainDao;
import com.dao.MServiceTypeDao;
import com.dao.RegionDao;
import com.dao.TeamDao;
import com.dao.TicketHistroyDao;
import com.dao.UserDao;
import com.dao.UserTeamDao;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class TicketHistroyController {
	private static final Logger logger = LoggerFactory.getLogger(TicketHistroyController.class);
	private static final String classname = "../ticket-history/list";
	 @RequestMapping(value = "/ticket-history/list", method = RequestMethod.GET)
	    public String teamlist(Locale locale, Model model,HttpServletRequest request, HttpSession session) throws SQLException {

		 UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			logger.info("/ticket-histry seen by "+user.getUSER_ID(), locale);
			
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
				model.addAttribute("fx", "Unauthorized Page for this role!!");
				return "/home";
			}
			model.addAttribute("fx", "Trouble Ticket histry by Service No");
			
	        
			return "troubleticket/history";

	 }  
	 
	    @RequestMapping(value = "/ticket-history/fetch", method = RequestMethod.GET)
		public String  getTroubleTicketList(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model,Locale locale) throws SQLException {
			UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			
			logger.info("/history/by service"+user.getUSER_ID()+" srv no "+request.getParameter("MAIN_SRV_NO") , locale);
			
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
				model.addAttribute("fx", "Unauthorized Page for this role!!");
				return "/home";
			}
			
			
			
			String MAIN_SRV_NO = request.getParameter("srv_no");
			
			model.addAttribute("fx", "Trouble Tickets histry");
			List<Map<String, Object>> list = null;
			TicketHistroyDao dao=new TicketHistroyDao();			 
			try {				
				list = dao.getComplainBySrvNo(user.getUSER_ID(), MAIN_SRV_NO);		
				
				model.addAttribute("data_list", list);
     			model.addAttribute("srv_no", MAIN_SRV_NO);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "troubleticket/history";

		}
}
