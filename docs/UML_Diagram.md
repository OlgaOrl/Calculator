# UML Class Diagram

## Calculator System Architecture

```mermaid
classDiagram
    class Calculator {
        -double memory
        -List~String~ history
        +double add(double a, double b)
        +double subtract(double a, double b)
        +double multiply(double a, double b)
        +double divide(double a, double b)
        +double power(double base, double exponent)
        +double squareRoot(double number)
        +double cubeRoot(double number)
        +void memoryStore(double value)
        +double memoryRecall()
        +void memoryClear()
        +List~String~ getHistory()
        +String formatResult(double result)
    }

    class CalculatorGUI {
        -JTextField display
        -JLabel operationLabel
        -JLabel memoryLabel
        -Calculator calculator
        -double firstNumber
        -String operation
        -boolean isNewCalculation
        +void actionPerformed(ActionEvent e)
        +void keyPressed(KeyEvent e)
        -void handleNumber(String number)
        -void handleOperation(String op)
        -void handleEquals()
        -void updateMemoryDisplay()
    }

    class CalculatorCLI {
        -Calculator calculator
        -Scanner scanner
        +void run()
        -void performBasicCalculation()
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
    }

    %% Relationships
    Calculator *-- CalculatorGUI : composition
    Calculator *-- CalculatorCLI : composition
    CalculatorException <|-- DivisionByZeroException : inheritance
    CalculatorException <|-- InvalidInputException : inheritance
    Calculator ..> CalculatorException : throws
    CalculatorGUI ..> HelpTopic : uses
```

## Class Relationships

### Composition
- **CalculatorGUI** and **CalculatorCLI** both contain a **Calculator** instance
- Strong "has-a" relationship where GUI/CLI cannot exist without Calculator

### Inheritance
- **DivisionByZeroException** and **InvalidInputException** extend **CalculatorException**
- Demonstrates polymorphism in exception handling

### Dependency
- **Calculator** throws **CalculatorException** subtypes
- **CalculatorGUI** uses **HelpTopic** for help system

## Design Patterns Used

1. **Model-View-Controller (MVC)**
   - Model: `Calculator` (business logic)
   - View: `CalculatorGUI`, `CalculatorCLI` (presentation)
   - Controller: Event handlers in GUI/CLI

2. **Strategy Pattern**
   - Different operation strategies (add, subtract, multiply, divide)
   - Encapsulated in Calculator methods

3. **Observer Pattern**
   - GUI components observe user input events
   - ActionListener and KeyListener implementations