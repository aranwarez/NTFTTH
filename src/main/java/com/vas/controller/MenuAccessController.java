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

import com.dao.MenuDao;
import com.dao.RoleDao;
import com.model.Menu;
import com.model.Role;
import com.model.UserInformationModel;

@Controller
public class MenuAccessController {
	RoleDao roledao = new RoleDao();
	MenuDao menudao = new MenuDao();

	private static final Logger logger = LoggerFactory.getLogger(MenuAccessController.class);

	@RequestMapping(value = "/access/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {
			return "redirect:/login";
		}

		List<Role> rolelist = null;
		try {
			rolelist = roledao.getlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("rolelist", rolelist);

		return "menuaccess/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogmenuaccess")
	public String dialogrole(Model model, Locale locale) {
		return "menuaccess/menuaccessdialog";

	}

	@RequestMapping(method = RequestMethod.GET, value = "menuaccessbody")
	public String menuaccessbody(Model model, Locale locale) {

		List<Menu> parementmenu = null;
		try {
			parementmenu = menudao.getByParent();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("parementmenu", parementmenu);
		return "menuaccess/menuaccessbody";

	}

}
