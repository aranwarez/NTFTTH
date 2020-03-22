package com.vas.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.MServiceTypeDao;
import com.dao.ProblemDao;
import com.model.UserInformationModel;

@Controller
public class ProblemController {
	 private static final Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);
	  @RequestMapping(value = "/problem/list", method = RequestMethod.GET)
	    public String serviceTypelist(Locale locale, Model model) throws SQLException {

	        logger.info("Getting Problem List", locale);
	        ProblemDao dao = new ProblemDao();
	        List<Map<String, Object>> list = null;
	        try {
	            list = dao.getProblemList();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        model.addAttribute("fx", "Problem List");
	        model.addAttribute("data_list", list);
//	        MServiceDao servicedao=new MServiceDao();
//	        List<Map<String, Object>> service_list = servicedao.getServiceList();
	        
//	        model.addAttribute("service_list", service_list);
	        

	        return "problem/list";
	    }
	  
	  @RequestMapping(method = RequestMethod.GET, value = "dialogproblem")
	    public String dialogservice(Model model, Locale locale) {
	    	
	        return "problem/problemdialog";

	    }
	  
	  @RequestMapping(value = "/problem/saveJS", method = RequestMethod.POST)
	    @ResponseBody
	    public String saveJSService(String DESCRIPTION, String SERVICE_TYPE_ID, String ACTIVE_DT,String DEACTIVE_DT, String ACTIVE_STATUS, HttpSession session, Model model, Locale locale) {

	        logger.info("Save Service {}.", locale);
	        ProblemDao dao = new ProblemDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER = userinfo.getUSER_ID();
	        
	        model.addAttribute("fx", "Problem list ");
	        model.addAttribute("userName", "NEPal");
	        String msg = null;
	        try {
	            msg = dao.saveProblem(DESCRIPTION, SERVICE_TYPE_ID, ACTIVE_DT, DEACTIVE_DT, ACTIVE_STATUS, USER);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	  
	  @RequestMapping(value = "/problem/update", method = RequestMethod.POST)
	    @ResponseBody
	    public String updateService(String PROBLEM_ID,String DESCRIPTION,String SERVICE_TYPE_ID, String ACTIVE_DT, String DEACTIVE_DT,String ACTIVE_STATUS, HttpSession session, Model model, Locale locale) {

	        logger.info("Updata Service {}.", locale);
	        ProblemDao dao = new ProblemDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER = userinfo.getUSER_ID();
	        model.addAttribute("fx", "Service controller list ");
	        model.addAttribute("userName", "NEPal");
	        String msg = null;
	        try {
	            msg = dao.updateProblem(PROBLEM_ID, DESCRIPTION, SERVICE_TYPE_ID,  ACTIVE_DT,  DEACTIVE_DT, ACTIVE_STATUS, USER);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	  @RequestMapping(value = "/problem/delete", method = RequestMethod.POST)
	    @ResponseBody
	    public String serviceDelete(String PROBLEM_ID, Model model, Locale locale) {
	        logger.info("delete service", locale);
	        ProblemDao dao = new ProblemDao();

	        String msg = null;
	        try {
	            msg = dao.DeleteProblem(PROBLEM_ID);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;

	    }
	  
	  
}
