package com.pustovit.dibookshop.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.pustovit.dibookshop.model.Repository;
import com.pustovit.dibookshop.model.entity.Book;
import com.pustovit.dibookshop.model.entity.Category;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "tag";
    private Repository repository;
    private Category selectedCategory;



    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        getSelectedCategoryFromPref();
    }





    public LiveData<List<Category>> getCategoriesLiveData() {
        return repository.getCategoriesLiveData();
    }


    public void setSelectedCategory(Category category) {
        selectedCategory = category;
        repository.putSelectedCategoryToPref(category);
    }

    private void getSelectedCategoryFromPref(){
        LiveData<Category> selectedCategoryFromPref= repository.getSelectedCategoryLiveDataFromSharedPref();
        selectedCategoryFromPref.observeForever(new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                Log.d(TAG, "MainActivityViewModel: onChanged: param"+category);
                selectedCategory = category;
                selectedCategoryFromPref.removeObserver(this);
            }
        });
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public Category getSelectedCategory(){
            return selectedCategory;
    }

    public void insertCategory(Category category) {
        repository.insertCategory(category);
    }

    public void updateCategory(Category category) {
        repository.updateCategory(category);
    }

    public void deleteCategory(Category category) {
        repository.deleteCategory(category);
    }


    public LiveData<List<Book>> getBooksByCategory(Category category) {
        return repository.getBooksByCategory(category);
    }


    public void insertBook(Book book) {
        repository.insertBook(book);
    }

    public void deleteBook(Book book) {
        repository.deleteBook(book);
    }

    public void updateBook(Book book) {
        repository.updateBook(book);
    }





}
