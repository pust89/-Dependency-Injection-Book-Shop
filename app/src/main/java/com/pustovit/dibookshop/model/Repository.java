package com.pustovit.dibookshop.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.pustovit.dibookshop.model.db.BooksDatabase;
import com.pustovit.dibookshop.model.db.DaoBook;
import com.pustovit.dibookshop.model.db.DaoCategory;
import com.pustovit.dibookshop.model.entity.Book;
import com.pustovit.dibookshop.model.entity.Category;
import com.pustovit.dibookshop.view.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class Repository {
    private static final String TAG = "tag";
    private BooksDatabase bookDatabase;
    private DaoBook daoBook;
    private DaoCategory daoCategory;

    private SharedPreferences sharedPreferences;
    private final String APP_PREFERENCES = "DI_BOOKSHOP_PREFERENCES";
    private final String PREF_SELECTED_CATEGORY_ID = "SELECTED_CATEGORY_ID";



    public Repository(Application application) {
        bookDatabase = BooksDatabase.getInstance(application);
        daoBook = bookDatabase.getDaoBook();
        daoCategory = bookDatabase.getDaoCategory();



        sharedPreferences = application.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void putSelectedCategoryToPref(Category category) {
        if (category != null) {
            sharedPreferences.edit().putInt(PREF_SELECTED_CATEGORY_ID, category.getCategory_id()).apply();
        }
    }


    public LiveData<Category> getSelectedCategoryLiveDataFromSharedPref() {
        int selectedCategoryId = sharedPreferences.getInt(PREF_SELECTED_CATEGORY_ID, 0);
        return getCategoryById(selectedCategoryId);
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return daoCategory.getAllCategories();
    }

    public void insertCategory(Category category) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                daoCategory.insertCategory(category);
            }
        });
    }

    public void updateCategory(Category category) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                daoCategory.updateCategory(category);
            }
        });
    }

    public void deleteCategory(Category category) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                daoCategory.deleteCategory(category);
            }
        });
    }

    private LiveData<Category> getCategoryById(int categoryId) {
        return daoCategory.getCategoryById(categoryId);
    }

    //TODO see this!
    public LiveData<List<Book>> getBooksByCategory(Category category) {
        int categoryId = 0;
        if (category != null) {
            categoryId = category.getCategory_id();
        }
        Log.d(TAG, "================================================");
        Log.d(TAG, "getBooksByCategory: categoryId="+categoryId);
        LiveData<List<Book>> tempLD= daoBook.getBooksByCategoryId(categoryId);

        Log.d(TAG, "getBooksByCategory: tempLD="+tempLD.hashCode());
        Log.d(TAG, "================================================");

        return tempLD;
    }


    public void insertBook(final Book book) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                long id = daoBook.insertBook(book);
            }
        });
    }

    public void updateBook(Book book) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                daoBook.updateBook(book);
            }
        });
    }

    public void deleteBook(Book book) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                daoBook.deleteBook(book);
            }
        });
    }

}
