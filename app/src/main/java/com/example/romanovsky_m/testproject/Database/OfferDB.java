package com.example.romanovsky_m.testproject.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Romanovsky_m on 07.11.2016.
 */

@Entity
public class OfferDB {

    @Id
    private Long ID;

    private String NAME;

    private String URL;

    private Double PRICE;

    private String DESCRIPTION;

    private String PICTURE_URL;

    private Integer NUMBER;

    @NotNull
    private Long CATEGORY_ID;

    @ToMany(referencedJoinProperty = "OFFER_ID")
    private List<ParamDB> mParamsList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1956332663)
    private transient OfferDBDao myDao;

    @Generated(hash = 1884496812)
    public OfferDB(Long ID, String NAME, String URL, Double PRICE,
            String DESCRIPTION, String PICTURE_URL, Integer NUMBER,
            @NotNull Long CATEGORY_ID) {
        this.ID = ID;
        this.NAME = NAME;
        this.URL = URL;
        this.PRICE = PRICE;
        this.DESCRIPTION = DESCRIPTION;
        this.PICTURE_URL = PICTURE_URL;
        this.NUMBER = NUMBER;
        this.CATEGORY_ID = CATEGORY_ID;
    }

    @Generated(hash = 1764970581)
    public OfferDB() {
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

    public String getURL() {
        return this.URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Double getPRICE() {
        return this.PRICE;
    }

    public void setPRICE(Double PRICE) {
        this.PRICE = PRICE;
    }

    public String getDESCRIPTION() {
        return this.DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getPICTURE_URL() {
        return this.PICTURE_URL;
    }

    public void setPICTURE_URL(String PICTURE_URL) {
        this.PICTURE_URL = PICTURE_URL;
    }

    public Integer getNUMBER() {
        return this.NUMBER;
    }

    public void setNUMBER(Integer NUMBER) {
        this.NUMBER = NUMBER;
    }

    public Long getCATEGORY_ID() {
        return this.CATEGORY_ID;
    }

    public void setCATEGORY_ID(Long CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 992519573)
    public List<ParamDB> getMParamsList() {
        if (mParamsList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ParamDBDao targetDao = daoSession.getParamDBDao();
            List<ParamDB> mParamsListNew = targetDao._queryOfferDB_MParamsList(ID);
            synchronized (this) {
                if (mParamsList == null) {
                    mParamsList = mParamsListNew;
                }
            }
        }
        return mParamsList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1517622022)
    public synchronized void resetMParamsList() {
        mParamsList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 696786543)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOfferDBDao() : null;
    }



}
