package com.model;

public class MenuAccess {
	
	
	private int SN;
	private String USER;
	private String ROLE_CODE;
	private String MENU_CODE;
	private String EDIT_FLAG;
	private String DELETE_FLAG;
	private String POST_FLAG;
	private String ADD_FLAG;
	private String CANCEL_FLAG;
	private String LIST_FLAG;

	public String getLIST_FLAG() {
		return LIST_FLAG;
	}
	
	

	public void setLIST_FLAG(String lIST_FLAG) {
		LIST_FLAG = lIST_FLAG;
	}

	public int getSN() {
		return SN;
	}

	public void setSN(int sN) {
		SN = sN;
	}

	public String getUSER() {
		return USER;
	}

	public void setUSER(String uSER) {
		USER = uSER;
	}

	public String getROLE_CODE() {
		return ROLE_CODE;
	}

	public void setROLE_CODE(String rOLE_CODE) {
		ROLE_CODE = rOLE_CODE;
	}

	public String getMENU_CODE() {
		return MENU_CODE;
	}

	public void setMENU_CODE(String mENU_CODE) {
		MENU_CODE = mENU_CODE;
	}

	public String getEDIT_FLAG() {
		return EDIT_FLAG;
	}

	public void setEDIT_FLAG(String eDIT_FLAG) {
		EDIT_FLAG = eDIT_FLAG;
	}

	public String getDELETE_FLAG() {
		return DELETE_FLAG;
	}

	public void setDELETE_FLAG(String dELETE_FLAG) {
		DELETE_FLAG = dELETE_FLAG;
	}

	public String getPOST_FLAG() {
		return POST_FLAG;
	}

	public void setPOST_FLAG(String pOST_FLAG) {
		POST_FLAG = pOST_FLAG;
	}

	public String getADD_FLAG() {
		return ADD_FLAG;
	}

	public void setADD_FLAG(String aDD_FLAG) {
		ADD_FLAG = aDD_FLAG;
	}

	public String getCANCEL_FLAG() {
		return CANCEL_FLAG;
	}

	public void setCANCEL_FLAG(String cANCEL_FLAG) {
		CANCEL_FLAG = cANCEL_FLAG;
	}

}
