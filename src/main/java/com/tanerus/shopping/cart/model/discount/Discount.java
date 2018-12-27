package com.tanerus.shopping.cart.model.discount;

import com.tanerus.shopping.cart.enumarations.DiscountType;
import com.tanerus.shopping.cart.model.product.Product;

/**
 * @author TanerCan 
 * Template pattern used for Campaign and Coupon classes
 * Discount class
 */
public abstract class Discount {

	private double discountRate;
	private DiscountType discountType;

	public Discount(double discountRate, DiscountType discountType) {
		super();
		this.discountRate = discountRate;
		this.discountType = discountType;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public abstract double getDiscountAmount(Product product);

}
