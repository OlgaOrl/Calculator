package com.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * A comprehensive calculator class providing mathematical operations,
 * memory functionality, and calculation history tracking.
 */
public class Calculator {
    private double memory = 0.0;
    private List<String> history = new ArrayList<>();
    
    // Mathematical constants
    public static final double PI = Math.PI;
    public static final double E = Math.E;
    
    // Basic arithmetic operations
    public double add(double a, double b) throws InvalidInputException {
        validateInput(a, "First parameter");
        validateInput(b, "Second parameter");
        
        double result = a + b;
        logCalculation(a + " + " + b + " = " + formatResult(result));
        return result;
    }
    
    public double subtract(double a, double b) throws InvalidInputException {
        validateInput(a, "Minuend");
        validateInput(b, "Subtrahend");
        
        double result = a - b;
        logCalculation(a + " - " + b + " = " + formatResult(result));
        return result;
    }
    
    public double multiply(double a, double b) throws InvalidInputException {
        validateInput(a, "Multiplicand");
        validateInput(b, "Multiplier");
        
        double result = a * b;
        logCalculation(a + " * " + b + " = " + formatResult(result));
        return result;
    }
    
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
    
    // Advanced operations
    public double power(double base, double exponent) throws InvalidInputException {
        validateInput(base, "Base");
        validateInput(exponent, "Exponent");
        
        double result = Math.pow(base, exponent);
        logCalculation(base + "^" + exponent + " = " + formatResult(result));
        return result;
    }
    
    public double squareRoot(double number) throws InvalidInputException {
        validateInput(number, "Number");
        if (number < 0) {
            throw new InvalidInputException("Cannot calculate square root of negative number: " + number);
        }
        
        double result = Math.sqrt(number);
        logCalculation("√" + number + " = " + formatResult(result));
        return result;
    }
    
    public double cubeRoot(double number) throws InvalidInputException {
        validateInput(number, "Number");
        
        double result = Math.cbrt(number);
        logCalculation("∛" + number + " = " + formatResult(result));
        return result;
    }
    
    public double nthRoot(double number, double n) throws InvalidInputException {
        validateInput(number, "Number");
        validateInput(n, "Root");
        
        if (n == 0) {
            throw new InvalidInputException("Root cannot be zero");
        }
        
        double result = Math.pow(number, 1.0 / n);
        logCalculation("" + n + "√" + number + " = " + formatResult(result));
        return result;
    }
    
    public double percentage(double number, double percent) throws InvalidInputException {
        validateInput(number, "Number");
        validateInput(percent, "Percentage");
        
        double result = (number * percent) / 100.0;
        logCalculation(percent + "% of " + number + " = " + formatResult(result));
        return result;
    }
    
    public double absolute(double number) throws InvalidInputException {
        validateInput(number, "Number");
        
        double result = Math.abs(number);
        logCalculation("|" + number + "| = " + formatResult(result));
        return result;
    }
    
    public long factorial(int number) throws InvalidInputException {
        if (number < 0) {
            throw new InvalidInputException("Factorial is not defined for negative numbers: " + number);
        }
        
        if (number > 20) {
            throw new InvalidInputException("Factorial calculation would overflow for number: " + number);
        }
        
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        
        logCalculation(number + "! = " + result);
        return result;
    }
    
    public double reciprocal(double number) throws InvalidInputException, DivisionByZeroException {
        validateInput(number, "Number");
        return divide(1.0, number);
    }
    
    // Memory operations
    public void memoryStore(double value) throws InvalidInputException {
        validateInput(value, "Memory value");
        this.memory = value;
        logCalculation("Memory store: " + formatResult(value));
    }
    
    public double memoryRecall() {
        return memory;
    }
    
    public void memoryAdd(double value) throws InvalidInputException {
        validateInput(value, "Memory value");
        this.memory += value;
        logCalculation("Memory add: " + formatResult(value) + ", Total: " + formatResult(memory));
    }
    
    public void memorySubtract(double value) throws InvalidInputException {
        validateInput(value, "Memory value");
        this.memory -= value;
        logCalculation("Memory subtract: " + formatResult(value) + ", Total: " + formatResult(memory));
    }
    
    public double getMemoryValue() {
        return memory;
    }
    
    public void memoryClear() {
        this.memory = 0.0;
        logCalculation("Memory cleared");
    }
    
    public boolean hasMemoryValue() {
        return memory != 0.0;
    }
    
    // Constants
    public double getPi() {
        return PI;
    }
    
    public double getE() {
        return E;
    }
    
    public double multiplyByPi(double number) throws InvalidInputException {
        return multiply(number, PI);
    }
    
    public double multiplyByE(double number) throws InvalidInputException {
        return multiply(number, E);
    }
    
    public double circleArea(double radius) throws CalculatorException {
        validateInput(radius, "Radius");
        return multiply(PI, power(radius, 2));
    }
    
    public double circumference(double radius) throws CalculatorException {
        validateInput(radius, "Radius");
        return multiply(2, multiply(PI, radius));
    }
    
    // History operations
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
    
    public void clearHistory() {
        history.clear();
    }
    
    public String getLastCalculation() {
        return history.isEmpty() ? null : history.get(history.size() - 1);
    }
    
    // Utility methods
    public double round(double value, int places) throws InvalidInputException {
        validateInput(value, "Value");
        if (places < 0) {
            throw new InvalidInputException("Decimal places cannot be negative: " + places);
        }
        
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        double result = bd.doubleValue();
        
        logCalculation("Round " + value + " to " + places + " places = " + formatResult(result));
        return result;
    }
    
    public boolean isInteger(double value) {
        return value == Math.floor(value) && !Double.isInfinite(value);
    }
    
    public String formatResult(double result) {
        if (Double.isNaN(result)) {
            return "NaN";
        } else if (Double.isInfinite(result)) {
            return result > 0 ? "∞" : "-∞";
        } else if (isInteger(result)) {
            return String.valueOf((long) result);
        } else {
            return String.valueOf(result);
        }
    }
    
    private void validateInput(double value, String parameterName) throws InvalidInputException {
        if (Double.isNaN(value)) {
            throw new InvalidInputException(parameterName + " cannot be NaN");
        }
    }
    
    private void logCalculation(String calculation) {
        history.add(calculation);
    }
    
    @Override
    public String toString() {
        return String.format("Calculator{memory=%.2f, history entries=%d}", 
                           memory, history.size());
    }
}



