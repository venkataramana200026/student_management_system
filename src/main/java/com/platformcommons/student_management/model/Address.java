package com.platformcommons.student_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String addressType;

	@ManyToOne
	@JoinColumn(name = "student_id")
	@JsonIgnore
	private Student student;

	public Address() {
	}

	public Address(String street, String city, String state, String zipCode, String addressType, Student student) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.addressType = addressType;
		this.student = student;
	}
}
