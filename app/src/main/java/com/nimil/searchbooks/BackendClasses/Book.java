package com.nimil.searchbooks.BackendClasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "book_table", indices = {@Index(value = {"ISBN"},unique = true)})
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String ISBN;
    @NonNull
    private String title;
    @NonNull
    private String authors;
    private String description;
    @NonNull
    private int pageCount;
    private String publisher;
    private String publishedDate;
    private String thumbnail;
    private String selfLink;
    private String webLink;
    private boolean isFavourite;
    @TypeConverters(StatusConverter.class)
    private BookStatus bookStatus;


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
        this.isFavourite=false;
    }
    public Book(){
        isFavourite=false;
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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

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
