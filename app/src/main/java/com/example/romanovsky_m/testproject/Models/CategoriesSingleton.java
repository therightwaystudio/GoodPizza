package com.example.romanovsky_m.testproject.Models;

import android.content.Context;


public class CategoriesSingleton {
    private static CategoriesSingleton sCategoriesSingleton;
    private Categories mCategories;

    public static CategoriesSingleton get(Context context){
        if(sCategoriesSingleton == null){
            sCategoriesSingleton = new CategoriesSingleton(context);
        }
        return sCategoriesSingleton;
    }

    private CategoriesSingleton(Context context){
    }

    public void addCategories(Categories categories){
        mCategories = categories;
    }

    public Categories getCategories(){
        return mCategories;
    }
}
