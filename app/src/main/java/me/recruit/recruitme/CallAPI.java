package me.recruit.recruitme;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CHAOY01 on 16-02-27.
 */
public class CallAPI extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {

        String urlString=params[0]; // URL to call

        String resultToDisplay = "";

        InputStream in = null;

        // HTTP Get
        try {

            URL url = new URL(urlString);

            Log.d("CallAPI", "Started call");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            Log.d("CallAPI", "Finished call");


            in = new BufferedInputStream(urlConnection.getInputStream());
            resultToDisplay = urlConnection.getResponseMessage();
            Log.d("CallAPI", resultToDisplay);


        } catch (Exception e ) {

            Log.d("CallAPI", e.getMessage());

            return e.getMessage();

        }
        return resultToDisplay;
    }

    protected void onPostExecute(String result) {

    }
}
