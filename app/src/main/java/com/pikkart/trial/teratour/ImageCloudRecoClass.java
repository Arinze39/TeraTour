package com.pikkart.trial.teratour;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Transformation;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.net.URL;

import retrofit2.http.Url;


/**
 * Created by root on 7/1/17.
 */

public class ImageCloudRecoClass extends AppCompatActivity implements IRecognitionListener, View.OnTouchListener{

    ARView m_arView;
    ImageView image;
    public static String customData;
    private String databaseName = "artdatabase_314";
    String Name,ProfilePic,Email,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initLayout();
    }


    private void initLayout(){

        setContentView(R.layout.activity_main);
        m_arView = new ARView(this);
        image = new ImageView(this);
        m_arView.setOnTouchListener(this);
        image.setOnTouchListener(this);
        draw(image, ProfilePic);
        addContentView(m_arView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addContentView(image, new FrameLayout.LayoutParams(100, 100));
        RecognitionFragment _cameraFragment = ((RecognitionFragment) getFragmentManager().findFragmentById(R.id.pikkart_ar_fragment));
        _cameraFragment.startRecognition(
                new RecognitionOptions(
                        RecognitionOptions.RecognitionStorage.GLOBAL,
                        RecognitionOptions.RecognitionMode.CONTINUOUS_SCAN,
                        new CloudRecognitionInfo(new String[]{databaseName})
                ),
                this);

        //HideActionBar();


//        Toast.makeText(getApplicationContext(),"name = "+name+
//                "Name = "+Name+"\nEmail = "+Email+"\nProfilePic = "+ProfilePic,Toast.LENGTH_LONG).show();

        //showMarkerDetails("Govt. House Enugu Ind. Layout. Enugu");
    }

    private void getData(){
        Bundle data = getIntent().getExtras();
        if(data != null) {
            Name = data.getString("Name");
            Email = data.getString("Email");
            ProfilePic = data.getString("ProfilePicture");
            name = getIntent().getStringExtra("name");
        }
    }

    private void HideActionBar(){

        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    // Set up the action bar.
                    getSupportActionBar().setShowHideAnimationEnabled(true);
                    getSupportActionBar().hide();
                    AnimateOut(image);
                } catch (Exception ex) {
                    Log.e("ERROR", ex.getMessage());
                }
            }
        }, 5000);
    }

    public void draw(final ImageView imageView, String Url){
        if(Url == null){
            Resources res = getResources();
            //load default image from resources
            Bitmap src = BitmapFactory.decodeResource(res, R.drawable.image1);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res,src);
            //set the image circular
            dr.setCircular(true);
            dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
            imageView.setImageDrawable(dr);
        }
        else {
            try {
                Picasso.with(this).load(Url)
                        .resize(150, 150)
                        .centerCrop()
//                        .placeholder(R.drawable.image1)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                                imageDrawable.setCircular(true);
                                imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                                imageView.setImageDrawable(imageDrawable);
                            }

                            @Override
                            public void onError() {
                                imageView.setImageResource(R.drawable.splash);
                            }
                        });


//            Resources res = getResources();
//            //load default image from resources
//            Bitmap src = BitmapFactory.decodeResource(res, Rrs);
//            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res,src);
//            //set the image circular
//            dr.setCircular(true);
//            imageView.setImageDrawable(dr);

//            Load picasso to download image
//            Picasso.with(this)
//                    .load(ProfilePic)
////                             .placeholder(R.drawable.splash) //optional
//                              .resize(50, 50)               //optional
//                             .centerCrop()                  //optional
//
////                            .centerInside()
//                    .into(imageView);                       //Your image view object.
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());

            }
        }

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
        AnimateIn(image);

//        Toast.makeText(getApplicationContext(),"Name = "+Name+"\nEmail = "+Email+"\nProfilePic = "+ProfilePic,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(m_arView!=null) m_arView.onPause();
    }

    private void AnimateIn(final View view){
        //Prepare the view
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);

        // Start the animation
        view.animate()
                .translationY(30)
                .alpha(1.0f)
                .setListener(null);
    }

    private void AnimateOut(final View view){
        view.animate()
                .translationY(0)
                .alpha(0.0f)
                .setListener(null);
        view.setVisibility(View.INVISIBLE);

    }

    private void ShowProgressDialog(){
        final ProgressDialog dialog = ProgressDialog.show(this,"","Signing Out",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(5000);
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

        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
//            Toast.makeText(getApplicationContext(), "name = " + name + "\nName = " +
//                    Name + "\nEmail = " + Email + "\nProfilePic = " + ProfilePic, Toast.LENGTH_LONG).show();
            if (view == image) {
                try {

                    LoginManager.getInstance().logOut();
                    logoutTwitter();
                    ShowProgressDialog();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                } catch (Exception e) {
                    Log.e("LogOutError", e.getMessage());
                }
            } else {
                try {

                    if (image.getVisibility() == View.VISIBLE) {
                        AnimateOut(image);
                    }
                    else {
                        AnimateIn(image);
                        image.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AnimateOut(image);
                            }
                        },5000);

//                        Handler h = new Handler();
//
//                        h.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                   if(image.getVisibility()== View.VISIBLE)
//                                        AnimateOut(image);
//                                } catch (Exception ex) {
//                                    Log.e("ERROR", ex.getMessage());
//                                }
//                            }
//                        }, 5000);
                    }
                } catch (Exception ex) {
                    Log.e("ERROR", ex.getMessage());
                }
            }
        }
//        doRecognition();
        return true;
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
