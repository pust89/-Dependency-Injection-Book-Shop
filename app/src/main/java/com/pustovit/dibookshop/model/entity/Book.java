package com.pustovit.dibookshop.model.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;



import java.io.Serializable;




import static androidx.room.ForeignKey.CASCADE;



//@Entity(tableName = "books", foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "book_category_id", onDelete = CASCADE)},
//        indices = {@Index("category_id")})

@Entity(tableName = "books_table", foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "book_category_id", onDelete = CASCADE)})
public class Book extends BaseObservable implements Serializable {

    @ColumnInfo(name = "book_id")
    @PrimaryKey(autoGenerate = true)
    private int book_id;

    @ColumnInfo(name = "book_title")
    private String book_title;


    @ColumnInfo(name = "book_description")
    private String book_description;

    @ColumnInfo(name = "book_price")
    private double book_price;

    @ColumnInfo(name = "book_category_id", index = true)
    private int book_category_id;


    public Book(int book_id, String book_title, String book_description, double book_price, int book_category_id) {
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_description = book_description;
        this.book_price = book_price;
        this.book_category_id = book_category_id;
    }

    @Ignore
    public Book(String book_title, String book_description, double book_price, int book_category_id) {
        this.book_title = book_title;
        this.book_description = book_description;
        this.book_price = book_price;
        this.book_category_id = book_category_id;
    }

    @Bindable
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
        notifyPropertyChanged(com.pustovit.dibookshop.BR.book_id);
    }

    @Bindable
    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
        notifyPropertyChanged(com.pustovit.dibookshop.BR.book_title);
    }

    @Bindable
    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
        notifyPropertyChanged(com.pustovit.dibookshop.BR.book_description);

    }

    @Bindable
    public double getBook_price() {
        return book_price;
    }

    public void setBook_price(double book_price) {
        this.book_price = book_price;
        notifyPropertyChanged(com.pustovit.dibookshop.BR.book_price);
    }

    @Bindable
    public int getBook_category_id() {
        return book_category_id;
    }

    public void setBook_category_id(int book_category_id) {
        this.book_category_id = book_category_id;
        notifyPropertyChanged(com.pustovit.dibookshop.BR.book_category_id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", book_title='" + book_title + '\'' +
                ", book_category_id=" + book_category_id +
                '}';
    }
}
