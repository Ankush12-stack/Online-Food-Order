package com.cdacproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public User getCustomer() {
	return customer;
}

public void setCustomer(User customer) {
	this.customer = customer;
}

@OneToOne
  private User customer;

  private Long total;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> item = new ArrayList<>();
}
