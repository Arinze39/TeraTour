package com.pikkart.trial.teratour;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pikkart.trial.teratour.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends DialogFragment {


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

    }

    }
