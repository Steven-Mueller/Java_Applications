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
	 * previous calculation. Btw I have to use static variables anyway because the main method is 
	 * static and it's not possible to work with non-static variables in a static method.
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
		// Create a batch file for the user to start the program in console
		File batchFile = new File("start_calc.bat");
		try {
			FileWriter writer = new FileWriter(batchFile);
			writer.write("start java -jar calc.jar");
			writer.close();
		} catch (IOException e) {
			System.out.println("Could not write file \"start_calc.bat\"");
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
		operation = sc.nextLine();

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

		// TODO: Need to verify input in a method and return the correct data type
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
				Integer.parseInt(num1);
				Integer.parseInt(num2);
				if (!isValdInput)
					isValdInput = true;
			} catch (NumberFormatException e) {
				System.out
						.println(AnsiColors.YELLOW.getColor() + "Type mismatch, try to convert into suitable type...");
				try {
					Double.parseDouble(num1);
					System.out.println(AnsiColors.GREEN.getColor() + "First number successfully converted !");

					Double.parseDouble(num2);
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

		System.out.println("Do more stuff...");
		
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
}
