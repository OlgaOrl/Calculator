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
