package homework7;

import java.util.Random;

public class Main {
  private static final Random random = new Random();

  public static void main(String[] args) {
    try {
      getLeave();
    } catch (NoLeaveGrantedException e) {
      e.printStackTrace();
    }
  }

  static void getLeave() throws NoLeaveGrantedException {
    try {
      workToBeDone();
      howIsTeamLead();
      System.out.println("Leave granted");
    } catch (WorkToBeDoneException e) {
      throw new NoLeaveGrantedException("Leave not granted due to work to be done", e);
    } catch (TeamLeadUpsetException e) {
      throw new NoLeaveGrantedException("Leave not granted due to upset team lead", e);
    }
  }

  static void howIsTeamLead() throws TeamLeadUpsetException {
    if (random.nextInt(3) == 0) {
      throw new TeamLeadUpsetException("Team lead is upset!");
    }
  }

  static void workToBeDone() throws WorkToBeDoneException {
    if (random.nextInt(3) == 0) {
      throw new WorkToBeDoneException("Work to be done!");
    }
  }

  static class NoLeaveGrantedException extends Exception {
    public NoLeaveGrantedException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  static class TeamLeadUpsetException extends Exception {
    public TeamLeadUpsetException(String message) {
      super(message);
    }
  }

  static class WorkToBeDoneException extends Exception {
    public WorkToBeDoneException(String message) {
      super(message);
    }
  }
}
