# Calculator 🧮

A simple and powerful calculator application with beautiful graphical interface and command-line option.

## 📚 Documentation

### Project Documentation
- 📋 [UML Class Diagram](docs/UML_Diagram.md) - System architecture and class relationships
- 🏗️ [Design Patterns & Architecture](docs/DESIGN.md) - SOLID principles and design patterns
- 🔢 [Algorithm Documentation](docs/ALGORITHM.md) - Pseudocode and complexity analysis
- ☕ [Java Best Practices](docs/JAVA_BEST_PRACTICES.md) - OOP principles and code quality

### Development Documentation
- 🚀 [Development Workflow](DEVELOPMENT.md) - Git workflow and development practices

## 🎯 Project Planning

### Development Methodology
This project follows **Agile Development** principles with emphasis on:

- **Iterative Development**: Features developed in small, testable increments
- **Test-Driven Development (TDD)**: Comprehensive unit tests for all functionality
- **Clean Code Principles**: Following Robert Martin's clean code guidelines
- **SOLID Principles**: Ensuring maintainable and extensible architecture

### Project Phases

#### Phase 1: Core Foundation ✅
- [x] Basic arithmetic operations (add, subtract, multiply, divide)
- [x] Exception handling framework
- [x] Unit testing infrastructure
- [x] Maven build configuration

#### Phase 2: Advanced Features ✅
- [x] Advanced mathematical functions (power, square root, factorial)
- [x] Memory operations (store, recall, clear, add, subtract)
- [x] Calculation history tracking
- [x] Input validation and error handling

#### Phase 3: User Interfaces ✅
- [x] Command-line interface (CLI)
- [x] Graphical user interface (GUI)
- [x] Keyboard shortcuts and accessibility
- [x] Visual feedback and status indicators

#### Phase 4: Polish & Documentation ✅
- [x] Comprehensive documentation
- [x] Code quality improvements
- [x] Performance optimizations
- [x] Professional project structure

### Architecture Decisions

#### Design Pattern Selection
- **MVC Pattern**: Separates business logic from presentation
- **Strategy Pattern**: Encapsulates mathematical operations
- **Observer Pattern**: Handles user interface events
- **Exception Hierarchy**: Provides specific error handling

#### Technology Stack
- **Java 22**: Latest LTS features and performance improvements
- **Swing**: Mature GUI framework with extensive customization
- **JUnit 5**: Modern testing framework with parameterized tests
- **Maven**: Dependency management and build automation

## Quick Start 🚀

### What You Need
- Java 22 or higher installed on your computer

### How to Run

#### GUI Version (Recommended)
```bash
mvn clean compile
mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorGUI"
```

#### CLI Version
```bash
mvn clean compile
mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorCLI"
```

#### Default Version
```bash
mvn clean compile exec:java
```

## How to Use 💡

### Calculator Window (GUI)
- **Numbers**: Click 0-9 buttons or use your keyboard
- **Operations**: Click +, -, ×, ÷ for calculations
- **Operation Display**: See current operation with symbols (78 +, 45 ×, etc.) above the main display
- **Clear**: Press C to start over
- **Sign**: Press ± to make numbers positive/negative  
- **Percent**: Press % for percentage calculations
- **Decimal**: Press . for decimal numbers
- **Advanced**: √ (square root), ∛ (cube root), x² (square), x^y (power), n√ (nth root), 1/x (reciprocal)
- **Help**: Click Help button for detailed instructions
- **History**: View and clear calculation history
- **Memory Indicator**: Shows current memory value at top right

### Visual Interface Features
- **Dark Theme**: Modern dark interface with color-coded buttons
- **Live Operation Display**: Shows "78 +" or "45 ×" while you're calculating
- **Memory Status**: "Memory: 0" changes to cyan when memory contains a value
- **Status Bar**: Shows keyboard shortcuts and operation confirmations
- **Color-Coded Buttons**:
  - Numbers (0-9): Dark gray
  - Operations (+, -, ×, ÷): Orange
  - Memory (MC, MR, MS, M+, M-): Blue
  - Functions (√, x², %): Purple
  - Other controls: Light gray

### Command Line Version
Simple menu-driven interface for basic calculations:
```
=== CALCULATOR MENU ===
1. Basic Calculation
2. Exit
Choose option:
```

## Features ✨

