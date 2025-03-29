package com.platformcommons.student_management.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String addressType;

	public AddressDTO() {
	}

	public AddressDTO(String street, String city, String state, String zipCode, String addressType) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.addressType = addressType;
	}
}