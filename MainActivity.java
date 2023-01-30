package net.htlgkr.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TODO: Program crashes while trying to format date
    //TODO: No idea how to display "finished" messages


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);
        model.readList(getApplicationContext());

        model.showNodeSpace();

        model.state.observe(this, state -> {
            FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
            if(state == MainViewModel.SHOW_NODE_SPACE) {
                fragTransaction.replace(R.id.cl_main, ListOverviewFragment.newInstance(1) , "Overview");
            } else if(state == MainViewModel.SHOW_SAVE_SCREEN) {
                fragTransaction.replace(R.id.cl_main, SaveScreenFragment.newInstance("", "") , "Save Screen");
            } else if(state == MainViewModel.SHOW_SETTINGS_SCREEN) {
                fragTransaction.replace(R.id.cl_main, SettingsFragment.newInstance("", "") , "Settings Screen");
            } else if(state == MainViewModel.SHOW_EDIT_SCREEN) {
                fragTransaction.replace(R.id.cl_main, EditFragment.newInstance("", "") , "Edit Screen");
            }
            fragTransaction.commit();
        });
    }
}