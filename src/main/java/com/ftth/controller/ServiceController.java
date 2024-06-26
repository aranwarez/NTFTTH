package com.ftth.controller;
import com.dao.CommonMenuDao;
import com.dao.MServiceDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

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

import com.model.MenuAccess;
import com.model.UserInformationModel;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ServiceController {
	private static final String classname = "../service/list";
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    public String servicelist(Locale locale, HttpServletRequest request,Model model) throws SQLException {

        logger.info("Getting Services List", locale);
        

        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		
        MServiceDao dao = new MServiceDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getServiceList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Service List");
        model.addAttribute("data_list", list);

        

        return "service/list";
    }
    // getting list of all SP wise Service List
    @ResponseBody
    @RequestMapping(value = "/service/jsonlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getSPtargetist(Locale locale, Model model, HttpSession session)
            throws SQLException {
    	MServiceDao dao = new MServiceDao();
        return dao.getServiceList();
        
    }

    @RequestMapping(value = "/service/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSService(String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,String DEACTIVE_DT, String ACTIVE_STATUS,HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

        logger.info("Save Service {}.", locale);
        
        
    //  ADD_FLAG
        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
        		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
        		
        		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
        			
        			model.addAttribute("fx", "Unauthorized Page for this role!!");
        			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        		}

        		
        		
        MServiceDao dao = new MServiceDao();

        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Service controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveService(DESCRIPTION, SHORT_CODE, ACTIVE_DT, DEACTIVE_DT, ACTIVE_STATUS, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }


    @RequestMapping(method = RequestMethod.GET, value = "dialogservice")
    public String dialogservice(Model model, Locale locale) {
        return "service/servicedialog";

    }

    @RequestMapping(value = "/service/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateService(String SERVICE_ID,String DESCRIPTION, String SHORT_CODE, String ACTIVE_DT,String DEACTIVE_DT, String ACTIVE_STATUS, HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

        logger.info("Updata Service {}.", locale);
        

    //  EDIT_FLAG
   
    UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
    		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

    		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
    			
    			model.addAttribute("fx", "Unauthorized Page for this role!!");
    			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
    		}
        
        
        MServiceDao dao = new MServiceDao();

        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Service controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateService(SERVICE_ID, DESCRIPTION, SHORT_CODE, ACTIVE_DT, DEACTIVE_DT,ACTIVE_STATUS, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/service/delete", method = RequestMethod.POST)
    @ResponseBody
    public String serviceDelete(String SERVICE_ID, Model model,HttpServletRequest request, Locale locale) throws SQLException {
        logger.info("delete service", locale);
        
        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	
		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		
        MServiceDao dao = new MServiceDao();

        String msg = null;
        try {
            msg = dao.DeleteService(SERVICE_ID);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
}