package com.example.romanovsky_m.testproject.Models;

import android.content.Context;


public class OffersSingleton {
    private static OffersSingleton sOffersSingleton;
    private Offers mOffers;

    public static OffersSingleton get(Context context){
        if(sOffersSingleton == null){
            sOffersSingleton = new OffersSingleton(context);
        }
        return sOffersSingleton;
    }

    private OffersSingleton(Context context){
    }

    public void addOffers(Offers offers){
        mOffers = offers;
    }

    public Offers getOffers(){
        return mOffers;
    }
}
