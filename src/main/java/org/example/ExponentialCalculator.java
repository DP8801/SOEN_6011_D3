package org.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * This class represents a simple Swing-based application that calculates the
 * value of the expression a * b^x using user-provided inputs for a, b, and x.
 */
public final class ExponentialCalculator {

  private static final String INPUT_ERROR = "Input Error";
  private static final String INPUT_INFO = "Input Info";
  private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);
  private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
  private static final Color BUTTON_COLOR = new Color(70, 130, 180);
  private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

  private static JTextField aText;
  private static JTextField bText;
  private static JTextField xText;
  private static JTextField resultText;

  private ExponentialCalculator() {
    // Private constructor to prevent instantiation
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * The entry point of the application. Initializes the user interface,
   * creates a JFrame window, and adds a JPanel with components to the frame.
   * This method sets up the main window of the application with the title "Function: ab^x".
   * It configures the window size and close operation, adds a panel to the frame,
   * and then places components on the panel. Finally, it makes the window visible.
   *
   * @param args an array of command-line arguments passed to the application
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Exponential Function Calculator");
    frame.setSize(400, 300);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null); // Center the window on the screen

    JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
    panel.setBackground(BACKGROUND_COLOR);
    frame.add(panel);
    placeComponents(panel);

    frame.setVisible(true);
  }

  /**
   * Places the components (labels, text fields, button) on the panel.
   *
   * @param panel the panel to place components on
   */
  private static void placeComponents(JPanel panel) {
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setLayout(new GridLayout(5, 2, 10, 10));

    JLabel labelA = new JLabel("Enter the value for a:");
    labelA.setFont(DEFAULT_FONT);
    labelA.setToolTipText("Click for valid range of 'a'");
    labelA.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JOptionPane.showMessageDialog(panel, "Valid range for 'a': Any real number.",
                INPUT_INFO, JOptionPane.INFORMATION_MESSAGE);
      }
    });
    panel.add(labelA);

    aText = new JTextField(20);
    aText.setFont(DEFAULT_FONT);
    aText.getDocument().addDocumentListener(createDocumentListener(aText));
    panel.add(aText);

    JLabel labelB = new JLabel("Enter the value for b:");
    labelB.setFont(DEFAULT_FONT);
    labelB.setToolTipText("Click for valid range of 'b'");
    labelB.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JOptionPane.showMessageDialog(panel, "Valid range for 'b': Any real number "
                + "greater than zero.", INPUT_INFO, JOptionPane.INFORMATION_MESSAGE);
      }
    });
    panel.add(labelB);

    bText = new JTextField(20);
    bText.setFont(DEFAULT_FONT);
    bText.getDocument().addDocumentListener(createDocumentListener(bText));
    panel.add(bText);

    JLabel labelX = new JLabel("Enter the value for x:");
    labelX.setFont(DEFAULT_FONT);
    labelX.setToolTipText("Click for valid range of 'x'");
    labelX.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JOptionPane.showMessageDialog(panel, "Valid range for 'x': Any real number.",
                INPUT_INFO, JOptionPane.INFORMATION_MESSAGE);
      }
    });
    panel.add(labelX);

    xText = new JTextField(20);
    xText.setFont(DEFAULT_FONT);
    xText.getDocument().addDocumentListener(createDocumentListener(xText));
    panel.add(xText);

    JButton calculateButton = new JButton("Calculate");
    calculateButton.setFont(DEFAULT_FONT);
    calculateButton.setBackground(BUTTON_COLOR);
    calculateButton.setForeground(BUTTON_TEXT_COLOR);
    panel.add(calculateButton);

    JLabel resultLabel = new JLabel("Result:");
    resultLabel.setFont(DEFAULT_FONT);
    panel.add(resultLabel);

    resultText = new JTextField(20);
    resultText.setFont(DEFAULT_FONT);
    resultText.setEditable(false);
    panel.add(resultText);

    calculateButton.addActionListener(e -> {
      try {
        // Check if any input fields are empty
        if (aText.getText().trim().isEmpty()) {
          showMessage(panel, "Please enter a value for 'a'.");
          return;
        }
        if (bText.getText().trim().isEmpty()) {
          showMessage(panel, "Please enter a value for 'b'.");
          return;
        }
        if (xText.getText().trim().isEmpty()) {
          showMessage(panel, "Please enter a value for 'x'.");
          return;
        }

        // Parse inputs
        double a = Double.parseDouble(aText.getText());
        double b = Double.parseDouble(bText.getText());
        double x = Double.parseDouble(xText.getText());

        // Check if b is less than or equal to zero
        if (b <= 0) {
          showMessage(panel, "The base 'b' must be greater than zero.");
          return;
        }

        // Compute result
        double result = computeExponentialFunction(a, b, x);
        resultText.setText(String.valueOf(result));
      } catch (NumberFormatException ex) {
        showMessage(panel, "Invalid input. Please enter numerical values.");
      } catch (ArithmeticException ex) {
        JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(),
                "Calculation Error", JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  /**
   * Creates a DocumentListener for validating input fields.
   *
   * @param textField the JTextField to add the listener to
   * @return a DocumentListener that validates the text field
   */
  private static DocumentListener createDocumentListener(JTextField textField) {
    return new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        validateField(textField);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        validateField(textField);
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        validateField(textField);
      }

      private void validateField(JTextField textField) {
        String text = textField.getText().trim();
        try {
          if (textField == aText || textField == xText) {
            Double.parseDouble(text); // Validate that the input is a number
            textField.setBackground(Color.WHITE);
          } else if (textField == bText) {
            double value = Double.parseDouble(text);
            if (value > 0) {
              textField.setBackground(Color.WHITE);
            } else {
              textField.setBackground(Color.PINK); // Invalid input color
            }
          }
        } catch (NumberFormatException e) {
          textField.setBackground(Color.PINK); // Invalid input color
        }
      }
    };
  }

  /**
   * Shows an error message dialog with a specific message.
   *
   * @param panel the panel to associate the message dialog with
   * @param message the message to display in the dialog
   */
  private static void showMessage(JPanel panel, String message) {
    JOptionPane.showMessageDialog(panel, message, INPUT_ERROR, JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Computes the exponential function a * b^x and handles arithmetic exceptions.
   *
   * @param a the base multiplier
   * @param b the base value
   * @param x the exponent value
   * @return the computed value of a * b^x
   * @throws ArithmeticException if the result is out of range
   */
  public static double computeExponentialFunction(double a, double b, double x)
          throws ArithmeticException {
    double result = a * power(b, x);

    if (Double.isInfinite(result) || Double.isNaN(result)) {
      throw new ArithmeticException("Result is out of the range of double values.");
    }

    return result;
  }

  /**
   * Computes the value of base raised to the power of exponent, accommodating
   * for decimal values in the exponent. This method implements the power
   * function from scratch without using Java's built-in math libraries.
   *
   * @param base     the base value
   * @param exponent the exponent value
   * @return the result of base raised to the power of exponent
   */
  public static double power(double base, double exponent) {
    if (base == 0) {
      return 0;
    }
    if (exponent == 0) {
      return 1;
    }

    double lnBase = log(base);
    double expLnBase = exponent * lnBase;
    return exp(expLnBase);
  }

  /**
   * Computes the natural logarithm of a number using a series expansion.
   *
   * @param x the number to compute the natural logarithm for
   * @return the natural logarithm of x
   */
  public static double log(double x) {
    if (x <= 0) {
      throw new IllegalArgumentException("Logarithm of non-positive numbers is undefined.");
    }

    double result = 0;
    double term = (x - 1) / (x + 1);
    double termSquared = term * term;
    double currentTerm = term;
    int n = 1;

    while (currentTerm > 1e-10) {
      result += currentTerm / n;
      currentTerm *= termSquared;
      n += 2;
    }

    return 2 * result;
  }

  /**
   * Computes the exponential function e^x using a series expansion.
   *
   * @param x the exponent value
   * @return the value of e raised to the power of x
   */
  public static double exp(double x) {
    double result = 1;
    double term = 1;
    int n = 1;

    while (term > 1e-10) {
      term *= x / n;
      result += term;
      n++;
    }

    return result;
  }
}
