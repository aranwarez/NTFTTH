package com.vas.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dao.CommonMenuDao;
import com.dao.MenuDao;
import com.model.Menu;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class MenuController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	MenuDao dao = new MenuDao();

	Menu m = new Menu();

	@RequestMapping(value = "/menu/list", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		if (user == null) {
			return "redirect:/login";

		}
		List<Menu> list = null;
		try {
			list = dao.getlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//menu code should know before validate
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), "M0043");

		model.addAttribute("LIST_FLAG", menuaccess.getLIST_FLAG());
		model.addAttribute("ADD_FLAG", menuaccess.getADD_FLAG());
		model.addAttribute("EDIT_FLAG", menuaccess.getEDIT_FLAG());
		model.addAttribute("DELETE_FLAG", menuaccess.getDELETE_FLAG());
		model.addAttribute("POST_FLAG", menuaccess.getPOST_FLAG());
		model.addAttribute("CANCEL_FLAG", menuaccess.getCANCEL_FLAG());
		
		model.addAttribute("fx", "Menu List");
		model.addAttribute("data_list", list);

		return "menu/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogmenu")
	public String dialogrole(Model model, Locale locale) {
		return "menu/menudialog";

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "menu/menulist")

	public List<Menu> getMenuList() {
		List<Menu> list = null;
		try {
			list = dao.getlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "menu/save")
	public String saveMenu(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) {
		logger.info(" user id:", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		m.setMENU_CODE(request.getParameter("MENU_CODE"));
		m.setMENU_DESC(request.getParameter("MENU_DESC"));

		if (user == null) {
			return "Session has been expired";
		}
		m.setUSER(user.getUSER_ID());

		m.setMENU_URL(request.getParameter("MENU_URL"));
		m.setPARENT_MENU(request.getParameter("PARENT_MENU"));
		m.setMODULE_TYPE(request.getParameter("MODULE_TYPE"));
		m.setSTATUS_TYPE(request.getParameter("STATUS_TYPE"));
		String msg = null;
		try {
			msg = dao.saveMenu(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "menu/update")
	public String updateMenu(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) {
		logger.info(" user id:", locale);

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		m.setMENU_CODE(request.getParameter("MENU_CODE"));
		m.setMENU_DESC(request.getParameter("MENU_DESC"));

		if (user == null) {
			return "Session has been expired";
		}
		m.setUSER(user.getUSER_ID());

		m.setMENU_URL(request.getParameter("MENU_URL"));
		m.setPARENT_MENU(request.getParameter("PARENT_MENU"));
		m.setMODULE_TYPE(request.getParameter("MODULE_TYPE"));
		m.setSTATUS_TYPE(request.getParameter("STATUS_TYPE"));
		String msg = null;
		try {
			msg = dao.update(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "menu/delete")
	public String deleteMenu(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) {
		logger.info(" user id:", locale);
		String msg = null;
		try {
			msg = dao.delete(request.getParameter("MENU_CODE"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getChildMenulist")

	public List<Menu> getMenuList(HttpServletRequest request, HttpServletResponse response) {
		String PARENT_MENU = request.getParameter("PARENT_MENU");
		String ROLE_CODE = request.getParameter("ROLE_CODE");

		List<Menu> list = null;
		try {
			list = dao.getChildCode(PARENT_MENU, ROLE_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

}
