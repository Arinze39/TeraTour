package com.pikkart.trial.teratour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pikkart.trial.teratour.R;

public class SplashScreenActivity extends AppCompatActivity {
    long SplashScreenTimer = 3000;
    String AppName = "TeraTour";

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        //Draw the splash screen
        setContentView(R.layout.activity_splash_screen);

        //Create a thread to hold the splash screen for 3 sec
        Thread TimerThread = new Thread() {
            public void run() {
                try {
                    long time = 0; //declare the counter
                    //Check that the splash screen is active and time is not up to 3 sec
                    while (time < SplashScreenTimer) {
                        sleep(100); //Wait for 1 sec
                        time += 100;
                    }
                    //Advance to the next Screen
                    startActivity(new Intent("com.pikkart.trial.teratour.signIn"));

                } catch (Exception e) {
                    Toast.makeText(SplashScreenActivity.this,"Error starting" + AppName + "\nTry shutting down other apps before restarting this app", Toast.LENGTH_SHORT).show();
                }
                finally {
                    finish();
                }

            }

        };
        TimerThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
