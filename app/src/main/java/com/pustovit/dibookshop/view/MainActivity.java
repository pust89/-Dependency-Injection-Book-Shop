package com.pustovit.dibookshop.view;

import android.content.Intent;
import android.os.Bundle;

import com.pustovit.dibookshop.App;
import com.pustovit.dibookshop.R;
import com.pustovit.dibookshop.databinding.ActivityMainBinding;
import com.pustovit.dibookshop.model.entity.Book;
import com.pustovit.dibookshop.model.entity.Category;
import com.pustovit.dibookshop.view.adapter.BookAdapter;
import com.pustovit.dibookshop.view.adapter.CategoryArrayAdapter;
import com.pustovit.dibookshop.viewmodel.MainActivityViewModel;
import com.pustovit.dibookshop.viewmodel.MainActivityViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tag";

    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;


    private List<Category> categories;
    private Spinner spinner;
    private CategoryArrayAdapter categoryAdapter;
    private Category selectedCategory;


    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;


    private final Observer<List<Book>> booksObserver = new Observer<List<Book>>() {
        @Override
        public void onChanged(List<Book> books) {
            if (bookAdapter != null) {
                bookAdapter.setNewData(books);
            }
        }
    };
    private LiveData<List<Book>> booksLiveData = null;

    @Inject
    public MainActivityViewModelFactory mainActivityViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getApp().getBookComponent().inject(this);
        mainActivityViewModel = new ViewModelProvider(MainActivity.this, mainActivityViewModelFactory).get(MainActivityViewModel.class);


        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        Toolbar toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);
        activityMainBinding.setClickHandler(new MainActivityClickHandler());

        getAllCategoriesInSpinner();
        setRecyclerView();
    }


    private void getAllCategoriesInSpinner() {
        spinner = activityMainBinding.layoutContentMain.spinner;

        categories = new ArrayList<>();
        categoryAdapter = new CategoryArrayAdapter(MainActivity.this, categories);

        activityMainBinding.setCategoryAdapter(categoryAdapter);
        mainActivityViewModel.getCategoriesLiveData().observe(MainActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoriesFromLiveData) {
                Log.d(TAG, "getAllCategoriesInSpinner: onChanged: called");
                categories.clear();
                categories.addAll(categoriesFromLiveData);
                categoryAdapter.notifyDataSetChanged();
                selectedCategory = mainActivityViewModel.getSelectedCategory();
                Log.d(TAG, "getAllCategoriesInSpinner: onChanged: selectedCategory=" + selectedCategory);
                spinner.setSelection(categories.indexOf(selectedCategory));
            }
        });

    }


    private void setRecyclerView() {
        recyclerView = activityMainBinding.layoutContentMain.rvBooks;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        bookAdapter = new BookAdapter(new BookAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Book book) {
                editBook(book);
            }
        });
        recyclerView.setAdapter(bookAdapter);

        ItemTouchHelper itemTouchHelperForDelete = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, "direction =" + direction, Toast.LENGTH_LONG).show();
                Book book = bookAdapter.getBook(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteBook(book);

            }
        });

        itemTouchHelperForDelete.attachToRecyclerView(recyclerView);

        ItemTouchHelper itemTouchHelperForClone = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, "direction =" + direction, Toast.LENGTH_LONG).show();
                Book origBook = bookAdapter.getBook(viewHolder.getAdapterPosition());
                mainActivityViewModel.insertBook(new Book("Copy" + origBook.getBook_title(),
                        origBook.getBook_description(),
                        origBook.getBook_price(), origBook.getBook_category_id()));
                bookAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelperForClone.attachToRecyclerView(recyclerView);

    }


    private void loadBooks(Category _selectedCategory) {
        if (booksLiveData == null) {
            booksLiveData = mainActivityViewModel.getBooksByCategory(_selectedCategory);
            booksLiveData.observe(MainActivity.this, booksObserver);
        } else {
            booksLiveData.removeObserver(booksObserver);
            booksLiveData = mainActivityViewModel.getBooksByCategory(_selectedCategory);
            booksLiveData.observe(MainActivity.this, booksObserver);
        }
    }


    public class MainActivityClickHandler {

        public void onFabClick(View view) {
            addNewBook();
        }

        public void spinnerSelectItem(AdapterView<?> parent, View view, int position, long id) {

            selectedCategory = (Category) parent.getItemAtPosition(position);
            mainActivityViewModel.setSelectedCategory(selectedCategory);
            loadBooks(selectedCategory);
        }
    }

    private void addNewBook() {
        Book book = new Book("", "", 0, selectedCategory.getCategory_id());
        Intent intent = new Intent(this, AddEditBookActivity.class);
        intent.putExtra(Book.class.getName(), book);
        startActivityForResult(intent, AddEditBookActivity.REQUEST_ADD_NEW_BOOK);
    }

    private void editBook(Book book) {
        Intent intent = new Intent(this, AddEditBookActivity.class);
        intent.putExtra(Book.class.getName(), book);
        startActivityForResult(intent, AddEditBookActivity.REQUEST_EDIT_BOOK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Book book = (Book) data.getSerializableExtra(Book.class.getName());
            if (requestCode == AddEditBookActivity.REQUEST_ADD_NEW_BOOK) {
                mainActivityViewModel.insertBook(book);
            } else if (requestCode == AddEditBookActivity.REQUEST_EDIT_BOOK) {
                mainActivityViewModel.updateBook(book);
            }
        }
    }
}
