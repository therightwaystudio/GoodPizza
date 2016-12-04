package com.example.romanovsky_m.testproject.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Romanovsky_m on 07.11.2016.
 */
@Entity
public class ParamDB {

    @Id
    private Long ID;

    private String NAME;

    private String VALUE;

    @NotNull
    private Long OFFER_ID;

    @Generated(hash = 811811382)
    public ParamDB(Long ID, String NAME, String VALUE, @NotNull Long OFFER_ID) {
        this.ID = ID;
        this.NAME = NAME;
        this.VALUE = VALUE;
        this.OFFER_ID = OFFER_ID;
    }

    @Generated(hash = 882044508)
    public ParamDB() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return this.NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getVALUE() {
        return this.VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public Long getOFFER_ID() {
        return this.OFFER_ID;
    }

    public void setOFFER_ID(Long OFFER_ID) {
        this.OFFER_ID = OFFER_ID;
    }

}
