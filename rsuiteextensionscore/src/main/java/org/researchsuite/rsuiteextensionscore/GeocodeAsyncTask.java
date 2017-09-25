package org.researchsuite.rsuiteextensionscore;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Christina on 6/23/17.
 */

public class GeocodeAsyncTask  {

    public interface GeocodeCompletion {
        void onCompletion(GeocodeLocationResponse response, Exception e);
    }

    public static void getAddressFromLatLon(String apiKey, double latitude, double longitude, final GeocodeCompletion completion) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("geocode")
                .addPathSegment("json")
                .addQueryParameter("latlng", new StringBuilder().append(latitude).append(",").append(longitude).toString())
                .addQueryParameter("key", apiKey)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(processGeocodeResponse(completion));

    }

    public static void getAddressFromUserInput(String apiKey, String userInput, final GeocodeCompletion completion) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("maps.googleapis.com")
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("geocode")
                .addPathSegment("json")
                .addQueryParameter("address", userInput)
                .addQueryParameter("key", apiKey)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(processGeocodeResponse(completion));

    }

    private static Callback processGeocodeResponse(final GeocodeCompletion completion) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                completion.onCompletion(null, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseBody = "";
                try {
                    responseBody = response.body().string();
                    JSONObject responseJson = new JSONObject(responseBody);


                    JSONArray results = responseJson.getJSONArray("results");

                    JSONObject firstResult = results.getJSONObject(0);
                    String formattedAddress = firstResult.getString("formatted_address");

                    JSONObject location = firstResult.getJSONObject("geometry").getJSONObject("location");

                    GeocodeLocationResponse locationResponse = new GeocodeLocationResponse(
                            formattedAddress,
                            location.getDouble("lat"),
                            location.getDouble("lng")
                    );

                    completion.onCompletion(locationResponse, null);

                } catch (JSONException e) {
                    Log.e("geocode task", "Fail to parse response from google geocode:" + responseBody, e);

                    completion.onCompletion(null, new GeocodeException(responseBody));
                    return;
                }
            }
        };
    }





}