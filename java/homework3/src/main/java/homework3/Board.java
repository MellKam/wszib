package homework3;

import java.util.Random;

public class Board {
	public int width;
	public int height;
	private boolean[][] board;

	private static final int NEIGHBORS_COUNT = 8;

	public Board(int width, int height, double initialAliveProbability) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Board size cannot be less than or equal to zero");
		}
		if (width * height < 9) {
			throw new IllegalArgumentException("The game doesn't make any sense with a board less than 3x3. Please provide the correct board size.");
		}
		this.width = width;
		this.height = height;
		this.board = new boolean[width][height];

		Random random = new Random();
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				board[x][y] = random.nextDouble() < initialAliveProbability;
			}
		}
	}

	public void print() {
		for (boolean[] col : this.board) {
			for (boolean cell : col) {
				System.out.print(cell ? "■" : " ");
			}
			System.out.println();
		}
	}

	private int countAliveNeighbors(int x, int y) {
		int aliveNeighbors = 0;
		int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

		for (int i = 0; i < NEIGHBORS_COUNT; i++) {
			int neighborX = x + dx[i];
			int neighborY = y + dy[i];
			if (
				neighborX >= 0 && neighborX < this.width &&
				neighborY >= 0 && neighborY < this.height &&
				board[neighborX][neighborY]
			) {
				aliveNeighbors++;
			}
		}

		return aliveNeighbors;
	}

	public void nextGeneration() {
		var newBoard = new boolean[this.width][this.height];

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				int aliveNeighbors = this.countAliveNeighbors(x, y);
				if (board[x][y]) {
					newBoard[x][y] = aliveNeighbors == 2 || aliveNeighbors == 3;
				} else {
					newBoard[x][y] = aliveNeighbors == 3;
				}
			}
		}

		this.board = newBoard;
	}
}

