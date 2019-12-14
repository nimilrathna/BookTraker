package com.nimil.searchbooks.ui.SearchResults;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nimil.searchbooks.BackendClasses.Book;
import com.nimil.searchbooks.BackendClasses.BookLocalRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsViewModel extends ViewModel {
    public MutableLiveData<List<Book>> mBookList;
    BookLocalRepository mBookLocalRepository;
    String mSearchQuery;
    public int insertCount;
    public SearchResultsViewModel(BookLocalRepository repository,String searchQuery) {
        mBookLocalRepository=repository;
        mSearchQuery=searchQuery;
    }

    public LiveData<List<Book>> getBookList(){
        if(mBookList==null){
            mBookList=new MutableLiveData<List<Book>>();
            loadBooks();
        }
        return mBookList;
    }

    private void loadBooks() {
        Book[] book=new Book[3];
        FetchBook fetchTask=new FetchBook(this,mSearchQuery);
        fetchTask.execute();
    }
    public void addBook(Book book){
        mBookLocalRepository.insert(book);
    }
    public void addFavourite(Book book){
            if(mBookLocalRepository.insert(book)==-1) {
                mBookLocalRepository.updateFavourite(book.getISBN());
            }
    }

}
