package com.learnJava.calculator;

public class CalcMethods {
	/*
	 * I am aware of the fact that extensive use of static in Java is considered as 
	 * bad practice but this is a simple calculator application so it's OK for me 
	 * at this point.
	 */
	static String[] operations = {"ADDITION", "SUBTRACTION", "DIVISION", "MULTIPLICATION"};
	
	public static boolean exists(String op) {
		for (String operation : operations) {
			if (operation.equals(op.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Static methods can be used without the need to create an object. It's
	 * important to understand that they cannot be overridden in subclasses because
	 * they are tightly coupled to the class they were created in.
	 * 
	 * I am using them because I believe it's not necessary to create an object just
	 * to make some calculations.
	 */
	public static int addition(int num1, int num2) {
		return num1 + num2;
	}

	// Method overload
	public static double addition(double num1, double num2) {
		return num1 + num2;
	}
	
	public static int subtraction(int num1, int num2) {
		return num1 - num2;
	}

	// Method overload
	public static double subtraction(double num1, double num2) {
		return num1 - num2;
	}
	
	public static int division(int num1, int num2) {
		return num1 / num2;
	}

	// Method overload
	public static double division(double num1, double num2) {
		return num1 / num2;
	}
	
	public static int multiplication(int num1, int num2) {
		return num1 * num2;
	}

	// Method overload
	public static double multiplication(double num1, double num2) {
		return num1 * num2;
	}
}
