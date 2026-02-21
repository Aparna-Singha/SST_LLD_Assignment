

public class StudentDiscount {
    public static  void discountAmount(String customerType,double subtotal) {
        // hard-coded policy (smell)
        if (subtotal >= 180.0){
            System.out.println(10);
        }
        else{
            System.out.println(0);
        }
    }
}