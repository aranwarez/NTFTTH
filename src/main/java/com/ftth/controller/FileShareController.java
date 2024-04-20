package com.ftth.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
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

import com.dao.CommonMenuDao;
import com.dao.DistrictDao;
import com.dao.FileUploadDao;
import com.dao.RoleDao;
import com.dao.Web_Folder_Dao;
import com.model.MenuAccess;
import com.model.Role;
import com.model.UserInformationModel;

@Controller
public class FileShareController {
	private static final String classname = "../fileshare/list";

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	RoleDao roledao = new RoleDao();

	@GetMapping("/fileshare/list")
	public String fileUploadForm(Locale locale, Model model, HttpSession session, HttpServletRequest request)
			throws SQLException {
		logger.info("File Share Page.", locale);

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getLIST_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		FileUploadDao dao = new FileUploadDao();

		List<Map<String, Object>> list = null;
		list = dao.getUploadList();

		model.addAttribute("pgtitle", "Document Manager");
		model.addAttribute("fx", "Share your files");
		model.addAttribute("data_list", list);

		return "fileshare/list";

	}

	/**
	 * Upload single file using Spring Controller
	 * 
	 * @throws SQLException
	 */
	@RequestMapping(value = "/shareuploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(String name, MultipartFile file, String role_code, String folder,
			String tag, String desc, HttpSession session, Model model, HttpServletRequest request) throws SQLException {

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
//					File dir = new File(rootPath + File.separator + "webapps/FTTH_IMAGE");
				File dir = new File(rootPath + File.separator + "FTTH_FILES");
				if (!dir.exists())
					dir.mkdirs();

				// generate FIle id
				Web_Folder_Dao sdao = new Web_Folder_Dao();
				Integer Fileid = sdao.getFileseq();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + "/" + Fileid);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location= + extension " + file.getOriginalFilename()
						+ serverFile.getAbsolutePath());
//					http://172.16.39.16:8080/ server

//				if (dao.get_file(role_code) != null) {
//
//					File deletedir = new File(rootPath + File.separator + "FTTH_FILES");
//
//					File deleteFile = new File(
//							deletedir.getAbsolutePath() + File.separator + "/" + dao.get_file(role_code));
//
//					org.apache.commons.io.FileUtils.deleteQuietly(deleteFile);
//				}

				sdao.saveFile(file.getOriginalFilename(), desc, user.getUSER_ID(), Fileid, folder, tag);

				return "You successfully uploaded file=" + file.getOriginalFilename()
						+ "<a href='fileshare/list'> &nbsp;BACK</a>";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/shareMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("name") String[] names,
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
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name + "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}

	@RequestMapping(method = RequestMethod.GET, value = "dialogshare")
	public String dialogservice(Model model, Locale locale) throws SQLException {

		List<Role> rolelist = roledao.getlist();

		model.addAttribute("rolelist", rolelist);

		return "fileshare/filedialog";

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update/sharepath", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getViewFDC(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model)
			throws SQLException {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getEDIT_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		String UPLOAD_ID = request.getParameter("UPLOAD_ID");
		String DISPLAY_FLAG = request.getParameter("DISPLAY_FLAG");
		FileUploadDao dao = new FileUploadDao();

		String msg = null;

		try {
			msg = dao.enable_file(UPLOAD_ID, DISPLAY_FLAG, user.getUSER_ID());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@RequestMapping(value = "/share/delete", method = RequestMethod.POST)
	@ResponseBody
	public String serviceDelete(String UPLOAD_ID, Model model, Locale locale, HttpServletRequest request)
			throws SQLException {
		logger.info("delete service", locale);
		FileUploadDao dao = new FileUploadDao();

		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getDELETE_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}

		String msg = null;
		try {
			msg = dao.delete_file(UPLOAD_ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getAllFolder", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getAllFolder(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		Web_Folder_Dao dao = new Web_Folder_Dao();

		List<Map<String, Object>> list = null;
		try {
			list = dao.getAllFolder();
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return list;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "createFolder")
	public String createFolder(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		Web_Folder_Dao dao = new Web_Folder_Dao();
		String msg;

		msg = dao.saveFolder(request.getParameter("DESCRIPTION"), user.getUSER_ID(), request.getParameter("topic"));

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "editFolder")
	public String editFolder(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		Web_Folder_Dao dao = new Web_Folder_Dao();
		String msg = null;

		try {
			msg = dao.editFolder(request.getParameter("DESCRIPTION"), user.getUSER_ID(),
					request.getParameter("parentnode"), request.getParameter("topic"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "delFolder")
	public String delFolder(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		Web_Folder_Dao dao = new Web_Folder_Dao();
		String msg = null;

		try {
			msg = dao.delFolder(request.getParameter("parentnode"), request.getParameter("topic"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	// Download file
	@RequestMapping(value = "/sharedownloadFile",method = RequestMethod.GET)
	public @ResponseBody String DownloadFileHandler(String name, String FileId, Model model, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) throws SQLException {

		// ADD_FLAG
		UserInformationModel user = (UserInformationModel) request.getSession().getAttribute("UserList");
		MenuAccess menuaccess = CommonMenuDao.checkAccess(user.getROLE_CODE(), classname);

		if (menuaccess == null || menuaccess.getADD_FLAG().equals("N")) {

			model.addAttribute("fx", "Unauthorized Page for this role!!");
			return "/home";
		}
		try {
		String rootPath = System.getProperty("catalina.home");
		File dir = new File(rootPath + File.separator + "FTTH_FILES");
		File serverFile = new File(dir.getAbsolutePath() + File.separator + "/" + FileId);
		System.out.println("File location on server::" + serverFile.getAbsolutePath());
		ServletContext ctx = request.getServletContext();
		InputStream fis = new FileInputStream(serverFile);
		String mimeType = ctx.getMimeType(serverFile.getAbsolutePath());
		mimeType = ctx.getMimeType(serverFile.getAbsolutePath());
		response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
		response.setContentLength((int) serverFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");

		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read = 0;
		while ((read = fis.read(bufferData)) != -1) {
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("File downloaded at client successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;

	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getAllFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getAllFiles(String pfolder,HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		UserInformationModel user = (UserInformationModel) session.getAttribute("UserList");

		Web_Folder_Dao dao = new Web_Folder_Dao();

		List<Map<String, Object>> list = null;
		try {
			list = dao.getAllFiles(pfolder);
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return list;

	}


}
