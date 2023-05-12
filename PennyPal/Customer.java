/**
 * Customer class that is used to create a new Customer
 * 
 * @author Diego Rios
 */
public class Customer {

    // fields
    private String firstName;
    private String lastName;
    private String userName;

    /**
     * Customer constructor that thats in three String parameters
     * 
     * @param firstName String for the first name
     * @param lastName  String for the last name
     * @param userName  String for the unique username
     */
    public Customer(String firstName, String lastName, String userName) {
        firstName = this.firstName;
        lastName = this.lastName;
        userName = this.userName;
    }

    /**
     * first name getter
     * 
     * @return return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * last name getter
     * 
     * @return return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * username getter
     * 
     * @return return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * firstname setter method (can be used if the customer wants to change their
     * name)
     * 
     * @param firstName takes in a new firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * lastName setter method (can be used if the customer wants to change their
     * name)
     * 
     * @param lastName takes in a new lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * userName setter method (can be used if the customer wants to change their
     * userName)
     * 
     * @param userName takes in a new userName String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Override the toString() to Print out the Customers information
     * 
     * @return A String is returned with the information about the Customer
     */
    @Override
    public String toString() {

        return "Customer: " + firstName + " " + lastName + " " +
                "Username: " + userName;
    }
}
