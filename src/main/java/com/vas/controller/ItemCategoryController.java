/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.ItemCategoryDao;
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
public class ItemCategoryController {
    private static final Logger logger = LoggerFactory.getLogger(com.vas.controller.ItemCategoryController.class);
    @RequestMapping(value = "/itemcategory/list", method = RequestMethod.GET)
    public String itemcategorylist(Locale locale, Model model) throws SQLException {
//System.out.println("nabin code==");
        logger.info("Getting itemcategory List", locale);
        ItemCategoryDao dao = new ItemCategoryDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getItemCategoryList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Item Category List");
        model.addAttribute("data_list", list);
        //COADao COA = new COADao();
        //model.addAttribute("COA_list", COA.getCOAlist());

        return "itemcategory/list";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "dialogitemcategory")
    public String dialogitemcategory(Model model, Locale locale) {
        return "itemcategory/itemcategorydialog";

    }
    
    @RequestMapping(value = "/itemcategory/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSItemCategory(String CATEGORY_CODE, String DESCRIPTION,  HttpSession session, Model model, Locale locale) {
        logger.info("Save itemcategory {}.", locale);
        ItemCategoryDao dao = new ItemCategoryDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "item category controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveItemCategory(CATEGORY_CODE, DESCRIPTION, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    
    @RequestMapping(value = "/itemcategory/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateItemCategory(String CATEGORY_CODE, String DESCRIPTION, HttpSession session, Model model, Locale locale) {

        logger.info("Updata itemcategory {}.", locale);
        ItemCategoryDao dao = new ItemCategoryDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "itemcategory controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateItemCategory(CATEGORY_CODE, DESCRIPTION, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/itemcategory/delete", method = RequestMethod.POST)
    @ResponseBody
    public String ItemCategoryDelete(String CATEGORY_CODE, Model model, Locale locale) {
        logger.info("delete itemcategory", locale);
        ItemCategoryDao dao = new ItemCategoryDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteItemCategory(CATEGORY_CODE);            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
}
