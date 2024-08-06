package com.cdacproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdacproject.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
