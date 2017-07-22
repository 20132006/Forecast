package com.example.artlab.forecast.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artlab.forecast.Common.Common;
import com.example.artlab.forecast.Helper.Helper;
import com.example.artlab.forecast.MainActivity;
import com.example.artlab.forecast.Model.OpenWeatherMap;
import com.example.artlab.forecast.R;
import com.example.artlab.forecast.onMapClicked;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

/**
 * Created by artlab on 17. 7. 22.
 */

public class CurrentWeather extends Fragment implements onMapClicked {

    private WeeklyWeather anInterface;

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter City name below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                new GetWeather().execute(Common.apiRequest(edt.getText().toString()));
                anInterface.buttonClicked(edt.getText().toString());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    ImageView imageView;
    static View view_real;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    FloatingActionButton setting;


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_real = inflater.inflate(R.layout.current_weather, container, false);
        new GetWeather().execute(Common.apiRequest("Tokyo"));
        txtCity = view_real.findViewById(R.id.txtCity);
        txtLastUpdate = view_real.findViewById(R.id.txtLastUpdate);
        txtDescription = view_real.findViewById(R.id.txtDescription);
        txtHumidity = view_real.findViewById(R.id.txtHumidity);
        txtTime = view_real.findViewById(R.id.txtTime);
        txtCelsius = view_real.findViewById(R.id.txtCelsius);
        imageView = view_real.findViewById(R.id.imageView);
        setting = view_real.findViewById(R.id.fab);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLangDialog();
            }
        });

        return view_real;
    }

    @Override
    public void buttonClicked(String cityName) {
        new GetWeather().execute(Common.apiRequest(cityName));
    }

    public void setInterface(WeeklyWeather anInterface) {
        this.anInterface = anInterface;
    }


    private class GetWeather extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Please Wait...");
        }


        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];

            Helper http = new Helper();
            stream = http.getHttpData(urlString);

            return stream;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city")){
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s,mType);
            pd.dismiss();
            txtCity.setText(String.format("%s, %s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("Last Update: %s", Common.getDateNow()));
            txtDescription.setText(String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("Humidity: %d%%",openWeatherMap.getMain().getHumidity()));
            txtCelsius.setText(String.format("%.2f °C", openWeatherMap.getMain().getTemp()));

            Picasso.with(getContext())
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(imageView);
        }
    }
}
