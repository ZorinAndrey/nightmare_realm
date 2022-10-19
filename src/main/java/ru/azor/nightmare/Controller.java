package ru.azor.nightmare;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller implements Initializable {
    @FXML
    public Label label;
    @FXML
    public Pane pane, colorPane;
    @FXML
    public Button newGameButton;

    private Rectangle movingChip;
    private double deltaX;
    private double deltaY;
    private double x;
    private double y;
    private List<Node> blocks;
    private List<Node> chips;
    private Map<Integer, Point2D> points;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RectangleUtil.drawColor(colorPane, Color.RED, 0, 0);
        RectangleUtil.drawColor(colorPane, Color.YELLOW, 200, 0);
        RectangleUtil.drawColor(colorPane, Color.GREEN, 400, 0);

        blocks = List.of(RectangleUtil.drawBlock(pane, 100, 0),
                RectangleUtil.drawBlock(pane, 300, 0),
                RectangleUtil.drawBlock(pane, 100, 200),
                RectangleUtil.drawBlock(pane, 300, 200),
                RectangleUtil.drawBlock(pane, 100, 400),
                RectangleUtil.drawBlock(pane, 300, 400));

        points = new ConcurrentHashMap<>(Map.of(1, new Point2D(0, 0), 2, new Point2D(0, 100),
                3, new Point2D(0, 200), 4, new Point2D(0, 300), 5, new Point2D(0, 400),
                6, new Point2D(200, 0), 7, new Point2D(200, 100), 8, new Point2D(200, 200),
                9, new Point2D(200, 300), 10, new Point2D(200, 400)));
        points.putAll(Map.of(11, new Point2D(400, 0),
                12, new Point2D(400, 100), 13, new Point2D(400, 200), 14, new Point2D(400, 300),
                15, new Point2D(400, 400)));

        chips = new CopyOnWriteArrayList<>();

        startNewGame();
    }

    public void mouseDragged(MouseEvent e) {
        if (movingChip != null) {

            x = e.getX() - deltaX;
            y = e.getY() - deltaY;

            doNotReleaseTheChipOutsideTheField();
            doNotCrossOtherShapes();

            movingChip.setX(x);
            movingChip.setY(y);
        }
        e.consume();
    }

    private void doNotReleaseTheChipOutsideTheField() {
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x > (pane.getWidth() - movingChip.getWidth())) {
            x = pane.getWidth() - movingChip.getWidth();
        }
        if (y > (pane.getHeight() - movingChip.getHeight())) {
            y = pane.getHeight() - movingChip.getHeight();
        }
    }

    private void doNotCrossOtherShapes() {
        for (Node node : pane.getChildren()) {

            double n = 3;

            if ((node.contains(movingChip.getX(), movingChip.getY() + n)
                    || node.contains(movingChip.getX(), movingChip.getY() + movingChip.getHeight() - n)
                    || node.contains(movingChip.getX(), movingChip.getY() + movingChip.getHeight() / 2))
                    && node != movingChip && x < movingChip.getX()) {
                x = movingChip.getX();
            }

            if ((node.contains(movingChip.getX() + movingChip.getWidth(), movingChip.getY() + n)
                    || node.contains(movingChip.getX() + movingChip.getWidth(), movingChip.getY() + movingChip.getHeight() - n)
                    || node.contains(movingChip.getX() + movingChip.getWidth(), movingChip.getY() + movingChip.getHeight() / 2))
                    && node != movingChip && x > movingChip.getX()) {
                x = movingChip.getX();
            }

            if ((node.contains(movingChip.getX() + n, movingChip.getY())
                    || node.contains(movingChip.getX() + movingChip.getWidth() - n, movingChip.getY())
                    || node.contains(movingChip.getX() + movingChip.getWidth() / 2, movingChip.getY()))
                    && node != movingChip && y < movingChip.getY()) {
                y = movingChip.getY();
            }

            if ((node.contains(movingChip.getX() + n, movingChip.getY() + movingChip.getHeight())
                    || node.contains(movingChip.getX() + movingChip.getWidth() - n, movingChip.getY() + movingChip.getHeight())
                    || node.contains(movingChip.getX() + movingChip.getWidth() / 2, movingChip.getY() + movingChip.getHeight()))
                    && node != movingChip && y > movingChip.getY()) {
                y = movingChip.getY();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (Node node : pane.getChildren()) {
            if (node.contains(e.getX(), e.getY()) && !blocks.contains(node)) {
                pane.setCursor(Cursor.HAND);
                break;
            } else {
                pane.setCursor(Cursor.DEFAULT);
            }
        }
        e.consume();
    }

    public void mousePressed(MouseEvent e) {
        for (Node node : pane.getChildren()) {
            if (node.contains(e.getX(), e.getY()) && !blocks.contains(node)) {
                movingChip = (Rectangle) node;

                deltaX = e.getX() - movingChip.getX();
                deltaY = e.getY() - movingChip.getY();
            }
        }
    }

    public void mouseReleased() {
        if (movingChip == null) {
            return;
        }

        x = Math.round(movingChip.getX() / 100) * 100;
        y = Math.round(movingChip.getY() / 100) * 100;

        doNotReleaseTheChipOutsideTheField();

        movingChip.setX(x);
        movingChip.setY(y);

        movingChip = null;

        if (isWin()) {
            label.setText("You won!");
        }
    }

    public void startNewGame() {
        label.setText("New Game");

        if (chips.size() != 0) {
            pane.getChildren().removeAll(chips);
            chips.clear();
        }

        List<Integer> usedKeys = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 5; i++) {
            chips.add(RectangleUtil.drawRedChip(pane, points.get(getRandomKey(usedKeys))));
            chips.add(RectangleUtil.drawYellowChip(pane, points.get(getRandomKey(usedKeys))));
            chips.add(RectangleUtil.drawGreenChip(pane, points.get(getRandomKey(usedKeys))));
        }

        usedKeys.clear();
    }

    private int getRandomKey(List<Integer> usedKeys) {
        if (usedKeys == null) {
            return 0;
        }
        int newKey;
        do {
            newKey = new Random().nextInt(15) + 1;
        } while (usedKeys.contains(newKey));
        usedKeys.add(newKey);

        return newKey;
    }

    private boolean isWin() {
        int winCount = 0;

        for (Node node : pane.getChildren()) {
            if (node.contains(50, 50) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.RED)) {
                winCount++;
            }
            if (node.contains(50, 150) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.RED)) {
                winCount++;
            }
            if (node.contains(50, 250) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.RED)) {
                winCount++;
            }
            if (node.contains(50, 350) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.RED)) {
                winCount++;
            }
            if (node.contains(50, 450) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.RED)) {
                winCount++;
            }
            if (node.contains(250, 50) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.YELLOW)) {
                winCount++;
            }
            if (node.contains(250, 150) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.YELLOW)) {
                winCount++;
            }
            if (node.contains(250, 250) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.YELLOW)) {
                winCount++;
            }
            if (node.contains(250, 350) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.YELLOW)) {
                winCount++;
            }
            if (node.contains(250, 450) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.YELLOW)) {
                winCount++;
            }
            if (node.contains(450, 50) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.GREEN)) {
                winCount++;
            }
            if (node.contains(450, 150) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.GREEN)) {
                winCount++;
            }
            if (node.contains(450, 250) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.GREEN)) {
                winCount++;
            }
            if (node.contains(450, 350) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.GREEN)) {
                winCount++;
            }
            if (node.contains(450, 450) && node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.GREEN)) {
                winCount++;
            }
        }

        return winCount == 15;
    }
}