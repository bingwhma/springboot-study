package com.linyi.service.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Customer implements java.io.Serializable {

	@Id
	private String id;

	private String firstName;
	
	@JsonIgnore
	private String xxxx;
	private String yyyy;
	
	
	public String getXxxx() {
		return xxxx;
	}

	public void setXxxx(String xxxx) {
		this.xxxx = xxxx;
	}

	public String getYyyy() {
		return yyyy;
	}

	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<Customer> getSubCust() {
		return subCust;
	}

	public void setSubCust(List<Customer> subCust) {
		this.subCust = subCust;
	}

	private String lastName;
	
	public List<Customer> subCust = new ArrayList<Customer>();

	public Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

}