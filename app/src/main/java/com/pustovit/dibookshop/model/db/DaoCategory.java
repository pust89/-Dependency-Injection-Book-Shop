package com.pustovit.dibookshop.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pustovit.dibookshop.model.entity.Category;

import java.util.List;

@Dao
public interface DaoCategory {


    @Query("SELECT * FROM categories_table;")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM categories_table WHERE category_id==:categoryId;")
    LiveData<Category> getCategoryById(int categoryId);

    @Insert
    void insertCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Update
    void updateCategory(Category category);
}
