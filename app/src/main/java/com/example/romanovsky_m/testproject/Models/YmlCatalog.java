package com.example.romanovsky_m.testproject.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Romanovsky_m on 31.10.2016.
 */
@Root(name = "yml_catalog")
public class YmlCatalog {

    @Attribute(name = "date")
    public String date;

    @Element(name = "shop")
    public Shop shop;

    public YmlCatalog(){

    }

}
