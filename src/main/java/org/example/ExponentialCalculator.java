package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * This class represents a simple Swing-based application that calculates the
 * value of the expression a * b^x using user-provided inputs for a, b, and x.
 */
public class ExponentialCalculator {
  /**
   * Text field for inputting the value of 'a'.
   */
  private static JTextField aText;

  /**
   * Text field for inputting the value of 'b'.
   */
  private static JTextField bText;

  /**
   * Text field for inputting the value of 'x'.
   */
  private static JTextField xText;

  /**
   * Text field for displaying the result of the calculation.
   */
  private static JTextField resultText;

  private static final String INPUT_ERROR = "Input Error";

  // Private constructor to prevent instantiation
  private ExponentialCalculator() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * The entry point of the application. Initializes the user interface,
   * creates a JFrame window, and adds a JPanel with components to the frame.
   * <p>
   * This method sets up the main window of the application with the title "Function: ab^x".
   * It configures the window size and close operation, adds a panel to the frame,
   * and then places components on the panel. Finally, it makes the window visible.
   * </p>
   *
   * @param args an array of command-line arguments passed to the application
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Function: ab^x");
    frame.setSize(400, 300);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    frame.add(panel);
    placeComponents(panel);

    frame.setVisible(true);
  }

  /**
   * Places the components (labels, text fields, button) on the panel.
   *
   * @param panel the panel to place components on
   */
  private static void placeComponents(final JPanel panel) {
    panel.setLayout(null);

    JLabel labelA = new JLabel("Enter the value for a:");
    labelA.setBounds(10, 20, 150, 25);
    panel.add(labelA);

    aText = new JTextField(20);
    aText.setBounds(180, 20, 165, 25);
    panel.add(aText);

    JLabel labelB = new JLabel("Enter the value for b:");
    labelB.setBounds(10, 50, 150, 25);
    panel.add(labelB);

    bText = new JTextField(20);
    bText.setBounds(180, 50, 165, 25);
    panel.add(bText);

    JLabel labelX = new JLabel("Enter the value for x:");
    labelX.setBounds(10, 80, 150, 25);
    panel.add(labelX);

    xText = new JTextField(20);
    xText.setBounds(180, 80, 165, 25);
    panel.add(xText);

    JButton calculateButton = new JButton("Calculate");
    calculateButton.setBounds(10, 110, 150, 25);
    panel.add(calculateButton);

    JLabel resultLabel = new JLabel("Result:");
    resultLabel.setBounds(10, 140, 150, 25);
    panel.add(resultLabel);

    resultText = new JTextField(20);
    resultText.setBounds(180, 140, 165, 25);
    panel.add(resultText);

    calculateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          // Check if any input fields are empty
          if (aText.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter a value for 'a'.", INPUT_ERROR,
                    JOptionPane.WARNING_MESSAGE);
            return;
          }
          if (bText.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter a value for 'b'.",
                    INPUT_ERROR, JOptionPane.WARNING_MESSAGE);
            return;
          }
          if (xText.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter a value for 'x'.",
                    INPUT_ERROR, JOptionPane.WARNING_MESSAGE);
            return;
          }

          // Parse inputs
          double a = Double.parseDouble(aText.getText());
          double b = Double.parseDouble(bText.getText());
          double x = Double.parseDouble(xText.getText());

          // Check if b is less than or equal to zero
          if (b <= 0) {
            JOptionPane.showMessageDialog(panel, "The base 'b' must be greater than zero.",
                    INPUT_ERROR, JOptionPane.WARNING_MESSAGE);
            return;
          }

          // Compute result
          double result = computeExponentialFunction(a, b, x);
          resultText.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(panel, "Invalid input. Please enter numerical values.",
                  INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
          JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(),
                  "Calculation Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
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
  public static double computeExponentialFunction(double a, double b, double x) throws
          ArithmeticException {
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

    // Calculate natural log of base
    double lnBase = log(base);

    // Calculate exponent * ln(base)
    double expLnBase = exponent * lnBase;

    // Calculate e^(expLnBase)
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
      throw new IllegalArgumentException(
              "Logarithm of non-positive numbers is undefined."
      );
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
