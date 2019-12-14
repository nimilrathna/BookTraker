package com.nimil.searchbooks.ui.SearchResults;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.nimil.searchbooks.ui.SearchResults.NetworkUtils;

public class BookLoader extends AsyncTaskLoader<String> {
    private String mQueryString;

    BookLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }
}
