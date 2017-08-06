package com.pikkart.trial.teratour;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pikkart.ar.recognition.IRecognitionListener;
import com.pikkart.ar.recognition.RecognitionFragment;
import com.pikkart.ar.recognition.RecognitionOptions;
import com.pikkart.ar.recognition.data.CloudRecognitionInfo;
import com.pikkart.ar.recognition.items.Marker;


import java.io.IOException;

/**
 * Created by root on 7/1/17.
 */

public class ImageCloudRecoClass extends AppCompatActivity implements IRecognitionListener, View.OnTouchListener{

    ARView m_arView;
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
        m_arView.setOnTouchListener(this);
        addContentView(m_arView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        RecognitionFragment _cameraFragment = ((RecognitionFragment) getFragmentManager().findFragmentById(R.id.pikkart_ar_fragment));
        _cameraFragment.startRecognition(
                new RecognitionOptions(
                        RecognitionOptions.RecognitionStorage.GLOBAL,
                        RecognitionOptions.RecognitionMode.TAP_TO_SCAN,
                        new CloudRecognitionInfo(new String[]{databaseName})
                ),
                this);

        showMarkerDetails("Govt. House Enugu Ind. Layout. Enugu");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        doRecognition();

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
