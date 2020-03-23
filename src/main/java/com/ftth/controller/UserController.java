package com.ftth.controller;

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

import com.dao.EmployeeDao;
import com.dao.RegionDao;
import com.dao.RoleDao;
import com.dao.UserDao;
import com.model.Region;
import com.model.Role;
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
		List<Map<String, Object>> empList = null;
		RegionDao regiondao = new RegionDao();
		RoleDao roledao = new RoleDao();

		List<Region> regionlist = null;
		List<Role> rolelist = null;

		try {
			list = dao.getList();
			empList = EmployeeDao.getEmpList();

			regionlist = regiondao.getlist();
			rolelist = roledao.getlist();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("fx", "User Information List");
		model.addAttribute("data_list", list);

		model.addAttribute("empList", empList);

		model.addAttribute("regionlist", regionlist);
		model.addAttribute("rolelist", rolelist);
		return "user/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialoguser")
	public String dialogrole(Model model, Locale locale) {

		return "user/userdialog";

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "save/user")
	public String saveMenu(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) {

		logger.info(" user id:", locale);

		UserDao userdao = new UserDao();

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		UserInformationModel m = new UserInformationModel();

		m.setUSER_ID(request.getParameter("USER_ID"));
		m.setFULL_NAME(request.getParameter("FULL_NAME"));
		m.setUSER(user.getUSER_ID());
		m.setPASSWORD(request.getParameter("PASSWORD"));
		m.setEMPLOYEE_CODE(request.getParameter("EMPLOYEE_CODE"));

		m.setLOCK_FLAG(request.getParameter("LOCK_FLAG"));

		m.setSUPER_FLAG(request.getParameter("SUPER_FLAG"));

		m.setDISABLE_FLAG(request.getParameter("DISABLE_FLAG"));

		m.setOFFICE_CODE(request.getParameter("OFFICE_CODE"));

		m.setUSER_LEVEL(request.getParameter("USER_LEVEL"));
		m.setROLE_CODE(request.getParameter("ROLE_CODE"));
	
		String msg = null;
		try {
			msg = userdao.saveUser(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "adminPassChange")
	public String adminPasswordChange(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		String usercode = request.getParameter("usercode");
		UserDao userdao = new UserDao();
		String msg = null;
		try {
			msg = userdao.passWordChange(request.getParameter("pass"), usercode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "user/edit")
	public UserInformationModel editUser(String getEdit, String code, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Locale locale) {

		UserDao userdao = new UserDao();
		UserInformationModel user = null;
		try {
			user = userdao.getUser(code);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "update/user")
	public String userUpdate(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) {

		UserInformationModel m = new UserInformationModel();
		UserDao userdao = new UserDao();
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
//		String user = session.getAttribute("USER_ID").toString();

		m.setUSER_ID(request.getParameter("USER_ID"));

		m.setFULL_NAME(request.getParameter("FULL_NAME"));
		m.setUSER(user.getUSER_ID());
		m.setPASSWORD(request.getParameter("PASSWORD"));
		m.setEMPLOYEE_CODE(request.getParameter("EMPLOYEE_CODE"));

		m.setLOCK_FLAG(request.getParameter("LOCK_FLAG"));

		m.setSUPER_FLAG(request.getParameter("SUPER_FLAG"));

		m.setDISABLE_FLAG(request.getParameter("DISABLE_FLAG"));

		m.setOFFICE_CODE(request.getParameter("OFFICE_CODE"));

		m.setUSER_LEVEL(request.getParameter("USER_LEVEL"));
		m.setROLE_CODE(request.getParameter("ROLE_CODE"));
		String msg = null;

		try {

			msg = userdao.updateUser(m);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "user/delete")
	public String userDelete(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {

		UserDao userdao = new UserDao();
		String msg = null;

		try {
			 msg = userdao.deleteUser(request.getParameter("USER_ID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
}
