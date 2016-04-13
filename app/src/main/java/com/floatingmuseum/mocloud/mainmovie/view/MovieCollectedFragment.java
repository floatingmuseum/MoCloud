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
public class MovieCollectedFragment extends Fragment {

    public static MovieCollectedFragment newInstance() {

        Bundle args = new Bundle();

        MovieCollectedFragment fragment = new MovieCollectedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("MovieCollectedFragment");
        return tv;
    }
}
