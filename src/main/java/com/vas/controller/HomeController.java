package com.vas.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.RoleDao;
import com.model.Role;
import com.model.UserInformationModel;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		System.out.println("hello ");
		
		model.addAttribute("fx", "HomeController : home()");
		
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
//		UserInformationModel usernsmer = (UserInformationModel) session.getAttribute("UserList");
		
		model.addAttribute("fx", "Nepal Telecom - Login ");
        return "NTC/common/login";
    }
     
//    @RequestMapping(value = "/home", method = RequestMethod.POST)
//    public String login(@Validated User user, Model model) {
//    	model.addAttribute("fx", "HomeController : login()");
//        model.addAttribute("userName", user.getUserName());
//        return "user";
//    }
	
	
	@RequestMapping(value="/dashboard",method=RequestMethod.GET)
	public String dashboard(Locale locale,Model model) {

		model.addAttribute("fx", "Dashboard - Nepal Telecom");
		
		return "NTC/dashboard/index";
		
	}
	
	@RequestMapping(value="/data",method=RequestMethod.GET)
	public String data(Locale locale, Model model) {
		
		model.addAttribute("fx", "Test data");
		
		return "data/index";
		
	}
	

	
	
}
