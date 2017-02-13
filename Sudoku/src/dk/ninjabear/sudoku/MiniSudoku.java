package dk.ninjabear.sudoku;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MiniSudoku extends Group {
	private int width;
	private Cell[][] data;
	private boolean selected;
	public MiniSudoku(int width, Cell[][] data) {
		this.width = width;
		this.data = data;
		selected = false;
		paintThis();
	}
	public Cell[][] getData() {return data;}
	public boolean isSelected() {return selected;}
	public void setSelected(boolean selected) {
		this.selected = selected;
		paintThis();
	}
	
	private void paintThis() {
		Color color = Settings.getNumberColor();
		Rectangle background = new Rectangle(width, width);
		if (selected)
			background.setFill(Settings.getBackgroundColor().darker());
		else
			background.setFill(Settings.getBackgroundColor());
		getChildren().add(background);
		
		double d = width/9.0;
		for (int x = 0; x < 10; x++) {
			Line line = new Line(x*d, 0, x*d, width);
			line.setFill(color);
			if (x%3==0) line.setStrokeWidth(2);
			getChildren().add(line);
		}
		for (int y = 0; y < 10; y++) {
			Line line = new Line(0, y*d, width, y*d);
			line.setFill(color);
			if (y%3==0) line.setStrokeWidth(2);
			getChildren().add(line);
		}
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				if (data[x][y].getValue() != 0) {
					Text text = new Text(x*d+3.9, y*d+8.8, "" + data[x][y].getValue());
					text.setFill(color);
					text.setFont(Font.font(8));
					getChildren().add(text);
				}
	}
}
