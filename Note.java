package net.htlgkr.notepad;

public class Note {
    private String title;
    private String description;
    private String date;
    private String time;
    private boolean finished;

    public Note(String title, String description, String date, String time, boolean finished) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        if(time == null) return "";

        return " " + time;
    }

    public String getDate() {
        if(time == null) return "";
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
