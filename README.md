# Calculator 🧮

A simple and powerful calculator application with beautiful graphical interface and command-line option.

## Quick Start 🚀

### What You Need
- Java 22 or higher installed on your computer

### How to Run
1. Download this project to your computer
2. Open terminal/command prompt in the project folder
3. Type: `mvn clean compile exec:java`
4. Press Enter and enjoy! 🎉

## How to Use 💡

### Calculator Window
- **Numbers**: Click 0-9 buttons or use your keyboard
- **Operations**: Click +, -, ×, ÷ for calculations
- **Clear**: Press C to start over
- **Sign**: Press ± to make numbers positive/negative  
- **Percent**: Press % for percentage calculations
- **Decimal**: Press . for decimal numbers
- **Advanced**: √ (square root), ∛ (cube root), x² (square), xʸ (power), ⁿ√ (nth root), 1/x (reciprocal)

### Command Line Version
If you prefer typing, run:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.calculator.CalculatorCLI"
```

**New in CLI v2.0:**
- 🎯 **Smart Error Messages**: Clear explanations when you enter invalid data
- 🔄 **Recovery Options**: Automatic retry for division by zero
- 💡 **Helpful Examples**: Shows you exactly what to type
- 😊 **Friendly Interface**: Emojis and encouraging messages
- ⚡ **Better Input Validation**: Handles empty inputs and typos gracefully

## Features ✨

- **Basic Math**: Add, subtract, multiply, divide
- **Advanced Functions**: Powers, square roots, cube roots, nth roots, reciprocals
- **Memory Functions**: Store, recall, clear, add to/subtract from memory
- **History**: See your previous calculations (GUI only)
- **Error Protection**: Won't crash on invalid input
- **Smart CLI**: Improved command-line experience with helpful guidance
- **Dual Interface**: Both GUI and CLI available

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
| Golden Ratio | φ | 1.618033988749895 | Nature proportions and art |

#### Using Constants in CLI
```
=== MATHEMATICAL CONSTANTS ===
π (Pi) = 3.1415926536
  → Used for circles (area, circumference)
e (Euler's number) = 2.7182818285
  → Used for exponential and logarithmic functions

Choose option: 1
Using π = 3.141593
Choose operation (+, -, *, /): *
Enter second number: 2
✨ Result: 3.141593 * 2 = 6.283185
(π * 2 = 6.283185)
Save result to memory? (y/n): y
✅ Result saved to memory
```

#### Using Constants in GUI
- Click **π** button to insert Pi value (tooltip: "Pi (3.14159...) - for circles")
- Click **e** button to insert Euler's number (tooltip: "Euler's number (2.71828...) - for exponential")
- Click **φ** for Golden Ratio (tooltip: "Golden Ratio (1.61803...) - nature proportions")
- Click **2π** for 2×Pi (tooltip: "2×Pi (6.28318...) - full circle circumference")
- Constants display with symbol and precise value
- Info panel shows what each constant represents

#### Circle Calculations
```java
// Circumference = 2πr
double circumference = calculator.circumference(radius);

// Area = πr²  
double area = calculator.circleArea(radius);
```

#### Memory Integration
Constants work seamlessly with memory functions:
- Store constants in memory: MS with π or e
- Add constants to memory: M+ π  
- Use stored constants in calculations
- Memory displays constant values with full precision

## Usage Examples 📝

### Memory Operations Example
```
=== CALCULATOR MENU ===
1. Basic Calculation
2. Advanced Operations
3. Memory Operations  
4. View Memory
5. Start GUI
6. Exit
📝 Memory: 42.5

Choose option: 3

=== MEMORY OPERATIONS ===
MS - Memory Store (save a number)
MR - Memory Recall (use saved number)
MC - Memory Clear (clear memory)
M+ - Memory Add (add to memory)
M- - Memory Subtract (subtract from memory)
📝 Current Memory: 42.5

Choose memory operation: M+
Current memory: 42.5
Enter number to add to memory: 7.5
✅ Added 7.5 to memory
📝 New memory value: 50
```

### Using Memory in Calculations
```
=== BASIC CALCULATION ===
Use memory value 50? (y/n): y
Using 50 as first number
Choose operation (+, -, *, /): *
Enter second number: 2
✨ Result: 50 * 2 = 100
Save result to memory? (y/n): y
✅ Result saved to memory
```

### Advanced Operations Example
```
=== ADVANCED OPERATIONS ===
1. Square Root (√)
2. Power (x^y)
3. Square (x²)
4. Cube Root (∛)
5. Nth Root

Choose operation: 1
Enter number for square root: 16
✨ Result: √16 = 4
Save result to memory? (y/n): n
```

## Troubleshooting 🔧

**Calculator won't start?**
- Make sure Java 22+ is installed
- Check that you're in the correct folder
- Try running `java -version` to verify Java installation

**Compilation errors?**
- Run `mvn clean compile` first
- Check for any missing dependencies
- Ensure all source files are present

**CLI Issues?**
- Use: `mvn clean compile` then `java -cp target/classes com.calculator.CalculatorCLI`
- Or try: `mvn exec:java -Dexec.mainClass=com.calculator.CalculatorCLI` (without quotes)

**GUI Issues?**
- Default command starts GUI: `mvn clean compile exec:java`
- From CLI, choose option 5 to launch GUI
- Both interfaces can run simultaneously

**Need help with calculations?**
- CLI shows helpful examples for invalid inputs
- All error messages include suggestions
- Use C button in GUI to clear and start fresh
- History feature in GUI tracks your calculations

---

**Happy calculating!** 🎉
