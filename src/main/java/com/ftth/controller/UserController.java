package com.ftth.controller;

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
import com.dao.EmployeeDao;
import com.dao.FileUploadDao;
import com.dao.OfficeDao;
import com.dao.ProfileDao;
import com.dao.RoleDao;
import com.dao.UserDao;
import com.model.MenuAccess;
import com.model.Role;
import com.model.UserInformationModel;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String classname = "../user/list";

	UserDao dao = new UserDao();

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String rolelist(Locale locale, Model model,HttpServletRequest request, HttpSession session) throws SQLException {
		logger.info("Welcome home! The client locale is {}.", locale);


		//LIST_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

				if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					return "/home";
				}


		List<UserInformationModel> list = null;
		List<Map<String, Object>> empList = null;
		
		List<Map<String, Object>> officeList = null;	

		RoleDao roledao = new RoleDao();

		
		List<Role> rolelist = null;
			
		try {
			list = dao.getList();
			empList = EmployeeDao.getEmpList();
			officeList=OfficeDao.getOfficeList();

			rolelist = roledao.getlist();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("fx", "User Information List");
		
		model.addAttribute("data_list", list);

		model.addAttribute("empList", empList);

		model.addAttribute("officeList", officeList);
		model.addAttribute("rolelist", rolelist);
		return "user/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialoguser")
	public String dialogrole(Model model, Locale locale) {

		return "user/userdialog";

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "save/user")
	public String saveMenu(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) throws SQLException {

		logger.info(" user id:", locale);
	//  ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
				
				if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					return "/home";
				}
				
		UserDao userdao = new UserDao();

		
		UserInformationModel m = new UserInformationModel();

		m.setUSER_ID(request.getParameter("USER_ID"));
		m.setPASSWORD(request.getParameter("PASSWORD"));
		System.out.println(request.getParameter("PASSWORD"));
		m.setFULL_NAME(request.getParameter("FULL_NAME"));
		
		m.setUSER(user.getUSER_ID());
		
		
		m.setEMPLOYEE_CODE(request.getParameter("EMPLOYEE_CODE"));

		m.setLOCK_FLAG(request.getParameter("LOCK_FLAG"));

		m.setSUPER_FLAG(request.getParameter("SUPER_FLAG"));

		m.setDISABLE_FLAG(request.getParameter("DISABLE_FLAG"));
		
		m.setLOCATION_CODE(request.getParameter("LOCATION_CODE"));
		
		
		

		m.setUSER_LEVEL(request.getParameter("USER_LEVEL"));
		
		m.setROLE_CODE(request.getParameter("ROLE_CODE"));
			
		
		m.setOFFICE_CODE(request.getParameter("OFFICE_CODE"));
		
		m.setMOBILE_NO(request.getParameter("MOBILE_NO"));
		
		String msg = null;
		try {
			msg = userdao.saveUser(m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "adminPassChange")
	public String adminPasswordChange(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) {
		String usercode = request.getParameter("usercode");
		UserDao userdao = new UserDao();
		String msg = null;
		try {
			msg = userdao.passWordChange(request.getParameter("pass"), usercode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "user/edit",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInformationModel editUser(String code, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Locale locale) {

		
		
		UserDao userdao = new UserDao();
		UserInformationModel user = null;
		try {
			user = userdao.getUser(code);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "update/user")
	public String userUpdate(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Locale locale) throws SQLException {	
	//  EDIT_FLAG
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
				MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

				if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {
					
					model.addAttribute("fx", "Unauthorized Page for this role!!");
					return "/home";
				}
				
		UserInformationModel m = new UserInformationModel();
		UserDao userdao = new UserDao();
	
		m.setUSER_ID(request.getParameter("USER_ID"));

		m.setFULL_NAME(request.getParameter("FULL_NAME"));
		m.setUSER(user.getUSER_ID());
		m.setPASSWORD(request.getParameter("PASSWORD"));
		m.setEMPLOYEE_CODE(request.getParameter("EMPLOYEE_CODE"));

		m.setLOCK_FLAG(request.getParameter("LOCK_FLAG"));

		m.setSUPER_FLAG(request.getParameter("SUPER_FLAG"));

		m.setDISABLE_FLAG(request.getParameter("DISABLE_FLAG"));

		m.setOFFICE_CODE(request.getParameter("OFFICE_CODE"));

		m.setUSER_LEVEL(request.getParameter("USER_LEVEL"));
		m.setROLE_CODE(request.getParameter("ROLE_CODE"));
		m.setMOBILE_NO(request.getParameter("MOBILE_NO"));
		
		String msg = null;

		try {

			msg = userdao.updateUser(m);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "user/delete")
	public String userDelete(Model model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Locale locale) throws SQLException {
		
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);
	
		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {
			
			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		UserDao userdao = new UserDao();
		String msg = null;

		try {
			 msg = userdao.deleteUser(request.getParameter("USER_ID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getUserByALL",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getViewFDC(HttpServletRequest request, HttpServletResponse response) {
		
		String WORKING_REGION_CODE = request.getParameter("WORKING_REGION_CODE");
		String WORKING_ZONE_CODE = request.getParameter("WORKING_ZONE_CODE");
		String WORKING_DIS_CODE = request.getParameter("WORKING_DIS_CODE");
		String WORKING_OFFICE_CODE = request.getParameter("WORKING_OFFICE_CODE");
	
		
		
		
//		String REGION_CODE = request.getParameter("REGION_CODE");
		
		
		List<Map<String, Object>> list = null;
		
		try {
			
			list = UserDao.getUserListByALL(WORKING_REGION_CODE,WORKING_ZONE_CODE,WORKING_DIS_CODE,WORKING_OFFICE_CODE);
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	//for profile and password change
	@RequestMapping(value = "/my-profile/list", method = RequestMethod.GET)
    public String sd(Locale locale, Model model,HttpSession session) throws SQLException {

		logger.info("my-profile/list.", locale);
        

        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
        model.addAttribute("fx",userinfo.getUSER_ID());
        model.addAttribute("name",userinfo.getFULL_NAME()+"");
        model.addAttribute("userinfo", ProfileDao.getUserInfo(userinfo.getUSER_ID()));

        return "user/profile";
    }
	 	@RequestMapping(value = "/profile-update/saveJS", method = RequestMethod.POST)
	    @ResponseBody
	    public String profileUpdate(String USER_ID, String FULL_NAME,String MOBILE_NO, HttpSession session, Model model, Locale locale) {

	        logger.info("Save Service {}.", locale);
	        ProfileDao dao = new ProfileDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String updateby = userinfo.getUSER_ID();
	        model.addAttribute("fx", "Profile Update ");
	        model.addAttribute("userName", updateby);
	        String msg = null;
	        try {
	            msg = dao.updateProfile(FULL_NAME,MOBILE_NO,USER_ID,updateby);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	 	
	 	@RequestMapping(value = "/checkOldPASS", method = RequestMethod.POST)
	    @ResponseBody
	    public String oldPasswordCheck(String PASSWORD, HttpSession session, Model model, Locale locale) {

	        logger.info("Save Service {}.", locale);
	        UserDao dao = new UserDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER_ID = userinfo.getUSER_ID();
	        
	        
	        String msg = null;
	        try {
	            msg = dao.checkOldPAss(USER_ID,PASSWORD);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	 	@RequestMapping(value = "/update-user/password", method = RequestMethod.POST)
	    @ResponseBody
	    public String updateUserPasswordByUser(String PASSWORD, HttpSession session, Model model, Locale locale) {

	        logger.info("Save Service {}.", locale);
	        UserDao dao = new UserDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER_ID = userinfo.getUSER_ID();
	        
	        
	        String msg = null;
	        try {
	            msg = dao.passWordChange(PASSWORD,USER_ID);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	 	
	 	@ResponseBody
		@RequestMapping(method = RequestMethod.GET, value = "firsttime/check")
		public String loginCount(String COUNT,HttpServletRequest request, HttpServletResponse response,HttpSession session) {
	 		
	 		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String USER_ID = userinfo.getUSER_ID();
	        
	 		String msg=null;
			try {
				msg = ProfileDao.firstTimePasswordCheck(USER_ID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return msg;

		}
		@ResponseBody
		@RequestMapping(method = RequestMethod.GET, value = "max-OneActiveFile/check")
		public String OneActiveFile(String FILENAME,HttpServletRequest request, HttpServletResponse response,HttpSession session) {
	 		
	 		UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        
	        FileUploadDao dao=new FileUploadDao();
	        System.out.println(userinfo.getROLE_CODE());
	        
	 		String msg=null;
			try {
				msg = dao.maxOneActiveFile(userinfo.getROLE_CODE());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	 		OneActiveFile
			
			return msg;

		}
		
		
		@RequestMapping(value = "/checkUniqueUser", method = RequestMethod.POST)
	    @ResponseBody
	    public String uniqueUser(String USER_ID, HttpSession session, Model model, Locale locale) {

	        logger.info("checkUniqueUser {}.", locale);
	        ProfileDao dao = new ProfileDao();

	        UserInformationModel userinfo = (UserInformationModel) session.getAttribute("UserList");
	        String updateby = userinfo.getUSER_ID();
	        model.addAttribute("fx", "unique user ");
	        model.addAttribute("userName", updateby);
	        String msg = null;
	        try {
	            msg = dao.uniqueUserCheck(USER_ID);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;
	    }
	    
	
}
