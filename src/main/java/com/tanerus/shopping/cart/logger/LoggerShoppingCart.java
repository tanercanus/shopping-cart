package com.tanerus.shopping.cart.logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Logger;

import com.tanerus.shopping.cart.constants.ProjectConstant;
import com.tanerus.shopping.cart.model.product.Product;

/**
 * @author TanerCan LoggerShoppingCart class Can change its format with added a
 *         properties file
 */
public class LoggerShoppingCart {

	// This implementation can be change
	private Logger logger;

	NumberFormat formatter = new DecimalFormat("#0.00");

	public LoggerShoppingCart(Object obj) {
		this.logger = Logger.getLogger(obj.getClass().getName());
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void printLog(Product value) {

		// This code can change
		getLogger().info("CategoryName: " + value.getCategory().getTitle() + ", ProductName: " + value.getTitle()
				+ ", Quantity: " + value.getQuantity() + ", UnitPrice: " + formatter.format(value.getPrice()) + value.getCurrency()
				+ ", TotalPrice: " + formatter.format((value.getQuantity() * value.getPrice())) + value.getCurrency()
				+ ", TotalDiscount: " + formatter.format(value.getQuantity() * (value.getDiscountedAmount() + value.getCouponDisAmount()))
				+ value.getCurrency());

	}

	public void printTotalAmount(double totalAmount) {
		// If we have different currencies, it has to change
		getLogger().info("Total Amount: " + totalAmount + ProjectConstant.DEFAULT_CURRENCY);
	}

	public void printDeliveryCost(double deliveryCost) {
		getLogger().info("Delivery Cost: " + formatter.format(deliveryCost) + ProjectConstant.DEFAULT_CURRENCY);
	}

}
