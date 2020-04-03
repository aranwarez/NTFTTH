package com.ftth.controller;

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

import com.dao.MServiceDao;
import com.dao.TeamDao;
import com.model.UserInformationModel;
@Controller
public class TeamController {
	
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);
    
    @RequestMapping(value = "/team/list", method = RequestMethod.GET)
    public String teamlist(Locale locale, Model model) throws SQLException {

        logger.info("Getting Services List", locale);
        TeamDao dao = new TeamDao();
        List<Map<String, Object>> list = null;
        try {
            list = dao.getTeamList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Sub Team");
        model.addAttribute("data_list", list);

        

       return "team/list";
    }
    @ResponseBody
    @RequestMapping(value = "/team/jsonlist", method = RequestMethod.GET)
    public List<Map<String, Object>> getSPtargetist(Locale locale, Model model, HttpSession session)
            throws SQLException {
    	TeamDao dao = new TeamDao();
        return dao.getTeamList();
        
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "dialogteam")
    public String dialogservice(Model model, Locale locale) {
        return "team/teamdialog";

    }
    

    @RequestMapping(value = "/team/saveJS", method = RequestMethod.POST)
    @ResponseBody
    public String saveJSService(String TEAM_CODE, String DESCRIPTION, HttpSession session, Model model, Locale locale) {

        logger.info("Save Service {}.", locale);
        TeamDao dao = new TeamDao();

        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "Team controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.saveTeam(TEAM_CODE,DESCRIPTION,USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping(value = "/team/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateService(String TEAM_CODE,String DESCRIPTION, HttpSession session, Model model, Locale locale) {

        logger.info("Updata Service {}.", locale);
        TeamDao dao = new TeamDao();

        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        String USER = userinfo.getUSER_ID();
        model.addAttribute("fx", "team controller list ");
        model.addAttribute("userName", "NEPal");
        String msg = null;
        try {
            msg = dao.updateTeam(TEAM_CODE, DESCRIPTION, USER);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping(value = "/team/delete", method = RequestMethod.POST)
    @ResponseBody
    public String serviceDelete(String TEAM_CODE, Model model, Locale locale) {
        logger.info("delete service", locale);
       TeamDao dao = new TeamDao();

        String msg = null;
        try {
            msg = dao.DeleteTeam(TEAM_CODE);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;

    }
    
    
}
