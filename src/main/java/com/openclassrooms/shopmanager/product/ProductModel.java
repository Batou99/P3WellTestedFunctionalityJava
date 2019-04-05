package com.openclassrooms.shopmanager.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ProductModel {

	private Long id;

	@NotBlank(message = "Name must not be blank")
	@Pattern(regexp = "^[^%#]*$", message = "Enter a valid name")
	private String name;

	private String description;
	private String details;

	@NotBlank(message = "Quantity must not be blank")
	@Pattern(regexp = "^(1|[1-9][0-9]*)$", message = "Quantity it should be number and greater than zero")
	private String quantity;

	@NotBlank(message = "Price must not be blank")
	@Pattern(regexp = "(1|[1-9]\\d*)?(\\.\\d+)", message = "Price should be a decimal number and greater than zero")
	private String price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
