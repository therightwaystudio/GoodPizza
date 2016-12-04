package com.example.romanovsky_m.testproject.Models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name="categories")
public class Categories{

    @ElementList(inline=true)
    public List<Category> mCategoryList;

    public  Categories(){

    }

    public Categories(List<Category> categoryList){
        this.mCategoryList = categoryList;

    }

    public Category getCategory(int ID){
        for(Category category : mCategoryList)
            if(category.getID()==ID){
                return category;
            }
        return null;
    }


}