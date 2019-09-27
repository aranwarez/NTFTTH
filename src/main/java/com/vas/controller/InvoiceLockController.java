/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.InvoiceLockDao;
import com.dao.VASServiceDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.MenuAccess;
import com.model.UserInformationModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author nabin
 */
@Controller
public class InvoiceLockController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceLockController.class);
    private static final String classname = "../invoicelock/list";

    @RequestMapping(value = "/invoicelock/list", method = RequestMethod.GET)
    public String invoicelocklist(String IMP_YEAR, String IMP_PERIOD, String IMP_MONTH,
            String POST_FLAG, Locale locale, Model model,  HttpSession session) throws SQLException {
        logger.info("Getting invoice lock List", locale);
        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
        MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
        if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
            model.addAttribute("fx", "Unauthorized Page for this role!!");
            return "/home";
        }
        InvoiceLockDao dao = new InvoiceLockDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getBillMasterList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, POST_FLAG);
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
            String POST_FLAG, Locale locale, Model model, HttpSession session)
            throws SQLException {
        InvoiceLockDao dao = new InvoiceLockDao();
        return dao.getBillMasterList(IMP_YEAR, IMP_PERIOD, IMP_MONTH, POST_FLAG);
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

    @ResponseBody
    @RequestMapping(value = "/invoicelock/post", method = RequestMethod.POST)
    public String invoicelockPost(String Transno_List, HttpSession session, Model model, Locale locale){
    //    logger.info("post invoicelock", locale);
    UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
    MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
        if (menuaccess == null || menuaccess.getPOST_FLAG().equals("N")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Unauthorized");
        }
        try{
        InvoiceLockDao dao = new InvoiceLockDao();
        String msg = null;
        ObjectMapper mapper = new ObjectMapper();
        List<String> Transno = mapper.readValue(Transno_List,new TypeReference<List<String>>()  {});
        
        msg = dao.post_new(Transno, user.getUSER_ID());
        return msg;}
        catch(Exception e){
        e.printStackTrace();
        }
    return "Error";
    }
    
    @RequestMapping(value = "/invoicelock/delete", method = RequestMethod.POST)
    @ResponseBody
    public String invoicelockDelete(String TRANS_NO, HttpSession session, Model model, Locale locale) {
        logger.info("delete invoice", locale);
        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
        MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
        if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Unauthorized");
        }
        InvoiceLockDao dao = new InvoiceLockDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteInvoicelock(TRANS_NO);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
    
    @RequestMapping(value = "/invoicelock/unpost", method = RequestMethod.POST)
    @ResponseBody
    public String invoicelockUnpost(String TRANS_NO,HttpSession session, Model model, Locale locale) {
        logger.info("delete invoice", locale);
        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
        InvoiceLockDao dao = new InvoiceLockDao();
//        System.out.println("unpost Transno" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.Unpost(TRANS_NO,user.getUSER_ID());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
    
}
