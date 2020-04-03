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

import com.dao.MServiceTypeDao;
import com.dao.MenuAccessDao;
import com.dao.ServiceTeamDao;
import com.dao.SubTeamDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Menu;
import com.model.MenuAccess;
import com.model.Role;
import com.model.ServiceTeamModel;
import com.model.UserInformationModel;

@Controller
public class ServiceTeamController {
	private static final Logger logger = LoggerFactory.getLogger(ServiceTeamController.class);
	
	

	@RequestMapping(value = "/service-team/list", method = RequestMethod.GET)
	public String menuacesslistlist(Locale locale, Model model, HttpSession session)  {
		logger.info("Welcome home! The client locale is {}.", locale);
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		if (user == null) {
			return "redirect:/login";
		}
		
		MServiceTypeDao dao = new MServiceTypeDao();
        List<Map<String, Object>> servicetypelist = null;
        
        
        try {
        	servicetypelist = dao.getServiceTypeList();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("fx", "Service Team List");
        model.addAttribute("servicetypelist", servicetypelist);
		

		return "serviceteam/list";

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "serviceteambody")
	public String serviceteambody(Model model, Locale locale) {


		
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
	        
		
	        return "serviceteam/serviceteambody";

	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getServiceAccess",produces = MediaType.APPLICATION_JSON_VALUE)

	public List<ServiceTeamModel> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String SERVICE_TYPE_ID = request.getParameter("SERVICE_TYPE_ID");
	System.out.println("SERVICE_TYPE_ID "+SERVICE_TYPE_ID);
		
		List<ServiceTeamModel> list = null;
		
		try {
			list = ServiceTeamDao.getModeList(SERVICE_TYPE_ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	
	@RequestMapping(value = "/saveServiceTeam", method = RequestMethod.POST)
	@ResponseBody

	public String saveModeList(String SERVICE_TYPE_ID, String menu_mode,  Model model, Locale locale,HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {


		System.out.println(" role code = " + SERVICE_TYPE_ID);
		if (SERVICE_TYPE_ID == null) {
			return "Please Enter SERVICE_TYPE ";
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
		ServiceTeamDao menuaccessdao = new ServiceTeamDao();
		String msg = null;
		try {
			
			msg = menuaccessdao.SaveMenuAccess(SERVICE_TYPE_ID, myObjects, user.getUSER_ID());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed " + e;
		}
		return msg;
	}
	
	
	
	
	
}
