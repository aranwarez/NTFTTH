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

import com.dao.FDCDao;
import com.dao.RegionDao;
import com.dao.UserDao;
import com.dao.UserFdcDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Region;
import com.model.UserFdcModel;
import com.model.UserInformationModel;

@Controller
public class UserFDCController {
	private static final Logger logger = LoggerFactory.getLogger(UserFDCController.class);
	
	

	@RequestMapping(value = "/user-fdc/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpSession session)  {
		logger.info("Welcome home! The client locale is {}.", locale);
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
	
		if (user == null) {
			return "redirect:/login";
		}
		
		RegionDao dao = new RegionDao();
		List<Region> regionlist = null;
       List<Map<String, Object>> levelcontrollist=null;
//        System.out.println("");
       
        try {
        	regionlist = dao.getlist();
        	levelcontrollist=UserDao.getUserDetailByOfficeCode(user.getOFFICE_CODE());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "User FDC Map List");
        model.addAttribute("regionlist", regionlist);	        
        model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());        
        model.addAttribute("levelcontrollist", levelcontrollist);
        
		return "userfdc/list";
		
		
		

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "userfdcbody")
	public String userfdcbody(Model model, Locale locale) {


		
	        return "userfdc/userfdcbody";

	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getuserfdc",produces = MediaType.APPLICATION_JSON_VALUE)

	public List<UserFdcModel> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String USER_ID = request.getParameter("USER_ID");
	System.out.println("USER_ID "+USER_ID);
		
		List<UserFdcModel> list = null;
		
		try {
			list = UserFdcDao.getModeList(USER_ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/currentNepaliDate")

	public String getCurrentDt(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			return FDCDao.getCurrentNepaliDate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	@RequestMapping(value = "/saveuserfdc", method = RequestMethod.POST)
	@ResponseBody

	public String saveModeList(String USER_ID, String menu_mode,  Model model, Locale locale,HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {


		System.out.println(" User code = " + USER_ID);
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
		
//		SaveServiceTeam
		UserFdcDao menuaccessdao = new UserFdcDao();
		String msg = null;
		try {
			
			msg = menuaccessdao.SaveUserFDC(USER_ID, myObjects, user.getUSER_ID());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}
	
	
	
	
	
}
