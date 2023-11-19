package homework4;

import java.util.*;

public class Student {
  private String id;
  private String firstName;
  private String lastName;
  private String faculty;
  private Map<String, Integer> marks;
  private double averageMark;

  public Student(String id, String firstName, String lastName, String faculty) {
    setId(id);
    setFirstName(firstName);
    setLastName(lastName);
    setFaculty(faculty);
    marks = new HashMap<>();
    calculateAverage();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    if (id.matches("[1-9]\\d{5}")) {
      this.id = id;
    } else {
      throw new IllegalArgumentException("Invalid id format");
    }
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFaculty() {
    return faculty;
  }

  public void setFaculty(String faculty) {
    this.faculty = faculty;
  }

  public Map<String, Integer> getMarks() {
    return marks;
  }

  public double getAverageMark() {
    return averageMark;
  }

  public void calculateAverage() {
    if (!marks.isEmpty()) {
      averageMark = marks.values().stream()
          .mapToDouble(Integer::doubleValue)
          .average()
          .orElse(0.0);
    } else {
      averageMark = 0.0;
    }
  }

  public void changeMark(String subject, int mark) {
    if (mark >= 2 && mark <= 5) {
      marks.put(subject, mark);
      calculateAverage();
    } else {
      throw new IllegalArgumentException("Invalid mark. Mark should be in the range [2, 5]");
    }
  }

  @Override
  public String toString() {
    return "Student{" +
        "id='" + id + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", faculty='" + faculty + '\'' +
        ", marks=" + marks +
        ", averageMark=" + averageMark +
        '}';
  }
}
