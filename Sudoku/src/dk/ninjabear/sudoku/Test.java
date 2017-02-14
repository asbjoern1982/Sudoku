package dk.ninjabear.sudoku;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Test extends Application {
	
	public static void main(String[] args) {
		//storeTestData();
		//readTestData();
		Application.launch(args);;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("test!");
		Button button = new Button("test!!");
		button.setOnAction(e -> addDialogTest(primaryStage));
		primaryStage.setScene(new Scene(button));
		primaryStage.show();
		addDialogTest(primaryStage);
	}
	
	static void addDialogTest(Stage primaryStage) {
		AddSudokuDialog dialog = new AddSudokuDialog();
		dialog.initOwner(primaryStage);
		dialog.showAndWait();
		Cell[][] out = dialog.getResult();
		if (out != null) printData(out);
		System.exit(-1);
	}
	
	public static void storeTestData() {
		int[][] s1Data = {
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
		Cell[][] s1 = new Cell[9][9];
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				s1[x][y] = new Cell(x, y, s1Data[x][y]);

		
		int[][] s2Data = {
				{2,0,0,  8,0,4,  0,0,6},
				{0,0,6,  0,0,0,  5,0,0},
				{0,7,4,  0,0,0,  9,2,0},

				{3,0,0,  0,4,0,  0,0,7},
				{0,0,0,  3,0,5,  0,0,0},
				{4,0,0,  0,6,0,  0,0,9},

				{0,1,9,  0,0,0,  7,4,0},
				{0,0,8,  0,0,0,  2,0,0},
				{5,0,0,  6,0,8,  0,0,1}
		};
		Cell[][] s2 = new Cell[9][9];
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				s2[x][y] = new Cell(x, y, s2Data[x][y]);
		
		SudokuStorage.storeSudoku(s1);
	}
	
	public static void readTestData() {
		List<Cell[][]> list = SudokuStorage.getSudokus();
		for (Cell[][] sudoku : list) {
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) 
					System.out.print("[" + sudoku[x][y].getValue() + "]");
				System.out.println();
			}
			System.out.println();
		}
	}
	
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
	
	public static void printData(Cell[][] data) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (x%3 == 0 && x != 0) System.out.print(" ");
				System.out.print(data[x][y].getValue());
			}
			if (y%3 == 2) System.out.println();
			System.out.println();
		}
	}
}
