# UML Class Diagram - Calculator System

## System Architecture Overview

This document presents the UML class diagram for the Calculator application, demonstrating object-oriented design principles and class relationships.

## Class Diagram

```mermaid
classDiagram
    class Calculator {
        -double memory
        -List~String~ history
        +static final double PI
        +static final double E
        
        +double add(double a, double b)
        +double subtract(double a, double b)
        +double multiply(double a, double b)
        +double divide(double a, double b)
        +double power(double base, double exponent)
        +double squareRoot(double number)
        +double cubeRoot(double number)
        +long factorial(int number)
        +double reciprocal(double number)
        
        +void memoryStore(double value)
        +double memoryRecall()
        +void memoryClear()
        +void memoryAdd(double value)
        +void memorySubtract(double value)
        +boolean hasMemoryValue()
        
        +List~String~ getHistory()
        +void clearHistory()
        +String getLastCalculation()
        +String formatResult(double result)
        -void validateInput(double value, String parameterName)
        -void logCalculation(String calculation)
    }

    class CalculatorGUI {
        -JTextField display
        -JLabel operationLabel
        -JLabel memoryLabel
        -JLabel statusLabel
        -Calculator calculator
        -double firstNumber
        -String operation
        -boolean isNewCalculation
        
        +void actionPerformed(ActionEvent e)
        +void keyPressed(KeyEvent e)
        +void keyReleased(KeyEvent e)
        +void keyTyped(KeyEvent e)
        
        -void initializeGUI()
        -void setupKeyboardShortcuts()
        -JPanel createDisplayPanel()
        -JPanel createButtonPanel()
        -JButton createButton(String text)
        -void handleNumber(String number)
        -void handleOperation(String op)
        -void handleEquals()
        -void handleClear()
        -void handleMemoryOperation(String command)
        -void updateMemoryDisplay()
        -void showHelp()
        -void showHistory()
        -void updateStatusLabel(String message)
    }

    class CalculatorCLI {
        -Calculator calculator
        -Scanner scanner
        
        +void run()
        -void performBasicCalculation()
        +static void main(String[] args)
    }

    class CalculatorException {
        <<abstract>>
        +CalculatorException(String message)
    }

    class DivisionByZeroException {
        +DivisionByZeroException(String message)
    }

    class InvalidInputException {
        +InvalidInputException(String message)
    }

    class HelpTopic {
        +static String getKeyboardShortcuts()
        +static String getOperationGuide()
        +static String getMemoryHelp()
    }

    %% Relationships
    Calculator ||--o{ CalculatorGUI : composition
    Calculator ||--o{ CalculatorCLI : composition
    CalculatorException <|-- DivisionByZeroException : inheritance
    CalculatorException <|-- InvalidInputException : inheritance
    Calculator ..> CalculatorException : throws
    CalculatorGUI ..> HelpTopic : dependency
    CalculatorGUI ..|> ActionListener : implements
    CalculatorGUI ..|> KeyListener : implements
```

## Class Relationships Analysis

### 1. Composition Relationships
- **CalculatorGUI** ◆→ **Calculator**: Strong "has-a" relationship
- **CalculatorCLI** ◆→ **Calculator**: Strong "has-a" relationship
- Both GUI and CLI cannot exist without Calculator instance

### 2. Inheritance Hierarchy
```
CalculatorException (Abstract)
├── DivisionByZeroException
└── InvalidInputException
```
- Demonstrates **polymorphism** in exception handling
- Follows **Liskov Substitution Principle**

### 3. Interface Implementation
- **CalculatorGUI** implements **ActionListener** and **KeyListener**
- Demonstrates **Interface Segregation Principle**

### 4. Dependency Relationships
- **Calculator** depends on **CalculatorException** subtypes
- **CalculatorGUI** depends on **HelpTopic** for help system

## Design Principles Demonstrated

### SOLID Principles

1. **Single Responsibility Principle (SRP)**
   - `Calculator`: Mathematical operations and memory management
   - `CalculatorGUI`: User interface and event handling
   - `CalculatorCLI`: Command-line interface
   - Each exception class: Specific error type

2. **Open/Closed Principle (OCP)**
   - Easy to add new operations without modifying existing code
   - Exception hierarchy allows new exception types

3. **Liskov Substitution Principle (LSP)**
   - All exception subtypes can replace `CalculatorException`
   - Polymorphic exception handling

4. **Interface Segregation Principle (ISP)**
   - Separate interfaces for different event types
   - GUI implements only needed listener interfaces

5. **Dependency Inversion Principle (DIP)**
   - GUI depends on Calculator abstraction
   - High-level modules don't depend on low-level details

## Object-Oriented Features

### Encapsulation
- Private fields with public methods
- Data hiding in Calculator class
- Controlled access to memory and history

### Inheritance
- Exception hierarchy with specialized error types
- Code reuse through inheritance

### Polymorphism
- Method overloading in Calculator operations
- Interface implementation in GUI
- Exception handling polymorphism

### Abstraction
- Abstract CalculatorException class
- Interface-based event handling
- Clean separation of concerns