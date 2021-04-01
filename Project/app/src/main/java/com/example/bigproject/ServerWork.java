package com.example.bigproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.PublicKey;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.bigproject.MainActivity.APP_PREFERENCES;

/**
 * Работа с сервером
 */
public class ServerWork {
    private static String defaultHost = "http://6d4fbb127505.ngrok.io/";
    private SharedPreferences mSittings;
    private final String AUTHTOHOST = "AuthToHost";
    private Context context;


    /**
     * Instantiates a new Server work.
     *
     * @param context the context
     */
    ServerWork(Context context){
        this.context = context;
    }

    //Получаем от сервера публичный ключ
    private PublicKey getPublickKeyForAutorization()
    {
        return null;
    }

    /**
     * Aut server int.
     *
     * @param login    the login
     * @param password the password
     * @return the int
     */
    public int autServer(String login, String password){
        //Готовим запрос
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(defaultHost)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ServerApi registrationApi = retrofit.create(ServerApi.class);
        Call<String> request = registrationApi.regPerson(login,password);
        String req;
        return 0;
    }

    /**
     * Reg aut server int.
     *
     * @param login    the login
     * @param password the password
     * @param regOrAut the reg or aut
     * @return the int
     * Регистрация или авторизация на сервере.
     */
    public int regAutServer(String login, String password, String regOrAut)
    {
        //Готовим запрос
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(defaultHost)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ServerApi registrationApi = retrofit.create(ServerApi.class);
        Call<String> request;
        if(regOrAut.equals("reg"))
            request = registrationApi.regPerson(login,password);
        else
            request = registrationApi.autPerson(login,password);
        String req;

        //Делаем запрос
        try {
            req = request.execute().body();
            String[] requests = req.split(":");
            //Проверяем код ответа сервера
            if(!requests[0].equals("1"))
                return Integer.parseInt(requests[0]);

            //Шифруем файл для автоавторизации
            req = LocalBase.encode(requests[1]);
            //Cохраняем файл для автоавторизации
            mSittings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSittings.edit();
            editor.putString(AUTHTOHOST,req);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
