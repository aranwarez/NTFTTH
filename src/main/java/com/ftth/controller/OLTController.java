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

import com.dao.OLTDao;
import com.dao.OfficeDao;

@Controller
public class OLTController {
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getOLTByOffice",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String OFFICE_CODE = request.getParameter("OFFICE_CODE");
		
		List<Map<String, Object>> list = null;
		
		try {
			list = OLTDao.getOLTListByOffice(OFFICE_CODE);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
}
