package com.algonquincollegelive.oh000024.hilo;

/**
 * Created by calcifer on 2017-09-25.
 */

public final class Game {

    public Integer guessCount;
    private String guess;
    private RangeRandomNumber secretNumberGenerator;
    private int state = 0;

    public static final int MAX_COUNT = 10;
    public static final int MAX_NUM = 1000;

    // RETURN VALUES

    public static final int UNKNOW_ERROR = -1;

    public static final int RESSET = 0;
    public static final int HIGH = 1;
    public static final int LOW = 2;
    public static final int SUPERIORWIN = 3;
    public static final int EXCELLENTWIN = 4;

    public final String messages[] = {"Please Reset!", "High", "Low", "Superior win!", "Excellent win!"};


    public Game() {
        // Object for RandomNumber
        secretNumberGenerator = new RangeRandomNumber();
        guess = ""; // Initialize
        guessCount = 0;
        state = 0;
    }

    public int PlayGame(Integer guessNum) {
//        Integer guessNum = 0;
        int ret = 0;
        try {
            guessCount++;

            if (guessNum > secretNumberGenerator.GetCurrentRandomNumber()) {
                ret = HIGH;

            } else if (guessNum < secretNumberGenerator.GetCurrentRandomNumber()) {
                ret = LOW;

            } else if (guessNum == secretNumberGenerator.GetCurrentRandomNumber()) {
                if (guessCount <= 5) {
                    ret = SUPERIORWIN;
                } else if (guessCount > 5 && guessCount <= 10) {
                    ret = EXCELLENTWIN;
                }
            }
            if (guessCount >= MAX_COUNT) {
                ret = RESSET;
            }


        } catch (Exception e)//NumberFormatException
        {
            //Console.Write("Invalid input: " + e.Message);
            return 0;
        } finally {

            return ret;
        }
    }

    public void InitGame() {
        guessCount = 0;
        secretNumberGenerator.GenerateRandomNumber();
    }

    public int getCount() {
        return guessCount;
    }

    public int getMagicNumber() {
        return secretNumberGenerator.GetCurrentRandomNumber();
    }

    public void setState(int mode) {
        state = mode;
    }

    public int getState() {
        return state;
    }
}
