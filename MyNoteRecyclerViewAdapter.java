package net.htlgkr.notepad;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import net.htlgkr.notepad.databinding.FragmentListOverviewBinding;

import java.util.List;

public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;
    private ItemClickListener mItemClickListener;

    public MyNoteRecyclerViewAdapter(List<Note> items, ItemClickListener mItemClickListener) {
        mValues = items;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentListOverviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getTitle() + ":");
        holder.mContentView.setText(mValues.get(position).getDescription());
        holder.mCalendarView.setText(mValues.get(position).getDate() + mValues.get(position).getTime());

        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(mValues.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemClickListener{
        void onItemClick(Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox mFinished;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mCalendarView;
        public Note mItem;

        public ViewHolder(FragmentListOverviewBinding binding) {
            super(binding.getRoot());
            mFinished = binding.checkBox;
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mCalendarView = binding.calendar;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}