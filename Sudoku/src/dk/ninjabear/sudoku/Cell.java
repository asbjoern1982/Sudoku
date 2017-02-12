package dk.ninjabear.sudoku;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private int x, y;
	private int value = 0;
	private List<Integer> tempValues = new ArrayList<>();
	private boolean locked;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Cell(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
		if (value != 0)
			locked = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setValue(int value) {
		if (!locked)
			this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean getLocked() {
		return locked;
	}

	public void addTempValue(int tempValue) {
		if (!locked && value == 0 && !tempValues.contains(new Integer(tempValue)))
			tempValues.add(new Integer(tempValue));
	}

	public void removeTempValue(int tempValue) {
		if (!locked && value == 0 && tempValues.contains(new Integer(tempValue)))
			tempValues.remove(new Integer(tempValue));
	}

	public boolean hasTempValue(int tempValue) {
		return tempValues.contains(tempValue);
	}

	public List<Integer> getTempValues() {
		return tempValues;
	}
}
