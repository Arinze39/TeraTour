package com.pikkart.trial.teratour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    //declare facebook callbackmanager
    CallbackManager mFacebookCallbackManager;
    LoginButton mFacebookLogInButton;
    TwitterLoginButton mTwitterloginButton;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Facebook SDK Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        Twitter.initialize(this);
        mFacebookCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_sign_in);


        //set the callback for twitter button
        mTwitterloginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        mTwitterloginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //TODO: Do something with result, which provides a TwitterSession for making API calls
                HandleSignIn();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d(SignInActivity.class.getCanonicalName(), exception.getMessage());
            }
        });

        //Setup the callback for facebook button
        mFacebookLogInButton = (LoginButton)findViewById(R.id.facebook_sign_in_button);
        mFacebookLogInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        //TODO: Use the Profile class to get information about the current user.
                        HandleSignIn();
                    }

                    @Override
                    public void onCancel() {
                        //LoginManager.getInstance().logOut();
                        //handleSignInResult(null);
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(SignInActivity.class.getCanonicalName(), error.getMessage());
                        //LoginManager.getInstance().logOut();
                        //handleSignInResult(null);
                    }
                }
        );
    }

    public void HandleSignIn(){
        try
        {
            //Advance to the next Screen
            startActivity(new Intent(getBaseContext(),ImageCloudRecoClass.class));
        }
        catch (Exception ex)
        {
            Toast.makeText(SignInActivity.this, "Error starting Teratour\n" +
                    "Try shutting down other apps before restarting this app", Toast.LENGTH_SHORT).show();
        }
        finally {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the FacebookLogin button
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the TwitterLogin button.
        mTwitterloginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
