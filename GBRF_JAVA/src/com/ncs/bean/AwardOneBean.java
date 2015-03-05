package com.ncs.bean;

import java.sql.Timestamp;

public class AwardOneBean extends BaseBean {

	private int customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String telephone;
	private String paymentFirstName;
	private String paymentLastName;
	private String paymentMethod;
	private String paymentCode;
	private int orderStatusId;
	private double total;
	private int currencyId;
	private String currencyCode;
	private double currencyValue;
	private Timestamp dateAdded;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPaymentFirstName() {
		return paymentFirstName;
	}

	public void setPaymentFirstName(String paymentFirstName) {
		this.paymentFirstName = paymentFirstName;
	}

	public String getPaymentLastName() {
		return paymentLastName;
	}

	public void setPaymentLastName(String paymentLastName) {
		this.paymentLastName = paymentLastName;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public int getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(double currencyValue) {
		this.currencyValue = currencyValue;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

}
