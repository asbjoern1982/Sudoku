package dk.ninjabear.sudoku;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
		primaryStage.setScene(new Scene(root, 400, 485));
		primaryStage.show();
	}
	
	private void initContent(BorderPane root) {
		controller.board = new Board();
		controller.board.setOnMouseClicked(e -> controller.mouseClickedAction(e));
		root.setCenter(controller.board);
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15));
		pane.setHgap(11);
		pane.setVgap(11);
		controller.group = new ToggleGroup();
		ToggleButton c = new ToggleButton("c");
		c.setToggleGroup(controller.group);
		c.setUserData("value");
		c.setOnAction(e -> controller.highlightAction(e));
		pane.add(c, 0, 0);;
		for (int n = 1; n <= 9; n++) {
			ToggleButton b = new ToggleButton("" + n);
			b.setToggleGroup(controller.group);
			b.setUserData("value");
			b.setOnAction(e -> controller.highlightAction(e));
			pane.add(b, n, 0);
		}
		root.setBottom(pane);
		
		for (int n = 1; n <= 9; n++) {
			ToggleButton b = new ToggleButton("" + n);
			b.setToggleGroup(controller.group);
			b.setUserData("tempData");
			b.setOnAction(e -> controller.highlightAction(e));
			pane.add(b, n, 1);
		}
	}
	
	private class Controller {
		Board board;
		ToggleGroup group;
		
		void mouseClickedAction(MouseEvent e) {
			ToggleButton b = (ToggleButton)group.getSelectedToggle();
			if (b != null) {
				String s = b.getText();
				int n = s.equals("c") ? 0 : Integer.parseInt(s);
				
				if (b.getUserData().equals("value")) {
					board.playAt(e.getX(), e.getY(), n);
					if (board.isSolved())
						System.out.println("SOLVED!");
				} else {
					board.toggleTempValue(e.getX(), e.getY(), n);
				}
			}
		}
		
		void highlightAction(ActionEvent e) {
			ToggleButton b = (ToggleButton) e.getSource();
			if (!b.getText().equals("c")) {
				board.highlight(Integer.parseInt(b.getText()));
			} else {
				board.clearHighlight();
			}
		}
	}
}
