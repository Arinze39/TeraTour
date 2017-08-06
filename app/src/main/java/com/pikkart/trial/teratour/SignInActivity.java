package com.pikkart.trial.teratour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    //declare facebook callbackmanager
    CallbackManager mFacebookCallbackManager;
    LoginButton mFacebookSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Facebook SDK Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_sign_in);

        //Setup the callback for facebook button
        mFacebookSignInButton = (LoginButton)findViewById(R.id.facebook_sign_in_button);
        mFacebookSignInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        //TODO: Use the Profile class to get information about the current user.

                        try
                        {
                            //Advance to the next Screen
                            startActivity(new Intent(getBaseContext(),MainActivity.class));
                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(SignInActivity.this, "Error starting Teratour\n" +
                                    "Try shutting down other apps before restarting this app", Toast.LENGTH_SHORT).show();
                        }
                        finally {
                            finish();
                        }

//                        handleSignInResult(new Callable<Void>() {
//                            @Override
//                            public Void call() throws Exception {
//                                LoginManager.getInstance().logOut();
//                                return null;
//                            }
//                        });
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

    public void logIn(View v){

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
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
