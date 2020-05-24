package com.ftth.controller;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.UserDao;
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

		model.addAttribute("fx", "Do not use URL to define path!!!");

		return "NTC/common/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("fx", "Nepal Telecom FTTH CMS Complain Management System ");
		return "NTC/common/login";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotPasswordPage(Locale locale, Model model, HttpSession session) {
		logger.info("Forgot Password", locale);
		model.addAttribute("resettype", "username");
		return "NTC/common/forgotpassword";
	}

	@RequestMapping(value = "/generateToken", method = RequestMethod.POST)
	public String generateToken(String username, String resettype,String reseterror, String tokenid, Locale locale, Model model,
			HttpSession session) {

		logger.info("generate Token", locale);

		// validating entered token id
		if (resettype.equals("valtokenid")) {
			if (tokenid != null && tokenid.length() == 4) {
				// generate temp session and reset first time first password
				UserDao dao = new UserDao();
				UserInformationModel level;
				try {
					level = dao.getUserByTokenkey(username, tokenid);
					if (level == null) {
						model.addAttribute("resetuser", "TOKENERROR");
						model.addAttribute("username", username);
						model.addAttribute("Error", "Invalid Token ID or token has expired.");

						return "NTC/common/forgotpassword";
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					model.addAttribute("resetuser", "TOKENERROR");
					model.addAttribute("Error", e.getLocalizedMessage());

					return "NTC/common/forgotpassword";
				}

				if (level != null && level.getLOCK_FLAG().equals("N")) {

					session.setMaxInactiveInterval(15 * 60);
					session.setAttribute("UserList", level);
					// ProfileDao.loginLog(level.getUSER_ID());
					model.addAttribute("fx", "Thank you for signing up!");
					return "redirect:/dashboard/list";
				} else if (level != null && level.getLOCK_FLAG().equalsIgnoreCase("Y")) {
					model.addAttribute("error", "You Account is locked !");
					model.addAttribute("fx", "Nepal Telecom FTTH CMS Complain Management Systems ");
				} else {
					model.addAttribute("error", "Wrong Credentials!!");
					model.addAttribute("fx", "Nepal Telecom FTTH CMS Complain Management System  ");
				}
				return "NTC/common/login";
				// till here session creation
			}
			if (tokenid != null && tokenid.length() != 4) {
				model.addAttribute("resetuser", "TOKENERROR");
				model.addAttribute("Error", "Invalid Token ID");
				return "NTC/common/forgotpassword";
			}
		}

		// genrating token to reset
		// generate random number
		if (resettype.equals("gentoken") || reseterror.equals("gentoken")) {
			Random rand = new Random();
			String id = String.format("%04d", rand.nextInt(10000));
			UserDao userd = new UserDao();
			try {
				String msg = userd.generateToken(username, id);
				if (msg.equals("Valid Mobile Not Found")) {
					model.addAttribute("resetuser", "ERROR");
					model.addAttribute("Error", msg);
					return "NTC/common/forgotpassword";
				}
				if (msg.substring(0, 3).equals("OTP")) {
					model.addAttribute("resetuser", "successtoken");
					model.addAttribute("username", username);
					model.addAttribute("Sucess", msg);
					return "NTC/common/forgotpassword";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("resetuser", "ERROR");
				model.addAttribute("Error", e.getLocalizedMessage());
				return "NTC/common/forgotpassword";
			}
		}
		model.addAttribute("resetuser", "successtoken");
		return "NTC/common/forgotpassword";

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

		// return "NTC/dashboard/index";
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
