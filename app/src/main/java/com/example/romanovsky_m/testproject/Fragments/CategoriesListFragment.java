package com.example.romanovsky_m.testproject.Fragments;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import android.content.res.Resources;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.romanovsky_m.testproject.Models.Categories;
import com.example.romanovsky_m.testproject.Models.CategoriesSingleton;
import com.example.romanovsky_m.testproject.Models.Category;
import com.example.romanovsky_m.testproject.R;


import java.util.List;


public class CategoriesListFragment extends Fragment {

    private RecyclerView mCategoriesRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private int mNumberColumns = 0;
    private int mWidthDisplay = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);

        setRetainInstance(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mNumberColumns = 2;
        }
        else{
            mNumberColumns=3;
        }

        mCategoriesRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),mNumberColumns));

        updateUI();
        return  view;
    }

    private class CategoryHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        private ImageView mImageView;
        private Category mCategory;
        private RelativeLayout mRelativeLayout;
        private FrameLayout mFrameLayout;

        public void bindCategory(Category category){
            mCategory = category;
            mTextView.setText(mCategory.getName());
            int mRID = getActivity().getResources().getIdentifier("r"+mCategory.getID(), "drawable", getActivity().getPackageName());
            mImageView.setBackgroundResource(mRID);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            display.getMetrics(displayMetrics);
            mWidthDisplay = displayMetrics.widthPixels;
            mRelativeLayout.getLayoutParams().width=mWidthDisplay/mNumberColumns;
            mRelativeLayout.getLayoutParams().height=mWidthDisplay/mNumberColumns;
            mFrameLayout.getLayoutParams().width = mWidthDisplay/mNumberColumns*8/10;
            mFrameLayout.getLayoutParams().height = mWidthDisplay/mNumberColumns*8/10;
            mTextView.getLayoutParams().height = mWidthDisplay/mNumberColumns/10;
            mTextView.setTextSize(convertPixelsToDp(mWidthDisplay/mNumberColumns*8/100,getContext()));

        }

        public float convertPixelsToDp(float px, Context context){
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return dp;
        }

        public CategoryHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = OffersListFragment.newInstance(mCategory.getID());

                    fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(BACK_STACK_ROOT_TAG)
                            .commit();
                }
            });

            mTextView = (TextView) itemView.findViewById(R.id.item_list_categories_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.item_list_categories_image_view);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.categories_list_layout);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.categories_list_frame_layout);
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{
        private List<Category> mCategories;

        public CategoryAdapter(List<Category> categories){
            mCategories = categories;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_list_categories,parent,false);
            return new CategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            Category category = mCategories.get(position);
            holder.bindCategory(category);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }

    private void updateUI(){
        Categories categories = CategoriesSingleton.get(getActivity()).getCategories();
        List<Category> categoryList = categories.mCategoryList;
        if(mCategoryAdapter==null) {
            mCategoryAdapter = new CategoryAdapter(categoryList);
            mCategoriesRecyclerView.setAdapter(mCategoryAdapter);
        }
        else{
            mCategoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCategoryAdapter=null;
    }
}