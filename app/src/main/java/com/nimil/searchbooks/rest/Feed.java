package com.nimil.searchbooks.rest;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

public class Feed implements Parcelable {

    private transient long id;
    private String status;
    private long totalResults;
    private List<Book> books;

    protected Feed(Parcel in) {
        id = getRandomNumber();
        status = in.readString();
        totalResults = in.readLong();
        books = in.createTypedArrayList(Book.CREATOR);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(status);
        dest.writeLong(totalResults);
        dest.writeTypedList(books);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setArticles(List<Book> books) {
        this.books = books;
    }


    public static long getRandomNumber() {
        long x = (long) ((Math.random() * ((100000 - 0) + 1)) + 0);
        return x;
    }


}
