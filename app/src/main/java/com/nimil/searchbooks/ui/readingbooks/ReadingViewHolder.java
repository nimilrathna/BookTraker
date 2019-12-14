package com.nimil.searchbooks.ui.readingbooks;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimil.searchbooks.BackendClasses.Book;
import com.nimil.searchbooks.R;
import com.nimil.searchbooks.ui.SearchResults.SearchRecyclerAdapter;

public class ReadingViewHolder extends RecyclerView.ViewHolder {
    private ImageView mBookThumbnail;
    private TextView mBookTitle;
    private TextView mBookAuthor;
    private TextView mReadingStartDate;
    private ProgressBar mReadingProgress;
    public ReadingViewHolder(@NonNull View itemView) {
        super(itemView);
        mBookThumbnail=itemView.findViewById(R.id.image_reading_thubnail);
        mBookTitle=itemView.findViewById(R.id.text_reading_title);
        mBookAuthor=itemView.findViewById(R.id.text_reading_author);
        mReadingStartDate=itemView.findViewById(R.id.text_reading_startdate);
        mReadingProgress=itemView.findViewById(R.id.progress_reading);
    }
    void bindTo(final Book book, final ClickListener listener){
        mBookTitle.setText(book.getTitle());
        mBookAuthor.setText(book.getAuthors());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(book);
            }
        });
    }
    interface ClickListener{
        public void itemClick(Book book);
    }
}
