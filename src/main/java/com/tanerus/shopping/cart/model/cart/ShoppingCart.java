package com.tanerus.shopping.cart.model.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tanerus.shopping.cart.calculator.DeliveryCostCalculator;
import com.tanerus.shopping.cart.logger.LoggerShoppingCart;
import com.tanerus.shopping.cart.model.category.Category;
import com.tanerus.shopping.cart.model.discount.Campaign;
import com.tanerus.shopping.cart.model.discount.Coupon;
import com.tanerus.shopping.cart.model.product.Product;

/**
 * @author TanerCan ShoppingCart class
 */
public class ShoppingCart {

	private List<Product> productList = new ArrayList<>();
	private DeliveryCostCalculator deliveryCostCalculator;

	private LoggerShoppingCart logger;

	public ShoppingCart() {
		super();
		/*
		 * Because of SRP principle, logger has different class and add it with
		 * composition.
		 */
		this.logger = new LoggerShoppingCart(this);
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	// Add new item to Shopping Cart
	public void addItem(Product product, int quantity) {

		Product productExist = productList.stream().filter(item -> product.getTitle().equals(item.getTitle())).findAny()
				.orElse(null);

		if (productExist != null) {
			productExist.setQuantity(productExist.getQuantity() + quantity);
		} else {
			product.setQuantity(quantity);
			productList.add(product);
		}

	}

	public void applyDiscount(Campaign... campaigns) {
		// calculate max discount

		for (int i = 0; i < productList.size(); i++) {

			Product item = productList.get(i);

			int campaignLength = campaigns.length;
			double[] discountedAmounts = new double[campaignLength];
			for (int j = 0; j < campaignLength; j++) {
				discountedAmounts[j] = campaigns[j].getDiscountAmount(item);
			}

			// unit discount amount
			double maxDiscountedAmount = Arrays.stream(discountedAmounts).max().getAsDouble();
			item.setDiscountedAmount(maxDiscountedAmount);

			// If applyCoupon called before applyDiscount, recalculate
			// getCouponDisAmount
			if (item.getCouponDisAmount() != 0) {
				setProductCouponDisc(item, Coupon.coupon);
			}

		}

	}

	public void applyCoupon(Coupon coupon) {
		// make discount
		for (int i = 0; i < productList.size(); i++) {
			Product item = productList.get(i);
			setProductCouponDisc(item, coupon);
		}

	}

	private void setProductCouponDisc(Product item, Coupon coupon) {
		double couponDisAmount = coupon.getDiscountAmount(item);
		item.setCouponDisAmount(couponDisAmount);
	}

	public double getTotalAmountAfterDiscounts() {
		double totalAmounAfterDiscounts = 0;

		totalAmounAfterDiscounts = productList.stream().mapToDouble(
				item -> item.getPrice() * item.getQuantity() - item.getDiscountedAmount() - item.getCouponDisAmount())
				.sum();

		return totalAmounAfterDiscounts;
	}

	// get all coupon discount
	public double getCouponDiscount() {
		double couponDiscount = 0;
		couponDiscount = productList.stream().mapToDouble(item -> item.getCouponDisAmount()).sum();
		return couponDiscount;
	}

	// get all campaign discount
	public double getCampaignDiscount() {
		double campaignDiscount = 0;

		campaignDiscount = productList.stream().mapToDouble(item -> item.getDiscountedAmount()).sum();

		return campaignDiscount;
	}

	public double getDeliveryCost(DeliveryCostCalculator deliveryCostCalculator) {
		
		if ( deliveryCostCalculator != null ) {
			this.deliveryCostCalculator = deliveryCostCalculator;
			return deliveryCostCalculator.calculateFor(this);
		} else {
			return 0;
		}
		

	}

	@SuppressWarnings("serial")
	public void print() {

		Map<Category, List<Product>> groupByCategoryProduct = new HashMap<>();
		for (int i = 0; i < productList.size(); i++) {
			Product item = productList.get(i);

			List<Product> products = groupByCategoryProduct.get(item.getCategory());
			if (products != null) {
				products.add(item);
			} else {
				groupByCategoryProduct.put(item.getCategory(), new ArrayList<Product>() {
					{
						add(item);
					}
				});
			}

		}

		for (Map.Entry<Category, List<Product>> entry : groupByCategoryProduct.entrySet()) {

			entry.getValue().forEach(value -> {
				getLogger().printLog(value);
			});

		}

		getLogger().printTotalAmount(
				this.getProductList().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());

		getLogger().printDeliveryCost(getDeliveryCost(this.deliveryCostCalculator));

	}

	public LoggerShoppingCart getLogger() {
		return logger;
	}

	public void setLogger(LoggerShoppingCart logger) {
		this.logger = logger;
	}

	public DeliveryCostCalculator getDeliveryCostCalculator() {
		return deliveryCostCalculator;
	}

	public void setDeliveryCostCalculator(DeliveryCostCalculator deliveryCostCalculator) {
		this.deliveryCostCalculator = deliveryCostCalculator;
	}

}
