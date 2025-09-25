package com.calculator;

import java.util.Scanner;

public class CalculatorCLI {
    
    private Calculator calculator;
    private Scanner scanner;
    
    public CalculatorCLI() {
        this.calculator = new Calculator();
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("Welcome to Calculator CLI!");
        
        while (true) {
            try {
                displayMainMenu();
                String choice = scanner.nextLine().trim();
                
                switch (choice) {
                    case "1":
                        handleBasicCalculation();
                        break;
                    case "2":
                        handleAdvancedOperations();
                        break;
                    case "3":
                        handleMemoryOperations();
                        break;
                    case "4":
                        startGUI();
                        break;
                    case "5":
                        System.out.println("Thank you for using Calculator!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== CALCULATOR MENU ===");
        System.out.println("1. Basic Calculation");
        System.out.println("2. Advanced Operations");
        System.out.println("3. Memory Operations");
        System.out.println("4. Start GUI");
        System.out.println("5. Exit");
        System.out.print("\nChoose option: ");
    }
    
    private void handleBasicCalculation() throws CalculatorException {
        System.out.println("\n=== BASIC CALCULATION ===");
        
        double num1 = getNumberInput("Enter first number: ");
        String operation = getOperationInput();
        double num2 = getNumberInput("Enter second number: ");
        
        double result = performOperation(num1, operation, num2);
        System.out.println("Result: " + calculator.formatResult(result));
    }
    
    private void handleAdvancedOperations() throws CalculatorException {
        System.out.println("\n=== ADVANCED OPERATIONS ===");
        System.out.println("1. Square Root");
        System.out.println("2. Square (x^2)");
        System.out.println("3. Power (x^y)");
        System.out.print("Choose operation: ");
        
        String choice = scanner.nextLine().trim();
        double result = 0;
        
        switch (choice) {
            case "1":
                double num = getNumberInput("Enter number: ");
                result = calculator.squareRoot(num);
                break;
            case "2":
                num = getNumberInput("Enter number: ");
                result = calculator.power(num, 2);
                break;
            case "3":
                double base = getNumberInput("Enter base: ");
                double exponent = getNumberInput("Enter exponent: ");
                result = calculator.power(base, exponent);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        System.out.println("Result: " + calculator.formatResult(result));
    }
    
    private void handleMemoryOperations() throws CalculatorException {
        System.out.println("\n=== MEMORY OPERATIONS ===");
        System.out.println("Current Memory: " + calculator.formatResult(calculator.memoryRecall()));
        System.out.println("1. Store value");
        System.out.println("2. Clear memory");
        System.out.print("Choose operation: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                double value = getNumberInput("Enter value to store: ");
                calculator.memoryStore(value);
                System.out.println("Value stored in memory");
                break;
            case "2":
                calculator.memoryClear();
                System.out.println("Memory cleared");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void startGUI() {
        System.out.println("Starting GUI Calculator...");
        try {
            CalculatorGUI.main(new String[]{});
        } catch (Exception e) {
            System.out.println("Error starting GUI: " + e.getMessage());
        }
    }
    
    private double getNumberInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
    
    private String getOperationInput() {
        System.out.print("Choose operation (+, -, *, /): ");
        return scanner.nextLine().trim();
    }
    
    private double performOperation(double a, String operation, double b) throws CalculatorException {
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
    
    public static void main(String[] args) {
        if (args.length > 0 && "--gui".equals(args[0])) {
            CalculatorGUI.main(args);
        } else {
            new CalculatorCLI().run();
        }
    }
}


