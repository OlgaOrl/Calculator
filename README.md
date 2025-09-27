# Calculator 🧮

A comprehensive calculator application demonstrating professional software development practices, object-oriented programming principles, and modern Java development standards. This project showcases clean architecture, design patterns, and industry-standard development workflows suitable for academic evaluation.

## 📚 Documentation

### Core Documentation Files
- 📋 [UML Class Diagram](UML_Diagram.md) - Complete system architecture and class relationships
- 🏗️ [System Design & Architecture](DESIGN.md) - Design patterns, SOLID principles, and architectural decisions
- 🔢 [Algorithm Documentation](ALGORITHM.md) - Detailed pseudocode and complexity analysis
- ☕ [Java Best Practices](JAVA_BEST_PRACTICES.md) - OOP principles and modern Java implementation
- 🚀 [Development Workflow](DEVELOPMENT.md) - Professional Git workflow and development methodology

### Academic Evaluation Features
This project demonstrates mastery of:
- **Object-Oriented Programming**: Encapsulation, inheritance, polymorphism, abstraction
- **Design Patterns**: MVC, Strategy, Observer, Exception hierarchy
- **SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **Professional Development**: Git workflow, testing strategies, documentation standards
- **Modern Java**: Java 22 features, Maven build system, JUnit 5 testing

## 🎯 Project Planning & Methodology

### Development Approach
This project follows **Agile Development** principles with emphasis on:

- **Iterative Development**: Features developed in small, testable increments
- **Test-Driven Development (TDD)**: Comprehensive unit tests for all functionality
- **Clean Code Principles**: Following Robert Martin's clean code guidelines
- **SOLID Principles**: Ensuring maintainable and extensible architecture
- **Professional Git Workflow**: Feature branches with squash-merge strategy

### Implementation Phases

#### Phase 1: Core Foundation ✅
- [x] Basic arithmetic operations with proper exception handling
- [x] Object-oriented design with clear class responsibilities
- [x] Unit testing infrastructure with JUnit 5
- [x] Maven build configuration and dependency management

#### Phase 2: Advanced Features ✅
- [x] Advanced mathematical functions (power, roots, reciprocals)
- [x] Memory operations with state management
- [x] Calculation history tracking and management
- [x] Comprehensive input validation and error handling

#### Phase 3: User Interfaces ✅
- [x] Command-line interface following MVC pattern
- [x] Graphical user interface with event-driven architecture
- [x] Keyboard shortcuts and accessibility features
- [x] Visual feedback and real-time operation display

#### Phase 4: Professional Standards ✅
- [x] Complete documentation with UML diagrams
- [x] Code quality improvements and refactoring
- [x] Performance optimizations and complexity analysis
- [x] Professional project structure and Git workflow

## Quick Start 🚀

### Prerequisites
- **Java 22 or higher** - Latest LTS features and performance improvements
- **Maven 3.8+** - Build automation and dependency management

### Running the Application

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

#### Default Execution
```bash
mvn clean compile exec:java
```

## Features & Capabilities ✨

### Dual Interface Design
- **Graphical Interface**: Modern dark theme with intuitive button layout
- **Command-Line Interface**: Simple menu-driven interaction
- **Live Operation Display**: Real-time feedback showing current calculation state
- **Memory Management**: Complete memory operations (store, recall, clear, arithmetic)

