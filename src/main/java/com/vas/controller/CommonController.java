package com.vas.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.MenuDao;
import com.model.Menu;
import com.model.UserInformationModel;

@Controller
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@RequestMapping(method = RequestMethod.GET, value = "leftmenu")

	public String newItem(Model model, HttpSession leftsession) {

		UserInformationModel leftmenuuser = (UserInformationModel) leftsession.getAttribute("UserList");
		if (leftmenuuser == null) {
			return "redirect:/login";

		}
		MenuDao leftmenudao = new MenuDao();

		String leftmenurole_code = leftmenuuser.getROLE_CODE();
		String leftmenuMODULE_ACCESS = leftmenuuser.getMODULE_ACCESS();
		String leftmenuRoleCode = leftmenuuser.getROLE_CODE();
		System.out.println(leftmenurole_code);
		System.out.println(leftmenuMODULE_ACCESS);
		
		model.addAttribute("leftmenurole_code", leftmenurole_code);		
		model.addAttribute("leftmenuMODULE_ACCESS", leftmenuMODULE_ACCESS);		
		model.addAttribute("leftmenuRoleCode", leftmenuRoleCode);		
		try {
			List<Menu> menulist = leftmenudao.getMenuDisplay(leftmenurole_code);
			
			
			model.addAttribute("leftmenuheadlist", menulist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public String newHeadCss(Model model, HttpSession session) {

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		model.addAttribute("UserList", user);

		return "include/headcss";
	}

	@RequestMapping(method = RequestMethod.GET, value = "footJS")

	public String newFootJS(Model model) {

		return "include/footJS";
	}

}
