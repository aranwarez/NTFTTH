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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.dao.CommonMenuDao;
import com.dao.OLTDao;
import com.dao.RegionDao;
import com.dao.UserDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;

@Controller
public class OLTContactController {
	private static final Logger logger = LoggerFactory.getLogger(OLTContactController.class);

	private static final String classname = "../oltcontact/list";

	@RequestMapping(value = "/oltcontact/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpServletRequest request, HttpSession session)
			throws SQLException {

		// LIST_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		RegionDao dao = new RegionDao();
		List<Region> regionlist = null;
		List<Map<String, Object>> levelcontrollist = null;

		try {

			regionlist = dao.getlistByUserFDC(user.getUSER_ID(), user.getUSER_LEVEL());

			levelcontrollist = UserDao.getUserDetailByOfficeCode(user.getOFFICE_CODE());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		model.addAttribute("fx", "OLT Contact Information");
		model.addAttribute("regionlist", regionlist);
		model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());
		model.addAttribute("levelcontrollist", levelcontrollist);
		model.addAttribute("menuaccess", menuaccess);

		return "oltcontact/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "useroltcontactbody")
	public String userfdcbody(Model model, Locale locale) {

		return "oltcontact/oltcontactbody";

	}

	@RequestMapping(value = "/savecontactolt", method = RequestMethod.POST)
	@ResponseBody

	public String saveoltcontact(String OLTCODE, String ContactNo, String ContactName, String menu_mode,
			HttpServletRequest request, Model model, Locale locale, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException, SQLException {

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

//		SaveServiceTeam
		OLTDao dao = new OLTDao();
		String msg = null;
		try {

			msg = dao.saveoltcontact(OLTCODE, ContactNo, ContactName, user.getUSER_ID());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}
	
	
	@RequestMapping(value = "/updateoltcontact", method = RequestMethod.POST)
	@ResponseBody
	public String updateoltcontact(String OLTCODE, String ContactNo, String ContactName, String menu_mode,
			HttpServletRequest request, Model model, Locale locale, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException, SQLException {

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		OLTDao dao = new OLTDao();
		String msg = null;
		try {

			msg = dao.updateoltcontact(OLTCODE, ContactNo, ContactName, user.getUSER_ID());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}
	
	@RequestMapping(value = "/deleteoltcontact", method = RequestMethod.POST)
	@ResponseBody
	public String deleteoltcontact(String OLTCODE,
			HttpServletRequest request, Model model, Locale locale, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException, SQLException {

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		OLTDao dao = new OLTDao();
		String msg = null;
		try {

			msg = dao.deleteoltcontact(OLTCODE);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}



	@RequestMapping(method = RequestMethod.GET, value = "oltcontactdialog")
	public String dialogservice(Model model, Locale locale) {

		return "oltcontact/oltcontactdialog";

	}
	

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getOLTContacts",produces = MediaType.APPLICATION_JSON_VALUE)

	public List<Map<String, Object>> getOltContacts(String Region,String zone,String district,String OFFICE_CODE,HttpServletRequest request, HttpServletResponse response) {
		
		List<Map<String, Object>>  list = null;
		OLTDao dao=new OLTDao();
		
		try {
			list = dao.getOLTContact(Region, zone, district, OFFICE_CODE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	
	
	

}
