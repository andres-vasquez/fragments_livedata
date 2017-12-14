package edu.upb.andresvasquez.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andresvasquez on 12/12/17.
 */

public class FormFragment extends Fragment {
    private Context context;
    private MainActivity activity;

    private EditText nameEditText;
    private SeekBar progressSeekBar;
    private Button threadButton;
    private Button saveButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if(context instanceof MainActivity){
            this.activity = (MainActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate: Mostrar el XML del layout y vincular con sus variables
        View view = inflater.inflate(R.layout.fragment_form,container,false);
        nameEditText=view.findViewById(R.id.nameEditText);
        progressSeekBar=view.findViewById(R.id.progressSeekBar);
        threadButton = view.findViewById(R.id.threadButton);
        saveButton = view.findViewById(R.id.saveButton);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Usar las vistas creadas
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity.nameLiveData.setValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                activity.progressLiveData.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        threadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Execute mando los parametros
                new AccionLarga().execute(7000);

                /*activity.procesoLiveData.setValue("Proceso iniciado");

                Thread hilo = new Thread(){
                    @Override
                    public void run() {
                        procesoLargo();
                    }
                };
                hilo.start();*/
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInPreferences();
            }
        });

        saveButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearPreferences();
                return false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void procesoLargo(){
        try {
            Thread.sleep(5000);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.procesoLiveData.setValue("Proceso terminado");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Integer: Param
    //String: Proceso
    //String: Resultado
    class AccionLarga extends AsyncTask<Integer,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.procesoLiveData.setValue("Proceso iniciado");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int tiempo = integers[0];

            for(int i=1000 ; i<=tiempo;i=i+1000){
                try {
                    Thread.sleep(1000);
                    int segundo = i/1000;
                    publishProgress("Han pasado: "+segundo+" segundo(s)");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            //El return es resultado en onPostExecute
            return "Proceso terminado";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            activity.procesoLiveData.setValue(values[0]);
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            activity.procesoLiveData.setValue(resultado);
        }
    }

    private void saveInPreferences(){
        String contenido = nameEditText.getText().toString();
        int progreso=progressSeekBar.getProgress();

        Datitos adrian = new Datitos(contenido,progreso);
        adrian.setSucess(true);
        adrian.setTimestamp(Calendar.getInstance().getTimeInMillis());

        //Agregar lista
        List<Datitos> datitos = new ArrayList<>();
        datitos.add(new Datitos("Natalia",22));
        datitos.add(new Datitos("Lucia",24));
        datitos.add(new Datitos("Andres",22));
        adrian.setDatitos(datitos);

        String json = new Gson().toJson(adrian);
        Log.d("JSON",json);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CONTENIDO",contenido);
        editor.putInt("PROGRESO",progreso);
        editor.putString("JSON",json);
        editor.apply();
    }

    private void clearPreferences(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("CONTENIDO");
        editor.remove("PROGRESO");
        editor.apply();
        Toast.makeText(context,"Datos borrados",Toast.LENGTH_SHORT).show();
    }
}
