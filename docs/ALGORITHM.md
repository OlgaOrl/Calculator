# Algorithm Documentation

## Overview

This document provides detailed pseudocode and complexity analysis for the core algorithms implemented in the Calculator application. Each algorithm is analyzed for time and space complexity, with implementation considerations.

## Basic Arithmetic Operations

### Addition Algorithm
```pseudocode
ALGORITHM Add(a, b)
INPUT: Two double values a and b
OUTPUT: Sum of a and b
THROWS: InvalidInputException

BEGIN
    VALIDATE_INPUT(a, "First parameter")
    VALIDATE_INPUT(b, "Second parameter")
    
    result ← a + b
    LOG_CALCULATION(a + " + " + b + " = " + FORMAT_RESULT(result))
    
    RETURN result
END

SUBROUTINE VALIDATE_INPUT(value, parameterName)
BEGIN
    IF value is NaN THEN
        THROW InvalidInputException(parameterName + " cannot be NaN")
    END IF
END
```

**Complexity Analysis:**
- Time Complexity: O(1) - Constant time operation
- Space Complexity: O(1) - No additional space required
- Edge Cases: NaN inputs, infinity values

### Division Algorithm
```pseudocode
ALGORITHM Divide(dividend, divisor)
INPUT: Two double values dividend and divisor
OUTPUT: Quotient of dividend ÷ divisor
THROWS: DivisionByZeroException, InvalidInputException

BEGIN
    VALIDATE_INPUT(dividend, "Dividend")
    VALIDATE_INPUT(divisor, "Divisor")
    
    IF divisor = 0.0 THEN
        THROW DivisionByZeroException("Cannot divide " + dividend + " by zero")
    END IF
    
    result ← dividend / divisor
    LOG_CALCULATION(dividend + " / " + divisor + " = " + FORMAT_RESULT(result))
    
    RETURN result
END
```

**Complexity Analysis:**
- Time Complexity: O(1) - Constant time operation
- Space Complexity: O(1) - No additional space required
- Special Cases: Division by zero, infinity results

## Advanced Mathematical Operations

### Power Algorithm
```pseudocode
ALGORITHM Power(base, exponent)
INPUT: Two double values base and exponent
OUTPUT: base raised to the power of exponent
THROWS: InvalidInputException

BEGIN
    VALIDATE_INPUT(base, "Base")
    VALIDATE_INPUT(exponent, "Exponent")
    
    result ← MATH_POW(base, exponent)
    LOG_CALCULATION(base + "^" + exponent + " = " + FORMAT_RESULT(result))
    
    RETURN result
END
```

**Complexity Analysis:**
- Time Complexity: O(log n) - Where n is the exponent value
- Space Complexity: O(1) - Constant space
- Implementation: Uses optimized mathematical library function

### Square Root Algorithm
```pseudocode
ALGORITHM SquareRoot(number)
INPUT: Double value number
OUTPUT: Square root of number
THROWS: InvalidInputException

BEGIN
    VALIDATE_INPUT(number, "Number")
    
    IF number < 0 THEN
        THROW InvalidInputException("Square root of negative number: " + number)
    END IF
    
    result ← MATH_SQRT(number)
    LOG_CALCULATION("√" + number + " = " + FORMAT_RESULT(result))
    
    RETURN result
END
```

**Complexity Analysis:**
- Time Complexity: O(1) - Uses hardware/library optimization
- Space Complexity: O(1) - Constant space
- Edge Cases: Negative inputs, zero input

### Factorial Algorithm
```pseudocode
ALGORITHM Factorial(n)
INPUT: Integer n
OUTPUT: n! (factorial of n)
THROWS: InvalidInputException

BEGIN
    IF n < 0 THEN
        THROW InvalidInputException("Factorial not defined for negative numbers: " + n)
    END IF
    
    IF n > 20 THEN
        THROW InvalidInputException("Factorial would overflow for number: " + n)
    END IF
    
    result ← 1
    FOR i ← 2 TO n DO
        result ← result * i
    END FOR
    
    LOG_CALCULATION(n + "! = " + result)
    
    RETURN result
END
```

