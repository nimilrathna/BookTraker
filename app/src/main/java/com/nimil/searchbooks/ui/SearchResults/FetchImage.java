package com.nimil.searchbooks.ui.SearchResults;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.nimil.searchbooks.R;

import java.lang.ref.WeakReference;

import static com.nimil.searchbooks.ui.SearchResults.NetworkUtils.getBitmapFromURL;

public class FetchImage extends AsyncTask<Void,Void,Bitmap> {
    private String imageUrl;
    private Context mContext;
    private WeakReference<ImageView> mBookThubnail;
    FetchImage(Context context, ImageView imageView, String url){
        mContext=context;
        mBookThubnail=new WeakReference<>(imageView);
        imageUrl=url;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap=NetworkUtils.getBitmapFromURL(imageUrl);
        NetworkUtils.getResizedBitmap(bitmap
                ,mContext.getResources().getInteger(R.integer.book_thumbnail_height)
                ,mContext.getResources().getInteger(R.integer.book_thumbnail_width));
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mBookThubnail.get().setImageBitmap(bitmap);

    }
}
