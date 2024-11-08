package car.rent;

import car.rent.db.CarRepository;
import car.rent.model.Car;
import car.rent.model.CarGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class App {

    public static void main(String[] args) {
        CarRepository carRepository = new CarRepository("./db.txt");
        ArrayList<Car> cars = carRepository.getCars();

        if (cars.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                Car car1 = new Car(623, "Toyota", "Corolla");
                carRepository.addCar(car1);
            }
            for (int i = 0; i < 2; i++) {
                Car car2 = new Car(124, "Mazda", "RX-7");
                carRepository.addCar(car2);
            }
            Car car3 = new Car(123, "Nissan", "Skyline");
            carRepository.addCar(car3);
            carRepository.save();
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextLine()) {
                case "1":
                    HashMap<Integer, CarGroup> groups = CarGroup.groupCars(cars);
                    for (CarGroup group : groups.values()) {
                        System.out.println(group);
                    }
                    break;
                case "2":
                    System.out.println("Enter registration number:");
                    try {
                        int registrationNumber = Integer.parseInt(scanner.nextLine());
                        carRepository.rentCar(registrationNumber);
                        carRepository.save();
                        System.out.println("Car rented");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
