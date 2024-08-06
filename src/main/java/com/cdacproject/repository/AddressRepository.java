package com.cdacproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdacproject.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
