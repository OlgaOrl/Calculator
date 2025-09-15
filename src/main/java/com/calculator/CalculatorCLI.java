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
                displayMainMenu();
                int choice = getMenuChoice();
                
                switch (choice) {
                    case 1:
                        performBasicCalculation();
                        break;
                    case 2:
                        handleMemoryOperations();
                        break;
                    case 3:
                        viewMemory();
                        break;
                    case 4:
                        System.out.println("\n🎉 Thank you for using Calculator!");
                        System.out.println("Have a great day! 😊");
                        scanner.close();
                        return;
                    default:
                        System.out.println("💡 Please choose 1, 2, 3, or 4");
                }
                
            } catch (CalculatorException e) {
                System.out.println("⚠️  " + e.getMessage());
                System.out.println("💡 Let's try again!");
            } catch (Exception e) {
                System.out.println("🤔 Something unexpected happened, but don't worry!");
                System.out.println("💡 Let's continue with a fresh calculation.");
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("=== CALCULATOR MENU ===");
        System.out.println("1. Basic Calculation");
        System.out.println("2. Memory Operations");
        System.out.println("3. View Memory");
        System.out.println("4. Exit");
        if (calculator.hasMemoryValue()) {
            System.out.println("📝 Memory: " + calculator.formatResult(calculator.getMemoryValue()));
        }
    }
    
    private int getMenuChoice() {
        while (true) {
            System.out.print("Choose option (1-4): ");
            String input = scanner.nextLine().trim();
            
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    return choice;
                }
                System.out.println("💡 Please enter a number between 1 and 4");
            } catch (NumberFormatException e) {
                System.out.println("💡 Please enter a valid number (1, 2, 3, or 4)");
            }
        }
    }
    
    private void performBasicCalculation() throws CalculatorException {
        System.out.println("\n=== BASIC CALCULATION ===");
        
        double firstNumber = getFirstNumber();
        String operation = getOperationInput();
        double secondNumber = getNumberInput("Enter second number: ");
        
        // Handle division by zero
        if (operation.equals("/") && secondNumber == 0.0) {
            displayDivisionByZeroHelp();
            System.out.print("Would you like to try a different second number? (y/n): ");
            String retry = scanner.nextLine().trim().toLowerCase();
            
            if (retry.equals("y") || retry.equals("yes")) {
                secondNumber = getNumberInput("Enter different second number: ");
            } else {
                return;
            }
        }
        
        double result = performCalculation(firstNumber, operation, secondNumber);
        displayResult(firstNumber, operation, secondNumber, result);
        
        // Offer to save result to memory
        System.out.print("Save result to memory? (y/n): ");
        if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
            calculator.memoryStore(result);
            System.out.println("✅ Result saved to memory");
        }
    }
    
    private double getFirstNumber() {
        if (calculator.hasMemoryValue()) {
            System.out.print("Use memory value " + calculator.formatResult(calculator.getMemoryValue()) + "? (y/n): ");
            if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
                System.out.println("Using " + calculator.formatResult(calculator.getMemoryValue()) + " as first number");
                return calculator.memoryRecall();
            }
        }
        return getNumberInput("Enter first number: ");
    }
    
    private void handleMemoryOperations() throws CalculatorException {
        while (true) {
            displayMemoryMenu();
            String choice = getMemoryChoice();
            
            switch (choice.toUpperCase()) {
                case "MS":
                    memoryStore();
                    break;
                case "MR":
                    memoryRecall();
                    break;
                case "MC":
                    memoryClear();
                    break;
                case "M+":
                    memoryAdd();
                    break;
                case "M-":
                    memorySubtract();
                    break;
                case "BACK":
                    return;
                default:
                    System.out.println("💡 Please choose MS, MR, MC, M+, M-, or 'back'");
            }
        }
    }
    
    private void displayMemoryMenu() {
        System.out.println("\n=== MEMORY OPERATIONS ===");
        System.out.println("MS - Memory Store (save a number)");
        System.out.println("MR - Memory Recall (use saved number)");
        System.out.println("MC - Memory Clear (clear memory)");
        System.out.println("M+ - Memory Add (add to memory)");
        System.out.println("M- - Memory Subtract (subtract from memory)");
        System.out.println("📝 Current Memory: " + calculator.formatResult(calculator.getMemoryValue()));
    }
    
    private String getMemoryChoice() {
        System.out.print("Choose memory operation (MS/MR/MC/M+/M-) or 'back': ");
        return scanner.nextLine().trim();
    }
    
    private void memoryStore() throws CalculatorException {
        System.out.println("\n--- Memory Store ---");
        double value = getNumberInput("Enter number to store in memory: ");
        calculator.memoryStore(value);
        System.out.println("✅ Stored " + calculator.formatResult(value) + " in memory");
    }
    
    private void memoryRecall() throws CalculatorException {
        System.out.println("\n--- Memory Recall ---");
        double memoryValue = calculator.memoryRecall();
        System.out.println("📝 Memory contains: " + calculator.formatResult(memoryValue));
        
        if (memoryValue != 0.0) {
            System.out.print("Use this number in calculation? (y/n): ");
            if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
                useMemoryInCalculation(memoryValue);
            }
        } else {
            System.out.println("💡 Memory is empty. Use MS to store a number first.");
        }
    }
    
    private void useMemoryInCalculation(double memoryValue) throws CalculatorException {
        System.out.println("Using " + calculator.formatResult(memoryValue) + " as first number");
        String operation = getOperationInput();
        double secondNumber = getNumberInput("Enter second number: ");
        
        double result = performCalculation(memoryValue, operation, secondNumber);
        displayResult(memoryValue, operation, secondNumber, result);
        
        System.out.print("Store result in memory? (y/n): ");
        if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
            calculator.memoryStore(result);
            System.out.println("✅ Result stored in memory");
        }
    }
    
    private void memoryClear() {
        System.out.println("\n--- Memory Clear ---");
        calculator.memoryClear();
        System.out.println("✅ Memory cleared (set to 0)");
    }
    
    private void memoryAdd() throws CalculatorException {
        System.out.println("\n--- Memory Add ---");
        System.out.println("Current memory: " + calculator.formatResult(calculator.getMemoryValue()));
        double value = getNumberInput("Enter number to add to memory: ");
        calculator.memoryAdd(value);
        System.out.println("✅ Added " + calculator.formatResult(value) + " to memory");
        System.out.println("📝 New memory value: " + calculator.formatResult(calculator.getMemoryValue()));
    }
    
    private void memorySubtract() throws CalculatorException {
        System.out.println("\n--- Memory Subtract ---");
        System.out.println("Current memory: " + calculator.formatResult(calculator.getMemoryValue()));
        double value = getNumberInput("Enter number to subtract from memory: ");
        calculator.memorySubtract(value);
        System.out.println("✅ Subtracted " + calculator.formatResult(value) + " from memory");
        System.out.println("📝 New memory value: " + calculator.formatResult(calculator.getMemoryValue()));
    }
    
    private void viewMemory() {
        System.out.println("\n=== MEMORY STATUS ===");
        double memoryValue = calculator.getMemoryValue();
        System.out.println("📝 Current Memory: " + calculator.formatResult(memoryValue));
        
        if (memoryValue == 0.0) {
            System.out.println("💡 Memory is empty. Use Memory Operations → MS to store a number.");
        } else {
            System.out.println("✅ Memory contains a value. Use MR to recall it in calculations.");
        }
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




