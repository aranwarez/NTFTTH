package com.ftth.controller;

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
import com.dao.UserTeamDao;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class TroubleTicketController {
	private static final Logger logger = LoggerFactory.getLogger(TroubleTicketController.class);

	private static final String classname = "../troubleticket/list";

	@RequestMapping(value = "/troubleticket/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
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
		
		
		try {
			regionlist = dao.getlist();
			servicetypelist=ServiceTypedao.getServiceTypeList();
			teamlist=teamdao.getTeamList();
			
			model.addAttribute("Date_list", DAT.getDateList());
			model.addAttribute("userteamlist", UserTeamDao.getModeList(user.getUSER_ID()));
			model.addAttribute("teamlist", teamlist);
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Trouble Tickets");
		model.addAttribute("regionlist", regionlist);
		model.addAttribute("servicetypelist", servicetypelist);
		
		
		return "troubleticket/list";

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/troubleticket/getfilterlist",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getTroubleTicketList(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		String REGION_CODE = request.getParameter("REGION_CODE");
		String ZONE_CODE = request.getParameter("ZONE_CODE");
		String DISTRICT_CODE = request.getParameter("DISTRICT_CODE");
		String OFFICE_CODE = request.getParameter("OFFICE_CODE");
		String OLT_CODE = request.getParameter("OLT_CODE");
		String Service_Type = request.getParameter("SERVICE_TYPE");
		String Sub_Team = request.getParameter("SUB_TEAM");
		String FRM_DT = request.getParameter("FRM_DT");
		String TO_DT = request.getParameter("TO_DT");
		String Statusflag = request.getParameter("Statusflag");
		
		
//		String REGION_CODE = request.getParameter("REGION_CODE");
		
		
		List<Map<String, Object>> list = null;
		ComplainDao dao=new ComplainDao();
		
		try {
			list = dao.getComplainList(user.getUSER_ID(), REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, OLT_CODE, Sub_Team, Service_Type, FRM_DT, TO_DT,Statusflag);
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/troubleticket/dialog")
	public String dialogservice(Model model, Locale locale) {

		return "troubleticket/troubledialog";

	}
	
	
	 @RequestMapping(value = "/troubleticket/Forward", method = RequestMethod.POST)
	    @ResponseBody
	    public String Teamforward(String Remarks,String token,String toteam,Locale locale,HttpSession session) {
	        logger.info("Forwarding Ticket", locale);
	        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}

	        ComplainDao dao = new ComplainDao();
	    	
	        String msg = null;
	        try {
	        msg=	dao.ForwardTeam(token, toteam, user.getUSER_ID(), Remarks);
	         } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;

	    }


	 @RequestMapping(value = "/troubleticket/Close", method = RequestMethod.POST)
	    @ResponseBody
	    public String CloseTicket(String Remarks,String token,Locale locale,HttpSession session) {
	        logger.info("Forwarding Ticket", locale);
	        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}

	        ComplainDao dao = new ComplainDao();
	    	
	        String msg = null;
	        try {
	        msg=	dao.Closeticket(token, user.getUSER_ID(), Remarks);
	         } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;

	    }
	 
	 
	 @ResponseBody
		@RequestMapping(method = RequestMethod.GET, value = "/troubleticket/gettokenhistory",produces = MediaType.APPLICATION_JSON_VALUE)
		public List<Map<String, Object>> getTroubleTicketHistory(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		    UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
					MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
					if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
						throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
					}

			
			String SUBTOKEN = request.getParameter("SUBTOKEN");
			List<Map<String, Object>> list = null;
			ComplainDao dao=new ComplainDao();
			
			try {
				list = dao.getTokenDetail(SUBTOKEN);
						
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;

		}



	 
	 
}
