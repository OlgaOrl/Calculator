package com.calculator.validation;

import com.calculator.CalculatorException;

/**
 * Custom exception for input validation errors in the Calculator application.
 * 
 * This exception extends CalculatorException to maintain consistency with the
 * existing exception hierarchy while providing specific validation error handling.
 * It includes detailed error messages and supports exception chaining for
 * comprehensive error reporting.
 * 
 * @author Calculator Development Team
 * @version 1.0
 * @since 2.0
 */
public class ValidationException extends CalculatorException {
    
    /** The input that caused the validation error */
    private final String invalidInput;
    
    /** The validation rule that was violated */
    private final String violatedRule;
    
    /**
     * Constructs a new ValidationException with the specified message.
     * 
     * @param message the detail message explaining the validation error
     */
    public ValidationException(String message) {
        super(message);
        this.invalidInput = null;
        this.violatedRule = null;
    }
    
    /**
     * Constructs a new ValidationException with message and cause.
     * 
     * @param message the detail message explaining the validation error
     * @param cause the underlying cause of the validation error
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.invalidInput = null;
        this.violatedRule = null;
    }
    
    /**
     * Constructs a new ValidationException with detailed validation information.
     * 
     * @param message the detail message explaining the validation error
     * @param invalidInput the input that caused the validation error
     * @param violatedRule the validation rule that was violated
     */
    public ValidationException(String message, String invalidInput, String violatedRule) {
        super(message);
        this.invalidInput = invalidInput;
        this.violatedRule = violatedRule;
    }
    
    /**
     * Constructs a new ValidationException with complete error information.
     * 
     * @param message the detail message explaining the validation error
     * @param invalidInput the input that caused the validation error
     * @param violatedRule the validation rule that was violated
     * @param cause the underlying cause of the validation error
     */
    public ValidationException(String message, String invalidInput, String violatedRule, Throwable cause) {
        super(message, cause);
        this.invalidInput = invalidInput;
        this.violatedRule = violatedRule;
    }
    
    /**
     * Gets the input that caused the validation error.
     * 
     * @return the invalid input, or null if not specified
     */
    public String getInvalidInput() {
        return invalidInput;
    }
    
    /**
     * Gets the validation rule that was violated.
     * 
     * @return the violated rule, or null if not specified
     */
    public String getViolatedRule() {
        return violatedRule;
    }
    
    /**
     * Returns a detailed string representation of this exception.
     * 
     * @return detailed exception information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        
        if (invalidInput != null) {
            sb.append(" [Invalid Input: '").append(invalidInput).append("']");
        }
        
        if (violatedRule != null) {
            sb.append(" [Violated Rule: ").append(violatedRule).append("]");
        }
        
        return sb.toString();
    }
}