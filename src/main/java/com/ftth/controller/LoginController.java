package com.ftth.controller;

import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.ProfileDao;
import com.dao.UserDao;
import com.model.UserInformationModel;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	UserDao dao = new UserDao();

	@RequestMapping(method = RequestMethod.POST, value = "postLogIn")

	public String postLogIn(@ModelAttribute UserInformationModel user, Model model, Locale locale,
			HttpServletRequest request) throws Exception {
		logger.info(" user id:", locale);
		
		UserInformationModel level = dao.getUserByUsername(user.getUSER_ID(), user.getPASSWORD());		
		
		if (level != null && level.getLOCK_FLAG().equals("N")) {
			HttpSession session = request.getSession();
			session.setAttribute("userName", user.getUSER_ID());
			session.setMaxInactiveInterval(30 * 60);
			session.setAttribute("UserList", level);
			
			
			ProfileDao.loginLog(level.getUSER_ID());
			
			model.addAttribute("fx", "Thank you for signing up!");
			
			return "redirect:/dashboard/list";

		} else if (level != null && level.getLOCK_FLAG().equalsIgnoreCase("Y")) {

			model.addAttribute("error", "You Account is locked !");
			model.addAttribute("fx", "<h1>FTTH CMS</h1><h2>Complain Management System</h2>");

		} else {

			model.addAttribute("error", "Wrong Credentials!!");
			model.addAttribute("fx", "<h1>FTTH CMS</h1><h2>Complain Management System</h2> ");

		}
		return "NTC/common/login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "Logout")

	public String Logout(HttpSession session, Locale locale) throws SQLException {
		logger.info(" Log out:", locale);
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "CheckSession")
	@ResponseBody
	public Boolean CheckSession(HttpSession session, Locale locale) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if(user!=null) {return true;}
		return false;
	}

}


