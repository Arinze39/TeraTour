package com.pikkart.trial.teratour;

/**
 * Created by Arinze on 8/2/2017.
 */
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
// ...

public class MarkerDetailsDialogFragment extends DialogFragment {

    private EditText mEditText;
    private TextView TitleTextView;
    private Button downloadButton, likeButton, fbButton,commentButton;
    boolean like = false;
//    static String Title;

    public MarkerDetailsDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MarkerDetailsDialogFragment newInstance(String title) {
        MarkerDetailsDialogFragment frag = new MarkerDetailsDialogFragment();
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
        return inflater.inflate(R.layout.details_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        likeButton = (Button) view.findViewById(R.id.like);
        fbButton = (Button)view.findViewById(R.id.fb);
        commentButton = (Button)view.findViewById(R.id.comment);
        downloadButton = (Button)view.findViewById(R.id.download);
        TitleTextView = (TextView)view.findViewById(R.id.TitleText);

        String title = getArguments().getString("title", "marker Title");
        TitleTextView.setMovementMethod(new ScrollingMovementMethod());
        TitleTextView.setText(title);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text2 = likeButton.getText().toString();
                String text = "1";
                int count;
                if(text2.isEmpty())
                {
                    view.setBackgroundResource(R.drawable.like); like = true;
                }
                else{                    
                    if(!like) {
                        view.setBackgroundResource(R.drawable.like);
                        count = Integer.valueOf(text2);
                        count++;
                        text = String.valueOf(count);
                        like = true;
                    }
                    else{
                        view.setBackgroundResource(R.drawable.unlike);
                        count = Integer.valueOf(text2);
                        count--;
                        text = (count==0) ? "" : String.valueOf(count);
                        like = false;
                    }
                }
                likeButton.setText(text);
            }
        });

        //mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
//        String title = getArguments().getString("title", "Enter Name");
//        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}