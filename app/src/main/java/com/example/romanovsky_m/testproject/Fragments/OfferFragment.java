package com.example.romanovsky_m.testproject.Fragments;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.romanovsky_m.testproject.Models.Offer;
import com.example.romanovsky_m.testproject.Models.OffersSingleton;

import com.example.romanovsky_m.testproject.R;
import com.squareup.picasso.Picasso;


public class OfferFragment extends Fragment {

    private static final String ARG_OFFER_ID = "offer_id";

    private Offer mOffer;
    private NumberPicker mNumberPicker;
    private TextView mTextViewNumberHelper;
    private ImageButton mImageButtonAdd;
    private RelativeLayout mOfferLayout;
    private AppCompatActivity appCompatActivity;

    public static OfferFragment newInstance(long ID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_OFFER_ID,ID);

        OfferFragment fragment = new OfferFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        long ID = (long) getArguments().getSerializable(ARG_OFFER_ID);
        mOffer = OffersSingleton.get(getActivity()).getOffers().getOffer(ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_offer,container,false);

        appCompatActivity =(AppCompatActivity) getActivity();

        TextView mTextViewName = (TextView) view.findViewById(R.id.offer_fragment_name);
        TextView mTextViewWeight = (TextView) view.findViewById(R.id.offer_fragment_weigth);
        TextView mTextViewPrice = (TextView) view.findViewById(R.id.offer_fragment_price);
        TextView mTextViewDescription = (TextView) view.findViewById(R.id.offer_fragment_description);
        ImageView mImageViewPicture = (ImageView) view.findViewById(R.id.offer_fragment_picture);
        mImageButtonAdd = (ImageButton) view.findViewById(R.id.offer_fragment_imagebutton_add);
        mNumberPicker = (NumberPicker) view.findViewById(R.id.offer_fragment_numberpicker);
        mTextViewNumberHelper = (TextView) view.findViewById(R.id.offer_fragment_number_helper);
        mOfferLayout = (RelativeLayout) view.findViewById(R.id.offer_fragment_layout);

        mNumberPicker.setMaxValue(50);
        mNumberPicker.setMinValue(0);
        int number = mOffer.getNumber();
        mNumberPicker.setValue(number);
        if(number!=0){
            mTextViewNumberHelper.setText("На сумму:" + mOffer.getPrice()*number);
            mNumberPicker.setEnabled(false);
            mImageButtonAdd.setEnabled(false);
            mOfferLayout.setBackgroundResource(R.color.background_light_blue);

        } else {
            mTextViewNumberHelper.setText("");
            mNumberPicker.setEnabled(true);
            mImageButtonAdd.setEnabled(true);
            mOfferLayout.setBackgroundResource(R.color.cardview_light_background);
        }

        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if(i1!=0){
                    mTextViewNumberHelper.setText("На сумму:" + mOffer.getPrice()*i1);
                } else{
                    mTextViewNumberHelper.setText("");
                }
            }
        });

        mImageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNumberPicker.getValue() == 0){
                    Snackbar.make(view,"Выберите количество", Snackbar.LENGTH_SHORT).show();
                } else {
                    mOffer.setNumber(mNumberPicker.getValue());
                    Snackbar.make(view,"Добавлено в корзину", Snackbar.LENGTH_SHORT).show();
                    updateSubtitle();
                    mNumberPicker.setEnabled(false);
                    mImageButtonAdd.setEnabled(false);
                    mOfferLayout.setBackgroundResource(R.color.background_light_blue);
                }
            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mTextViewName.setText(mOffer.getName());

        if(mOffer.getParam("Вес") != null) {
            mTextViewWeight.setText(mOffer.getParam("Вес").getValue());
        };

        mTextViewPrice.setText(mOffer.getPrice()+" руб");
        if(mOffer.getDescription() == null){
            mTextViewDescription.setText("Нет описания");
        }
        else {
            mTextViewDescription.setText(mOffer.getDescription());
        }

        if(mOffer.getPictureUrl()==null){
            mImageViewPicture.setImageResource(R.drawable.no_photo_300x200);
        }else {
            Picasso.with(getActivity())
                    .load(mOffer.getPictureUrl())
                    .resize(480, 320)
                    .into(mImageViewPicture);
        }
        return view;
    }

    public void updateSubtitle(){
        double price = OffersSingleton.get(getContext()).getOffers().getPrice();
        if(price!=0.0) {
            appCompatActivity.getSupportActionBar().setSubtitle("Сумма: " + price + " руб");
        }
    }
}
