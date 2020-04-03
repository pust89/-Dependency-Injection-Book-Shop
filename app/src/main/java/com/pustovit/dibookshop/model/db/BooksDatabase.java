package com.pustovit.dibookshop.model.db;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pustovit.dibookshop.R;
import com.pustovit.dibookshop.model.entity.Book;
import com.pustovit.dibookshop.model.entity.Category;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Book.class}, version = 1, exportSchema = false)
public abstract class BooksDatabase extends RoomDatabase {
    private static BooksDatabase instance;

    public abstract DaoBook getDaoBook();

    public abstract DaoCategory getDaoCategory();


    public static synchronized BooksDatabase getInstance(Application application) {

        if (instance == null) {

            instance = Room.databaseBuilder(application.getApplicationContext(), BooksDatabase.class, application.getString(R.string.database_name))
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDatabaseCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executor executor = Executors.newSingleThreadExecutor();

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    DaoCategory daoCategory = instance.getDaoCategory();
                    DaoBook daoBook = instance.getDaoBook();

                    daoCategory.insertCategory(new Category(1, "Detective"));
                    daoCategory.insertCategory(new Category(2, "Fantasy"));
                    daoCategory.insertCategory(new Category(3, "Erotic"));
                    daoCategory.insertCategory(new Category(4, "Roman"));

                    daoCategory.insertCategory(new Category(5, "Classic"));
                    daoCategory.insertCategory(new Category(6, "Dystopia"));
                    daoCategory.insertCategory(new Category(7, "Cyberpunk"));
                    daoCategory.insertCategory(new Category(8, "Fairy tale"));

                    int id=0;
                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Detective id="+id, "Detective description id="+id, 10.00f+i, 1);
                        daoBook.insertBook(book);
                    }

                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Fantasy id="+id, "Fantasy description id="+id, 11.00f+i, 2);
                        daoBook.insertBook(book);
                    }

                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Erotic id="+id, "Erotic description id="+id, 12.00f+i, 3);
                        daoBook.insertBook(book);
                    }

                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Roman id="+id, "Roman description id="+id, 13.00f+i, 4);
                        daoBook.insertBook(book);
                    }

                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Classic id="+id, "Classic description id="+id, 14.00f+i, 5);
                        daoBook.insertBook(book);
                    }

                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Dystopia id="+id, "Dystopia description id="+id, 15.00f+i, 6);
                        daoBook.insertBook(book);
                    }

                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Cyberpunk id="+id, "Cyberpunk description id="+id, 16.00f+i, 7);
                        daoBook.insertBook(book);
                    }

                    for (int i = 1; i < 6; i++) {
                        Book book = new Book(++id,"Fairy tale id="+id, "Fairy tale description id="+id, 17.00f+i, 8);
                        daoBook.insertBook(book);
                    }
                }
            });


        }
    };
}
