package com.tanerus.shopping.cart.model.discount;

import com.tanerus.shopping.cart.enumarations.DiscountType;
import com.tanerus.shopping.cart.model.product.Product;

/**
 * @author TanerCan
 * Coupon class
 */
public class Coupon extends Discount {

	private double minAmount;
	public static volatile Coupon coupon;

	public Coupon(double minAmount, int discountRate, DiscountType discountType) {
		super(discountRate, discountType);
		this.minAmount = minAmount;
		//If coupon discount apply before campaign discount, we can use the field.
		coupon = this;
	}

	public double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	@Override
	public double getDiscountAmount(Product product) {
		double discountAmnt = 0;
		if (this.getDiscountType() == DiscountType.Rate) {
			double amount = product.getPrice()* product.getQuantity() - product.getDiscountedAmount();
			if (amount > this.getMinAmount()) {
				discountAmnt = amount * this.getDiscountRate() / 100;
			}
		}
		return discountAmnt;
	}

	public Coupon getCoupon() {
		return coupon;
	}

}
