package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.dao.CommonMenuDao;
import com.dao.SubTeamDao;
import com.model.MenuAccess;
import com.model.UserInformationModel;
@Controller
public class SubTeamController {
	
	private static final String classname = "../subTeam/list";
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	    
	 
	@RequestMapping(value = "/subTeam/list", method = RequestMethod.GET)
    public String servicelist(Locale locale, Model model,HttpServletRequest request) throws SQLException {
		  logger.info("Getting Services List", locale);
		//LIST_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

				if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					return "/home";
				}
      
        SubTeamDao dao = new SubTeamDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getSubTeamList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Service List");
        model.addAttribute("data_list", list);

        

        return "subteam/list";
    }
	
	
	@ResponseBody
    @RequestMapping(value = "/subteam/jsonlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getSPtargetist(Locale locale, Model model, HttpSession session)
            throws SQLException {
		SubTeamDao dao = new SubTeamDao();
        return dao.getSubTeamList();
        
    }
	
	@ResponseBody
    @RequestMapping(value = "/subteamByTeam/jsonlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getSubTeamList(String TEAM_CODE,Locale locale, Model model, HttpSession session)
            throws SQLException {
	
		SubTeamDao dao = new SubTeamDao();
        return dao.getSubTeamByTeam(TEAM_CODE);
        
    }
	
	
	
	 @RequestMapping(method = RequestMethod.GET, value = "dialogsubteam")
	    public String dialogservice(Model model, Locale locale) {
	        return "subteam/subteamdialog";

	    }
	 
	 

	    @RequestMapping(value = "/subteam/saveJS", method = RequestMethod.POST)
	    @ResponseBody
	    public String saveJSService(String SUB_TEAM_CODE, String DESCRIPTION,String TEAM_CODE,HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

	        logger.info("Save subteam {}.", locale);
	        
	    //  ADD_FLAG
	        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
	        		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	        		
	        		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
	        			
	        			model.addAttribute("fx", "Unauthorized Page for this role!!");
	        			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
	        		}
	        
	        SubTeamDao dao = new SubTeamDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER = userinfo.getUSER_ID();
	        model.addAttribute("fx", "Team controller list ");
	        model.addAttribute("userName", "NEPal");
	        String msg = null;
	        try {
	            msg = dao.saveSubTeam(SUB_TEAM_CODE,DESCRIPTION,TEAM_CODE,USER);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	    
	    
	    @RequestMapping(value = "/subteam/update", method = RequestMethod.POST)
	    @ResponseBody
	    public String updateService(String SUB_TEAM_CODE,String DESCRIPTION,String TEAM_CODE,HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

	        logger.info("Updata Service {}.", locale);
	        
	    //  EDIT_FLAG
	        
	        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
	        		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

	        		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
	        			
	        			model.addAttribute("fx", "Unauthorized Page for this role!!");
	        			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
	        		}
	        		
	        SubTeamDao dao = new SubTeamDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER = userinfo.getUSER_ID();
	        model.addAttribute("fx", "team controller list ");
	        model.addAttribute("userName", USER);
	        String msg = null;
	        try {
	            msg = dao.updateSubTeam(SUB_TEAM_CODE, DESCRIPTION,TEAM_CODE, USER);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	    @RequestMapping(value = "/subteam/delete", method = RequestMethod.POST)
	    @ResponseBody
	    public String serviceDelete(String SUB_TEAM_CODE, HttpServletRequest request,Model model, Locale locale) throws SQLException {
	        logger.info("delete service", locale);
	        
	        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		
			if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
				
				model.addAttribute("fx", "Unauthorized Page for this role!!");
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}
			
			
	        SubTeamDao dao = new SubTeamDao();

	        String msg = null;
	        try {
	            msg = dao.DeleteSubTeam(SUB_TEAM_CODE);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;

	    }
	    
	    
	 
}
