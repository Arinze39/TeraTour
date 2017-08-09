package com.pikkart.trial.teratour;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.pikkart.ar.recognition.IRecognitionListener;
import com.pikkart.ar.recognition.RecognitionFragment;
import com.pikkart.ar.recognition.RecognitionOptions;
import com.pikkart.ar.recognition.data.CloudRecognitionInfo;
import com.pikkart.ar.recognition.items.Marker;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;


import java.io.IOException;

/**
 * Created by root on 7/1/17.
 */

public class ImageCloudRecoClass extends AppCompatActivity implements IRecognitionListener, View.OnTouchListener{

    ARView m_arView;
    ImageView image;
    public static String customData;
    private String databaseName = "artdatabase_314";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }


    private void initLayout()
    {
        setContentView(R.layout.activity_main);
        m_arView = new ARView(this);
        image = new ImageView(this);
        m_arView.setOnTouchListener(this);
        image.setOnTouchListener(this);
        draw(image, R.drawable.image1);
        addContentView(m_arView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addContentView(image, new FrameLayout.LayoutParams(160, 160));
        RecognitionFragment _cameraFragment = ((RecognitionFragment) getFragmentManager().findFragmentById(R.id.pikkart_ar_fragment));
        _cameraFragment.startRecognition(
                new RecognitionOptions(
                        RecognitionOptions.RecognitionStorage.GLOBAL,
                        RecognitionOptions.RecognitionMode.CONTINUOUS_SCAN,
                        new CloudRecognitionInfo(new String[]{databaseName})
                ),
                this);

        HideActionBar();

        showMarkerDetails("Govt. House Enugu Ind. Layout. Enugu");
    }

    private void HideActionBar(){

        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    // Set up the action bar.
                    final ActionBar actionBar = getSupportActionBar();
                    actionBar.hide();
                    image.setVisibility(View.INVISIBLE);
                } catch (Exception ex) {
                    Log.e("ERROR", ex.getMessage());
                }
            }
        }, 3000);
    }

    public void draw(ImageView imageView, int Rrs){

        Resources res = getResources();
        Bitmap src = BitmapFactory.decodeResource(res, Rrs);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, src);
        dr.setCircular(true);
        imageView.setImageDrawable(dr);
    }

    private void showMarkerDetails(String Title) {
        FragmentManager fm = getSupportFragmentManager();
        MarkerDetailsDialogFragment markerDetailsDialogFragment = MarkerDetailsDialogFragment.newInstance(Title);
        markerDetailsDialogFragment.show(fm, "fragment_edit_name");

    }

    private void doRecognition(){

        RecognitionFragment _cameraFragment = ((RecognitionFragment) getFragmentManager().findFragmentById(R.id.pikkart_ar_fragment));
        _cameraFragment.startRecognition(
                new RecognitionOptions(
                        RecognitionOptions.RecognitionStorage.GLOBAL,
                        RecognitionOptions.RecognitionMode.TAP_TO_SCAN,
                        new CloudRecognitionInfo(new String[]{databaseName})
                ),
                this);
    }

    public void logoutTwitter() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (twitterSession != null) {
//            ClearCookies(getApplicationContext());
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            //Twitter.logOut();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecognitionFragment _cameraFragment = ((RecognitionFragment) getFragmentManager().findFragmentById(R.id.pikkart_ar_fragment));
        _cameraFragment.startRecognition(
                new RecognitionOptions(
                        RecognitionOptions.RecognitionStorage.GLOBAL,
                        RecognitionOptions.RecognitionMode.TAP_TO_SCAN,
                        new CloudRecognitionInfo(new String[]{databaseName})
                ),
                this);

        if(m_arView!=null) m_arView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(m_arView!=null) m_arView.onPause();
    }

    private void ShowProgressDialog(){
        final ProgressDialog dialog = ProgressDialog.show(this,"","Signing Out",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(500);
                    dialog.dismiss();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if(view == image){
            try {
                ShowProgressDialog();
                LoginManager.getInstance().logOut();
                logoutTwitter();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            } catch (Exception e) {
                Log.e("LogOutError", e.getMessage());
            }
        }
        else{
            try {
                // Set up the action bar.
                final ActionBar actionBar = getSupportActionBar();
                if (actionBar.isShowing()) {
                    actionBar.hide();
                    image.setVisibility(View.INVISIBLE);
                } else {
                    actionBar.show();
                    image.setVisibility(View.VISIBLE);
                    HideActionBar();
                }
            }
            catch (Exception ex){
                Log.e("ERROR",ex.getMessage());
            }
        }
//        doRecognition();
        return false;
    }

    @Override
    public void executingCloudSearch() {
        //Toast.makeText(this, "Scanning...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cloudMarkerNotFound() {
        //Toast.makeText(this, "cloudMarkerNotFound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void internetConnectionNeeded() {

    }

    @Override
    public void markerFound(Marker marker) {
        Toast.makeText(this, "Found" + marker.getId(), Toast.LENGTH_SHORT).show();
        //customData = marker.getCustomData();
        //Log.d("customDatamsg", customData);
        //System.out.print(customData);
    }

    @Override
    public void markerNotFound() {

    }

    @Override
    public void markerTrackingLost(String s) {

    }

    @Override
    public void ARLogoFound(String s, int i) {

    }

    @Override
    public boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
