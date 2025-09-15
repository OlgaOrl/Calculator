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
- **Advanced Functions**: Powers, square roots, percentages
- **Memory**: Store and recall numbers
- **History**: See your previous calculations
- **Error Protection**: Won't crash on invalid input
- **Smart CLI**: Improved command-line experience with helpful guidance

## Examples 📝

### Simple Calculations
- `5 + 3 = 8`
- `10 × 2.5 = 25`
- `100 ÷ 4 = 25`

### Advanced Features
- `15% of 200 = 30`
- `√16 = 4`
- `2³ = 8`

## Troubleshooting 🔧

**Calculator won't start?**
- Make sure Java 22+ is installed
- Check that you're in the correct folder
- Try running `java -version` to verify Java installation

**CLI Issues?**
- Use: `mvn clean compile` then `java -cp target/classes com.calculator.CalculatorCLI`
- Or try: `mvn exec:java -Dexec.mainClass=com.calculator.CalculatorCLI` (without quotes)

**Need help with calculations?**
- CLI shows helpful examples for invalid inputs
- All error messages include suggestions
- Use C button in GUI to clear and start fresh

---

**Happy calculating!** 🎉
