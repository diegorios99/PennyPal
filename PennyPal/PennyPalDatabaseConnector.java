import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PennyPalDatabaseConnector {
    private Connection connection;
    private final String url;
    private final String username;
    private final String password;
    private int loggedInUserId;

    public PennyPalDatabaseConnector() {
        this.url = "jdbc:mysql://localhost:3306/pennypal";
        this.username = "root";
        this.password = "Temporary";
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("Error connecting to the database: " + ex.getMessage());
        }
    }

    public void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    public int authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(url, this.username, this.password)) {
            String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);


                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error validating login: " + ex.getMessage());

        }

        return -1;
    }

    public void insertCategory(String categoryName, int monthlyBudget) {
        try {
            String sql = "INSERT INTO categories (user_id, name, budget, spending) VALUES (?, ?, ?, 0)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, loggedInUserId);
                statement.setString(2, categoryName);
                statement.setInt(3, monthlyBudget);
                statement.executeUpdate();
            }
            System.out.println("New category inserted into the database!");
        } catch (SQLException ex) {
            System.out.println("Error inserting category into the database: " + ex.getMessage());
            System.out.println("userid: " + loggedInUserId);
            System.out.println("categoryName: " + categoryName);
            System.out.println("monthlyBudget: " + monthlyBudget);
        }
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        try {
            String sql = "SELECT name FROM categories";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    String categoryName = resultSet.getString("name");
                    categories.add(categoryName);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving categories from the database: " + ex.getMessage());
        }
        return categories;
    }

    public void insertExpense(String categoryName, int expenseAmount) {
        try {
            int categoryId = getCategoryId(categoryName);

            updateCategorySpending(categoryId, expenseAmount); // Update the spending for an existing category

            String sql = "INSERT INTO expenses (user_id, category_id, amount, date) VALUES (?, ?, ?, CURDATE())";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, loggedInUserId);
                statement.setInt(2, categoryId);
                statement.setInt(3, expenseAmount);
                statement.executeUpdate();
            }
            System.out.println("New expense inserted into the database!");
        } catch (SQLException ex) {
            System.out.println("Error inserting expense into the database: " + ex.getMessage());
        }
    }

    private void updateCategorySpending(int categoryId, int expenseAmount) {
        try {
            String sql = "UPDATE categories SET spending = spending + ? WHERE id = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, expenseAmount);
                statement.setInt(2, categoryId);
                statement.setInt(3, loggedInUserId);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Error updating category spending: " + ex.getMessage());
        }
    }



    private int getCategoryId(String categoryName) {
        try {
            String sql = "SELECT id FROM categories WHERE name = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, categoryName);
                statement.setInt(2, loggedInUserId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving category ID: " + ex.getMessage());
        }
        return -1; // Return -1 if category doesn't exist
    }

}