**Complexity Analysis:**
- Time Complexity: O(n) - Linear in input size
- Space Complexity: O(1) - Constant space (iterative approach)
- Optimization: Overflow prevention, input validation

### Nth Root Algorithm
```pseudocode
ALGORITHM NthRoot(number, n)
INPUT: Double number, integer n (root degree)
OUTPUT: nth root of number
THROWS: InvalidInputException

BEGIN
    VALIDATE_INPUT(number, "Number")
    
    IF n = 0 THEN
        THROW InvalidInputException("Root degree cannot be zero")
    END IF
    
    IF n % 2 = 0 AND number < 0 THEN
        THROW InvalidInputException("Even root of negative number: " + number)
    END IF
    
    IF number < 0 THEN
        result ← -MATH_POW(-number, 1.0/n)
    ELSE
        result ← MATH_POW(number, 1.0/n)
    END IF
    
    LOG_CALCULATION(n + "√" + number + " = " + FORMAT_RESULT(result))
    
    RETURN result
END
```

**Complexity Analysis:**
- Time Complexity: O(log n) - Logarithmic in root degree
- Space Complexity: O(1) - Constant space
- Special Handling: Negative numbers with odd roots

## Memory Management Algorithms

### Memory Store Algorithm
```pseudocode
ALGORITHM MemoryStore(value)
INPUT: Double value to store
OUTPUT: None (void)
THROWS: InvalidInputException

BEGIN
    VALIDATE_INPUT(value, "Memory value")
    
    memory ← value
    LOG_CALCULATION("Memory store: " + FORMAT_RESULT(value))
END
```

### Memory Add Algorithm
```pseudocode
ALGORITHM MemoryAdd(value)
INPUT: Double value to add to memory
OUTPUT: None (void)
THROWS: InvalidInputException

BEGIN
    VALIDATE_INPUT(value, "Memory value")
    
    memory ← memory + value
    LOG_CALCULATION("Memory add: " + FORMAT_RESULT(value) + 
                   ", Total: " + FORMAT_RESULT(memory))
END
```

**Complexity Analysis:**
- Time Complexity: O(1) - Constant time operations
- Space Complexity: O(1) - Single memory variable
- Thread Safety: Single-threaded design, no concurrency issues

## History Management Algorithm

### History Logging Algorithm
```pseudocode
ALGORITHM LogCalculation(calculation)
INPUT: String calculation description
OUTPUT: None (void)

BEGIN
    history.ADD(calculation)
    
    // Optional: Limit history size to prevent memory issues
    IF history.SIZE() > MAX_HISTORY_SIZE THEN
        history.REMOVE_FIRST()
    END IF
END
```

**Complexity Analysis:**
- Time Complexity: O(1) - Amortized constant time for ArrayList
- Space Complexity: O(n) - Where n is number of calculations
- Memory Management: Optional size limiting to prevent memory leaks

### History Retrieval Algorithm
```pseudocode
ALGORITHM GetHistory()
INPUT: None
OUTPUT: List of calculation strings

BEGIN
    RETURN COPY_OF(history)
END

ALGORITHM GetLastCalculation()
INPUT: None
OUTPUT: String of last calculation or null

BEGIN
    IF history.IS_EMPTY() THEN
        RETURN null
    ELSE
        RETURN history.GET_LAST()
    END IF
END
```

## Input Validation Algorithm

### Comprehensive Input Validation
```pseudocode
ALGORITHM ValidateInput(value, parameterName)
INPUT: Double value, String parameter name
OUTPUT: None (void)
THROWS: InvalidInputException

BEGIN
    IF IS_NAN(value) THEN
        THROW InvalidInputException(parameterName + " cannot be NaN")
    END IF
    
    // Additional validations can be added here:
    // - Range checking
    // - Specific operation constraints
    // - Business rule validations
END
```

## Result Formatting Algorithm

