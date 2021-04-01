package com.example.bigproject;

import java.security.PublicKey;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The interface Server api.
 */
public interface ServerApi {

    /**
     * Reg person call.
     *
     * @param login    the login
     * @param password the password
     * @return the call
     */
    @POST( "/regPerson")
    Call<String> regPerson(@Query(value = "login") String login,@Query(value = "password") String password);

    /**
     * Aut person call.
     *
     * @param login    the login
     * @param password the password
     * @return the call
     */
    @POST( "/autPerson")
    Call<String> autPerson(@Query(value = "login") String login,@Query(value = "password") String password);

}
