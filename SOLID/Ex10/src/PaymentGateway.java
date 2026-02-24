public class PaymentGateway implements IPaymentGateway{

    @Override
    public String charge(String studentId, double amount) {
        // fake deterministic txn
        return "TXN-9001";
    }
    
}
