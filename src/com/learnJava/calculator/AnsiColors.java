package com.learnJava.calculator;

public enum AnsiColors {

	/*
	 * Basically enums are constant objects. We declare this special type like a
	 * POJO class without using setter methods because they keep constant values.
	 */

	DEFAULT("\u001B[0m"), RED("\u001B[31m"), GREEN("\u001B[32m"), YELLOW("\u001B[33m"), BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"), CYAN("\u001B[36m");

	private final String color;

	private AnsiColors(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public static boolean exists(String userInput) {
		// Use of Guard Clause to avoid unnecessary cycles. 
		if (userInput.equals("GREEN") || userInput.equals("RED") || userInput.equals("YELLOW")) {
			return false;
		}
		
		for (AnsiColors color : AnsiColors.values()) {
			if (color.toString().equals(userInput)) {
				return true;
			}
		}
		
		return false;
	}
}
