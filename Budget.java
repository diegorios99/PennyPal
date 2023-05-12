/**
 * Budget --- An object that will act as a budget with its own category name, limit, and
 * amount to track how close you are to the limit set.
 * @author Eduardo Villalobos
 */
public class Budget {
    /**
     * The name of the budget category.
     */
    private String budgetCategory;
    /**
     * The limit of the budget.
     */
    private int budgetLimit;
    /**
     * The amount of money currently spent towards the budget.
     */
    private int budgetAmount;

    /**
     * The constructor for the Budget class. User input dictates the name of the
     * category and the limit of the budget. The budget amount is automatically set to 0.
     * @param cat The name of the Budget Category.
     * @param limit The limit for the Budget
     */
    public Budget(String cat, int limit){
        budgetCategory = cat;
        budgetLimit = limit;
        budgetAmount = 0;
    }

    /**
     * A method to change the name of the budget category;
     * @param cat The name that will replace the current category name.
     */
    public void changeCategory(String cat){
        budgetCategory = cat;
        //insert line that updates category
    }

    /**
     * A method to increase the limit of the budget.
     * @param limit The new limit of the budget.
     */
    public void increaseLimit(int limit){
        budgetLimit = limit;
        //insert line that displays the increase in budget limit
    }

    /**
     * A method to decrease the limit of the budget.
     * @param limit The new limit of the budget.
     */
    public void decreaseLimit(int limit){
        budgetLimit = limit;
        //insert line that displays the decrease in budget limit
    }

    /**
     * A method to add to the amount that has been spent towards the budget.
     * @param amount The amount that will be added to the total budget amount.
     */
    public void addToAmount(int amount){
        budgetAmount+=amount;
        if(budgetAmount > this.budgetLimit){
            //print to screen a warning of the budget limit being exceeded.
        }
    }

    /**
     * A method that prints out the summary of the budget for the user.
     */
    public void summary(){
        //Add print statements that display the budget category, budget limit, and current budget amount
        //Add print statement that warns the user if they have exceeded the limit or congratulates user for staying under limit
    }

    /**
     * A method that returns the budgetCategory.
     * @return budgetCategory
     */
    public String getBudgetCategory(){
        return budgetCategory;
    }

    /**
     * A method that returns the budgetLimit.
     * @return budgetLimit
     */
    public int getBudgetLimit(){
        return budgetLimit;
    }

    /**
     * A method that returns the budgetAmount.
     * @return budgetAmount
     */
    public int getBudgetAmount(){
        return budgetAmount;
    }

}
