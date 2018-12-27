package com.tanerus.shopping.cart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.tanerus.shopping.cart.calculator.DeliveryCostCalculator;
import com.tanerus.shopping.cart.enumarations.DiscountType;
import com.tanerus.shopping.cart.model.cart.ShoppingCart;
import com.tanerus.shopping.cart.model.category.Category;
import com.tanerus.shopping.cart.model.discount.Campaign;
import com.tanerus.shopping.cart.model.discount.Coupon;
import com.tanerus.shopping.cart.model.product.Product;

/**
 * Unit test for simple App.
 */
public class AppTest {

	final double THRESHOLD = .001;

	@Test
	public void addItemCartTest() {
		Category food = new Category("food");

		Product apple = new Product("Apple", 100.0, food);
		Product almond = new Product("Almonds", 150.0, food);

		ShoppingCart cart = new ShoppingCart();
		cart.addItem(apple, 4);
		cart.addItem(apple, 2);
		cart.addItem(almond, 5);

		Map<String, List<Product>> map1 = cart.getProductList().stream()
				.collect(Collectors.groupingBy(Product::getTitle));

		// Added apple two times(4,2 quantity)
		assertEquals(6, map1.get("Apple").get(0).getQuantity());
		assertEquals(5, map1.get("Almonds").get(0).getQuantity());

	}

	@Test
	public void checkCampaign() {
		ShoppingCart cart = new ShoppingCart();
		Category food = new Category("food");

		Product apple = new Product("Apple", 100.0, food);
		cart.addItem(apple, 10);

		Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
		Campaign campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
		Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);

		cart.applyDiscount(campaign1, campaign2, campaign3);

		// campaign1 discount price --> quantity 10 > 3 --> 100.0*10 * (20/100)
		// = 200 tl discount
		// campaign2 discount price --> quantity 10 > 5 --> 100.0*10 * (50/100)
		// = 500 tl discount
		// campaign3 discount price --> quantity 10 > 5 --> 5.0 tl discount
		assertTrue(500 == cart.getProductList().get(0).getDiscountedAmount());

	}

	@Test
	public void checkCouponDiscount() {
		ShoppingCart cart = new ShoppingCart();
		Category food = new Category("food");

		Product apple = new Product("Apple", 100.0, food);
		cart.addItem(apple, 10);

		Coupon coupon = new Coupon(100, 10, DiscountType.Rate);

		cart.applyCoupon(coupon);

		// coupon discount price --> amount 1000 > 100 --> 100.0*10 * (10/100) =
		// 100 tl discount
		assertTrue(100 == cart.getProductList().get(0).getCouponDisAmount());

	}

	@Test
	public void checkDiscountAll() {
		ShoppingCart cart = new ShoppingCart();
		Category food = new Category("food");

		Product apple = new Product("Apple", 100.0, food);
		cart.addItem(apple, 10);

		Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
		Campaign campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
		Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);

		Coupon coupon = new Coupon(100, 10, DiscountType.Rate);

		cart.applyCoupon(coupon);

		cart.applyDiscount(campaign1, campaign2, campaign3);

		// campaign1 discount price --> quantity 10 > 3 --> 100.0*10 * (20/100)
		// = 200 tl discount
		// campaign2 discount price --> quantity 10 > 5 --> 100.0*10 * (50/100)
		// = 500 tl discount
		// campaign3 discount price --> quantity 10 > 5 --> 5.0 tl discount
		assertTrue(500 == cart.getProductList().get(0).getDiscountedAmount());

		// coupon discount price --> amount 1000- 500 > 100 --> 500 * (10/100) =
		// 50 tl discount
		assertTrue(50 == cart.getProductList().get(0).getCouponDisAmount());

	}

	@Test
	public void calculateDeliveryCost() {
		ShoppingCart cart = new ShoppingCart();
		Category food = new Category("food");
		Category stuff = new Category("stuff");

		Product apple = new Product("Apple", 100.0, food);
		Product almond = new Product("Almonds", 150.0, food);

		Product tv = new Product("Tv", 1500.0, stuff);

		cart.addItem(apple, 10);
		cart.addItem(apple, 20);
		cart.addItem(almond, 5);
		cart.addItem(tv, 2);

		// Formula is ( CostPerDelivery * NumberOfDeliveries ) + (CostPerProduct
		// * NumberOfProducts) + Fixed Cost
		DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(4, 5);

		// CostPerDelivery --> 4, CostPerProduct --> 5, NumberOfDeliveries --> 2
		// distinct categories, NumberOfProducts --> 3, fixedCost = 2.99
		// 4 * 2 + 5 * 3 + 2.99 = 25.99

		assertTrue(Math.abs(25.99 - cart.getDeliveryCost(deliveryCostCalculator)) < THRESHOLD);

	}
	
	@Test
	public void checkOtherFunctionsCart() {
		ShoppingCart cart = new ShoppingCart();
		Category food = new Category("food");
		Category stuff = new Category("stuff");

		Product apple = new Product("Apple", 100.0, food);
		Product almond = new Product("Almonds", 150.0, food);

		Product tv = new Product("Tv", 1500.0, stuff);

		cart.addItem(apple, 10);
		cart.addItem(apple, 20);
		cart.addItem(almond, 5);
		cart.addItem(tv, 2);
		
		Campaign campaign1 = new Campaign(food, 10.0, 3, DiscountType.Rate);
		Campaign campaign2 = new Campaign(food, 20.0, 5, DiscountType.Rate);
		Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);
		
		Campaign campaign4 = new Campaign(stuff, 10.0, 3, DiscountType.Rate);
		Campaign campaign5 = new Campaign(stuff, 25.0, 5, DiscountType.Rate);
		Campaign campaign6 = new Campaign(stuff, 50.0, 1, DiscountType.Amount);

		cart.applyDiscount(campaign1, campaign2, campaign3, campaign4, campaign5, campaign6);
		
		Coupon coupon = new Coupon(100, 10, DiscountType.Rate);

		cart.applyCoupon(coupon);		
				
		// total price = 100 * 30 + 150 * 5 + 1500 * 2 = 6750
		// campaign discount = 600 + 75 + 50 = 725
		// couponDiscount = 6750 - 725 
		// couponDiscount = (3000 - 600) *10/100 + (750-75) *10/100 + (3000-50)*10/100 = 602.5 
		
		// 6750 - 725 - 602.5 = 5422.5
		assertTrue(5422.5 == cart.getTotalAmountAfterDiscounts());
		//602.5
		assertTrue(602.5 == cart.getCouponDiscount());
		//725
		assertTrue(725 == cart.getCampaignDiscount());
				
	}

}
