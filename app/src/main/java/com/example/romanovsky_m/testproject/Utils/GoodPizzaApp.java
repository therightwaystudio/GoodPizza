package com.example.romanovsky_m.testproject.Utils;

import android.app.Application;

import com.example.romanovsky_m.testproject.Database.DaoMaster;
import com.example.romanovsky_m.testproject.Database.DaoSession;

import org.greenrobot.greendao.database.Database;


public class GoodPizzaApp extends Application{

    private DaoSession mDaoSession;

    @Override
    public void onCreate(){
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop-db");
        Database database = helper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
