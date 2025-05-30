package com.gammatech.coffee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coffees")
public class Coffee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private String country;
	private String grindType;
	private String roastLevel;
	private String flavorNotes;
	private int weight;
	private double price;

	public Coffee() {
	}

	public Coffee(String name, String description, String country, String grindType, String roastLevel,
			String flavorNotes, int weight, double price) {
		this.name = name;
		this.description = description;
		this.country = country;
		this.grindType = grindType;
		this.roastLevel = roastLevel;
		this.flavorNotes = flavorNotes;
		this.weight = weight;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGrindType() {
		return grindType;
	}

	public void setGrindType(String grindType) {
		this.grindType = grindType;
	}

	public String getRoastLevel() {
		return roastLevel;
	}

	public void setRoastLevel(String roastLevel) {
		this.roastLevel = roastLevel;
	}

	public String getFlavorNotes() {
		return flavorNotes;
	}

	public void setFlavorNotes(String flavorNotes) {
		this.flavorNotes = flavorNotes;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
