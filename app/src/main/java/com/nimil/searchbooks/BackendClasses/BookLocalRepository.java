package com.nimil.searchbooks.BackendClasses;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class BookLocalRepository {
    private BookDao mBookDao;

    private LiveData<PagedList<Book>> mReadingBooks;
    public BookLocalRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        mReadingBooks=new LivePagedListBuilder<>(mBookDao.getReadingBooks(),10).build();

    }
   public LiveData<PagedList<Book>> getReadingBooks() {
        return mReadingBooks;
    }

    public long insert (Book book) {
        insertAsyncTask task=new insertAsyncTask(mBookDao);
        task.execute(book);
        return task.insertedRowId;
    }

    private static class insertAsyncTask extends AsyncTask<Book, Void, Long> {

        private BookDao mAsyncTaskDao;
        long insertedRowId=0;
        insertAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Book... params) {
            long rowId=mAsyncTaskDao.insert(params[0]);
            return rowId;
        }

        @Override
        protected void onPostExecute(Long rowid) {
            super.onPostExecute(rowid);
            insertedRowId=rowid;
        }
    }

    public void deleteAll(){
        new deleteAllWordsAsyncTask(mBookDao).execute();
    }
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    public void deleteBook(Book book)  {
        new deleteWordAsyncTask(mBookDao).execute(book);
    }
    private static class deleteWordAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao mAsyncTaskDao;

        deleteWordAsyncTask(BookDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Book... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
    public void updateBook(Book book){
        new updateWOrdAsyncTask(mBookDao).execute(book);
    }
    private static class updateWOrdAsyncTask extends AsyncTask<Book,Void,Void>{
        private BookDao mAsyncTaskDao;
        updateWOrdAsyncTask(BookDao dao){
            mAsyncTaskDao=dao;
        }
        @Override
        protected Void doInBackground(final Book... books) {
            mAsyncTaskDao.update(books[0]);
            return null;
        }
    }

    public void updateFavourite(String isbn){
        new updateFavouriteAsyncTask(mBookDao).execute(isbn);
    }
    private static class updateFavouriteAsyncTask extends AsyncTask<String,Void,Void>{
        private BookDao mAsyncTaskDao;
        updateFavouriteAsyncTask(BookDao dao){
            mAsyncTaskDao=dao;
        }
        @Override
        protected Void doInBackground(final String... Isbns) {
            mAsyncTaskDao.updateFavourite(Isbns[0]);
            return null;
        }
    }
}