### Smart Number Formatting
```pseudocode
ALGORITHM FormatResult(result)
INPUT: Double result value
OUTPUT: Formatted string representation

BEGIN
    IF IS_NAN(result) THEN
        RETURN "NaN"
    ELSE IF IS_POSITIVE_INFINITY(result) THEN
        RETURN "∞"
    ELSE IF IS_NEGATIVE_INFINITY(result) THEN
        RETURN "-∞"
    ELSE IF IS_INTEGER(result) THEN
        RETURN STRING_VALUE_OF(LONG_CAST(result))
    ELSE
        RETURN STRING_VALUE_OF(result)
    END IF
END

SUBROUTINE IS_INTEGER(value)
BEGIN
    RETURN (value = FLOOR(value)) AND IS_FINITE(value)
END
```

**Complexity Analysis:**
- Time Complexity: O(1) - Constant time formatting
- Space Complexity: O(1) - Single string creation
- User Experience: Clean display of different number types

## GUI Event Handling Algorithms

### Button Click Processing
```pseudocode
ALGORITHM HandleButtonClick(buttonText)
INPUT: String representing button clicked
OUTPUT: None (updates display)

BEGIN
    SWITCH buttonText
        CASE "0" TO "9":
            HANDLE_NUMBER(buttonText)
        CASE "+", "-", "×", "÷":
            HANDLE_OPERATION(buttonText)
        CASE "=":
            HANDLE_EQUALS()
        CASE "C", "CE":
            HANDLE_CLEAR()
        CASE MEMORY_OPERATIONS:
            HANDLE_MEMORY_OPERATION(buttonText)
        DEFAULT:
            // Handle other special functions
    END SWITCH
    
    UPDATE_MEMORY_DISPLAY()
    UPDATE_STATUS_DISPLAY()
END
```

### Keyboard Input Processing
```pseudocode
ALGORITHM HandleKeyboardInput(keyEvent)
INPUT: KeyEvent from keyboard
OUTPUT: None (updates display)

BEGIN
    keyChar ← GET_KEY_CHAR(keyEvent)
    keyCode ← GET_KEY_CODE(keyEvent)
    
    IF IS_DIGIT(keyChar) THEN
        HANDLE_NUMBER(STRING_OF(keyChar))
        UPDATE_STATUS("Number entered: " + keyChar)
    ELSE IF keyChar = '+' THEN
        HANDLE_OPERATION("+")
        UPDATE_STATUS("Operation: Addition")
    // ... other key mappings
    ELSE IF keyCode = ENTER THEN
        HANDLE_EQUALS()
        UPDATE_STATUS("Result calculated")
    END IF
END
```

## Performance Optimization Strategies

### Algorithm Optimization Techniques Used

1. **Early Validation**: Input validation before expensive operations
2. **Efficient String Operations**: Minimal string concatenation
3. **Lazy Evaluation**: History formatting only when needed
4. **Memory Pooling**: Reuse of calculation objects where possible
5. **Caching**: Result formatting cached for repeated values

### Big O Summary Table

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|----------------|------------------|-------|
| Basic Arithmetic | O(1) | O(1) | Constant time operations |
| Power | O(log n) | O(1) | Logarithmic in exponent |
| Factorial | O(n) | O(1) | Linear, with overflow protection |
| Square Root | O(1) | O(1) | Hardware optimized |
| Memory Operations | O(1) | O(1) | Simple variable operations |
| History Add | O(1) amortized | O(n) | ArrayList operations |
| Input Validation | O(1) | O(1) | Simple checks |
| Result Formatting | O(1) | O(1) | String operations |

## Error Handling Complexity

### Exception Processing Algorithm
```pseudocode
ALGORITHM ProcessException(exception)
INPUT: CalculatorException or subtype
OUTPUT: User-friendly error message

BEGIN
    errorMessage ← GET_MESSAGE(exception)
    
    // Log error for debugging (optional)
    LOG_ERROR(exception)
    
    // Display user-friendly message
    DISPLAY_ERROR_MESSAGE(errorMessage)
    
    // Reset calculator state if necessary
    IF IS_CRITICAL_ERROR(exception) THEN
        RESET_CALCULATOR_STATE()
    END IF
END
```

This comprehensive algorithm documentation provides the foundation for understanding the Calculator's implementation efficiency and correctness.