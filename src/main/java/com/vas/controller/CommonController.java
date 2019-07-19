package com.vas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@RequestMapping(method = RequestMethod.GET, value = "leftmenu")

	public String newItem(Model model) {

		return "include/leftsidebar";
	}

	@RequestMapping(method = RequestMethod.GET, value = "topmenu")

	public String newTopMenu(Model model) {

		model.addAttribute("menu_name", "<b>Nepal </b>Telecom ");
		
		return "include/topsidebar";
	}
	@RequestMapping(method = RequestMethod.GET, value = "footer")

	public String newFooterMenu(Model model) {

		model.addAttribute("version", "1.0.0");
		model.addAttribute("website", "https://www.ntc.net.np/");
		
		return "include/footer";
	}
	@RequestMapping(method = RequestMethod.GET, value = "headCss")

	public String newHeadCss(Model model) {

		return "include/headcss";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "footJS")

	public String newFootJS(Model model) {

		return "include/footJS";
	}
	
	
}
