package com.example.conversionapp.viewmodels;

import android.app.Application;

import com.example.conversionapp.models.Currency;
import com.example.conversionapp.repository.CurrenciesRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    private CurrenciesRepository currenciesRepository;
    private MutableLiveData<List<Currency>> allCurrencies = new MutableLiveData<>();
    private MutableLiveData<String> exchangeRate = new MutableLiveData<>();
    private MutableLiveData<String> amountExchanged = new MutableLiveData<>();
    private float amount = 0;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        currenciesRepository = new CurrenciesRepository(application);
        getCurrencies();

    }

    private void getCurrencies(){
        Single<List<Currency>> currencies = currenciesRepository.getCurrencies();
        currencies.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Currency>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Currency> currencies) {
                        allCurrencies.setValue(currencies);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

    }

    public LiveData<List<Currency>> observeAllCurrencies(){
        return allCurrencies;
    }

    public LiveData<String> getExchangeRate(){
        return exchangeRate;
    }

    public LiveData<String> getAmountExchanged(){
        return amountExchanged;
    }

    public void convert(float userAmount, String from, String to){
        float buyingCurrency = 0,sellingCurrency = 0;
        amount = userAmount;
        if(allCurrencies.getValue() != null) {
            for (Currency currency : allCurrencies.getValue()) {
                if (currency.getCurrencyCode().equals(from)) {
                    float buyingOne = currency.getBuyingRate() / currency.getUnitValue();
                    buyingCurrency = 1 / buyingOne;
                }
                if (currency.getCurrencyCode().equals(to)) {
                    float sellingOne = currency.getMedianRate() / currency.getUnitValue();
                    sellingCurrency = 1 / sellingOne;
                }
            }
        }
        float calculatedValue = sellingCurrency/buyingCurrency;
        amountExchanged.setValue(String.valueOf(amount*calculatedValue));
        exchangeRate.setValue(String.valueOf(calculatedValue));
    }
}
