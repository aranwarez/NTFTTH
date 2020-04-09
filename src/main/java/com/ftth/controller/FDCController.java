package com.ftth.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.FDCDao;
import com.dao.OLTDao;

@Controller
public class FDCController {
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getFDCByOLT",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String OLT_CODE = request.getParameter("OLT_CODE");
		
		List<Map<String, Object>> list = null;
		
		try {
			list = FDCDao.getFDCListByOLT(OLT_CODE);
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getFDCByALL",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getViewFDC(HttpServletRequest request, HttpServletResponse response) {
		
		String REGION_CODE = request.getParameter("REGION_CODE");
		String ZONE_CODE = request.getParameter("ZONE_CODE");
		String DISTRICT_CODE = request.getParameter("DISTRICT_CODE");
		String OFFICE_CODE = request.getParameter("OFFICE_CODE");
		String OLT_CODE = request.getParameter("OLT_CODE");
		
		
		
//		String REGION_CODE = request.getParameter("REGION_CODE");
		
		
		List<Map<String, Object>> list = null;
		
		try {
			list = FDCDao.getFDCListByALL(REGION_CODE, ZONE_CODE, DISTRICT_CODE, OFFICE_CODE, OLT_CODE, "");
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
}
