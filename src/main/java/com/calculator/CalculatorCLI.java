package com.calculator;

import java.util.Scanner;

/**
 * Command Line Interface for the Calculator application.
 */
public class CalculatorCLI {
    
    private final Calculator calculator;
    private final Scanner scanner;
    
    public CalculatorCLI() {
        this.calculator = new Calculator();
        this.scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        CalculatorCLI cli = new CalculatorCLI();
        cli.run();
    }
    
    public void run() {
        System.out.println("=== Calculator CLI ===");
        
        while (true) {
            try {
                System.out.println("\nAvailable operations: +, -, *, /");
                
                double firstNumber = getNumberInput("Enter first number: ");
                String operation = getOperationInput();
                double secondNumber = getNumberInput("Enter second number: ");
                
                double result = performCalculation(firstNumber, operation, secondNumber);
                System.out.println("Result: " + firstNumber + " " + operation + " " + secondNumber + " = " + result);
                
                if (!askForAnotherCalculation()) {
                    break;
                }
                
            } catch (CalculatorException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        
        System.out.println("Thank you for using Calculator CLI!");
        scanner.close();
    }
    
    private double performCalculation(double a, String operation, double b) throws CalculatorException {
        switch (operation) {
            case "+":
                return calculator.add(a, b);
            case "-":
                return calculator.subtract(a, b);
            case "*":
                return calculator.multiply(a, b);
            case "/":
                return calculator.divide(a, b);
            default:
                throw new InvalidInputException("Invalid operation: " + operation);
        }
    }
    
    private double getNumberInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private String getOperationInput() {
        while (true) {
            System.out.print("Choose operation (+, -, *, /): ");
            String operation = scanner.nextLine().trim();
            
            if (operation.equals("+") || operation.equals("-") || 
                operation.equals("*") || operation.equals("/")) {
                return operation;
            } else {
                System.out.println("Please choose a valid operation: +, -, *, /");
            }
        }
    }
    
    private boolean askForAnotherCalculation() {
        while (true) {
            System.out.print("Would you like to perform another calculation? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y") || response.equals("yes")) {
                return true;
            } else if (response.equals("n") || response.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}

