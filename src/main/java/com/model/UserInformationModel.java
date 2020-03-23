package com.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UserInformationModel implements java.io.Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SN;
    private String USER_ID;
    private String FULL_NAME;
    private String PASSWORD;
    private String EMPLOYEE_CODE;
    private String LOCK_FLAG;
    private String SUPER_FLAG;
    private String CREATED_BY;
    private String CREATED_DATE;
    private String DISABLE_FLAG;
    private String LOCATION_CODE;
    private String USER_LEVEL;
    private String ROLE_CODE;
    private String USER;  
    private String EMPLOYEE_NAME;
    private String OFFICE_CODE;
    private String ROLE_DESCRIPTION;
	public int getSN() {
		return SN;
	}
	public void setSN(int sN) {
		SN = sN;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getFULL_NAME() {
		return FULL_NAME;
	}
	public void setFULL_NAME(String fULL_NAME) {
		FULL_NAME = fULL_NAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getEMPLOYEE_CODE() {
		return EMPLOYEE_CODE;
	}
	public void setEMPLOYEE_CODE(String eMPLOYEE_CODE) {
		EMPLOYEE_CODE = eMPLOYEE_CODE;
	}
	public String getLOCK_FLAG() {
		return LOCK_FLAG;
	}
	public void setLOCK_FLAG(String lOCK_FLAG) {
		LOCK_FLAG = lOCK_FLAG;
	}
	public String getSUPER_FLAG() {
		return SUPER_FLAG;
	}
	public void setSUPER_FLAG(String sUPER_FLAG) {
		SUPER_FLAG = sUPER_FLAG;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public String getDISABLE_FLAG() {
		return DISABLE_FLAG;
	}
	public void setDISABLE_FLAG(String dISABLE_FLAG) {
		DISABLE_FLAG = dISABLE_FLAG;
	}
	public String getLOCATION_CODE() {
		return LOCATION_CODE;
	}
	public void setLOCATION_CODE(String lOCATION_CODE) {
		LOCATION_CODE = lOCATION_CODE;
	}
	public String getUSER_LEVEL() {
		return USER_LEVEL;
	}
	public void setUSER_LEVEL(String uSER_LEVEL) {
		USER_LEVEL = uSER_LEVEL;
	}
	public String getROLE_CODE() {
		return ROLE_CODE;
	}
	public void setROLE_CODE(String rOLE_CODE) {
		ROLE_CODE = rOLE_CODE;
	}
	public String getUSER() {
		return USER;
	}
	public void setUSER(String uSER) {
		USER = uSER;
	}
	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}
	public void setEMPLOYEE_NAME(String eMPLOYEE_NAME) {
		EMPLOYEE_NAME = eMPLOYEE_NAME;
	}
	public String getOFFICE_CODE() {
		return OFFICE_CODE;
	}
	public void setOFFICE_CODE(String oFFICE_CODE) {
		OFFICE_CODE = oFFICE_CODE;
	}
	public String getROLE_DESCRIPTION() {
		return ROLE_DESCRIPTION;
	}
	public void setROLE_DESCRIPTION(String rOLE_DESCRIPTION) {
		ROLE_DESCRIPTION = rOLE_DESCRIPTION;
	}    
    
}
