package com.meleeChat.data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import com.meleeChat.data.JSONAdapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Praneetha on 3/12/2016.
 */
class GetBracket extends AsyncTask<String, Void, String> {
    public static String output = "";
    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection urlConnection;
        String apiKey = urls[0];
        String subdomain = urls[1];
        String result = "";
        // do above Server call here
        try
        {
            URL url = new URL("https://api.challonge.com/v1/tournaments.json?subdomain="+subdomain+"&api_key="+apiKey);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.i("Code is:", "" + urlConnection.getResponseCode());
            if (in != null)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line + "(:)";
                }
                Log.i("Information is", result);

            }

            //convert result to java Tournament object


            in.close();
            urlConnection.disconnect();
            getTournamentJson(result);

            return result;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "some message";
    }

    public Tournament getTournamentJson(String s) {
        output = "";
        Tournament TournamentJson = new Tournament();
        try{
            JSONArray test = new JSONArray(s);
            for(int i=0; i < test.length(); i++){
                String tournamentName = "";
                JSONObject jsonObject = test.getJSONObject(i);
                JSONObject o1 = test.getJSONObject(i);
                //JSONObject mainObject = new JSONObject(s);
                JSONObject o2 = o1.getJSONObject("tournament");
                tournamentName = o2.getString("name");
                Log.i("Tournament name is: ", tournamentName);
                output += tournamentName + "\n";
            }
        }
        catch(JSONException e) {
            System.out.print("Could not parse");
        }

        return TournamentJson;
    }
}
