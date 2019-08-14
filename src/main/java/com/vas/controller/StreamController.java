/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.StreamDao;
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
public class StreamController {
    private static final Logger logger = LoggerFactory.getLogger(StreamController.class);
    @RequestMapping(value = "/stream/list", method = RequestMethod.GET)
    public String streamlist(Locale locale, Model model) throws SQLException {
//System.out.println("nabin code==");
        logger.info("Getting stream List", locale);
        StreamDao dao = new StreamDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getStreamList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Stream List");
        model.addAttribute("data_list", list);

        //COADao COA = new COADao();
        //model.addAttribute("COA_list", COA.getCOAlist());

        return "stream/list";
    }
    
    @RequestMapping(value = "/stream/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSStream(String STREAM_TYPE, String DESCRIPTION, HttpSession session, Model model, Locale locale) {

        logger.info("Save stream {}.", locale);
        StreamDao dao = new StreamDao();
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        model.addAttribute("fx", "Stream controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveStream(STREAM_TYPE, DESCRIPTION, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "dialogstream")
    public String dialogstream(Model model, Locale locale) {
        return "stream/streamdialog";

    }
    
    @RequestMapping(value = "/stream/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateStream(String STREAM_TYPE, String DESCRIPTION,  HttpSession session, Model model, Locale locale) {

        logger.info("Updata stream {}.", locale);
        StreamDao dao = new StreamDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Stream controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateStream(STREAM_TYPE, DESCRIPTION, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/stream/delete", method = RequestMethod.POST)
    @ResponseBody
    public String streamDelete(String STREAM_TYPE, Model model, Locale locale) {
        logger.info("delete stream", locale);
        StreamDao dao = new StreamDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteStream(STREAM_TYPE);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
    
}
