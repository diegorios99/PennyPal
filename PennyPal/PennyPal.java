// Imports needed for the program

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Finance tracking app.
 * 
 * @authors: Diego Rios, Eduardo Villalobos, Ahmed Issa, Bishal Kshetri
 */
public class PennyPal extends JFrame {

    // Fields
    Connection con;

    // Frame
    private JFrame frame = new JFrame("PennyPal");

    // Panel
    private JPanel panel;

    // Global fields
    private JButton loginButton;
    private JTextField usernameTF;
    private JPasswordField passwordF;
    private JLabel result;

    private JButton addExpenseBtn;
    private JButton viewSpendingBtn;
    private JButton changeCategoryBtn;

    // fields for PennyPalGUI
    private final int[] expenses = {450, 870}; // Expenses for previous months
    //private JPanel panel;
    private JPanel panelA;
    private JPanel panelB;
    private JPanel panelC;
    private JPanel addExpensePanel;
    private JTextField amountField;
    private JButton addExpenseButton;
    private JButton viewButton;
    private JButton budgetButton;

    /**
     * Main method that starts the program
     */
    public static void main(String[] args) {
        PennyPal pennyPal = new PennyPal();
        pennyPal.connect();
    }// end main

    /**
     * Connect method is used to connect to the MySQL database
     */
    public void connect(){
        // information needed to gain access to the MySQL server
        String url = "jdbc:mysql://localhost:3306/pennypal";
        String userName = "root";
        String pass = "password";

        // try to connect
        try{
            con = DriverManager.getConnection(url,userName,pass);
            System.out.println("Connected");
        }catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }//end try-catch
    }// end connect

    /**
     * Constructor for the PennyPal GUI
     */
    public PennyPal(){
        loginPanel();
        frame.add(panel);
        frame.setSize(1280,720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }// end PennyPal

    /**
     * BuildPanel is used to put all the components on the panel
     * and connect them with an ActionLister.
     */
    public void loginPanel(){
        // Adding the components to the panel
        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Sub Panels
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();
        JPanel row5 = new JPanel();

        // Components for login page
        JLabel loginPage = new JLabel("Login");;
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        usernameTF = new JTextField(10);
        passwordF = new JPasswordField(10);
        loginButton = new JButton("Login");
        result = new JLabel("");

        // add LoginListener
        loginButton.addActionListener(new LoginListener());

        // add components to the rows
        row1.add(loginPage);
        row2.add(usernameLabel);
        row2.add(usernameTF);
        row3.add(passwordLabel);
        row3.add(passwordF);
        row4.add(loginButton);
        row5.add(result);

        // formatting for the rows
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0,1)));

        // add the rows to the panel
        panel.add(row1);
        panel.add(row2);
        panel.add(row3);
        panel.add(row4);
        panel.add(row5);
    }// end loginPanel

    public void pennyPalGUI() {
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

    /**
     * Inner LoginListner class that is for the login page
     */
    private class LoginListener implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // reset the result of the login
            result.setText("");

            /**
             * This part can be rewritten to grab username and passwords from
             * the MySQL server rather than a hardcoded username and password
             */
            // if the username and password match

            if(e.getSource() == loginButton){
                if(usernameTF.getText().equals("Admin") && passwordF.getText().equals("Pass")){

                   panel.removeAll();
                   pennyPalGUI();
                   panel.updateUI();

                }else{
                    result.setText("Invalid Information!");
                }
            }
        }

    }// end LoginListener inner class

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
                // fortmat: `pennypal`.`table` (`attribute` , `attribute`)
                String insert = "INSERT INTO `pennypal`.`` (``,``) ";

                // format: '[value]' , '[value]'
                String values = "VALUES(" + "'" + "'" +
                                ");";

                String queery = insert + values;
                try{
                    Statement statement = con.createStatement();
                    //statement.executeUpdate(queery);
                }catch(SQLException throwables){
                    System.out.printf(throwables.getMessage());
                }


            }else if(e.getSource() == viewButton){
                // TODO - implement sql statments using the connector/j
            }else if(e.getSource() == budgetButton){
                // TODO - implement sql statments using the connector/j
            }

        }// end actionPerformed
    }// end ButtonListener

}// end PennyPal