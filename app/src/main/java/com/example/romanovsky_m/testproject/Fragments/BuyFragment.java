package com.example.romanovsky_m.testproject.Fragments;


import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.romanovsky_m.testproject.Models.Offer;
import com.example.romanovsky_m.testproject.Models.Offers;
import com.example.romanovsky_m.testproject.Models.OffersSingleton;


import com.example.romanovsky_m.testproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BuyFragment extends Fragment{

    private RecyclerView mOffersRecyclerView;
    private OfferAdapter mOfferAdapter;
    private TextView mTextPrice;
    private TextView mTextNoBuy;
    private TextView mTextViewMyBuy;
    private Button mButtonSendEmail;
    private AppCompatActivity appCompatActivity;
    private Offer deletedOffer = null;
    private int deletedNumber = 0;
    private int deletedPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_recycler_list_buy, container, false);

        appCompatActivity =(AppCompatActivity) getActivity();
        mOffersRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_buy);
        mOffersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTextPrice = (TextView) view.findViewById(R.id.fregment_recycler_price);
        mTextNoBuy = (TextView) view.findViewById(R.id.fragment_recycler_no_offer);
        mButtonSendEmail = (Button) view.findViewById(R.id.fragment_recycler_button_send_email);
        mTextViewMyBuy = (TextView) view.findViewById(R.id.fragment_buy_my_buy);

        mButtonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"therightwaystudio@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Заказ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, OffersSingleton.get(getActivity()).getOffers().getBuyString());
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });

        updateUI();
        updateFooter();

        return view;
    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferHolder>{
        private List<Offer> mOfferList;

        public OfferAdapter(List<Offer> offerList){
            mOfferList = offerList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.offers_list_item,parent,false);
            return new OfferHolder(view);
        }

        @Override
        public void onBindViewHolder(OfferHolder holder, int position) {
            Offer offer = mOfferList.get(position);
            holder.bindOffer(offer);
        }

        @Override
        public int getItemCount() {
            return mOfferList.size();
        }

        public void delete(int position){
            mOfferList.remove(position);
            notifyItemRemoved(position);
        }

        public void undo( Offer offer, int position){
            mOfferList.add(position,offer);
            notifyDataSetChanged();
        }

        public class OfferHolder extends RecyclerView.ViewHolder{
            private TextView mTextViewName;
            private TextView mTextViewPrice;
            private TextView mTextViewWeight;
            private ImageView mImageViewPicture;
            private NumberPicker mNumberPicker;
            private ImageButton mImageButton;
            private Offer mOffer;

            private TextView mTextViewNumberHelper;

            public void bindOffer(final Offer offer){
                mOffer = offer;
                mTextViewName.setText(mOffer.getName());
                mTextViewPrice.setText(mOffer.getPrice()+" руб");

                if(mOffer.getParam("Вес") != null) {
                    mTextViewWeight.setText(mOffer.getParam("Вес").getValue());
                };

                if(mOffer.getPictureUrl()==null){
                    mImageViewPicture.setImageResource(R.drawable.no_photo_150x100);

                }else{
                    Picasso.with(getActivity())
                            .load(mOffer.getPictureUrl())
                            .resize(300, 200)
                            .into(mImageViewPicture);
                }

                mNumberPicker.setMaxValue(50);
                mNumberPicker.setMinValue(1);
                int number = mOffer.getNumber();
                mNumberPicker.setValue(number);
                mTextViewNumberHelper.setText("На сумму:" + mOffer.getPrice()*number+" руб");
                mImageButton.setBackgroundResource(R.drawable.delete_buy_icon);

                mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        if(i1!=0){
                            mTextViewNumberHelper.setText("На сумму:" + mOffer.getPrice()*i1+" руб");
                            mOffer.setNumber(i1);
                            updateFooter();
                            updateSubtitle();
                        } else{
                            mTextViewNumberHelper.setText("");
                        }
                    }
                });

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



                mImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        deletedNumber = mOffer.getNumber();
                        deletedOffer = mOffer;
                        deletedPosition = getAdapterPosition();
                        delete(deletedPosition);
                        mOffer.setNumber(0);
                        Snackbar.make(view,"Позиция удалена",Snackbar.LENGTH_LONG).setAction("ВЕРНУТЬ", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deletedOffer.setNumber(deletedNumber);
                                undo(deletedOffer,deletedPosition);
                                updateFooter();
                                updateSubtitle();
                            }
                        }).show();
                        updateFooter();
                        updateSubtitle();
                    }
                });
            }

            public OfferHolder(View itemView) {
                super(itemView);
                //need create
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                mTextViewName = (TextView) itemView.findViewById(R.id.item_list_offers_name);
                mTextViewPrice = (TextView) itemView.findViewById(R.id.offers_list_item_price);
                mTextViewWeight = (TextView) itemView.findViewById(R.id.offers_list_item_weight);
                mImageViewPicture = (ImageView) itemView.findViewById(R.id.offers_list_item_photo);
                mNumberPicker = (NumberPicker) itemView.findViewById(R.id.offers_list_item_numberpicker);
                mImageButton = (ImageButton) itemView.findViewById(R.id.offers_list_item_button_buy);
                mTextViewNumberHelper = (TextView) itemView.findViewById(R.id.offers_list_item_textview_numberpicker_helper);
            }
        }
    }

    protected void updateUI() {
        Offers offers = OffersSingleton.get(getActivity()).getOffers();
        List<Offer> offerList = offers.getOfferListByNumber();

        if (mOfferAdapter == null) {
            mOfferAdapter = new OfferAdapter(offerList);
            mOffersRecyclerView.setAdapter(mOfferAdapter);

        } else {
            mOfferAdapter.notifyDataSetChanged();
        }
    }

    private void updateFooter(){
        if(OffersSingleton.get(getActivity()).getOffers().getPrice() != 0){
            mTextNoBuy.setVisibility(View.INVISIBLE);
            mButtonSendEmail.setVisibility(View.VISIBLE);
            mTextViewMyBuy.setVisibility(View.VISIBLE);
            mTextPrice.setText("Сумма заказа: " + OffersSingleton.get(getActivity()).getOffers().getPrice()+" руб");
        } else {
            mTextPrice.setText("");
            mTextNoBuy.setVisibility(View.VISIBLE);
            mButtonSendEmail.setVisibility(View.INVISIBLE);
            mTextViewMyBuy.setVisibility(View.INVISIBLE);
        }
    }


    public void updateSubtitle(){
        double price = OffersSingleton.get(getContext()).getOffers().getPrice();
        if(price!=0.0) {
            appCompatActivity.getSupportActionBar().setSubtitle("Сумма: " + price + " руб");
        }
        else{
            appCompatActivity.getSupportActionBar().setSubtitle("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mOfferAdapter=null;
    }

}
