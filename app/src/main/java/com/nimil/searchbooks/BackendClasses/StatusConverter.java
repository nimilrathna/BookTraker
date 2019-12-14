package com.nimil.searchbooks.BackendClasses;

import androidx.room.TypeConverter;

import static com.nimil.searchbooks.BackendClasses.Book.BookStatus.COMPLETED;
import static com.nimil.searchbooks.BackendClasses.Book.BookStatus.FUTURE;
import static com.nimil.searchbooks.BackendClasses.Book.BookStatus.READING;

public class StatusConverter {
    @TypeConverter
    public static Book.BookStatus toBookStatus(Integer status) {
        if (status == READING.getCode()) {
            return READING;
        } else if (status == COMPLETED.getCode()) {
            return COMPLETED;
        } else if (status == FUTURE.getCode()) {
            return FUTURE;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static Integer toInteger(Book.BookStatus status) {
        if(status==null)
            return -1;
        return status.getCode();
    }
}
