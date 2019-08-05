package com.model;

public class Menu {
	private int SN;
	private String MENU_CODE;
	private String MENU_DESC;
	private String MENU_URL;
	private String PARENT_MENU;
	private String USER;
	private String LEVEL;
	private String STATUS_TYPE;
	private String MODULE_TYPE;
	
	public int getSN() {
		return SN;
	}
	public void setSN(int sN) {
		SN = sN;
	}
	public String getMENU_CODE() {
		return MENU_CODE;
	}
	public void setMENU_CODE(String mENU_CODE) {
		MENU_CODE = mENU_CODE;
	}
	public String getMENU_DESC() {
		return MENU_DESC;
	}
	public void setMENU_DESC(String mENU_DESC) {
		MENU_DESC = mENU_DESC;
	}
	public String getMENU_URL() {
		return MENU_URL;
	}
	public void setMENU_URL(String mENU_URL) {
		MENU_URL = mENU_URL;
	}
	public String getPARENT_MENU() {
		return PARENT_MENU;
	}
	public void setPARENT_MENU(String pARENT_MENU) {
		PARENT_MENU = pARENT_MENU;
	}
	public String getUSER() {
		return USER;
	}
	public void setUSER(String uSER) {
		USER = uSER;
	}
	public String getLEVEL() {
		return LEVEL;
	}
	public void setLEVEL(String lEVEL) {
		LEVEL = lEVEL;
	}
	public String getSTATUS_TYPE() {
		return STATUS_TYPE;
	}
	public void setSTATUS_TYPE(String sTATUS_TYPE) {
		STATUS_TYPE = sTATUS_TYPE;
	}
	public String getMODULE_TYPE() {
		return MODULE_TYPE;
	}
	public void setMODULE_TYPE(String mODULE_TYPE) {
		MODULE_TYPE = mODULE_TYPE;
	}
}
