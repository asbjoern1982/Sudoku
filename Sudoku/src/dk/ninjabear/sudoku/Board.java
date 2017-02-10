package dk.ninjabear.sudoku;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Board extends Pane {
	public static final double WIDTH = 400;
	public static final double MARGIN = 10;
	private Cell[][] cells = new Cell[9][9];
	private int highlighted = 0;
	
	public Board() {
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				cells[x][y] = new Cell(x, y);
		Test.initTestData(cells);
		drawThis();
	}
	
	public void drawThis() {
		double m1 = MARGIN;
		double m2 = WIDTH-m1;
		List<Line> lines = new ArrayList<>();
		lines.add(new Line(m1, m1, m2, m1));
		lines.add(new Line(m1, m2, m2, m2));
		
		lines.add(new Line(m1, m1, m1, m2));
		lines.add(new Line(m2, m1, m2, m2));
		
		for (Line line : lines) {
			line.setStroke(Color.BLACK);
			line.setStrokeWidth(2);;
		}
		
		double d = (WIDTH - m1 * 2) / 9;
		for (int y = 1; y < 9; y++) {

			Line line = new Line(m1, m1+y*d, m2, m1+y*d);
			if (y%3 == 0) line.setStrokeWidth(2);
			lines.add(line);
		}
		for (int x = 1; x < 9; x++) {
			Line line = new Line(m1+x*d, m1, m1+x*d, m2);
			if (x%3 == 0) line.setStrokeWidth(2);
			lines.add(line);
		}
		
		this.getChildren().addAll(lines);
		
		List<Text> texts = new ArrayList<>();
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++) {
				double dx = m1+x*d;
				double dy = m1+y*d;
				if (cells[x][y].getValue() != 0) {
					Text text = new Text(dx+12, dy+34, "" + cells[x][y].getValue());
					text.setFont(Font.font(35));
					if (cells[x][y].getValue() != highlighted && cells[x][y].getLocked())
						text.setFill(Color.color(0.4,0.4,0.4));
					else if (cells[x][y].getValue() == highlighted && !cells[x][y].getLocked())
						text.setFill(Color.DARKRED);
					else if (cells[x][y].getValue() == highlighted && cells[x][y].getLocked())
						text.setFill(Color.RED);
					
						
					texts.add(text);
				} else {
					List<Integer> tempValues = cells[x][y].getTempValues();
					for (int n : tempValues) {
						int tvX = (n-1) % 3;
						int tvY = (int) ((n-1) / 3);
						Text text = new Text(dx + tvX*d/3.7 + 4.5, dy + tvY*d/3.4 + 13, "" + n);
						text.setFont(Font.font(13));
						if (n == highlighted)
							text.setFill(Color.DARKRED);
						texts.add(text);
					}
				}
			}
		this.getChildren().addAll(texts);
	}
	
	public void playAt(double mouseX, double mouseY, int n) {
		int x = (int)(9*(mouseX - MARGIN)/(WIDTH-2*MARGIN));
		int y = (int)(9*(mouseY - MARGIN)/(WIDTH-2*MARGIN));
		if (	-1 < x && x < 9 &&
				-1 < y && y < 9) {
			cells[x][y].setValue(n);
			this.getChildren().clear();
			drawThis();
		}
	}
	
	public void toggleTempValue(double mouseX, double mouseY, int n) {
		int x = (int)(9*(mouseX - MARGIN)/(WIDTH-2*MARGIN));
		int y = (int)(9*(mouseY - MARGIN)/(WIDTH-2*MARGIN));
		if (	-1 < x && x < 9 &&
				-1 < y && y < 9) {
			if (cells[x][y].getTempValues().contains(new Integer(n)))
				cells[x][y].removeTempValue(new Integer(n));
			else
				cells[x][y].addTempValue(new Integer(n));
			this.getChildren().clear();
			drawThis();
		}
	}
	
	public boolean isValid() {
		// horizontal lines
		for (int y = 0; y < 9; y++) {
			List<Integer> row = new ArrayList<>();
			for (int x = 0; x < 9; x++)
				if (cells[x][y].getValue() != 0)
					row.add(cells[x][y].getValue());
			for (int i = 1; i < 10; i++)
				if (row.contains(new Integer(i)))
					row.remove(new Integer(i));
			if (!row.isEmpty()) {
				System.out.println("row " + y + " not valid " + row);
				return false;
			}
		}
			
		// vertical lines:
		for (int x = 0; x < 9; x++) {
			List<Integer> col = new ArrayList<>();
			for (int y = 0; y < 9; y++)
				if (cells[x][y].getValue() != 0)
					col.add(cells[x][y].getValue());
			for (int i = 1; i < 10; i++)
				if (col.contains(new Integer(i)))
					col.remove(new Integer(i));
			if (!col.isEmpty()) {
				System.out.println("col " + x + " not valid " + col);
				return false;
			}
		}
		
		// boxes
		for (int dx = 0; dx < 9; dx+=3)
			for (int dy = 0; dy < 9; dy+=3) {
				List<Integer> box = new ArrayList<>();
				for (int x = 0; x < 3; x++)
					for (int y = 0; y < 3; y++)
						if (cells[dx+x][dy+y].getValue() != 0)
							box.add(cells[dx+x][dy+y].getValue());
				for (int i = 1; i < 10; i++)
					if (box.contains(new Integer(i)))
						box.remove(new Integer(i));
				if (!box.isEmpty()) {
					System.out.println("box (" + dx + "," + dy + ") not valid " + box);
					return false;
				}
			}
		
		return true;
	}
	
	public boolean isSolved() {
		if (isValid()) {
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++)
					if (cells[x][y].getValue() == 0)
						return false;
			return true;
		}
		return false;
	}

	public void highlight(int n) {
		highlighted = n;
		this.getChildren().clear();
		drawThis();
	}
	
	public void clearHighlight() {
		highlighted = 0;
		this.getChildren().clear();
		drawThis();
	}
}
