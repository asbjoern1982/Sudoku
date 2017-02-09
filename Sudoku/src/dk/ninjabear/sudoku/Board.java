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
	private Cell[][] cells = new Cell[9][9];
	
	public Board() {
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				cells[x][y] = new Cell(x, y);
		Test.initTestData(cells);
		drawThis();
	}
	
	public void drawThis() {
		double m1 = 10;
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
			for (int y = 0; y < 9; y++)
				if (cells[x][y].getValue() != 0) {
					Text text = new Text(m1+x*d+12, m1+y*d+34, "" + cells[x][y].getValue());
					text.setFont(Font.font(35));
					texts.add(text);
				} else {
					for (int tX = 0; tX < 9; tX++)
						for (int tY = 0; tY < 9; tY++) {
							//TODO draw as small text
						}
				}
		this.getChildren().addAll(texts);
	}
}
