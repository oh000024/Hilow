package com.algonquincollegelive.oh000024.hilo;

import android.content.Intent;
import android.graphics.Interpolator;

/**
 * Created by calcifer on 2017-09-25.
 */

public class RangeRandomNumber  extends RandomNumber
{

    protected Integer minimum;
    protected Integer maximum;


    public RangeRandomNumber() {
        minimum = 0;
        maximum = 1000;
    }

    public RangeRandomNumber (Integer minimum, Integer maximum) {

        SetMaximun(maximum);
        SetMinmun(minimum);

    }

    public void SetMaximun(Integer maximum) {
        if(maximum < 1 ){
            maximum = 1;
        }
        this.maximum = maximum;

    }
    public void SetMinmun(Integer minimum) {

        if(minimum < 0){
            minimum = 0;
        }
        this.minimum = minimum;
    }
    public int GenerateRandomNumber(){

        if(maximum < minimum){
            int temp = minimum;
            minimum = maximum;
            maximum = temp;
        } else if( maximum == minimum) {
            return maximum;
        }
        theNumber = random.nextInt((maximum - minimum) + 1);
        return 0;
    }

    /**
     * Getter and Setter
     */
    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }


    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }


}