package homework4;

import java.util.Random;

public class Main {
  public static void main(String[] args) {
    Student[] students = new Student[3];

    Random random = new Random();
    for (int i = 0; i < students.length; i++) {
      String id = String.format("%06d", random.nextInt(1000000));

      students[i] = new Student(
          id,
          "FirstName" + (i + 1),
          "LastName" + (i + 1),
          "Faculty" + (i + 1));

      students[i].changeMark("Math", random.nextInt(4) + 2);
      students[i].changeMark("Physics", random.nextInt(4) + 2);
      students[i].changeMark("English", random.nextInt(4) + 2);
    }

    for (Student student : students) {
      System.out.println(student.toString());
    }
  }
}
