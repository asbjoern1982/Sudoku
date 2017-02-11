package dk.ninjabear.sudoku;

public class Test {
	public static void initTestData(Cell[][] cells) {
		
		int[][] sudoku = {
				{5,0,6,  0,2,0,  9,0,3},
				{0,0,0,  0,0,0,  5,0,0},
				{0,0,0,  0,0,0,  0,0,0},
				
				{6,0,0,  2,8,5,  0,0,9},
				{0,0,0,  9,0,3,  0,0,0},
				{8,0,0,  7,6,1,  0,0,4},
				
				{0,0,0,  0,0,0,  0,0,0},
				{0,0,4,  0,0,0,  3,0,0},
				{2,0,1,  0,5,0,  6,0,7}
		};

		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				if (sudoku[x][y] != 0) {
					cells[x][y].setValue(sudoku[x][y]);
					cells[x][y].setLocked(true);
				}
	}
}
