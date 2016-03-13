package com.meleeChat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.meleeChat.message.MessageService;
import com.meleeChat.message.Messages;
import com.meleeChat.message.ResultList;
import com.meleeChat.message.SecureRandomString;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlayerLogin extends AppCompatActivity {
    private static final String LOG_TAG = "PLAYER_LOGIN";
    private String user_id;
    private float lat;
    private float lon;
    private SharedPreferences settings;
    private List<ResultList> responses;
    private boolean taken = false;

    private String username;


    private boolean getLoginInfo() {
        EditText editText = (EditText) findViewById(R.id.username);
        username = editText.getText().toString();

        //or do you bounds check with ""?
        if (username == null) {
            return false;
        }
        return true;
    }

    public void playerLogin(View v) {
        if (getLoginInfo()) {
            if (responses == null) {
                return;
                //add message
            }
            //parse responses so that username isnt taken
            for (int i = (responses.size() - 1); i >= 0; i--) {
                String tag = responses.get(i).nickname;
                String message = responses.get(i).message;
                char c = message.charAt(0);
                Log.i(LOG_TAG, "tag list: " + tag);
                if (c == '!' && message.equals("!LOGIN!" + tag) && tag.equals(username)) {
                    //show something saying username taken
                    Log.i(LOG_TAG, tag + " is already taken!");
                    return;
                }
            }

            postMessage();

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            //username = settings.getString("username", null);
            SharedPreferences.Editor e = settings.edit();
            e.putString("username", username);
            e.commit();

            Intent intent = new Intent(this, Menu.class);

            Bundle b = new Bundle();
            b.putFloat("LAT", lat);
            b.putFloat("LON", lon);
            intent.putExtras(b);

            startActivity(intent);
        }
    }


    public void postMessage() {
        //Magic HTTP stuff
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://luca-teaching.appspot.com/localmessages/default/")
                .addConverterFactory(GsonConverterFactory.create())    //parse Gson string
                .client(httpClient)    //add logging
                .build();

        MessageService service = retrofit.create(MessageService.class);

        SecureRandomString srs = new SecureRandomString();
        String message_id = srs.nextString();

        String message = "!LOGIN!" + username;
        Call<Messages> queryResponseCall =
                service.post_Message(lat, lon, username, user_id, message, message_id);


        //Call retrofit asynchronously
        queryResponseCall.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Response<Messages> response) {
                Log.i(LOG_TAG, "Code is: " + response.code());
                if ((response.body().result.equals("ok") && response.code() == 200)) {
                    Log.i(LOG_TAG, "The result is: " + response.body().result);

                    responses = response.body().resultList;

                }
                else {
                    Log.i(LOG_TAG, "Code is: " + response.code());
                    //toast with error
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                //toast error
            }
        });
    }

    public void getMessages() {
        //Magic HTTP stuff
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://luca-teaching.appspot.com/localmessages/default/")
                .addConverterFactory(GsonConverterFactory.create())    //parse Gson string
                .client(httpClient)    //add logging
                .build();

        MessageService service = retrofit.create(MessageService.class);

            SecureRandomString srs = new SecureRandomString();
            String message_id = srs.nextString();

            Call<Messages> queryResponseCall =
                    service.get_Messages(lat, lon, user_id);


            //Call retrofit asynchronously
            queryResponseCall.enqueue(new Callback<Messages>() {
                @Override
                public void onResponse(Response<Messages> response) {
                    Log.i(LOG_TAG, "Code is: " + response.code());
                    if ((response.body().result.equals("ok") && response.code() == 200)) {
                        Log.i(LOG_TAG, "The result is: " + response.body().result);

                        responses = response.body().resultList;

                    }
                    else {
                        Log.i(LOG_TAG, "Code is: " + response.code());
                        //toast with error
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    //toast error
                }
            });
    }
    @Override
    protected void onResume() {
        getSupportActionBar().setTitle("Competitor Login");
        getMessages();
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_login);

        Bundle b = getIntent().getExtras();
        lat = b.getFloat("LAT");
        lon = b.getFloat("LON");
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user_id = settings.getString("user_id", user_id);



        Log.i(LOG_TAG, "LAT: " + lat + " LON: " + lon + "user_id: " + user_id);
    }

}
