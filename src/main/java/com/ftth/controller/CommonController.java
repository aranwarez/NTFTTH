package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommonDateDao;
import com.dao.MenuDao;
import com.model.Menu;
import com.model.UserInformationModel;

@Controller
public class CommonController {
	@RequestMapping(method = RequestMethod.GET, value = "leftmenu")

	public String newItem(Model model, HttpSession leftsession) {

		UserInformationModel leftmenuuser = (UserInformationModel) leftsession.getAttribute("UserList");
		if (leftmenuuser == null) {
			return "redirect:/login";

		}
		MenuDao leftmenudao = new MenuDao();

		String leftmenurole_code = leftmenuuser.getROLE_CODE();
		// String leftmenuMODULE_ACCESS = leftmenuuser.getMODULE_ACCESS();
		String leftmenuRoleCode = leftmenuuser.getROLE_CODE();
		model.addAttribute("leftmenurole_code", leftmenurole_code);
		// model.addAttribute("leftmenuMODULE_ACCESS", leftmenuMODULE_ACCESS);
		model.addAttribute("leftmenuRoleCode", leftmenuRoleCode);
		try {
			List<Menu> menulist = leftmenudao.getMenuDisplay(leftmenurole_code);
			model.addAttribute("UserList", leftmenuuser);
			model.addAttribute("leftmenuheadlist", menulist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "include/leftsidebar";
	}

	@RequestMapping(method = RequestMethod.GET, value = "topmenu")

	public String newTopMenu(Model model, HttpSession session) {

		model.addAttribute("menu_name", "Nepal Telecom <b>FTTH</b> Complain Management System Telecom ");
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		model.addAttribute("UserList", user);
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

	@ResponseBody
	@RequestMapping(value = "/common/getenglishdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> Englishdate(String fromdate,String todate, Model model, HttpSession session) throws SQLException {
		CommonDateDao ojb = new CommonDateDao();
		return ojb.convertDateADstring(fromdate,todate);
	}
}
