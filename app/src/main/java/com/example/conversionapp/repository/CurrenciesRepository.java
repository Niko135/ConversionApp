package com.example.conversionapp.repository;

import android.app.Application;

import com.example.conversionapp.models.Currency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrenciesRepository {

    private SearchAPI searchAPI;
    private Single<List<Currency>> currencies;
    private Map<String,String> dateData = new HashMap<>();

    public CurrenciesRepository(Application application){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hnbex.eu/api/v1/rates/daily/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        searchAPI = retrofit.create(SearchAPI.class);
    }

    public Single<List<Currency>> getCurrencies(){
        currencies = searchAPI.getCurrencies();
        return currencies;
    }

}
