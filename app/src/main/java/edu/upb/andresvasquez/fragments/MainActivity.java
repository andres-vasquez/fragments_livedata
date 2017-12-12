package edu.upb.andresvasquez.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Fragment formFragment;
    private Fragment resultFragment;

    public MutableLiveData<String> nameLiveData;
    public MutableLiveData<Integer> progressLiveData;
    public MutableLiveData<String> procesoLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formFragment = new FormFragment();
        resultFragment = new ResultFragment();

        nameLiveData = new MutableLiveData<>();
        progressLiveData = new MutableLiveData<>();
        procesoLiveData = new MutableLiveData<>();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.resultFrameLayout,resultFragment)
                .commit();
    }
}
