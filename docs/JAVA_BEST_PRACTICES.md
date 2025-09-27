# Java Best Practices Implementation

## Overview

This document demonstrates how the Calculator project implements Java best practices, OOP principles, and modern Java development standards. Each practice is explained with concrete examples from the codebase.

## Object-Oriented Programming Principles

### 1. Encapsulation

**Implementation in Calculator Class:**
```java
public class Calculator {
    private double memory = 0.0;           // Private field
    private List<String> history = new ArrayList<>();  // Private field
    
    // Public methods provide controlled access
    public void memoryStore(double value) throws InvalidInputException {
        validateInput(value, "Memory value");
        this.memory = value;
        logCalculation("Memory store: " + formatResult(value));
    }
    
    public double getMemoryValue() {       // Getter method
        return memory;
    }
    
    private void validateInput(double value, String parameterName) {  // Private helper
        if (Double.isNaN(value)) {
            throw new InvalidInputException(parameterName + " cannot be NaN");
        }
    }
}
```

**Benefits Demonstrated:**
- Data hiding through private fields
- Controlled access via public methods
- Internal validation logic hidden from clients
- State consistency maintained

### 2. Inheritance

**Exception Hierarchy Implementation:**
```java
// Base abstract class
public abstract class CalculatorException extends Exception {
    public CalculatorException(String message) {
        super(message);
    }
}

// Specialized exceptions
public class DivisionByZeroException extends CalculatorException {
    public DivisionByZeroException(String message) {
        super(message);
    }
}

public class InvalidInputException extends CalculatorException {
    public InvalidInputException(String message) {
        super(message);
    }
}
```

**Benefits Demonstrated:**
- Code reuse through inheritance
- Polymorphic exception handling
- Specialized behavior in subclasses
- Consistent interface across exception types

### 3. Polymorphism

**Interface Implementation in GUI:**
```java
public class CalculatorGUI extends JFrame implements ActionListener, KeyListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks polymorphically
        String command = e.getActionCommand();
        // Process different button types uniformly
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // Handle keyboard input polymorphically
        char keyChar = e.getKeyChar();
        // Process different key types uniformly
    }
}
```

**Exception Handling Polymorphism:**
```java
try {
    double result = calculator.divide(a, b);
} catch (CalculatorException ex) {  // Polymorphic catch
    // Handles both DivisionByZeroException and InvalidInputException
    showError(ex.getMessage());
}
```

### 4. Abstraction

**Abstract Exception Class:**
```java
public abstract class CalculatorException extends Exception {
    // Provides common structure for all calculator exceptions
    // Forces subclasses to implement specific error types
}
```

**Interface Abstraction:**
```java
// GUI abstracts complex event handling
private void setupKeyboardShortcuts() {
    // Complex keyboard mapping abstracted into simple method calls
    inputMap.put(KeyStroke.getKeyStroke("ctrl M"), "memoryStore");
    actionMap.put("memoryStore", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            // Abstract action handling
        }
    });
}
```

## SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)

**Calculator Class - Mathematical Operations:**
```java
public class Calculator {
    // ONLY responsible for mathematical calculations and memory management
    public double add(double a, double b) { /* math logic */ }
    public double subtract(double a, double b) { /* math logic */ }
    public void memoryStore(double value) { /* memory logic */ }
    // No UI logic, no file I/O, no network operations
}
```

**CalculatorGUI Class - User Interface:**
```java
public class CalculatorGUI extends JFrame {
    // ONLY responsible for user interface and event handling
    private void initializeGUI() { /* UI setup */ }
    private void handleButtonClick() { /* event handling */ }
    // No mathematical calculations, delegates to Calculator
}
```

### 2. Open/Closed Principle (OCP)

**Extensible Operations:**
```java
public class Calculator {
    // Easy to add new operations without modifying existing code
    public double power(double base, double exponent) { /* new operation */ }
    public double cubeRoot(double number) { /* new operation */ }
    public double reciprocal(double number) { /* new operation */ }
    
    // Existing operations remain unchanged
    public double add(double a, double b) { /* unchanged */ }
}
```

**Extensible Exception Handling:**
```java
// New exception types can be added without modifying existing code
public class OverflowException extends CalculatorException {
    public OverflowException(String message) {
        super(message);
    }
}
```

### 3. Liskov Substitution Principle (LSP)

