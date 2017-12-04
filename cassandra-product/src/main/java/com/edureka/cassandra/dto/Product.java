package com.edureka.cassandra.dto;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 4913933159110420730L;

	private String productCode;
	private String productName;
	private String description;
	private Integer quantity;
	private float price;
	private float mrp;
	private String brand;
	private String category;
	private String sellerCode;
	private long creationTimeStamp;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getMrp() {
		return mrp;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public void setCreationTimeStamp(long creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}

	public Product(String productCode, String productName, String description, Integer quantity, float price, float mrp,
			String brand, String category, String sellerCode, long creationTimeStamp) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.mrp = mrp;
		this.brand = brand;
		this.category = category;
		this.sellerCode = sellerCode;
		this.creationTimeStamp = creationTimeStamp;
	}

}
