package com.ftth.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.DistrictDao;
import com.dao.OfficeDao;
@Controller
public class OfficeController {
private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getOfficeByDistrict",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String DISTRICT_CODE = request.getParameter("DISTRICT_CODE");
		
		List<Map<String, Object>> list = null;
		
		try {
			list = OfficeDao.getOfficeListByDistrict(DISTRICT_CODE);
			
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
}
