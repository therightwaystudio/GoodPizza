package com.example.romanovsky_m.testproject.Models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;


@Root(name = "offers")
public class Offers{

    @ElementList(inline=true)
    public List<Offer> mOfferList;

    public Offers(){

    }

    public Offers(List<Offer> offerList){
        this.mOfferList = offerList;

    }

    public List<Offer> getOfferListById(long ID){
        List<Offer> offerListById = new ArrayList<>();
        for(Offer offer : mOfferList){
            if(offer.getCategoryId() == ID){
                offerListById.add(offer);
            }
        }
        return offerListById;
    }

    public Offer getOffer(long ID){
        for(Offer offer : mOfferList){
            if(offer.getId() == ID ){
                return offer;
            }
        }
        return null;
    }


    public double getPrice(){
        double sum = 0;
        for(Offer offer : mOfferList){
            if(offer.getNumber() != 0){
                sum+=offer.getPrice()*offer.getNumber();
            }
        }
        return sum;
    }

    public List<Offer> getOfferListByNumber(){
        List<Offer> offerListById = new ArrayList<>();
        for(Offer offer : mOfferList){
            if(offer.getNumber() != 0){
                offerListById.add(offer);
            }
        }
        return offerListById;
    }

    public String getBuyString(){
        List<Offer> offerListById = new ArrayList<>();
        StringBuilder buf=new StringBuilder();
        String line=null;
        for(Offer offer : mOfferList){
            if(offer.getNumber() != 0) {
                buf.append(offer.getName() + ": " + offer.getNumber() + "шт" + "\n");
            }
        }
        return(buf.toString());
    }

}
