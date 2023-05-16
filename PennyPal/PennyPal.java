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

    // Sub Panels
    private JPanel row1 = new JPanel();
    private JPanel row2 = new JPanel();
    private JPanel row3 = new JPanel();
    private JPanel row4 = new JPanel();

    // Components for login page
    private JLabel          loginPage;
    private JLabel          usernameLabel;
    private JLabel          passwordLabel;
    private JTextField      usernameTF;
    private JTextField      passwordTF;
    private JButton         loginButton;


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
        buildPanel();
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
    public void buildPanel(){
        // Adding the components to the panel
        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // scroll pane
        JTextArea textArea = new JTextArea(5,20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);

        // sub panels
        row1 = new JPanel();

        // labels and textFields
        loginPage = new JLabel("Login");

        /** #################################
         * Add components to the rows, then add the rows to the panel
         */

        row1.add(loginPage);


        // formatting for the rows
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(0,4)));

        // add the rows to the panel
        panel.add(row1);
        panel.add(row2);
        panel.add(row3);
        panel.add(row4);
    }// end buildPanel

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
