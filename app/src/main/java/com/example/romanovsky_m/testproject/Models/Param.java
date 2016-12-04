package com.example.romanovsky_m.testproject.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;


@Root(name = "param",strict = false)
public class Param{
    @Attribute(name = "name")
    public String mName;

    @Text
    public String mValue;

    public Param(){

    }

    public  Param(String Name, String Value){
        this.mName = Name;
        this.mValue = Value;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}



