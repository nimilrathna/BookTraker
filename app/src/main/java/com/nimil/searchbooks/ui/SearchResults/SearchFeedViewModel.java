package com.nimil.searchbooks.ui.SearchResults;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.nimil.searchbooks.AppController;
import com.nimil.searchbooks.rest.Book;
import com.nimil.searchbooks.rest.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.nimil.searchbooks.rest.BookSearchDataSource.*;

public class SearchFeedViewModel extends ViewModel {
    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Book>> bookLiveData;
    private String searchQuery;

    private AppController appController;
    public SearchFeedViewModel(@NonNull AppController appController,String query) {
        this.appController = appController;
        searchQuery=query;
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);

        BookDataFactory bookDataFactory = new BookDataFactory(appController,searchQuery);
        networkState = Transformations.switchMap(bookDataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        bookLiveData = (new LivePagedListBuilder(bookDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Book>> getBookLiveData() {
        return bookLiveData;
    }
}
