// Imports needed for the program
import javax.swing.*;
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



    /**
     * Main method that starts the program
     */
    public static void main(String[] args) {
        PennyPal pennyPal = new PennyPal();
        pennyPal.connect();
    }

    /**
     * Connect method is used to connect to the MySQL database
     */
    public void connect(){
        // information needed to gain access to the MySQL server
        String url = "jdbc:mysql://localhost:3306/pennypal";
        String userName = "root";
        String pass = "Qpmzasdf11!";

        // try to connect
        try{
            con = DriverManager.getConnection(url,userName,pass);
            System.out.println("Connected");
        }catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Constructor for the PennyPal GUI
     */
    public void PennyPal(){


    }

    /**
     * BuildPanel is used to put all the components on the panel
     * and connect them with an ActionLister.
     */
    public void buildPanel(){


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

        }
    }

}
