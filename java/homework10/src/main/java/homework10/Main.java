package homework10;

public class Main {
  public static void main(String[] args) {
    Computer myComputer = new Computer.Builder("AMD FX-6300", "8GB", "256GB HDD")
        .graphicCard("NVIDIA GeForce GTX 650")
        .display("27-inch 4K IPS")
        .build();

    Computer computerOfMyMotherFriendSon = new Computer.Builder("AMD Ryzen 9 7950X3D", "128GB", "4TB SSD")
        .graphicCard("NVIDIA GeForce RTX 4090")
        .operatingSystem("Windows 11")
        .build();

    System.out.println(myComputer);
    System.out.println(computerOfMyMotherFriendSon);
  }
}
