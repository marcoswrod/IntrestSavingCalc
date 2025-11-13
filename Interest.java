/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interest;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Marcos Rodriguez
 * Enhanced Savings & Interest Calculator
 */
public class Interest {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Savings & Interest Calculator ===");

        double yearlySavings = getPositiveDouble(input, "How much will you save per year? ");
        double interestRatePercent = getPositiveDouble(input, "What annual interest rate will you earn? (in %) ");
        int yearsToSave = getPositiveInt(input, "How many years will you save? ");

        double interestRate = interestRatePercent / 100.0;

        int compoundingChoice = getCompoundingChoice(input);

        int compoundsPerYear;
        if (compoundingChoice == 1) {
            compoundsPerYear = 1;   // annual
        } else {
            compoundsPerYear = 12;  // monthly
        }

        runSimulation(yearlySavings, interestRate, yearsToSave, compoundsPerYear);

        input.close();
    }

    /**
     * Prompts the user for a positive double value.
     */
    private static double getPositiveDouble(Scanner input, String prompt) {
        double value = -1;
        while (value <= 0) {
            System.out.print(prompt);
            try {
                value = input.nextDouble();
                if (value <= 0) {
                    System.out.println("Value must be greater than 0. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                input.next(); // clear invalid input
            }
        }
        return value;
    }

    /**
     * Prompts the user for a positive integer value.
     */
    private static int getPositiveInt(Scanner input, String prompt) {
        int value = -1;
        while (value <= 0) {
            System.out.print(prompt);
            try {
                value = input.nextInt();
                if (value <= 0) {
                    System.out.println("Value must be greater than 0. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                input.next(); // clear invalid input
            }
        }
        return value;
    }

    /**
     * Lets the user choose annual or monthly compounding.
     */
    private static int getCompoundingChoice(Scanner input) {
        int choice = 0;
        System.out.println();
        System.out.println("Choose compounding frequency:");
        System.out.println("1. Annual compounding");
        System.out.println("2. Monthly compounding");

        while (choice != 1 && choice != 2) {
            System.out.print("Enter your choice (1 or 2): ");
            try {
                choice = input.nextInt();
                if (choice != 1 && choice != 2) {
                    System.out.println("Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                input.next(); // clear invalid input
            }
        }
        return choice;
    }

    /**
     * Runs the savings simulation and prints a formatted yearly report.
     */
    private static void runSimulation(double yearlySavings, double annualRate,
                                      int yearsToSave, int compoundsPerYear) {

        double balance = 0.0;
        double totalContributed = 0.0;
        double totalInterestEarned = 0.0;

        double periodRate = annualRate / compoundsPerYear;
        double periodicContribution = yearlySavings / compoundsPerYear;

        System.out.println();
        System.out.println("===============================================");
        System.out.println("Compounds per year: " + compoundsPerYear);
        System.out.println("Period interest rate: " + String.format("%.4f", periodRate * 100) + "%");
        System.out.println("===============================================");
        System.out.printf("%-6s %-18s %-18s %-18s%n",
                "Year", "Year Contribution", "Year Interest", "End Balance");
        System.out.println("--------------------------------------------------------------------");

        for (int year = 1; year <= yearsToSave; year++) {
            double yearContribution = 0.0;
            double yearInterest = 0.0;

            // For each compounding period in the year
            for (int period = 1; period <= compoundsPerYear; period++) {
                // deposit for this period
                balance += periodicContribution;
                yearContribution += periodicContribution;
                totalContributed += periodicContribution;

                // interest for this period
                double interestThisPeriod = balance * periodRate;
                balance += interestThisPeriod;

                yearInterest += interestThisPeriod;
                totalInterestEarned += interestThisPeriod;
            }

            System.out.printf("%-6d $%-17.2f $%-17.2f $%-17.2f%n",
                    year, yearContribution, yearInterest, balance);
        }
        
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("Total Contributed:     $%.2f%n", totalContributed);
        System.out.printf("Total Interest Earned: $%.2f%n", totalInterestEarned);
        System.out.printf("Final Balance:         $%.2f%n", balance);
        System.out.println("===============================================");
    }
}