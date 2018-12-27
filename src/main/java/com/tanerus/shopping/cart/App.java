package com.tanerus.shopping.cart;

import com.tanerus.shopping.cart.calculator.DeliveryCostCalculator;
import com.tanerus.shopping.cart.enumarations.DiscountType;
import com.tanerus.shopping.cart.model.cart.ShoppingCart;
import com.tanerus.shopping.cart.model.category.Category;
import com.tanerus.shopping.cart.model.discount.Campaign;
import com.tanerus.shopping.cart.model.discount.Coupon;
import com.tanerus.shopping.cart.model.product.Product;

public class App {
	public static void main(String[] args) {
		Category food = new Category("food");

		Category stuff = new Category("stuff");

		Product apple = new Product("Apple", 100.0, food);
		Product almond = new Product("Almonds", 150.0, food);

		Product tv = new Product("Tv", 1500.0, stuff);
		Product glass = new Product("Glass", 15.0, stuff);
		Product paper = new Product("Paper", 25.0, stuff);

		ShoppingCart cart = new ShoppingCart();
		cart.addItem(apple, 4);
		cart.addItem(almond, 7);
		cart.addItem(tv, 2);
		cart.addItem(tv, 5);
		cart.addItem(glass, 25);
		cart.addItem(paper, 50);

		Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
		Campaign campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
		Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);

		cart.applyDiscount(campaign1, campaign2, campaign3);

		Coupon coupon = new Coupon(100, 10, DiscountType.Rate);

		cart.applyCoupon(coupon);

		DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(2.5, 2.1);

		deliveryCostCalculator.calculateFor(cart);
		
		cart.getDeliveryCost(deliveryCostCalculator);

		cart.print();

	}
}
