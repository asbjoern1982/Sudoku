package dk.ninjabear.sudoku;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SudokuStorage {
	public static final String FILENAME = "sudokus.json";
	private static List<Cell[][]> sudokuList = new ArrayList<>();
	private static boolean load = true;
	
	public static List<Cell[][]> getSudokus() {if (load) load(); return new ArrayList<>(sudokuList);}
	public static void storeSudoku(Cell[][] sudoku) {
		sudokuList.add(sudoku);
		store();
	}
	
	private static void load() {
		try {
			String jsonString = Files.readAllLines(Paths.get(FILENAME))
					.stream()
					.collect(Collectors.joining("\n"));
			JSONObject obj = new JSONObject(jsonString);
			JSONArray sudokus = obj.getJSONArray("sudokus");
			for (int i = 0; i < sudokus.length(); i++) {
				JSONArray sudokuJSON = sudokus.getJSONArray(i);
				Cell[][] sudoku = new Cell[9][9];
				for (int n = 0; n < sudokuJSON.length(); n++)
					sudoku[n%9][n/9] = new Cell(n%9, n/9, sudokuJSON.getInt(n));
				sudokuList.add(sudoku);
			}
		} catch (Exception e) {System.out.println(e);}
		load = false;
	}
	
	private static void store() {
		try (FileWriter file = new FileWriter(FILENAME)) {
			JSONArray root = new JSONArray();
			for (Cell[][] sudoku : sudokuList) {
				JSONArray sudokuJSON = new JSONArray();
				for (int x = 0; x < 9; x++)
					for (int y = 0; y < 9; y++)
						sudokuJSON.put(sudoku[x][y].getValue());					
				root.put(sudokuJSON);
			}
			JSONObject obj = new JSONObject();
			obj.put("sudokus", root);
			obj.write(file);
		} catch (JSONException e) {System.err.println(e);} catch (IOException e) {System.err.println(e);}
	}
}
