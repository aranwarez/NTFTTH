/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.CommonDateDao;
import com.dao.ImpNtSpDao;
import com.dao.InvoiceLockDao;
import com.dao.SpDao;
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
public class InvoiceLockController {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceLockController.class);

    @RequestMapping(value = "/invoicelock/list", method = RequestMethod.GET)
    public String invoicelocklist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH, String SERVICE_CODE, 
            String POST_FLAG, Locale locale, Model model) throws SQLException {
        logger.info("Getting invoice lock List", locale);
        InvoiceLockDao dao = new InvoiceLockDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getBillMasterList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, POST_FLAG);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Invoice List");
        model.addAttribute("data_list", list);
        VASServiceDao VASSER = new VASServiceDao();
        model.addAttribute("VASSer_list", VASSER.getVasServiceList());
        CommonDateDao DAT = new CommonDateDao();
        model.addAttribute("Date_list", DAT.getDateList());
        CommonDateDao mon = new CommonDateDao();
        model.addAttribute("Mon_list", mon.getNepMonthList());
        return "invoicelock/list";
    }
    // getting list of all Bill Master
    @ResponseBody
    @RequestMapping(value = "/invoicelock/getInvoiceLockList", method = RequestMethod.GET)
    public List<Map<String, Object>> getInvoiceLockList(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
            String SERVICE_CODE, String POST_FLAG, Locale locale, Model model, HttpSession session)
            throws SQLException {
        InvoiceLockDao dao = new InvoiceLockDao();
        return dao.getBillMasterList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, SERVICE_CODE, POST_FLAG);
    }
    
    // getting list of all Bill Master
    @ResponseBody
    @RequestMapping(value = "/invoicelock/getInvoiceDetailList", method = RequestMethod.GET)
    public List<Map<String, Object>> getInvoiceDetailList(String TRANS_NO, Locale locale, Model model, HttpSession session)
            throws SQLException {
        InvoiceLockDao dao = new InvoiceLockDao();
        return dao.getBillDetailList(TRANS_NO);
    }
    

    @RequestMapping(method = RequestMethod.GET, value = "dialoginvoicelock")
    public String dialoginvoicelock(Model model, Locale locale) {
        return "invoicelock/invoicelockdialog";
    }
    
}
