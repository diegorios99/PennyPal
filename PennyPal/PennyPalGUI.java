import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PennyPalGUI extends JFrame {
    private int[] expenses; // Expenses for previous months
    private JButton createCategoryButton; // Declare createCategoryButton as an instance variable
    private String categoryName; // Category name variable
    private int monthlyBudget; // Monthly budget variable
    private PennyPalDatabaseConnector connector = new PennyPalDatabaseConnector();
    private JPanel panelA; // Declare panelA as an instance variable
    private JPanel panelB; // Declare panelB as an instance variable
    private JLabel reportLabel; // Spending report variable
    private boolean isViewButtonClicked = false; // Flag to track if viewButton is clicked


    public PennyPalGUI(List<String> categories) {
        // Set window title
        setTitle("PennyPal Expense Tracker");

        // Create and add components for the login GUI
        JPanel loginPanel = createLoginPanel();
        add(loginPanel);

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window size
        setSize(800, 600);

        // Set visibility
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        // Create the login panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Create and add the logo
        ImageIcon pennyPalIcon = new ImageIcon("PennyPal/images/PennyPal.png");
        Image pennyPalImage = pennyPalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(pennyPalImage));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(logoLabel, constraints);

        // Create and add the username label and field
        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(usernameLabel, constraints);

        JTextField usernameField = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usernameField, constraints);

        // Create and add the password label and field
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField();
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordField, constraints);

        // Create and add the login button
        JButton loginButton = new JButton("Login");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        // Create and add the create account button
        JButton createAccountButton = new JButton("Create Account");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(createAccountButton, constraints);

        // Add action listeners
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            int userId =  connector.authenticateUser(username, password);
            // Validate the login credentials
            if (userId > 0) {
                // Create an instance of the database connector class
                connector = new PennyPalDatabaseConnector();

                // Set the loggedInUserId in the connector instance
                connector.setLoggedInUserId(userId);

                // Remove the login panel and create the main panel
                remove(panel);
                JPanel mainPanel = createMainPanel();
                add(mainPanel);

                // Refresh the frame to reflect the changes
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the create account dialog
                openCreateAccountDialog();
            }
        });

        return panel;
    }


    private JPanel createMainPanel() {
        // Create the main panel
        JPanel panel = new JPanel(null); // Use null layout for precise positioning
        panel.setBackground(Color.BLACK); // Set background color for the main panel

        // Panel A (Bar Chart)
        JPanel panelA = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isViewButtonClicked) {
                    drawBarChart(g);
                } else {
                    drawWelcomeMessage(g);
                }
            }
        };
        panelA.setBounds(0, 0, 800, 200); // Set position and size
        panelA.setBackground(Color.LIGHT_GRAY); // Set background color for panel A
        panel.add(panelA);
        this.panelA = panelA; // Store panelA as an instance variable

        // Panel B (Congrats message)
        panelB = new JPanel(new BorderLayout());
        panelB.setBackground(Color.WHITE);
        panelB.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        reportLabel = new JLabel(connector.getReport());
        reportLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelB.add(reportLabel, BorderLayout.CENTER);
        panelB.setBounds(0, 200, 400, 400); // Set position and size
        panel.add(panelB);

        // Panel C (Buttons)
        JPanel panelC = createButtonPanel();
        panel.add(panelC);

        // Separator lines
        JSeparator separator1 = new JSeparator();
        separator1.setBounds(0, 200, 800, 2); // Set position and size
        separator1.setForeground(Color.BLACK);
        panel.add(separator1);

        JSeparator separator2 = new JSeparator();
        separator2.setOrientation(SwingConstants.VERTICAL);
        separator2.setBounds(400, 200, 2, 400); // Set position and size
        separator2.setForeground(Color.BLACK);
        panel.add(separator2);

        JSeparator separator3 = new JSeparator();
        separator3.setBounds(0, 600, 800, 2); // Set position and size
        separator3.setForeground(Color.BLACK);
        panel.add(separator3);

        return panel;
    }

    private JPanel createButtonPanel() {
        // Create the button panel
        JPanel panelC = new JPanel(new GridLayout(4, 1, 0, 10)); // Vertical alignment with spacing
        panelC.setBackground(Color.WHITE);
        panelC.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelC.setBounds(400, 200, 400, 400); // Set position and size

        // Styling for buttons
        UIManager.put("Button.arc", 15);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Button.border", new LineBorder(Color.BLACK, 2));
        UIManager.put("Button.focus", Color.WHITE);

        // Add Expense components
        JPanel addExpensePanel = new JPanel(new BorderLayout());

        JTextField amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(105, 30)); // Set size for text field
        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setPreferredSize(new Dimension(285, 30)); // Set size for button

        addExpensePanel.add(amountField, BorderLayout.WEST);
        addExpensePanel.add(addExpenseButton, BorderLayout.EAST);
        panelC.add(addExpensePanel);

        createCategoryButton = new JButton("Create Category"); // Initialize createCategoryButton
        createCategoryButton.setBackground(Color.ORANGE);
        panelC.add(createCategoryButton);

        JButton viewButton = new JButton("View Expenses");
        viewButton.setBackground(Color.ORANGE);
        JButton budgetButton = new JButton("Change Budget");
        budgetButton.setBackground(Color.ORANGE);
        panelC.add(viewButton);
        panelC.add(budgetButton);

        // Add action listener to "Create Category" button
        createCategoryButton.addActionListener(e -> {
            // Display input dialogs to get the category name and monthly budget
            String categoryName = JOptionPane.showInputDialog(this, "Enter category name:");
            String budgetString = JOptionPane.showInputDialog(this, "Enter monthly budget:");

            // Check if a category name and budget were entered
            if (categoryName != null && !categoryName.isEmpty() && budgetString != null && !budgetString.isEmpty()) {
                try {
                    int monthlyBudget = Integer.parseInt(budgetString);
                    connector.insertCategory(categoryName, monthlyBudget);
                } catch (NumberFormatException ex) {}
            }
        });

        addExpenseButton.addActionListener(e -> {
            // Get the expense amount from the amountField
            String expenseAmount = amountField.getText();

            // Check if the expense amount is not empty
            if (!expenseAmount.isEmpty()) {
                // Retrieve the existing categories from the database
                List<String> existingCategories = connector.getCategories();

                // Display input dialog to get the selected category
                Object selectedCategory = JOptionPane.showInputDialog(
                        this, "Select a category:", "Choose Category",
                        JOptionPane.QUESTION_MESSAGE, null, existingCategories.toArray(), existingCategories.get(0));

                // Check if a category was selected
                if (selectedCategory != null) {
                    String selectedCategoryName = selectedCategory.toString();

                    // Perform any additional processing with the selected category and expense amount
                    // For now, let's just print them to the console
                    System.out.println("Expense amount: " + expenseAmount);
                    System.out.println("Selected category: " + selectedCategoryName);

                    // Add the expense to the selected category in the database
                    connector.insertExpense(selectedCategoryName, Integer.parseInt(expenseAmount));

                    // Update the congrats message and repaint panelB
                    reportLabel.setText(connector.getReport());
                    panelB.repaint();
                }
            }
        });

        budgetButton.addActionListener(e -> {
            // Retrieve the existing categories from the database
            List<String> existingCategories = connector.getCategories();

            // Display input dialog to get the selected category
            Object selectedCategory = JOptionPane.showInputDialog(
                    this, "Select a category:", "Choose Category",
                    JOptionPane.QUESTION_MESSAGE, null, existingCategories.toArray(), existingCategories.get(0));

            // Check if a category was selected
            if (selectedCategory != null) {
                String selectedCategoryName = selectedCategory.toString();

                // Retrieve the current monthly budget for the selected category
                int currentBudget = connector.getMonthlyBudget(selectedCategoryName);

                // Display a custom dialog to update the budget
                JTextField budgetField = new JTextField(String.valueOf(currentBudget));
                JPanel panel = new JPanel(new GridLayout(2, 1));
                panel.add(new JLabel("Current Monthly Budget: " + currentBudget));
                panel.add(budgetField);

                int option = JOptionPane.showConfirmDialog(
                        this, panel, "Change Budget", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    // Retrieve the new desired budget from the text field
                    String newBudgetString = budgetField.getText();

                    try {
                        int newBudget = Integer.parseInt(newBudgetString);

                        // Update the budget for the selected category in the database
                        connector.updateBudget(selectedCategoryName, newBudget);

                        // Show a confirmation message
                        JOptionPane.showMessageDialog(
                                this, "Budget updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        // Handle invalid budget input
                        JOptionPane.showMessageDialog(
                                this, "Invalid budget value. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        viewButton.setBackground(Color.ORANGE);
        viewButton.addActionListener(e -> {
            isViewButtonClicked = true;
            panelA.repaint();
        });


        return panelC;
    }

    private void openCreateAccountDialog() {
        // Create a dialog to enter the new account details
        String username = JOptionPane.showInputDialog(this, "Enter username:");
        String password = JOptionPane.showInputDialog(this, "Enter password:");

        // Create a new user in the database
        connector.createUser(username, password);
    }

    private void drawWelcomeMessage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Welcome!", 350, 100);
    }

    private void drawBarChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        int numBars = 3;
        int barWidth = Math.min(150, (getWidth() - (numBars - 1) * 25) / numBars);
        int barSpacing = barWidth / 2;

        expenses = connector.getTwelveExpenses();

        int[] flippedExpenses = new int[expenses.length];
        for (int i = 0; i < expenses.length; i++) {
            flippedExpenses[i] = expenses[expenses.length - 1 - i];
        }

        int firstNonZeroIndex = 0;
        while (firstNonZeroIndex < flippedExpenses.length && flippedExpenses[firstNonZeroIndex] == 0) {
            firstNonZeroIndex++;
        }

        int[] trimmedExpenses = Arrays.copyOfRange(flippedExpenses, firstNonZeroIndex, flippedExpenses.length);

        for (int i = 0; i < numBars; i++) {
            if (i < trimmedExpenses.length) {
                int x = 100 + (i * (barWidth + barSpacing));
                int y = getHeight() - 20 - (int) ((double) trimmedExpenses[i] / getMaxExpense() * (getHeight() - 40));

                drawBar(g2d, x, y, barWidth, trimmedExpenses[i], connector.getMonthName(i), getHeight());
            }
        }
    }



    private void drawBar(Graphics2D g2d, int x, int y, int barWidth, int barHeight, String monthName, int chartHeight) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y, barWidth, barHeight);

        // Write month name below the bar
        Font font = new Font("Arial", Font.PLAIN, 12);
        g2d.setFont(font);
        g2d.setColor(Color.RED);

        String expenseText = String.valueOf(barHeight);
//
//        int stringWidth = g2d.getFontMetrics().stringWidth(expenseText);
//        int stringX = x + (barWidth - stringWidth) / 2;
//        int stringY = y + barHeight + 15; // Position text below the bar
//
//        g2d.drawString(monthName, stringX, stringY);

        // Write total expense above the bar
        int expenseStringWidth = g2d.getFontMetrics().stringWidth(expenseText);
        int expenseStringX = x + (barWidth - expenseStringWidth) / 4;
        int expenseStringY = y - 5;

        g2d.drawString(monthName + "($" + expenseText + ")", expenseStringX, expenseStringY);
    }


    private int getMaxExpense() {
        if (expenses.length == 0) {
            return 0;
        }

        int max = expenses[0];
        for (int i = 1; i < expenses.length; i++) {
            if (expenses[i] > max) {
                max = expenses[i];
            }
        }
        return max;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<String> categories = new ArrayList<>(); // Populate this list with existing categories
            new PennyPalGUI(categories);
        });
    }
}
