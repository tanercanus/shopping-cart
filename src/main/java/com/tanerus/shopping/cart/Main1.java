package com.tanerus.shopping.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main1 {

	// Complete the biggerIsGreater function below.
	static String biggerIsGreater(String w) {

		try {
			char[] passwordInCharArray = w.toCharArray();
			int size = passwordInCharArray.length;

			for (int i = 0; i < size; i++) {
				char iItem = passwordInCharArray[size - 1 - i];
				boolean found = false;
				for (int j = i + 1; j < size; j++) {
					if (iItem > passwordInCharArray[size - 1 - j]) {

						char temp = passwordInCharArray[size - 1-j];
						passwordInCharArray[size - 1- j] = iItem;
						passwordInCharArray[size - 1- i] = temp;
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}

			if (w.equals(String.valueOf(passwordInCharArray))) {
				return "no answer";
			} else {
				return String.valueOf(passwordInCharArray);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return "no answer";
		}

	}

	public static void main(String[] args) {

		String result = biggerIsGreater("ab");

		System.out.println(result);

		result = biggerIsGreater("bb");

		System.out.println(result);

		result = biggerIsGreater("hefg");

		System.out.println(result);

		result = biggerIsGreater("dhck");

		System.out.println(result);

		result = biggerIsGreater("dkhc");

		System.out.println(result);

	}

}
