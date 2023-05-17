// Imports needed for the program
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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

    // Main Panel
    private JPanel panel;



    // Components for main page
    private JLabel          dataVis;
    private JLabel          reports;
    private JLabel          expenses;

    private JTextField      addExpenseTF;
    private JButton         addExpenseBtn;
    private JButton         viewSpendingBtn;
    private JButton         changeCategoryBtn;


    /**
     * Main method that starts the program
     */
    public static void main(String[] args) {
        PennyPal pennyPal = new PennyPal();
        //pennyPal.connect();
    }// end main

    /**
     * Connect method is used to connect to the MySQL database
     */
    public void connect(){
        // information needed to gain access to the MySQL server
        String url = "jdbc:mysql://localhost:3306/pennypal";
        String userName = "root";
        String pass = "";

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
        frame.setSize(400,400);
        frame.setResizable(false);
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

        // Components for login page
        JLabel loginPage = new JLabel("Login");;
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        JTextField usernameTF = new JTextField(10);
        JTextField passwordTF = new JTextField(10);
        JButton loginButton = new JButton("Login");

        // add LoginListener
        loginButton.addActionListener(new LoginListener());

        // add components to the rows
        row1.add(loginPage);
        row2.add(usernameLabel);
        row2.add(usernameTF);
        row3.add(passwordLabel);
        row3.add(passwordTF);
        row4.add(loginButton);

        // formatting for the rows
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0,1)));

        // add the rows to the panel
        panel.add(row1);
        panel.add(row2);
        panel.add(row3);
        panel.add(row4);
    }// end loginPanel

    /**
     * mainPanel method is going to hold the
     * actual application with it's components
     */
    public void mainPanel(){
        
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

        }

    }

    /**
     * Inner ButtonListner class that implements the ActionListener to get data from the buttons
     */
    private class ButtonListener implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }// end actionPerformed
    }// end ButtonListener

}// end PennyPal
