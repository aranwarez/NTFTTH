package com.vas.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.UserDao;
import com.model.UserInformationModel;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	UserDao dao = new UserDao();

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String rolelist(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {

			return "redirect:/login";

		}

		List<UserInformationModel> list = null;
		try {
			list = dao.getList(user.getMODULE_ACCESS());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("fx", "User Information List");
		model.addAttribute("data_list", list);
		
	
		
		return "user/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialoguser")
	public String dialogrole(Model model, Locale locale) {

		return "user/userdialog";

	}

}
