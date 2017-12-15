package edu.upb.andresvasquez.fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andresvasquez on 12/12/17.
 */

public class ResultFragment extends Fragment {
    private static final String LOG = ResultFragment.class.getSimpleName();

    private Context context;
    private MainActivity activity;

    private TextView nameTextView;
    private SeekBar seekBar;
    private TextView procesoTextView;
    private Button showDataButton;
    private TextView dataTextView;
    private ImageView pokemonImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof MainActivity) {
            this.activity = (MainActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate: Mostrar el XML del layout y vincular con sus variables
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        nameTextView = view.findViewById(R.id.nameTextView);
        seekBar = view.findViewById(R.id.seekBar);
        procesoTextView = view.findViewById(R.id.procesoTextView);
        showDataButton = view.findViewById(R.id.showDataButton);
        dataTextView = view.findViewById(R.id.dataTextView);
        pokemonImageView = view.findViewById(R.id.pokemonImageView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Usar las vistas creadas

        activity.nameLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    nameTextView.setText(s);
                }
            }
        });

        activity.progressLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    seekBar.setProgress(integer);
                }
            }
        });

        activity.procesoLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    procesoTextView.setText(s);
                }
            }
        });

        activity.searchLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    searchPokemon(s);
                }
            }
        });


        showDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataFromPreferences();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void showDataFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //dataTextView.setText(prefs.getString("CONTENIDO","porDefecto"));
        //seekBar.setProgress(prefs.getInt("PROGRESO",0));


        try {
            String json = prefs.getString("JSON", "");
            Datitos adrian = new Gson().fromJson(json, Datitos.class);

            Datitos natalia = adrian.getDatitos().get(0);
            seekBar.setProgress(natalia.getProgreso());
            dataTextView.setText(natalia.getName());
        } catch (Exception ex) {
            Log.e("ERROR", "" + ex.getMessage());
        }
    }

    private void searchPokemon(String search) {
        Call<Pokemon> pokemonCall = activity.pokemonService.searchPokemon(search);
        pokemonCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon pokemon = response.body();
                    showPokemon(pokemon);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(LOG, "" + t.getMessage());
            }
        });
    }

    private void showPokemon(final Pokemon pokemon) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataTextView.setText(pokemon.getName());
                Glide.with(context)
                        .load(pokemon.getSprites().getFront_default())
                        .into(pokemonImageView);
            }
        });
    }

    private void showError(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,"Usted no sabe de Pokemons, vuelva a la academia",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
