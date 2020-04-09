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
import com.dao.MenuAccessDao;
import com.dao.MenuDao;
import com.dao.RoleDao;
import com.dao.ZoneDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.model.Menu;
import com.model.MenuAccess;
@Controller
public class ZoneController {
	

	
	private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getZoneByRegion",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getEditMode(HttpServletRequest request, HttpServletResponse response) {
		
		String REGION_CODE = request.getParameter("REGION_CODE");
		
		List<Map<String, Object>> list = null;
		
		try {
			list = ZoneDao.getZoneListByRegion(REGION_CODE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	
}
