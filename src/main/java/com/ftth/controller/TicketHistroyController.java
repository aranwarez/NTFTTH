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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

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
			model.addAttribute("fx", "Trouble Ticket History by CPE Serial Number");
			
	        
			return "troubleticket/history";

	 }  
	 
	 
	    
		@RequestMapping(method = RequestMethod.GET, value = "/ticket-history/fetch")
		public String getTroubleTicketbyTokenID(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model) throws SQLException {
		    UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
				//	MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
					
		    model.addAttribute("fx", "Trouble Ticket History by CPE Serial Number");
			
			String MAIN_SRV_NO = request.getParameter("MAIN_SRV_NO").trim();
			
			List<Map<String, Object>> list = null;
			TicketHistroyDao dao=new TicketHistroyDao();
			
			try {
				list = dao.getComplainListDetailbyToken(MAIN_SRV_NO);
			
				model.addAttribute("data_list", list);
				model.addAttribute("MAIN_SRV_NO", MAIN_SRV_NO);
				
				
						
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "troubleticket/history";

		}
	 
	   
}
