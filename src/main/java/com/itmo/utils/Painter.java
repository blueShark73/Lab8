package com.itmo.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Painter {
    private Canvas canvas;

    public void drawElement(int x, int y, int size, Color color) {
        x = fromNormalXToCanvasX(x);
        y = fromNormalYToCanvasY(y);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(color);
        drawHuman(x, y);
        if (size == 1) return;
        x -= 6;
        drawHuman(x, y);
        x += 12;
        drawHuman(x, y);
        if (size < 11) return;
        x -= 18;
        drawHuman(x, y);
        x += 24;
        drawHuman(x, y);
    }

    private void drawHuman(int x, int y) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.fillOval(x - 3, y - 3, 6, 6);
        graphicsContext.strokeLine(x, y + 3, x, y + 8);
        graphicsContext.strokeLine(x, y + 8, x - 3, y + 12);
        graphicsContext.strokeLine(x, y + 8, x + 3, y + 12);
        graphicsContext.strokeLine(x, y + 3, x + 3, y + 9);
        graphicsContext.strokeLine(x, y + 3, x - 3, y + 9);
    }

    public void drawAxis() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Color.BLACK);
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
}
