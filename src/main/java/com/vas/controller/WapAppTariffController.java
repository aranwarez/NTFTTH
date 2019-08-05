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
import com.dao.WapAppTariffDao;
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
public class WapAppTariffController {

    private static final Logger logger = LoggerFactory.getLogger(WapAppTariffController.class);

    @RequestMapping(value = "/wapapptariff/list", method = RequestMethod.GET)
    public String wapapptarifflist(String SERVICE_CODE, String PACKAGE_TYPE, Locale locale, Model model) throws SQLException {
        logger.info("Getting VAS Service Tariff List", locale);
        WapAppTariffDao dao = new WapAppTariffDao();
        List<Map<String, Object>> list = null;
        model.addAttribute("fx", "VAS Service Tariff List");
        model.addAttribute("data_list", list);
        PackageDao PAC = new PackageDao();
        model.addAttribute("Package_list", PAC.getPackageList());
        VASServiceDao VASSER = new VASServiceDao();
        model.addAttribute("VASSer_list", VASSER.getVasServiceList());
        return "wapapptariff/list";
    }

    // getting list of all wapapptariff with parameter
    @ResponseBody
    @RequestMapping(value = "/wapapptariff/getWapapptarifflist", method = RequestMethod.GET)
    public List<Map<String, Object>> getWapapptarifflist(String SERVICE_CODE, String PACKAGE_TYPE, Locale locale, Model model,
            HttpSession session) throws SQLException {
        WapAppTariffDao dao = new WapAppTariffDao();
        return dao.getWapAppTariffList(SERVICE_CODE, PACKAGE_TYPE);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "dialogwapapptariff")
    public String dialogwapapptariff(Model model, Locale locale) {
        return "wapapptariff/wapapptariffdialog";
    }
    

    @RequestMapping(value = "/wapapptariff/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSWapapptariff(String SERVICE_CODE, String PACKAGE_TYPE, String RATE,
            String EFFECTIVE_DT, String RANGE_FROM, String RANGE_TO, String MO_MT_RATIO, Model model, Locale locale) {

        logger.info("Save WAP APP Tariff {}.", locale);
        WapAppTariffDao dao = new WapAppTariffDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        String USER = "NEpal";
        model.addAttribute("fx", "Wapapptariff controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveWapAppTariff(SERVICE_CODE, PACKAGE_TYPE, RATE,
            EFFECTIVE_DT, RANGE_FROM, RANGE_TO, USER, MO_MT_RATIO);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/wapapptariff/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateWapapptariff(String TRANS_ID, String SERVICE_CODE, String PACKAGE_TYPE, String RATE,
            String EFFECTIVE_DT, String RANGE_FROM, String RANGE_TO, String MO_MT_RATIO, Model model, Locale locale) {

        logger.info("Updata Wap App Tariff {}.", locale);
        WapAppTariffDao dao = new WapAppTariffDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        String USER = "NEpal";
        model.addAttribute("fx", "WAP APP Tariff list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateWapAppTariff(TRANS_ID, SERVICE_CODE, PACKAGE_TYPE, RATE, 
                    EFFECTIVE_DT, RANGE_FROM, RANGE_TO, MO_MT_RATIO, USER);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/wapapptariff/delete", method = RequestMethod.POST)
    @ResponseBody
    public String wapapptariffDelete(String TRANS_ID, Model model, Locale locale) {
        logger.info("delete Wap App Tariff", locale);
        WapAppTariffDao dao = new WapAppTariffDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteWapAppTariff(TRANS_ID);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
 
}
