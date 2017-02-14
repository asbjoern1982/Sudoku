package dk.ninjabear.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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
		setScene(new Scene(borderPane, 365, 300));
	}
	
	private final GridPane pane = new GridPane();
	
	public void initContent(BorderPane borderPane) {
		pane.setPadding(new Insets(10));
		pane.setHgap(10);
		pane.setVgap(10);
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
		List<MiniSudoku> sudokuList = new ArrayList<>();
		AddSudokuDialog addDialog;
		
		public void updateControls() {
			sudokuList.clear();
			pane.getChildren().clear();
			List<Cell[][]> cells = SudokuStorage.getSudokus();
			int counter = 0;
			for (Cell[][] cell : cells) {
				MiniSudoku mini = new MiniSudoku(100, cell);
				mini.setOnMouseClicked(e -> mouseAction(e));
				pane.add(mini, counter%3, counter/3);
				sudokuList.add(mini);
				counter++;
			}
			
		}
		
		public void mouseAction(MouseEvent e) {
			if (e.getSource().getClass() == MiniSudoku.class) {
				for (MiniSudoku sudoku : sudokuList)
					sudoku.setSelected(false);
				MiniSudoku selected = (MiniSudoku) e.getSource();
				selected.setSelected(true);
			}
		}
		
		public void resetControls() {
			result = null;
			for (MiniSudoku sudoku : sudokuList)
				sudoku.setSelected(false);
			updateControls();
		}
		
		public void addAction() {
			if (addDialog == null) {
				addDialog = new AddSudokuDialog();
				addDialog.initOwner(SelectDialog.this.getOwner());
			} else
				addDialog.reset();
			
			addDialog.showAndWait();
			
			if (addDialog.getResult() != null) {
				Cell[][] newSudoku = addDialog.getResult();
				SudokuStorage.storeSudoku(newSudoku);
				updateControls();
			}
		}
		
		public void removeAction() {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Sudoku");
			alert.setHeaderText("You are about to delete a sudoku");
			alert.setContentText("Do you want to continiue?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				for (MiniSudoku sudoku : sudokuList)
					if (sudoku.isSelected()) {
						SudokuStorage.removeSudoku(sudoku.getData());
						reset();
						break; //TODO does not work??
					}
			}
		}
		
		public void cancelAction() {
			SelectDialog.this.hide();
		}
		
		public void selectAction() {
			for (MiniSudoku sudoku : sudokuList)
				if (sudoku.isSelected())
					result = sudoku.getData();
			SelectDialog.this.hide();
		}
	}
}
