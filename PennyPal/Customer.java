/**
 * Customer class that h
 */
public class Customer {

    // fields
    private String firstName;
    private String lastName;
    private String userName;

    /**
     * Customer constructor that thats in three String parameters
     * @param firstName String for the first name
     * @param lastName String for the last name
     * @param userName String for the unique username
     */
    public Customer(String firstName, String lastName, String userName){
        firstName = this.firstName;
        lastName = this.lastName;
        userName = this.userName;
    }

    /**
     * first name getter
     * @return return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * last name getter
     * @return return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * username getter
     * @return return the username
     */
    public String getUserName() {
        return userName;
    }


}
