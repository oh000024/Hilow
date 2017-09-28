package com.algonquincollegelive.oh000024.hilo;

import java.util.Random;
import java.util.UUID;

/**
 * Created by calcifer on 2017-09-25.
 */

public class RandomNumber {

    protected Random random;
    protected int theNumber;

    public RandomNumber()
    {
        theNumber = 0; // save a random number

        String  uniqueID = UUID.randomUUID().toString();
        long hexvlue = Long.parseLong(uniqueID.substring(0,8), 16);
        random = new Random(hexvlue);

        //Setting Seed
        //random = new Random(int.Parse(Guid.NewGuid().ToString().Substring(0, 8), System.Globalization.NumberStyles.HexNumber));
    }

    public int GenerateRandomNumber()
    {
        theNumber = random.nextInt();
        return theNumber;
    }

    public int GetCurrentRandomNumber() { return theNumber; }
}

