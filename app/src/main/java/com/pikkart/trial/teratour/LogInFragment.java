package com.pikkart.trial.teratour;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pikkart.trial.teratour.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends DialogFragment {

    //declare facebook callbackmanager
    CallbackManager mFacebookCallbackManager;
    LoginButton mFacebookSignInButton;

    public LogInFragment() {
        // Required empty public constructor
    }

    public static LogInFragment newInstance(String title) {
        LogInFragment frag = new LogInFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
//        Title = title;
        return frag;
    }

    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE, theme = android.R.style.Theme_DeviceDefault_Light_Dialog;
        setStyle(style,theme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFacebookSignInButton = (LoginButton)getActivity().findViewById(R.id.facebook_sign_in_button);
        mFacebookSignInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        //TODO: Use the Profile class to get information about the current user.

                        try {
                            //Advance to the next Screen
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Error starting Teratour\n" +
                                    "Try shutting down other apps before restarting this app", Toast.LENGTH_SHORT).show();
                        } finally {
                           getActivity().finish();
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

    }
