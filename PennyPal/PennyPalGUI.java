import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;




public class PennyPalGUI extends JFrame {
    private final int[] expenses = {450, 870}; // Expenses for previous months
    private JButton createCategoryButton; // Declare createCategoryButton as an instance variable
    private String categoryName; // Category name variable
    private int monthlyBudget; // Monthly budget variable
    private PennyPalDatabaseConnector connector = new PennyPalDatabaseConnector();

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


    /**
     *
     * @return
     */
    private JPanel createLoginPanel() {
        // Create the login panel
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        // Add action listener to login button
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

        return panel;
    }


    /**
     *
      * @return
     */
    private JPanel createMainPanel() {
        // Create the main panel
        JPanel panel = new JPanel(null); // Use null layout for precise positioning
        panel.setBackground(Color.BLACK); // Set background color for the main panel

        // Panel A (Bar Chart)
        JPanel panelA = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBarChart(g);
            }
        };
        panelA.setBounds(0, 0, 800, 200); // Set position and size
        panelA.setBackground(Color.LIGHT_GRAY); // Set background color for panel A
        panel.add(panelA);

        // Panel B (Congrats message)
        JPanel panelB = new JPanel(new BorderLayout());
        panelB.setBackground(Color.WHITE);
        panelB.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        JLabel congratsLabel = new JLabel("Congrats! You spent 30% less than the previous month");
        congratsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelB.add(congratsLabel, BorderLayout.CENTER);
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


    /**
     *
     * @return
     */
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
        JButton budgetButton = new JButton("Set Budget");
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

                    // ...
                } catch (NumberFormatException ex) {
                    // ...
                }
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
                }
            }
        });

        return panelC;
    }

    private void drawBarChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        int barWidth = 50;
        int maxExpense = getMaxExpense();

        for (int i = 0; i < expenses.length; i++) {
            int barHeight = (int) (((double) expenses[i] / maxExpense) * 150);
            int x = 100 + (i * 100);
            int y = 180 - barHeight;

            g2d.fillRect(x, y, barWidth, barHeight);
        }
    }

    private int getMaxExpense() {
        int max = 0;
        for (int expense : expenses) {
            if (expense > max) {
                max = expense;
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
