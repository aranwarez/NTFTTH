package com.model;

public class Bank {
	 private int SN;
	    private String USER;
	    private String CC_CODE;
	    private String BANK_CD;
	    private String BANK_NAME;
	    private String BANK_ADDRESS;
	    private String ACCT_NO;
	    private String ACCT_TYPE;
	    private String ACT_FLAG;
	    private String DEACTIVE_DT;
	    
	    /*
	       bank details
	    */
	    
	     private String TRANS_NO;            
	     private String EFFECTIVE_DT;   
	     private String INT_RATE;             
	     private String TAX_RATE ;
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
		public String getCC_CODE() {
			return CC_CODE;
		}
		public void setCC_CODE(String cC_CODE) {
			CC_CODE = cC_CODE;
		}
		public String getBANK_CD() {
			return BANK_CD;
		}
		public void setBANK_CD(String bANK_CD) {
			BANK_CD = bANK_CD;
		}
		public String getBANK_NAME() {
			return BANK_NAME;
		}
		public void setBANK_NAME(String bANK_NAME) {
			BANK_NAME = bANK_NAME;
		}
		public String getBANK_ADDRESS() {
			return BANK_ADDRESS;
		}
		public void setBANK_ADDRESS(String bANK_ADDRESS) {
			BANK_ADDRESS = bANK_ADDRESS;
		}
		public String getACCT_NO() {
			return ACCT_NO;
		}
		public void setACCT_NO(String aCCT_NO) {
			ACCT_NO = aCCT_NO;
		}
		public String getACCT_TYPE() {
			return ACCT_TYPE;
		}
		public void setACCT_TYPE(String aCCT_TYPE) {
			ACCT_TYPE = aCCT_TYPE;
		}
		public String getACT_FLAG() {
			return ACT_FLAG;
		}
		public void setACT_FLAG(String aCT_FLAG) {
			ACT_FLAG = aCT_FLAG;
		}
		public String getDEACTIVE_DT() {
			return DEACTIVE_DT;
		}
		public void setDEACTIVE_DT(String dEACTIVE_DT) {
			DEACTIVE_DT = dEACTIVE_DT;
		}
		public String getTRANS_NO() {
			return TRANS_NO;
		}
		public void setTRANS_NO(String tRANS_NO) {
			TRANS_NO = tRANS_NO;
		}
		public String getEFFECTIVE_DT() {
			return EFFECTIVE_DT;
		}
		public void setEFFECTIVE_DT(String eFFECTIVE_DT) {
			EFFECTIVE_DT = eFFECTIVE_DT;
		}
		public String getINT_RATE() {
			return INT_RATE;
		}
		public void setINT_RATE(String iNT_RATE) {
			INT_RATE = iNT_RATE;
		}
		public String getTAX_RATE() {
			return TAX_RATE;
		}
		public void setTAX_RATE(String tAX_RATE) {
			TAX_RATE = tAX_RATE;
		}
	
}
