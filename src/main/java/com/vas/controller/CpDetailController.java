/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.CpDao;
import com.dao.CpDetailDao;
import com.dao.SpDao;
import com.dao.SpServiceDao;
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
public class CpDetailController {
    private static final Logger logger = LoggerFactory.getLogger(CpDetailController.class);

    @RequestMapping(value = "/cpdetail/list", method = RequestMethod.GET)
    public String cpdetaillist(String SP_CODE, String SERVICE_CODE, String CP_CODE, Locale locale, Model model) throws SQLException {
        logger.info("Getting Content Provider List", locale);
        CpDao dao = new CpDao();
        List<Map<String, Object>> list = null;
        model.addAttribute("fx", "VAS Provider's Client rate");
        model.addAttribute("data_list", list);
        SpDao sp = new SpDao();
        model.addAttribute("Sp_list", sp.getSpList());
        SpServiceDao ser = new SpServiceDao();
        model.addAttribute("Service_list", ser.getSpServiceList(SP_CODE));
        VASServiceDao VASSER = new VASServiceDao();
        model.addAttribute("VASSer_list", VASSER.getVasServiceList());
        return "cpdetail/list";
    }
    
    // getting list of all SP target
    @ResponseBody
    @RequestMapping(value = "/cpdetail/getCpDetaillist", method = RequestMethod.GET)
    public List<Map<String, Object>> getCpDetaillist(String SP_CODE, String SERVICE_CODE, String CP_CODE, Locale locale, Model model,
            HttpSession session) throws SQLException {
        CpDetailDao dao = new CpDetailDao();
        return dao.getCpDetailList(SP_CODE, SERVICE_CODE,CP_CODE);
    }
    @RequestMapping(method = RequestMethod.GET, value = "dialogcpdetail")
    public String dialogcp(Model model, Locale locale) {
        return "cpdetail/cpdetaildialog";
    }
    
    @RequestMapping(value = "/cpdetail/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSCpdetail(String CP_CODE, String RATE, String EFFECTIVE_DT, HttpSession session, Model model, Locale locale) {

        logger.info("Save content provider rate {}.", locale);
        CpDetailDao dao = new CpDetailDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Cp controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveCpDetail(CP_CODE, RATE, EFFECTIVE_DT, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
}
