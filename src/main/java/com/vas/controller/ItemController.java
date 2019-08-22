/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.ItemCategoryDao;
import com.dao.ItemDao;
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
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(com.vas.controller.ItemController.class);
    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    public String itemlist(Locale locale, Model model) throws SQLException {
//System.out.println("nabin code==");
        logger.info("Getting item List", locale);
        ItemDao dao = new ItemDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getItemList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Item List");
        model.addAttribute("data_list", list);
        ItemCategoryDao cat = new ItemCategoryDao();
        model.addAttribute("ItemCategory_list", cat.getItemCategoryList());

        return "item/list";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "dialogitem")
    public String dialogitemcategory(Model model, Locale locale) {
        return "item/itemdialog";
    }
    
    @RequestMapping(value = "/item/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSItem(String ITEM_CODE, String DESCRIPTION, String CATEGORY_CODE, String IS_RECURRING,
            String TAXABLE_AMT, String VATABLE_AMT, String OWN_AMT, String CASH_SALE_FLAG, String ACTIVE_FLAG, HttpSession session, Model model, Locale locale) {
        logger.info("Save item {}.", locale);
        ItemDao dao = new ItemDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "item controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveItem(ITEM_CODE, DESCRIPTION, CATEGORY_CODE, IS_RECURRING,
            TAXABLE_AMT, VATABLE_AMT, OWN_AMT, CASH_SALE_FLAG, ACTIVE_FLAG, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping(value = "/item/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateItem(String ITEM_CODE, String DESCRIPTION, String CATEGORY_CODE, String IS_RECURRING,
            String TAXABLE_AMT, String VATABLE_AMT, String OWN_AMT, String CASH_SALE_FLAG, String ACTIVE_FLAG, HttpSession session, Model model, Locale locale) {

        logger.info("Updata item {}.", locale);
        ItemDao dao = new ItemDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "item controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateItem(ITEM_CODE, DESCRIPTION, CATEGORY_CODE, IS_RECURRING,
            TAXABLE_AMT, VATABLE_AMT, OWN_AMT, CASH_SALE_FLAG, ACTIVE_FLAG, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/item/delete", method = RequestMethod.POST)
    @ResponseBody
    public String ItemDelete(String ITEM_CODE, Model model, Locale locale) {
        logger.info("delete item", locale);
        ItemDao dao = new ItemDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteItem(ITEM_CODE);            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
}
