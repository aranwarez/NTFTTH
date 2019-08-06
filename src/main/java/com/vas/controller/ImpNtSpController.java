/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.CommonDateDao;
import com.dao.ImpNtSpDao;
import com.dao.VASServiceDao;
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
public class ImpNtSpController {

    private static final Logger logger = LoggerFactory.getLogger(ImpNtSpController.class);

    @RequestMapping(value = "/impntsp/list", method = RequestMethod.GET)
    public String impntsplist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE,
            String NT_SP, String POST_FLAG, Locale locale, Model model) throws SQLException {
        logger.info("Getting ImpMOMT List", locale);
        ImpNtSpDao dao = new ImpNtSpDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getImpNtSpList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, POST_FLAG);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Excel MO MT of NT/SP List");
        model.addAttribute("data_list", list);
        VASServiceDao VASSER = new VASServiceDao();
        model.addAttribute("VASSer_list", VASSER.getVasServiceList());
        CommonDateDao DAT = new CommonDateDao();
        model.addAttribute("Date_list", DAT.getDateList());       
        CommonDateDao mon = new CommonDateDao();
        model.addAttribute("Mon_list",mon.getNepMonthList());
        return "impntsp/list";
    }

    // getting list of all SP target
    @ResponseBody
    @RequestMapping(value = "/impntsp/getImpNtSplist", method = RequestMethod.GET)
    public List<Map<String, Object>> getImpNtSplist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE,
            String NT_SP, String POST_FLAG, Locale locale, Model model,
            HttpSession session) throws SQLException {
        ImpNtSpDao dao = new ImpNtSpDao();
        return dao.getImpNtSpList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, POST_FLAG);
    }

    @RequestMapping(method = RequestMethod.GET, value = "dialogimpntsp")
    public String dialogcp(Model model, Locale locale) {
        return "impntsp/impntspdialog";
    }

}
