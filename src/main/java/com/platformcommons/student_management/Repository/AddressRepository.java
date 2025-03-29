package com.platformcommons.student_management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platformcommons.student_management.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<Address> findByStreetAndCityAndStateAndZipCode(String street, String city, String state, String zipCode);
}
