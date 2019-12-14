package com.nimil.searchbooks.ui.readingbooks;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nimil.searchbooks.BackendClasses.BookLocalRepository;

public class ReadingViewModelFactory implements ViewModelProvider.Factory
{
    private BookLocalRepository mBookLocalRepository;
    ReadingViewModelFactory(BookLocalRepository repository){
        mBookLocalRepository =repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ReadingViewModel(mBookLocalRepository);
    }
}
