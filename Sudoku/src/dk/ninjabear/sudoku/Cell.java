package dk.ninjabear.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private int x,y;
	private int value = 0;
	private List<Integer> tempValues = new ArrayList<>();
	public Cell(int x, int y) {
		this.x = x; this.y = y;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	public void setValue(int value) {this.value = value;}
	public int getValue() {return value;}
	
	public void addTempValue(int tempValue) {tempValues.add(tempValue);}
	public boolean hasTempValue(int tempValue) {return tempValues.contains(tempValue);}
	public List<Integer> getTempValues() {return tempValues;}
}
