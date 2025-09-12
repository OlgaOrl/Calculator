package com.calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CalculatorGUI extends JFrame implements ActionListener {
    
    private JTextField display;
    private Calculator calculator;
    private double firstNumber = 0;
    private String operation = "";
    private boolean isNewCalculation = true;
    
    public CalculatorGUI() {
        calculator = new Calculator();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("🧮 Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(45, 45, 45));
        
        createDisplay();
        mainPanel.add(display, BorderLayout.NORTH);
        
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void createDisplay() {
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        display.setPreferredSize(new Dimension(300, 60));
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttons = {
            "C", "±", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", "="
        };
        
        for (String text : buttons) {
            if (text.equals("=") && panel.getComponentCount() == 19) continue;
            
            JButton button = createButton(text);
            panel.add(button);
        }
        
        return panel;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(70, 50));
        button.addActionListener(this);
        
        if (text.matches("[0-9.]")) {
            button.setBackground(new Color(80, 80, 80));
            button.setForeground(Color.WHITE);
        } else if (text.matches("[+\\-×÷=]")) {
            button.setBackground(new Color(255, 149, 0));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(165, 165, 165));
            button.setForeground(Color.BLACK);
        }
        
        button.setFocusPainted(false);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        try {
            if (command.matches("[0-9]")) {
                handleNumber(command);
            } else if (command.equals(".")) {
                handleDecimal();
            } else if (command.matches("[+\\-×÷]")) {
                handleOperation(command);
            } else if (command.equals("=")) {
                handleEquals();
            } else if (command.equals("C")) {
                handleClear();
            } else if (command.equals("±")) {
                handlePlusMinus();
            } else if (command.equals("%")) {
                handlePercent();
            }
        } catch (Exception ex) {
            display.setText("Error");
            isNewCalculation = true;
        }
    }
    
    private void handleNumber(String number) {
        if (isNewCalculation) {
            display.setText(number);
            isNewCalculation = false;
        } else {
            String current = display.getText();
            if (!current.equals("0")) {
                display.setText(current + number);
            } else {
                display.setText(number);
            }
        }
    }
    
    private void handleDecimal() {
        String current = display.getText();
        if (isNewCalculation) {
            display.setText("0.");
            isNewCalculation = false;
        } else if (!current.contains(".")) {
            display.setText(current + ".");
        }
    }
    
    private void handleOperation(String op) {
        firstNumber = Double.parseDouble(display.getText());
        operation = op;
        isNewCalculation = true;
    }
    
    private void handleEquals() {
        if (!operation.isEmpty()) {
            try {
                double secondNumber = Double.parseDouble(display.getText());
                double result = 0;
                
                switch (operation) {
                    case "+":
                        result = calculator.add(firstNumber, secondNumber);
                        break;
                    case "-":
                        result = calculator.subtract(firstNumber, secondNumber);
                        break;
                    case "×":
                        result = calculator.multiply(firstNumber, secondNumber);
                        break;
                    case "÷":
                        result = calculator.divide(firstNumber, secondNumber);
                        break;
                }
                
                display.setText(calculator.formatResult(result));
                operation = "";
                isNewCalculation = true;
                
            } catch (CalculatorException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                handleClear();
            }
        }
    }
    
    private void handleClear() {
        display.setText("0");
        firstNumber = 0;
        operation = "";
        isNewCalculation = true;
    }
    
    private void handlePlusMinus() {
        double current = Double.parseDouble(display.getText());
        current = -current;
        display.setText(calculator.formatResult(current));
    }
    
    private void handlePercent() {
        double current = Double.parseDouble(display.getText());
        current = current / 100;
        display.setText(calculator.formatResult(current));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorGUI().setVisible(true);
        });
    }
}



