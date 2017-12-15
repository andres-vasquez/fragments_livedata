package edu.upb.andresvasquez.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Fragment formFragment;
    private Fragment resultFragment;

    public MutableLiveData<String> nameLiveData;
    public MutableLiveData<Integer> progressLiveData;
    public MutableLiveData<String> procesoLiveData;

    public MutableLiveData<String> searchLiveData;

    public PokemonService pokemonService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formFragment = new FormFragment();
        resultFragment = new ResultFragment();

        nameLiveData = new MutableLiveData<>();
        progressLiveData = new MutableLiveData<>();
        procesoLiveData = new MutableLiveData<>();
        searchLiveData = new MutableLiveData<>();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.resultFrameLayout,resultFragment)
                .commit();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://pokeapi.co/").build();
        pokemonService = retrofit.create(PokemonService.class);
    }
}
