package com.example.romanovsky_m.testproject.Utils;

import com.example.romanovsky_m.testproject.Models.YmlCatalog;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {

    @GET("getyml/?key=ukAXxeJYZN")
    Call<YmlCatalog> getYmlCatalog();
}
