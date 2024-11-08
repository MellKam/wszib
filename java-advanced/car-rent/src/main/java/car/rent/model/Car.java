package car.rent.model;

public class Car {

    private final String brand;
    private final String model;
    private final int registrationNumber;
    private boolean isAvailable;

    public Car(
            int registrationNumber,
            String brand,
            String model,
            boolean isAvailable
    ) {
        this.brand = brand;
        this.model = model;
        this.isAvailable = isAvailable;
        this.registrationNumber = registrationNumber;
    }

    public Car(
            int registrationNumber,
            String brand,
            String model
    ) {
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.isAvailable = true;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean setAvailable(boolean available) {
        return isAvailable = available;
    }

    @Override
    public String toString() {
        String available = isAvailable == true
                ? "(available)"
                : "(not available)";
        return brand + " " + model + " " + registrationNumber + " " + available;
    }
}
