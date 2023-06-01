// Imports needed for the program

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

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
                    //mainPanel();


                    panel.updateUI();

                   // panel.removeAll();
                    SwingUtilities.invokeLater(() -> {
                        new PennyPalGUI();
                    });


                   //panel.removeAll();
                    //mainPanel();
                   // panel.updateUI();

                }else{
                    result.setText("Invalid Information!");
                }
            }
        }

    }
}// end PennyPal