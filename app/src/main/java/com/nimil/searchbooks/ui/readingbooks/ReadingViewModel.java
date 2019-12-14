package com.nimil.searchbooks.ui.readingbooks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.nimil.searchbooks.BackendClasses.Book;
import com.nimil.searchbooks.BackendClasses.BookLocalRepository;

import java.util.List;

public class ReadingViewModel extends ViewModel {

    private LiveData<PagedList<Book>> mReadingBooks;
    private BookLocalRepository mBookLocalRepository;
    public ReadingViewModel(BookLocalRepository repository) {
        mReadingBooks = new MutableLiveData<>();
        mBookLocalRepository =repository;
        mReadingBooks= mBookLocalRepository.getReadingBooks();
    }

    public LiveData<PagedList<Book>> getReadingBooks() {
        return mReadingBooks;
    }
}