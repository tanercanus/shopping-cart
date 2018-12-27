package com.tanerus.shopping.cart.calculator;

import com.tanerus.shopping.cart.model.cart.ShoppingCart;
import com.tanerus.shopping.cart.model.product.Product;
import com.tanerus.shopping.cart.util.Util;


/**
 * @author TanerCan
 * DeliveryCostCalculator class
 * calculate delivery cost
 */
public class DeliveryCostCalculator {
	
	private double costPerDelivery;
	private double costPerProduct;
	private final static double fixedCost = 2.99;

	public DeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
		super();
		this.costPerDelivery = costPerDelivery;
		this.costPerProduct = costPerProduct;
	}

	public static double getFixedcost() {
		return fixedCost;
	}

	public double calculateFor(ShoppingCart cart) {

		int numberOfProducts = cart.getProductList().size();

		int numberOfDeliveries = (int) cart.getProductList().stream().filter(Util.distinctByKey(Product::getCategory))
				.count();

		return (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost;

	}

}
