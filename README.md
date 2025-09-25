# Calculator 🧮

A simple and powerful calculator application with beautiful graphical interface and command-line option.

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
- **Clear**: Press C to start over
- **Sign**: Press ± to make numbers positive/negative  
- **Percent**: Press % for percentage calculations
- **Decimal**: Press . for decimal numbers
- **Advanced**: √ (square root), ∛ (cube root), x² (square), xʸ (power), ⁿ√ (nth root), 1/x (reciprocal)
- **Help**: Click Help button for detailed instructions
- **History**: View and clear calculation history

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
- **Advanced Functions**: Powers, square roots, cube roots, nth roots, reciprocals
- **Memory Functions**: Store, recall, clear, add to/subtract from memory
- **History**: See your previous calculations (GUI only)
- **Error Protection**: Won't crash on invalid input
- **Help System**: Built-in help with detailed instructions
- **Visual Design**: Modern dark theme with color-coded buttons

### GUI Button Layout
```
┌─────┬─────┬─────┬─────┬─────┐
│ MC  │ MR  │ MS  │ M+  │ M-  │ Memory Functions
├─────┼─────┼─────┼─────┼─────┤
│  C  │ CE  │  √  │ x²  │ 1/x │ Clear & Advanced
├─────┼─────┼─────┼─────┼─────┤
│  ∛  │ xʸ  │ ⁿ√  │  %  │  ±  │ Advanced Functions
├─────┼─────┼─────┼─────┼─────┤
│  7  │  8  │  9  │  ÷  │Help │ Numbers & Operations
├─────┼─────┼─────┼─────┼─────┤
│  4  │  5  │  6  │  ×  │Cl H │ Numbers & Operations
├─────┼─────┼─────┼─────┼─────┤
│  1  │  2  │  3  │  -  │     │ Numbers & Operations
├─────┼─────┼─────┼─────┼─────┤
│  0  │  .  │  =  │  +  │     │ Numbers & Operations
└─────┴─────┴─────┴─────┴─────┘
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
- **xʸ**: Raise to any power
- **ⁿ√**: Calculate nth root
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
3. Press **=** to calculate
4. Use **Help** button for detailed instructions
5. View **History** to see previous calculations
6. Use memory functions (MS, MR, MC, M+, M-) to store values

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
├── src/
│   ├── main/java/com/calculator/
│   │   ├── Calculator.java          # Core calculation logic
│   │   ├── CalculatorGUI.java       # Graphical interface
│   │   ├── CalculatorCLI.java       # Command-line interface
│   │   ├── CalculatorException.java # Base exception class
│   │   ├── DivisionByZeroException.java
│   │   ├── InvalidInputException.java
│   │   └── help/
│   │       └── HelpTopic.java       # Help system support
│   └── test/java/com/calculator/
│       └── CalculatorTest.java      # Comprehensive tests
├── pom.xml                          # Maven configuration
└── README.md                        # This file
```

## Features Comparison 📊

| Feature | GUI | CLI |
|---------|-----|-----|
| Basic Operations | ✅ | ✅ |
| Advanced Functions | ✅ | ❌ |
| Memory Operations | ✅ | ❌ |
| History Tracking | ✅ | ❌ |
| Help System | ✅ | ❌ |
| Visual Interface | ✅ | ❌ |
| Keyboard Input | ✅ | ✅ |
| Error Handling | ✅ | ✅ |

---

**Happy calculating!** 🎉

*Choose GUI for full features or CLI for simple calculations.*
