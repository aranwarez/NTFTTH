/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.ItemCategoryDao;
import com.dao.ItemDao;
import com.dao.ItemTariffDao;
import com.dao.VASServiceDao;
import com.dao.WapAppTariffDao;
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
public class ItemTariffController {

    private static final Logger logger = LoggerFactory.getLogger(com.vas.controller.ItemTariffController.class);

    @RequestMapping(value = "/itemtariff/list", method = RequestMethod.GET)
    public String itemtarifflist(String SERVICE_CODE, String ITEM_CODE, Locale locale, Model model) throws SQLException {
        //System.out.println("nabin code==");
        logger.info("Getting item tariff List", locale);
        ItemTariffDao dao = new ItemTariffDao();
        List<Map<String, Object>> list = null;
        model.addAttribute("fx", "Item Tariff List");
        model.addAttribute("data_list", list);
        VASServiceDao VASSER = new VASServiceDao();
        model.addAttribute("VASSer_list", VASSER.getVasServiceList());
        ItemDao cat = new ItemDao();
        model.addAttribute("Item_list", cat.getItemList());
        return "itemtariff/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "dialogitemtariff")
    public String dialogitemtariff(Model model, Locale locale) {
        return "itemtariff/itemtariffdialog";
    }

    // getting list of all Item tariff with parameter
    @ResponseBody
    @RequestMapping(value = "/itemtariff/getItemtarifflist", method = RequestMethod.GET)
    public List<Map<String, Object>> getItemtarifflist(String SERVICE_CODE, String ITEM_CODE, Locale locale, Model model,
            HttpSession session) throws SQLException {
        ItemTariffDao dao = new ItemTariffDao();
        return dao.getItemTariffList(SERVICE_CODE, ITEM_CODE);
    }

    @RequestMapping(value = "/itemtariff/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSItemtariff(String ITEM_CODE, String SERVICE_CODE, String AMOUNT, String EFFECTIVE_DT,
            String ACTIVE_FLAG, HttpSession session, Model model, Locale locale) {

        logger.info("Save Item Tariff {}.", locale);
        ItemTariffDao dao = new ItemTariffDao();
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Itemtariff controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveItemTariff(ITEM_CODE, SERVICE_CODE, AMOUNT,
                    EFFECTIVE_DT, ACTIVE_FLAG, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    
    @RequestMapping(value = "/itemtariff/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateItemtariff(String ID_NO, String ITEM_CODE, String SERVICE_CODE, String AMOUNT, String EFFECTIVE_DT,
            String ACTIVE_FLAG, HttpSession session, Model model, Locale locale) {

        logger.info("Updata item Tariff {}.", locale);
        ItemTariffDao dao = new ItemTariffDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Item Tariff list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateItemTariff(ID_NO, ITEM_CODE, SERVICE_CODE, AMOUNT, EFFECTIVE_DT, ACTIVE_FLAG, USER);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/itemtariff/delete", method = RequestMethod.POST)
    @ResponseBody
    public String itemtariffDelete(String ID_NO, Model model, Locale locale) {
        logger.info("delete Item Tariff", locale);
        ItemTariffDao dao = new ItemTariffDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteItemTariff(ID_NO);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

}
