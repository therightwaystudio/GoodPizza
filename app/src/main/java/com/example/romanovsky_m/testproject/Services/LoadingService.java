package com.example.romanovsky_m.testproject.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;



import com.example.romanovsky_m.testproject.Database.CategoryDB;
import com.example.romanovsky_m.testproject.Database.DaoSession;
import com.example.romanovsky_m.testproject.Database.OfferDB;
import com.example.romanovsky_m.testproject.Database.ParamDB;
import com.example.romanovsky_m.testproject.Models.Categories;
import com.example.romanovsky_m.testproject.Models.CategoriesSingleton;
import com.example.romanovsky_m.testproject.Models.Category;
import com.example.romanovsky_m.testproject.Models.Offer;
import com.example.romanovsky_m.testproject.Models.Offers;
import com.example.romanovsky_m.testproject.Models.OffersSingleton;
import com.example.romanovsky_m.testproject.Models.Param;
import com.example.romanovsky_m.testproject.Models.Shop;
import com.example.romanovsky_m.testproject.Models.YmlCatalog;
import com.example.romanovsky_m.testproject.Utils.ApiService;
import com.example.romanovsky_m.testproject.Utils.GoodPizzaApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class LoadingService extends IntentService{

    public static final String ACTION_LOADINGSERVICE = "com.example.romanovsky_m.testproject";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String RESPONSE_OK = "OK";
    public static final String RESPONSE_NETWORK_ERROR = "NETWORK_ERROR";
    public static final String RESPONSE_SERVER_ERROR = "SERVER_ERROR";
    public static final String BASE_URL = "http://ufa.farfor.ru/";

    public LoadingService(){
        super("LoadingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(getAppDaoSession().getOfferDBDao().count()!=0){
            loadFromDbInSingleton();
            Intent responseIntent = new Intent();
            responseIntent.setAction(ACTION_LOADINGSERVICE);
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
            responseIntent.putExtra(EXTRA_KEY_OUT, RESPONSE_OK);
            sendBroadcast(responseIntent);
        } else {
            loadingFromServer();
        }

    }


    public void loadingFromServer(){

        if (isOnline(getBaseContext())) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            Call<YmlCatalog> requestNode = apiService.getYmlCatalog();
            requestNode.enqueue(new Callback<YmlCatalog>() {
                @Override
                public void onResponse(Call<YmlCatalog> call, Response<YmlCatalog> response) {
                    if (response.isSuccessful()) {
                        YmlCatalog ymlCatalog = response.body();
                        Shop shop = ymlCatalog.shop;
                        CategoriesSingleton.get(getBaseContext()).addCategories(shop.mCategories);
                        OffersSingleton.get(getBaseContext()).addOffers(shop.mOffers);
                        loadToDataBase();
                        Intent responseIntent = new Intent();
                        responseIntent.setAction(ACTION_LOADINGSERVICE);
                        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        responseIntent.putExtra(EXTRA_KEY_OUT, RESPONSE_OK);
                        sendBroadcast(responseIntent);
                    }
                }

                @Override
                public void onFailure(Call<YmlCatalog> call, Throwable t) {
                    Intent responseIntent = new Intent();
                    responseIntent.setAction(ACTION_LOADINGSERVICE);
                    responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    responseIntent.putExtra(EXTRA_KEY_OUT, RESPONSE_SERVER_ERROR);
                    sendBroadcast(responseIntent);
                    stopSelf();
                }
            });

        } else {
            Intent responseIntent = new Intent();
            responseIntent.setAction(ACTION_LOADINGSERVICE);
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
            responseIntent.putExtra(EXTRA_KEY_OUT, RESPONSE_NETWORK_ERROR);
            sendBroadcast(responseIntent);
            stopSelf();
        }
    }

    private void loadToDataBase(){
        List<OfferDB> offersListDB;
        OfferDB offerDB;

        Long ID;
        String NAME;
        String URL;
        Double PRICE;
        String DESCRIPTION;
        String PICTURE_URL;
        Integer NUMBER;
        Long CATEGORY_ID;

        List<ParamDB> paramDBList;
        ParamDB paramDB;
        String PARAMNAME;
        String VALUE;
        Long OFFER_ID;

        offersListDB = new ArrayList<>();
        paramDBList = new ArrayList<>();
        for(Offer offer : OffersSingleton.get(getBaseContext()).getOffers().mOfferList){
            ID = (long)offer.getId();
            NAME = offer.getName();
            URL = offer.getUrl();
            PRICE = offer.getPrice();
            DESCRIPTION = offer.getDescription();
            PICTURE_URL = offer.getPictureUrl();
            NUMBER = 0;
            CATEGORY_ID = (long)offer.getCategoryId();

            if(offer.getParamList() != null) {
                for (Param param : offer.getParamList()) {
                    PARAMNAME = param.getName();
                    VALUE = param.getValue();
                    OFFER_ID = ID;
                    paramDB = new ParamDB(null, PARAMNAME, VALUE, OFFER_ID);
                    paramDBList.add(paramDB);
                }
            }

            offerDB = new OfferDB(ID,NAME,URL,PRICE,DESCRIPTION,PICTURE_URL,NUMBER,CATEGORY_ID);
            offersListDB.add(offerDB);
        }
        getAppDaoSession().getOfferDBDao().insertInTx(offersListDB);
        getAppDaoSession().getParamDBDao().insertInTx(paramDBList);


        List<CategoryDB> categoryDBList;
        CategoryDB categoryDB;

        categoryDBList = new ArrayList<>();
        for(Category category : CategoriesSingleton.get(getBaseContext()).getCategories().mCategoryList){
            ID = (long) category.getID();
            NAME = category.getName();

            categoryDB = new CategoryDB(ID,NAME);
            categoryDBList.add(categoryDB);
        }
        getAppDaoSession().getCategoryDBDao().insertInTx(categoryDBList);
    }

    public void loadFromDbInSingleton(){
        List<CategoryDB> categoryDBList = getAppDaoSession().getCategoryDBDao().loadAll();
        List<Category> categoryList;
        Category category;
        long ID;
        String Name;
        categoryList = new ArrayList<>();
        for(CategoryDB categoryDB : categoryDBList){
            ID = categoryDB.getID();
            Name = categoryDB.getNAME();
            category = new Category(ID, Name);
            categoryList.add(category);
        }
        Categories categories = new Categories(categoryList);
        CategoriesSingleton.get(getBaseContext()).addCategories(categories);


        List<OfferDB> offerDBList = getAppDaoSession().getOfferDBDao().loadAll();
        List<Offer> offerList;
        Offer offer;
        String Url;
        double Price;
        String Description;
        String PictureUrl;
        int Number;
        long CategoryId;
        offerList = new ArrayList<>();
        for(OfferDB offerDB : offerDBList){
            ID = offerDB.getID();
            Name = offerDB.getNAME();
            Url = offerDB.getURL();
            Price = offerDB.getPRICE();
            Description = offerDB.getDESCRIPTION();
            PictureUrl = offerDB.getPICTURE_URL();
            Number = offerDB.getNUMBER();
            CategoryId = offerDB.getCATEGORY_ID();
            offer = new Offer(ID,Url,Name,Price,Description,PictureUrl,Number,CategoryId);
            offerList.add(offer);
        }
        Offers offers = new Offers(offerList);
        OffersSingleton.get(getBaseContext()).addOffers(offers);


        List<ParamDB> paramDBList = getAppDaoSession().getParamDBDao().loadAll();
        Param param;
        String Value;
        long OfferId;
        for(ParamDB paramDB : paramDBList){
            Name = paramDB.getNAME();
            Value = paramDB.getVALUE();
            OfferId = paramDB.getOFFER_ID();
            param = new Param(Name,Value);
            offers.getOffer(OfferId).getParamList().add(param);
        }
    }

    private DaoSession getAppDaoSession(){
        return ((GoodPizzaApp) getApplication()).getDaoSession();
    }

    public boolean isOnline(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
}
