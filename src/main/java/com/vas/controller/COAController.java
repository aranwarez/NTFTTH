/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.controller;

import com.dao.COADao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
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
public class COAController {

    @ResponseBody
    @RequestMapping(value = "/COA/getCOAList", method = RequestMethod.GET)
    public List<Map<String, Object>> getCOAList(Locale locale, Model model, HttpSession session) throws SQLException {
        COADao dao = new COADao();
        return dao.getCOAlist();

    }

}
