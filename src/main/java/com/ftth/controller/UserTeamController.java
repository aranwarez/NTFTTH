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

import com.dao.ServiceTeamDao;
import com.dao.SubTeamDao;
import com.dao.TeamDao;
import com.dao.UserDao;
import com.model.UserInformationModel;
@Controller
public class UserTeamController {
	private static final Logger logger = LoggerFactory.getLogger(UserTeamController.class);
    
	 
	@RequestMapping(value = "/userTeam/list", method = RequestMethod.GET)
    public String servicelist(Locale locale, Model model) throws SQLException {

        logger.info("Getting Services List", locale);
        UserDao dao = new UserDao();
        
        List<UserInformationModel> list=null;
        try {
            list = dao.getList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "User Team List");
        model.addAttribute("userteam_list", list);

        

        return "userteam/list";
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "userteambody")
	public String serviceteamlist(Model model, Locale locale) {
		
			ServiceTeamDao dao = new ServiceTeamDao();
		
	        List<Map<String, Object>> serviceteamlist = null;
	        try {
	        	serviceteamlist = dao.getServiceTeamList();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        model.addAttribute("fx", "Service List");
	        model.addAttribute("serviceteamlist", serviceteamlist);
	        
		
	        return "serviceteam/serviceteambody";

	}
	
}
