Na podstawie programu zaprezentowanego na zajęciach demonstrującym tzw. "chained exceptions" napisz program LeaveWithChaining, który sprawdza, czy szef udzieli nam urlopu. Szef może nie udzielić urlopu gdy jest jeszcze dużo pracy do wykonania lub gdy ma zły humor. Proszę wykorzystać poniższy schemat.

```java
public class LeaveWithChaining {

	private static final Random random = new Random();

	public static void main(String[] args) {
		try {
			getLeave();
		} catch(NoLeaveGrantedException e) {
			e.printStackTrace();
		}
	}

	static void getLeave() throws NoLeaveGrantedException {
		try {
			workToBeDone();
			howIsTeamLead();
			System.out.println("Leave granted");
		} catch(Exception e) {
			// ...
		}
	}

	static void howIsTeamLead() throws TeamLeadUpsetException {
		// throw TeamLeadUpsetException with probability 1/3
	}

	static void workToBeDone() throws WorkToBeDoneException {
		// throw WorkToBeDoneException with probability 1/3
	}

	static class NoLeaveGrantedException extends Exception {
		public NoLeaveGrantedException(String message, Throwable cause) {
			// ctor with cause
		}
	}

	static class TeamLeadUpsetException extends Exception {
		public TeamLeadUpsetException(String message) {
			// ...
		}
	}

	static class WorkToBeDoneException extends Exception {
		public WorkToBeDoneException(String message) {
			// ...
		}
	}
}
```