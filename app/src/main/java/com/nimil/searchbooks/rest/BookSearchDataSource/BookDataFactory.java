package com.nimil.searchbooks.rest.BookSearchDataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.nimil.searchbooks.AppController;


public class BookDataFactory extends DataSource.Factory {
    private MutableLiveData<BookDataSource> mutableLiveData;
    private BookDataSource bookDataSource;
    private AppController appController;
    private String searchQuery;

    public BookDataFactory(AppController appController,String query) {
        this.appController = appController;
        this.mutableLiveData = new MutableLiveData<BookDataSource>();
        searchQuery=query;
    }

    @Override
    public DataSource create() {
        bookDataSource = new BookDataSource(appController,searchQuery);
       // mutableLiveData.postValue(bookDataSource);
        return bookDataSource;
    }


    public MutableLiveData<BookDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