**Exception Substitutability:**
```java
public void handleCalculation() {
    try {
        // Any CalculatorException subtype can be used here
        calculator.performOperation();
    } catch (CalculatorException ex) {
        // Works correctly with DivisionByZeroException, InvalidInputException, etc.
        displayError(ex.getMessage());
    }
}
```

### 4. Interface Segregation Principle (ISP)

**Focused Interface Implementation:**
```java
public class CalculatorGUI implements ActionListener, KeyListener {
    // Only implements interfaces it actually uses
    // Doesn't implement unnecessary interfaces like MouseListener
    
    @Override
    public void actionPerformed(ActionEvent e) { /* used */ }
    
    @Override
    public void keyPressed(KeyEvent e) { /* used */ }
    
    // Unused KeyListener methods are empty but required
    @Override
    public void keyReleased(KeyEvent e) { /* not used */ }
    
    @Override
    public void keyTyped(KeyEvent e) { /* not used */ }
}
```

### 5. Dependency Inversion Principle (DIP)

**High-level modules depend on abstractions:**
```java
public class CalculatorGUI {
    private Calculator calculator;  // Depends on Calculator abstraction
    
    public CalculatorGUI() {
        calculator = new Calculator();  // Could be injected for better DIP
    }
    
    // GUI doesn't depend on specific Calculator implementation details
    private void handleEquals() {
        try {
            double result = calculator.add(firstNumber, secondNumber);
            // Uses Calculator interface, not implementation
        } catch (CalculatorException ex) {
            // Depends on exception abstraction
        }
    }
}
```

## Java Coding Standards

### 1. Naming Conventions

**Class Names - PascalCase:**
```java
public class Calculator { }
public class CalculatorGUI { }
public class DivisionByZeroException { }
```

**Method Names - camelCase:**
```java
public double squareRoot(double number) { }
public void memoryStore(double value) { }
public String formatResult(double result) { }
```

**Variable Names - camelCase:**
```java
private double firstNumber = 0;
private boolean isNewCalculation = true;
private JLabel operationLabel;
```

**Constants - UPPER_SNAKE_CASE:**
```java
public static final double PI = Math.PI;
public static final double E = Math.E;
private static final double DELTA = 0.0001;  // In tests
```

### 2. Method Design Best Practices

**Single Purpose Methods:**
```java
// Each method has one clear responsibility
private void handleNumber(String number) {
    // Only handles number input
}

private void handleOperation(String op) {
    // Only handles operation selection
}

private void updateMemoryDisplay() {
    // Only updates memory display
}
```

**Proper Parameter Validation:**
```java
public double divide(double a, double b) throws DivisionByZeroException, InvalidInputException {
    validateInput(a, "Dividend");      // Validate first parameter
    validateInput(b, "Divisor");       // Validate second parameter
    
    if (b == 0.0) {                    // Business rule validation
        throw new DivisionByZeroException("Cannot divide " + a + " by zero");
    }
    
    // Perform operation only after validation
    double result = a / b;
    logCalculation(a + " / " + b + " = " + formatResult(result));
    return result;
}
```

### 3. Exception Handling Best Practices

**Specific Exception Types:**
```java
// Don't use generic Exception
public double squareRoot(double number) throws InvalidInputException {  // Specific
    if (number < 0) {
        throw new InvalidInputException("Square root of negative number: " + number);
    }
    // ...
}
```

**Meaningful Error Messages:**
```java
public long factorial(int number) throws InvalidInputException {
    if (number < 0) {
        throw new InvalidInputException("Factorial is not defined for negative numbers: " + number);
    }
    
    if (number > 20) {
        throw new InvalidInputException("Factorial calculation would overflow for number: " + number);
    }
    // ...
}
```

**Proper Exception Propagation:**
```java
private void handleSquareRoot() {
    try {
        double current = Double.parseDouble(display.getText());
        double result = calculator.squareRoot(current);  // May throw
        display.setText(calculator.formatResult(result));
        isNewCalculation = true;
    } catch (CalculatorException ex) {
        showError(ex.getMessage());  // Handle at appropriate level
    }
}
```

### 4. Resource Management

**Proper Scanner Usage:**
```java
public class CalculatorCLI {
    private Scanner scanner;
    
    public CalculatorCLI() {
        scanner = new Scanner(System.in);  // Initialize once
    }
    
    // Scanner closed when application exits
    // For more complex scenarios, use try-with-resources
}
```

**GUI Resource Management:**
```java
public CalculatorGUI() {
    calculator = new Calculator();
    initializeGUI();
    setupKeyboardShortcuts();
    // Swing handles component cleanup automatically
}
```

