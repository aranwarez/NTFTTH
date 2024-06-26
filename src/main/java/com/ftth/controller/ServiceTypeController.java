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
import com.dao.MServiceTypeDao;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class ServiceTypeController {
	private static final String classname = "../serviceType/list";

	  private static final Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);
	  @RequestMapping(value = "/serviceType/list", method = RequestMethod.GET)
	    public String serviceTypelist(Locale locale, Model model,HttpServletRequest request) throws SQLException {
		  	
		   logger.info("Getting Services type list", locale);
	       UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		  MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		
	        MServiceTypeDao dao = new MServiceTypeDao();
	        List<Map<String, Object>> list = null;
	        try {
	            list = dao.getServiceTypeList();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        model.addAttribute("fx", "Service Type List");
	        model.addAttribute("data_list", list);


	        return "servicetype/list";
	    }
	  	@ResponseBody
	    @RequestMapping(value = "/serviceType/jsonlist", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Map<String, Object>> getSPtargetist(Locale locale, Model model, HttpSession session)
	            throws SQLException {
		  MServiceTypeDao dao = new MServiceTypeDao();
	        return dao.getServiceTypeList();
	        
	    }
	  
	    @RequestMapping(method = RequestMethod.GET, value = "dialogservicetype")
	    public String dialogservice(Model model, Locale locale) {
	    	
	        return "servicetype/servicedialog";

	    }
	    @RequestMapping(value = "/serviceType/saveJS", method = RequestMethod.POST)
	    @ResponseBody
	    public String saveJSService(String SERVICE_ID,String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,String DEACTIVE_DT, String ACTIVE_STATUS, HttpServletRequest request,HttpSession session, Model model, Locale locale) throws SQLException {

	        logger.info("Save Service {}.", locale);
	        
	        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			
			if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
				
				model.addAttribute("fx", "Unauthorized Page for this role!!");
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}
	        
	        MServiceTypeDao dao = new MServiceTypeDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER = userinfo.getUSER_ID();
	        model.addAttribute("fx", "Service controller list ");
	        model.addAttribute("userName", "NEPal");
	        String msg = null;
	        try {
	            msg = dao.saveServiceType(SERVICE_ID,DESCRIPTION, SHORT_CODE, ACTIVE_DT, DEACTIVE_DT, ACTIVE_STATUS, USER);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	    
	    
	    @RequestMapping(value = "/servicetype/update", method = RequestMethod.POST)
	    @ResponseBody
	    public String updateService(String SERVICE_TYPE_ID,String SERVICE_ID,String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,String DEACTIVE_DT, String ACTIVE_STATUS,  HttpServletRequest request,HttpSession session, Model model, Locale locale) throws SQLException {

	        logger.info("Updata Service {}.", locale);	        
	        
	    //  EDIT_FLAG
	   
	        	UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
	    		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

	    		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
	    			
	    			model.addAttribute("fx", "Unauthorized Page for this role!!");
	    			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
	    		}


	    		
	        MServiceTypeDao dao = new MServiceTypeDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER = userinfo.getUSER_ID();
	        model.addAttribute("fx", "Service controller list ");
	        model.addAttribute("userName", "NEPal");
	        String msg = null;
	        try {
	            msg = dao.updateServiceType(SERVICE_TYPE_ID,SERVICE_ID, DESCRIPTION, SHORT_CODE, ACTIVE_DT, DEACTIVE_DT,ACTIVE_STATUS, USER);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	    
	    @RequestMapping(value = "/serviceType/delete", method = RequestMethod.POST)
	    @ResponseBody
	    public String serviceDelete(String SERVICE_TYPE_ID, Model model, Locale locale,HttpServletRequest request) throws SQLException {
	        logger.info("delete service", locale);
	        
	        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		
			if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
				
				model.addAttribute("fx", "Unauthorized Page for this role!!");
				return "/home";
			}

	        
	        MServiceTypeDao dao = new MServiceTypeDao();

	        String msg = null;
	        try {
	            msg = dao.DeleteServiceType(SERVICE_TYPE_ID);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;

	    }
	    
	    
}
