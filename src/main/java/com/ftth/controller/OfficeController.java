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

import com.dao.OfficeDao;
import com.model.UserInformationModel;
@Controller
public class OfficeController {
private static final Logger logger = LoggerFactory.getLogger(OfficeController.class);
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getOfficeByDistrict",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getEditMode(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
		String DISTRICT_CODE = request.getParameter("DISTRICT_CODE");
		
		List<Map<String, Object>> list = null;
		
		try {
			
			if(user.getUSER_LEVEL().contains("6")) {
							
				list = OfficeDao.getOfficeListByDistrictFDC(DISTRICT_CODE, user.getUSER_ID());
			}else {
				list = OfficeDao.getOfficeListByDistrict(DISTRICT_CODE);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getOfficeList",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> officeList(HttpServletRequest request, HttpServletResponse response) {
				
		List<Map<String, Object>> list = null;
		
		try {
			list = OfficeDao.getOfficeList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getOfficeByALL",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getViewFDC(HttpServletRequest request, HttpServletResponse response) {
		
		String REGION_CODE = request.getParameter("REGION_CODE");
		String ZONE_CODE = request.getParameter("ZONE_CODE");
		String DISTRICT_CODE = request.getParameter("DISTRICT_CODE");
		
		
		
		
//		String REGION_CODE = request.getParameter("REGION_CODE");
		
		
		List<Map<String, Object>> list = null;
		
		try {
			list = OfficeDao.getOfficeListByALL(REGION_CODE, ZONE_CODE, DISTRICT_CODE);
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
}
