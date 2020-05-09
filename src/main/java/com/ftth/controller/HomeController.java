package com.ftth.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

		model.addAttribute("fx", "Do not use URL to define path!!!");

		return "NTC/common/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("fx", "Nepal Telecom - Login ");
		return "NTC/common/login";
	}

//    @RequestMapping(value = "/home", method = RequestMethod.POST)
//    public String login(@Validated User user, Model model) {
//    	model.addAttribute("fx", "HomeController : login()");
//        model.addAttribute("userName", user.getUserName());
//        return "user";
//    }

	@RequestMapping(value = "/dashboard/list", method = RequestMethod.GET)
	public String dashboard(Locale locale, Model model) {

		model.addAttribute("fx", "Dashboard");

		//return "NTC/dashboard/index";
		return "dashboard";
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String data(Locale locale, Model model) {

		model.addAttribute("fx", "Test data");

		return "data/index";

	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(@RequestAttribute(name = "errorMessage") Exception ex, String emsg, Locale locale,
			Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("fx", ex);

		return "home";
	}

	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String error404(String emsg, Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("fx", "Page Doesnt Exist or Invalid URL! ");
		return "home";
	}

}
