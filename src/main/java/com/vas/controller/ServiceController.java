package com.vas.controller;

import com.dao.COADao;
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

import com.dao.VASServiceDao;

import com.model.Service;
import com.model.UserInformationModel;
import java.util.Map;
import javax.servlet.http.HttpSession;

@Controller
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    public String servicelist(Locale locale, Model model) throws SQLException {

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

        COADao COA = new COADao();
        model.addAttribute("COA_list", COA.getCOAlist());

        return "service/list";
    }

    @RequestMapping(value = "/service/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSService(String SERVICE_CODE, String DESCRIPTION, String SLDG_CODE, String DATA_IMPORT, String ACTIVE_FLAG, HttpSession session, Model model, Locale locale) {

        logger.info("Save Service {}.", locale);
        VASServiceDao dao = new VASServiceDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Service controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveVasService(SERVICE_CODE, DESCRIPTION, SLDG_CODE, DATA_IMPORT, ACTIVE_FLAG, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/service/save", method = RequestMethod.POST)
    public String saveVasService(@Validated Service service, HttpSession session, Model model, Locale locale) {
        logger.info("Trying to save new vas service by user id:", locale);
        VASServiceDao dao = new VASServiceDao();
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        service.setUSER(userinfo.getUSER_ID());
        model.addAttribute("ServiceCode", service.getSERVICE_CODE());
        String msg = null;
        //            msg = dao.saveVasService(service);

        if (msg.toUpperCase().substring(0, 3).equals("SUC")) {
            model.addAttribute("sucess", msg);
        } else {
            model.addAttribute("error", msg);
        }
        return "redirect:/service/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "dialogservice")
    public String dialogservice(Model model, Locale locale) {
        return "service/servicedialog";

    }

    @RequestMapping(value = "/service/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateService(String SERVICE_CODE, String DESCRIPTION, String SLDG_CODE, String DATA_IMPORT, String ACTIVE_FLAG, HttpSession session, Model model, Locale locale) {

        logger.info("Updata Service {}.", locale);
        VASServiceDao dao = new VASServiceDao();
//        System.out.println("uddate Servce code==" + SERVICE_CODE);
        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Service controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateVasService(SERVICE_CODE, DESCRIPTION, SLDG_CODE, DATA_IMPORT, ACTIVE_FLAG, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/service/delete", method = RequestMethod.POST)
    @ResponseBody
    public String serviceDelete(String SERVICE_CODE, Model model, Locale locale) {
        logger.info("delete service", locale);
        VASServiceDao dao = new VASServiceDao();
//        System.out.println("delete service code==" + SERVICE_CODE);
        String msg = null;
        try {
            msg = dao.DeleteService(SERVICE_CODE);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
}
