package com.example.romanovsky_m.testproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.romanovsky_m.testproject.R;


public class PopularFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PopularFragment() {
    }

    public static PopularFragment newInstance(int sectionNumber) {
        PopularFragment fragment = new PopularFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(R.string.long_string);
        return rootView;
    }
}
