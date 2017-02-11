package dk.ninjabear.sudoku;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.paint.Color;

public class Settings {
	public static final String FILENAME = "settings.json";

	private static double width;
	private static double margin;
	private static Color backgroundColor;
	private static Color lineColor;
	private static Color numberColor;
	private static Color lockedNumberColor;
	private static Color highlightedNumberColor;
	private static Color highlightedLocledNumberColor;

	public static double getWidth() {if (load) load(); return width;}
	public static double getMargin() {if (load) load(); return margin;}
	public static Color getBackgroundColor() {if (load) load(); return backgroundColor;}
	public static Color getLineColor() {if (load) load(); return lineColor;}
	public static Color getNumberColor() {if (load) load(); return numberColor;}
	public static Color getLockedNumberColor() {if (load) load(); return lockedNumberColor;}
	public static Color getHighlightedNumberColor() {if (load) load(); return highlightedNumberColor;}
	public static Color getHighlightedLocledNumberColor() {if (load) load(); return highlightedLocledNumberColor;}
	
	private static boolean load = true;
	
	private static void load() {
		try {
			String jsonString = Files.readAllLines(Paths.get(FILENAME))
					.stream()
					.collect(Collectors.joining("\n"));

			JSONObject obj = new JSONObject(jsonString);
			width = obj.getDouble("width");
			margin = obj.getDouble("margin");
			backgroundColor = Color.valueOf(obj.getString("backgroundColor"));
			lineColor = Color.valueOf(obj.getString("lineColor"));
			numberColor = Color.valueOf(obj.getString("numberColor"));
			lockedNumberColor = Color.valueOf(obj.getString("lockedNumberColor"));
			highlightedNumberColor = Color.valueOf(obj.getString("highlightedNumberColor"));
			highlightedLocledNumberColor = Color.valueOf(obj.getString("highlightedLocledNumberColor"));
		} catch (Exception e) {
			System.out.println(e);
			defaultValues();
			store();
		}
		load = false;
	}

	private static void defaultValues() {
		width = 400;
		margin = 10;
		backgroundColor = Color.WHITE;
		lineColor = Color.BLACK;
		numberColor = Color.BLACK;
		lockedNumberColor = Color.color(0.4, 0.4, 0.4);
		highlightedNumberColor = Color.RED;
		highlightedLocledNumberColor = Color.RED;
	}

	private static void store() {
		try (FileWriter file = new FileWriter(FILENAME)) {
			JSONObject obj = new JSONObject();
			obj.put("width", width);
			obj.put("margin", margin);
			obj.put("backgroundColor", backgroundColor);
			obj.put("lineColor", lineColor);
			obj.put("numberColor", numberColor);
			obj.put("lockedNumberColor", lockedNumberColor);
			obj.put("highlightedNumberColor", highlightedNumberColor);
			obj.put("highlightedLocledNumberColor", highlightedLocledNumberColor);
			obj.write(file);
		} catch (JSONException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
