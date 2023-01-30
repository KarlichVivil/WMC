package net.htlgkr.notepad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListOverviewFragment extends Fragment implements View.OnClickListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private FloatingActionButton plusBtn;
    private MainViewModel model;
    private FloatingActionButton settingsButton;
    private FloatingActionButton deleteBtn;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListOverviewFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListOverviewFragment newInstance(int columnCount) {
        ListOverviewFragment fragment = new ListOverviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_overview_list, container, false);

        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

//        List<Note> noteList = model.getNoteList();
//        if(model.getDateState() == 0){
//
//            noteList.forEach(n -> {
//                String dateString = n.getDate() + n.getTime();
//                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//                LocalDateTime dateTime = LocalDateTime.parse(dateString, inputFormatter);
//                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss aa");
//                String outputDate = dateTime.format(outputFormatter);
//
//                String[] parts = outputDate.split(" ");
//                n.setDate(parts[0]);
//                n.setTime(parts[1] + " " + parts[2]);
//            });
//
//            model.setNoteList(noteList);
//
//            Gson gson = new Gson();
//
//            String json = gson.toJson(model.getNoteList());
//            JsonHandler.writeJSON(requireContext(), json);
//        }
//        else if(model.getDateState() == 1){
//
//            noteList.forEach(n -> {
//                String dateString = n.getDate() + n.getTime();
//                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss aa");
//                LocalDateTime dateTime = LocalDateTime.parse(dateString, inputFormatter);
//                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//                String outputDate = dateTime.format(outputFormatter);
//
//                String[] parts = outputDate.split(" ");
//                n.setDate(parts[0]);
//                n.setTime(parts[1]);
//            });
//
//            Gson gson = new Gson();
//
//            String json = gson.toJson(model.getNoteList());
//            JsonHandler.writeJSON(requireContext(), json);
//        }

        plusBtn = view.findViewById(R.id.newPlusBtn);
        plusBtn.setOnClickListener(this);

        deleteBtn = view.findViewById(R.id.deleteAllNotes);
        deleteBtn.setOnClickListener(this);

        settingsButton = view.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);

        if (view.findViewById(R.id.list) instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if(!model.isExpiredNotes()){
                recyclerView.setAdapter(new MyNoteRecyclerViewAdapter(model.getNoteList(), note -> {
                    model.showEditScreen();
                    model.setEditNode(note);
                    Gson gson = new Gson();

                    String json = gson.toJson(model.getNoteList());
                    JsonHandler.writeJSON(requireContext(), json);
                }));
            }
            else{
                recyclerView.setAdapter(new MyNoteRecyclerViewAdapter(model.returnExpired(), note -> {
                    model.showEditScreen();
                    model.setEditNode(note);
                    Gson gson = new Gson();

                    String json = gson.toJson(model.getNoteList());
                    JsonHandler.writeJSON(requireContext(), json);
                }));
            }
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.newPlusBtn) model.showSaveScreen();

        if(view.getId() == R.id.settingsButton) model.showSettingsScreen();

        if(view.getId() == R.id.deleteAllNotes){
            model.clearNotes();

            Gson gson = new Gson();

            String json = gson.toJson(model.getNoteList());
            JsonHandler.writeJSON(requireContext(), json);
        }
    }
}