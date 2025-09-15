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
        System.out.println("🧮 === WELCOME TO CALCULATOR === 🧮");
        System.out.println("Let's do some math together!");
        
        while (true) {
            try {
                System.out.println("\n" + "=".repeat(40));
                
                double firstNumber = getNumberInput("Enter first number: ");
                String operation = getOperationInput();
                double secondNumber = getNumberInput("Enter second number: ");
                
                // Handle division by zero with helpful recovery
                if (operation.equals("/") && secondNumber == 0.0) {
                    displayDivisionByZeroHelp();
                    System.out.print("Would you like to try a different second number? (y/n): ");
                    String retry = scanner.nextLine().trim().toLowerCase();
                    
                    if (retry.equals("y") || retry.equals("yes")) {
                        secondNumber = getNumberInput("Enter different second number: ");
                    } else {
                        continue; // Skip this calculation
                    }
                }
                
                double result = performCalculation(firstNumber, operation, secondNumber);
                displayResult(firstNumber, operation, secondNumber, result);
                
                if (!askForAnotherCalculation()) {
                    break;
                }
                
            } catch (CalculatorException e) {
                System.out.println("⚠️  " + e.getMessage());
                System.out.println("💡 Let's try again!");
            } catch (Exception e) {
                System.out.println("🤔 Something unexpected happened, but don't worry!");
                System.out.println("💡 Let's continue with a fresh calculation.");
            }
        }
        
        System.out.println("\n🎉 Thank you for using Calculator!");
        System.out.println("Have a great day! 😊");
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
            String input = scanner.nextLine();
            
            if (input == null || input.trim().isEmpty()) {
                displayEmptyInputHelp();
                continue;
            }
            
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                displayInvalidNumberHelp(input.trim());
            }
        }
    }
    
    private String getOperationInput() {
        while (true) {
            System.out.print("Choose operation (+, -, *, /): ");
            String input = scanner.nextLine();
            
            if (input == null || input.trim().isEmpty()) {
                displayEmptyOperationHelp();
                continue;
            }
            
            String operation = input.trim();
            if (operation.equals("+") || operation.equals("-") || 
                operation.equals("*") || operation.equals("/")) {
                return operation;
            } else {
                displayInvalidOperationHelp(operation);
            }
        }
    }
    
    private void displayInvalidNumberHelp(String invalidInput) {
        System.out.println("❌ Oops! '" + invalidInput + "' is not a valid number.");
        System.out.println("💡 Please enter a number like:");
        System.out.println("   • Whole numbers: 5, 42, 100");
        System.out.println("   • Negative numbers: -5, -10.5"); 
        System.out.println("   • Decimal numbers: 3.14, 0.25, -2.7");
    }

    private void displayEmptyInputHelp() {
        System.out.println("💭 You didn't enter anything!");
        System.out.println("💡 Please type a number and press Enter.");
        System.out.println("   Example: Type '5' then press Enter");
    }

    private void displayInvalidOperationHelp(String invalidOperation) {
        System.out.println("❌ Oops! '" + invalidOperation + "' is not a valid operation.");
        System.out.println("💡 Please choose one of these:");
        System.out.println("   • + for Addition (example: 5 + 3)");
        System.out.println("   • - for Subtraction (example: 8 - 2)");
        System.out.println("   • * for Multiplication (example: 4 * 7)");
        System.out.println("   • / for Division (example: 10 / 2)");
    }

    private void displayEmptyOperationHelp() {
        System.out.println("💭 You didn't choose an operation!");
        System.out.println("💡 Please type one of: +, -, *, /");
    }

    private void displayDivisionByZeroHelp() {
        System.out.println("⚠️  Division by zero is undefined in mathematics!");
        System.out.println("💡 Try dividing by a different number (like 1, 2, 0.5)");
        System.out.println("   Remember: 10 ÷ 2 = 5, but 10 ÷ 0 is not possible");
    }
    
    private void displayResult(double first, String operation, double second, double result) {
        System.out.println("\n✨ Result: " + first + " " + operation + " " + second + " = " + result);
    }
    
    private boolean askForAnotherCalculation() {
        while (true) {
            System.out.print("\nWould you like to perform another calculation? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y") || response.equals("yes")) {
                return true;
            } else if (response.equals("n") || response.equals("no")) {
                return false;
            } else {
                System.out.println("💡 Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}