### 5. Documentation Standards

**Class-level Documentation:**
```java
/**
 * A comprehensive calculator class providing mathematical operations,
 * memory functionality, and calculation history tracking.
 * 
 * This class follows the Single Responsibility Principle by focusing
 * solely on mathematical calculations and data management.
 */
public class Calculator {
    // Implementation
}
```

**Method-level Documentation:**
```java
/**
 * Calculates the factorial of a given integer.
 * 
 * @param number the integer to calculate factorial for (must be >= 0 and <= 20)
 * @return the factorial of the number
 * @throws InvalidInputException if number is negative or would cause overflow
 */
public long factorial(int number) throws InvalidInputException {
    // Implementation
}
```

## Modern Java Features Usage

### 1. Enhanced Switch Expressions (Java 14+)
```java
private void handleOperation(String op) {
    // Modern switch expression
    String operationSymbol = switch (op) {
        case "+" -> "+";
        case "-" -> "-";
        case "×" -> "×";
        case "÷" -> "÷";
        case "power" -> "^";
        default -> op;
    };
    operationLabel.setText(calculator.formatResult(firstNumber) + " " + operationSymbol);
}
```

### 2. Text Blocks (Java 15+)
```java
private void showHelp() {
    String helpText = """
        🧮 CALCULATOR HELP & KEYBOARD SHORTCUTS
        
        ⌨️ KEYBOARD SHORTCUTS:
        
        📱 NUMBER INPUT:
        • 0-9 keys - Type numbers directly
        • . key - Decimal point
        """;
    // Display help text
}
```

### 3. Type Inference with var (Java 10+)
```java
// Used judiciously where type is obvious
var history = calculator.getHistory();  // Type is clearly List<String>
var helpDialog = new JOptionPane();     // Type is obvious from constructor
```

### 4. Collection Factory Methods (Java 9+)
```java
// Modern collection initialization (when applicable)
private static final List<String> OPERATION_SYMBOLS = List.of("+", "-", "×", "÷");
```

## Testing Best Practices

### 1. Comprehensive Test Coverage
```java
@DisplayName("Calculator Tests")
class CalculatorTest {
    
    @ParameterizedTest
    @CsvSource({
        "10.0, 3.0, 13.0",
        "0.0, 5.0, 5.0",
        "-3.0, -2.0, -5.0"
    })
    void testAdd(double a, double b, double expected) throws CalculatorException {
        assertEquals(expected, calculator.add(a, b), DELTA);
    }
}
```

### 2. Descriptive Test Names
```java
@Test
@DisplayName("User can save and recall calculation results")
void userCanSaveAndRecallResults() throws CalculatorException {
    // Test implementation
}

@Test
void testDivideByZero() {
    assertThrows(DivisionByZeroException.class, () -> calculator.divide(10.0, 0.0));
}
```

## Performance Considerations

### 1. Efficient String Operations
```java
public String formatResult(double result) {
    // Avoid unnecessary string concatenation
    if (Double.isNaN(result)) {
        return "NaN";
    } else if (Double.isInfinite(result)) {
        return result > 0 ? "∞" : "-∞";
    } else if (isInteger(result)) {
        return String.valueOf((long) result);  // Efficient conversion
    } else {
        return String.valueOf(result);
    }
}
```

### 2. Memory Management
```java
private List<String> history = new ArrayList<>();

private void logCalculation(String calculation) {
    history.add(calculation);
    
    // Optional: Prevent memory leaks in long-running applications
    if (history.size() > MAX_HISTORY_SIZE) {
        history.remove(0);  // Remove oldest entry
    }
}
```

## Code Quality Metrics

### Maintainability Features
- **Low Coupling**: Classes have minimal dependencies
- **High Cohesion**: Each class has focused responsibility
- **Clear Interfaces**: Public methods have clear contracts
- **Consistent Style**: Uniform naming and formatting

### Reliability Features
- **Input Validation**: All inputs validated before processing
- **Exception Safety**: Proper exception handling throughout
- **State Consistency**: Object state always valid
- **Error Recovery**: Application continues after errors

### Extensibility Features
- **Open for Extension**: Easy to add new operations
- **Closed for Modification**: Existing code unchanged when extending
- **Pluggable Architecture**: Components can be replaced
- **Clean Abstractions**: Well-defined interfaces

This comprehensive implementation of Java best practices ensures the Calculator project demonstrates professional-grade software development standards.