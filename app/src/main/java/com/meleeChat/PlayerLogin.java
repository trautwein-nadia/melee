package com.meleeChat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Vibrator;

import com.meleeChat.data.Tournament;
import com.meleeChat.message.MessageService;
import com.meleeChat.message.Messages;
import com.meleeChat.message.ResultList;
import com.meleeChat.message.SecureRandomString;
import com.meleeChat.data.GetBracket;

import java.util.ArrayList;
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
    private boolean allowed = false;
    private List<String> domains;
    private List<String> tournaments;
    private ListView tournamentList;
    private String username;
    final Context context = this;


    protected Vibrator vib;

    protected void vibrateCheck() {
        long timeNow = System.currentTimeMillis();
        if (vib != null) {
            long[] once = {0, 100};
            long[] twice = {0, 100, 400, 100};
            long[] thrice = {0, 100, 400, 100, 400, 100};
            vib.vibrate(thrice, -1);
            //vib.vibrate(twice, -1);
            //vib.vibrate(once, -1);
        }
    }


    private boolean getLoginInfo() {
        EditText editText = (EditText) findViewById(R.id.username);
        username = editText.getText().toString();
        SharedPreferences.Editor e = settings.edit();
        e.putString("username", username);
        e.commit();

        if (username == null) {
            return false;
        }
        return true;
    }

    public void playerLogin(View v) {
        /* too burnt to fix this rn :c
        username = settings.getString("username", null);
        if (username != null) {
            //parse responses so that username isnt taken
            for (int i = (responses.size() - 1); i >= 0; i--) {
                String tag = responses.get(i).nickname;
                String type = responses.get(i).messageId;
                String oldID = responses.get(i).userId;
                Log.i(LOG_TAG, "tag list: " + tag);
                if (type.equals("!LOGIN!") && tag.equals(username) && user_id.equals(oldID)) {
                    //add message
                    Log.i(LOG_TAG, tag + " is ALLOWED :3");
                }
            }
            Log.i(LOG_TAG, "USERNAAAAAME: " + username);

            Intent intent = new Intent(this, Menu.class);

            Bundle b = new Bundle();
            b.putFloat("LAT", lat);
            b.putFloat("LON", lon);
            intent.putExtras(b);

            startActivity(intent);

        }
        else */if (getLoginInfo()) {
            if (responses == null) {
                return;
                //add message
            }
            //parse responses so that username isnt taken
            for (int i = (responses.size() - 1); i >= 0; i--) {
                String tag = responses.get(i).nickname;
                String type = responses.get(i).messageId;
                Log.i(LOG_TAG, "tag list: " + responses.get(i).message);
                if (type.equals("!LOGIN!") && tag.equals(username)) {
                    //add message
                    vibrateCheck();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set title
                    alertDialogBuilder.setTitle("Tag already taken!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Please enter a new Tag")
                            .setCancelable(false)
                            .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Fuck you",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                    Log.i(LOG_TAG, tag + " is already taken!");
                    return;
                }
            }

            postMessage();

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

        Call<Messages> queryResponseCall =
                service.post_Message(lat, lon, username, user_id, username, "!LOGIN!");


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
        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (vib == null) {
            Log.i(LOG_TAG, "Vibrator not detected");

        }
        Bundle b = getIntent().getExtras();
        lat = b.getFloat("LAT");
        lon = b.getFloat("LON");
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user_id = settings.getString("user_id", user_id);


        Log.i(LOG_TAG, "LAT: " + lat + " LON: " + lon + " user_id: " + user_id);
    }

    public void getTournaments(View view) {
        if (responses == null) {
            return;
            //add message
        }
        domains = new ArrayList<String>();
        String domain;
        AsyncTask asyncTask;
        tournaments = new ArrayList<String>();
        //parse responses so that username isnt taken
        for (int i = (responses.size() - 1); i >= 0; i--) {
            System.out.println("RESPONSES ARE: " + responses.get(0).message);
            if (responses.get(i).message.contains("!DOMAIN!")) {
                domain = responses.get(i).message.split("!DOMAIN!")[1];
                if (!domains.contains(domain)){
                    domains.add(domain);
                    try {
                        tournaments.addAll(new GetBracket().execute("FlyxNHAwJNMvcoibWQvxIp4jaFcu28tIgh0eUQak", domain).get());
                        System.out.println(tournaments);
                    }
                    catch (java.util.concurrent.ExecutionException e) {
                        tournaments = null;
                    }
                    catch (java.lang.InterruptedException e) {
                        tournaments = null;
                    }
                    catch (java.lang.NullPointerException e) {
                        // Nothing
                    }
                }
            }
        }
        tournamentList = (ListView) findViewById(R.id.tournamentList);
        if (tournaments != null) {
            ArrayAdapter<String> adapter;
            adapter=new ArrayAdapter<String>(this,
                    R.layout.tournament_listview,
                    R.id.textView5,
                    tournaments);
            tournamentList.setAdapter(adapter);
        }
        tournamentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = (TextView) findViewById(R.id.tournamentName);
                TextView text = (TextView) tournamentList.getChildAt(position - tournamentList.getFirstVisiblePosition()).findViewById(R.id.textView5);
                name.setText(text.getText());
            }
        });

    }

}
