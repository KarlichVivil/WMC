package net.htlgkr.notepad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MainViewModel mainViewModel;
    private SwitchMaterial format;
    private FloatingActionButton backToMenu;
    private Button displayUnfinished;
    private Button displayExpired;


    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        format = view.findViewById(R.id.dateSwitch);
        format.setOnClickListener(this);

        backToMenu = view.findViewById(R.id.overviewBtn);
        backToMenu.setOnClickListener(this);

        displayUnfinished = view.findViewById(R.id.finishedNotesBtn);
        displayUnfinished.setOnClickListener(this);

        displayExpired = view.findViewById(R.id.displayExpired);
        displayExpired.setOnClickListener(this);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.overviewBtn){
            mainViewModel.showNodeSpace();
        }

        if(view.getId() == R.id.dateSwitch){
            if(format.isActivated()){
                mainViewModel.setDateState(0);
            }
            else{
                mainViewModel.setDateState(1);
            }
        }

        if(view.getId() == R.id.displayExpired){
            if(mainViewModel.isExpiredNotes()) mainViewModel.setExpiredNotes(false);

            if(!mainViewModel.isExpiredNotes()) mainViewModel.setExpiredNotes(true);
        }
    }
}