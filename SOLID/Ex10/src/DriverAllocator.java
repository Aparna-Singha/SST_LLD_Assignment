public class DriverAllocator implements IDriverAllocator{

    @Override
    public String allocate(String studentId) {
        // fake deterministic driver
        return "DRV-17";
    }
    
}
