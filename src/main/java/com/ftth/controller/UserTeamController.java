package com.ftth.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.SubTeamDao;
import com.dao.UserDao;
import com.dao.UserTeamDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.UserInformationModel;
import com.model.UserTeamModel;
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
		
			
			 SubTeamDao dao = new SubTeamDao();
	        List<Map<String, Object>> subteamlist = null;
	        try {
	        	subteamlist = dao.getSubTeamList();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        model.addAttribute("fx", "Service List");
	        model.addAttribute("subteamlist", subteamlist);
	        
		
	        return "userteam/userteambody";

	}
	
	
	@RequestMapping(value = "/saveUserTeam", method = RequestMethod.POST)
	@ResponseBody

	public String saveModeList(String USER_ID, String menu_mode,  Model model, Locale locale,HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {


	
		if (USER_ID == null) {
			return "Please Enter User  ";
		}

		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {
			return "redirect:/login";
		}

		ObjectMapper mapper = new ObjectMapper();
	
		List<Map<String, Object>> myObjects = mapper.readValue(menu_mode,
                new TypeReference<List<Map<String, Object>>>() {
        });
		
//		UserTeamDao
		UserTeamDao menuaccessdao = new UserTeamDao();
		String msg = null;
		try {
			
			msg = menuaccessdao.SaveUserTeam(USER_ID, myObjects, user.getUSER_ID());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getuserteam",produces = MediaType.APPLICATION_JSON_VALUE)

	public List<UserTeamModel> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String USER_ID = request.getParameter("USER_ID");
	System.out.println("USER_ID "+USER_ID);
		
		List<UserTeamModel> list = null;
		
		try {
			list = UserTeamDao.getModeList(USER_ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	
	
	
}
