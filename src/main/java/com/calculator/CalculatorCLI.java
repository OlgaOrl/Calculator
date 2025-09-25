package com.calculator;

import java.util.Scanner;

public class CalculatorCLI {
    private Calculator calculator;
    private Scanner scanner;
    
    public CalculatorCLI() {
        calculator = new Calculator();
        scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("Welcome to Calculator CLI!");
        
        while (true) {
            System.out.println("\n=== CALCULATOR MENU ===");
            System.out.println("1. Basic Calculation");
            System.out.println("2. Exit");
            System.out.print("Choose option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    performBasicCalculation();
                    break;
                case "2":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void performBasicCalculation() {
        try {
            System.out.print("Enter first number: ");
            double a = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Enter operation (+, -, *, /): ");
            String op = scanner.nextLine().trim();
            
            System.out.print("Enter second number: ");
            double b = Double.parseDouble(scanner.nextLine());
            
            double result = 0;
            switch (op) {
                case "+": result = calculator.add(a, b); break;
                case "-": result = calculator.subtract(a, b); break;
                case "*": result = calculator.multiply(a, b); break;
                case "/": result = calculator.divide(a, b); break;
                default: 
                    System.out.println("Invalid operation");
                    return;
            }
            
            System.out.println("Result: " + result);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new CalculatorCLI().run();
    }
}

