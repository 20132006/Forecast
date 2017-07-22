package com.example.artlab.forecast.Fragments;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.example.artlab.forecast.Model.OpenWeatherMapWeek;
import com.example.artlab.forecast.R;
import com.example.artlab.forecast.onMapClicked;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.artlab.forecast.R.id.txtDescription;

/**
 * Created by artlab on 17. 7. 22.
 */

public class WeeklyWeather extends Fragment implements onMapClicked {
    static View view_real;
    OpenWeatherMapWeek openWeatherMap = new OpenWeatherMapWeek();
    TextView txtCity;
    List<TextView> txtMinT;
    List<TextView> txtMaxT;
    List<TextView> txtLastUpdate;
    List<ImageView> imageView;
    FloatingActionButton setting;
    private CurrentWeather anInterface;

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
                new GetWeather().execute(Common.apiRequest(edt.getText().toString(),"7"));
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_real = inflater.inflate(R.layout.weekly_weather, container, false);

        //Control
        txtCity = view_real.findViewById(R.id.txtCity);
        txtLastUpdate = new ArrayList<>();
        imageView = new ArrayList<>();
        txtMaxT = new ArrayList<>();
        txtMinT = new ArrayList<>();

        txtLastUpdate.add((TextView) view_real.findViewById(R.id.txtLastUpdate0));
        txtLastUpdate.add((TextView) view_real.findViewById(R.id.txtLastUpdate1));
        txtLastUpdate.add((TextView) view_real.findViewById(R.id.txtLastUpdate2));
        txtLastUpdate.add((TextView) view_real.findViewById(R.id.txtLastUpdate3));
        txtLastUpdate.add((TextView) view_real.findViewById(R.id.txtLastUpdate4));
        txtLastUpdate.add((TextView) view_real.findViewById(R.id.txtLastUpdate5));
        txtLastUpdate.add((TextView) view_real.findViewById(R.id.txtLastUpdate6));

        imageView.add((ImageView) view_real.findViewById(R.id.imageView0));
        imageView.add((ImageView) view_real.findViewById(R.id.imageView1));
        imageView.add((ImageView) view_real.findViewById(R.id.imageView2));
        imageView.add((ImageView) view_real.findViewById(R.id.imageView3));
        imageView.add((ImageView) view_real.findViewById(R.id.imageView4));
        imageView.add((ImageView) view_real.findViewById(R.id.imageView5));
        imageView.add((ImageView) view_real.findViewById(R.id.imageView6));

        txtMaxT.add((TextView) view_real.findViewById(R.id.maxT0));
        txtMaxT.add((TextView) view_real.findViewById(R.id.maxT1));
        txtMaxT.add((TextView) view_real.findViewById(R.id.maxT2));
        txtMaxT.add((TextView) view_real.findViewById(R.id.maxT3));
        txtMaxT.add((TextView) view_real.findViewById(R.id.maxT4));
        txtMaxT.add((TextView) view_real.findViewById(R.id.maxT5));
        txtMaxT.add((TextView) view_real.findViewById(R.id.maxT6));

        txtMinT.add((TextView) view_real.findViewById(R.id.minT0));
        txtMinT.add((TextView) view_real.findViewById(R.id.minT1));
        txtMinT.add((TextView) view_real.findViewById(R.id.minT2));
        txtMinT.add((TextView) view_real.findViewById(R.id.minT3));
        txtMinT.add((TextView) view_real.findViewById(R.id.minT4));
        txtMinT.add((TextView) view_real.findViewById(R.id.minT5));
        txtMinT.add((TextView) view_real.findViewById(R.id.minT6));

        setting = view_real.findViewById(R.id.fab);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLangDialog();
            }
        });

        new GetWeather().execute(Common.apiRequest("Tokyo","7"));

        return view_real;
    }

    @Override
    public void buttonClicked(String cityName) {
        new GetWeather().execute(Common.apiRequest(cityName,"7"));
    }

    public void setInterface(CurrentWeather anInterface) {
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
            Type mType = new TypeToken<OpenWeatherMapWeek>(){}.getType();
            openWeatherMap = gson.fromJson(s,mType);
            pd.dismiss();
            txtCity.setText(String.format("%s, %s", openWeatherMap.getCity().getName(), openWeatherMap.getCity().getCountry()));
            for (int i=0;i<7;i++)
            {
                txtLastUpdate.get(i).setText(String.format("      %s      ", Common.ConvertToDate( i )));
                txtMinT.get(i).setText( String.format( "     %.2f °C", openWeatherMap.getList().get(i).getTemp().getMin() ) );
                txtMaxT.get(i).setText( String.format( "     %.2f °C", openWeatherMap.getList().get(i).getTemp().getMax() ) );
                Picasso.with(getContext())
                        .load(Common.getImage(openWeatherMap.getList().get(i).getWeather().get(0).getIcon()))
                        .into(imageView.get(i));
            }

        }
    }
}