- **Dual Interface**: Both beautiful GUI and simple CLI
- **Basic Math**: Add, subtract, multiply, divide
- **Live Operation Display**: See what operation you're performing in real-time
- **Advanced Functions**: Powers, square roots, cube roots, nth roots, reciprocals
- **Memory Functions**: Store, recall, clear, add to/subtract from memory
- **History**: See your previous calculations (GUI only)
- **Error Protection**: Won't crash on invalid input
- **Help System**: Built-in help with detailed instructions
- **Visual Design**: Modern dark theme with color-coded buttons
- **Real-time Feedback**: Operation display shows current calculation state

### GUI Button Layout
```
┌─────┬─────┬─────┬─────┬─────┐
│ MC  │ MR  │ MS  │ M+  │ M-  │ Memory Functions (Blue)
├─────┼─────┼─────┼─────┼─────┤
│  C  │ CE  │  √  │ x²  │ 1/x │ Clear & Advanced (Purple)
├─────┼─────┼─────┼─────┼─────┤
│  ∛  │ x^y │ n√  │  %  │  ±  │ Advanced Functions (Purple)
├─────┼─────┼─────┼─────┼─────┤
│  7  │  8  │  9  │  ÷  │Help │ Numbers & Operations
├─────┼─────┼─────┼─────┼─────┤
│  4  │  5  │  6  │  ×  │Cl H │ Numbers & Operations
├─────┼─────┼─────┼─────┼─────┤
│  1  │  2  │  3  │  -  │     │ Numbers & Operations
├─────┼─────┼─────┼─────┼─────┤
│  0  │  .  │  =  │  +  │     │ Numbers & Operations (Orange)
└─────┴─────┴─────┴─────┴─────┘
```

### Display Layout
```
┌─────────────────────────────────────┐
│                    Memory: 0        │ Memory Indicator
├─────────────────────────────────────┤
│              78 +                   │ Operation Display
├─────────────────────────────────────┤
│                                  78 │ Main Display
└─────────────────────────────────────┘
│ Ready - Use keyboard or mouse       │ Status Bar
└─────────────────────────────────────┘
```

### Memory Operations
- **MS (Memory Store)**: Save a number to memory
- **MR (Memory Recall)**: Retrieve and use number from memory  
- **MC (Memory Clear)**: Clear memory (set to zero)
- **M+ (Memory Add)**: Add a number to current memory value
- **M- (Memory Subtract)**: Subtract a number from current memory value

### Advanced Operations
- **√**: Square root calculation
- **∛**: Cube root calculation  
- **x²**: Square a number
- **x^y**: Raise to any power
- **n√**: Calculate nth root
- **1/x**: Reciprocal (1 divided by number)

### Mathematical Constants
The calculator includes high-precision mathematical constants:

| Constant | Symbol | Value | Usage |
|----------|--------|--------|-------|
| Pi | π | 3.141592653589793 | Circle calculations (area, circumference) |
| Euler's Number | e | 2.718281828459045 | Exponential and logarithmic functions |

## Usage Examples 📝

### GUI Usage
1. Launch the calculator: `mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorGUI"`
2. Click numbers and operations
3. **See operation display**: When you click +, you'll see "78 +" above the main display
4. Press **=** to calculate
5. Use **Help** button for detailed instructions
6. View **History** to see previous calculations
7. Use memory functions (MS, MR, MC, M+, M-) to store values

### Visual Feedback Examples
```
Type: 78 + 22
Display shows:
┌─────────────────┐
│      78 +       │ ← Operation indicator
├─────────────────┤
│              22 │ ← Current input
└─────────────────┘

Press =:
┌─────────────────┐
│                 │ ← Operation cleared
├─────────────────┤
│             100 │ ← Result
└─────────────────┘
```

### CLI Usage
```
Welcome to Calculator CLI!

=== CALCULATOR MENU ===
1. Basic Calculation
2. Exit
Choose option: 1

Enter first number: 15
Enter operation (+, -, *, /): *
Enter second number: 3
Result: 45.0
```

## Testing 🧪

Run comprehensive tests:
```bash
mvn test
```

Tests include:
- ✅ Basic arithmetic operations
- ✅ Advanced mathematical functions
- ✅ Memory operations
- ✅ Error handling
- ✅ Input validation
- ✅ History tracking

## Troubleshooting 🔧

**Calculator won't start?**
- Make sure Java 22+ is installed: `java -version`
- Check that you're in the correct project folder
- Try: `mvn clean compile` first

**GUI won't open?**
- Use quotes around the parameter: `mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorGUI"`
- Or run in two steps:
  ```bash
  mvn clean compile
  java -cp target/classes com.calculator.CalculatorGUI
  ```

