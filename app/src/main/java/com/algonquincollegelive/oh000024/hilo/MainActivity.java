/**
 * Guessing a number game
 *
 * @author Jake Oh (oh000024@algonquinlive.com)
 */

package com.algonquincollegelive.oh000024.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    static Game game = new Game();
    RangeRandomNumber r = new RangeRandomNumber();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guessbut = (Button) findViewById(R.id.guessbtn);
        Button resetbut = (Button) findViewById(R.id.resetbtn);

        TextView countlabel = (TextView) findViewById(R.id.guessCoundID);
        TextView label = (TextView) findViewById(R.id.magicnumID);
        label.setText("Magic Number:???");


        game.InitGame();
        Toast.makeText(getApplicationContext(), String.valueOf(game.getMagicNumber()), Toast.LENGTH_SHORT).show();

        Toast.makeText(getApplicationContext(), "Start a New Game", Toast.LENGTH_LONG).show();

        // Resetting a Config
        resetbut.setOnClickListener(new Button.OnClickListener() {

            // Click Rrest Button: Initialize all setting
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // Initialize Setting
                game.InitGame();

                // Setting Text
                TextView label = (TextView) findViewById(R.id.magicnumID);
                label.setText("Magic Number:???");

                // Enable Guess Button
                Button guessbut = (Button) findViewById(R.id.guessbtn);
                guessbut.setEnabled(true);
                guessbut.setText("GUESS");

                EditText inputBox = (EditText) findViewById(R.id.inputBoxID);
                inputBox.setText("");

                TextView countView = (TextView) findViewById(R.id.guessCoundID);
                countView.setText("Count: 0");
            }
        });

        // Showing Magic Number
        resetbut.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                TextView label = (TextView) findViewById(R.id.magicnumID);
                label.setText(Integer.toString(game.getMagicNumber()));
                return true;
            }
        });

    }

    public boolean CheckValidation(String value) {

        for (char c : value.toCharArray()) {

            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param v
     * Handle of Guess Button
     */
    public void handleGuess(View v) {

        EditText inputBox = (EditText) findViewById(R.id.inputBoxID);
        TextView label = (TextView) findViewById(R.id.magicnumID);
        try {

            if (!CheckValidation(inputBox.getText().toString())) {
                inputBox.setError("It is not digit! ");
                inputBox.requestFocus();
                return;
            }


            int userGuess = Integer.parseInt(inputBox.getText().toString());

            if (userGuess > game.MAX_NUM) {
                inputBox.setError("Max Value is 1000.Input a number under 1000.");
                inputBox.requestFocus();
                return;
            }

            // Checking Guessing nuber
            int ret = game.PlayGame(userGuess);

            if (ret != game.UNKNOW_ERROR) {
                String message = game.messages[ret];

                TextView guessCount = (TextView) findViewById(R.id.guessCoundID);
                guessCount.setText(String.format("Count: %d", game.getCount()));

                if (ret == game.SUPERIORWIN || ret == game.EXCELLENTWIN) {
                    ((Button) v).setEnabled(false);
                    ((Button) v).setText("Please Reset");
                    label.setText("You won!!" + "The number is " + Integer.toString(game.getMagicNumber()));
                    label.setTextSize(24);
                    label.setTypeface(null, Typeface.BOLD_ITALIC);

                }else  if (ret == game.RESSET) {
                    ((Button) v).setEnabled(false);
                    ((Button) v).setText("Please Reset");
                    //label.setTextColor(Color.parseColor(Integer.toString(R.color.colorTextDart)));
                    label.setText("You Lose!!" +  "The number is "+Integer.toString(game.getMagicNumber()));
                    label.setTextSize(24);
                    label.setTypeface(null, Typeface.BOLD_ITALIC);

                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "UNKNOW ERROR IS HAPPENED\n PLEASE, RESET IT", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            inputBox.setError("Invalid Input. Input Less then 1000.");
            inputBox.requestFocus();
        } finally {
            inputBox.setSelectAllOnFocus(true);
            inputBox.selectAll();
        }


    }
}
