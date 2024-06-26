package com.ftth.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.DistrictDao;
import com.model.UserInformationModel;

@Controller
public class DistrictController {

	private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getDistrictByZone",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getEditMode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		String ZONE_CODE = request.getParameter("ZONE_CODE");
		
		List<Map<String, Object>> list = null;
		
		try {
			
			if(user.getUSER_LEVEL().contains("6")) {
				
				
				list = DistrictDao.getDistrictListByZoneFDC(ZONE_CODE, user.getUSER_ID());
			}else {
				list = DistrictDao.getDistrictListByZone(ZONE_CODE);
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
}
