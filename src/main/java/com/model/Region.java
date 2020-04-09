package com.model;

public class Region {

	private int SN;
	private String REGION_CODE;
	private String DESCRIPTION;
	
	private String USER;
	
	public String getACTIVE_STATUS() {
		return ACTIVE_STATUS;
	}

	public void setACTIVE_STATUS(String aCTIVE_STATUS) {
		ACTIVE_STATUS = aCTIVE_STATUS;
	}

	private String ACTIVE_STATUS;
	
	public String getUSER() {
		return USER;
	}

	public void setUSER(String uSER) {
		USER = uSER;
	}



	public int getSN() {
		return SN;
	}

	public void setSN(int sN) {
		SN = sN;
	}

	public String getREGION_CODE() {
		return REGION_CODE;
	}

	public void setREGION_CODE(String rEGION_CODE) {
		REGION_CODE = rEGION_CODE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

}
