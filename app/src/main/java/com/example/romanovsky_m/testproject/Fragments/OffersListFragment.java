package com.example.romanovsky_m.testproject.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.romanovsky_m.testproject.Activities.GoodPizzaActivity;
import com.example.romanovsky_m.testproject.Models.Offer;
import com.example.romanovsky_m.testproject.Models.Offers;
import com.example.romanovsky_m.testproject.Models.OffersSingleton;

import com.example.romanovsky_m.testproject.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Romanovsky_m on 20.10.2016.
 */
public class OffersListFragment extends Fragment {

    private static final String ARG_OFFERS_LIST_ID = "offers_list_id";

    private RecyclerView mOffersRecyclerView;
    private OfferAdapter mOfferAdapter;

    public static OffersListFragment newInstance(long ID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_OFFERS_LIST_ID, ID);

        OffersListFragment fragment = new OffersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);

        long ID =(long) getArguments().getSerializable(ARG_OFFERS_LIST_ID);

        HashMap<Long,Integer> resourceMap = new HashMap<Long,Integer>();
        resourceMap.put((long)1,R.id.nav_pizza);
        resourceMap.put((long)2,R.id.nav_sets);
        resourceMap.put((long)3,R.id.nav_noodles);
        resourceMap.put((long)5,R.id.nav_sushi);
        resourceMap.put((long)6,R.id.nav_soups);
        resourceMap.put((long)7,R.id.nav_salads);
        resourceMap.put((long)8,R.id.nav_teploe);
        resourceMap.put((long)9,R.id.nav_drinks);
        resourceMap.put((long)10,R.id.nav_desserts);
        resourceMap.put((long)18,R.id.nav_rolls);
        resourceMap.put((long)20,R.id.nav_snacks);
        resourceMap.put((long)23,R.id.nav_supplements);
        resourceMap.put((long)25,R.id.nav_party_maker);

        MenuItem menuItem = getActivityMenu().findItem(resourceMap.get(ID));
        menuItem.setCheckable(true);
        menuItem.setChecked(true);
        ((GoodPizzaActivity) getActivity()).setPreviousItem(menuItem);

        mOffersRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mOffersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private class OfferHolder extends RecyclerView.ViewHolder{

        private TextView mTextViewName;
        private TextView mTextViewPrice;
        private TextView mTextViewWeight;
        private ImageView mImageViewPicture;
        private NumberPicker mNumberPicker;
        private ImageButton mImageButton;
        private Offer mOffer;
        private TextView mTextViewNumberHelper;
        private RelativeLayout mOffersListItemLayout;

        public void bindOffer(final Offer offer){
            mOffer = offer;
            mTextViewName.setText(mOffer.getName());
            mTextViewPrice.setText(mOffer.getPrice()+" руб");

            if(mOffer.getParam("Вес")!= null) {
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
            mNumberPicker.setMinValue(0);
            int number = mOffer.getNumber();

            mNumberPicker.setValue(number);
            if(number!=0){
                mTextViewNumberHelper.setText("На сумму:" + mOffer.getPrice()*number+" руб");
                mNumberPicker.setEnabled(false);
                mImageButton.setEnabled(false);
                mOffersListItemLayout.setBackgroundResource(R.color.background_light_blue);

            } else {
                mTextViewNumberHelper.setText("");
                mNumberPicker.setEnabled(true);
                mImageButton.setEnabled(true);
                mOffersListItemLayout.setBackgroundResource(R.color.cardview_light_background);
            }

            mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    if(i1!=0){
                        mTextViewNumberHelper.setText("На сумму:" + mOffer.getPrice()*i1+" руб");
                    } else{
                        mTextViewNumberHelper.setText("");
                    }
                }
            });

            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            mImageButton.setImageResource(R.drawable.add_buy_icon);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mNumberPicker.getValue() == 0){
                        Snackbar.make(view,"Выберите количество", Snackbar.LENGTH_SHORT).show();
                    } else {
                        mOffer.setNumber(mNumberPicker.getValue());
                        Snackbar.make(view,"Добавлено в корзину", Snackbar.LENGTH_SHORT).show();
                        updateSubtitle();
                        mNumberPicker.setEnabled(false);
                        mImageButton.setEnabled(false);
                        mOffersListItemLayout.setBackgroundResource(R.color.background_light_blue);
                    }
                }
            });
        }

        public OfferHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();

                    Fragment fragment = OfferPagerFragment.newInstance(mOffer.getId(),(long)getArguments().getSerializable(ARG_OFFERS_LIST_ID));

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            mTextViewName = (TextView) itemView.findViewById(R.id.item_list_offers_name);
            mTextViewPrice = (TextView) itemView.findViewById(R.id.offers_list_item_price);
            mTextViewWeight = (TextView) itemView.findViewById(R.id.offers_list_item_weight);
            mImageViewPicture = (ImageView) itemView.findViewById(R.id.offers_list_item_photo);
            mNumberPicker = (NumberPicker) itemView.findViewById(R.id.offers_list_item_numberpicker);
            mImageButton = (ImageButton) itemView.findViewById(R.id.offers_list_item_button_buy);
            mTextViewNumberHelper = (TextView) itemView.findViewById(R.id.offers_list_item_textview_numberpicker_helper);
            mOffersListItemLayout = (RelativeLayout) itemView.findViewById(R.id.offers_list_item_layout);
        }
    }

    private class OfferAdapter extends RecyclerView.Adapter<OfferHolder>{
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
    }

    protected void updateUI(){
        Offers offers = OffersSingleton.get(getActivity()).getOffers();
        long ID =(long) getArguments().getSerializable(ARG_OFFERS_LIST_ID);
        List<Offer> offerList = offers.getOfferListById(ID);

        if(mOfferAdapter==null) {
            mOfferAdapter = new OfferAdapter(offerList);
            mOffersRecyclerView.setAdapter(mOfferAdapter);

        }
        else{
            mOfferAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mOfferAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mOfferAdapter=null;
    }

    public void updateSubtitle(){
        double price = OffersSingleton.get(getContext()).getOffers().getPrice();

        if(price!=0.0) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Сумма: " + price + " руб");
        }
    }

    private Menu getActivityMenu(){
        return  ((GoodPizzaActivity) getActivity()).getSingleActivityMenu();
    }
}