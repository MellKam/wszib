package homework5;

public class Car implements Vehicle {
  private String make;
  private String color;
  private String model;
  private int year;

  public Car(String make, String color, String model, int year) {
    this.make = make;
    this.color = color;
    this.model = model;
    this.year = year;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public void start() {
    System.out.println("The car is starting.");
  }

  @Override
  public void stop() {
    System.out.println("The car is stopping.");
  }

  @Override
  public String toString() {
    return "Car{" +
        "make='" + make + '\'' +
        ", color='" + color + '\'' +
        ", model='" + model + '\'' +
        ", year=" + year +
        '}';
  }
}
