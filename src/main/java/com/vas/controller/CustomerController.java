package com.vas.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the application home page.
 */
@Controller
public class CustomerController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public String listAction(Locale locale, Model model) {
		
		User u1 = new User();
		u1.setUserName("Nimesh");
		
		User u2 = new User();
		u2.setUserName("Ram");
		
		User u3 = new User();
		u3.setUserName("Krishna");
		
		
		ArrayList<User> users = new ArrayList();
		users.add(u1);
		users.add(u2);
		users.add(u3);
		
		model.addAttribute("fx", "CustomerController : listAction()");
		model.addAttribute("data_list", users );
		return "customer/list";
	}
	
}
