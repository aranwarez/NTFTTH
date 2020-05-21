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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.CommonMenuDao;
import com.dao.ExcelImportDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.MenuAccess;
import com.model.UserInformationModel;

@Controller
public class ExcelImportController {
	private static final String classname = "../import-excel/list";
	
	  private static final Logger logger = LoggerFactory.getLogger(ExcelImportController.class);
	  
	    
	   @RequestMapping(value = "/import-excel/list", method = RequestMethod.GET)
	    public String menuList(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,
	            HttpSession session) throws SQLException {
	        logger.info("Welcome home! The client locale is {}.", locale);

	        UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

	       
	//menu code should know before validate

	        MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	        if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
	            model.addAttribute("fx", "Unauthorized Page for this role!!");
	            return "/home";
	        }

	        model.addAttribute("menuaccess", menuaccess);
//	                model.addAttribute("LIST_FLAG", menuaccess.getLIST_FLAG());
//			model.addAttribute("ADD_FLAG", menuaccess.getADD_FLAG());
//			model.addAttribute("EDIT_FLAG", menuaccess.getEDIT_FLAG());
//			model.addAttribute("DELETE_FLAG", menuaccess.getDELETE_FLAG());
//			model.addAttribute("POST_FLAG", menuaccess.getPOST_FLAG());
//			model.addAttribute("CANCEL_FLAG", menuaccess.getCANCEL_FLAG());
	        model.addAttribute("fx", "Import Excel");
	    	
		//	List<Map<String, Object>> employeelist = null;     
			 try {
				 ExcelImportDao dao=new ExcelImportDao();
				 
				model.addAttribute("excel2list", dao.getExcel2List());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return "importexcel/list";

	    }	   
	   
	   @RequestMapping(method = RequestMethod.GET, value = "dialogexcelImport")
	    public String dialogcp(Model model, Locale locale) {
	        return "importexcel/impntspdialog";
	    }
	   
	   
	// posting Excel data into db
	    @RequestMapping(value = "/impntsp/postExcelHalfData", method = RequestMethod.POST)
	    @ResponseBody
	    public String saveExcelData(String JSONarrayList, String Filename, Model model, Locale locale, String IMP_YEAR,
	            String Period, String IMP_MONTH, String Services, String NT_SP, String SERVICE_CODE, String IMP_PERIOD, HttpSession session,HttpServletRequest request)
	            throws JsonParseException, JsonMappingException, IOException, SQLException {

	    //  ADD_FLAG
	    	UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
	    			MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	    			
	    			if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
	    				
	    				model.addAttribute("fx", "Unauthorized Page for this role!!");
	    				return "/home";
	    			}
	    			
	        logger.info("Saving Excel data in TMP table in DB {}.", locale);
	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER = userinfo.getUSER_ID();
	        ObjectMapper mapper = new ObjectMapper();

//			TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
//			};
	//
//			List<Map<String, Object>> list = mapper.readValue(JSONarrayList, typeRef);
	        ExcelImportDao dao = new ExcelImportDao();
	        List<Map<String, Object>> myObjects = mapper.readValue(JSONarrayList,
	                new TypeReference<List<Map<String, Object>>>() {
	        });
	        String msg = dao.saveExcelHalfData(myObjects, Filename, IMP_YEAR, Period, IMP_MONTH, Services, NT_SP, SERVICE_CODE,
	                IMP_PERIOD, USER);
	        
	        
	        return msg;
	    }
	    
	   
}
