package com.nimil.searchbooks.ui.SearchResults;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nimil.searchbooks.BackendClasses.BookLocalRepository;

public class SearchResultsViewFactory implements ViewModelProvider.Factory {
    private String mQuery;
    private BookLocalRepository mLocalRepository;
    public SearchResultsViewFactory(BookLocalRepository repository,String query){
        mLocalRepository=repository;
        mQuery=query;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchResultsViewModel(mLocalRepository,mQuery);
    }
}
