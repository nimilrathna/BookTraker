package com.nimil.searchbooks.BackendClasses;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Entity;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Book.class}, version=2,exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    private static BookDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BookDao mDao;

        PopulateDbAsync(BookDatabase db) {
            mDao = db.bookDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            if (mDao.getAnyBook().length < 1) {
                mDao.deleteAll();
                List bookList=new ArrayList<Book>();
              /*  for (int i = 0; i <= words.length - 1; i++) {
                    wordList.add(new Word(words[i]));
                    //Word word = new Word(words[i]);
                }
                mDao.insertWords(wordList);*/
            }
            return null;
        }
    }
    static BookDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "books_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
