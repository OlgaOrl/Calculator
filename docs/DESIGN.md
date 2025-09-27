# System Design & Architecture

## Overview

The Calculator application demonstrates professional software architecture using established design patterns, SOLID principles, and clean code practices. This document outlines the architectural decisions and design patterns implemented.

## Architectural Pattern: Model-View-Controller (MVC)

### Model Layer
**Class**: `Calculator`
- **Responsibility**: Business logic and data management
- **Components**:
  - Mathematical operations (add, subtract, multiply, divide, power, roots)
  - Memory management (store, recall, clear, add, subtract)
  - Calculation history tracking
  - Input validation and error handling
  - Result formatting

### View Layer
**Classes**: `CalculatorGUI`, `CalculatorCLI`
- **Responsibility**: User interface presentation
- **CalculatorGUI Features**:
  - Swing-based graphical interface
  - Real-time operation display
  - Memory status indicators
  - Keyboard and mouse input support
  - Visual feedback and status messages
- **CalculatorCLI Features**:
  - Simple command-line interface
  - Menu-driven interaction
  - Basic calculation support

### Controller Layer
**Implementation**: Event handlers in GUI and CLI
- **GUI Controllers**:
  - `ActionListener` for button clicks
  - `KeyListener` for keyboard input
  - Event routing to appropriate Calculator methods
- **CLI Controllers**:
  - Menu selection handling
  - Input parsing and validation
  - Operation dispatch

## Design Patterns Implementation

### 1. Strategy Pattern
**Implementation**: Mathematical operations in Calculator class

```java
// Each operation encapsulated as a method
public double add(double a, double b) throws InvalidInputException
public double subtract(double a, double b) throws InvalidInputException
public double multiply(double a, double b) throws InvalidInputException
public double divide(double a, double b) throws DivisionByZeroException
```

**Benefits**:
- Easy to add new operations
- Operations are interchangeable
- Clean separation of algorithms

### 2. Observer Pattern
**Implementation**: GUI event handling system

```java
// GUI observes user actions
public void actionPerformed(ActionEvent e) // Button clicks
public void keyPressed(KeyEvent e)         // Keyboard input
```

**Benefits**:
- Loose coupling between UI and logic
- Multiple input methods supported
- Event-driven architecture

### 3. Template Method Pattern
**Implementation**: Exception handling hierarchy

```java
abstract class CalculatorException extends Exception {
    public CalculatorException(String message) {
        super(message);
    }
}
```

**Benefits**:
- Consistent exception handling
- Specialized error messages
- Polymorphic exception processing

### 4. Facade Pattern
**Implementation**: Calculator class as facade

```java
public class Calculator {
    // Provides simple interface to complex mathematical operations
    // Hides internal complexity of validation, logging, formatting
}
```

**Benefits**:
- Simplified interface for clients
- Internal complexity hidden
- Easy to use and maintain

## SOLID Principles Application

### Single Responsibility Principle (SRP)
Each class has one reason to change:
- **Calculator**: Mathematical operations change
- **CalculatorGUI**: UI requirements change
- **CalculatorCLI**: CLI interface changes
- **Exceptions**: Error handling requirements change

### Open/Closed Principle (OCP)
System is open for extension, closed for modification:
- New operations can be added to Calculator without changing existing code
- New exception types can be added without modifying base exception
- New UI components can be added without changing core logic

### Liskov Substitution Principle (LSP)
Subtypes are substitutable for base types:
- All CalculatorException subtypes can replace base exception
- Polymorphic exception handling works correctly

### Interface Segregation Principle (ISP)
Clients depend only on interfaces they use:
- GUI implements only ActionListener and KeyListener
- No forced dependencies on unused interfaces

### Dependency Inversion Principle (DIP)
High-level modules don't depend on low-level modules:
- GUI depends on Calculator abstraction, not implementation details
- Exception handling uses abstraction, not concrete types

## Error Handling Strategy

### Exception Hierarchy
```
Exception
└── CalculatorException (abstract)
    ├── DivisionByZeroException
    └── InvalidInputException
```

### Error Handling Approach
1. **Input Validation**: All inputs validated before processing
2. **Specific Exceptions**: Different exception types for different errors
3. **User-Friendly Messages**: Clear error messages for users
4. **Graceful Recovery**: Application continues after errors

### Example Implementation
```java
public double divide(double a, double b) throws DivisionByZeroException, InvalidInputException {
    validateInput(a, "Dividend");
    validateInput(b, "Divisor");
    
    if (b == 0.0) {
        throw new DivisionByZeroException("Cannot divide " + a + " by zero");
    }
    
    double result = a / b;
    logCalculation(a + " / " + b + " = " + formatResult(result));
    return result;
}
```

## Memory Management Design

### Memory Operations
- **Store (MS)**: Save current value
- **Recall (MR)**: Retrieve stored value
- **Clear (MC)**: Reset memory to zero
- **Add (M+)**: Add to memory value
- **Subtract (M-)**: Subtract from memory value

### Implementation Features
- Thread-safe memory operations
- Memory state persistence during calculations
- Visual memory indicators in GUI
- History logging of memory operations

## Testing Strategy

### Unit Testing Approach
- **Comprehensive Coverage**: All public methods tested
- **Parameterized Tests**: Multiple input scenarios
- **Exception Testing**: Error conditions verified
- **Edge Cases**: Boundary conditions tested

### Test Categories
1. **Basic Operations**: Add, subtract, multiply, divide
2. **Advanced Functions**: Power, roots, factorial
3. **Memory Operations**: Store, recall, clear, add, subtract
4. **Error Handling**: Invalid inputs, division by zero
5. **History Tracking**: Calculation logging and retrieval

## Performance Considerations

### Optimization Strategies
1. **BigDecimal Usage**: High precision calculations when needed
2. **Efficient String Formatting**: Optimized result display
3. **Memory Management**: Controlled history size
4. **Event Handling**: Efficient GUI event processing

### Scalability Features
- Modular design allows easy feature addition
- Separation of concerns enables independent scaling
- Clean interfaces support multiple UI implementations

## Security Considerations

### Input Validation
- All numeric inputs validated for NaN and infinity
- String inputs sanitized and validated
- Exception handling prevents application crashes

### Error Information
- Error messages don't expose internal implementation
- User-friendly error descriptions
- Secure exception handling

## Future Enhancement Opportunities

### Potential Extensions
1. **Scientific Functions**: Trigonometric, logarithmic operations
2. **Unit Conversions**: Length, weight, temperature conversions
3. **Graphing Capabilities**: Function plotting and visualization
4. **Expression Parsing**: Complex mathematical expression evaluation
5. **Themes and Customization**: User interface personalization

### Architecture Support
The current architecture supports these enhancements through:
- Open/Closed Principle compliance
- Modular design structure
- Clean separation of concerns
- Extensible exception handling