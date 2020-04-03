package com.pustovit.dibookshop.model.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



import java.util.Objects;

@Entity(tableName = "categories_table")
public class Category extends BaseObservable {

    @ColumnInfo(name = "category_id")
    @PrimaryKey(autoGenerate = true)
    private int category_id;

    @ColumnInfo(name = "category_name")
    private String category_name;

    public Category(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    @Ignore
    public Category(String category_name) {
        this.category_name = category_name;
    }

    @Bindable
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
        notifyPropertyChanged(com.pustovit.dibookshop.BR.category_id);
    }

    @Bindable
    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
        notifyPropertyChanged(com.pustovit.dibookshop.BR.category_name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return category_id == category.category_id &&
                category_name.equals(category.category_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, category_name);
    }
}
