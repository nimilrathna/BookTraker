package com.nimil.searchbooks.ui.SearchResults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimil.searchbooks.BackendClasses.Book;
import com.nimil.searchbooks.R;

import java.util.List;

public class SearchRecyclerAdapter extends  RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private List<Book> mBookList;
    public ClickListener mClickListener;

    public SearchRecyclerAdapter(Context context, List<Book> bookList){
        mContext=context;
        mBookList=bookList;
    }
    public void setBookList(List<Book> bookList){
        mBookList=bookList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View itemView=layoutInflater.inflate(R.layout.search_result_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mBookList!=null){
            Book book=mBookList.get(position);
            holder.bindTo(book);
        }

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mBookThumbnail;
        private TextView mTextTitle;
        private TextView mTextDescription;
        private TextView mTextAuthor;
        private ImageButton mButtonFavourite;
        private ImageButton mButtonAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBookThumbnail=itemView.findViewById(R.id.image_book_thubnail);
            mTextTitle=itemView.findViewById(R.id.text_book_title);
            mTextDescription=itemView.findViewById(R.id.text_description);
            mTextAuthor=itemView.findViewById(R.id.text_author);
            mButtonFavourite=itemView.findViewById(R.id.button_favourite);
            mButtonAdd=itemView.findViewById(R.id.button_add);
        }
        void bindTo(final Book book){
            FetchImage task=new FetchImage(mContext,mBookThumbnail,book.getThumbnail());
            task.execute();
            mTextTitle.setText(book.getTitle());
            mTextDescription.setText(book.getDescription());
            mTextAuthor.setText(book.getAuthors());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(book);
                }
            });
           mButtonFavourite.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mClickListener.onAddFavouriteClick(v,book);
               }
           });
           mButtonAdd.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mClickListener.onAddButtonClick(book);
               }
           });

        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }
    interface ClickListener{
        public void onItemClick(Book book);
        public void onAddButtonClick(Book book);
        public void onAddFavouriteClick(View v,Book book);
    }
}
