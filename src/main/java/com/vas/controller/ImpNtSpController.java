/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.CommonDateDao;
import com.dao.CpDao;
import com.dao.ImpNtSpDao;
import com.dao.PackageDao;
import com.dao.SpDao;
import com.dao.VASServiceDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
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
    public String impntsplist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
            String POST_FLAG, Locale locale, Model model) throws SQLException {
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
        model.addAttribute("Mon_list", mon.getNepMonthList());
        SpDao sp = new SpDao();
        model.addAttribute("Sp_list", sp.getSpList());
        return "impntsp/list";
    }

    // getting list of all SP target
    @ResponseBody
    @RequestMapping(value = "/impntsp/getImpNtSplist", method = RequestMethod.GET)
    public List<Map<String, Object>> getImpNtSplist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
            String SERVICE_CODE, String NT_SP, String POST_FLAG, Locale locale, Model model, HttpSession session)
            throws SQLException {
        ImpNtSpDao dao = new ImpNtSpDao();
        return dao.getImpNtSpList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, POST_FLAG);
    }

    @RequestMapping(method = RequestMethod.GET, value = "dialogimpntsp")
    public String dialogcp(Model model, Locale locale) {
        return "impntsp/impntspdialog";
    }

    // posting Excel data into db
    @RequestMapping(value = "/impntsp/postExcelData", method = RequestMethod.POST)
    @ResponseBody
    public String saveExcelData(String JSONarrayList, String Filename, Model model, Locale locale, String IMP_YEAR,
            String Period, String IMP_MONTH, String Services, String NT_SP, String SERVICE_CODE, String IMP_PERIOD)
            throws JsonParseException, JsonMappingException, IOException, SQLException {

        logger.info("Saving Excel data in TMP table in DB {}.", locale);
        String USER = "NEpal";
        ObjectMapper mapper = new ObjectMapper();

//		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
//		};
//
//		List<Map<String, Object>> list = mapper.readValue(JSONarrayList, typeRef);
        ImpNtSpDao dao = new ImpNtSpDao();
        List<Map<String, Object>> myObjects = mapper.readValue(JSONarrayList,
                new TypeReference<List<Map<String, Object>>>() {
        });
        String msg = dao.saveExcelData(myObjects, Filename, IMP_YEAR, Period, IMP_MONTH, Services, NT_SP, SERVICE_CODE,
                IMP_PERIOD);
        return msg;
    }

    @RequestMapping(value = "/impntsp/postExcelDataTransaction", method = RequestMethod.POST)
    @ResponseBody
    public String postExcelDataTrans(Model model, Locale locale, String IMP_YEAR, String Period, String IMP_MONTH,
            String Services, String NT_SP, String SERVICE_CODE, String IMP_PERIOD) throws SQLException {
        logger.info("Saving Excel data in TMP table in DB {}.", locale);
        String USER = "NEpal";
        ImpNtSpDao dao = new ImpNtSpDao();
        String msg = dao.postExcelImportData(IMP_YEAR, IMP_PERIOD, IMP_MONTH, Services, NT_SP, USER);
        return msg;
    }

    @RequestMapping(value = "/impntsp/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSImpntsp(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, String NT_SP,
            String CATEGORY, String CP_DESC, String S_NO, String ESME_CODE, String MO_1ST, String MT_1ST,
            Model model, Locale locale) {

        logger.info("Save Imp nt sp {}.", locale);
        ImpNtSpDao dao = new ImpNtSpDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        String USER = "NEpal";

        model.addAttribute("fx", "Impntsp controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveImpNtSp(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, NT_SP, CATEGORY, CP_DESC,
                    S_NO, ESME_CODE, MO_1ST, MT_1ST, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
}
