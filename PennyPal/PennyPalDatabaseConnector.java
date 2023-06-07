import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
        this.password = "password";
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("Error connecting to the database: " + ex.getMessage());
        }
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

    public void createUser(String username, String password) {
        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.executeUpdate();
            }
            System.out.println("New user created successfully!");
        } catch (SQLException ex) {
            System.out.println("Error creating user: " + ex.getMessage());
        }
    }

    public void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    public void insertCategory(String categoryName, int monthlyBudget) {
        try {
            int yearId = getYearId(getCurrentYear()); // Replace with your logic to get the current year
            int monthId = getMonthId(getCurrentMonthName(), yearId); // Replace with your logic to get the current month name

            String sql = "INSERT INTO categories (user_id, year_id, month_id, name, budget, spending) VALUES (?, ?, ?, ?, ?, 0)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, loggedInUserId);
                statement.setInt(2, yearId);
                statement.setInt(3, monthId);
                statement.setString(4, categoryName);
                statement.setInt(5, monthlyBudget);
                statement.executeUpdate();
            }
            System.out.println("New category inserted into the database!");

            // Rest of the code
        } catch (SQLException ex) {
            System.out.println("Error inserting category into the database: " + ex.getMessage());
        }
    }

    public int getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear();
    }

    public String getCurrentMonthName() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");

        System.out.println(currentDate.format(monthFormatter));
        return currentDate.format(monthFormatter);
    }

    private int getYearId(int currentYear) {
        try {
            String sql = "SELECT id FROM years WHERE year_number = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, currentYear);
                statement.setInt(2, this.loggedInUserId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        // Year doesn't exist in the database for the user, so insert it
                        String insertSql = "INSERT INTO years (year_number, user_id) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                            insertStatement.setInt(1, currentYear);
                            insertStatement.setInt(2, this.loggedInUserId);
                            insertStatement.executeUpdate();
                            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    return generatedKeys.getInt(1);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving/inserting year ID: " + ex.getMessage());
        }
        return -1; // Return -1 if year retrieval/insertion fails
    }

    public int getMonthId(String monthName, int year) {
        try {
            int yearId = getYearId(year); // Get the year ID for the given year and user ID

            String sql = "SELECT id FROM months WHERE name = ? AND year_id = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, monthName);
                statement.setInt(2, yearId);
                statement.setInt(3, this.loggedInUserId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        // Month doesn't exist in the database, so insert it
                        String insertSql = "INSERT INTO months (name, year_id, user_id) VALUES (?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                            insertStatement.setString(1, monthName);
                            insertStatement.setInt(2, yearId);
                            insertStatement.setInt(3, this.loggedInUserId);
                            insertStatement.executeUpdate();
                            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    return generatedKeys.getInt(1);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving/inserting month ID: " + ex.getMessage());
        }
        return -1; // Return -1 if month retrieval/insertion fails
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

    private int getCategoryId(String categoryName) {
        try {
            int yearId = getYearId(getCurrentYear());
            int monthId = getMonthId(getCurrentMonthName(), yearId);

            String sql = "SELECT id FROM categories WHERE name = ? AND user_id = ? AND month_id = ? AND year_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, categoryName);
                statement.setInt(2, loggedInUserId);
                statement.setInt(3, monthId);
                statement.setInt(4, yearId);

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

    public void insertExpense(String categoryName, int expenseAmount) {
        try {
            int categoryId = getCategoryId(categoryName);

            if (categoryId == -1) {
                insertCategory(categoryName, getMonthlyBudget(categoryName));

                categoryId = getCategoryId(categoryName);
                System.out.println("Category updated from the last month");
            }

            int yearId = getYearId(getCurrentYear()); // Get the year ID for the current year
            int monthId = getMonthId(getCurrentMonthName(), yearId); // Get the month ID for the current month

            String sql = "INSERT INTO expenses (user_id, category_id, month_id, year_id, amount, date) VALUES (?, ?, ?, ?, ?, CURDATE())";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, this.loggedInUserId);
                statement.setInt(2, categoryId);
                statement.setInt(3, monthId);
                statement.setInt(4, yearId);
                statement.setInt(5, expenseAmount);
                statement.executeUpdate();
            }

            // Update category spending
            sql = "UPDATE categories SET spending = spending + ? WHERE id = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, expenseAmount);
                statement.setInt(2, categoryId);
                statement.setInt(3, loggedInUserId);
                statement.executeUpdate();
            }

            // Update monthly expenses
            sql = "UPDATE months SET monthly_expenses = monthly_expenses + ? WHERE id = ? AND user_id = ? AND year_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, expenseAmount);
                statement.setInt(2, monthId);
                statement.setInt(3, loggedInUserId);
                statement.setInt(4, yearId);
                statement.executeUpdate();
            }

            System.out.println("New expense inserted into the database!");
        } catch (SQLException ex) {
            System.out.println("Error inserting expense into the database: " + ex.getMessage());
        }
    }


    public int getMonthlyBudget(String categoryName) {
        try {
            String sql = "SELECT budget FROM categories WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, categoryName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("budget");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving monthly budget: " + ex.getMessage());
        }
        return -1; // Return -1 if budget retrieval fails or category doesn't exist
    }

    public void updateBudget(String categoryName, int newBudget) {
        try {
            String sql = "UPDATE categories SET budget = ? WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, newBudget);
                statement.setString(2, categoryName);
                statement.executeUpdate();
                System.out.println("Budget updated successfully!");
            }
        } catch (SQLException ex) {
            System.out.println("Error updating budget: " + ex.getMessage());
        }
    }

    public int getMonthlyExpense(int monthId) {
        try {
            String sql = "SELECT monthly_expenses FROM months WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, monthId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("monthly_expenses");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving monthly expense: " + ex.getMessage());
        }
        return 0; // Return 0 if monthly expense retrieval fails or no record found
    }


    public String getMonthName(int monthIndex) {
        switch (monthIndex) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
            default: return "Invalid Month";
        }
    }

    public int[] getTwelveExpenses(){
        int[] expenses = new int[12];

        int currentYear = getCurrentYear();
        int currentMonthIndex = getMonthId(getCurrentMonthName(), currentYear); // Get the month index for the current month and year

        for (int i = 0; i < 12; i++) {
            int previousMonthIndex = (currentMonthIndex - i - 1) % 12; // Calculate the index of the previous month (0-11) based on the current month and the loop index
            if (previousMonthIndex < 0) {
                currentYear--;
                previousMonthIndex += 12; // Handle negative indices to wrap around to the correct month
            }

            int previousMonthYear = (previousMonthIndex == 11) ? currentYear - 1 : currentYear; // Adjust the year for the previous month if it's December

            int monthId = getMonthId(getMonthName(previousMonthIndex), previousMonthYear); // Get the month ID for the previous month and year

            int barHeight = getMonthlyExpense(monthId); // Get the monthly expense for the month ID
            expenses[i] = barHeight; // Store the monthly expense in the array
        }

        // Print the monthly expenses for verification
        for (int expense : expenses) {
            System.out.println(expense);
        }

        return expenses;
    }

    public String getReport(){
        // Assuming the variables are available:
        int totalBudget = getTotalBudget();
        int currentSpending = getCurrentExpenses();
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Get the total days in the current month
        int totalDays = YearMonth.from(currentDate).lengthOfMonth();
        // Get the current day of the month
        int currentDay = currentDate.getDayOfMonth();

        double expectedExpensePerDay = ((double) totalBudget) / totalDays;
        double totalExpectedExpense = ((double) totalBudget) / totalDays * currentDay;

        int daysDifference = (int) Math.round((totalExpectedExpense - currentSpending)/expectedExpensePerDay);

        String message;

        if (daysDifference == 0) {
            // Within budget or on track
            message = "Congrats! You are within your budget. You are perfectly on track.";
        } else if (daysDifference < 0){
            // Exceeded budget
            message = "You are not doing a good job. \nYou are " + (-1 * daysDifference) + " days ahead on your expenses.";
        }else{
            // No spending so far
            message = "Congrats! You are " + daysDifference + " days less spendy. \nYou're looking great!";
        }

        return message;

    }

    public int getTotalBudget() {
        int totalBudget = 0;

        try {
            int yearId = getYearId(getCurrentYear());
            int monthId = getMonthId(getCurrentMonthName(), yearId);

            String sql = "SELECT SUM(budget) AS total_budget FROM categories WHERE month_id = ? AND year_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, monthId);
                statement.setInt(2, yearId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        totalBudget = resultSet.getInt("total_budget");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving total budget from the database: " + ex.getMessage());
        }

        return totalBudget;
    }

    public int getCurrentExpenses() {
        int currentExpenses = 0;

        try {
            int yearId = getYearId(getCurrentYear());
            int monthId = getMonthId(getCurrentMonthName(), yearId);

            String sql = "SELECT SUM(spending) AS total_spending FROM categories WHERE month_id = ? AND year_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, monthId);
                statement.setInt(2, yearId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        currentExpenses = resultSet.getInt("total_spending");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving current expenses from the database: " + ex.getMessage());
        }

        return currentExpenses;
    }
}
