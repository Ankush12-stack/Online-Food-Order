package com.cdacproject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  private User owner;

  private String name;

  private String description;

  private String cuisineType;

  @OneToOne
  private Address address;

  @Embedded
  private ContactInformation contactInformation;

  public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getOpeningHours() {
	return openingHours;
}

public void setOpeningHours(String openingHours) {
	this.openingHours = openingHours;
}

public LocalDateTime getRegistrationDate() {
	return registrationDate;
}

public void setRegistrationDate(LocalDateTime registrationDate) {
	this.registrationDate = registrationDate;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public List<String> getImages() {
	return images;
}

public void setImages(List<String> images) {
	this.images = images;
}

public String getCuisineType() {
	return cuisineType;
}

public User getOwner() {
	return owner;
}

public void setOwner(User owner) {
	this.owner = owner;
}

public void setCuisineType(String cuisineType) {
	this.cuisineType = cuisineType;
}

public ContactInformation getContactInformation() {
	return contactInformation;
}

public void setContactInformation(ContactInformation contactInformation) {
	this.contactInformation = contactInformation;
}

public Address getAddress() {
	return address;
}

public void setAddress(Address address) {
	this.address = address;
}

private String openingHours;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  @ElementCollection
  @Column(length = 1000)
  private List<String>images;

  private LocalDateTime registrationDate;

  private boolean open;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Food> foods = new ArrayList<>();

public boolean isOpen() {
	// TODO Auto-generated method stub
	return false;
}

public void setOpen(boolean b) {
	// TODO Auto-generated method stub
	
}
}
