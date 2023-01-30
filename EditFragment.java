package net.htlgkr.notepad;

import android.annotation.SuppressLint;
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
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainViewModel mainViewModel;
    private TextView descriptionSpace;
    private TextView titleSpace;
    private TextView dateTimeView;
    private FloatingActionButton datePicker;
    private FloatingActionButton timePicker;
    private Note editNote;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;
    private int seconds;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        editNote = mainViewModel.getEditNote();

        descriptionSpace = view.findViewById(R.id.descriptionSpaceEdit);
        descriptionSpace.setText(editNote.getDescription());

        titleSpace = view.findViewById(R.id.titleSpaceEdit);
        titleSpace.setText(editNote.getTitle());

        view.findViewById(R.id.editButton).setOnClickListener(this);

        datePicker = view.findViewById(R.id.datePickerEdit);
        timePicker = view.findViewById(R.id.timeSelectEdit);

        dateTimeView = view.findViewById(R.id.dateTimeEdit);
        dateTimeView.setText(editNote.getDate() + editNote.getTime());

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

        if(view.getId() == R.id.editButton){
            String[] parts = (dateTimeView.getText() + "").split("-");

            editNote.setTitle(titleSpace.getText() + "");
            editNote.setDescription(descriptionSpace.getText() + "");
            editNote.setDate(parts[0]);
            editNote.setTime(parts[1]);

            mainViewModel.addNode(editNote);
            Gson gson = new Gson();

            String json = gson.toJson(mainViewModel.getNoteList());
            JsonHandler.writeJSON(requireContext(), json);
            mainViewModel.showNodeSpace();
        }
        else if(view.getId() == R.id.datePickerEdit){
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {

                i1 = month + 1;
                String date = i2 + "/" + i1 + "/" + i;

                dateTimeView.setText(date);
            }, year, month, day);
            dialog.show();
        }
        else if(view.getId() == R.id.timeSelectEdit){
            TimePickerDialog dialog = new TimePickerDialog(getContext(), (timePicker, i, i1) -> {
                hour = i;
                minute = i1;
                String time = "";

                if(minute == 0){
                    time = hour + ":00";
                }
                else if(minute < 10){
                    time = hour + ":0" + minute;
                }
                else{
                    time = hour + ":" + minute;
                }

                if(dateTimeView != null){
                    dateTimeView.setText(dateTimeView.getText() + "-" + time);
                }
            }, hour, minute, true);
            dialog.show();
        }
    }
}