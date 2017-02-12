package dk.ninjabear.sudoku;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MiniSudoku extends Group {
	public MiniSudoku(int width, Cell[][] data) {
		double d = width/9.0;
		for (int x = 0; x < 10; x++) {
			Line line = new Line(x*d, 0, x*d, width);
			line.setFill(Settings.getLineColor());
			if (x%3==0) line.setStrokeWidth(2);
			getChildren().add(line);
		}
		for (int y = 0; y < 10; y++) {
			Line line = new Line(0, y*d, width, y*d);
			line.setFill(Settings.getLineColor());
			if (y%3==0) line.setStrokeWidth(2);
			getChildren().add(line);
		}
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				if (data[x][y].getValue() != 0) {
					Text text = new Text(x*d+3.9, y*d+8.8, "" + data[x][y].getValue());
					text.setFill(Settings.getNumberColor());
					text.setFont(Font.font(8));
					getChildren().add(text);
				}
		
		
	}
}
