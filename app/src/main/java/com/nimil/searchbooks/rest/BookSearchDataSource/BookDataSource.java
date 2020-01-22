package com.nimil.searchbooks.rest.BookSearchDataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.nimil.searchbooks.AppController;
import com.nimil.searchbooks.rest.Book;
import com.nimil.searchbooks.rest.BookRestApi;
import com.nimil.searchbooks.rest.BookRestApiFactory;
import com.nimil.searchbooks.rest.Feed;
import com.nimil.searchbooks.rest.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookDataSource extends PageKeyedDataSource<Long, Book> {

    private static final String TAG = BookDataSource.class.getSimpleName();

    private AppController appController;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private String searchQuery;

    public BookDataSource(AppController appController,String query) {
        this.appController = appController;
        searchQuery=query;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, Book> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        appController.getRestApi().fetchFeed(searchQuery,params.requestedLoadSize)
                .enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        if(response.isSuccessful()) {
                            callback.onResult(response.body().getBooks(), null, 2l);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Book> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Book> callback) {

    }
}
