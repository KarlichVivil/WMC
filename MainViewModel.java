package net.htlgkr.notepad;

import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public static final int DEFAULT = 0;
    public static final int SHOW_NODE_SPACE = 1;
    public static final int SHOW_SAVE_SCREEN = 2;
    public static final int SHOW_SETTINGS_SCREEN = 3;
    public static final int SHOW_EDIT_SCREEN = 4;

    private int dateState = 0;

    private List<Note> noteList = new ArrayList<>();

    private Note editNote;

    private boolean expiredNotes = false;

    private MutableLiveData<Integer> _state = new MutableLiveData<>(SHOW_NODE_SPACE);
    // state can only be observed but not changed
    public LiveData<Integer> state = _state;

    // use specific methods to change the game, prevent misuse of the state variable
    public void showSaveScreen(){_state.postValue(SHOW_SAVE_SCREEN);}

    public void showNodeSpace(){_state.postValue(SHOW_NODE_SPACE);}

    public void showSettingsScreen(){_state.postValue(SHOW_SETTINGS_SCREEN);}

    public void showEditScreen(){_state.postValue(SHOW_EDIT_SCREEN);}

    public void addNode(Note note){noteList.add(note);}

    public List<Note> getNoteList(){return noteList;}

    public Note getEditNote(){return editNote;}

    public void setEditNode(Note editNote){
        noteList.remove(editNote);
        this.editNote = editNote;
    }

    public boolean isExpiredNotes() {
        return expiredNotes;
    }

    public void setExpiredNotes(boolean expiredNotes) {
        this.expiredNotes = expiredNotes;
    }

    public void readList(Context context){
        Gson gson = new Gson();

        String json = JsonHandler.readJSON(context);
        TypeToken<ArrayList<Note>> teipetoken = new TypeToken<ArrayList<Note>>(){};

        if(gson.fromJson(json, teipetoken) != null) noteList = gson.fromJson(json, teipetoken);
    }

    public int getDateState() {
        return dateState;
    }

    public void setDateState(int dateState) {
        this.dateState = dateState;
    }

    public void clearNotes(){
        noteList.clear();
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public List<Note> returnExpired(){
        List<Note> expiredNotes = new ArrayList<>();

        noteList.forEach(n -> {
            String date = n.getDate();
            String time = n.getTime();

            String str = date.replace("/", "-") + time;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

            LocalDateTime now = LocalDateTime.now();

            if(now.isAfter(dateTime)) expiredNotes.add(n);
        });

        return expiredNotes;
    }
}