package com.alberto.retrofit3;

import android.graphics.drawable.Drawable;
import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alberto.retrofit3.data.model.Weather;
import com.alberto.retrofit3.data.remote.WeatherAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.textView_city) TextView textView_city;
    @Bind(R.id.textView_temperature) TextView textView_temperature;
    @Bind(R.id.textView_lastUpdate) TextView textView_lastupdate;
    @Bind(R.id.textView_condition) TextView textView_condition;
    @Bind(R.id.button_refresh) Button button_refresh;
    @Bind(R.id.weather_icon) ImageView weatherIcon;

    String texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_refresh) public void onClick_button_refresh(){


        WeatherAPI.Factory.getIstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Response<Weather> response) {

                textView_temperature.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getTemp()+ "C");
                textView_city.setText(response.body().getQuery().getResults().getChannel().getLocation().getCity());
                textView_lastupdate.setText(response.body().getQuery().getResults().getChannel().getLastBuildDate());
                textView_condition.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getText());


                int resourceid = getResources().getIdentifier("drawable/i" + response.body().getQuery().getResults().getChannel().getItem().getCondition().getCode(), null,getPackageName());
               @SuppressWarnings("deprecation")
               Drawable weatherIconDrawable = getResources().getDrawable(resourceid);

                weatherIcon.setImageDrawable(weatherIconDrawable);

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Failed",t.getMessage());

            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        onClick_button_refresh();
    }




}
