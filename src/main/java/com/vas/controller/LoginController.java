package com.vas.controller;

import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.UserDao;
import com.model.UserInformationModel;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	UserDao dao = new UserDao();

	@RequestMapping(method = RequestMethod.POST, value = "postLogIn")

	public String postLogIn(@ModelAttribute UserInformationModel user, Model model, Locale locale,
			HttpServletRequest request, HttpServletResponse response,HttpSession session) throws SQLException {
		logger.info(" user id:", locale);
//		HttpSession session = request.getSession(true);

		UserInformationModel level = dao.getUserByUsername(user.getUSER_ID(), user.getPASSWORD());

		if (level != null && level.getLOCK_FLAG().equals("N")) {
			session.setAttribute("UserList", level);

			model.addAttribute("fx", "Thank you for signing up!");

		} else if (level != null && level.getLOCK_FLAG().equalsIgnoreCase("Y")) {

			model.addAttribute("fx", "Thank you for login!");

		} else {

			model.addAttribute("fx", "Role List");

		}
		System.out.println("level=" + level.getACC_CEN_CODE());

		return "redirect:role/list";
	}

}
