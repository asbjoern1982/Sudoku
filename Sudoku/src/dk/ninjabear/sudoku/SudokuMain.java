package dk.ninjabear.sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SudokuMain extends Application {
	private Controller controller = new Controller();
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Sudoku");
		BorderPane root = new BorderPane();
		initContent(root);
		primaryStage.setScene(new Scene(root, 400, 430));
		primaryStage.show();
	}
	
	private void initContent(BorderPane root) {
		controller.board = new Board();
		root.setCenter(controller.board);
		HBox box = new HBox();
		for (int n = 1; n <= 9; n++) {
			Button b = new Button("" + n);
			box.getChildren().add(b);
		}
		root.setBottom(box);
	}
	
	private class Controller {
		Board board;
		
	}
}
