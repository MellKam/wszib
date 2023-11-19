package homework5;

public class Main {
  public static void main(String[] args) {
    Car car1 = new Car("Toyota", "Blue", "Camry", 2022);
    Car car2 = new Car("Ford", "Red", "Mustang", 2021);
    Car car3 = new Car("Honda", "Silver", "Civic", 2023);

    drive(car1);
    drive(car2);
    drive(car3);

    System.out.println(car1);
    System.out.println(car2);
    System.out.println(car3);

  }

  public static void drive(Vehicle vehicle) {
    vehicle.start();
    vehicle.stop();
  }
}
