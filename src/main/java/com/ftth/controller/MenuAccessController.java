package com.ftth.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dao.MenuAccessDao;
import com.dao.MenuDao;
import com.dao.RoleDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.model.Menu;
import com.model.MenuAccess;
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

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getEditMode")

	public List<MenuAccess> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String ROLE_CODE = request.getParameter("ROLE_CODE");
		List<MenuAccess> list = null;

		try {
			list = MenuAccessDao.getModeList(ROLE_CODE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(value = "/saveModeList", method = RequestMethod.POST)
	@ResponseBody

	public String saveModeList(String ROLE_CODE, String menu_mode,  Model model, Locale locale,HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {

//		String ROLE_CODE = request.getParameter("ROLE_CODE");
		System.out.println(" role code = " + ROLE_CODE);
		if (ROLE_CODE == null) {
			return "Please Enter role code ";
		}

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {
			return "redirect:/login";
		}

//		String menu_mode = request.getParameter("menu_mode");

		ObjectMapper mapper = new ObjectMapper();

//		List<MenuAccess> editmodelist = mapper.readValue(menu_mode, new TypeReference<List<MenuAccess>>() {
//		});
	
		List<Map<String, Object>> myObjects = mapper.readValue(menu_mode,
                new TypeReference<List<Map<String, Object>>>() {
        });
		
//		SaveMenuAccess
		MenuAccessDao menuaccessdao = new MenuAccessDao();
		String msg = null;
		try {
			msg = menuaccessdao.SaveMenuAccess(ROLE_CODE, myObjects, user.getUSER_ID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}

}
