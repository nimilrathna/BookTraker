package com.nimil.searchbooks.ui.readingbooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nimil.searchbooks.BackendClasses.Book;
import com.nimil.searchbooks.R;

public class ReadingAdapter extends PagedListAdapter<Book,ReadingViewHolder>{
    ReadingViewHolder.ClickListener mClickListener;

    protected ReadingAdapter() {
        super(DIFF_CALLBACK);
    }
    public void setClickListener(ReadingViewHolder.ClickListener listener){
        mClickListener=listener;
    }
    public ReadingViewHolder.ClickListener getClickListener(){
        return mClickListener;
    }

    @NonNull
    @Override
    public ReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.reading_item,parent,false);
        return new ReadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingViewHolder holder, int position) {
        holder.bindTo(getItem(position),mClickListener);
    }
    private static DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Book>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Book oldBook, Book newBook) {
                    return oldBook.getId() == newBook.getId();
                }

                @Override
                public boolean areContentsTheSame(Book oldBook,
                                                  Book newBook) {
                    return oldBook.equals(newBook);
                }
            };


    public Book getWordAtPosition (int position) {
        return getItem(position);
        //return mWords.get(position);
    }
}
