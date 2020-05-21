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

import com.dao.CommonMenuDao;
import com.dao.RegionDao;
import com.dao.UserDao;
import com.dao.UserOfficeDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.MenuAccess;
import com.model.Region;
import com.model.UserInformationModel;
import com.model.UserOfficeModel;

@Controller
public class UserOfficeController {
	private static final String classname = "../user-office/list";
	
	private static final Logger logger = LoggerFactory.getLogger(UserOfficeController.class);
	@RequestMapping(value = "/user-office/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model,HttpServletRequest request, HttpSession session) throws SQLException  {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		//LIST_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

				if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					return "/home";
				}
				
		
		
		RegionDao dao = new RegionDao();
		List<Region> regionlist = null;
       List<Map<String, Object>> levelcontrollist=null;
//        System.out.println("");
       
        try {
        	
        	regionlist = dao.getlistByUserFDC(user.getUSER_ID(),user.getUSER_LEVEL());
        	levelcontrollist=UserDao.getUserDetailByOfficeCode(user.getOFFICE_CODE());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "User Office Map List");
        model.addAttribute("regionlist", regionlist);	        
        model.addAttribute("USER_LEVEL", user.getUSER_LEVEL());        
        model.addAttribute("levelcontrollist", levelcontrollist);
        
		return "useroffice/list";
		
		
		

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "userofficebody")
	public String userfdcbody(Model model, Locale locale) {


		
	        return "useroffice/userofficebody";

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getuseroffice",produces = MediaType.APPLICATION_JSON_VALUE)

	public List<UserOfficeModel> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String USER_ID = request.getParameter("USER_ID");
	System.out.println("USER_ID "+USER_ID);
		
		List<UserOfficeModel> list = null;
		
		try {
			list = UserOfficeDao.getModeList(USER_ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	@RequestMapping(value = "/saveuseroffice", method = RequestMethod.POST)
	@ResponseBody

	public String saveModeList(String USER_ID, String menu_mode, HttpServletRequest request, Model model, Locale locale,HttpSession session)
			throws JsonParseException, JsonMappingException, IOException, SQLException {

	//  ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
				
				if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					return "/home";
				}
				
		
		if (USER_ID == null) {
			return "Please Enter User  ";
		}

		

		ObjectMapper mapper = new ObjectMapper();
	
		List<Map<String, Object>> myObjects = mapper.readValue(menu_mode,
                new TypeReference<List<Map<String, Object>>>() {
        });
		
//		SaveServiceTeam
		UserOfficeDao menuaccessdao = new UserOfficeDao();
		String msg = null;
		try {
			
			msg = menuaccessdao.SaveUserOffice(USER_ID, myObjects, user.getUSER_ID());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}
	
}
