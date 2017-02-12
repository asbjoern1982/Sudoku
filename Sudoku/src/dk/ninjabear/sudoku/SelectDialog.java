package dk.ninjabear.sudoku;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectDialog extends Stage {
	private Controller controller = new Controller();
	public SelectDialog() {
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Select Sudoku");
		BorderPane borderPane = new BorderPane();
		initContent(borderPane);
		setScene(new Scene(borderPane));
	}
	
	private final GridPane pane = new GridPane();
	
	public void initContent(BorderPane borderPane) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(pane);
		borderPane.setCenter(scrollPane);
		
		HBox buttons = new HBox();
		buttons.setSpacing(5);
		buttons.setPadding(new Insets(10));
		Button addButton = new Button("+");
		addButton.setAlignment(Pos.CENTER_LEFT);
		addButton.setOnAction(e->controller.addAction());
		Button removeButton = new Button("-");
		removeButton.setAlignment(Pos.CENTER_LEFT);
		removeButton.setOnAction(e->controller.removeAction());
		Button cancelButton = new Button("cancel");
		cancelButton.setAlignment(Pos.CENTER_RIGHT);
		cancelButton.setOnAction(e->controller.cancelAction());
		Button selectButton = new Button("select");
		selectButton.setAlignment(Pos.CENTER_RIGHT);
		selectButton.setOnAction(e->controller.selectAction());
		selectButton.setDefaultButton(true);
		buttons.getChildren().addAll(addButton, removeButton, cancelButton, selectButton);
		borderPane.setBottom(buttons);
		
		controller.updateControls();
	}

	public Cell[][] getResult() {return controller.result;}
	public void reset() {controller.resetControls();}
	public class Controller {
		Cell[][] result;
		
		
		public void updateControls() {
			List<Cell[][]> cells = SudokuStorage.getSudokus();
			int counter = 0;
			for (Cell[][] cell : cells) {
				MiniSudoku mini = new MiniSudoku(100, cell);
				pane.add(mini, counter%3, counter/3);
			}
			
		}
		
		public void resetControls() {
			result = null;
		}
		
		public void addAction() {
			
		}
		
		public void removeAction() {
			
		}
		
		public void cancelAction() {
			SelectDialog.this.hide();
		}
		
		public void selectAction() {
			
			
			
			SelectDialog.this.hide();
		}
	}
}
