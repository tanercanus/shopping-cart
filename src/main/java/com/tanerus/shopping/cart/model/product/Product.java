package com.tanerus.shopping.cart.model.product;

import com.tanerus.shopping.cart.constants.ProjectConstant;
import com.tanerus.shopping.cart.model.category.Category;

/**
 * @author TanerCan Product Class
 */
public class Product {

	private String title;
	private double price;
	// unit discount amount * quantity
	private double discountedAmount = 0;
	// unit coupon discount amount * quantity
	private double couponDisAmount = 0;
	private Category category;
	private int quantity = 0;
	private String currency = ProjectConstant.DEFAULT_CURRENCY;

	public Product(String title, double price, Category category) {
		this.title = title;
		this.price = price;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public double getCouponDisAmount() {
		return couponDisAmount;
	}

	public void setCouponDisAmount(double couponDisAmount) {
		this.couponDisAmount = couponDisAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
