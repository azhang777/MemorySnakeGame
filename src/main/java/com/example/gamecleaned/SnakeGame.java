package com.example.gamecleaned;

import javafx.scene.shape.Rectangle;


public class SnakeGame extends Game {
    //not my code
    private final double snakeSize = 50;
    private double xPosition;
    private double yPosition;

    public SnakeGame(double xPosition, double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    @Override
    public String toString() {
        return "Position{" +
                "xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}


