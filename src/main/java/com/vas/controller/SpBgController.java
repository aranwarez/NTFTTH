/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.BankDao;
import com.dao.CommonDateDao;
import com.dao.CpDao;
import com.dao.PackageDao;
import com.dao.SpBgDao;
import com.dao.SpDao;
import com.dao.SpServiceDao;
import com.dao.StreamDao;
import com.dao.TranstypeDao;
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
public class SpBgController {

    private static final Logger logger = LoggerFactory.getLogger(SpBgController.class);

    @RequestMapping(value = "/spbg/list", method = RequestMethod.GET)
    public String spbglist(String SP_CODE, String FROM_DT, String TO_DT, String TRANS_CD, String POST_FLAG, Locale locale, Model model) throws SQLException {
        logger.info("Getting SP Bank Deposit/Guarantee List", locale);
        SpBgDao dao = new SpBgDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getSpBgList(SP_CODE, FROM_DT, TO_DT, TRANS_CD, POST_FLAG);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "SP Bank Deposit/Guarantee/Refund List");
        model.addAttribute("data_list", list);
        SpDao sp = new SpDao();
        model.addAttribute("Sp_list", sp.getSpList());
        PackageDao PAC = new PackageDao();
        model.addAttribute("Package_list", PAC.getPackageList());
        SpServiceDao ser = new SpServiceDao();
        model.addAttribute("Service_list", ser.getSpServiceList(SP_CODE));
        TranstypeDao TRA = new TranstypeDao();
        model.addAttribute("Trans_list", TRA.getTranstypeList());       
        BankDao BAN = new BankDao();
        model.addAttribute("Bank_list", BAN.getBankList());       
        CommonDateDao DAT = new CommonDateDao();
        model.addAttribute("Date_list", DAT.getDateList());       
        
        //COADao COA = new COADao();
        //model.addAttribute("COA_list", COA.getCOAlist());

        return "spbg/list";
    }

    // getting list of all SP target
    @ResponseBody
    @RequestMapping(value = "/spbg/getSpbglist", method = RequestMethod.GET)
    public List<Map<String, Object>> getSpbglist(String SP_CODE, String FROM_DT, String TO_DT, String TRANS_CD, String POST_FLAG, Locale locale, Model model,
            HttpSession session) throws SQLException {
        SpBgDao dao = new SpBgDao();
        return dao.getSpBgList(SP_CODE, FROM_DT, TO_DT, TRANS_CD, POST_FLAG);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "dialogspbg")
    public String dialogspbg(Model model, Locale locale) {
        return "spbg/spbgdialog";
    }
    
    @RequestMapping(value = "/spbg/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSSpbg(String TRANS_CD, String SP_CODE, String TRANS_DT, String BANK_CD, String BANK_GUARENTEE_DATE,
            String AMT, String BANK_VALIDITY_DATE, String REMARKS, Model model, Locale locale) {

        logger.info("Save Bank Deposit/Refund/Granrentee {}.", locale);
        SpBgDao dao = new SpBgDao();        
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        String USER = "NEpal";
        model.addAttribute("fx", "Cp controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveSpBg(TRANS_CD, SP_CODE, TRANS_DT, BANK_CD, BANK_GUARENTEE_DATE, AMT, BANK_VALIDITY_DATE, USER, REMARKS);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    
    @RequestMapping(value = "/spbg/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateSpbg(String TRANS_ID, String TRANS_CD, String SP_CODE, String TRANS_DT, String BANK_CD, String BANK_GUARENTEE_DATE,
            String AMT, String BANK_VALIDITY_DATE, String REMARKS, Model model, Locale locale) {

        logger.info("Bank Deposit/Refund/Granrentee {}", locale);
        SpBgDao dao = new SpBgDao();        
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        String USER = "NEpal";
        model.addAttribute("fx", "Cp controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateSpBg(TRANS_ID, TRANS_CD, SP_CODE, TRANS_DT, BANK_CD, BANK_GUARENTEE_DATE, AMT, BANK_VALIDITY_DATE, USER, REMARKS);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping(value = "/spbg/delete", method = RequestMethod.POST)
    @ResponseBody
    public String spbgDelete(String TRANS_ID, Model model, Locale locale) {
        logger.info("delete Content provider", locale);
        SpBgDao dao = new SpBgDao();        
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteSpBg(TRANS_ID);            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }


}
