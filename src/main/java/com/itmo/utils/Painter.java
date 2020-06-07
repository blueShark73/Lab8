package com.itmo.utils;

import com.itmo.client.StudyGroupForUITable;
import com.itmo.client.controllers.MainController;
import com.itmo.server.notifications.ServerNotification;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Painter {
    private static final double HEIGHT = 24, WIDTH = 24;
    private Canvas canvas;
    private ArrayBlockingQueue<ServerNotification> queue = new ArrayBlockingQueue<>(1000);

    public Painter(Canvas canvas) {
        this.canvas = canvas;
    }

    public void run(MainController mainController) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    ServerNotification serverNotification = queue.take();
                    serverNotification.updateData(mainController);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void addNotification(ServerNotification notification) {
        queue.add(notification);
    }

    public void executeTasks(MainController mainController) {
        queue.forEach(notification -> notification.updateData(mainController));
        queue.clear();
    }

    public void drawElement(int x, int y, int size, Color color) {
        x = fromNormalXToCanvasX(x);
        y = fromNormalYToCanvasY(y);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(color);
        drawHuman(x, y, 1);
        if (size == 1) return;
        x -= 6;
        drawHuman(x, y, 1);
        x += 12;
        drawHuman(x, y, 1);
        if (size < 11) return;
        x -= 18;
        drawHuman(x, y, 1);
        x += 24;
        drawHuman(x, y, 1);
    }

    private void drawHuman(int x, int y, double kf) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.fillOval(x - 3 * kf, y - 3 * kf, 6 * kf, 6 * kf);
        graphicsContext.strokeLine(x, y + 3 * kf, x, y + 8 * kf);
        graphicsContext.strokeLine(x, y + 8 * kf, x - 3 * kf, y + 12 * kf);
        graphicsContext.strokeLine(x, y + 8 * kf, x + 3 * kf, y + 12 * kf);
        graphicsContext.strokeLine(x, y + 3 * kf, x + 3 * kf, y + 9 * kf);
        graphicsContext.strokeLine(x, y + 3 * kf, x - 3 * kf, y + 9 * kf);
    }

    public void drawAxis() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(1);
        graphicsContext.strokeLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight());
        graphicsContext.strokeLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
    }

    public int fromNormalXToCanvasX(int x) {
        return (int) canvas.getWidth() / 2 + x;
    }

    public int fromNormalYToCanvasY(int y) {
        return (int) canvas.getHeight() / 2 - y;
    }

    public int calculateDistance(int x1, int x2, int y1, int y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void redraw(ObservableList<StudyGroupForUITable> list) {
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawAxis();
        for (StudyGroupForUITable studyGroupForUITable : list) {
            Color color = Color.color(studyGroupForUITable.getRed(), studyGroupForUITable.getGreen(), studyGroupForUITable.getBlue());
            drawElement(studyGroupForUITable.getX().intValue(), studyGroupForUITable.getY().intValue(), studyGroupForUITable.getStudentsCount().intValue(), color);
        }
    }

    private void drawElement(int x, int y, int size, Color color, double kf) {
        x = fromNormalXToCanvasX(x);
        y = fromNormalYToCanvasY(y);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.setLineWidth(1 * kf);
        graphicsContext.setStroke(color);
        drawHuman(x, y, kf);
        if (size == 1) return;
        x -= 6 * kf;
        drawHuman(x, y, kf);
        x += 12 * kf;
        drawHuman(x, y, kf);
        if (size < 11) return;
        x -= 18 * kf;
        drawHuman(x, y, kf);
        x += 24 * kf;
        drawHuman(x, y, kf);
    }

    private void drawPortal(double x, double y, double w, double h, double xDif, double yDif) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(w / 12);
        graphicsContext.setStroke(Color.RED);
        graphicsContext.strokeOval(x, y, w, h);
        graphicsContext.setStroke(Color.ORANGE);
        graphicsContext.strokeOval(x + xDif / 6, y + yDif / 6, 5 * w / 6, 5 * h / 6);
        graphicsContext.setStroke(Color.YELLOW);
        graphicsContext.strokeOval(x + xDif / 3, y + yDif / 3, w * 2 / 3, h * 2 / 3);
        graphicsContext.setStroke(Color.GREEN);
        graphicsContext.strokeOval(x + xDif / 2, y + yDif / 2, w / 2, h / 2);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.strokeOval(x + 2 * xDif / 3, y + 2 * yDif / 3, w / 3, h / 3);
        graphicsContext.setStroke(Color.VIOLET);
        graphicsContext.strokeOval(x + 5 * xDif / 6, y + 5 * yDif / 6, w / 6, h / 6);
    }

    private void createPortal(double x, double y, double leftUpX, double leftUpY, ObservableList<StudyGroupForUITable> list) throws InterruptedException {
        while (leftUpX > x - WIDTH / 2 || leftUpY > y - HEIGHT / 2) {
            Thread.sleep(50);
            leftUpX -= WIDTH / 5;
            leftUpY -= HEIGHT / 5;
            double finalLeftUpX = leftUpX, finalLeftUpY = leftUpY;
            Platform.runLater(() -> {
                redraw(list);
                drawPortal(finalLeftUpX, finalLeftUpY, (x - finalLeftUpX) * 2, (y - finalLeftUpY) * 2, x - finalLeftUpX, y - finalLeftUpY);
            });
        }
    }

    private void hidePortal(double x, double y, double leftUpX, double leftUpY, StudyGroupForUITable studyGroupForUITable, ObservableList<StudyGroupForUITable> list, Color color, boolean showGroup) throws InterruptedException {
        while (leftUpX < x || leftUpY < y) {
            Thread.sleep(50);
            leftUpX += WIDTH / 5;
            leftUpY += HEIGHT / 5;
            double finalLeftUpX = leftUpX, finalLeftUpY = leftUpY;
            Platform.runLater(() -> {
                redraw(list);
                drawPortal(finalLeftUpX, finalLeftUpY, (x - finalLeftUpX) * 2, (y - finalLeftUpY) * 2, x - finalLeftUpX, y - finalLeftUpY);
                if (showGroup)
                    drawElement(studyGroupForUITable.getX().intValue(), studyGroupForUITable.getY().intValue(), studyGroupForUITable.getStudentsCount().intValue(), color);
            });
        }
    }

    @SneakyThrows
    public void drawWithAdding(StudyGroupForUITable studyGroupForUITable, ObservableList<StudyGroupForUITable> list) {
        int x = fromNormalXToCanvasX(studyGroupForUITable.getX().intValue()), y = fromNormalYToCanvasY(studyGroupForUITable.getY().intValue());
        double leftUpX = x, leftUpY = y, kf = 0;
        Color color = Color.color(studyGroupForUITable.getRed(), studyGroupForUITable.getGreen(), studyGroupForUITable.getBlue());
        createPortal(x, y, leftUpX, leftUpY, list);
        leftUpX = x - WIDTH / 2;
        leftUpY = y - HEIGHT / 2;
        while (kf < 1) {
            Thread.sleep(50);
            kf += 0.25;
            workWithHumans(leftUpX, leftUpY, x, y, studyGroupForUITable, list, kf, color);
        }
        hidePortal(x, y, leftUpX, leftUpY, studyGroupForUITable, list, color, true);
    }

    @SneakyThrows
    public void drawWithRemoving(StudyGroupForUITable studyGroupForUITable, ObservableList<StudyGroupForUITable> list) {
        int x = fromNormalXToCanvasX(studyGroupForUITable.getX().intValue()), y = fromNormalYToCanvasY(studyGroupForUITable.getY().intValue());
        double leftUpX = x, leftUpY = y, kf = 1;
        Color color = Color.color(studyGroupForUITable.getRed(), studyGroupForUITable.getGreen(), studyGroupForUITable.getBlue());
        createPortal(x, y, leftUpX, leftUpY, list);
        leftUpX = x - WIDTH / 2;
        leftUpY = y - HEIGHT / 2;
        while (kf > 0) {
            Thread.sleep(50);
            kf -= 0.25;
            workWithHumans(leftUpX, leftUpY, x, y, studyGroupForUITable, list, kf, color);
        }
        hidePortal(x, y, leftUpX, leftUpY, studyGroupForUITable, list, color, false);
        Platform.runLater(() -> redraw(list));
    }

    private void workWithHumans(double leftUpX, double leftUpY, double x, double y, StudyGroupForUITable studyGroupForUITable, ObservableList<StudyGroupForUITable> list, double kf, Color color) {
        Platform.runLater(() -> {
            redraw(list);
            drawPortal(leftUpX, leftUpY, (x - leftUpX) * 2, (y - leftUpY) * 2, x - leftUpX, y - leftUpY);
            drawElement(studyGroupForUITable.getX().intValue(), studyGroupForUITable.getY().intValue(), studyGroupForUITable.getStudentsCount().intValue(), color, kf);
        });
    }
}
