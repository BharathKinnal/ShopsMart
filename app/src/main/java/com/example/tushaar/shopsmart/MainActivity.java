package com.example.tushaar.shopsmart ;

import android.content.Intent ;
import android.support.v7.app.AppCompatActivity ;
import android.os.Bundle ;
import android.view.Gravity ;
import android.view.View ;
import android.widget.AutoCompleteTextView ;
import android.widget.Button ;
import android.widget.ImageView ;
import android.widget.RelativeLayout ;
import android.widget.TextView ;
import android.widget.Toast ;

public class MainActivity extends AppCompatActivity {

    RelativeLayout object ;
    TextView titleOfApp ;
    AutoCompleteTextView SearchBar ;
    ImageView cart ;
    Button browser ;
    static public String enteredtext ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;

        object = (RelativeLayout) findViewById(R.id.relative_1) ;
        titleOfApp = (TextView) findViewById(R.id.ShopSmart) ;
        SearchBar = (AutoCompleteTextView) findViewById(R.id.PriceSearch) ;
        cart = (ImageView) findViewById(R.id.cart) ;
        browser = (Button) findViewById(R.id.browse) ;

        browser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                enteredtext = SearchBar.getText().toString() ;
                if (enteredtext.equals(null)) {
                    Toast toast = new Toast(getApplicationContext()) ;
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0) ;
                    toast.setDuration(Toast.LENGTH_LONG) ;
                    toast.setView(object) ;
                    Toast.makeText(MainActivity.this,
                            "Please enter a valid commodity", Toast.LENGTH_LONG).show() ;
                } else {
                    enteredtext = SearchBar.getText().toString() ;
                    Intent intent = new Intent(getApplicationContext(), Pricing.class) ;
                    startActivity(intent) ;
                }
            }
        });
    }
}