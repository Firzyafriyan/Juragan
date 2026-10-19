package com.cadenza.bottomnavigation.networking;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientAsyncTask extends AsyncTask<String, String, String> {

    private static final int CONNECTON_TIMEOUT_MILLISECONDS = 60000;

    private Context mContext;
    private OnPostExecuteListener mPostExecuteListener;

    public interface OnPostExecuteListener {
        void onPostExecute(String result);
    }

    public ClientAsyncTask(Context context, OnPostExecuteListener postExecuteListener) {
        mContext = context;
        mPostExecuteListener = postExecuteListener;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mPostExecuteListener.onPostExecute(result);
    }

    @Override
    protected String doInBackground(String... urls) {

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(CONNECTON_TIMEOUT_MILLISECONDS);
            urlConnection.setReadTimeout(CONNECTON_TIMEOUT_MILLISECONDS);

            String inString = streamToString(urlConnection.getInputStream());
            return inString;

        } catch (Exception ex) {
            // handle exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return "";
    }

    private String streamToString(InputStream inputStream) {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder result = new StringBuilder();

        try {
            while ((line = bufferReader.readLine()) != null) {
                result.append(line);
            }
            inputStream.close();

        } catch (Exception ex) {
            // handle exception
        }

        return result.toString();
    }

}