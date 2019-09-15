package com.vas.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.NettingDao;
import com.dao.SpDao;
import com.dao.VASServiceDao;
import com.model.Menu;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class NettingController {
	private static final Logger logger = LoggerFactory.getLogger(ImpNtSpController.class);

	@RequestMapping(value = "/Netting/list", method = RequestMethod.GET)
	public String menuList(Locale locale, Model model, HttpServletRequest request, HttpSession session)
			throws Exception {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		String url = request.getServletPath();
		List<Menu> list = null;

//menu code should know before validate
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), url);
		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		model.addAttribute("MENU_ACCESS", menuaccess);
//		model.addAttribute("EDIT_FLAG", menuaccess.getEDIT_FLAG());
//		model.addAttribute("DELETE_FLAG", menuaccess.getDELETE_FLAG());
//		model.addAttribute("POST_FLAG", menuaccess.getPOST_FLAG());
//		model.addAttribute("CANCEL_FLAG", menuaccess.getCANCEL_FLAG());
//		model.addAttribute("fx", "Netting List");
		model.addAttribute("data_list", list);

		VASServiceDao VASSER = new VASServiceDao();
		model.addAttribute("VASSer_list", VASSER.getVasServiceList());
		CommonDateDao DAT = new CommonDateDao();
		model.addAttribute("Date_list", DAT.getDateList());
		CommonDateDao mon = new CommonDateDao();
		model.addAttribute("Mon_list", mon.getNepMonthList());
		SpDao sp = new SpDao();
		model.addAttribute("Sp_list", sp.getSpList());

		return "netting/list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogNetting")
	public String dialogrole(Model model, Locale locale) {
		return "netting/ntdialog";

	}

	// getting list of all NT/SP Netting
	@ResponseBody
	@RequestMapping(value = "/netting/getImpNtSplist", method = RequestMethod.GET)

	public List<Map<String, Object>> getImpNtSplist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
			String SERVICE_CODE, String NT_SP, String POST_FLAG, Locale locale, Model model, HttpSession session)
			throws SQLException {

		NettingDao dao = new NettingDao();
		if (NT_SP.equals("NT")) {
			return dao.getImpNTList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, POST_FLAG);
		} else {
			return dao.getImpSPList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, POST_FLAG);

		}

	}

	@RequestMapping(value = "/Netting/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateImpntsp(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
			String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST,
			String TRANS_NO, String SEQ_NO, Model model, Locale locale, HttpSession session) {

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		logger.info("Updata Imp Nt sp {}.", locale);
		NettingDao dao = new NettingDao();// System.out.println("uddate Servce code==" + SERVICE_CODE);
		String msg = null;
		try {
			if (NT_SP.equals("NT"))
				msg = dao.updateImpNt(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, CATEGORY, CP_DESC, S_NO,
						ESME_CODE, MO_1ST, MT_1ST, user.getUSER_ID(), TRANS_NO, SEQ_NO);
			else
				msg = dao.updateImpSP(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, CATEGORY, CP_DESC, S_NO,
						ESME_CODE, MO_1ST, MT_1ST, user.getUSER_ID(), TRANS_NO, SEQ_NO);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/Netting/saveNTSP", method = RequestMethod.POST)
	@ResponseBody
	public String saveNettingSImpntsp(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE,
			String NT_SP, String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST,
			Model model, Locale locale, HttpSession session) {
		String msg = "Bad";

		try {

			logger.info("Save Imp nt sp {}.", locale);
			UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

			NettingDao dao = new NettingDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
			String USER = user.getUSER_ID();

			if (NT_SP.equals("NT"))

				msg = dao.saveImpNT(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, CATEGORY, CP_DESC, S_NO,
						ESME_CODE, MO_1ST, MT_1ST, USER);

			else
				msg = dao.saveImpSP(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, CATEGORY, CP_DESC, S_NO,
						ESME_CODE, MO_1ST, MT_1ST, USER);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/Netting/postExcelDataTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String postExcelDataTrans(Model model, Locale locale, String IMP_YEAR, String Period, String IMP_MONTH,
			String Services, String NT_SP, String SERVICE_CODE, String IMP_PERIOD, HttpSession session)
			throws SQLException {
		logger.info("Saving Excel data in TMP table in DB {}.", locale);
		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();
		NettingDao dao = new NettingDao();
		String msg = dao.postExcelImportData(IMP_YEAR, IMP_PERIOD, IMP_MONTH, Services, NT_SP, USER);
		return msg;
	}

	// getting list of all Sharing Non Sharing Netting
	@ResponseBody
	@RequestMapping(value = "/netting/getSharingData", method = RequestMethod.GET)

	public List<Map<String, Object>> getSharinglist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
			String SERVICE_CODE, String SHARING_TYPE, String POST_FLAG, Locale locale, Model model)
			throws SQLException {

		NettingDao dao = new NettingDao();

		return dao.getNonSharing(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, SHARING_TYPE, POST_FLAG);

	}
        
        @RequestMapping(value = "/Netting/postNettingtoBill", method = RequestMethod.POST)
	@ResponseBody
	public String postNettingtoBill(Model model, Locale locale, String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
			String SERVICE_CODE,  HttpSession session)
			throws SQLException {
		logger.info("Posting netting to bill", locale);
		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
		String USER = userinfo.getUSER_ID();
		NettingDao dao = new NettingDao();
		String msg = dao.postNettingtoBill(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, USER);
		return msg;
	}

}
