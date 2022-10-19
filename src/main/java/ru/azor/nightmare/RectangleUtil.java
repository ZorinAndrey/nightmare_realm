package ru.azor.nightmare;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleUtil {
    private RectangleUtil() {

    }

    public static Rectangle drawRedChip(Pane pane, Point2D point) {
        Rectangle rectangle = new Rectangle(point.getX(), point.getY(), 100, 100);
        rectangle.setFill(Color.RED);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(5);
        pane.getChildren().add(rectangle);
        return rectangle;
    }

    public static Rectangle drawYellowChip(Pane pane, Point2D point) {
        Rectangle rectangle = new Rectangle(point.getX(), point.getY(), 100, 100);
        rectangle.setFill(Color.YELLOW);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(5);
        pane.getChildren().add(rectangle);
        return rectangle;
    }

    public static Rectangle drawGreenChip(Pane pane, Point2D point) {
        Rectangle rectangle = new Rectangle(point.getX(), point.getY(), 100, 100);
        rectangle.setFill(Color.GREEN);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(5);
        pane.getChildren().add(rectangle);
        return rectangle;
    }

    public static Rectangle drawBlock(Pane pane, double startX, double startY) {
        Rectangle rectangle = new Rectangle(startX, startY, 100, 100);
        rectangle.setFill(Color.BLACK);
        pane.getChildren().add(rectangle);
        return rectangle;
    }

    public static void drawColor(Pane pane, Color color, double startX, double startY) {
        Rectangle rectangle = new Rectangle(startX, startY, 100, 50);
        rectangle.setFill(color);
        pane.getChildren().add(rectangle);
    }
}
