import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PennyPalGUI extends JFrame {
    private final int[] expenses = {450, 870}; // Expenses for previous months

    // global fields
    private JPanel panel;
    private JPanel panelA;
    private JPanel panelB;
    private JPanel panelC;
    private JPanel addExpensePanel;
    private JTextField amountField;
    private JButton addExpenseButton;
    private JButton viewButton;
    private JButton budgetButton;


    public PennyPalGUI() {
        // Set window title
        setTitle("PennyPal Expense Tracker");

        // Create and add components
        panel = new JPanel(null); // Use null layout for precise positioning
        panel.setBackground(Color.BLACK); // Set background color for the main panel

        // Panel A (Bar Chart)
        panelA = new JPanel() {
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
        panelB = new JPanel(new BorderLayout());
        panelB.setBackground(Color.WHITE);
        panelB.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        JLabel congratsLabel = new JLabel("Congrats! You spent 30% less than the previous month");
        congratsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelB.add(congratsLabel, BorderLayout.CENTER);
        panelB.setBounds(0, 200, 400, 400); // Set position and size
        panel.add(panelB);

        // Panel C (Buttons)
        panelC = new JPanel(new GridLayout(3, 1, 0, 10)); // Vertical alignment with spacing
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
        addExpensePanel = new JPanel(new BorderLayout());

        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(105, 30)); // Set size for text field
        addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setPreferredSize(new Dimension(285, 30)); // Set size for button
        addExpenseButton.addActionListener(new ButtonListener());

        addExpensePanel.add(amountField, BorderLayout.WEST);
        addExpensePanel.add(addExpenseButton, BorderLayout.EAST);
        panelC.add(addExpensePanel);

        viewButton = new JButton("View Expenses");
        viewButton.setBackground(Color.ORANGE);
        viewButton.addActionListener(new ButtonListener());
        budgetButton = new JButton("Set Budget");
        budgetButton.addActionListener(new ButtonListener());
        budgetButton.setBackground(Color.ORANGE);
        panelC.add(viewButton);
        panelC.add(budgetButton);
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

        // Add panel to the frame
        add(panel);

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set window size
        setSize(800, 600);

        // Set visibility
        setVisible(true);
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

    // TODO - ButtonListener inner class
    /**
     * Inner ButtonListner class that implements the ActionListener to get data from the buttons
     */
    private class ButtonListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            /**
             * Using a series of if-else statements, determine what each action is for the buttons on the GUI
             */
            if(e.getSource() == addExpenseButton) {
                // TODO - implement sql statments using the connector/j
            }else if(e.getSource() == viewButton){
                // TODO - implement sql statments using the connector/j
            }else if(e.getSource() == budgetButton){
                // TODO - implement sql statments using the connector/j
            }

        }// end actionPerformed
    }// end ButtonListener
    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PennyPalGUI();
        });
    }

     */
}
