package edu.upb.andresvasquez.fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by andresvasquez on 12/12/17.
 */

public class ResultFragment extends Fragment{
    private Context context;
    private MainActivity activity;

    private TextView nameTextView;
    private SeekBar seekBar;
    private TextView procesoTextView;

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
        View view = inflater.inflate(R.layout.fragment_result,container,false);
        nameTextView = view.findViewById(R.id.nameTextView);
        seekBar = view.findViewById(R.id.seekBar);
        procesoTextView = view.findViewById(R.id.procesoTextView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Usar las vistas creadas

        activity.nameLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s!=null){
                    nameTextView.setText(s);
                }
            }
        });

        activity.progressLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer!=null){
                    seekBar.setProgress(integer);
                }
            }
        });

        activity.procesoLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s!=null){
                    procesoTextView.setText(s);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
