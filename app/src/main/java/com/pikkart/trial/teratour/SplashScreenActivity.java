package com.pikkart.trial.teratour;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;

import retrofit2.Call;


public class SplashScreenActivity extends AppCompatActivity implements View.OnTouchListener {
    long SplashScreenTimer = 2500;
    AccessToken accessToken;
    TwitterSession mTwitterSession;
    String mPhotoUrlOriginalSize;
    String mName;



    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Draw the splash screen
        setContentView(R.layout.activity_splash_screen);

        if(!isConnectionAvailable()){
            Toast.makeText(this,"Connection to internet unavailable.",Toast.LENGTH_LONG).show();
        }

        Thread TimerThread = new Thread() {
            public void run() {
                try {
                    long time = 0; //declare the counter

                    //Check that the splash screen is active and time is not up to 3 sec
                    while (time < SplashScreenTimer) {//
                        sleep(100); //Wait for 1 sec
                        time += 100;
                        //if(time == 7000)break;
                    }
                } catch (Exception e) {
                    Log.e("Error ", "Cannot Start activity", e);
                    finish();
                }
            }

        };TimerThread.start();


        //Facebook SDK Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        Twitter.initialize(this);

        if(TwitterLogIn()) {

            final Bundle Data = new Bundle();
            final Intent intent = new Intent(SplashScreenActivity.this, ImageCloudRecoClass.class);

            Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false, false);
            user.enqueue(new Callback<User>() {
                @Override
                public void success(Result<User> userResult) {
                    //get Name of the user logged in
                    mName = userResult.data.name;
                    mPhotoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");

                    //Save these data to be passed to Another Activity.
                    Data.putString("ProfilePicture", mPhotoUrlOriginalSize);
                    Data.putString("Name", mName);

                    //Pass the data to the Activity declared in the Intent.
                    intent.putExtras(Data);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void failure(TwitterException exc) {
                    Log.e("TwitterKit", "Verify Credentials Failure", exc);
                }
            });
        }
        else if(FbLogIn()){
            startActivity(new Intent(SplashScreenActivity.this, ImageCloudRecoClass.class));
            finish();
        }
        else {
            startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
            finish();
        }
   }

    public boolean isConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

   @Override
   public void onResume(){
        super.onResume();

    }

    private boolean FbLogIn() {
        boolean loggedIn = false;
        boolean FbLoggedIn;
        try {
            accessToken = AccessToken.getCurrentAccessToken();
            FbLoggedIn = accessToken != null;
            loggedIn = FbLoggedIn;
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return loggedIn;
    }

    private boolean TwitterLogIn(){
        boolean loggedIn = false;
        boolean TwitterLoggedIn;
        try{
            mTwitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
            TwitterLoggedIn = mTwitterSession != null;
            loggedIn = TwitterLoggedIn;
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return loggedIn;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return false;
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
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if(!isConnectionAvailable()){
                Toast.makeText(this,"Connection to internet unavailable.",Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

}
