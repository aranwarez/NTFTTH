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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.dao.CommonMenuDao;
import com.dao.EmployeeDao;

import com.model.EmployeeModel;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class EmployeeController {
	private static final String classname = "../employee/list";
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@RequestMapping(value = "/employee/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpSession session,HttpServletRequest request) throws SQLException  {
		logger.info("Welcome home! The client locale is {}.", locale);


		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		
		
		List<Map<String, Object>> employeelist = null;              
              model.addAttribute("fx", "Employee List");
     
			 model.addAttribute("employeelist", employeelist);
		

		return "employee/list";

	}
	@RequestMapping(method = RequestMethod.GET, value = "dialogemployee")
    public String dialogservice(Model model, Locale locale) {
		
        return "employee/employeedialog";

    }
	
	@ResponseBody
    @RequestMapping(value = "/employee/jsonlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getSPtargetist(Locale locale, Model model, HttpSession session)
            throws SQLException {
    	
        return EmployeeDao.getEmpList();
        
    }
	
	

    @RequestMapping(value = "/employee/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSEmployee(@ModelAttribute("employee") EmployeeModel employee, HttpSession session, Model model, Locale locale,HttpServletRequest request) throws SQLException {

        logger.info("employee Service {}.", locale);
        
        
    //  ADD_FLAG
        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
        		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
        		
        		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
        			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        			
        		}
        		
        EmployeeDao dao = new EmployeeDao();
        
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        employee.setUSER(userinfo.getUSER_ID());
        
        model.addAttribute("fx", "Employee controller list ");
        model.addAttribute("userName", userinfo.getUSER_ID());
        String msg = null;
        try {
			msg = dao.saveEmployeeObj(employee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    
        return msg;
    }
    @RequestMapping(value = "/employee/updateJS", method = RequestMethod.POST)
    @ResponseBody
    public String updateJSEmployee(String EMPLOYEE_CODE,String EMPLOYEE_NAME,String ADDRESS,String TEL_NO,String MOBILE_NO,String EMAIL,String DEPT_CD,String LOCATION_CD,String EMP_TITLE, HttpSession session, Model model, Locale locale,HttpServletRequest request) throws SQLException {

        logger.info("Update JS Employee {}.", locale);
        
        

        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
			
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			
		}
		
        EmployeeDao dao = new EmployeeDao();
        
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
      
        
        model.addAttribute("fx", "Employee controller list ");
        model.addAttribute("userName", userinfo.getUSER_ID().toString());
        String msg = null;
        try {
			msg = dao.updateEmployee(userinfo.getUSER_ID().toString(), EMPLOYEE_NAME, ADDRESS, TEL_NO, MOBILE_NO, EMAIL, DEPT_CD, LOCATION_CD,EMP_TITLE,EMPLOYEE_CODE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    
        return msg;
    }
    
    @RequestMapping(value = "/employee/deleteJS", method = RequestMethod.POST)
    @ResponseBody
    public String deleteJSEmployee(String EMPLOYEE_CODE, HttpSession session, Model model, Locale locale,HttpServletRequest request) throws SQLException {
    	
        logger.info("delete JS Employee {}.", locale);
        
        UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	
		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {

			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
	
		}
		
        
        EmployeeDao dao = new EmployeeDao();
        
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
     
        model.addAttribute("fx", "Employee controller list ");
        model.addAttribute("userName",userinfo.getUSER_ID() );
        String msg = null;
        try {
			msg = dao.deleteEmployee(EMPLOYEE_CODE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    
        return msg;
    }
    
}
