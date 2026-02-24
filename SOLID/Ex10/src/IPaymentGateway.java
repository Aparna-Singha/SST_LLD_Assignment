public interface IPaymentGateway {
    public String charge(String studentId, double amount);
}
