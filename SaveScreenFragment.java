package net.htlgkr.notepad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveScreenFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaveScreenFragment() {
        // Required empty public constructor
    }

    private MainViewModel mainViewModel;
    private TextView descriptionSpace;
    private TextView titleSpace;
    private TextView dateTimeView;
    private FloatingActionButton datePicker;
    private FloatingActionButton timePicker;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;
    private int seconds;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveScreenFragment newInstance(String param1, String param2) {
        SaveScreenFragment fragment = new SaveScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_save_screen, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        descriptionSpace = view.findViewById(R.id.descriptionSpace);
        titleSpace = view.findViewById(R.id.titleSpace);
        view.findViewById(R.id.saveButton).setOnClickListener(this);

        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        dateTimeView = view.findViewById(R.id.dateTime);

        datePicker.setOnClickListener(this);
        timePicker.setOnClickListener(this);


        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.saveButton){
            String[] parts = (dateTimeView.getText() + "").split(" ");

            mainViewModel.addNode(new Note(titleSpace.getText() + "", descriptionSpace.getText() + "", parts[0], parts[1], false));
            Gson gson = new Gson();

            String json = gson.toJson(mainViewModel.getNoteList());
            JsonHandler.writeJSON(requireContext(), json);
            mainViewModel.showNodeSpace();
        }
        else if(view.getId() == R.id.datePicker){
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                String date = "";
                String monthS = "";
                String dayS = "";

                month = i1++;
                day = i2;
                year = i;

                if (month < 10) monthS = "0" + i1;
                else monthS = month + "";
                if(day < 10) dayS = "0"  + i2;
                else dayS = day + "";

                date = dayS + "/" + monthS + "/" + year;

                dateTimeView.setText(date);
            }, year, month, day);
            dialog.show();
        }
        else if(view.getId() == R.id.timePicker){
            TimePickerDialog dialog = new TimePickerDialog(getContext(), (timePicker, i, i1) -> {
                hour = i;
                minute = i1;
                String time = "";

                String hourS = "";
                String minuteS = "";

                if(minute == 0){
                    minuteS = "00";
                }
                else if(minute < 10){
                    minuteS = "0" + minute;
                }
                else{
                    minuteS = minute + "";
                }

                if(hour < 10){
                    hourS = "0" + hour;
                }
                else{
                    hourS = hour + "";
                }

                time = hourS + ":" + minuteS;

                if(dateTimeView != null){
                    dateTimeView.setText(dateTimeView.getText() + " " + time);
                }
            }, hour, minute, true);
            dialog.show();
        }
    }
}