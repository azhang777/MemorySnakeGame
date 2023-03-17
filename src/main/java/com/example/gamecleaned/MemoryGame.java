package com.example.gamecleaned;

import javafx.scene.shape.Rectangle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;



public class MemoryGame extends Game {
    ArrayList<Integer> sequence = new ArrayList<>(); //new ArrayList<Integer>() does not work?
    ArrayList<Integer> playerSequence = new ArrayList<>(); //these arraylists needs to be moved to MemoryGame
    Random generator = new Random(); //move to MemoryGame

    void generateSequence() { // 04/24 there is some issue with this.
        for (int i = 0; i < getCurrentScore() + 1; i++) {
            int num = generator.nextInt(1, 5);
            sequence.add(i, num); //stores each generated number in index i
        }
    }

    void indicateSequence(ArrayList<Rectangle> boxes) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        long secondsToSleep = 2;
        for (int i = 0; i < getCurrentScore() + 1; i++) {
            boxes.get(sequence.get(i) - 1).setStrokeWidth(10);
            System.out.println(boxes.get(sequence.get(i) - 1).toString() + dateTimeFormat.format(now));
            try {
                Thread.sleep(secondsToSleep * 1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            System.out.println("I have waited!!" + dateTimeFormat.format(now));
            boxes.get(sequence.get(i) - 1).setStrokeWidth(0);
        }
    } //no clue on how to delay resetting stroke width.

    /*
    results from terminal
    Rectangle[id=greenBox, x=0.0, y=0.0, width=191.0, height=191.0, fill=0x00ff00ff, stroke=0x000000ff, strokeWidth=10.0]2022/04/27 18:24:47
    I have waited!!2022/04/27 18:24:47

seems like Thread.sleep does not work...
     */
    boolean compareSequence() {
        if (sequence.equals(playerSequence)) {
            setCurrentScore(getCurrentScore() + 1);
            return true;
        } else return false;
    }

    void successfulAttempt(ArrayList<Rectangle> boxes) {

        sequence.clear();
        playerSequence.clear();
        generateSequence();
        indicateSequence(boxes);
    }

    void failedAttempt(ArrayList<Rectangle> boxes) {
        sequence.clear();
        playerSequence.clear();
        setCurrentScore(0);
        generateSequence();
        indicateSequence(boxes);
    }

    String displaySequence() {
        return sequence.toString();

    }
}