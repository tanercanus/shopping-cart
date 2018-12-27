package com.tanerus.shopping.cart.model.discount;

import com.tanerus.shopping.cart.enumarations.DiscountType;
import com.tanerus.shopping.cart.model.category.Category;
import com.tanerus.shopping.cart.model.product.Product;

/**
 * @author TanerCan Campaign class
 */
public class Campaign extends Discount {

	private Category category;
	private int quantity;

	public Campaign(Category category, double discountRate, int quantity, DiscountType discountType) {
		super(discountRate, discountType);
		this.category = category;
		this.quantity = quantity;
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

	@Override
	public double getDiscountAmount(Product product) {
		double discountAmnt = 0;

		if (this.category.getTitle().equals(product.getCategory().getTitle())) {
			if (this.getDiscountType() == DiscountType.Rate) {

				if (product.getQuantity() > this.getQuantity()) {
					discountAmnt = product.getPrice() * product.getQuantity() * this.getDiscountRate() / 100;
				}
			} else if (this.getDiscountType() == DiscountType.Amount) {
				if (product.getQuantity() > this.getQuantity()) {
					discountAmnt = this.getDiscountRate();
				}

			}
		}

		return discountAmnt;
	}

}
