package com.ftth.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dao.TeamDao;
@Controller
public class UserTeamController {
	private static final Logger logger = LoggerFactory.getLogger(UserTeamController.class);
    
	 
	@RequestMapping(value = "/userTeam/list", method = RequestMethod.GET)
    public String servicelist(Locale locale, Model model) throws SQLException {

        logger.info("Getting Services List", locale);
        TeamDao dao = new TeamDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getTeamList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Service List");
        model.addAttribute("data_list", list);

        

        return "team/list";
    }
}