### Mathematical Operations
- **Basic Arithmetic**: Addition, subtraction, multiplication, division
- **Advanced Functions**: Powers, square roots, cube roots, nth roots, reciprocals
- **Mathematical Constants**: High-precision π (Pi) and e (Euler's number)
- **Error Handling**: Robust exception handling for all edge cases

### User Experience Features
- **Visual Design**: Color-coded buttons with professional dark theme
- **Keyboard Support**: Complete keyboard shortcuts for all operations
- **History Tracking**: View and manage calculation history (GUI only)
- **Help System**: Built-in comprehensive help with usage instructions
- **Status Feedback**: Real-time status updates and operation confirmations

## Testing & Quality Assurance 🧪

### Comprehensive Test Suite
```bash
mvn test
```

**Test Coverage Includes:**
- ✅ **Unit Tests**: All mathematical operations and edge cases
- ✅ **Exception Testing**: Proper error handling validation
- ✅ **Memory Operations**: State management and persistence
- ✅ **Input Validation**: Boundary conditions and invalid inputs
- ✅ **History Management**: Data integrity and operations
- ✅ **Parameterized Tests**: Multiple test scenarios with JUnit 5

### Quality Metrics
- **Code Coverage**: Comprehensive test coverage for all core functionality
- **Error Handling**: Graceful handling of all exception scenarios
- **Performance**: Optimized algorithms with documented complexity
- **Maintainability**: Clean code structure following SOLID principles

## Project Structure 📁

```
Calculator-1/
├── README.md                        # 📖 Main project documentation
├── DEVELOPMENT.md                   # 🚀 Git workflow and development practices
├── UML_Diagram.md                   # 📋 System architecture and class diagrams
├── DESIGN.md                        # 🏗️ Design patterns and architectural decisions
├── ALGORITHM.md                     # 🔢 Pseudocode and complexity analysis
├── JAVA_BEST_PRACTICES.md           # ☕ OOP principles and coding standards
├── src/
│   ├── main/java/com/calculator/
│   │   ├── Calculator.java          # 🧮 Core mathematical operations and business logic
│   │   ├── CalculatorGUI.java       # 🖥️ Graphical user interface with event handling
│   │   ├── CalculatorCLI.java       # 💻 Command-line interface implementation
│   │   ├── CalculatorException.java # ⚠️ Base exception class for error hierarchy
│   │   ├── DivisionByZeroException.java # ⚠️ Specific mathematical error handling
│   │   ├── InvalidInputException.java   # ⚠️ Input validation error handling
│   │   └── help/
│   │       └── HelpTopic.java       # ❓ Help system support and documentation
│   └── test/java/com/calculator/
│       └── CalculatorTest.java      # 🧪 Comprehensive unit test suite
├── pom.xml                          # 📦 Maven configuration and dependencies
└── .gitignore                       # 🚫 Version control exclusions
```

## Academic Evaluation Points 🎓

### Object-Oriented Programming Demonstration
- **Encapsulation**: Private fields with controlled access through public methods
- **Inheritance**: Exception hierarchy with proper polymorphic behavior
- **Polymorphism**: Interface implementations and method overriding
- **Abstraction**: Clear separation between interface and implementation

### Design Patterns Implementation
- **Model-View-Controller**: Clean separation of business logic and presentation
- **Strategy Pattern**: Encapsulated mathematical operations
- **Observer Pattern**: Event-driven GUI architecture
- **Exception Hierarchy**: Structured error handling with specific exception types

### Professional Development Practices
- **Version Control**: Professional Git workflow with feature branches
- **Testing Strategy**: Comprehensive unit testing with JUnit 5
- **Documentation**: Complete technical documentation with UML diagrams
- **Build Management**: Maven configuration with proper dependency management
- **Code Quality**: Adherence to Java coding standards and best practices

### Technical Implementation Highlights
- **Modern Java Features**: Utilization of Java 22 capabilities
- **Error Handling**: Robust exception management throughout the application
- **User Experience**: Intuitive interfaces with comprehensive keyboard support
- **Performance**: Optimized algorithms with documented time/space complexity
- **Extensibility**: Architecture designed for easy feature additions

## Usage Examples 📝

### GUI Calculator Usage
```
Launch: mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorGUI"

Example Calculation:
1. Type: 15 * 3
2. Display shows: "15 ×" (operation indicator)
3. Press Enter or = button
4. Result: 45

Memory Operations:
1. Calculate: 100 + 50 = 150
2. Press MS (Memory Store)
3. Memory indicator shows: "Memory: 150"
4. Later press MR (Memory Recall) to retrieve value
```

### CLI Calculator Usage
```
Launch: mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorCLI"

=== CALCULATOR MENU ===
1. Basic Calculation
2. Exit
Choose option: 1

Enter first number: 25
Enter operation (+, -, *, /): /
Enter second number: 5
Result: 5.0
```

## Keyboard Shortcuts Reference ⌨️

### Essential Shortcuts
- **0-9**: Number input
- **+ - * /**: Mathematical operations
- **Enter**: Calculate result
- **Escape**: Clear calculation
- **Ctrl+M**: Memory Store
- **Ctrl+R**: Memory Recall
- **Ctrl+P**: Insert π (Pi)
- **Ctrl+E**: Insert e (Euler's number)
- **F1**: Open help system

## Troubleshooting 🔧

### Common Issues and Solutions

**Application won't start:**
```bash
# Verify Java version
java -version  # Should show Java 22+

# Clean and recompile
mvn clean compile
```

**GUI display issues:**
```bash
# Use quoted parameters in PowerShell
mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorGUI"

# Alternative method
mvn clean compile
java -cp target/classes com.calculator.CalculatorGUI
```

**Symbol display problems:**
- Mathematical symbols may appear as squares on some systems
- Functionality remains unaffected regardless of symbol display
- All operations work correctly with keyboard shortcuts

## Contributing & Development 🤝

For detailed development workflow, Git practices, and contribution guidelines, see [DEVELOPMENT.md](DEVELOPMENT.md).

### Development Setup
```bash
# Clone and setup
git clone <repository-url>
cd Calculator-1
mvn clean compile test

# Run application
mvn exec:java "-Dexec.mainClass=com.calculator.CalculatorGUI"
```

### Code Quality Standards
- Follow Java naming conventions and coding standards
- Maintain comprehensive unit test coverage
- Document all public methods with Javadoc
- Adhere to SOLID principles in all implementations
- Use meaningful commit messages following conventional format

---

## Summary

This Calculator project demonstrates comprehensive understanding of:

✅ **Object-Oriented Programming** - Complete implementation of OOP principles  
✅ **Design Patterns** - Professional architectural patterns and practices  
✅ **Modern Java Development** - Java 22 features and Maven build system  
✅ **Testing Strategies** - Comprehensive unit testing with JUnit 5  
✅ **Documentation Standards** - Complete technical documentation with UML  
✅ **Professional Workflow** - Industry-standard Git practices and development methodology  

**Perfect for academic evaluation and professional portfolio demonstration!** 🎉

*Choose GUI for full-featured experience or CLI for simple calculations.*
