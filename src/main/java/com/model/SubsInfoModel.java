package com.model;

import java.util.List;

public class SubsInfoModel {
	private String balanceOfCreditLimit;
	private String offerName;
	private String serviceNumber;
	private String status;
	private List<String> vasName;
	public String getBalanceOfCreditLimit() {
		return balanceOfCreditLimit;
	}
	public void setBalanceOfCreditLimit(String balanceOfCreditLimit) {
		this.balanceOfCreditLimit = balanceOfCreditLimit;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getVasName() {
		return vasName;
	}
	public void setVasName(List<String> vasName) {
		this.vasName = vasName;
	}
	


}
