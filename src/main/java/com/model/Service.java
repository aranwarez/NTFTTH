package com.model;

public class Service {

    /**
     * @param USER the USER to set
     */
    public void setUSER(String USER) {
        this.USER = USER;
    }

    /**
     * @return the USER
     */
    public String getUSER() {
        return USER;
    }

	private String SERVICE_CODE;
	private String DESCRIPTION;
	private String SLDG_CODE;
	private String DATA_IMPORT;
	private String ACTIVE_FLAG;
        private String CREATED_BY;
        private String CREATED_DATE;
        private String UPDATED_BY;
        private String UPDATED_DATE;
        private String USER;
       
	public String getSERVICE_CODE() {
		return SERVICE_CODE;
	}

	public void setSERVICE_CODE(String sERVICE_CODE) {
		SERVICE_CODE = sERVICE_CODE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getSLDG_CODE() {
		return SLDG_CODE;
	}

	public void setSLDG_CODE(String sLDG_CODE) {
		SLDG_CODE = sLDG_CODE;
	}

	public String getDATA_IMPORT() {
		return DATA_IMPORT;
	}

	public void setDATA_IMPORT(String dATA_IMPORT) {
		DATA_IMPORT = dATA_IMPORT;
	}

	public String getACTIVE_FLAG() {
		return ACTIVE_FLAG;
	}

	public void setACTIVE_FLAG(String aCTIVE_FLAG) {
		ACTIVE_FLAG = aCTIVE_FLAG;
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

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}

	public String getUPDATED_DATE() {
		return UPDATED_DATE;
	}

	public void setUPDATED_DATE(String uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}

	

}
