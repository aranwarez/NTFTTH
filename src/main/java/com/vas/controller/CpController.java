/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.CpDao;
import com.dao.PackageDao;
import com.dao.SpDao;
import com.dao.SpServiceDao;
import com.dao.StreamDao;
import com.dao.VASServiceDao;
import com.model.UserInformationModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nabin
 */
@Controller
public class CpController {

    private static final Logger logger = LoggerFactory.getLogger(CpController.class);

    @RequestMapping(value = "/cp/list", method = RequestMethod.GET)
    public String cplist(String SP_CODE, String SERVICE_CODE, Locale locale, Model model) throws SQLException {
        logger.info("Getting Content Provider List", locale);
        CpDao dao = new CpDao();
        List<Map<String, Object>> list = null;
//        try {
//            list = dao.getCpList(SP_CODE, SERVICE_CODE);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        model.addAttribute("fx", "Content Provider List");
        model.addAttribute("data_list", list);
        SpDao sp = new SpDao();
        model.addAttribute("Sp_list", sp.getSpList()); 
        PackageDao PAC = new PackageDao();
        model.addAttribute("Package_list", PAC.getPackageList());
        StreamDao str = new StreamDao();
        model.addAttribute("Stream_list", str.getStreamList());
        SpServiceDao ser = new SpServiceDao();
        model.addAttribute("Service_list", ser.getSpServiceList(SP_CODE));
        VASServiceDao VASSER = new VASServiceDao();
        model.addAttribute("VASSer_list", VASSER.getVasServiceList());
        
        //COADao COA = new COADao();
        //model.addAttribute("COA_list", COA.getCOAlist());

        return "cp/list";
    }

	// getting list of all SP target
	
	@ResponseBody
	@RequestMapping(value = "/cp/getCplist", method = RequestMethod.GET)
	public List<Map<String, Object>> getCplist(String SP_CODE, String SERVICE_CODE, Locale locale, Model model,
			HttpSession session) throws SQLException {
		CpDao dao = new CpDao();
		return dao.getCpList(SP_CODE, SERVICE_CODE);
	}
        
    @RequestMapping(value = "/cp/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSCp(String CP_CODE, String SP_CODE, String SERVICE_CODE, String CP_NAME, String ESME_CODE,
            String SRV_FOR, String PACKAGE_TYPE, String STREAM_TYPE, String START_DT, String END_DT, String SHARING_TYPE,
            String SHARE_NT_PER, String AFS_FLAG, String MIN_QTY, HttpSession session, Model model, Locale locale) {

        logger.info("Save content provider {}.", locale);
        CpDao dao = new CpDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Cp controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveCp(SP_CODE, SERVICE_CODE, CP_NAME, ESME_CODE, SRV_FOR,
                    PACKAGE_TYPE, STREAM_TYPE, START_DT, END_DT, SHARING_TYPE,
                    SHARE_NT_PER, AFS_FLAG, MIN_QTY, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(method = RequestMethod.GET, value = "dialogcp")
    public String dialogcp(Model model, Locale locale) {
        return "cp/cpdialog";
    }

    @RequestMapping(value = "/cp/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateCp(String CP_CODE, String SP_CODE, String SERVICE_CODE, String CP_NAME, String ESME_CODE,
            String SRV_FOR, String PACKAGE_TYPE, String STREAM_TYPE, String START_DT, String END_DT, String SHARING_TYPE,
            String SHARE_NT_PER, String AFS_FLAG, String MIN_QTY, HttpSession session, Model model, Locale locale) {

        logger.info("Updata Content Provider {}.", locale);
        CpDao dao = new CpDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Cp controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateCp(CP_CODE, SP_CODE, SERVICE_CODE, CP_NAME, ESME_CODE, SRV_FOR,
                    PACKAGE_TYPE, STREAM_TYPE, START_DT, END_DT, SHARING_TYPE,
                    SHARE_NT_PER, AFS_FLAG, MIN_QTY, USER);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/cp/delete", method = RequestMethod.POST)
    @ResponseBody
    public String cpDelete(String CP_CODE, Model model, Locale locale) {
        logger.info("delete Content provider", locale);
        CpDao dao = new CpDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteCp(CP_CODE);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }

}
