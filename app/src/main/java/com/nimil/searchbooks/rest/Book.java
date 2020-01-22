package com.nimil.searchbooks.rest;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.nimil.searchbooks.BackendClasses.StatusConverter;

public class Book implements Parcelable {

    private int id;
    private String ISBN;
    private String title;
    private String authors;
    private String description;
    private int pageCount;
    private String publisher;
    private String publishedDate;
    private String thumbnail;
    private String selfLink;
    private String webLink;

    protected Book(Parcel in) {
        id = in.readInt();
        ISBN = in.readString();
        title = in.readString();
        authors = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        publisher = in.readString();
        publishedDate = in.readString();
        thumbnail = in.readString();
        selfLink = in.readString();
        webLink = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(ISBN);
        dest.writeString(title);
        dest.writeString(authors);
        dest.writeString(description);
        dest.writeInt(pageCount);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(thumbnail);
        dest.writeString(selfLink);
        dest.writeString(webLink);
    }


    public enum BookStatus {
        READING(0),
        COMPLETED(1),
        FUTURE(2);

        private Integer code;

         BookStatus(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }
    public Book(String ISBN, String name, String author, String description, int pageCount,
                String publisher, String publishedDate, String thumbnail, String selfLink, String webLink){
        this.ISBN=ISBN;
        this.title =name;
        this.authors =author;
        this.description=description;
        this.pageCount=pageCount;
        this.publisher=publisher;
        this.publishedDate=publishedDate;
        this.thumbnail=thumbnail;
        this.selfLink=selfLink;
        this.webLink=webLink;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public static DiffUtil.ItemCallback<Book> DIFF_CALLBACK = new DiffUtil.ItemCallback<Book>() {
        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        Book word = (Book) obj;
        return (word.getISBN() == this.getISBN() && word.getTitle()==this.getTitle());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
