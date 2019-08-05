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

	/**
     * @return the USER_FROM
     */
    public String getUSER_FROM() {
        return USER_FROM;
    }

    /**
     * @param USER_FROM the USER_FROM to set
     */
    public void setUSER_FROM(String USER_FROM) {
        this.USER_FROM = USER_FROM;
    }

    /**
     * @return the CC_CODE
     */
    public String getCC_CODE() {
        return CC_CODE;
    }

    /**
     * @param CC_CODE the CC_CODE to set
     */
    public void setCC_CODE(String CC_CODE) {
        this.CC_CODE = CC_CODE;
    }

    /**
     * @return the EMPLOYEE_NAME
     */
    public String getEMPLOYEE_NAME() {
        return EMPLOYEE_NAME;
    }

    /**
     * @param EMPLOYEE_NAME the EMPLOYEE_NAME to set
     */
    public void setEMPLOYEE_NAME(String EMPLOYEE_NAME) {
        this.EMPLOYEE_NAME = EMPLOYEE_NAME;
    }

    /**
     * @return the LOCATION_DESCRIPTION
     */
    public String getLOCATION_DESCRIPTION() {
        return LOCATION_DESCRIPTION;
    }

    /**
     * @param LOCATION_DESCRIPTION the LOCATION_DESCRIPTION to set
     */
    public void setLOCATION_DESCRIPTION(String LOCATION_DESCRIPTION) {
        this.LOCATION_DESCRIPTION = LOCATION_DESCRIPTION;
    }

    /**
     * @return the ROLE_DESCRIPTION
     */
    public String getROLE_DESCRIPTION() {
        return ROLE_DESCRIPTION;
    }

    /**
     * @param ROLE_DESCRIPTION the ROLE_DESCRIPTION to set
     */
    public void setROLE_DESCRIPTION(String ROLE_DESCRIPTION) {
        this.ROLE_DESCRIPTION = ROLE_DESCRIPTION;
    }

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
    
    private String CC_CODE;
    
    private String REGION_CODE;
    private String LOCATION_CODE;
    private String USER_LEVEL;
    private String ROLE_CODE;
    
    private String USER_FROM;

    private String USER;  
        
    private String EMPLOYEE_NAME;    
    private String LOCATION_DESCRIPTION;
    private String ROLE_DESCRIPTION;
    private String ACC_CEN_CODE;
    private String MODULE_ACCESS;
    
    /**
     * @return the USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * @param USER_ID the USER_ID to set
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    /**
     * @return the FULL_NAME
     */
    public String getFULL_NAME() {
        return FULL_NAME;
    }

    /**
     * @param FULL_NAME the FULL_NAME to set
     */
    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    /**
     * @return the PASSWORD
     */
    public String getPASSWORD() {
        return PASSWORD;
    }

    /**
     * @param PASSWORD the PASSWORD to set
     */
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    /**
     * @return the EMPLOYEE_CODE
     */
    public String getEMPLOYEE_CODE() {
        return EMPLOYEE_CODE;
    }

    /**
     * @param EMPLOYEE_CODE the EMPLOYEE_CODE to set
     */
    public void setEMPLOYEE_CODE(String EMPLOYEE_CODE) {
        this.EMPLOYEE_CODE = EMPLOYEE_CODE;
    }

    /**
     * @return the LOCK_FLAG
     */
    public String getLOCK_FLAG() {
        return LOCK_FLAG;
    }

    /**
     * @param LOCK_FLAG the LOCK_FLAG to set
     */
    public void setLOCK_FLAG(String LOCK_FLAG) {
        this.LOCK_FLAG = LOCK_FLAG;
    }

    /**
     * @return the SUPER_FLAG
     */
    public String getSUPER_FLAG() {
        return SUPER_FLAG;
    }

    /**
     * @param SUPER_FLAG the SUPER_FLAG to set
     */
    public void setSUPER_FLAG(String SUPER_FLAG) {
        this.SUPER_FLAG = SUPER_FLAG;
    }

    /**
     * @return the CREATED_BY
     */
    public String getCREATED_BY() {
        return CREATED_BY;
    }

    /**
     * @param CREATED_BY the CREATED_BY to set
     */
    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY;
    }

    /**
     * @return the CREATED_DATE
     */
    public String getCREATED_DATE() {
        return CREATED_DATE;
    }

    /**
     * @param CREATED_DATE the CREATED_DATE to set
     */
    public void setCREATED_DATE(String CREATED_DATE) {
        this.CREATED_DATE = CREATED_DATE;
    }

    /**
     * @return the DISABLE_FLAG
     */
    public String getDISABLE_FLAG() {
        return DISABLE_FLAG;
    }

    /**
     * @param DISABLE_FLAG the DISABLE_FLAG to set
     */
    public void setDISABLE_FLAG(String DISABLE_FLAG) {
        this.DISABLE_FLAG = DISABLE_FLAG;
    }

    /**
     * @return the REGION_CODE
     */
    public String getREGION_CODE() {
        return REGION_CODE;
    }

    /**
     * @param REGION_CODE the REGION_CODE to set
     */
    public void setREGION_CODE(String REGION_CODE) {
        this.REGION_CODE = REGION_CODE;
    }

    /**
     * @return the LOCATION_CODE
     */
    public String getLOCATION_CODE() {
        return LOCATION_CODE;
    }

    /**
     * @param LOCATION_CODE the LOCATION_CODE to set
     */
    public void setLOCATION_CODE(String LOCATION_CODE) {
        this.LOCATION_CODE = LOCATION_CODE;
    }

    /**
     * @return the USER_LEVEL
     */
    public String getUSER_LEVEL() {
        return USER_LEVEL;
    }

    /**
     * @param USER_LEVEL the USER_LEVEL to set
     */
    public void setUSER_LEVEL(String USER_LEVEL) {
        this.USER_LEVEL = USER_LEVEL;
    }

    /**
     * @return the ROLE_CODE
     */
    public String getROLE_CODE() {
        return ROLE_CODE;
    }

    /**
     * @param ROLE_CODE the ROLE_CODE to set
     */
    public void setROLE_CODE(String ROLE_CODE) {
        this.ROLE_CODE = ROLE_CODE;
    }

    /**
     * @return the SN
     */
    public int getSN() {
        return SN;
    }

    /**
     * @param SN the SN to set
     */
    public void setSN(int SN) {
        this.SN = SN;
    }

    /**
     * @return the USER
     */
    public String getUSER() {
        return USER;
    }

    /**
     * @param USER the USER to set
     */
    public void setUSER(String USER) {
        this.USER = USER;
    }

    /**
     * @return the ACC_CEN_CODE
     */
    public String getACC_CEN_CODE() {
        return ACC_CEN_CODE;
    }

    /**
     * @param ACC_CEN_CODE the ACC_CEN_CODE to set
     */
    public void setACC_CEN_CODE(String ACC_CEN_CODE) {
        this.ACC_CEN_CODE = ACC_CEN_CODE;
    }

    /**
     * @return the MODULE_ACCESS
     */
    public String getMODULE_ACCESS() {
        return MODULE_ACCESS;
    }

    /**
     * @param MODULE_ACCESS the MODULE_ACCESS to set
     */
    public void setMODULE_ACCESS(String MODULE_ACCESS) {
        this.MODULE_ACCESS = MODULE_ACCESS;
    }

	
}
