package com.example.romanovsky_m.testproject.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;


@Root(name ="offer",strict = false)
public class Offer{

    @Attribute(name = "id")
    public long mId;

    @Element(name = "url")
    public String mUrl;

    @Element(name = "name")
    public String mName;

    @Element(name = "price")
    public double mPrice;

    @Element(name = "description",required = false)
    public String mDescription;

    @Element(name = "picture",required = false)
    public String mPictureUrl;

    @Element(name = "categoryId")
    public long mCategoryId;

    public int mNumber = 0;

    @ElementList(entry = "param",inline=true,required = false)
    public List<Param> mParamList;


    public Offer(){

    }

    public Offer(long Id, String Url, String Name, double Price, String Description, String PictureUrl, int Number, long CategoryId){
        this.mId = Id;
        this.mUrl = Url;
        this.mName = Name;
        this.mPrice = Price;
        this.mDescription = Description;
        this.mPictureUrl = PictureUrl;
        this.mCategoryId = CategoryId;
        this.mNumber = Number;
    }

    public Param getParam(String name){
        if(mParamList != null) {
            for (Param param : mParamList) {
                if (name.equals(param.mName)) {
                    return param;
                }
            }
        }
        return null;
    }



    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(long categoryId) {
        mCategoryId = categoryId;
    }

    public List<Param> getParamList() {
        if(mParamList == null){
            mParamList = new ArrayList<>();
        }
        return mParamList;
    }

    public void setParamList(List<Param> paramList) {
        mParamList = paramList;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

}

