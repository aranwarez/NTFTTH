package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.aaa.dao.AAAStatusDao;
import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.ComplainDao;
import com.dao.MServiceDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.AAAModel;
import com.model.MenuAccess;
import com.model.UserInformationModel;
import com.soap.dao.AAADao;
import com.soap.dao.CPEAPI;
import com.soap.dao.CRMSBalanceDao;
import com.soap.dao.IPTV;

@Controller
public class ComplainController {
	private static final Logger logger = LoggerFactory.getLogger(ComplainController.class);

	private static final String classname = "../complain/list";

	@RequestMapping(value = "/complain/list", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpSession session) throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
		model.addAttribute("fx", "Register New Complaint ");

		// getting list of paramater required

		// model.addAttribute("bank_list", bank.getBankListforCC(user.getCC_CODE()));
		CommonDateDao DAT = new CommonDateDao();
		model.addAttribute("Date_list", DAT.getDateList());

		MServiceDao services = new MServiceDao();
		model.addAttribute("Services", services.getServiceList());

		return "/complain/list";

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getCustomerInfo", method = RequestMethod.GET)
	public String getItemTariff(String info, String infotype, Locale locale, Model model, HttpSession session)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		CRMSBalanceDao dao = new CRMSBalanceDao();
		try {
			String msg = dao.getCustomerDetail(info, infotype);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getStatusInfo", method = RequestMethod.GET)
	public String getStatussinfo(String cpeSn, Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		AAADao dao = new AAADao();
		try {
			String msg = dao.getCPEINFO(cpeSn);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getComplainServiceInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getComplainServiceInfo(String cpeSn, Locale locale, Model model,
			HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		ComplainDao dao = new ComplainDao();
		try {
			List<Map<String, Object>> msg = dao.getComplainbyCPESN(cpeSn);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/complain/dialog")
	public String dialogrole(Model model, Locale locale) {
		return "complain/dialog";

	}

	@RequestMapping(value = "/complain/Register", method = RequestMethod.POST)
	@ResponseBody
	public String Complainregister(String JSON, String Complain_no, String Remarks, String SRV_NO, String contactName,
			String fdcname, String teamname, String Supervisorname, String SupervisorContno, String Teamleader,
			String TeamleaderNo, Boolean solved, String CUSTOMER_NAME, String CONTACT_NO, String OLT_PORT,
			String FAP_LOCATION, String FAP_PORT, String CPE_RX_LVL,String ODF_PORT, Model model, Locale locale, HttpSession session)
			throws SQLException {
		logger.info("Registering new service for", locale);
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		ComplainDao dao = new ComplainDao();
		ObjectMapper mapper = new ObjectMapper();

		String msg = null;
		try {
			List<Map<String, Object>> myObjects = mapper.readValue(JSON,
					new TypeReference<List<Map<String, Object>>>() {
					});
			if (solved) {
				msg = dao.solveProblem(myObjects, "1", SRV_NO, Complain_no, contactName, Remarks, user.getUSER_ID(),
						fdcname, teamname, Supervisorname, SupervisorContno, Teamleader, TeamleaderNo, CUSTOMER_NAME,
						CONTACT_NO, OLT_PORT, FAP_LOCATION, FAP_PORT, CPE_RX_LVL,ODF_PORT);
			} else
				msg = dao.saveProblem(myObjects, "1", SRV_NO, Complain_no, contactName, Remarks, user.getUSER_ID(),
						fdcname, teamname, Supervisorname, SupervisorContno, Teamleader, TeamleaderNo, CUSTOMER_NAME,
						CONTACT_NO, OLT_PORT, FAP_LOCATION, FAP_PORT, CPE_RX_LVL,ODF_PORT);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}


	//Resolve service to existing token
	@RequestMapping(value = "/complain/addthencloservexistingtoken", method = RequestMethod.POST)
	@ResponseBody
	public String addthencloservexistingtoken(String JSON,String tokenid,HttpSession session)
			throws SQLException {
		logger.info("Adding then closing service to existing tokenid : ",tokenid);
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		ComplainDao dao = new ComplainDao();
		ObjectMapper mapper = new ObjectMapper();

		String msg = null;
		try {
			List<Map<String, Object>> myObjects = mapper.readValue(JSON,
					new TypeReference<List<Map<String, Object>>>() {
					});
			msg=dao.addthencloseProblem(myObjects, tokenid, user.getUSER_ID());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	
	//add service to existing token
	@RequestMapping(value = "/complain/addsrvexistingtoken", method = RequestMethod.POST)
	@ResponseBody
	public String addsrvexistingtoken(String JSON,String tokenid,HttpSession session)
			throws SQLException {
		logger.info("Adding service to existing tokenid : ",tokenid);
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}

		ComplainDao dao = new ComplainDao();
		ObjectMapper mapper = new ObjectMapper();

		String msg = null;
		try {
			List<Map<String, Object>> myObjects = mapper.readValue(JSON,
					new TypeReference<List<Map<String, Object>>>() {
					});
			msg=dao.addProblem(myObjects, tokenid, user.getUSER_ID());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	
	// CPE detial information
	@ResponseBody
	@RequestMapping(value = "/complain/getCPETMSStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCPETMSStatus(String cpeSn, Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

//	<---	session validation for TMS status username password --->

//		if((!username.isEmpty()) || !password.isEmpty()) {
//		
//			System.err.println("username not empty");
//			
//			session.setAttribute("TMSusername", username);
//			session.setAttribute("TMSpassword", password);
//		}
//		if(session.getAttribute("TMSusername")==null || session.getAttribute("TMSusername").toString().isEmpty()) {
//			return "{\"Error\":\"Enter Credentials\"}";
//		}
//		
//		else if(session.getAttribute("TMSusername")!=null && session.getAttribute("TMSpassword")!=null) {
//			System.err.println("getting password from session");
//			username=session.getAttribute("TMSusername").toString();
//			password=session.getAttribute("TMSpassword").toString();
//		}
//		
//		

		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		CPEAPI ddd = new CPEAPI();

		try {
			String msg = ddd.getCPEINFO(cpeSn);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getCRMSServiceStatus", method = RequestMethod.GET)
	public String getCRMSServiceStatus(String MDN, Locale locale, Model model, HttpSession session)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		CRMSBalanceDao cbd = new CRMSBalanceDao();

		try {
			String msg = cbd.getMDNState(MDN);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getCRMSServiceBalance", method = RequestMethod.GET)
	public String getCRMSServiceBalance(String MDN, Locale locale, Model model, HttpSession session)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		CRMSBalanceDao cbd = new CRMSBalanceDao();

		try {
			String msg = cbd.getMDNBalance(MDN);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getCRMSQueryFreeResource", method = RequestMethod.GET)
	public String getCRMSQueryFreeResource(String MDN,String FUP, Locale locale, Model model, HttpSession session)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		CRMSBalanceDao cbd = new CRMSBalanceDao();

		try {
			String msg = cbd.getMDNQueryFreeResource(MDN,FUP);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getAAAstatus1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public AAAModel getgetAAAstatus1(String FTTHDatanum, Locale locale, Model model, HttpSession session)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		AAAStatusDao cbd = new AAAStatusDao();
		AAAModel obj=new AAAModel();
		try {
			obj = cbd.getAAAstatus(FTTHDatanum);
			return obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	
	@ResponseBody
	@RequestMapping(value = "/complain/getAAAstatusviewAuthenticationlog", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getAAAstatusviewAuthenticationlog(String FTTHDatanum,String mmdd, Locale locale, Model model, HttpSession session)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		AAAStatusDao cbd = new AAAStatusDao();
		
		try {
			return cbd.getAuthenticationlog(FTTHDatanum, mmdd);
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/complain/getAAAstatusviewAccountinglog", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getAAAstatusviewAccountinglog(String FTTHDatanum,String mmdd, Locale locale, Model model, HttpSession session)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		AAAStatusDao cbd = new AAAStatusDao();
		
		try {
			return cbd.getAccountinglog(FTTHDatanum, mmdd);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	// IPTV detail information
	@ResponseBody
	@RequestMapping(value = "/complain/getIPTVDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getIPTVDetail(String IPTV, Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		IPTV obj= new IPTV();

		try {
			String msg = obj.getIPTVINFO(IPTV);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	
	// IPTV app detail information
	@ResponseBody
	@RequestMapping(value = "/complain/getIPTVAPPDETAIL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getIPTVAPPDETAIL(String IPTV, Locale locale, Model model, HttpSession session) throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
		}
		IPTV obj= new IPTV();

		try {
			String msg = obj.getIPTVAPPDETAIL(IPTV);
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	// IPTV Serial Number information from CRM
		@ResponseBody
		@RequestMapping(value = "/complain/getIPTVCPEMAP", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public List<Map<String, String>> getIPTVCPEMAP(String CPE, Locale locale, Model model, HttpSession session) throws SQLException {
			UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}
			CRMSBalanceDao obj=new CRMSBalanceDao();

			try {
				return obj.getIPTVID(CPE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}


		// IPTV Get STB from IPTV Number
		@ResponseBody
		@RequestMapping(value = "/complain/getSTBfrmIPTV", method = RequestMethod.GET)
		public String getSTBfrmIPTV(String IPTV, Locale locale, Model model, HttpSession session) throws SQLException {
			UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
			if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
			}
			CRMSBalanceDao obj = new CRMSBalanceDao();

			try {
				return obj.getSTBfrmIPTVNumber(IPTV);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
	
}
