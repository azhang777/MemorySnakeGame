package com.example.gamecleaned;

import java.io.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private int currentScore, highestScore = 0;

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    int compareScore() {
        if (currentScore > highestScore) {
            highestScore = currentScore;
            return highestScore;
        } else return highestScore;
    }

    public void checkRecord(String filename) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(filename));
        String lastLine = null;
        String line;

        while ((line = input.readLine()) != null) {
            lastLine = line;
        }
        highestScore = Integer.parseInt(lastLine);
        System.out.println("file input has been called!");

        if (currentScore > highestScore) {
            File recordScore = new File(filename);
            FileWriter fileWriter = new FileWriter(recordScore, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            System.out.println("New data has been written!");
            printWriter.write(currentScore + "\n");
            printWriter.close();
        }
    }

    String retrieveRecord(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String lastLine = null;
        String line;

        while ((line = reader.readLine()) != null) {
            lastLine = line;
        }
        assert lastLine != null;
        highestScore = Integer.parseInt(lastLine);
        return lastLine;
    }
}
