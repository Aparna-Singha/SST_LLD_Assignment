public interface  ITreasurerTool {
    void addIncome(double amt, String note);
    void addExpense(double amt, String note);
    int getEventsCount();
}
