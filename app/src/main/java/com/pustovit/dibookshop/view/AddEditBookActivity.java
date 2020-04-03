package com.pustovit.dibookshop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.pustovit.dibookshop.R;
import com.pustovit.dibookshop.databinding.ActivityAddEditBookBinding;
import com.pustovit.dibookshop.model.entity.Book;
import com.pustovit.dibookshop.model.entity.Category;

import org.w3c.dom.Text;

public class AddEditBookActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_NEW_BOOK = 1;
    public static final int REQUEST_EDIT_BOOK = 2;

    private ActivityAddEditBookBinding activityBinding;

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_book);
        activityBinding.setClickHandlerAddEdit(new ClickHandlerAddEdit());
        Intent intent = getIntent();

        if (intent != null) {
            book = (Book) intent.getSerializableExtra(Book.class.getName());
            if (book != null) {
                activityBinding.setBook(book);
            }
        }
    }

    public class ClickHandlerAddEdit {

        public void onClickBtnSave(View view) {
            if (book != null && !TextUtils.isEmpty(book.getBook_title()) && !TextUtils.isEmpty(book.getBook_description()) &&
                    book.getBook_price() != 0) {
                Intent data = new Intent(AddEditBookActivity.this, MainActivity.class);
                data.putExtra(Book.class.getName(), book);
                setResult(RESULT_OK, data);
                finish();
            } else{
                Toast.makeText(AddEditBookActivity.this, "PLESSE FEEL ALL DATA!",Toast.LENGTH_LONG).show();
            }
        }
    }


}
