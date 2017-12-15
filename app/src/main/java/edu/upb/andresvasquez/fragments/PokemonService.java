package edu.upb.andresvasquez.fragments;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by andresvasquez on 12/15/17.
 */

public interface PokemonService {

    @GET("/api/v2/pokemon/{search}")
    @Headers({"Content-Type: application/json"})
    Call<Pokemon> searchPokemon(@Path("search") String search);
}
