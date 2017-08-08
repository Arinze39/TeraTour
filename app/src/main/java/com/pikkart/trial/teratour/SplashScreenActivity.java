package com.pikkart.trial.teratour;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;


public class SplashScreenActivity extends AppCompatActivity {
    long SplashScreenTimer = 2000;
    String AppName = "TeraTour";

    //declare facebook CallBackManager
   // CallbackManager mFacebookCallbackManager;
//    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    TwitterSession mTwitterSession;

//    TwitterAuthToken mTwitterAuthToken;
//    boolean mLoggedIn;
//    String mTwiterToken;
//    String mTwitterSecret;

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        //Facebook SDK Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        Twitter.initialize(this);
        //mFacebookCallbackManager = CallbackManager.Factory.create();

        //Draw the splash screen
        setContentView(R.layout.activity_splash_screen);

     /*   accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                accessToken = currentAccessToken;
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

        mLoggedIn = accessToken != null;


        mTwitterAuthToken = mTwitterSession.getAuthToken();
        mTwiterToken = mTwitterAuthToken.token;
        mTwitterSecret = mTwitterAuthToken.secret;     */

        //Create a thread to hold the splash screen for 2 sec
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
                    if(IsUserLoggedIn())
                    {
                        startActivity(new Intent(getBaseContext(),ImageCloudRecoClass.class));
                    }
                    else{
                        startActivity(new Intent(getBaseContext(),SignInActivity.class));
                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Error starting" + AppName +
                            "\nTry shutting down other apps before restarting this app", Toast.LENGTH_SHORT).show();
                    finish();
                }
                finally {
                    finish();
                }

            }

        };
        TimerThread.start();
    }

    private boolean IsUserLoggedIn(){
        boolean loggedIn = false;
        boolean FbLoggedIn;
        boolean TwitterLoggedIn;

        try{
            accessToken = AccessToken.getCurrentAccessToken();
            mTwitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
            FbLoggedIn = accessToken != null;
            TwitterLoggedIn = mTwitterSession != null;

            loggedIn = FbLoggedIn || TwitterLoggedIn;


        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();

        }
        return loggedIn;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }
    @Override
    public void onResume()
    {
        super.onResume();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
//        accessTokenTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
