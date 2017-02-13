package dk.ninjabear.sudoku;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(10));
		pane.setVgap(5);
		pane.setHgap(5);
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++) {
				textFields[x][y] = new TextField();
				textFields[x][y].setMaxWidth(30);
				textFields[x][y].setOnKeyTyped(e -> controller.updateControls());
				pane.add(textFields[x][y], x, y);
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
		
		controller.updateControls();
	}
	
	public void reset() {
		controller.reset();
	}
	
	public Cell[][] getResult() {
		return controller.result;
	}
	
	private class Controller {
		Cell[][] result;
		
		void updateControls() {
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++)
					if (!textFields[x][y].getText().isEmpty() && !textFields[x][y].getText().matches("[1-9]"))
						textFields[x][y].setStyle("-fx-background-color: red;");
					else {
						String style = "-fx-border-color: black; ";
						if (	x/3 == 0 && y/3 == 0 ||
								x/3 == 0 && y/3 == 2 ||
								x/3 == 2 && y/3 == 0 ||
								x/3 == 2 && y/3 == 2 ||
								x/3 == 1 && y/3 == 1)
							style += "-fx-background-color: lightgrey;";
						else
							style += "-fx-background-color: white;";
						textFields[x][y].setStyle(style);
					}
		}
		
		void reset() {
			result = null;
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++)
					textFields[x][y].setText("");
			updateControls();
		}
		
		void cancelAction() {
			AddSudokuDialog.this.hide();
		}
		
		void addAction() {
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++)
					if (!textFields[x][y].getText().isEmpty() && !textFields[x][y].getText().matches("[1-9]")) {
						updateControls(); // paints it red
						return;
					}
			
			result = new Cell[9][9];
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++)
					if (textFields[x][y].getText().isEmpty())
						result[x][y] = new Cell(x, y,0);
					else
						result[x][y] = new Cell(x, y, Integer.parseInt(textFields[x][y].getText()));
			AddSudokuDialog.this.hide();
		}
	}
}