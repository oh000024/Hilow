/**
 *  Guessing a number game
 *  @author Jake Oh (oh000024@algonquinlive.com)
 */

package com.algonquincollegelive.oh000024.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.InputMismatchException;
import java.util.StringTokenizer;

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    RangeRandomNumber r = new RangeRandomNumber();
    static Game game = new Game();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guessbut = (Button)findViewById(R.id.guessbtn);
        Button resetbut = (Button)findViewById(R.id.resetbtn);

        TextView countlabel = (TextView)findViewById(R.id.guessCoundID);
        TextView label = (TextView)findViewById(R.id.magicnumID);
        label.setText("Magic Number:???");


        game.InitGame();
        Toast.makeText(getApplicationContext(), String.valueOf(game.getMagicNumber()),Toast.LENGTH_SHORT).show();

        Toast.makeText(getApplicationContext(),"Start a New Game", Toast.LENGTH_LONG ).show();

        // Resetting a Config
        resetbut.setOnClickListener(new Button.OnClickListener(){

            // Click Rrest Button: Initialize all setting
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // Initialize Setting
                game.InitGame();

                // Setting Text
                TextView label = (TextView)findViewById(R.id.magicnumID);
                label.setText("Magic Number:???");


                // Enable Guess Button
                Button guessbut = (Button) findViewById(R.id.guessbtn);
                guessbut.setEnabled(true);

                EditText inputBox = (EditText) findViewById(R.id.inputBoxID);
                inputBox.setText("");

                TextView countView = (TextView) findViewById(R.id.guessCoundID);
                countView.setText("Count: 0");
            }
        });

        // Showing Magic Number
        resetbut.setOnLongClickListener(new Button.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {

                TextView label = (TextView)findViewById(R.id.magicnumID);
                label.setText(Integer.toString(game.getMagicNumber()));
                return false;
            }
        });

    }

    public boolean CheckValidation(String value){

        for (char c : value.toCharArray()) {

            if(!Character.isDigit(c)){
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

    public void handleGuess(View v) {

        EditText inputBox = (EditText) findViewById(R.id.inputBoxID);
        try {

            if(!CheckValidation(inputBox.getText().toString())){
                inputBox.setError("It is not digit! ");
                inputBox.requestFocus();
                return;
            }


            int userGuess = Integer.parseInt(inputBox.getText().toString());

            if(userGuess > game.MAX_NUM){
                inputBox.setError("Max Value is 1000.");
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
                    TextView label = (TextView) findViewById(R.id.magicnumID);
                    label.setText(Integer.toString(game.getMagicNumber()));
                    label.setTextSize(40);
                    label.setTypeface(null, Typeface.BOLD_ITALIC);

                }

                if (ret == game.RESSET) {
                    ((Button) v).setEnabled(false);
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "UNKNOW ERROR IS HAPPENED\n PLEASE, RESET IT", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e){
            inputBox.setError("Invalid Input. Input Less then 1000.");
            inputBox.requestFocus();
        }
        finally {
            inputBox.setSelectAllOnFocus(true);
            inputBox.selectAll();
        }



    }
}
