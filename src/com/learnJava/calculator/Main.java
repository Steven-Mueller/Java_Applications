package com.learnJava.calculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * This is a simple application for training purposes.
 * At this point I don't really care about principles or 
 * complexity. For me it's a playground to test implementation
 * of different stuff and get my hands "dirty" with Java at all.
 * I play as much as possible with my ideas to make this
 * project truly "MY-PROJECT" and to have some fun.
 * 
 * In some parts I will document my observations and thoughts.
 * Maybe they are wrong, maybe not, it's just what I believe to see
 * or happens at this moment.
 * 
 * Honestly, I am tired of theoretical learning and just want to start to 
 * do stuff, that's it <(*.*<)
 */

public class Main {
	/*
	 * I am using static variables to make it possible to reuse the result of the
	 * previous calculation. Btw I have to use static variables anyway because the
	 * main method is static and it's not possible to work with non-static variables
	 * in a static method.
	 */
	public static int intResult;
	public static double doubleResult;

	public static String color;
	public static int userTime;
	public static String operation;

	// TODO: Maybe I should use simple variable for this
	public static String favouriteColor(String color) {
		return AnsiColors.valueOf(color.toUpperCase()).getColor();
	}

	// Begin of main method
	public static void main(String[] args) {
		// Check java version because 17+ is needed
		String[] javaVersion = System.getProperty("java.version").split("\\.");
		int jvNum = Integer.parseInt(javaVersion[0]);

		String errorMessage = "Java Version below 17 detected !";
		if (jvNum < 17) {
			try {
				Runtime.getRuntime().exec("cmd /c start cmd /k echo " 
						+ AnsiColors.RED.getColor() + errorMessage + AnsiColors.DEFAULT.getColor());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		} else {
			System.out.println(AnsiColors.GREEN.getColor() + "Java Version " 
					+ System.getProperty("java.version") + " detected" 
					+ AnsiColors.DEFAULT.getColor());
		}

		/*
		 *  Create a batch file for the user to start the program in console.
		 *  Unfortunately I could not figure out how to start my application
		 *  in the console directly...
		 *  
		 *  I tried Runtime.getRuntime().exec("cmd /c start java -jar calc.jar")
		 *  like i did for JRE check, but this created a permanent loop so idk.
		 */
		 
		
		File batchFile = new File("start_calc.bat");
		if (!batchFile.exists()) {
			try {
				FileWriter writer = new FileWriter(batchFile);
				writer.write("start java -jar calc.jar");
				writer.close();
			} catch (IOException e) {
				System.out.println("Could not write file \"start_calc.bat\"");
			}
			return;
		}

		boolean colorAccepted = false;
		Scanner sc = new Scanner(System.in);

		int counter = 0;
		do {
			if (counter > 0) {
				clearScreen();
				System.out.println(AnsiColors.RED.getColor() + "No valid input, please try again !"
						+ AnsiColors.DEFAULT.getColor() + "\n");
			}

			System.out.print("Please choose a color.\n" + "\nOptions -> BLUE, PURPLE, CYAN" + "\nInput: ");
			color = sc.nextLine().trim();
			colorAccepted = AnsiColors.exists(color.toUpperCase());
			counter++;
		} while (!colorAccepted);

		clearScreen();

		System.out.println(favouriteColor(color) + """

				Basic Calculator
				================
				""" + AnsiColors.DEFAULT.getColor());

		System.out.print(
				"Which operation you wanna make ?\n" + "\nOptions -> ADDITION, SUBTRACTION, DIVISION, MULTIPLICATION\n"
						+ favouriteColor(color) + "Input: " + AnsiColors.DEFAULT.getColor());
		operation = sc.nextLine().trim();

		if (!CalcMethods.exists(operation)) {
			do {
				clearScreen();
				System.out.print(AnsiColors.RED.getColor()
						+ "\nCan not find any suitable operation, please try again !\n" + AnsiColors.DEFAULT.getColor()
						+ "\nOptions -> ADDITION, SUBTRACTION, DIVISION, MULTIPLICATION\n" + favouriteColor(color)
						+ "Input: " + AnsiColors.DEFAULT.getColor());
				operation = sc.nextLine();
			} while (!CalcMethods.exists(operation));
		}

		/*
		 * Couldn't find any better solution so I will use 2 data types for each
		 * possible outcome.
		 */
		int intNum1 = 0, intNum2 = 0;
		double doubleNum1 = 0, doubleNum2 = 0;
		boolean isValdInput = true;
		do {
			clearScreen();

			System.out.println(AnsiColors.GREEN.getColor() + "Chosen operation: " + AnsiColors.DEFAULT.getColor()
					+ operation.toUpperCase() + "\n");

			System.out.print(favouriteColor(color) + "\nPlease enter first number: " + AnsiColors.DEFAULT.getColor());
			String num1 = sc.nextLine();

			System.out.print(favouriteColor(color) + "Please enter secons number: " + AnsiColors.DEFAULT.getColor());
			String num2 = sc.nextLine();

			try {
				intNum1 = Integer.parseInt(num1);
				intNum2 = Integer.parseInt(num2);
				if (!isValdInput)
					isValdInput = true;
				if (num1.equals("0") && num2.equals("0")) {
					System.out.println(AnsiColors.RED.getColor() + "Input doesn't make sense, please try again."
							+ AnsiColors.DEFAULT.getColor() + "\n");
					isValdInput = false;
				}
			} catch (NumberFormatException e) {
				System.out
						.println(AnsiColors.YELLOW.getColor() + "Type mismatch, try to convert into suitable type...");
				try {
					doubleNum1 = Double.parseDouble(num1);
					System.out.println(AnsiColors.GREEN.getColor() + "First number successfully converted !");

					doubleNum2 = Double.parseDouble(num2);
					System.out.println("Second number successfully converted !" + AnsiColors.DEFAULT.getColor());

					try {
						timeToRead();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					if (!isValdInput)
						isValdInput = true;
				} catch (NumberFormatException n) {
					System.out.println(AnsiColors.RED.getColor()
							+ "Unable to convert input into suitable type for arithmetic operations."
							+ AnsiColors.DEFAULT.getColor());
					isValdInput = false;

					try {
						timeToRead();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		} while (!isValdInput);

		sc.close();

		clearScreen();

		// Calculations
		if (intNum1 != 0 && intNum2 != 0) {
			intResult = getResult(intNum1, intNum2, operation);
			System.out.println(intResult);
		} else {
			doubleResult = getResult(doubleNum1, doubleNum2, operation);
			System.out.println(doubleResult);
		}

		try {
			timeToRead();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	// Stop current thread and give the user some time to read
	public static void timeToRead() throws InterruptedException {
		System.out.print("\nContinue: ");
		for (int i = 5; i > 0; i--) {
			System.out.print(i + " ");
			Thread.sleep(1000);
		}
	}

	public static int getResult(int num1, int num2, String operation) {
		return switch (operation.toUpperCase()) {
		case "ADDITION" -> CalcMethods.addition(num1, num2);
		case "SUBTRACTION" -> CalcMethods.subtraction(num1, num2);
		case "MULTIPLICATION" -> CalcMethods.multiplication(num1, num2);
		case "DIVISION" -> CalcMethods.division(num1, num2);
		default -> 0;
		};
	}

	public static double getResult(double num1, double num2, String operation) {
		return switch (operation.toUpperCase()) {
		case "ADDITION" -> CalcMethods.addition(num1, num2);
		case "SUBTRACTION" -> CalcMethods.subtraction(num1, num2);
		case "MULTIPLICATION" -> CalcMethods.multiplication(num1, num2);
		case "DIVISION" -> CalcMethods.division(num1, num2);
		default -> 0;
		};
	}
}
