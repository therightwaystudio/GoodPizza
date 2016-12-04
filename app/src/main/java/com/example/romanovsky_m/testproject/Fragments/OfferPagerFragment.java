package com.example.romanovsky_m.testproject.Fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.DisplayMetrics;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.romanovsky_m.testproject.Models.Offer;
import com.example.romanovsky_m.testproject.Models.OffersSingleton;


import com.example.romanovsky_m.testproject.R;
import com.rd.PageIndicatorView;

import java.util.List;


public class OfferPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private List<Offer> mOfferList;

    private static final String ARG_OFFER_ID = "offer_id";
    private static final String ARG_OFFER_CATEGORY_ID = "offer__category_id";

    public static OfferPagerFragment newInstance(long ID, long categoryID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_OFFER_ID,ID);
        args.putSerializable(ARG_OFFER_CATEGORY_ID,categoryID);

        OfferPagerFragment fragment = new OfferPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_layout, container, false);

        long ID = (long) getArguments().getSerializable(ARG_OFFER_ID);
        long categoryID = (long) getArguments().getSerializable(ARG_OFFER_CATEGORY_ID);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        mOfferList = OffersSingleton.get(getContext()).getOffers().getOfferListById(categoryID);

        FragmentManager fragmentManager = getChildFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Offer offer = mOfferList.get(position);
                return OfferFragment.newInstance(offer.getId());
            }

            @Override
            public int getCount(){
                return mOfferList.size();
            }

        });

        PageIndicatorView pageIndicatorView = (PageIndicatorView) view.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(mViewPager);

        int size = mOfferList.size();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;


        int sizeIndicator = (width/size)/5;
        if(sizeIndicator>6){
            sizeIndicator=6;
        }
        int sizePadding = sizeIndicator*5/9;
        pageIndicatorView.setRadius(sizeIndicator);
        pageIndicatorView.setPadding(sizePadding);

        for(int i = 0; i<mOfferList.size(); i++){
            if(mOfferList.get(i).getId()==ID){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        return view;
    }
}