package com.example.conversionapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.conversionapp.R;
import com.example.conversionapp.models.Currency;
import com.example.conversionapp.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    Spinner from,to;
    TextView exchangeRate,amountExchanged;
    EditText amountToExchange;
    MainActivityViewModel mainActivityViewModel;
    List<Currency> currencies;
    float finalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        from = findViewById(R.id.fromCur);
        to = findViewById(R.id.toCur);
        exchangeRate = findViewById(R.id.exchangeRate);
        amountToExchange = findViewById(R.id.amountToExchange);
        amountExchanged = findViewById(R.id.amountExchanged);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mainActivityViewModel.observeAllCurrencies().observe(this, gottenCurrencies -> {
            currencies = gottenCurrencies;
            ArrayAdapter<Currency> array = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,currencies);
            array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            from.setAdapter(array);
            to.setAdapter(array);
        });

        mainActivityViewModel.getExchangeRate().observe(this, result -> exchangeRate.setText(result));
        mainActivityViewModel.getAmountExchanged().observe(this, result -> amountExchanged.setText(result));

        amountToExchange.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
    }

    private float getAmount(){
        float defaultAmount = 0;
        String userInput = amountToExchange.getText().toString();
        if(!userInput.isEmpty()){
            defaultAmount = Float.parseFloat(userInput);
        }
        return defaultAmount;
    }

    public void submit(View view) {
        finalAmount = getAmount();
        mainActivityViewModel.convert(finalAmount,from.getSelectedItem().toString(),to.getSelectedItem().toString());
    }

    public void swapValues(View view){
        int fromIndex = from.getSelectedItemPosition();
        from.setSelection(to.getSelectedItemPosition());
        to.setSelection(fromIndex);
        finalAmount = getAmount();
        mainActivityViewModel.convert(finalAmount,from.getSelectedItem().toString(),to.getSelectedItem().toString());
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
