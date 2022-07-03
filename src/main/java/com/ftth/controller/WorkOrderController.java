package com.ftth.controller;

import java.sql.SQLException;
import java.util.HashMap;
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

import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.FDCDao;
import com.dao.OLTDao;
import com.dao.RegionDao;
import com.dao.TeamDao;
import com.dao.UserDao;
import com.dao.WorkOrderDao;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;
import com.smpp.SendSMS;
import com.soap.dao.WorkOrderAPI;

@Controller
public class WorkOrderController {

	private static final Logger logger = LoggerFactory.getLogger(WorkOrderController.class);
	private static final String classname = "../workorder/list";

	@RequestMapping(value = "/workorder/list", method = RequestMethod.GET)
	public String teamlist(Locale locale, Model model, HttpServletRequest request) throws SQLException {

		logger.info("Work Order Page", locale);

		// LIST_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

//		adding query component
		RegionDao rdao = new RegionDao();
		List<Region> regionlist = null;

		CommonDateDao DAT = new CommonDateDao();

		List<Map<String, Object>> levelcontrollist = null;

		try {
			regionlist = rdao.getlistByUserFDC(user.getUSER_ID(), user.getUSER_LEVEL());

			// model.addAttribute("Date_list", DAT.getDateList());

			levelcontrollist = UserDao.getUserDetailByOfficeCode(user.getOFFICE_CODE());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("regionlist", regionlist);
		model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());
		model.addAttribute("levelcontrollist", levelcontrollist);

		// /adding query

		WorkOrderDao dao = new WorkOrderDao();
		// List<Map<String, Object>> list = null;
		List<Map<String, Object>> elementlist = null;
		List<Map<String, Object>> fdclist = null;
		List<Map<String, Object>> oltlist = null;

		try {
			elementlist = dao.getWorkOrderElementList();
			// list = dao.getActiveWorkOrder();
			if (user.getUSER_LEVEL().contains("6")) {
				fdclist = FDCDao.getFDCListlowlvl(user.getUSER_ID());
				oltlist = OLTDao.getOLTListlowlvl(user.getUSER_ID());
			}

			else {
				fdclist = FDCDao.getFDCList();
				oltlist = OLTDao.getOLTList();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("workorder_element", elementlist);
		model.addAttribute("location", fdclist);
		model.addAttribute("oltlist", oltlist);
		model.addAttribute("menuaccess", menuaccess);
		model.addAttribute("fx", "Work Order");
		// model.addAttribute("data_list", list);
		model.addAttribute("Date_list", DAT.getDateList());

		return "workorder/list";
	}

	@ResponseBody
	@RequestMapping(value = "/workorder/getJSListWorkOrder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getJSListWorkOrder(Locale locale, Model model, HttpSession session,
			String REGION_CODE, String ZONE_CODE, String DISTRICT_CODE, String OFFICE_CODE, String QFROM_DT,
			String QTO_DT, String qtype, String ACTIVE_FLAG) throws SQLException {

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		WorkOrderDao dao = new WorkOrderDao();
		try {
			if (user.getUSER_LEVEL().equals("6")) {
				return dao.getlowlvlWorkOrderList(REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, QFROM_DT, QTO_DT,
						qtype, ACTIVE_FLAG, user.getUSER_ID());

			} else {
				return dao.getWorkOrderList(REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, QFROM_DT, QTO_DT, qtype,
						ACTIVE_FLAG);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@ResponseBody
	@RequestMapping(value = "/workorder/getActiveWorkOrder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getActiveWorkOrder(Locale locale, Model model, HttpSession session)
			throws SQLException {
		WorkOrderDao dao = new WorkOrderDao();
		try {
			return dao.getActiveWorkOrder();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@ResponseBody
	@RequestMapping(value = "/workorder/getFAPfromFDC", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getFAPfromFDC(Locale locale, Model model, HttpSession session, String FDC) throws SQLException {
		WorkOrderAPI dao = new WorkOrderAPI();
		try {
			return dao.getFAPfromFDC(FDC);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@ResponseBody
	@RequestMapping(value = "/workorder/getCardfromOLT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getCardfromOLT(Locale locale, Model model, HttpSession session, String OLT)
			throws SQLException {
		WorkOrderAPI dao = new WorkOrderAPI();
		try {
			return dao.getCardfromOLT(OLT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@ResponseBody
	@RequestMapping(value = "/workorder/getPortFromCard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getPortFromCard(Locale locale, Model model, HttpSession session, String OLTCARD)
			throws SQLException {
		WorkOrderAPI dao = new WorkOrderAPI();
		try {
			return dao.getPortFromCard(OLTCARD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@ResponseBody
	@RequestMapping(value = "/workorder/getODFFromOLT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getODFFromOLT(Locale locale, Model model, HttpSession session, String OLT) throws SQLException {
		WorkOrderAPI dao = new WorkOrderAPI();
		try {
			return dao.getODFFromOLT(OLT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogworkorder")
	public String dialogservice(Model model, Locale locale) {
		return "workorder/workdialog";

	}

	@RequestMapping(value = "/workorder/saveworkorder", method = RequestMethod.POST)
	@ResponseBody
	public String saveJSService(String type, String value, String starttime, String active_flag, String remarks,
			HttpSession session, Model model, Locale locale, String fdc, String olt) throws SQLException {

		logger.info("Save Work Order {}.", locale);

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		WorkOrderDao dao = new WorkOrderDao();
		WorkOrderAPI APIdao = new WorkOrderAPI();
		SendSMS smsdao = new SendSMS();
		String USER = user.getUSER_ID();
		String msg = null;
		String smsmsg = "Dear Customer,\nWe are having maintenance work at your area. Your service might be interrupted for few hours. Sorry for the inconvenience caused.\nNepal Telecom";
		try {
			msg = dao.saveWorkOrder(type, value, remarks, starttime, active_flag, USER, olt, fdc);
			if (active_flag.equals("Y")) {
				try {
					List<String> MDN = APIdao.getFTTHNumberInfo(type, value);
					for (String mdn : MDN) {
						// send sms to these number regarding work order
						// System.out.println(APIdao.getContactNumber(mdn));

						String contactno = APIdao.getContactNumber(mdn);
						// send sms to only nt mobile number.. taking 10 digit no for now.
						if (contactno.length() == 10) {
							smsdao.sendsms(contactno, smsmsg, "WORKORDER", USER, msg);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "Failed Exception:" + e.getLocalizedMessage();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Sucessfuly Saved Work Order";
	}

	@RequestMapping(value = "/workorder/complete", method = RequestMethod.POST)
	@ResponseBody
	public String completeService(String ID, String endtime, String remarks, HttpSession session, Model model,
			Locale locale) throws SQLException {

		logger.info("Save Work Order {}.", locale);

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		WorkOrderDao dao = new WorkOrderDao();
		WorkOrderAPI APIdao = new WorkOrderAPI();
		String USER = user.getUSER_ID();
		String msg = null;
		try {
			msg = dao.completeWorkOrder(ID, USER, remarks, endtime);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/workorder/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateService(String TEAM_CODE, String DESCRIPTION, HttpServletRequest request, HttpSession session,
			Model model, Locale locale) throws SQLException {

		logger.info("Updata Service {}.", locale);

		// EDIT_FLAG

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		TeamDao dao = new TeamDao();

		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();
		model.addAttribute("fx", "team controller list ");
		model.addAttribute("userName", USER);
		String msg = null;
		try {
			msg = dao.updateTeam(TEAM_CODE, DESCRIPTION, USER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/workorder/delete", method = RequestMethod.POST)
	@ResponseBody
	public String serviceDelete(String TEAM_CODE, HttpServletRequest request, Model model, Locale locale)
			throws SQLException {
		logger.info("delete service", locale);

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		TeamDao dao = new TeamDao();

		String msg = null;
		try {
			msg = dao.DeleteTeam(TEAM_CODE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

}
