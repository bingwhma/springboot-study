package com.linyi.service.mongo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class XXCustomer extends Customer {
	
	public String getSex_() {
		return sex_;
	}
	public void setSex_(String sex_) {
		this.sex_ = sex_;
	}
	public int getAge_() {
		return age_;
	}
	public void setAge_(int age_) {
		this.age_ = age_;
	}
	public String sex_;
	
	@JsonIgnore
	public int age_;

}
