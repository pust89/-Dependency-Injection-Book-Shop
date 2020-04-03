package com.pustovit.dibookshop.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pustovit.dibookshop.model.entity.Book;

import java.util.List;

@Dao
public interface DaoBook {


//    @Query("SELECT * FROM books;")
//    LiveData<List<Book>> getAllBooks();


    @Query("SELECT * FROM books_table WHERE book_category_id == :categoryId;")
    LiveData<List<Book>> getBooksByCategoryId(int categoryId);

    @Insert
    long insertBook(Book book);

    @Delete
    void deleteBook(Book book);

    @Update
    void updateBook(Book book);

}
