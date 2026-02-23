public class FakeEligibilityStore implements EligibilityEngineCalculator {
    @Override
    public void save(String roll, String status) {
        System.out.println("Saved evaluation for roll=" + roll);
    }
}
