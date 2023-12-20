package homework10;

import java.util.Optional;

public class Computer {
  private final String CPU;
  private final String RAM;
  private final String HDD;

  private final Optional<String> graphicCard;
  private final Optional<String> display;
  private final Optional<String> operatingSystem;

  private Computer(Builder builder) {
    this.CPU = builder.CPU;
    this.RAM = builder.RAM;
    this.HDD = builder.HDD;
    this.graphicCard = builder.graphicCard;
    this.display = builder.display;
    this.operatingSystem = builder.operatingSystem;
  }

  public String getCPU() {
    return CPU;
  }

  public String getRAM() {
    return RAM;
  }

  public String getHDD() {
    return HDD;
  }

  public Optional<String> getGraphicCard() {
    return graphicCard;
  }

  public Optional<String> getDisplay() {
    return display;
  }

  public Optional<String> getOperatingSystem() {
    return operatingSystem;
  }

  @Override
  public String toString() {
    return "Computer{" +
        "CPU='" + CPU + '\'' +
        ", RAM='" + RAM + '\'' +
        ", HDD='" + HDD + '\'' +
        ", graphicCard='" + graphicCard.orElse("") + '\'' +
        ", display='" + display.orElse("") + '\'' +
        ", operatingSystem='" + operatingSystem.orElse("") + '\'' +
        '}';
  }

  public static class Builder {
    private final String CPU;
    private final String RAM;
    private final String HDD;

    private Optional<String> graphicCard = Optional.empty();
    private Optional<String> display = Optional.empty();
    private Optional<String> operatingSystem = Optional.empty();

    public Builder(String CPU, String RAM, String HDD) {
      this.CPU = CPU;
      this.RAM = RAM;
      this.HDD = HDD;
    }

    public Builder graphicCard(String graphicCard) {
      this.graphicCard = Optional.of(graphicCard);
      return this;
    }

    public Builder display(String display) {
      this.display = Optional.of(display);
      return this;
    }

    public Builder operatingSystem(String operatingSystem) {
      this.operatingSystem = Optional.of(operatingSystem);
      return this;
    }

    public Computer build() {
      return new Computer(this);
    }
  }
}
