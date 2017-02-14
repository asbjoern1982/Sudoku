package dk.ninjabear.sudoku;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddSudokuDialog extends Stage {
	private Controller controller = new Controller();
	public AddSudokuDialog() {
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Select Sudoku");
		BorderPane borderPane = new BorderPane();
		initContent(borderPane);
		setScene(new Scene(borderPane));
	}
	
	private final TextField[][] textFields = new TextField[9][9];
	
	private void initContent(BorderPane root) {
		double dx = 340 / 9;
		double dy = dx * 0.9;
		double m = 10;
		Pane pane = new Pane();
		Rectangle background = new Rectangle(345, 315);
		background.setFill(Color.WHITE);
		pane.getChildren().add(background);
		for (int x = 0; x < 10; x++) {
			Line line = new Line(x*dx+m-2.5, m-2.4, x*dx+m-2.4, 306);
			if (x%3 == 0) line.setStrokeWidth(2);
			pane.getChildren().add(line);
		}
		
		for (int y = 0; y < 10; y++) {
			Line line = new Line(m-2.5, y*dy+m-2.4, 340, y*dy+m-2.4);
			if (y%3 == 0) line.setStrokeWidth(2);
			pane.getChildren().add(line);
		}
		
		for (int y = 0; y < 9; y++) 
			for (int x = 0; x < 9; x++){
				textFields[x][y] = new TextField();
				textFields[x][y].setMaxWidth(30);
				textFields[x][y].setLayoutX(x * dx + m);
				textFields[x][y].setLayoutY(y * dy + m);
				textFields[x][y].setStyle("-fx-background-color: white;");
				textFields[x][y].textProperty().addListener(
						(observable, oldValue, newValue) -> {
							if (!newValue.matches("[1-9]?"))
								((StringProperty) observable).set(oldValue);
						});
				pane.getChildren().add(textFields[x][y]);
			}
		root.setCenter(pane);
		
		HBox buttons = new HBox();
		buttons.setSpacing(5);
		buttons.setPadding(new Insets(15));
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		Button cancelButton = new Button("cancel");
		cancelButton.setOnAction(e -> controller.cancelAction());
		Button addButton = new Button("add");
		addButton.setDefaultButton(true);
		addButton.setOnAction(e -> controller.addAction());
		buttons.getChildren().addAll(cancelButton, addButton);
		root.setBottom(buttons);
	}
	
	public void reset() {
		controller.reset();
	}
	
	public Cell[][] getResult() {
		return controller.result;
	}
	
	private class Controller {
		Cell[][] result;
		
		void reset() {
			result = null;
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++)
					textFields[x][y].setText("");
		}
		
		void cancelAction() {
			AddSudokuDialog.this.hide();
		}
		
		void addAction() {
			boolean empty = true;
			for (int y = 0; y < 9; y++)
				for (int x = 0; x < 9; x++)
					if (!textFields[x][y].getText().isEmpty())
						empty = false;
			if (empty) return;
			
			result = new Cell[9][9];
			for (int y = 0; y < 9; y++)
				for (int x = 0; x < 9; x++)
					if (textFields[x][y].getText().isEmpty())
						result[x][y] = new Cell(x, y,0);
					else
						result[x][y] = new Cell(x, y, Integer.parseInt(textFields[x][y].getText()));
			AddSudokuDialog.this.hide();
		}
	}
}