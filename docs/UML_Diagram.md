# UML Class Diagram

## Calculator System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                           Calculator                            │
├─────────────────────────────────────────────────────────────────┤
│ - memory: double                                                │
│ - history: List<String>                                         │
│ + PI: static final double                                       │
│ + E: static final double                                        │
├─────────────────────────────────────────────────────────────────┤
│ + add(double a, double b): double                               │
│ + subtract(double a, double b): double                          │
│ + multiply(double a, double b): double                          │
│ + divide(double a, double b): double                            │
│ + power(double base, double exponent): double                   │
│ + squareRoot(double number): double                             │
│ + cubeRoot(double number): double                               │
│ + memoryStore(double value): void                               │
│ + memoryRecall(): double                                        │
│ + memoryClear(): void                                           │
│ + getHistory(): List<String>                                    │
│ + formatResult(double result): String                           │
│ - validateInput(double value, String parameterName): void       │
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    │ composition
                    ┌───────────────┼───────────────┐
                    │               │               │
                    ▼               ▼               ▼
    ┌─────────────────────┐ ┌─────────────────┐ ┌─────────────────┐
    │   CalculatorGUI     │ │  CalculatorCLI  │ │   HelpTopic     │
    ├─────────────────────┤ ├─────────────────┤ ├─────────────────┤
    │ - display: JTextField│ │ - calculator:   │ │ <<utility>>     │
    │ - operationLabel:   │ │   Calculator    │ │                 │
    │   JLabel            │ │ - scanner:      │ │                 │
    │ - memoryLabel:      │ │   Scanner       │ │                 │
    │   JLabel            │ ├─────────────────┤ ├─────────────────┤
    │ - calculator:       │ │ + run(): void   │ │ + getKeyboard   │
    │   Calculator        │ │ - performBasic  │ │   Shortcuts():  │
    │ - firstNumber:      │ │   Calculation() │ │   String        │
    │   double            │ │   : void        │ │ + getOperation  │
    │ - operation: String │ │ + main(String[] │ │   Guide():      │
    │ - isNewCalculation: │ │   args): void   │ │   String        │
    │   boolean           │ └─────────────────┘ └─────────────────┘
    ├─────────────────────┤           │                   ▲
    │ + actionPerformed   │           │                   │
    │   (ActionEvent): void│          │                   │ uses
    │ + keyPressed        │           │                   │
    │   (KeyEvent): void  │           │                   │
    │ - handleNumber      │           │         ┌─────────┘
    │   (String): void    │           │         │
    │ - handleOperation   │           │         │
    │   (String): void    │           │         │
    │ - handleEquals():   │           │         │
    │   void              │           │         │
    │ - updateMemory      │           │         │
    │   Display(): void   │           │         │
    └─────────────────────┘           │         │
              │                       │         │
              │ uses                  │         │
              │                       │         │
              ▼                       ▼         │
    ┌─────────────────────┐           │         │
    │  ActionListener     │           │         │
    │  KeyListener        │           │         │
    │  <<interface>>      │           │         │
    └─────────────────────┘           │         │
                                      │         │
                                      ▼         │
                              ┌─────────────────┴─────┐
                              │                       │
                              ▼                       ▼

┌─────────────────────────────────────────────────────────────────┐
│                    Exception Hierarchy                         │
└─────────────────────────────────────────────────────────────────┘

                    ┌─────────────────────────┐
                    │  CalculatorException    │
                    │     <<abstract>>        │
                    ├─────────────────────────┤
                    │ + CalculatorException   │
                    │   (String message)      │
                    └─────────────────────────┘
                                │
                                │ inheritance
                    ┌───────────┼───────────┐
                    │           │           │
                    ▼           ▼           ▼
        ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
        │ DivisionByZero  │ │ InvalidInput    │ │   (Future       │
        │   Exception     │ │   Exception     │ │  Extensions)    │
        ├─────────────────┤ ├─────────────────┤ ├─────────────────┤
        │ + DivisionBy    │ │ + InvalidInput  │ │ + Overflow      │
        │   ZeroException │ │   Exception     │ │   Exception     │
        │   (String       │ │   (String       │ │ + Underflow     │
        │   message)      │ │   message)      │ │   Exception     │
        └─────────────────┘ └─────────────────┘ └─────────────────┘

RELATIONSHIP LEGEND:
═══════════════════
│ Composition    : Strong "has-a" relationship (filled diamond)
│ Inheritance    : "is-a" relationship (hollow triangle)  
│ Dependency     : "uses" relationship (dashed arrow)
│ Implementation : Interface implementation (dashed line with triangle)
```

## Class Relationships

### Composition Relationships
- **CalculatorGUI** ◆→ **Calculator**: GUI contains Calculator instance
- **CalculatorCLI** ◆→ **Calculator**: CLI contains Calculator instance
- Both GUI and CLI cannot exist without Calculator (strong ownership)

### Inheritance Hierarchy
```
Exception
└── CalculatorException (Abstract)
    ├── DivisionByZeroException
    └── InvalidInputException
```
- Demonstrates **polymorphism** in exception handling
- Follows **Liskov Substitution Principle**

### Interface Implementation
- **CalculatorGUI** implements **ActionListener** and **KeyListener**
- Demonstrates **Interface Segregation Principle**

### Dependency Relationships
- **Calculator** depends on **CalculatorException** subtypes (throws)
- **CalculatorGUI** depends on **HelpTopic** for help system (uses)

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
