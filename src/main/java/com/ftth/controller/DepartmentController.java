package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.dao.CommonMenuDao;
import com.dao.DepartmentDao;
import com.dao.TeamDao;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	private static final String classname = "../department/list";

	@RequestMapping(value = "/department/list", method = RequestMethod.GET)
	public String teamlist(Locale locale, Model model, HttpServletRequest request) throws SQLException {

		logger.info("Getting department List", locale);

		// LIST_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		DepartmentDao dao = new DepartmentDao();
		List<Map<String, Object>> list = null;
		try {
			list = dao.getDepartmentList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("fx", "Department Information");
		model.addAttribute("data_list", list);

		return "department/list";
	}

	@ResponseBody
	@RequestMapping(value = "/department/jsonlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getSPtargetist(Locale locale, Model model, HttpSession session)
			throws SQLException {
		DepartmentDao dao = new DepartmentDao();
		return dao.getDepartmentList();

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogdepartment")
	public String dialogservice(Model model, Locale locale) {
		return "department/teamdialog";

	}

	@RequestMapping(value = "/department/saveJS", method = RequestMethod.POST)
	@ResponseBody
	public String saveJSService(String DEPARTMENT_CODE, String ContactNo, String OFFICE_NAME, String AREA_NAME,
			String SECTION, String LANDLINE_NO, String MOBILE_NO, String REMARKS1, String REMARKS2,
			HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

		logger.info("Save department {}.", locale);

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		DepartmentDao dao = new DepartmentDao();

		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();
		model.addAttribute("fx", "Team controller list ");
		model.addAttribute("userName", USER);
		String msg = null;
		try {
			msg = dao.saveDepartment(DEPARTMENT_CODE, ContactNo,  OFFICE_NAME, AREA_NAME, SECTION, LANDLINE_NO,
					MOBILE_NO, REMARKS1, REMARKS2,USER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/department/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateService(String ID, String Department, String ContactNo, String OFFICE_NAME, String AREA_NAME,
			String SECTION, String LANDLINE_NO, String MOBILE_NO, String REMARKS1, String REMARKS2,
			HttpServletRequest request, HttpSession session, Model model, Locale locale) throws SQLException {

		logger.info("Updata Service {}.", locale);

		// EDIT_FLAG

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		DepartmentDao dao = new DepartmentDao();

		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();
		String msg = null;
		try {
			msg = dao.updateDepartment(Department, ContactNo,   OFFICE_NAME, AREA_NAME, SECTION, LANDLINE_NO,
					MOBILE_NO, REMARKS1, REMARKS2,USER,ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/department/delete", method = RequestMethod.POST)
	@ResponseBody
	public String DepartmentDelete(String ID, HttpServletRequest request, Model model, Locale locale)
			throws SQLException {
		logger.info("delete service", locale);

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		DepartmentDao dao = new DepartmentDao();

		String msg = null;
		try {
			msg = dao.DeleteDepartment(ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

}
