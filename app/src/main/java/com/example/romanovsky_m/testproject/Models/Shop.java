package com.example.romanovsky_m.testproject.Models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "shop")
public class Shop{

    @Element(name = "categories")
    public Categories mCategories;

    @Element(name = "offers")
    public Offers mOffers;

    public Shop(){

    }

}
