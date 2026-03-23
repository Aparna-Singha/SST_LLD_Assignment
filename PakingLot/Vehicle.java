public class Vehicle {
    private final String vehicleNumber;
    private final VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }

        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }

        this.vehicleNumber = vehicleNumber.trim();
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle{" + "vehicleNumber='" + vehicleNumber + '\'' + ", vehicleType=" + vehicleType + '}';
    }
}
