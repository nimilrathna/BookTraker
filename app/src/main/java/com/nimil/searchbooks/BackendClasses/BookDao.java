package com.nimil.searchbooks.BackendClasses;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Book book);

    @Insert
    public void insertBooks(List<Book> books);

    @Query("DELETE FROM book_table")
    void deleteAll();

    @Query("SELECT * from book_table ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();
    //@Query("SELECT * from word_table ORDER BY word ASC")
    //DataSource.Factory<Integer,Word> getAllWords();
    @Query("SELECT * from book_table LIMIT 1")
    Book[] getAnyBook();

    @Query("SELECT * FROM book_table WHERE bookStatus=0 ORDER BY title DESC")
    DataSource.Factory<Integer, Book> getReadingBooks();

    @Delete
    void deleteWord(Book book);

    @Update
    void update(Book... books);

    @Query("UPDATE book_table SET isFavourite= CASE isFavourite WHEN 0 THEN 1 ELSE 0 END where ISBN=:isbn")
    void updateFavourite(String isbn);
}
