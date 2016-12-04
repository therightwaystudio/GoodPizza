package com.example.romanovsky_m.testproject.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;


@Root(name = "category")
public class Category{

    @Attribute(name = "id")
    public long ID;

    @Text
    public String text;

    public Category(){

    }

    public Category(long ID, String Name){
        this.ID = ID;
        this.text = Name;
    }

    public String getName() {
        return text;
    }

    public long getID() {
        return ID;
    }

}
