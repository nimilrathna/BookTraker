package com.nimil.searchbooks.ui.SearchResults;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nimil.searchbooks.R;
import com.nimil.searchbooks.rest.Book;
import com.nimil.searchbooks.rest.NetworkState;
import com.squareup.picasso.Picasso;

public class FeedListAdapter extends PagedListAdapter<Book, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;
    ClickListener mClickListener;
    private Context context;
    private NetworkState networkState;
    public FeedListAdapter(Context context) {
        super(Book.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            View itemView=layoutInflater.inflate(R.layout.network_state_item,parent,false);
            return new FeedListAdapter.NetworkStateItemViewHolder(itemView);

        } else {
            View itemView=layoutInflater.inflate(R.layout.search_result_item,parent,false);
            return new FeedListAdapter.BookItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BookItemViewHolder) {
            ((BookItemViewHolder)holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindTo(networkState);
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    public class BookItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mBookThumbnail;
        private TextView mTextTitle;
        private TextView mTextDescription;
        private TextView mTextAuthor;
        private ImageButton mButtonFavourite;
        private ImageButton mButtonAdd;

        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mBookThumbnail=itemView.findViewById(R.id.image_book_thubnail);
            mTextTitle=itemView.findViewById(R.id.text_book_title);
            mTextDescription=itemView.findViewById(R.id.text_description);
            mTextAuthor=itemView.findViewById(R.id.text_author);
            mButtonFavourite=itemView.findViewById(R.id.button_favourite);
            mButtonAdd=itemView.findViewById(R.id.button_add);
        }

        void bindTo(final Book book){
            FetchImage task=new FetchImage(context,mBookThumbnail,book.getThumbnail());
            task.execute();
            Picasso.get().load(book.getThumbnail()).resize(250,200).into(mBookThumbnail);
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


    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mNetworkStateBar;
        private TextView mNetwork_error_msg;

        public NetworkStateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mNetworkStateBar=itemView.findViewById(R.id.network_state_bar);
            mNetwork_error_msg=itemView.findViewById(R.id.network_error_msg);
        }

        public void bindTo(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                mNetworkStateBar.setVisibility(View.VISIBLE);
            } else {
                mNetworkStateBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                mNetwork_error_msg.setVisibility(View.VISIBLE);
                mNetwork_error_msg.setText(networkState.getMsg());
            } else {
                mNetwork_error_msg.setVisibility(View.GONE);
            }
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