**Symbols display as squares?**
- This is normal - the calculator uses standard mathematical symbols
- All functionality works regardless of symbol display

**CLI Issues?**
- Use: `mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorCLI"`
- Or: `java -cp target/classes com.calculator.CalculatorCLI`

**Compilation errors?**
- Run `mvn clean compile` first
- Check for any missing dependencies
- Ensure all source files are present

**PowerShell command issues?**
- Always use quotes around `-Dexec.mainClass` parameter
- Or use two separate commands: compile first, then run

## Project Structure 📁

```
Calculator-1/
├── docs/                            # 📚 Project Documentation
│   ├── UML_Diagram.md              # Class diagrams and relationships
│   ├── DESIGN.md                   # Architecture and design patterns
│   ├── ALGORITHM.md                # Pseudocode and algorithms
│   └── JAVA_BEST_PRACTICES.md      # OOP principles and code quality
├── src/
│   ├── main/java/com/calculator/
│   │   ├── Calculator.java          # Core calculation logic
│   │   ├── CalculatorGUI.java       # Graphical interface with live display
│   │   ├── CalculatorCLI.java       # Command-line interface
│   │   ├── CalculatorException.java # Base exception class
│   │   ├── DivisionByZeroException.java
│   │   ├── InvalidInputException.java
│   │   └── help/
│   │       └── HelpTopic.java       # Help system support
│   └── test/java/com/calculator/
│       └── CalculatorTest.java      # Comprehensive tests
├── pom.xml                          # Maven configuration
├── README.md                        # This file
└── DEVELOPMENT.md                   # Development workflow and practices
```

## Features Comparison 📊

| Feature | GUI | CLI |
|---------|-----|-----|
| Basic Operations | ✅ | ✅ |
| Live Operation Display | ✅ | ❌ |
| Advanced Functions | ✅ | ❌ |
| Memory Operations | ✅ | ❌ |
| History Tracking | ✅ | ❌ |
| Help System | ✅ | ❌ |
| Visual Interface | ✅ | ❌ |
| Keyboard Input | ✅ | ✅ |
| Error Handling | ✅ | ✅ |
| Real-time Feedback | ✅ | ❌ |

## Keyboard Shortcuts ⌨️

The GUI calculator supports comprehensive keyboard shortcuts for lightning-fast calculations:

### Number Input
- **0-9 keys**: Type numbers directly
- **. (period)**: Add decimal point
- **Backspace**: Delete last entered digit

### Operations
- **+ key**: Addition
- **- key**: Subtraction  
- **\* key**: Multiplication
- **/ key**: Division
- **Enter**: Calculate result
- **Escape or C**: Clear calculation

### Memory Functions
- **Ctrl+M**: Memory Store (MS)
- **Ctrl+R**: Memory Recall (MR)
- **Ctrl+Shift+C**: Memory Clear (MC)

### Mathematical Constants
- **Ctrl+P**: Insert π (Pi = 3.14159...)
- **Ctrl+E**: Insert e (Euler's number = 2.71828...)

### Advanced Functions
- **Ctrl+S**: Square root (√)
- **F1**: Open Help with full shortcuts reference

### Features
✅ **Mix keyboard and mouse** - Use whatever feels faster  
✅ **Visual feedback** - Status bar confirms your key presses  
✅ **Live operation display** - See "78 +" while typing  
✅ **Instant response** - No delay between key press and action  
✅ **Error handling** - Invalid keys are safely ignored  
✅ **Tooltips** - Hover over buttons to see keyboard shortcuts  

### Quick Start Examples
```
Type: 15 * 3 [Enter]     → Shows "15 ×" then Result: 45
Type: 25 [Ctrl+S]        → Result: 5 (square root)
Type: 100 [Ctrl+M]       → Stores 100 in memory, shows "Memory: 100"
Type: [Ctrl+P] * 2       → Shows "3.14159 ×" then calculates π × 2
Type: [Esc]              → Clears everything
```

---

**Happy calculating!** 🎉

*Choose GUI for full features with live operation display or CLI for simple calculations.*

## 🤝 Contributing

This project demonstrates professional software development practices including:
- Clean architecture with separation of concerns
- Comprehensive unit testing with JUnit 5
- Proper exception handling and input validation
- Modern Java features and best practices
- Professional documentation and code organization

For development workflow and contribution guidelines, see [DEVELOPMENT.md](DEVELOPMENT.md).
