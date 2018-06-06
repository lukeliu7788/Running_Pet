package com.votors.runningx;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.votors.runningx.gson.WholeWeather;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by lenovo on 2017/10/12.
 */

public class WeatherFragment extends Fragment {

    WholeWeather wholeweather;
    TextView tvx_cityname;
    TextView tvx_temparure;
    TextView tvx_windspeed;
    TextView tvx_weathertype;
    TextView tvx_cloud;

    public static GpsRec currentrec;


    Context context = null;
    private static final String TAG = "WeatherFragment";
    String fileweathername = Environment.getDataDirectory() + "/data/com.votors.runningx/cache/weather.json";
    String strData = "";
    URL url;
    String strUrl;
    URLConnection urlconn = null;


    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    tvx_cityname.setText(wholeweather.getName());
                    String tempareturest = Double.toString(Double.parseDouble(wholeweather.getMain().getTempareture()) - 273.13);
                    tvx_temparure.setText("Temperature: " + tempareturest.substring(0,4)+ "℃");
                    tvx_weathertype.setText(wholeweather.getWeather().getMain());
                    tvx_windspeed.setText("Wind Speed: " + wholeweather.getWind().getSpeed());
                    tvx_cloud.setText("Cloud: " + wholeweather.getClouds().getAll() + "%");
                    break;
            }
        }
    };




        @Nullable
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 Bundle savedInstanceState) {

            Log.i(TAG, "onCreateView: " + fileweathername);
            context = getActivity();
            View rootView = inflater.inflate(R.layout.weather_layout, container, false);
            tvx_cityname = (TextView) rootView.findViewById(R.id.txvcity);
            tvx_temparure = (TextView) rootView.findViewById(R.id.txvtempc);
            tvx_weathertype = (TextView) rootView.findViewById(R.id.txvtype);
            tvx_windspeed = (TextView) rootView.findViewById(R.id.txvwindspeed);
            tvx_cloud = (TextView) rootView.findViewById(R.id.txvcloud);

            if(currentrec!=null) {
                double a = currentrec.getLat();
                double b = currentrec.getLng();
                strUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + a + "&lon=" + b + "&APPID=967c7c39aaaa99be0c9c46093dcb0268";

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(strUrl).build();
                changeWeather();
            }




            return rootView;
        }


        public void changeWeather() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        url = new URL(strUrl);
                        urlconn = url.openConnection();
                        urlconn.connect();
                        InputStream is = urlconn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        byte[] data = new byte[50];
                        int current = 0;
                        while ((current = bis.read(data, 0, data.length)) != -1) {
                            buffer.write(data, 0, current);
                        }
                        Log.i(TAG, "saveWeather: buffer" + buffer);
//                  FileOutputStream fos = new FileOutputStream(fileweathername);
//                   fos.write(buffer.toByteArray());
//                    fos.close();
                        String temp = buffer.toString().replace("[", "").replace("]", "");
                        Gson gson = new Gson();
                        wholeweather = gson.fromJson(temp, WholeWeather.class);

                        Log.i(TAG, "run: " + wholeweather.getWeather().getMain());
                        Log.i(TAG, "run: " + wholeweather.getWind().getSpeed());
                        Log.i(TAG, "run: " + "Cloud: " + wholeweather.getClouds().getAll() + "%");

                        Message msg = new Message();
                        msg.what = -1;
                        uiHandler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }


}
