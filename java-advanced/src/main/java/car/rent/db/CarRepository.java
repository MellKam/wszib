package car.rent.db;

import car.rent.model.Car;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public final class CarRepository {

    private final ArrayList<Car> cars = new ArrayList<>();
    private File file;

    public CarRepository(String filepath) {
        try {
            this.file = new File(filepath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            Scanner reader = new Scanner(this.file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                Car car = parseLineToCar(data);
                this.addCar(car);
            }

            // read file and populate cars
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    private Car parseLineToCar(String line) {
        String[] carData = line.split(",");
        if (carData.length != 4) {
            throw new IllegalArgumentException("Invalid car data");
        }
        return new Car(
                Integer.parseInt(carData[0]),
                carData[1],
                carData[2],
                Boolean.parseBoolean(carData[3])
        );
    }

    private String parseCarToLine(Car car) {
        return car.getRegistrationNumber() + "," + car.getBrand() + "," + car.getModel() + "," + car.isAvailable();
    }

    public void addCar(Car car) {
        cars.add(car);
        save();
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(this.file)) {
            writer.print(""); // Erase all content in the file
            for (Car car : this.cars) {
                writer.println(parseCarToLine(car));
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while saving.");
            ex.printStackTrace();
        }
    }

    public Car rentCar(int registrationNumber) {
        ArrayList<Car> carsWithRegistrationNumber = new ArrayList<>();
        for (Car car : this.cars) {
            if (car.getRegistrationNumber() == registrationNumber) {
                carsWithRegistrationNumber.add(car);
            }
        }

        if (carsWithRegistrationNumber.isEmpty()) {
            throw new IllegalArgumentException("Car with provided registration number not found");
        }
        for (Car car : carsWithRegistrationNumber) {
            if (car.isAvailable()) {
                car.setAvailable(false);
                return car;
            }
        }
        throw new IllegalArgumentException("Car with provided registration number is not available");
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}
