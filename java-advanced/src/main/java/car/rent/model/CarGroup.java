package car.rent.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CarGroup {

    private final ArrayList<Car> cars;

    public CarGroup(ArrayList<Car> cars) {
        this.cars = new ArrayList<>();
    }

    public int getAvailableCarsCount() {
        int availableCars = 0;
        for (Car car : cars) {
            if (car.isAvailable()) {
                availableCars++;
            }
        }
        return availableCars;
    }

    public int getTotalCarsCount() {
        return cars.size();
    }

    public int getRegistrationNumber() {
        return this.cars.get(0).getRegistrationNumber();
    }

    @Override
    public String toString() {
        Car sampleCar = cars.get(0);
        return sampleCar.getRegistrationNumber() + " " + sampleCar.getBrand() + " " + sampleCar.getModel() + " - (" + getAvailableCarsCount() + " of " + getTotalCarsCount() + " available)";
    }

    public static HashMap<Integer, CarGroup> groupCars(ArrayList<Car> cars) {
        HashMap<Integer, CarGroup> groupedCars = new HashMap<>();
        for (Car car : cars) {
            int registrationNumber = car.getRegistrationNumber();
            if (groupedCars.containsKey(registrationNumber)) {
                CarGroup carGroup = groupedCars.get(registrationNumber);
                carGroup.cars.add(car);
            } else {
                CarGroup carGroup = new CarGroup(new ArrayList<>());
                carGroup.cars.add(car);
                groupedCars.put(registrationNumber, carGroup);
            }
        }
        return groupedCars;
    }
}
