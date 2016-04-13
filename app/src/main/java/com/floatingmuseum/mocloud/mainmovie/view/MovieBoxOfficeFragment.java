package com.floatingmuseum.mocloud.mainmovie.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieBoxOfficeFragment extends Fragment {

    public static MovieBoxOfficeFragment newInstance() {

        Bundle args = new Bundle();

        MovieBoxOfficeFragment fragment = new MovieBoxOfficeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("MovieBoxOfficeFragment");
        return tv;
    }
}
