package com.calculator.validation;

import com.calculator.Calculator;
import com.calculator.CalculatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validation Integration Tests")
class ValidationIntegrationTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    @DisplayName("Should integrate validation with Calculator operations")
    void testCalculatorValidationIntegration() {
        // Test valid operations
        assertDoesNotThrow(() -> calculator.add(10.0, 20.0));
        assertDoesNotThrow(() -> calculator.divide(10.0, 2.0));
        assertDoesNotThrow(() -> calculator.squareRoot(9.0));
        
        // Test invalid operations
        assertThrows(CalculatorException.class, () -> calculator.divide(10.0, 0.0));
        assertThrows(CalculatorException.class, () -> calculator.squareRoot(-1.0));
        assertThrows(CalculatorException.class, () -> calculator.factorial(-1));
    }
    
    @Test
    @DisplayName("Should handle validation exceptions properly")
    void testValidationExceptionHandling() {
        try {
            calculator.divide(10.0, 0.0);
            fail("Should have thrown exception");
        } catch (CalculatorException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("zero"));
        }
    }
    
    @Test
    @DisplayName("Should validate configuration-based limits")
    void testConfigurationBasedValidation() {
        // Test with numbers within configured limits
        assertDoesNotThrow(() -> calculator.add(1000.0, 2000.0));
        
        // Test with very large numbers (depending on configuration)
        // This would test the configuration-based validation limits
    }
}