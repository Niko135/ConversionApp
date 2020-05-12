package com.example.conversionapp.repository;

import com.example.conversionapp.models.Currency;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SearchAPI {

     @GET(".")
    Single<List<Currency>> getCurrencies();
}
