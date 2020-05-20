package com.ftth.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.dao.FileUploadDao;
import com.dao.RoleDao;
import com.model.Role;
import com.model.UserInformationModel;

@Controller
public class FileUploadController {

	 private static final Logger logger = LoggerFactory
				.getLogger(FileUploadController.class);
	 RoleDao roledao = new RoleDao();
	 @GetMapping("/fileupload/list")
	   public String fileUploadForm(Locale locale, Model model, HttpSession session) throws SQLException {		 
		    logger.info("Welcome home! The client locale is {}.", locale);

			UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			
			FileUploadDao dao=new FileUploadDao();
			
			if (user == null) {
				return "redirect:/login";
			}
		
		
			List<Map<String, Object>> list = null;			
			list = dao.getUploadList();
			
			model.addAttribute("pgtitle", "File Notification list ");
			model.addAttribute("fx", "File Notification Control By Role ");
			model.addAttribute("data_list", list);
			
			
			return "fileupload/list";
			
	      
	   }
		/**
		 * Upload single file using Spring Controller
		 */
		@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
		public @ResponseBody
		String uploadFileHandler(String name,MultipartFile file,String role_code,
				String display_flag,String outputfile,String extension,HttpSession session) {
			UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
		
			
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					String rootPath = System.getProperty("catalina.home");
//					File dir = new File(rootPath + File.separator + "webapps/FTTH_IMAGE");
					File dir = new File(rootPath + File.separator + "webapps/FTTH_IMAGE");
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + "/"+outputfile+"."+extension);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());

					FileUploadDao.saveUpload(name, "webapps/FTTH/FTTH_IMAGE/"+outputfile+"."+extension,role_code, display_flag, user.getUSER_ID());
					
					
					
					
					
//					final Part filePart = request.getPart("file");
					
					
					
					
					
					
					
					return "You successfully uploaded file=" + name+"<a href='fileupload/list'> &nbsp;BACK</a>";
				} catch (Exception e) {
					return "You failed to upload " + name + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + name
						+ " because the file was empty.";
			}
		}

		/**
		 * Upload multiple file using Spring Controller
		 */
		@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
		public @ResponseBody
		String uploadMultipleFileHandler(@RequestParam("name") String[] names,
				@RequestParam("file") MultipartFile[] files) {

			if (files.length != names.length)
				return "Mandatory information missing";

			String message = "";
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				String name = names[i];
				try {
					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "tmpFiles");
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());

					message = message + "You successfully uploaded file=" + name
							+ "<br />";
				} catch (Exception e) {
					return "You failed to upload " + name + " => " + e.getMessage();
				}
			}
			return message;
		}
		
		
		@RequestMapping(method = RequestMethod.GET, value = "dialogupload")
		public String dialogservice(Model model, Locale locale) throws SQLException {

			List<Role> rolelist = roledao.getlist();
		 
			model.addAttribute("rolelist", rolelist);
		
			return "fileupload/filedialog";

		}
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/update/uploadpath",produces = MediaType.APPLICATION_JSON_VALUE)
		public String getViewFDC(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
			UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");
			
			
			String UPLOAD_ID = request.getParameter("UPLOAD_ID");
			String DISPLAY_FLAG = request.getParameter("DISPLAY_FLAG");			
			FileUploadDao dao=new FileUploadDao();
			
			String msg=null;
			
			
			try {
				msg = dao.enable_file(UPLOAD_ID, DISPLAY_FLAG, user.getUSER_ID());
						
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return msg;

		}
		@RequestMapping(value = "/upload/delete", method = RequestMethod.POST)
	    @ResponseBody
	    public String serviceDelete(String UPLOAD_ID, Model model, Locale locale) {
	        logger.info("delete service", locale);
	        FileUploadDao dao = new FileUploadDao();

	        String msg = null;
	        try {
	            msg = dao.delete_file(UPLOAD_ID);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return msg;

	    }
}
