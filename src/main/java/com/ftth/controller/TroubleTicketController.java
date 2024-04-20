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
import com.dao.UserDao;
import com.dao.UserTeamDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class TroubleTicketController {
	private static final Logger logger = LoggerFactory.getLogger(TroubleTicketController.class);

	private static final String classname = "../troubleticket/list";

	@RequestMapping(value = "/troubleticket/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		logger.info("/troubleticket/list by user"+user.getUSER_ID(), locale);
		
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
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
			regionlist = dao.getlistByUserFDC(user.getUSER_ID(),user.getUSER_LEVEL());
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
		model.addAttribute("fx", "Trouble Tickets");
		model.addAttribute("regionlist", regionlist);
		
		model.addAttribute("servicetypelist", servicetypelist);
		model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());        
        model.addAttribute("levelcontrollist", levelcontrollist);
		
        System.out.println(user.getUSER_LEVEL());
        
		return "troubleticket/list";

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/troubleticket/getfilterlist",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getTroubleTicketList(HttpServletRequest request, HttpServletResponse response,HttpSession session,Locale locale) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		logger.info("/troubleticket/getfilterlist"+user.getUSER_ID(), locale);
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
		String Teamid = request.getParameter("WEBTEAMCODE");
		
//		String REGION_CODE = request.getParameter("REGION_CODE");
		
		//getting english date from nepali date
		List<String> engdate=null;
		CommonController getengdate=new CommonController();
		try {
			engdate=	getengdate.Englishdate(FRM_DT, TO_DT, null, session);
					} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		List<Map<String, Object>> list = null;
		ComplainDao dao=new ComplainDao();
		
		try {
			if(user.getUSER_LEVEL().equals("6")) {
			list = dao.getComplainListlowlvl(user.getUSER_ID(), REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, OLT_CODE, Sub_Team, Service_Type, engdate.get(0), engdate.get(1),Statusflag,Teamid);
			}
			else {
				list = dao.getComplainList(user.getUSER_ID(), REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, OLT_CODE, Sub_Team, Service_Type, engdate.get(0), engdate.get(1),Statusflag,Teamid);
					
				
			}
			
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
	    public String Teamforward(String Remarks,String token,String toteam,Locale locale,HttpSession session) throws SQLException {
	        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
	        logger.info("Forwarding Ticket by "+user.getUSER_ID(), locale);
	        
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
	    public String CloseTicket(String Remarks,String token,Locale locale,HttpSession session) throws SQLException {
	        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
	        logger.info("Closing Ticket"+token+ "by "+user.getUSER_ID(), locale);
		       
	        MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
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

	 @RequestMapping(value = "/troubleticket/CloseArray", method = RequestMethod.POST)
	    @ResponseBody
	    public String CloseArray(String Remarks,String tokenarray,Locale locale,HttpSession session) throws SQLException {
	        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
	        logger.info("Closing Ticket In arrays"+tokenarray+ "by "+user.getUSER_ID(), locale);
			ObjectMapper mapper = new ObjectMapper();

//			List<MenuAccess> editmodelist = mapper.readValue(menu_mode, new TypeReference<List<MenuAccess>>() {
//			});
		
			
 
	        
	        MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}

	        ComplainDao dao = new ComplainDao();
	    	
	        String msg = null;
	        try {
	        	List<String> myObjects = mapper.readValue(tokenarray,
		                new TypeReference<List<String>>() {
		        });
	        	
	        	     msg=	dao.Closearrayofticket(myObjects, user.getUSER_ID(), Remarks);
	        	
	   
	         } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return e.getLocalizedMessage();
	         }
	        return msg;

	    }

	 

	 @RequestMapping(value = "/troubleticket/Resolved", method = RequestMethod.POST)
	    @ResponseBody
	    public String Resolved(String Remarks,String token,String solution_id,Locale locale,HttpSession session) throws SQLException {
	        logger.info("Resolved Ticket", locale);
	        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}

	        ComplainDao dao = new ComplainDao();
	    	
	        String msg = null;
	        try {
	        msg=	dao.Resolved(token, user.getUSER_ID(), Remarks,solution_id);
	         } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;

	    }
	 
	 
	 @ResponseBody
		@RequestMapping(method = RequestMethod.GET, value = "/troubleticket/gettokenhistory",produces = MediaType.APPLICATION_JSON_VALUE)
		public List<Map<String, Object>> getTroubleTicketHistory(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws SQLException {
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


	 
	 @ResponseBody
		@RequestMapping(method = RequestMethod.GET, value = "/troubleticket/gettokendetails",produces = MediaType.APPLICATION_JSON_VALUE)
		public List<Map<String, Object>> getTroubleTicketbyTokenID(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws SQLException {
		    UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
					MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
					if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
						throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
					}

			
			String TOKEN = request.getParameter("token_id");
			List<Map<String, Object>> list = null;
			ComplainDao dao=new ComplainDao();
			
			try {
				list = dao.getComplainListDetailbyToken(TOKEN);
						
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return list;

		}

	 
	 
}
