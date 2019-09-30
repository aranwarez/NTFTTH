/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.BankDao;
import com.dao.BankListDao;
import com.dao.CommonDateDao;
import com.dao.CommonMenuDao;
import com.dao.ReceiptDao;
import com.dao.SpDao;
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
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private static final String classname = "../receipt/list";

    @RequestMapping(value = "/receipt/list", method = RequestMethod.GET)
    public String receiptlist(String CC_CODE, String S_NO, String FROM_DT, String TO_DT, String POST_FLAG, Locale locale, Model model, HttpSession session) throws SQLException {
        logger.info("Getting Receipt List", locale);
        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
        MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
        if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
            model.addAttribute("fx", "Unauthorized Page for this role!!");
            return "/home";
        }
        model.addAttribute("MENU_ACCESS", menuaccess);

        ReceiptDao dao = new ReceiptDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getReceiptList(CC_CODE, S_NO, FROM_DT, TO_DT, POST_FLAG);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Receipt List");
        model.addAttribute("data_list", list);
        SpDao sp = new SpDao();
        model.addAttribute("Sp_list", sp.getSpList());
        BankDao BAN = new BankDao();
        model.addAttribute("Bank_list", BAN.getBankList());
        CommonDateDao DAT = new CommonDateDao();
        model.addAttribute("Date_list", DAT.getDateList());
        BankListDao BANL = new BankListDao();
        model.addAttribute("OBank_list", BANL.getBankList());

        return "receipt/list";
    }

    // getting list of all SP target
    @ResponseBody
    @RequestMapping(value = "/receipt/getReceiptlist", method = RequestMethod.GET)
    public List<Map<String, Object>> getReceiptlist(String CC_CODE, String S_NO, String FROM_DT, String TO_DT, String POST_FLAG, Locale locale, Model model,
            HttpSession session) throws SQLException {
        ReceiptDao dao = new ReceiptDao();
        return dao.getReceiptList(CC_CODE, S_NO, FROM_DT, TO_DT, POST_FLAG);
    }

    @RequestMapping(method = RequestMethod.GET, value = "dialogreceipt")
    public String dialogreceipt(Model model, Locale locale) {
        return "receipt/receiptdialog";
    }

    @RequestMapping(value = "/receipt/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSReceipt(String CC_CODE, String RECEIPT_NO, String RECEIPT_DT, String S_NO, String BANK_CD, String BANK_NAME,
            String CHEQUE_NO, String PAID_AMT, String REMARKS, HttpSession session, Model model, Locale locale) {
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        MenuAccess menuaccess = CommonMenuDao.checkAccess(userinfo.getROLE_CODE(), classname);
        if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Unauthorized");
        }
        logger.info("Save Receipt.", locale);
        ReceiptDao dao = new ReceiptDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Cp controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveReceipt(CC_CODE, RECEIPT_NO, RECEIPT_DT, S_NO, BANK_CD, BANK_NAME,
                    CHEQUE_NO, PAID_AMT, USER, REMARKS);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/receipt/delete", method = RequestMethod.POST)
    @ResponseBody
    public String receiptDelete(String RECEIPT_NO, HttpSession session, Model model, Locale locale) {
        logger.info("delete Content provider", locale);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");

        MenuAccess menuaccess = CommonMenuDao.checkAccess(userinfo.getROLE_CODE(), classname);
        if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Unauthorized");
        }

        ReceiptDao dao = new ReceiptDao();
        String USER = userinfo.getUSER_ID();
        String msg = null;
        try {
            msg = dao.DeleteReceipt(RECEIPT_NO, USER);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
// getting list of all SP wise Service List
    @ResponseBody
    @RequestMapping(value = "/receipt/getSpdue", method = RequestMethod.GET)
    public String getSpdue(String SP_CODE, Locale locale, Model model, HttpSession session)
            throws SQLException {
        ReceiptDao dao = new ReceiptDao();
        return dao.getSpdue(SP_CODE);
    }
}
