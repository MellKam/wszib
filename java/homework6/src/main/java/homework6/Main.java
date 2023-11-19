package homework6;

import java.util.Comparator;

public class Main {
  public static void main(String[] args) {
    Person[] people = {
        new Person("John", "Doe", 25),
        new Person("Alice", "Smith", 30),
        new Person("Bob", "Johnson", 22),
        new Person("Eve", "Brown", 28)
    };

    System.out.println("Original array:");
    for (Person person : people) {
      System.out.println(person);
    }

    Person.sort(people, Comparator.comparing(Person::getFirstName));
    System.out.println("\nSorted by first name:");
    for (Person person : people) {
      System.out.println(person);
    }

    Person.sort(people, Comparator.comparing(Person::getLastName));
    System.out.println("\nSorted by last name:");
    for (Person person : people) {
      System.out.println(person);
    }

    Person.sort(people, Comparator.comparingInt(Person::getAge));
    System.out.println("\nSorted by age:");
    for (Person person : people) {
      System.out.println(person);
    }
  }
}
