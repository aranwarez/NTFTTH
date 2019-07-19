package com.vas.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.RoleDao;
import com.dao.VASServiceDao;
import com.model.Role;
import java.util.Map;

@Controller
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    public String rolelist(Locale locale, Model model) {

        logger.info("Getting Services List", locale);
        VASServiceDao dao = new VASServiceDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getVasServiceList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Service List");
        model.addAttribute("data_list", list);

        return "service/list";
    }

}
